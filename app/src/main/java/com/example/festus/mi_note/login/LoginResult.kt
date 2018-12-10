package com.example.festus.mi_note.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

/**
 * Created by Festus Kiambi on 12/10/18.
 */

data class LoginResult(val requestCode: Int, val account: GoogleSignInAccount?)