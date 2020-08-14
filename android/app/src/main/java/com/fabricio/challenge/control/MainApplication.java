package com.fabricio.challenge.control;

import android.app.Application;

import com.fabricio.challenge.R;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/**
 * @author Fabricio Godoi
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Install and define default font for the application with Calligraphy3
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/roboto_regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

}
