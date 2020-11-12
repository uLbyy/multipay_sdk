package com.inventiv.multipaysdk.ui.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inventiv.multipaysdk.data.model.response.WalletResponse
import com.inventiv.multipaysdk.databinding.ItemWalletSingleSelectBinding

internal class WalletAdapter(private val clickListener: WalletListener) :
    ListAdapter<WalletListItem, WalletAdapter.ViewHolder>(WalletDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val walletItem = getItem(position)
        holder.bind(walletItem, clickListener)
    }

    class ViewHolder private constructor(private val binding: ItemWalletSingleSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(walletListItem: WalletListItem, clickListener: WalletListener) {
            binding.textWalletName.text =
                if (walletListItem.walletResponse.alias.isNullOrEmpty()) {
                    walletListItem.walletResponse.productName
                } else {
                    "${walletListItem.walletResponse.productName} - ${walletListItem.walletResponse.alias}"
                }
            binding.textWalletBalance.text = walletListItem.walletResponse.balance
            binding.textWalletNumber.text = walletListItem.walletResponse.maskedNumber
            binding.radiobtnWallet.isChecked = walletListItem.isChecked
//            binding.imageWallet.setImageUrl(walletListItem.walletResponse.imgUrl)
            binding.root.setOnClickListener {
                clickListener.onClick(walletListItem.walletResponse)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemWalletSingleSelectBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

internal class WalletListener(val clickListener: (walletResponse: WalletResponse) -> Unit) {
    fun onClick(walletResponse: WalletResponse) = clickListener(walletResponse)
}

internal class WalletDiffCallback : DiffUtil.ItemCallback<WalletListItem>() {
    override fun areItemsTheSame(oldItem: WalletListItem, newItem: WalletListItem): Boolean {
        return oldItem.walletResponse.token == newItem.walletResponse.token
    }

    override fun areContentsTheSame(oldItem: WalletListItem, newItem: WalletListItem): Boolean {
        return oldItem == newItem
    }
}

internal data class WalletListItem(
    val walletResponse: WalletResponse,
    var isChecked: Boolean = false
)