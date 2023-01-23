package com.dk.test_sport;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity  {

    private WebView webview;

    InternetDetector cd;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ВЕБВЬЮ
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://firebase.google.com/docs/remote-config/get-started?platform=android#firebase-console");
        webview.setWebViewClient(new WebViewClient());
        webview.setVisibility(View.INVISIBLE);
        //КОНЕЦ ВЕБВЬЮ

        //ИНТЕРНЕТ
        cd = new InternetDetector(this);
        //

        //ЗДЕСЬ ПРОВЕРКА СОХРАНЕНА ЛИ ССЫЛКА(If else)
        //Ссылка не сохранена
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d(TAG, "Config params updated: " + updated);
                            Toast.makeText(MainActivity.this, "Fetch and activate succeeded",
                                    Toast.LENGTH_SHORT).show();
                            String find = mFirebaseRemoteConfig.getString("url");
                            if (find == "EMULATOR||NO SIM"){
                                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                                startActivity(intent);
                            }else {
                                //Иначе сохранить ссылку и запуск вебвью
                            }
                            //Ссылка сохранена
                            //Интернет отключён?
                            if (!cd.isConnected()){
                                Toast.makeText(MainActivity.this, "Please turn internet on", Toast.LENGTH_SHORT).show();
                                showDialog("com.dk.test_sport");
                            } else {
                                webview.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, GameActivity.class);
                            startActivity(intent);
                        }

                        // displayWelcomeMessage();
                    }
                });

    }
        public class WebViewClient extends android.webkit.WebViewClient {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            //для старых устройств
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        }
        //ВКЛЮЧИ ИНЕТ
        private void showDialog(final String appPackageName){
            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("The application requires an internet connection")
                    .setPositiveButton("Ok", null)
                    .show();
        }
        //

}


