package sg.mirobotic.vrsample.fragments

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sg.mirobotic.vrsample.databinding.FragmentView360Binding


class View360Fragment : Fragment() {

    private var safebinding: FragmentView360Binding? = null
    private val binding get() = safebinding!!

    private lateinit var mContext: Context


    companion object {
        private const val TAG = "View360"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentView360Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val path = requireArguments().getString("path") ?: return

        val bitmap = BitmapFactory.decodeFile(path)

        binding.panorama.setPanoramaBitmap(bitmap)

    }

}