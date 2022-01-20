package com.pm.roomapp.fragments.add

import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pm.roomapp.R
import com.pm.roomapp.api_note.dto.NotesDto
import com.pm.roomapp.api_note.requests.NotesApi
import com.pm.roomapp.api_note.retrofit.ServiceBuilder
import com.pm.roomapp.model.Notes
import com.pm.roomapp.utils.Utils.Companion.getToken
import com.pm.roomapp.utils.Utils.Companion.getUserIdInSession
import com.pm.roomapp.utils.Utils.Companion.hideKeyboard
import com.pm.roomapp.utils.Utils.Companion.somethingWentWrong
import com.pm.roomapp.utils.Utils.Companion.unauthorized
import com.pm.roomapp.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_add) {
            addReport()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addReport() {
        if (TextUtils.isEmpty(add_notes_title.text.toString()) || TextUtils.isEmpty(
                add_notes_description.text.toString()
            )
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_description),
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            llProgressBar.bringToFront()
            llProgressBar.visibility = View.VISIBLE

            val request = ServiceBuilder.buildService(NotesApi::class.java)
            val call = request.createNote(
                token = "Bearer ${getToken()}",
                users_id = getUserIdInSession(),
                description = add_notes_description.text.toString(),
                title = add_notes_title.text.toString()
            )

            call.enqueue(object : Callback<NotesDto> {
                override fun onResponse(call: Call<NotesDto>, response: Response<NotesDto>) {
                    llProgressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val report: NotesDto = response.body()!!

                        if (report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_added_new_note),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_addFragment_to_notesListFragment)
                        } else {
                            Toast.makeText(
                                requireContext(), getString(
                                    resources.getIdentifier(
                                        report.message, "string",
                                        context?.packageName
                                    )
                                ), Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        if (response.code() == 401) {
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_addFragment_to_userLoginFragment2)
                            })
                        } else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<NotesDto>, t: Throwable) {
                    llProgressBar.visibility = View.GONE
                    somethingWentWrong()
                }
            })
        }
    }
}