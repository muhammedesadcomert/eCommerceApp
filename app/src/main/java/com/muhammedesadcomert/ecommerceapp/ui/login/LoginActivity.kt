package com.muhammedesadcomert.ecommerceapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.ActivityLoginBinding
import com.muhammedesadcomert.ecommerceapp.ui.MainActivity
import com.muhammedesadcomert.ecommerceapp.ui.store.StoreActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.set

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var firestore: FirebaseFirestore = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private var accountType = "Customer"
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signInSignUp = binding.signInSignUp
        val checkbox = binding.checkbox
        val username = binding.username
        val password = binding.password

        binding.buttonSignIn.setOnClickListener {
            signInSignUp.text = getString(R.string.sign_in)
            checkbox.visibility = View.GONE
            binding.storeNameInput.visibility = View.GONE
        }

        binding.buttonSignUp.setOnClickListener {
            signInSignUp.text = getString(R.string.sign_up)
            checkbox.visibility = View.VISIBLE
        }

        checkbox.setOnCheckedChangeListener { _, isSelected ->
            if (isSelected) {
                binding.storeNameInput.visibility = View.VISIBLE
            } else {
                binding.storeNameInput.visibility = View.GONE
            }
        }

        signInSignUp.setOnClickListener {
            if (binding.buttonSignIn.isChecked) {
                signIn()
            } else {
                signUp()
            }
        }

        username.doAfterTextChanged {
            if (isEmailValid(username.text.toString()) && isPasswordValid(password.text.toString())) {
                signInSignUp.apply {
                    isEnabled = true
                    background.setTint(getColor(R.color.primaryColor))
                }
            } else {
                signInSignUp.apply {
                    isEnabled = false
                    background.setTint(getColor(R.color.buttonDisabled))
                }
            }
        }

        password.doAfterTextChanged {
            if (isEmailValid(username.text.toString()) && isPasswordValid(password.text.toString())) {
                signInSignUp.apply {
                    isEnabled = true
                    background.setTint(getColor(R.color.primaryColor))
                }
            } else {
                signInSignUp.apply {
                    isEnabled = false
                    background.setTint(getColor(R.color.buttonDisabled))
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (Firebase.auth.currentUser != null) {
            activityStarter()
        }
    }

    private fun activityStarter() {
        auth = Firebase.auth
        firestore.collection("Users").document(auth.uid!!).get()
            .addOnSuccessListener {
                if (it.get("accountType") == "Store") {
                    startActivity(Intent(applicationContext, StoreActivity::class.java))
                } else if (it.get("accountType") == "Customer") {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                }
                binding.loading.visibility = View.GONE
                finish()
            }.addOnFailureListener {
                Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }

    private fun signIn() {
//        binding.loading.visibility = View.VISIBLE
//        auth = Firebase.auth
//
//        auth.signInWithEmailAndPassword(
//            binding.username.text.toString(),
//            binding.password.text.toString()
//        )
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    activityStarter()
//                }
//            }.addOnFailureListener {
//                binding.loading.visibility = View.GONE
//                Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
//            }

        binding.loading.visibility = View.VISIBLE

        loginViewModel.signIn(
            binding.username.text.toString(),
            binding.password.text.toString()
        )


//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    activityStarter()
//                }
//            }.addOnFailureListener {
//                binding.loading.visibility = View.GONE
//                Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
//            }
    }

    private fun signUp() {
        binding.loading.visibility = View.VISIBLE
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(
            binding.username.text.toString(),
            binding.password.text.toString()
        ).addOnCompleteListener { task ->
            // Success
            if (task.isSuccessful) {

                val postMap = hashMapOf<String, Any>()

                if (binding.checkbox.isChecked) {
                    accountType = "Store"
                    postMap["storeName"] = binding.storeName.text.toString()
                }

                val uuid: String = auth.uid.toString()

                postMap["accountType"] = accountType

                firestore.collection("Users").document(uuid).set(postMap)
                    .addOnSuccessListener {
                        activityStarter()
                    }.addOnFailureListener {
                        binding.loading.visibility = View.GONE
                        Toast.makeText(
                            applicationContext,
                            it.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }   // Fail
            .addOnFailureListener {
                binding.loading.visibility = View.GONE
                Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }
    }

    private fun isEmailValid(eMail: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(eMail).matches()

    private fun isPasswordValid(password: String) = password.length > 5
}