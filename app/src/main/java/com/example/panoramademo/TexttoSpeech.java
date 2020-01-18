package com.example.panoramademo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class TexttoSpeech extends AppCompatActivity {

    TextView textView;
    Button readButton;
    TextToSpeech texttoSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textto_speech);

        textView = findViewById(R.id.mytext);
        textView.setText("This is sample text to check the Text-To-Speech feature in android");
        readButton = findViewById(R.id.readbtn);
        texttoSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!=TextToSpeech.ERROR){
                    texttoSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texttoSpeech.speak(textView.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });

    }

}
