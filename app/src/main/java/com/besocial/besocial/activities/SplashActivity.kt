package com.besocial.besocial.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import java.util.*
import com.google.firebase.auth.FirebaseAuth
import com.besocial.besocial.R


class SplashActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SplashActivity"
        private const val RC_SIGN_IN = 123
        private const val FIRST_TIME_PREF = "FIRST_TIME_PREF"
        private const val IS_FIRST_TIME = "IS_FIRST_TIME"
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        logMessage("onCreate")
    }

    override fun onResume() {
        super.onResume()
        logMessage("onResume")

        val prefs = getSharedPreferences(FIRST_TIME_PREF, MODE_PRIVATE)
        val isFirstTime = prefs.getBoolean(IS_FIRST_TIME, true)
        logMessage("isFirstTime: $isFirstTime")
        if (isFirstTime) {
            startActivity(Intent(this, IntroActivity::class.java))
            val editor = prefs.edit()
            editor.putBoolean(IS_FIRST_TIME, false)
            editor.apply()
        } else {
            if (auth.currentUser != null) {
                // already signed in
                logMessage("already signed in")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // not signed in
                logMessage("not signed in")
                startLogin()
            }
        }
    }

    private fun startLogin() {
        logMessage("startLogin")
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.ic_people_24dp)
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(Arrays.asList(
                                AuthUI.IdpConfig.GoogleBuilder().build(),
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setTheme(R.style.AppTheme_NoActionBar)
                        .build(),
                RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackBar(getString(R.string.sign_in_cancelled))
                    return
                }

                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    showSnackBar(getString(R.string.no_internet_connection))
                    return
                }

                showSnackBar(getString(R.string.unknown_error))
                Log.e(TAG, "Sign-in error: ", response.error)
            }
        }
    }

    private fun logMessage(message: String) {
        Log.d(TAG, message)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
}
