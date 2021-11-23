package com.pm.roomapp.fragments.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pm.roomapp.R
import com.pm.roomapp.model.Notes
import kotlinx.android.synthetic.main.custom_row.view.*

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {

    private var notesList = emptyList<Notes>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = notesList[position]
        holder.itemView.name_txt.text = currentItem.name
        holder.itemView.title_txt.text = currentItem.title
        holder.itemView.note_txt.text = currentItem.note

        holder.itemView.rowLayout.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)

        }
    }

    fun setData(notes: List<Notes>) {
        this.notesList = notes
        notifyDataSetChanged()
    }

}