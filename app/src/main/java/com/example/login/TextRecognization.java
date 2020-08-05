package com.example.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import androidx.appcompat.app.AppCompatActivity;

public class TextRecognization extends AppCompatActivity {

    private Button capture, detect;
    private ImageView imageView;
    private TextView textView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognization);


        capture = findViewById(R.id.capture);
        detect = findViewById(R.id.detect);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 detectTextFromImage();
            }

            
        });
    }

    private void detectTextFromImage() {
        TextRecognizer recognizer = new TextRecognizer.Builder(TextRecognization.this).build();
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build() ;

            SparseArray<TextBlock> sparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();

            for(int i=0;i<sparseArray.size();i++){
                TextBlock tx = sparseArray.get(i);
                String str = tx.getValue();
                stringBuilder.append(str);
            }
            textView.setText(stringBuilder);

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }


}