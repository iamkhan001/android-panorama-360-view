package sg.mirobotic.vrsample

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import sg.mirobotic.vrsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

        mainViewModel.mStatus.observe(this, {
            if (it) {
                binding.progressBar.visibility = View.GONE
                return@observe
            }
            binding.progressBar.visibility = View.VISIBLE
        })

        mainViewModel.init(context)

    }

}