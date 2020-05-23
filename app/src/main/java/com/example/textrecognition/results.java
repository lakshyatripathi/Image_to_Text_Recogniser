package com.example.textrecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class results extends AppCompatActivity {
    TextView resultdisp;
    Button cpy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        cpy = findViewById(R.id.btncopy);

        resultdisp = findViewById(R.id.resultdisp);
        resultdisp.setText("");
        resultdisp.setText(getIntent().getStringExtra("mytext"));

        cpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)results.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(resultdisp.getText());
                Toast.makeText(results.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
