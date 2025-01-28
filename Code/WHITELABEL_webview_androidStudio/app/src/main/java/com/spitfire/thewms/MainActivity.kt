package com.spitfire.thewms

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    
    // Initialize WebView
    // Load configuration and apply settings
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        loadConfig()
    }


    // access the config.json file in res/raw
    // extract values from JSON
    // apply app name and load the website
    // set app's title
    // load the URL into WebView
    // handle error
    // default fallback URL
    private fun loadConfig() {
        try {
            val inputStream: InputStream = resources.openRawResource(R.raw.config)
            val jsonText = inputStream.bufferedReader().use { it.readText() }
            val config = JSONObject(jsonText)

            val branding = config.getJSONObject("branding")
            val appName = branding.getString("appName")
            val websiteUrl = config.getString("websiteUrl")

            title = appName
            webView.loadUrl(websiteUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            webView.loadUrl("https://fallback-website.com")
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
