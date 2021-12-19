package com.muhammedesadcomert.ecommerceapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.MainActivity
import com.muhammedesadcomert.ecommerceapp.StoreActivity
import com.muhammedesadcomert.ecommerceapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var firestore: FirebaseFirestore = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private var accountType = "Customer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signUp!!.setOnClickListener {
            binding.loading.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(
                binding.username.text.toString(),
                binding.password.text.toString()
            ).addOnCompleteListener { task ->
                // Success
                if (task.isSuccessful) {

                    val postMap = hashMapOf<String, Any>()

                    if (binding.checkBox!!.isChecked) {
                        accountType = "Store"
                        postMap["storeName"] = binding.storeName!!.text.toString()
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

        binding.signIn!!.setOnClickListener {
            binding.loading.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        activityStarter()
                    }
                }.addOnFailureListener {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
        }

        binding.checkBox!!.setOnCheckedChangeListener { _, isSelected ->
            if (isSelected) {
                binding.storeName!!.visibility = View.VISIBLE
            } else {
                binding.storeName!!.visibility = View.GONE
            }
        }

        binding.username.doAfterTextChanged {
            if (isEmailValid(binding.username.text.toString()) && isPasswordValid(binding.password.text.toString())) {
                binding.signIn!!.isEnabled = true
                binding.signUp!!.isEnabled = true
            } else {
                binding.signIn!!.isEnabled = false
                binding.signUp!!.isEnabled = false
            }
        }

        binding.password.doAfterTextChanged {
            if (isEmailValid(binding.username.text.toString()) && isPasswordValid(binding.password.text.toString())) {
                binding.signIn!!.isEnabled = true
                binding.signUp!!.isEnabled = true
            } else {
                binding.signIn!!.isEnabled = false
                binding.signUp!!.isEnabled = false
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (auth.currentUser != null) {
            activityStarter()
        }
    }

    private fun activityStarter() {
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

    private fun isEmailValid(eMail: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(eMail).matches()

    private fun isPasswordValid(password: String) = password.length > 5
}