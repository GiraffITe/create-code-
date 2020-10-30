package com.example.code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button QR = findViewById(R.id.qr);
        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //QRコード化する文字列
                EditText qr = findViewById(R.id.contents);
                String data = qr.getText().toString();

                TextView view = findViewById(R.id.view);
                view.setText(data);

                //QRコード画像の大きさを指定(pixel)
                int size = 500;

                try {
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

                    HashMap hints = new HashMap();

                    //文字コードの指定
                    hints.put(EncodeHintType.CHARACTER_SET, "shiftjis");

                    Bitmap bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, size, size, hints);

                    ImageView imageViewQrCode = (ImageView) findViewById(R.id.imageView);
                    imageViewQrCode.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    throw new AndroidRuntimeException("Barcode Error.", e);
                }
            }
        });

        Button itf = findViewById(R.id.itf);
        itf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //ITFコード化する文字列
                EditText qr = findViewById(R.id.contents);
                String data = qr.getText().toString();

                TextView view = findViewById(R.id.view);
                view.setText(data);

                //QRコード画像の大きさを指定(pixel)
                int height = 200;
                int width = 800;

                try {
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

                    Bitmap bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.ITF, width, height);

                    ImageView imageViewQrCode = (ImageView) findViewById(R.id.imageView);
                    imageViewQrCode.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    throw new AndroidRuntimeException("Barcode Error.", e);
                }
            }
        });

        Button readCode = findViewById(R.id.read);
        readCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainActivity.this).initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {

            String read = result.getContents();

            TextView view = findViewById(R.id.readText);
            view.setText(read);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}