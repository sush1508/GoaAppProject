package com.example.panoramademo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.InputStream;

public class Page2 extends AppCompatActivity {

    private VrPanoramaView vrPanoramaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        vrPanoramaView = findViewById(R.id.vrPanoramaView);
        loadPhotoSphere();

        Intent i = new Intent(Page2.this,TexttoSpeech.class);
        startActivity(i);

        vrPanoramaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        vrPanoramaView.pauseRendering();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        vrPanoramaView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        vrPanoramaView.shutdown();
        super.onDestroy();
    }

    private void loadPhotoSphere() {
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        InputStream inputStream;

        AssetManager assetManager = getAssets();
        try {
            inputStream = assetManager.open("photosphere.jpg");
            options.inputType = VrPanoramaView.Options.TYPE_MONO;
            vrPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
