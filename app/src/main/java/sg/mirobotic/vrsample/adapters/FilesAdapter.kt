package sg.mirobotic.vrsample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sg.mirobotic.vrsample.databinding.ItemFileBinding
import java.io.File

class FilesAdapter(private val onItemClickListener: OnItemClickListener<File>): RecyclerView.Adapter<FilesAdapter.MyViewHolder>() {

    private var list = ArrayList<File>()

    fun setData(list: ArrayList<File>) {
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: ItemFileBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemFileBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val file =  list[position]

        holder.binding.tvName.text = file.name

        holder.binding.root.setOnClickListener {
            onItemClickListener.onClick(file)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}