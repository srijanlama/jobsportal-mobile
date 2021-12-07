package com.scriptsbundle.nokri;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.multidex.MultiDex;

import com.google.android.gms.ads.MobileAds;
import com.scriptsbundle.nokri.manager.Nokri_AppLifeCycleManager;
import com.scriptsbundle.nokri.utils.Nokri_LanguageSupport;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by GlixenTech on 3/22/2018.
 */

public class Nokri_Application extends Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Nokri_AppLifeCycleManager.init(this);
        MobileAds.initialize(this,"ca-app-pub-3543987221312116~9785428511");
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Nokri_LanguageSupport.onAttach(base, "en"));
        MultiDex.install(this);
    }

}


