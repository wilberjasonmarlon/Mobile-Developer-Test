package cu.wilb3r.reign.ui.adapter

import android.annotation.SuppressLint
import android.view.*
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import cu.wilb3r.reign.data.db.models.DBArticle
import cu.wilb3r.reign.databinding.ItemArticleBinding
import cu.wilb3r.reign.utils.DiffUtilCallBack
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class ArticlePageAdapter :
    PagingDataAdapter<DBArticle, ArticlePageAdapter.ViewHolder>(DiffUtilCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    private var onItemClickListener: ((View, DBArticle, Int) -> Unit)? = null
    private var onItemSwipeListener: ((View, DBArticle, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (View, DBArticle, Int) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnSwipeListener(listener: (View, DBArticle, Int) -> Unit) {
        onItemSwipeListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.apply {
                bind(item)
                binding.root.apply {
                    setOnClickListener { view ->
                        onItemClickListener?.invoke(view, item, position)
                    }
                    setOnTouchListener(object: View.OnTouchListener{
                        @SuppressLint("ClickableViewAccessibility")
                        override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
                            onItemSwipeListener?.invoke(view!!, item, position)
                            return false
                        }
                    })
                }
            }
        }
    }

    class ViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(item: DBArticle) {
            binding.apply {
                val s = "${item.author} - " + PrettyTime(Locale.ENGLISH)
                    .formatDuration(item.created_at)
                title.text = item.story_title ?: item.title
                timeAuthor.text = s
                articleId.text = item.objectID
            }
        }
    }

}