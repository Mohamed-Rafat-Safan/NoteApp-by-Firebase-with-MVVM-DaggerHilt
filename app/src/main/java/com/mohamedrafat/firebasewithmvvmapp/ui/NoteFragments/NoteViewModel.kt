package com.mohamedrafat.firebasewithmvvmapp.ui.NoteFragments


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedrafat.firebasewithmvvmapp.data.model.Note
import com.mohamedrafat.firebasewithmvvmapp.data.repository.NoteRepository
import com.mohamedrafat.firebasewithmvvmapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    val repository: NoteRepository
) : ViewModel() {

    private val _notes = MutableLiveData<Resource<List<Note>>>()
    val note: LiveData<Resource<List<Note>>> get() = _notes

    private val _addNotes = MutableLiveData<Resource<String>>()
    val addNote: LiveData<Resource<String>> get() = _addNotes

    private val _updateNotes = MutableLiveData<Resource<String>>()
    val updateNote: LiveData<Resource<String>> get() = _updateNotes

    private val _deleteNotes = MutableLiveData<Resource<String>>()
    val deleteNote: LiveData<Resource<String>> get() = _deleteNotes

    fun getNotes() = viewModelScope.launch {
        _notes.value = Resource.Loading

        repository.getNotes {
            _notes.value = it
        }
    }

    fun addNote(note: Note) = viewModelScope.launch {
        _addNotes.value = Resource.Loading

        repository.addNote(note){
            _addNotes.value = it
        }
    }



    fun updateNote(note: Note) = viewModelScope.launch {
        _updateNotes.value = Resource.Loading

        repository.updateNote(note){
            _updateNotes.value = it
        }
    }


    fun deleteNote(note: Note) = viewModelScope.launch {
        _deleteNotes.value = Resource.Loading

        repository.deleteNote(note){
            _deleteNotes.value = it
        }
    }

}

