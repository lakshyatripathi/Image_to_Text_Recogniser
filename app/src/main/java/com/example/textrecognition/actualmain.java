package com.example.textrecognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.List;

public class actualmain extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;


    Button text;
    ImageView extract;
    ImageView showimg;
    Bitmap imageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualmain);
        showimg = findViewById(R.id.showimg);
        text = findViewById(R.id.extract);
        extract = findViewById(R.id.capture);


        extract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();

            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                detecttextfromimg();


            }
        });


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            showimg.setImageBitmap(imageBitmap);
        }
    }

    void detecttextfromimg()
    {
        if(imageBitmap==null)
        {
            Toast.makeText(actualmain.this,"Please select an image!!",Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseVisionImage fbimg = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextDetector fbvitd = FirebaseVision.getInstance().getVisionTextDetector();
        fbvitd.detectInImage(fbimg).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {

                displaytext(firebaseVisionText);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(actualmain.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void displaytext(FirebaseVisionText firebaseVisionText)
    {

        String text=null;
        List<FirebaseVisionText.Block> blockList = firebaseVisionText.getBlocks();
        if(blockList.size() == 0)
        {
            Toast.makeText(actualmain.this,"No Text Found in the Image",Toast.LENGTH_SHORT).show();

        }
        else
        {
            for(FirebaseVisionText.Block block: firebaseVisionText.getBlocks())
            {
                 text = block.getText();

            }



            Intent myintent = new Intent(actualmain.this,results.class);
            myintent.putExtra("mytext",text);
            startActivity(myintent);

        }
    }

}
