package com.pm.roomapp.fragments.list

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pm.roomapp.R
import com.pm.roomapp.api_note.models.Notes
import com.pm.roomapp.api_note.requests.NotesApi
import com.pm.roomapp.api_note.retrofit.ServiceBuilder
import com.pm.roomapp.utils.Utils.Companion.getToken
import com.pm.roomapp.utils.Utils.Companion.getUserIdInSession
import com.pm.roomapp.utils.Utils.Companion.hideKeyboard
import com.pm.roomapp.utils.Utils.Companion.somethingWentWrong
import com.pm.roomapp.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_notes_list.*
import kotlinx.android.synthetic.main.fragment_notes_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotesListFragment : Fragment() {
    private  var  _view : View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes_list, container, false)
        _view = view

        setHasOptionsMenu(true)

        getAndSetData(view)

        view.btn_add_new_note_from_notes_list.setOnClickListener() {
            findNavController().navigate(R.id.action_notesListFragment_to_addFragment)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.user_logout) {
            logout()
        }

        if(item.itemId == R.id.notes_list_refresh){
            _view?.let { getAndSetData(it) }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getAndSetData(view: View) {

        view.llProgressBarList.bringToFront()
        view.llProgressBarList.visibility = View.VISIBLE


        val adapter = NotesListAdapter(getUserIdInSession())

        val recyclerView = view.recyclerview_notes_list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val request = ServiceBuilder.buildService(NotesApi::class.java)
        val call = request.getNotes(token = "Bearer ${getToken()}")

        call.enqueue(object : Callback<List<Notes>> {
            override fun onResponse(call: Call<List<Notes>>, response: Response<List<Notes>>) {

                llProgressBarList.visibility = View.GONE

                if (response.isSuccessful) {
                    val reports: List<Notes> = response.body()!!
                    adapter.setData(reports)
                } else {
                    if (response.code() == 401) {
                        unauthorized(navigatonHandlder = {
                            findNavController().navigate(R.id.action_notesListFragment_to_userLoginFragment2)
                        })
                    } else {
                        somethingWentWrong()
                    }
                }
            }

            override fun onFailure(call: Call<List<Notes>>, t: Throwable) {
                llProgressBarList.visibility = View.GONE
                somethingWentWrong()
            }
        })
    }

    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val preferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
            preferences.edit().putString("token", null).apply()
            findNavController().navigate(R.id.action_notesListFragment_to_userLoginFragment2)
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.logout))
        builder.setMessage(getString((R.string.logout_question)))
        builder.create().show()
    }
}