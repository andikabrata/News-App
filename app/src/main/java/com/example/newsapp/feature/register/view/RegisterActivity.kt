package com.example.newsapp.feature.register.view

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import com.example.newsapp.core.base.BaseVMActivity
import com.example.newsapp.databinding.ActivityRegisterBinding
import com.example.newsapp.feature.login.model.Auth
import com.example.newsapp.feature.login.view.LoginActivity
import com.example.newsapp.feature.main.view.MainActivity
import com.example.newsapp.feature.register.view_model.RegisterViewModel
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : BaseVMActivity<RegisterViewModel, ActivityRegisterBinding>() {
    private lateinit var googleIdOption: GetSignInWithGoogleOption
    private lateinit var credentialManager: CredentialManager
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)


    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getViewModel() = RegisterViewModel::class.java

    override fun getViewBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        credentialManager = CredentialManager.create(this)
        initSignInGoogleRequest()
        binding.tvLogin.setOnClickListener {
            goToLogin()
        }

        binding.btnGoogle.setOnClickListener {
            loginGoogle()
        }

        binding.btnSignUp.setOnClickListener {
            Toast.makeText(this, "Cooming Soon, Only Login With Google", Toast.LENGTH_SHORT).show()
        }

        binding.btnFacebook.setOnClickListener {
            Toast.makeText(this, "Cooming Soon, Only Login With Google", Toast.LENGTH_SHORT).show()
        }
    }

    override fun observerViewModel() {}

    private fun goToLogin() {
        LoginActivity.startActivity(this)
    }

    private fun goToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun initSignInGoogleRequest() {
        googleIdOption =
            GetSignInWithGoogleOption.Builder("496157887356-ljqnsa208amkapb57886vck49cfb0e4v.apps.googleusercontent.com")
                .build()
    }

    private fun loginGoogle() {
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = this@RegisterActivity,
                )
                handleSignIn(result)
            } catch (_: GetCredentialException) {
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {
            // GoogleIdToken credential
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        viewModel.setPreference(
                            auth = Auth(
                                name = googleIdTokenCredential.displayName,
                                picture = googleIdTokenCredential.profilePictureUri.toString()
                            )
                        )
                        goToHome()
                        finish()

                    } catch (e: GoogleIdTokenParsingException) {
                        Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Toast.makeText(
                        applicationContext,
                        "Type Credential: " + credential.type + " has not been handled",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // Passkey credential
            is PublicKeyCredential -> {}

            // Password credential
            is PasswordCredential -> {}

            else -> {
                Toast.makeText(applicationContext, "Credential Not Found", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}