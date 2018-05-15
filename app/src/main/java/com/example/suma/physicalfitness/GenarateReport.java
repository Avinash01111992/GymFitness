package com.example.suma.physicalfitness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.suma.physicalfitness.pojo.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URI;

public class GenarateReport extends AppCompatActivity {

    Button uploadBtn;
    int REQUEST_CODE_DOC = 100;
    File file;
    String filepath;
    String userNme;
    ProgressBar progressBar;

    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genarate_report);
        Bundle bundle = getIntent().getExtras();
        userNme = bundle.getString("userName");
        Log.e("userName",userNme);

        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        uploadBtn = (Button)findViewById(R.id.upload_pdf);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Upload Report");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocument();
            }
        });

    }

    private void getDocument()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_CODE_DOC);
    }

    @Override
    protected void onActivityResult(int req, int result, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(req, result, data);
        if (result == RESULT_OK)
        {
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
//            updateConfirm();

        }
    }

    //this method is uploading the file
    //the code is same as the previous tutorial
    //so we are not explaining it
    private void uploadFile(Uri data) {
//        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef = mStorageReference.child("uploads/" + userNme + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        progressBar.setVisibility(View.GONE);
//                        textViewStatus.setText("File Uploaded Successfully")
                        Upload upload = new Upload(userNme, taskSnapshot.getDownloadUrl().toString());
                        mDatabaseReference.child(userNme).setValue(upload);
                        updateConfirm();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });

    }

    protected void updateConfirm() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle("Uploaded Successfully");
        alertbox.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {
            }
        }).show();

    }

}
