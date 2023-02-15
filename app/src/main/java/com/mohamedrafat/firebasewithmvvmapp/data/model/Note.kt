package com.mohamedrafat.firebasewithmvvmapp.data.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Note(
    var id: String = "",

    val title: String = "",
    val description: String = "",
    @ServerTimestamp
    val date: Date = Date()
):Parcelable

//var user_id: String = "",
//val tags: MutableList<String> = arrayListOf(),
//val images: List<String> = arrayListOf(),
//@ServerTimestamp
//val date: Date = Date()



// المشكله الي ديما بتحصل مع ال firebase انه اي variable لازم ياخد default value
//   اما تعمل constructor وديهم قيم فيه او تيجي داخل ال  constructor بتاع ال class وتديهم قيم زي ما انا عامل كده