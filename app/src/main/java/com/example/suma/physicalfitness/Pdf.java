package com.example.suma.physicalfitness;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Pdf extends AppCompatActivity {

    Button pdfbtn;
    ImageView pdfimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        pdfbtn = (Button)findViewById(R.id.btn_okay);
        pdfimg = (ImageView)findViewById(R.id.image_pdf);

        pdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openPDF(pdfimg);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pdf");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }



    private void openPDF(ImageView targetView) throws IOException {

        //open file in assets

        ParcelFileDescriptor fileDescriptor;

        String FILENAME = "sample_pdf.pdf";

        // Create file object to read and write on
        File file = new File(getApplicationContext().getCacheDir(), FILENAME);
        if (!file.exists()) {
            AssetManager assetManager = getApplicationContext().getAssets();
            copyAsset(assetManager, FILENAME, file.getAbsolutePath());
        }

        fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);

        PdfRenderer pdfRenderer = new PdfRenderer(fileDescriptor);

        //Display page 0
        PdfRenderer.Page rendererPage = pdfRenderer.openPage(0);
        int rendererPageWidth = rendererPage.getWidth();
        int rendererPageHeight = rendererPage.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(
                rendererPageWidth,
                rendererPageHeight,
                Bitmap.Config.ARGB_8888);
        rendererPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        targetView.setImageBitmap(bitmap);
        rendererPage.close();
        pdfRenderer.close();
    }


    public static boolean copyAsset(AssetManager assetManager, String fromAssetPath, String toPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(fromAssetPath);
            new File(toPath).createNewFile();
            out = new FileOutputStream(toPath);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
