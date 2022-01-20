package com.pm.roomapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pm.roomapp.R
import com.pm.roomapp.api_note.dto.NotesDto
import com.pm.roomapp.api_note.requests.NotesApi
import com.pm.roomapp.api_note.retrofit.ServiceBuilder
import com.pm.roomapp.utils.Utils.Companion.getToken
import com.pm.roomapp.utils.Utils.Companion.hideKeyboard
import com.pm.roomapp.utils.Utils.Companion.somethingWentWrong
import com.pm.roomapp.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_update_note.*
import kotlinx.android.synthetic.main.fragment_update_note.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateNoteFragment : Fragment() {
    private val args by navArgs<UpdateNoteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_note, container, false)

        setHasOptionsMenu(true)

        view.update_notes_title.setText(args.currentNote.title)
        view.update_notes_description.setText(args.currentNote.description)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_delete_report) {
            deleteReport()
        }

        if (item.itemId == R.id.menu_update_report) {
            updateReport()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateReport() {
        if (TextUtils.isEmpty(update_notes_title.text.toString()) || TextUtils.isEmpty(
                update_notes_description.text.toString()
            )
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_description),
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            val request = ServiceBuilder.buildService(NotesApi::class.java)
            val call = request.updateNote(
                token = "Bearer ${getToken()}",
                id = args.currentNote.id,
                title = update_notes_title.text.toString(),
                description = update_notes_description.text.toString()
            )

            call.enqueue(object : Callback<NotesDto> {
                override fun onResponse(call: Call<NotesDto>, response: Response<NotesDto>) {
                    if (response.isSuccessful) {
                        val report: NotesDto = response.body()!!

                        if (report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_updated_report),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_updateNoteFragment_to_notesListFragment)
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
                                findNavController().navigate(R.id.action_updateNoteFragment_to_userLoginFragment2)
                            })
                        } else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<NotesDto>, t: Throwable) {
                    somethingWentWrong()
                }
            })
        }
    }

    private fun deleteReport() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->

            val request = ServiceBuilder.buildService(NotesApi::class.java)
            val call = request.deleteNote(
                token = "Bearer ${getToken()}",
                id = args.currentNote.id
            )

            call.enqueue(object : Callback<NotesDto> {
                override fun onResponse(call: Call<NotesDto>, response: Response<NotesDto>) {
                    if (response.isSuccessful) {
                        val report: NotesDto = response.body()!!

                        if(report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_deleted_report),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_updateNoteFragment_to_notesListFragment)
                        }
                        else {
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

                        if(response.code() == 401){
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_updateNoteFragment_to_userLoginFragment2)
                            })
                        }
                        else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<NotesDto>, t: Throwable) {
                    somethingWentWrong()
                }
            })
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.delete_report))
        builder.setMessage(getString(R.string.question_delete_report))
        builder.create().show()
    }
}