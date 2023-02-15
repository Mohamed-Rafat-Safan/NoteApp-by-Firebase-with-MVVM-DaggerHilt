package com.mohamedrafat.firebasewithmvvmapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mohamedrafat.firebasewithmvvmapp.data.repository.AuthRepository
import com.mohamedrafat.firebasewithmvvmapp.data.repository.AuthRepositoryImpl
import com.mohamedrafat.firebasewithmvvmapp.data.repository.NoteRepository
import com.mohamedrafat.firebasewithmvvmapp.data.repository.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth, database: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(auth, database)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: FirebaseFirestore): NoteRepository {
        return NoteRepositoryImpl(database)
    }
}