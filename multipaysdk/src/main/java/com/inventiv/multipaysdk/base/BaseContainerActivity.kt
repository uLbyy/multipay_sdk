package com.inventiv.multipaysdk.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.MultiPaySdkListener
import com.inventiv.multipaysdk.R
import com.inventiv.multipaysdk.databinding.ActivityCommonBinding
import com.inventiv.multipaysdk.util.KEY_MULTIPAY_SDK_LISTENER
import com.inventiv.multipaysdk.util.addFragment
import com.inventiv.multipaysdk.util.updateBaseContextLocale

internal abstract class BaseContainerActivity : AppCompatActivity() {

    private var multipaySdkListener: MultiPaySdkListener? = null

    private var isBackPressed = false

    private lateinit var binding: ActivityCommonBinding

    override fun attachBaseContext(newBase: Context?) {
        if (newBase == null) {
            super.attachBaseContext(newBase)
        } else {
            super.attachBaseContext(
                updateBaseContextLocale(
                    newBase,
                    MultiPaySdk.getComponent().language().id
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiPaySdk.getComponent().activityCreated()
        isBackPressed = false
        super.onCreate(savedInstanceState)
        multipaySdkListener =
            intent.getSerializableExtra(KEY_MULTIPAY_SDK_LISTENER) as? MultiPaySdkListener
        binding = ActivityCommonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragment = supportFragmentManager.findFragmentById(R.id.layout_container)
        if (fragment == null) {
            addFragment(fragmentWithGlobalListener(), R.id.layout_container)
        }
        binding.toolbar.setNavigationOnClickListener {
            isBackPressed = true
            finish()
        }

    }

    protected abstract fun fragment(): Fragment

    private fun fragmentWithGlobalListener(): Fragment {
        return fragment().apply {
            multipaySdkListener?.let {
                val args = Bundle().apply {
                    putSerializable(KEY_MULTIPAY_SDK_LISTENER, multipaySdkListener)
                }
                if (arguments == null) {
                    arguments = args
                } else {
                    arguments?.putAll(args)
                }
            }
        }
    }

    fun requireMultipaySdkListener() = requireNotNull(multipaySdkListener)

    fun requireBinding() = binding

    override fun onBackPressed() {
        super.onBackPressed()
        isBackPressed = true
    }

    override fun onDestroy() {
        val activityCount = MultiPaySdk.getComponent().activityDestroyed()
        if (isBackPressed && activityCount == 0) {
            requireMultipaySdkListener().onCancelled()
        }
        super.onDestroy()
    }
}