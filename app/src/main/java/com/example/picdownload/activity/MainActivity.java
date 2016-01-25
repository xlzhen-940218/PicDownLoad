package com.example.picdownload.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.picdownload.R;
import com.example.picdownload.adapter.ImageAdapter;
import com.example.picdownload.adapter.ImageDownAdapter;
import com.example.picdownload.entity.DownEvent;
import com.example.picdownload.entity.ImageEntity;
import com.example.picdownload.utils.HtmlService;
import com.example.picdownload.utils.majid.core.DownloadManagerPro;
import com.example.picdownload.utils.majid.report.listener.DownloadManagerListener;
import com.example.picdownload.utils.sp.SavePreference;
import com.example.picdownload.widget.OneBtnDialogView;
import com.example.picdownload.widget.ToastView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,SearchView.OnQueryTextListener{
    FloatingActionButton fabDone;
    ListView listView,listDownView;
    ImageAdapter imageAdapter;
    ImageDownAdapter imageDownAdapter;
    ProgressBar progressBar;
    DownloadManagerPro downloadManagerPro;
    List<ImageEntity> imageEntities;
    boolean openMany;
    String savePath;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);

        savePath=SavePreference.getStr(this, "SavePath");
        if(savePath.length()<1) {
            savePath = "PicDownload";
            SavePreference.save(this, "SavePath", "PicDownload");
        }


        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        listDownView=(ListView)findViewById(R.id.listDownView);
        imageDownAdapter=new ImageDownAdapter(this,null);
        listDownView.setAdapter(imageDownAdapter);
        downloadManagerPro=new DownloadManagerPro(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabDone= (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(this);
        fabDone.setVisibility(View.GONE);

        listView=(ListView)findViewById(R.id.listView);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initDownLoad();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    public void onEventMainThread(DownEvent downEvent){
        imageDownAdapter.addData(downEvent);
    }
    private void initDownLoad() {

        downloadManagerPro.init("/"+savePath, 16, new DownloadManagerListener() {
            @Override
            public void OnDownloadStarted(long taskId,String downloadUrl) {

            }

            @Override
            public void OnDownloadPaused(long taskId,String downloadUrl) {

            }

            @Override
            public void onDownloadProcess(long taskId, double percent, long downloadedLength,String downloadUrl) {
                EventBus.getDefault().post(new DownEvent((int)percent, DownEvent.Type.loading,taskId,downloadUrl));
            }

            @Override
            public void OnDownloadFinished(long taskId,String downloadUrl) {

            }

            @Override
            public void OnDownloadRebuildStart(long taskId,String downloadUrl) {

            }

            @Override
            public void OnDownloadRebuildFinished(long taskId,String downloadUrl) {

            }

            @Override
            public void OnDownloadCompleted(long taskId,String downloadUrl) {
                EventBus.getDefault().post(new DownEvent(100, DownEvent.Type.complete,taskId,downloadUrl));
            }

            @Override
            public void connectionLost(long taskId,String downloadUrl) {
                EventBus.getDefault().post(new DownEvent(0, DownEvent.Type.error,taskId,downloadUrl));
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingActivity.class));
            return true;
        }else if(id==R.id.action_disclaimers){
            final OneBtnDialogView oneBtnDialogView=new OneBtnDialogView(this);
            oneBtnDialogView.setOneBtnDialogView(getString(R.string.action_disclaimers)
                    , getString(R.string.disclaimers_details), getString(R.string.prompt_ok), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oneBtnDialogView.dismiss();
                }
            });
            oneBtnDialogView.show();
        }else if(id==R.id.action_search){
            searchView= (SearchView) MenuItemCompat.getActionView(item);
            searchView.setQueryHint(getString(R.string.edit_hint));
            searchView.setOnQueryTextListener(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            listDownView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            // Handle the camera action
        } else if (id == R.id.nav_down) {
            listDownView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);

        } else if (id == R.id.nav_share) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, "我发现了一款可以下载网页图片的工具哦，戳这里下载:"+"http://pan.baidu.com/s/1c1mBQ4g");
            intent.putExtra(Intent.EXTRA_TITLE, "网页图片抓取器");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, "请选择"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabDone://选择完成
                imageEntities=imageAdapter.getSelectImage();
                if(imageEntities.size()<1)
                    return;

                File file=new File(Environment.getExternalStorageDirectory(),"/"+savePath+"/");
                if(!file.exists())
                    file.mkdir();//如果不存在这个图片文件夹则创建

                for(int i=0;i<imageEntities.size();i++) {
                    file = new File(Environment.getExternalStorageDirectory(), savePath+"/" + imageEntities.get(i).getImageUrl().split("/")[imageEntities.get(i).getImageUrl().split("/").length-1].replace(".jpg", ""));
                    if (file.exists()) {
                        imageEntities.remove(i);
                    }
                }

                for(int i=0;i<imageEntities.size();i++) {
                    if (!TextUtils.isEmpty(imageEntities.get(i).getImageUrl())) {
                        Log.v("下载地址", savePath+"/" + imageEntities.get(i).getImageUrl().split("/")[imageEntities.get(i).getImageUrl().split("/").length - 1].replace(".jpg", ""));
                        int token = downloadManagerPro.addTask(imageEntities.get(i).getImageUrl().split("/")[imageEntities.get(i).getImageUrl().split("/").length-1].replace(".jpg",""), imageEntities.get(i).getImageUrl(), true, true);

                        try {
                            downloadManagerPro.startDownload(token);
                            progressBar.setVisibility(View.VISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastView.showToast("下载地址"+imageEntities.get(i).getImageUrl()+"有误!", this);
                    }
                }

                ToastView.showToast("开始下载",this);
                fabDone.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        openMany=SavePreference.getBoolean(this, "isOpenMany");

        if(!query.contains("http")) {
            Toast.makeText(this, "无http头，已智能添加!", Toast.LENGTH_SHORT).show();
            query="http://"+query;
        }
        listDownView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        new SearchHtml().execute(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private class SearchHtml extends AsyncTask<String,String,String>{
        private String path="";
        @Override
        protected String doInBackground(String... params) {
            String result="";
            try {
                path=params[0];
                result=HtmlService.getHtml(params[0]);

                if(openMany)
                    for(int i=1;i<10;i++)
                        result+=HtmlService.getHtml(params[0].replace(".htm","_"+i+".htm"));

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            List<ImageEntity> imageEntities=HtmlService.getImageSrc("http://"+path.split("/")[2],s);
            imageAdapter=new ImageAdapter(MainActivity.this,imageEntities);
            listView.setAdapter(imageAdapter);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void changeFloatingActionButton(int visibility){
        fabDone.setVisibility(visibility);
    }
}
