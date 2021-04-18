package cu.wilb3r.reign.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cu.wilb3r.reign.R
import cu.wilb3r.reign.databinding.FragmentHomeBinding
import cu.wilb3r.reign.databinding.FragmentWebBinding


class WebFragment : Fragment() {
    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!
    private val args: WebFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWebBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.apply {
            title  = context.getString(R.string.back)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.webView.apply {
            webViewClient = CustomWebViewClient(binding.progress)
            settings.javaScriptEnabled = true
            args.article.story_url?.let {
                loadUrl(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}