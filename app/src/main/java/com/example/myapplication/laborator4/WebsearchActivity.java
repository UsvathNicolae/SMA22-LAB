package com.example.myapplication.laborator4;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class WebsearchActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "https://images.app.goo.gl/2N549cTjFVviosJFA";
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
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData abc = clipboard.getPrimaryClip();
        ClipData.Item item = abc.getItemAt(0);
        String url = item.getText().toString();

        if(!url.contains("https://images.app.goo.gl/")){
            Toast.makeText(this, "URL not valid. Try another.",Toast.LENGTH_SHORT).show();
        }else
            if(view.getId() == R.id.bLoadBackground){
            Intent intent = new Intent(this, ImageIntentService.class);
            intent.putExtra(EXTRA_URL, url);
            startService(intent);
        }else if(view.getId() == R.id.bLoadForeground){
            Intent startIntent = new Intent(this, ForegroundImageService.class);
            startIntent.setAction(ForegroundImageService.STARTFOREGROUND_ACTION);
            startIntent.putExtra(EXTRA_URL, url);
            startService(startIntent);
        }
    }
}
