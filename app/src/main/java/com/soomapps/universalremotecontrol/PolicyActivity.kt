package com.soomapps.universalremotecontrol

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.soomapps.universalremotecontrol.utils.ConnectionDetector
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class PolicyActivity : AppCompatActivity() {

    private var webView: WebView? = null
    private var currentUrl: String? = null
    private var dialogTV: TextView? = null
    private var animationFadeIn: Animation? = null
    private var animationFadeOut: Animation? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Privacy Policy"
        //  alertDialogBuilder = AlertDialog.Builder(context)
        dialogTV = findViewById<TextView>(R.id.dialogTV) as TextView
        dialogTV!!.visibility = View.INVISIBLE

        animationFadeIn = AnimationUtils.loadAnimation(this@PolicyActivity, R.anim.fade_in)
        animationFadeOut = AnimationUtils.loadAnimation(this@PolicyActivity, R.anim.fade_out)
        animationFadeIn!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {

                if (animation === animationFadeIn) {
                    dialogTV!!.startAnimation(animationFadeOut)
                    dialogTV!!.startAnimation(animationFadeIn)
                }
            }
            override fun onAnimationRepeat(animation: Animation) {
            }
        })

        if (!ConnectionDetector.checkInternetConnection(this@PolicyActivity)) {
            dialogTV!!.visibility = View.VISIBLE
            dialogTV!!.text = "You don't have internet connection."
            dialogTV!!.startAnimation(animationFadeIn)
            Toast.makeText(this@PolicyActivity,"No Internet Connection",Toast.LENGTH_LONG).show()
        } else {
            //casting webView
            //  webView = findViewById<WebView>(R.id.webView) as WebView
            // webView!!.getSettings().javaScriptEnabled = true
            //on long click should not copy thr content
            //  webView!!.setOnLongClickListener(View.OnLongClickListener { true })
            //  webView!!.loadUrl("http://www.soomapps.com/universal-tv-remote-privacy-policy")

            //casting webView
            webView = findViewById(R.id.webView)
            webView!!.visibility = View.INVISIBLE
            webView!!.webViewClient = MyBrowser()

            // progressDialog!!.setMessage("Loading....")
            // progressDialog.setCanceledOnTouchOutside(false);
            // progressDialog!!.setCancelable(false)

            //  dialogTV!!.visibility = View.VISIBLE
            // dialogTV!!.startAnimation(animationFadeIn)

            // disable copy of content on long click
            webView!!.setOnLongClickListener { true }
            //url for webView
            //https://www.retailmenot.com/view/netflix.com
            currentUrl = "http://www.soomapps.com/universal-tv-remote-privacy-policy"
            webView!!.settings.loadsImagesAutomatically = true
            webView!!.settings.javaScriptEnabled = true
            webView!!.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webView!!.loadUrl(currentUrl!!)
        }
    }

    //creating MyBrowser as webViewClient
    private inner class MyBrowser : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {

            dialogTV!!.visibility = View.VISIBLE
            dialogTV!!.startAnimation(animationFadeIn)

            webView!!.visibility = View.INVISIBLE
            super.onPageStarted(view, url, favicon)
        }

        /* override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

             if (url.contains("out")) { // Could be cleverer and use a regex
                 view.settings.userAgentString = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0"
                 val temp = "https://www.retailmenot.com/showcoupon/"
                 val separated = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                 view.loadUrl(temp + separated[separated.size - 1] + "/")
                 return true // Leave webview and use browser
             } else {
                 view.loadUrl(url)
                 return true
             }

         }*/

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {

            return super.shouldOverrideUrlLoading(view, request)
        }
        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            webView!!.goBack()
            super.onReceivedError(view, request, error)
        }

        override fun onPageFinished(view: WebView, url: String) {
            //  if (progressDialog!!.isShowing) {
            // inject CSS when page is done loading
            //handler
            Handler().postDelayed({
                dialogTV!!.visibility = View.INVISIBLE
                // dialogTV!!.startAnimation(animationFadeIn)
                webView!!.visibility = View.VISIBLE
            }, 1500)
            super.onPageFinished(view, url)
            // }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}

