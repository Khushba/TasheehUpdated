package com.example.android.tasheeh;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;


public class CameraT extends AppCompatActivity {

    ImageView im2;
    Integer CAMERA_REQUEST=1, SELECT_FILE=0;
    EditText Text;
    String strings="";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        im2 = findViewById(R.id.ivImage2);

        Text= findViewById(R.id.ed2);

        Button fab = findViewById(R.id.but2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelectImage();
            }
        });
        Button fab2 = findViewById(R.id.butt3);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BitmapDrawable drawable = (BitmapDrawable) im2.getDrawable();
                Bitmap b = drawable.getBitmap();
                TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if (!textRecognizer.isOperational()) {
                    Toast.makeText(getApplicationContext(), "Could not get the text", Toast.LENGTH_SHORT).show();

                } else {

                    Frame frame = new Frame.Builder().setBitmap(b).build();
                    SparseArray<TextBlock> items = textRecognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < items.size(); ++i) {
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");
                    }
                    Text.setText(sb.toString());
                    strings=strings+sb;
                }

            }
        });

        Button fab4 = findViewById(R.id.butt4);

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(CameraT.this, VerifyByExtractedText.class);
                i.putExtra("string",strings);
                startActivity(i);
            }
        });


    }

    private void SelectImage(){
        final CharSequence[] items={"Open Camera", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CameraT.this);
        builder.setTitle("");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Open Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);

                }
                if (items[i].equals("Open Camera")) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();

                }
            }
        });
        builder.show();

    }
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if (requestCode == CAMERA_REQUEST) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                ImageView imageview = findViewById(R.id.ivImage2);
                imageview.setImageBitmap(image);


            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                im2.setImageURI(selectedImageUri);
            }
        }
    }




}

