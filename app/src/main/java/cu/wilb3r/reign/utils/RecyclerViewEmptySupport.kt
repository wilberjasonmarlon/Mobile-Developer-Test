package cu.wilb3r.reign.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewEmptySupport : RecyclerView {
    private lateinit var emptyView: View

    private val emptyObserver: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            val adapter = adapter
            if (adapter != null) {
                if (adapter.itemCount == 0) {
                    emptyView.visibility = View.VISIBLE
                    this@RecyclerViewEmptySupport.visibility = View.GONE
                } else {
                    emptyView.visibility = View.GONE
                    this@RecyclerViewEmptySupport.visibility = View.VISIBLE
                }
            }
        }
    }

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(emptyObserver)
        emptyObserver.onChanged()
    }

    fun setEmptyView(emptyView: View) {
        this.emptyView = emptyView
    }

}