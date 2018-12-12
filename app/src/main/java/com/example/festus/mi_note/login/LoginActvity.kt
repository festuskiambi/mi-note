package com.example.festus.mi_note.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.festus.mi_note.R

class LoginActivity : AppCompatActivity(),ILoginContract.View {
    override fun setLoginStatus(text: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setAuthButton(text: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoopAnimation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setStatusDrawable(imageUrl: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startSignInInFlow() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun StartListFeature() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}

