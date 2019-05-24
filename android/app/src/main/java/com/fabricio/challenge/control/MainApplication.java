package com.fabricio.challenge.control;

import android.app.Application;

import com.fabricio.challenge.R;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Define default font for apk
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/roboto_regular2.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

}
