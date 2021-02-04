package sg.mirobotic.vrsample.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import sg.mirobotic.vrsample.MainViewModel
import sg.mirobotic.vrsample.adapters.FilesAdapter
import sg.mirobotic.vrsample.adapters.OnItemClickListener
import sg.mirobotic.vrsample.databinding.FragmentHomeBinding
import java.io.File

class HomeFragment : Fragment() {

    private var safebinding: FragmentHomeBinding? = null
    private val binding get() = safebinding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onItemClickListener = object : OnItemClickListener<File> {
            override fun onClick(item: File) {
                val action = HomeFragmentDirections.actionHomeFragmentToView360Fragment(item.absolutePath)
                findNavController().navigate(action)
            }
        }

        binding.rvFiles.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val filesAdapter = FilesAdapter(onItemClickListener)
        binding.rvFiles.adapter = filesAdapter

        mainViewModel.mFiles.observe(viewLifecycleOwner, {
            Log.e("show", "images ${it.size}")
            filesAdapter.setData(it)
        })

    }

}