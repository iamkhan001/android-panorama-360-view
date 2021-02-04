package sg.mirobotic.vrsample

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.*


class MainViewModel: ViewModel() {

    companion object {
        private val TAG = "MVM"
    }

    private lateinit var context: Context
    val mStatus = MutableLiveData<Boolean>()
    val mFiles = MutableLiveData<ArrayList<File>>()


    fun init(context: Context) {
        this.context = context
        viewModelScope.launch {
            mStatus.postValue(false)
            copyAssets()
        }

    }

    private val imageFolder = "360images/"

    private fun copyAssets() {
        val assetManager = context.assets
        try {
            val files = assetManager.list(imageFolder)

            Log.e(TAG, "Total files: ${files?.size}")
            for (filename in files!!) {
                try {
                    Log.d(TAG, "open $filename")
                    val outDir = context.getExternalFilesDir(imageFolder)!!.absolutePath
                    val outFolder = File(outDir)
                    Log.d(TAG, "copy: ${outFolder.absolutePath}")

                    if (!outFolder.exists()) {
                        outFolder.mkdir()
                    }
                    val `in` = assetManager.open("$imageFolder$filename")

                    val outFile = File(outDir, filename)

                    if (outFile.exists()) {
                        Log.d(TAG, "exists $filename")
                        continue
                    }

                    val out = FileOutputStream(outFile)
                    copyFile(`in`, out)
                    `in`.close()
                    out.flush()
                    out.close()
                } catch (e: IOException) {
                    Log.e("tag", "Failed to copy asset file: $filename", e)
                }
            }
        } catch (e: IOException) {
            Log.e("tag", "Failed to get asset file list.", e)
        }

        loadImages()

        mStatus.postValue(true)
    }

    @Throws(IOException::class)
    private fun copyFile(`in`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (`in`.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
    }

    private val imageFormats = arrayOf(".jpeg")

    private fun loadImages() {

        viewModelScope.launch {

            val list = ArrayList<File>()

            val images = context.getExternalFilesDir(imageFolder)!!.absolutePath
            val directory = File(images)
            val files: Array<File>? = directory.listFiles()

            files?.let {
                Log.d("Files", "Size: " + it.size)
                for (i in it.indices) {
                    val file = it[i]
                    val ext = getExtension(file.absolutePath)
                    Log.d("Files", "FileName:" + file.name + "ext: $ext")
                    if (ext in imageFormats) {
                        list.add(file)
                    }
                }
            }
            Log.d("Files", "360 images: " + list.size)

            mFiles.postValue(list)
        }

    }

    private fun getExtension(path: String): String = try {
        path.substring(path.lastIndexOf(".")).toLowerCase()
    }catch (e: Exception) {
        ""
    }


}