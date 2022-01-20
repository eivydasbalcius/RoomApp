package com.pm.roomapp.fragments.list

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pm.roomapp.R
import com.pm.roomapp.api_note.models.Notes
import kotlinx.android.synthetic.main.custom_row.view.*

class NotesListAdapter(userIdInSession: String?) : RecyclerView.Adapter<NotesListAdapter.MyViewHolder>() {

    private var notesList = emptyList<Notes>()
    private  val _userIdInSession = userIdInSession
    private  var  _ctx : Context? = null

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _ctx = parent.context

        return NotesListAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = notesList[position]
        holder.itemView.title_txt .text = currentItem.title
        holder.itemView.note_txt .text = currentItem.description
        holder.itemView.name_txt.text = currentItem.user_name

        if(position%2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#d6d4e0"))
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#b8a9c9"))
        }

        holder.itemView.rowLayout.setOnClickListener {
            if(_userIdInSession == currentItem.users_id.toString()){
                val action =
                    NotesListFragmentDirections.actionNotesListFragmentToUpdateNoteFragment(currentItem)
                holder.itemView.findNavController().navigate(action)
            }
            else {
                Toast.makeText(_ctx,R.string.ony_edit_your_notes, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setData(reportsList: List<Notes>){
        this.notesList = reportsList
        notifyDataSetChanged()
    }
}