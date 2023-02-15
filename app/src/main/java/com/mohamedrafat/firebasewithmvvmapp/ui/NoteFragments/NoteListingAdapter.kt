package com.mohamedrafat.firebasewithmvvmapp.ui.NoteFragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohamedrafat.firebasewithmvvmapp.data.model.Note
import com.mohamedrafat.firebasewithmvvmapp.databinding.ItemNoteLayoutBinding
import java.text.SimpleDateFormat

class NoteListingAdapter(
    val onItemClicked: (Int, Note) -> Unit,
    val onEditClicked: (Int, Note) -> Unit,
    val onDeleteClicked: (Int, Note) -> Unit
) : RecyclerView.Adapter<NoteListingAdapter.MyViewHolder>() {

    val sdf = SimpleDateFormat("dd MMM yyyy")
     var list: MutableList<Note> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemNoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun updateList(list: MutableList<Note>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemChanged(position)
    }


    inner class MyViewHolder(val binding: ItemNoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
//            binding.title.setText(item.title)
//            binding.date.setText(sdf.format(item.date))
//            binding.tags.apply {
//                if (item.tags.isNullOrEmpty()){
//                    com.mohamedrafat.firebasewithmvvmapp.util.hide()
//                }else {
//                    removeAllViews()
//                    if (item.tags.size > 2) {
//                        item.tags.subList(0, 2).forEach { tag -> com.mohamedrafat.firebasewithmvvmapp.util.addChip(tag) }
//                        com.mohamedrafat.firebasewithmvvmapp.util.addChip("+${item.tags.size - 2}")
//                    } else {
//                        item.tags.forEach { tag -> com.mohamedrafat.firebasewithmvvmapp.util.addChip(tag) }
//                    }
//                }
//            }
//            binding.desc.apply {
//                if (item.description.length > 120){
//                    text = "${item.description.substring(0,120)}..."
//                }else{
//                    text = item.description
//                }
//            }
//            binding.itemLayout.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }


            binding.date.text = sdf.format(item.date)
            binding.desc.text = item.description

            binding.itemLayout.setOnClickListener { onItemClicked.invoke(bindingAdapterPosition, item) }
            binding.ivEdit.setOnClickListener { onEditClicked.invoke(bindingAdapterPosition, item) }
            binding.ivDelete.setOnClickListener { onDeleteClicked.invoke(bindingAdapterPosition, item) }


        }
    }
}