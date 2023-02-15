package com.mohamedrafat.firebasewithmvvmapp.ui.NoteFragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mohamedrafat.firebasewithmvvmapp.R
import com.mohamedrafat.firebasewithmvvmapp.data.model.Note
import com.mohamedrafat.firebasewithmvvmapp.databinding.FragmentNoteDetailBinding
import com.mohamedrafat.firebasewithmvvmapp.databinding.FragmentNoteListingBinding
import com.mohamedrafat.firebasewithmvvmapp.util.Resource
import com.mohamedrafat.firebasewithmvvmapp.util.hide
import com.mohamedrafat.firebasewithmvvmapp.util.show
import com.mohamedrafat.firebasewithmvvmapp.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var mNavController: NavController

    val viewModel: NoteViewModel by viewModels()

    private var isEdit = false

    private val args:NoteDetailFragmentArgs by navArgs()
    private var objNote:Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ايه اي انت عاوز تنفذه create or update or view
        updateUI()

        binding.btnSave.setOnClickListener {
            if(isEdit){
                updateNote()
            }else{
                createNote()
            }
        }
    }


    private fun updateNote(){
        if(validation()){
            viewModel.updateNote(
                Note(objNote?.id!!, "note1",binding.etDesc.text.toString(), Date() )
            )

            viewModel.updateNote.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.show()
                        binding.btnSave.text = ""
                    }
                    is Resource.Failure -> {
                        binding.progressBar.hide()
                        toast(resource.error)
                        binding.btnSave.text = "Update"
                    }
                    is Resource.Success -> {
                        binding.progressBar.hide()
                        toast(resource.data)
                        binding.btnSave.text = "Update"
                    }
                }
            }

        }
    }

    private fun createNote(){
        if(validation()){
            viewModel.addNote(
                Note("1", "note1",binding.etDesc.text.toString(), Date() )
            )

            viewModel.addNote.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.show()
                        binding.btnSave.text = ""
                    }
                    is Resource.Failure -> {
                        binding.progressBar.hide()
                        toast(resource.error)
                        binding.btnSave.text = "Save"
                    }
                    is Resource.Success -> {
                        binding.progressBar.hide()
                        toast(resource.data)
                        binding.btnSave.text = "Save"
                    }
                }
            }

        }


    }

    private fun updateUI(){
        val type = args.type
        objNote = args.note
        type?.let {
            when(it){
                "view"->{
                    binding.etDesc.setText(objNote?.description)
                    isEdit = false
                    binding.etDesc.isEnabled = false
                    binding.etDesc.setTextColor(Color.BLACK)
                    binding.btnSave.hide()
                }
                "create"->{
                    isEdit = false
                    binding.btnSave.text = "Create"
                }
                "edit"->{
                    binding.etDesc.setText(objNote?.description)
                    isEdit = true
                    binding.btnSave.text = "Update"
                }
            }
        }

    }


    private fun validation(): Boolean {
        var isValid = true
//        if (binding.title.text.toString().isNullOrEmpty()) {
//            isValid = false
//            toast(getString(R.string.error_title))
//        }
        if (binding.etDesc.text.toString().isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.error_description))
        }
        return isValid
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}