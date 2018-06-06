package crats.mvcbaseproject.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import crats.mvcbaseproject.R;

/**
 * Created by yashshah on 2017-12-05.
 */

public class YashMainActivity extends AppCompatActivity {

    EditText review,productname;
    Button submit;
    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    String email;

    final int RequestCamerePermissionID = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCamerePermissionID:
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yashmainactivity);

       // productname = (EditText)findViewById(R.id.productname);
       // review = (EditText) findViewById(R.id.review);
       // submit = (Button) findViewById(R.id.submit);
        cameraPreview = (SurfaceView) findViewById(R.id.surfaceView);
        txtResult = (TextView) findViewById(R.id.textView3);

        //Bundle bundle = getIntent().getExtras();
        //email = bundle.getString("email");
        barcodeDetector = new BarcodeDetector.Builder(this).build();
       // barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

      //  cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(1080,880).build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                   ActivityCompat.requestPermissions(YashMainActivity.this,new String[]{Manifest.permission.CAMERA},RequestCamerePermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();

                if(qrcodes.size() != 0)
                {
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {

                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            txtResult.setText(qrcodes.valueAt(0).displayValue);
                            cameraSource.stop();
                            Intent intentyash = new Intent(YashMainActivity.this, YashGiveReview.class);
                            Bundle extras = new Bundle();
                            extras.putString("qrcode",qrcodes.valueAt(0).displayValue);
                            intentyash.putExtras(extras);

                            startActivity(intentyash);
                        }
                    });
                }
            }
        });


       /* submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Getset getset = new Getset();

                getset.setQrcode(txtResult.getText().toString());
                getset.setReview(review.getText().toString());
                getset.setProductName(productname.getText().toString());


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference();
                    databaseReference.child("primarydatabase").push().setValue(getset);

                    Toast.makeText(getApplicationContext(), "Review Submitted",
                            Toast.LENGTH_LONG).show();






            }


        });*/




    }




    }
