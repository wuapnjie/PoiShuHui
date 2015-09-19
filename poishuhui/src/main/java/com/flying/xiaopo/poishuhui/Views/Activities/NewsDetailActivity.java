package com.flying.xiaopo.poishuhui.Views.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.Toast;

import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.HtmlUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2015/8/23.
 */
public class NewsDetailActivity extends AppCompatActivity {
    public static String INTENT_KEY_URL="url";
    public static String INTENT_KEY_TITLE="title";
    public static String INTENT_KEY_TIME="time";
    @InjectView(R.id.toolbar_text)
    Toolbar toolbar;
    @InjectView(R.id.webview)
    WebView mWebView;

    String aimURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.inject(this);
        toolbar.setTitle(getIntent().getStringExtra(INTENT_KEY_TITLE));
        aimURL = getIntent().getStringExtra(INTENT_KEY_URL);
        Toast.makeText(this, aimURL, Toast.LENGTH_SHORT).show();
        Task task = new Task(){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mWebView.loadDataWithBaseURL(null,s,"text/html;charset=utf-8","utf-8",null);
            }
        };
        task.execute(aimURL);

    }

    private class Task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return HtmlUtil.obtainNewsContent(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}
