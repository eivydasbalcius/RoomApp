package com.pm.roomapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pm.roomapp.R
import com.pm.roomapp.model.Notes
import com.pm.roomapp.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mNotesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mNotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        val name = addName_et.text.toString()
        val title = addTitle_et.text.toString()
        val note = addNote_et.text.toString()

        //Create Note object
        val notes = Notes(0, name, title, note)
        //Add data to Database
        mNotesViewModel.addNote(notes)
        Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_LONG).show()
        //Navigate back from Add fragment to Notes fragment
        findNavController().navigate(R.id.action_addFragment_to_notesFragment)
    }
}