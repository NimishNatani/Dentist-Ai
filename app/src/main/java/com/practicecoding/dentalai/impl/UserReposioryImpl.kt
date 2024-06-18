package com.practicecoding.dentalai.impl

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.practicecoding.dentalai.R
import com.practicecoding.dentalai.data.UserRepository
import com.practicecoding.dentalai.data.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UserReposioryImpl @Inject constructor(
    private val authdb: FirebaseAuth,
    private val userdb: CollectionReference
) : UserRepository {
    override suspend fun getUser(): UserModel {
        return withContext(Dispatchers.IO) {
            val userSnapshot = userdb.document(authdb.currentUser?.uid.toString()).get().await()
            val user = UserModel(
                name = userSnapshot.getString("name").toString(),
                phoneNumber = userSnapshot.getString("phoneNumber").toString(),
                imageUri = userSnapshot.getString("imageUri").toString(),
                lowerteeth = userSnapshot.getString("lowerteeth").toString(),
                simileteeth = userSnapshot.getString("simileteeth").toString(),
                upperteeth = userSnapshot.getString("upperteeth").toString(),
            )
            user
        }
    }

    override suspend fun uploadImage(
        uri: Uri,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
        idx: Int
    ) {
        withContext(Dispatchers.IO) {
            try {
                val uid = FirebaseAuth.getInstance().currentUser?.uid ?: throw Exception("User not logged in")
                val storageReference = FirebaseStorage.getInstance().reference
                    .child("images/$uid/${uri.lastPathSegment}")

                storageReference.putFile(uri)
                    .addOnSuccessListener { taskSnapshot ->
                        taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { downloadUrl ->
                            val updateField = when (idx) {
                                R.drawable.girlimage1 -> "simileteeth"
                                R.drawable.girlimage2 -> "upperteeth"
                                else -> "lowerteeth"
                            }

                            userdb.document(uid).update(updateField, downloadUrl.toString())
                                .addOnSuccessListener {
                                    onSuccess()
                                }
                                .addOnFailureListener { exception ->
                                    onFailure(exception)
                                }
                        }?.addOnFailureListener { exception ->
                            onFailure(exception)
                        }
                    }.addOnFailureListener { exception ->
                        onFailure(exception)
                    }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

}