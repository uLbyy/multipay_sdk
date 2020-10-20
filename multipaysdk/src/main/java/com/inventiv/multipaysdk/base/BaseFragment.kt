package com.inventiv.multipaysdk.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.inventiv.multipaysdk.MultiPaySdkListener
import com.inventiv.multipaysdk.util.KEY_MULTIPAY_SDK_LISTENER

internal abstract class BaseFragment<V : ViewBinding> : Fragment() {

    private var binding: V? = null

    private var multipaySdkListener: MultiPaySdkListener? = null

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createBinding(inflater, container, savedInstanceState).also { binding = it }.root
    }

    protected abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): V

    protected fun requireBinding(): V = requireNotNull(binding)

    protected fun requireMultipaySdkListener() = requireNotNull(multipaySdkListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        multipaySdkListener =
            arguments?.getSerializable(KEY_MULTIPAY_SDK_LISTENER) as? MultiPaySdkListener
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}