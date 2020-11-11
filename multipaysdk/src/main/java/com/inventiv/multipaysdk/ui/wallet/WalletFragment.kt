package com.inventiv.multipaysdk.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.base.BaseFragment
import com.inventiv.multipaysdk.data.model.EventObserver
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.data.model.response.WalletResponse
import com.inventiv.multipaysdk.databinding.FragmentWalletBinding
import com.inventiv.multipaysdk.repository.WalletRepository
import com.inventiv.multipaysdk.util.showSnackBarAlert


internal class WalletFragment : BaseFragment<FragmentWalletBinding>() {

    companion object {
        fun newInstance() = WalletFragment()
    }

    private val viewModel: WalletViewModel by viewModels {
        WalletViewModelFactory(WalletRepository(MultiPaySdk.getComponent().apiService()))
    }

    private lateinit var listAdapter: WalletAdapter

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentWalletBinding = FragmentWalletBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = WalletAdapter(WalletListener { walletResponse ->
            viewModel.selectWallet(walletResponse)
        })
        prepareRecyclerView()
        subscribeWallet()
        viewModel.walletsListItem()
    }

    private fun prepareRecyclerView() {
        requireBinding().listWallets.apply {
            setHasFixedSize(true)
            adapter = listAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun createTestList(): MutableList<WalletListItem> {
        val walletList: MutableList<WalletListItem> = mutableListOf()
        walletList.add(
            WalletListItem(
                WalletResponse(
                    1,
                    "Multinet",
                    "Benim Kartım",
                    "1234 **** **** 1234",
                    "asdasdasd",
                    "30 TL"
                )
            )
        )
        walletList.add(
            WalletListItem(
                WalletResponse(
                    1,
                    "Multinet",
                    "Yedek Kartım",
                    "1235 **** **** 1235",
                    "asdxcvdfs",
                    "20 TL"
                )
            ).apply { isChecked = true })
        walletList.add(
            WalletListItem(
                WalletResponse(
                    2,
                    "Multinet Gift",
                    "Ücüncü Kartım",
                    "1214 **** **** 1214",
                    "asdasdasdzxs",
                    "40 TL"
                )
            )
        )
        walletList.add(
            WalletListItem(
                WalletResponse(
                    2,
                    "Multinet Gift",
                    "Dördüncü Kartım",
                    "1224 **** **** 1214",
                    "asdasdasdzxs1",
                    "110 TL"
                )
            )
        )
        walletList.add(
            WalletListItem(
                WalletResponse(
                    2,
                    "Multinet Gift",
                    "Beşinci Kartım",
                    "1234 **** **** 1214",
                    "asdasdasdzxs2",
                    "140 TL"
                )
            )
        )
        walletList.add(
            WalletListItem(
                WalletResponse(
                    2,
                    "Multinet Gift",
                    "Ücüncü Kartım",
                    "1244 **** **** 1214",
                    "asdasdasdzxs3",
                    "450 TL"
                )
            )
        )
        walletList.add(
            WalletListItem(
                WalletResponse(
                    2,
                    "Multinet Gift",
                    "Ücüncü Kartım",
                    "1254 **** **** 1214",
                    "asdasdasdzxs4",
                    "4 TL"
                )
            )
        )
        walletList.add(
            WalletListItem(
                WalletResponse(
                    2,
                    "Multinet Gift",
                    "Ücüncü Kartım",
                    "1264 **** **** 1214",
                    "asdasdasdzxs5",
                    "40,23 TL"
                )
            )
        )
        return walletList
    }

    private fun subscribeSelectedWallet() {
        viewModel.selectedWallet.observe(viewLifecycleOwner, Observer { walletResponse ->
            listAdapter.submitList(listAdapter.currentList.apply {
                this.map { it.isChecked = false }
                this.find { item -> item.walletResponse.token == walletResponse.token }?.isChecked =
                    true
            })
//            listAdapter.notifyDataSetChanged()
            /*listAdapter.currentList.apply {
                this.map { it.isChecked = false }
                this.find { item -> item.walletResponse.token == walletResponse.token }?.isChecked = true
            }*/
        })
    }

    private fun subscribeWallet() {
        viewModel.walletsListItemResult.observe(viewLifecycleOwner, EventObserver { resource ->
            when (resource) {
                is Resource.Loading -> {
                    setLayoutProgressVisibility(View.VISIBLE)
                }
                is Resource.Success -> {
                    val walletList = resource.data
                    setLayoutProgressVisibility(View.GONE)
                    listAdapter.submitList(walletList)
                    subscribeSelectedWallet()
                }
                is Resource.Failure -> {
                    showSnackBarAlert(resource.message)
                    setLayoutProgressVisibility(View.GONE)
                    val list: MutableList<WalletListItem> = mutableListOf()
                    listAdapter.submitList(createTestList())
//                    listAdapter.notifyDataSetChanged()
                    subscribeSelectedWallet()
                }
            }
        })
    }

    private fun setLayoutProgressVisibility(visibility: Int) {
        requireBinding().walletProgress.layoutProgress.visibility = visibility
    }
}