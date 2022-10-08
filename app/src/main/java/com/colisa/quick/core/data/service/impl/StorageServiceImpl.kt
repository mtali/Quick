package com.colisa.quick.core.data.service.impl

import com.colisa.quick.core.data.models.Task
import com.colisa.quick.core.data.service.AccountService
import com.colisa.quick.core.data.service.StorageService
import com.colisa.quick.core.data.service.utils.trace
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : StorageService {

    override val tasks: Flow<List<Task>>
        get() = auth.currentUser.flatMapLatest { user ->
            currentCollection(user.id).snapshots().map { snapshot -> snapshot.toObjects() }
        }

    override suspend fun getTask(taskId: String): Task? =
        currentCollection(auth.currentUserId).document(taskId).get().await().toObject()

    override suspend fun saveTask(task: Task): String =
        trace(SAVE_TASK_TRACE) { currentCollection(auth.currentUserId).add(task).await().id }

    override suspend fun updateTask(task: Task): Unit =
        trace(UPDATE_TASK_TRACE) {
            currentCollection(auth.currentUserId).document(task.id).set(task).await()
        }

    override suspend fun deleteTask(taskId: String) {
        currentCollection(auth.currentUserId).document(taskId).delete().await()
    }

    // TODO: It is not recommended to delete on the client
    override suspend fun deleteAllUserTasks(userId: String) {
        val matchingTasks = currentCollection(userId).get().await()
        matchingTasks.map {
            it.reference.delete().asDeferred()
        }.awaitAll()
    }

    private fun currentCollection(uid: String): CollectionReference =
        firestore.collection(USER_COLLECTION).document(uid).collection(TASK_COLLECTION)


    companion object {
        private const val USER_COLLECTION = "users"
        private const val TASK_COLLECTION = "tasks"
        private const val SAVE_TASK_TRACE = "saveTask"
        private const val UPDATE_TASK_TRACE = "updateTask"
    }

}