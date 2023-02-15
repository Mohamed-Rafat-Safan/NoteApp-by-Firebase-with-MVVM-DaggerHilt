package com.mohamedrafat.firebasewithmvvmapp.data.repository

import com.mohamedrafat.firebasewithmvvmapp.data.model.Note
import com.mohamedrafat.firebasewithmvvmapp.util.Resource

interface NoteRepository {

    suspend fun getNotes(result: (Resource<List<Note>>) -> Unit)

    suspend fun addNote(note: Note, result: (Resource<String>) -> Unit)

    suspend fun updateNote(note: Note, result: (Resource<String>) -> Unit)

    suspend fun deleteNote(note: Note, result: (Resource<String>) -> Unit)



}


