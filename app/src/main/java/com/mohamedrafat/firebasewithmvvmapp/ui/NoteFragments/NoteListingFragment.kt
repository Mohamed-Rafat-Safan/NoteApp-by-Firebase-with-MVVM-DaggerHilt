package com.mohamedrafat.firebasewithmvvmapp.ui.NoteFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mohamedrafat.firebasewithmvvmapp.R
import com.mohamedrafat.firebasewithmvvmapp.databinding.FragmentNoteListingBinding
import com.mohamedrafat.firebasewithmvvmapp.ui.AuthFragments.AuthViewModel
import com.mohamedrafat.firebasewithmvvmapp.util.Resource
import com.mohamedrafat.firebasewithmvvmapp.util.hide
import com.mohamedrafat.firebasewithmvvmapp.util.show
import com.mohamedrafat.firebasewithmvvmapp.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

@AndroidEntryPoint
class NoteListingFragment : Fragment() {
    private var _binding: FragmentNoteListingBinding? = null
    private val binding get() = _binding!!

    private lateinit var mNavController: NavController


    val viewModel: NoteViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()

    val adapter by lazy {
        NoteListingAdapter(
            onItemClicked = { pos, note ->
                val action =
                    NoteListingFragmentDirections.actionNoteListingFragmentToNoteDetailFragment2(
                        "view",
                        note
                    )
                mNavController.navigate(action)
            },
            onEditClicked = { pos, note ->
                val action =
                    NoteListingFragmentDirections.actionNoteListingFragmentToNoteDetailFragment2(
                        "edit",
                        note
                    )
                mNavController.navigate(action)
            },
            onDeleteClicked = { pos, note ->
                viewModel.deleteNote(note)
                viewModel.deleteNote.observe(viewLifecycleOwner) { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            binding.progressBar.show()
                        }
                        is Resource.Failure -> {
                            binding.progressBar.hide()
                            toast(resource.error)
                        }
                        is Resource.Success -> {
                            binding.progressBar.hide()
//                            toast(resource.data)

                            Snackbar.make(
                                binding.root,
                                "Successfully Deleted Note",
                                Snackbar.LENGTH_LONG
                            ).apply {
                                setAction("Undo") {
                                    viewModel.addNote(note)
                                }
                                show()
                            }

                        }
                    }
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNoteListingBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter

        binding.button.setOnClickListener {
            val action = NoteListingFragmentDirections.actionNoteListingFragmentToNoteDetailFragment2("create", null)
            mNavController.navigate(action)
        }

        binding.ivLogOut.setOnClickListener {
            authViewModel.logout {
                val action = NoteListingFragmentDirections.actionNoteListingFragmentToLoginFragment()
                mNavController.navigate(action)
            }
        }

        //  get notes from firebase
        viewModel.getNotes()
        observer()

    }


    private fun observer() {
        viewModel.note.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
                    toast(resource.error)
                }
                is Resource.Success -> {
                    binding.progressBar.hide()
                    adapter.updateList(resource.data.toMutableList())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}