package com.common.util.webviewshow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.common.util.R;
import com.common.widget.Custom_TitleBar_1;
import com.common.widget.Custom_TitleBar_1.Custom_TitleBar_1Listener;

public class WebViewActivity extends Activity {
	private static final String TAG = WebViewActivity.class.getSimpleName();

	public WebViewActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static WebViewActivity instance;
	private static Context mContext;

	public interface WebViewActivityListener {

		public void initTitleBar(Custom_TitleBar_1 custom_TitleBar_1);

		public void updateView(WebView webView);

		public Object getHtmlObject(WebView webView, Activity activity);

	}

	public synchronized static WebViewActivity getInstance(Context context) {
		if (instance == null) {
			instance = new WebViewActivity(context);
		}
		return instance;
	}

	protected WebViewActivity(Context context) {
		WebViewActivity.mContext = context;
	}

	private static WebViewActivityListener listener = null;
	private static String url = null;

	public void show_WebView(String url, WebViewActivityListener listener) {
		Intent intent = new Intent(mContext, WebViewActivity.class);
		WebViewActivity.listener = listener;
		WebViewActivity.url = url;
		mContext.startActivity(intent);
	}

	WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		// 处理返回键
		Custom_TitleBar_1 custom_TitleBar_1 = (Custom_TitleBar_1) findViewById(R.id.custom_titlebar_1_activitywebview);
		// custom_TitleBar_1.setActivity(this);
		mWebView = (WebView) findViewById(R.id.activity_webview);
		custom_TitleBar_1
				.setCustom_TitleBar_1Listener(new Custom_TitleBar_1Listener() {

					@Override
					public void right_tv_DO() {
						// TODO Auto-generated method stub
						if (listener != null) {
							listener.updateView(mWebView);
						}
					}

					@Override
					public void left_tv_DO() {
						// TODO Auto-generated method stub
						finish();
					}
				});
		if (listener != null) {
			listener.initTitleBar(custom_TitleBar_1);
		}
		

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setAllowFileAccess(true);
		webSettings.setAppCacheEnabled(false);
		String appCachePath = this.getCacheDir().getAbsolutePath();
		webSettings.setAppCachePath(appCachePath);
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webSettings.setJavaScriptEnabled(true);// 开启javascript设置
		webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 允许弹出框
		mWebView.setWebChromeClient(new WebChromeClient());// 允许弹出框
		webSettings.setDefaultTextEncodingName("utf-8");
		int version = Integer.valueOf(android.os.Build.VERSION.SDK);
		if (version >= 14) {// 4.0需打开硬件加速
			getWindow().setFlags(0x1000000, 0x1000000);
		}
		mWebView.addJavascriptInterface(getHtmlObject(), "jsObj");
		mWebView.loadUrl(url);

	}

	private Object getHtmlObject() {
		Object insertObj = null;
		if (listener != null) {
			insertObj = listener.getHtmlObject(mWebView, this);
		}
		// Object insertObj = new Object() {
		// public String HtmlcallJava() {
		// return "Html call Java";
		// }
		//
		// public String HtmlcallJava2(final String param) {
		// return "Html call Java : " + param;
		// }
		//
		// public void JavacallHtml() {
		// runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		// mWebView.loadUrl("javascript: showFromHtml()");
		// Toast.makeText(WebViewActivity.this, "clickBtn",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		// }
		//
		// public void JavacallHtml2() {
		// runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		// mWebView.loadUrl("javascript: showFromHtml2('IT-homer blog')");
		// Toast.makeText(WebViewActivity.this, "clickBtn2",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		// }
		// };

		return insertObj;
	}

}
