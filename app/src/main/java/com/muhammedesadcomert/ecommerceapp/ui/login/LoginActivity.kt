package com.muhammedesadcomert.ecommerceapp.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muhammedesadcomert.ecommerceapp.MainActivity
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.StoreActivity
import com.muhammedesadcomert.ecommerceapp.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var firestore: FirebaseFirestore = Firebase.firestore
    private var accountType = "Customer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val username = binding.username
        val password = binding.password
        val loading = binding.loading
        val checkBox = binding.checkBox
        val signIn = binding.signIn!!
        val signUp = binding.signUp!!

        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            signUp.isEnabled = loginState.isDataValid
            signIn.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }

            setResult(Activity.RESULT_OK)
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            signUp.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())

                auth.createUserWithEmailAndPassword(
                    username.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener { task ->
                    // Success
                    if (task.isSuccessful) {
                        if (checkBox!!.isChecked) {
                            accountType = "Store"
                        }

                        val uuid: String = auth.uid.toString()
                        val postMap = hashMapOf<String, Any>()
                        postMap["accountType"] = accountType
                        postMap["storeName"] = binding.storeName!!.text

                        firestore.collection("Users").document(uuid).set(postMap)
                            .addOnSuccessListener {
                                activityStarter()
                            }.addOnFailureListener {
                                loading.visibility = View.GONE
                                Toast.makeText(
                                    applicationContext,
                                    it.localizedMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }
                }   // Fail
                    .addOnFailureListener {
                        loading.visibility = View.GONE
                        Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG)
                            .show()
                    }
            }

            signIn.setOnClickListener {
                loading.visibility = View.VISIBLE

                auth.signInWithEmailAndPassword(username.text.toString(), password.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            activityStarter()
                        }
                    }.addOnFailureListener {
                        loading.visibility = View.GONE
                        Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG)
                            .show()
                    }
            }
        }
        checkBox!!.setOnCheckedChangeListener { _, isSelected ->
            if (isSelected) {
                binding.storeName!!.visibility = View.VISIBLE
            } else {
                binding.storeName!!.visibility = View.GONE
            }
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
                loading.visibility = View.GONE
                finish()
            }.addOnFailureListener {
                Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (auth.currentUser != null) {
            activityStarter()
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}