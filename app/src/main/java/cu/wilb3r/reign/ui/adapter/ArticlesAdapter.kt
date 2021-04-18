package cu.wilb3r.reign.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cu.wilb3r.reign.data.db.models.DBArticle
import cu.wilb3r.reign.databinding.ItemArticleBinding
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator

class ArticlesAdapter (): RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemArticleBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SimpleDateFormat")
        fun bind(item: DBArticle) {
            binding.apply {
                val s = "${item.author} - " + PrettyTime(Locale.ENGLISH)
                        .formatUnrounded(item.created_at)
                title.text = item.story_title ?: item.title
                timeAuthor.text = s
            }
        }
        }

    private val diffCallback = object : DiffUtil.ItemCallback<DBArticle>() {
        override fun areItemsTheSame(oldItem: DBArticle, newItem: DBArticle): Boolean {
            return oldItem.objectID == newItem.objectID
        }

        override fun areContentsTheSame(oldItem: DBArticle, newItem: DBArticle): Boolean {
            return oldItem.objectID == newItem.objectID && oldItem.created_at_i == newItem.created_at_i
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun getItem(pos: Int): DBArticle = differ.currentList[pos]

    fun submitList(items: List<DBArticle>?) : List<DBArticle>{
        differ.submitList(items)
        return differ.currentList
    }

    private var onItemClickListener: ((View, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (View, Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.apply {
            bind(item)
            binding.root.apply {
                setOnClickListener { view ->
                    onItemClickListener?.invoke(view, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}