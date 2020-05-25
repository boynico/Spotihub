package net.azarquiel.example.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_login.*
import net.azarquiel.example.R

class LoginActivity : AppCompatActivity() {

    private lateinit var webViewLogin: WebView
    private var loggedFlag: Boolean = false         //Flag para evitar redirigr muchas veces tras el callback del server

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //start de webView
        webViewLogin = webView_loginActivity
        val webView_settings = webViewLogin.settings
        webView_settings.javaScriptEnabled = true

        webViewLogin.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            //si se carga y renderiza la url de successful login del server ejecuto la función succesfulLogin()

            override fun onPageFinished(view: WebView?, url: String?){
                var current_url: String
                current_url = webViewLogin.url
                if (current_url.startsWith("http://192.168.1.108:8888/#access_token=")) {
                    if (!loggedFlag){
                        successfulLogin()
                    }
                    loggedFlag = true
                }
            }
        }
        webViewLogin.loadUrl("http://192.168.1.108:8888/login")
    }


    private fun successfulLogin() {
        Log.d("LOGIN-ACTIVITY-MSG", "Login successful")
        val intent = Intent(this, LoggedInActivity::class.java)
        startActivity(intent)
    }
}
