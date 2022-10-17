package com.example.myapplication.laborator4;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class WebsearchActivity extends AppCompatActivity {
    private WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websearch);

        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("https://www.google.ro/imghp?hl=en&ogbl");

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()){
            myWebView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }

    public void loadImage(View view) {
    }
}
