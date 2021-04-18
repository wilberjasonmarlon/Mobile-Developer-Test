package cu.wilb3r.reign.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import cu.wilb3r.reign.databinding.ItemLoadingStateBinding

class LoadingAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingAdapter.LoadingStateViewHolder>() {
    override fun onBindViewHolder(
        holder: LoadingStateViewHolder,
        loadState: LoadState
    ) {
        holder.bindState(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        return LoadingStateViewHolder(
            ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )
    }

    class LoadingStateViewHolder(val binding: ItemLoadingStateBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }


        fun bindState(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvErrorMessage.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.tvErrorMessage.isVisible = loadState !is LoadState.Loading
            binding.btnRetry.isVisible = loadState !is LoadState.Loading

        }

    }

}