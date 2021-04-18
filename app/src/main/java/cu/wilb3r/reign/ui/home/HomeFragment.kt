package cu.wilb3r.reign.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cu.wilb3r.reign.data.db.models.DBArticle
import cu.wilb3r.reign.databinding.FragmentHomeBinding
import cu.wilb3r.reign.ui.adapter.*
import cu.wilb3r.reign.ui.adapter.SwipeToDeleteCallback
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(){
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val vm: HomeViewModel by inject()
    private val articlesAdapter: ArticlePageAdapter by inject()

    private lateinit var articleToDelete: DBArticle
    private var loadJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRecyclerView()

        suspend {
            articlesAdapter.submitData(PagingData.from(DBArticle.LOADING_LIST))
        }
        loadData()
    }

    private fun moveToTop() {
        binding.recycler.scrollToPosition(0)
    }

    private fun loadData() {
        loadJob?.cancel()
        loadJob = lifecycleScope.launch {
            vm.getArticles().collectLatest { pagingData ->
                binding.refresh.isRefreshing = false
                articlesAdapter.submitData(pagingData)
            }
    }
    }

    private fun removeArticle(item:DBArticle) = vm.delectArticle(item)

    private fun initRecyclerView() {
        binding.recycler.layoutManager = LinearLayoutManager(requireContext()).apply { reverseLayout = false }


        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeArticle(articleToDelete)
                loadData()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recycler)
    }



    private fun initAdapter() {
        binding.refresh.setOnRefreshListener {
            loadData()
        }

        binding.recycler.adapter = articlesAdapter.withLoadStateHeaderAndFooter(
            header = LoadingAdapter { articlesAdapter.retry() },
            footer = LoadingAdapter { articlesAdapter.retry() }
        )
        articlesAdapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && articlesAdapter.itemCount == 0
            showEmptyList(isListEmpty)

            // Only show the list if refresh succeeds.
             //binding.recycler.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
             binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            setUpClickListener(loadState.source.refresh !is LoadState.Loading)
            // Show the retry state if initial load or refresh fails.
             binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.recycler.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpClickListener(active: Boolean){
        if(active) {
            articlesAdapter.setOnItemClickListener { _: View, article: DBArticle, position: Int ->
                loadJob?.cancel()
                val direction = HomeFragmentDirections.actionHomeFragmentToWebFragment(article)
                findNavController().navigate(direction)
            }
            articlesAdapter.setOnSwipeListener { _, dbArticle, i ->
                loadJob?.cancel()
                articleToDelete = dbArticle
            }
        } else {
            articlesAdapter.setOnItemClickListener { _: View, article: DBArticle, position: Int ->
                null
            }
            articlesAdapter.setOnSwipeListener { _, dbArticle, i ->
                null
            }
        }

    }

}