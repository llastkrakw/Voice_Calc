package com.ona.speechtotext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected static final int RESULT_SPEECH = 1;

    Button start;
    TextView record_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        record_result = (TextView) findViewById(R.id.speech_result);
        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "fr-FR");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    record_result.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Opps! Your device doesn't sup²port Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String value =  text.get(0).replace(" ","").trim();
                    String good_value = value.replace('×', '*');
                    int result = calC(good_value);
                    record_result.setText(String.valueOf(result));
                }
                break;
            }

        }
    }

    public int calC(String input) {
        Log.d("value", input);

        String[] operators = input.split("[0-9]");
        String[] operands = input.split("[+*\\/-]");

        int agregate = Integer.parseInt(operands[0]);

        for(int i=1;i<operands.length;i++){
            if(operators[i].equals("+"))
                agregate += Integer.parseInt(operands[i]);
            else if(operators[i].equals("-"))
                agregate -= Integer.parseInt(operands[i]);
            else if(operators[i].equals("*"))
                agregate *= Integer.parseInt(operands[i]);
        }

        return agregate;
    }

}