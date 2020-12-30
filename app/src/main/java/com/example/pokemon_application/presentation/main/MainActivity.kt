package com.example.pokemon_application.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.pokemon_application.R
import com.example.pokemon_application.presentation.list.PokemonListActivity
import com.example.pokemon_application.presentation.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private var mExitTime: Long = 0
    private val mainViewModel : MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton()
        registerButton()

    }

    private fun loginButton(){
        mainViewModel.loginLiveData.observe(this, Observer {
            when (it) {
                is LoginSuccess -> {
                    Thread.sleep(500)
                    val intent = Intent(this@MainActivity, PokemonListActivity::class.java)
                    this@MainActivity.startActivity(intent)
                }
                LoginError -> {
                    AlertDialog.Builder(this)
                        .setTitle("Error !")
                        .setMessage("The email address or password is wrong.")
                        .setPositiveButton("OK") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        })
        login_button.setOnClickListener{
            mainViewModel.onClickedLogin(username.text.toString().trim(), password.text.toString().trim())
            findViewById<EditText>(R.id.username).text = null
            findViewById<EditText>(R.id.password).text = null
        }
    }

    private fun registerButton() {

        create_account_button.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            this@MainActivity.startActivity(intent)
        })
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (System.currentTimeMillis() - mExitTime > 2000) {

                Toast.makeText(this, "Press again to exit the APP ~", Toast.LENGTH_SHORT).show()
                mExitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}