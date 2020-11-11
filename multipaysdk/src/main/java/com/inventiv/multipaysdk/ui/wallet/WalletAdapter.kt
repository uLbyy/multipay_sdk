package com.inventiv.multipaysdk.ui.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.R
import com.inventiv.multipaysdk.data.model.response.WalletResponse
import com.inventiv.multipaysdk.databinding.ItemWalletSingleSelectBinding
import com.inventiv.multipaysdk.databinding.LayoutListEmptyBinding

private const val ITEM_VIEW_TYPE_EMPTY = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

internal class WalletAdapter(private val clickListener: WalletListener) :
    ListAdapter<WalletListItem, RecyclerView.ViewHolder>(WalletDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_EMPTY -> EmptyViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val walletItem = getItem(position)
                holder.bind(walletItem, clickListener)
            }
            is EmptyViewHolder -> {
                holder.bind(MultiPaySdk.getComponent().getString(R.string.wallet_list_no_wallet))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount == 0) {
            ITEM_VIEW_TYPE_EMPTY
        } else {
            ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(private val binding: ItemWalletSingleSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(walletListItem: WalletListItem, clickListener: WalletListener) {
            binding.textWalletAlias.text = walletListItem.walletResponse.alias
            binding.textWalletBalance.text = walletListItem.walletResponse.balance
            binding.textWalletNumber.text = walletListItem.walletResponse.maskedNumber
            binding.radiobtnWallet.isChecked = walletListItem.isChecked
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

    class EmptyViewHolder private constructor(private val binding: LayoutListEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(emptyListMessage: String) {
            binding.textCommonEmptyList.text = emptyListMessage
        }

        companion object {
            fun from(parent: ViewGroup): EmptyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutListEmptyBinding.inflate(layoutInflater, parent, false)
                return EmptyViewHolder(binding)
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