package com.flying.xiaopo.poishuhui.Views.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.flying.xiaopo.poishuhui.Adapters.LinearAdapter;
import com.flying.xiaopo.poishuhui.Beans.ComicBean;
import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.HtmlUtil;
import com.flying.xiaopo.poishuhui.Views.Activities.MainActivity;
import com.flying.xiaopo.poishuhui.Views.Fragments.ThirdFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2015/8/23.
 */
public class TextActivity extends AppCompatActivity {
    @InjectView(R.id.rv_text)
    RecyclerView rv_text;

    @InjectView(R.id.toolbar_text)
    Toolbar toolbar;
    LinearAdapter adapter;

    LinearLayoutManager manager;

    String aimURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.inject(this);
        aimURL = getIntent().getStringExtra(ThirdFragment.INTENT_KEY);
        init();
        obtainTORV(aimURL);
    }

    private void init() {
        adapter = new LinearAdapter(this);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return MainActivity.DEVICE_HEIGHT;
            }
        };
        rv_text.setLayoutManager(manager);
        rv_text.setAdapter(adapter);

        toolbar.setTitle(R.string.app_name);
    }

    private void obtainTORV(String aimURL) {
        Task task = new Task();
        task.execute(aimURL);
    }

    private class Task extends AsyncTask<String, Void, List<ComicBean>> {

        @Override
        protected List<ComicBean> doInBackground(String... params) {
            return HtmlUtil.obtainComicContent(params[0]);
        }

        @Override
        protected void onPostExecute(List<ComicBean> comicBeans) {
            super.onPostExecute(comicBeans);
            adapter.obtainData(comicBeans);
            adapter.notifyDataSetChanged();
        }
    }


}
