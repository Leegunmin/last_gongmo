package nongsa.agoto.MaintoOther.information.adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import nongsa.agoto.R;


public class Webview extends AppCompatActivity {
    private WebView  mainWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent intent = getIntent();
        System.out.println("webview!!");
        String Link = intent.getStringExtra("link");
        mainWebView = (WebView) findViewById(R.id.main_web_view);
        mainWebView.getSettings().setUseWideViewPort(true);
        mainWebView.getSettings().setLoadWithOverviewMode(true);
        mainWebView.getSettings().setJavaScriptEnabled(true);
        mainWebView.loadUrl(Link);
        System.out.println("webview!!!");
        finish();

    }
}
