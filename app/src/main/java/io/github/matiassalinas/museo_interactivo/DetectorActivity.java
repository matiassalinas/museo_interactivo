package io.github.matiassalinas.museo_interactivo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class DetectorActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;
    private TextView resultTextView;
    private PointsOverlayView pointsOverlayView;
    private QRCodeReaderView qrCodeReaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if(resultTextView!=null){
            resultTextView.setText(text);
            pointsOverlayView.setPoints(points);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("RESUME","RESUME");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            setContentView(R.layout.activity_detector);
            initQRCodeReaderView();
        }else {
            Log.d("ELSE","ELSE");
            requestCameraPermission();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    private void initQRCodeReaderView(){
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        resultTextView = (TextView) findViewById(R.id.result_text_view);
        pointsOverlayView = (PointsOverlayView) findViewById((R.id.points_overlay_view));
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        //qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        //qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(DetectorActivity.this, new String[]{
                Manifest.permission.CAMERA
        }, MY_PERMISSION_REQUEST_CAMERA);
    }
}