package com.pm.roomapp.fragments.notes

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pm.roomapp.R
import com.pm.roomapp.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_notes.view.*


class NotesFragment : Fragment() {

    private lateinit var mNotesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        //RecyclerView
        val adapter = NotesAdapter()
        val recyclerView = view.recyclerview_notes_list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //NotesViewModel
        mNotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        mNotesViewModel.readAllData.observe(viewLifecycleOwner, Observer { notes ->
            adapter.setData(notes)
        })


        view.btn_add_new_note_from_notes_list.setOnClickListener(){
            findNavController().navigate(R.id.action_notesFragment_to_addFragment)
        }

        //Add delete menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mNotesViewModel.deleteAllNotes()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

}