package com.mohamedrafat.firebasewithmvvmapp.data.repository

import android.provider.ContactsContract
import com.google.firebase.firestore.FirebaseFirestore
import com.mohamedrafat.firebasewithmvvmapp.data.model.Note
import com.mohamedrafat.firebasewithmvvmapp.util.Constants.Companion.NOTE_TABLE
import com.mohamedrafat.firebasewithmvvmapp.util.Resource


class NoteRepositoryImpl(
    val database: FirebaseFirestore
) : NoteRepository {

    override suspend fun getNotes(result: (Resource<List<Note>>) -> Unit) {
        // we will get data from firebase
        database.collection(NOTE_TABLE).addSnapshotListener { value, error ->
            if(error != null){
                result.invoke(Resource.Failure(error.message))
                return@addSnapshotListener
            }

            val notes = arrayListOf<Note>()
            var note: Note
            value!!.documents.forEach { document ->
                note = document.toObject(Note::class.java)!!
                notes.add(note)
            }
            result.invoke(Resource.Success(notes))
        }


//        database.collection(NOTE_TABLE).get().addOnSuccessListener {
//            val notes = arrayListOf<Note>()
//            var note: Note
//            for (document in it) {
//                note = document.toObject(Note::class.java)
//                notes.add(note)
//            }
//            result.invoke(Resource.Success(notes))
//        }.addOnFailureListener {
//            // call lambda function and send Failure
//            result.invoke(Resource.Failure(it.message))
//        }
    }


    override suspend fun addNote(note: Note, result: (Resource<String>) -> Unit) {
        val document = database.collection(NOTE_TABLE).document()

        note.id = document.id

        document.set(note).addOnSuccessListener {
            // call lambda function and send Success
            result.invoke(Resource.Success("Note Is Created Successfully"))
        }.addOnFailureListener {
            // call lambda function and send Failure
            result.invoke(Resource.Failure(it.message))
        }
    }

    override suspend fun updateNote(note: Note, result: (Resource<String>) -> Unit) {
        val document = database.collection(NOTE_TABLE).document(note.id)

        document.set(note).addOnSuccessListener {
            // call lambda function and send Success
            result.invoke(Resource.Success("Note Is Update Successfully"))
        }.addOnFailureListener {
            // call lambda function and send Failure
            result.invoke(Resource.Failure(it.message))
        }
    }

    override suspend fun deleteNote(note: Note, result: (Resource<String>) -> Unit) {
        val document = database.collection(NOTE_TABLE).document(note.id)

        document.delete().addOnSuccessListener {
            // call lambda function and send Success
            result.invoke(Resource.Success("Note Is Deleted Successfully"))
        }.addOnFailureListener {
            // call lambda function and send Failure
            result.invoke(Resource.Failure(it.message))
        }
    }

}