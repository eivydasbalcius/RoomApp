package com.pm.roomapp.fragments.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //NotesViewModel
        mNotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        mNotesViewModel.readAllData.observe(viewLifecycleOwner, Observer { notes ->
            adapter.setData(notes)
        })


        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addFragment)
        }

        return view
    }

}