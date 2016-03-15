package com.zbar.lib;

import com.common.util.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ShowUrl extends Activity {
	private WebView mWebView;
	@SuppressLint("SetJavaScriptEnabled") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_text);
		mWebView=(WebView) findViewById(R.id.webview);
		Intent intent=getIntent();
		String getUrl=intent.getStringExtra("url");
		//加载互联网
		mWebView.loadUrl(getUrl);
		WebSettings settings = mWebView.getSettings();
		//设置支持js，必须设置
		settings.setJavaScriptEnabled(true);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mWebView != null && keyCode == KeyEvent.KEYCODE_BACK
				&& mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
