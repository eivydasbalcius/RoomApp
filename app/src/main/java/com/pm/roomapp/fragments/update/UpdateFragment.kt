package com.pm.roomapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pm.roomapp.R
import com.pm.roomapp.model.Notes
import com.pm.roomapp.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mNotesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        view.updateName_et.setText(args.currentNote.name)
        view.updateTitle_et.setText(args.currentNote.title)
        view.updateNote_et.setText(args.currentNote.note)

        view.update_btn.setOnClickListener {
            updateItem()
        }

        //Add menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val name = updateName_et.text.toString()
        val title = updateTitle_et.text.toString()
        val note = updateNote_et.text.toString()

        if(inputCheck(name,title, note)) {
            //Create updated Notes object
            val updatedNotes = Notes(args.currentNote.id, name, title, note)
            //Update current Notes
            mNotesViewModel.updateNotes(updatedNotes)
            Toast.makeText(requireContext(), "Updated successfully", Toast.LENGTH_SHORT).show()
            //Navigate back to Notes list fragment
            findNavController().navigate(R.id.action_updateFragment_to_notesFragment)
        }else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(name: String, title: String, note: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(title) && TextUtils.isEmpty(note))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _, _ ->
            mNotesViewModel.deleteNote(args.currentNote)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentNote.title}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_notesFragment)
        }
        builder.setNegativeButton("No"){ _, _ -> }
        builder.setTitle("Delete ${args.currentNote.title}?")
        builder.setMessage("Are you sure you want to delete ${args.currentNote.title}?")
        builder.create().show()
    }

}