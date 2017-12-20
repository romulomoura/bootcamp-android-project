package crats.mvcbaseproject.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import android.util.Log;
import android.provider.ContactsContract.Profile;
import android.widget.TextView;
import android.widget.Toast;

import crats.mvcbaseproject.R;

/**
 * Created by yashshah on 2017-12-09.
 */

public class RetriveReview extends AppCompatActivity
{


    SurfaceView cameraPreview7;
    TextView txtResult7;
    BarcodeDetector barcodeDetector7;
    CameraSource cameraSource7;
    ListView listView;
    Button b1;
    private ArrayList<String> mListview = new ArrayList<String>();

    ArrayAdapter<String> myArrayAdapter;



    final int RequestCamerePermissionID = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrivereview);



        //listView = (ListView)findViewById(R.id.listview);
        //b1 = (Button)findViewById(R.id.button2);
        myArrayAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, R.id.rowTextView, mListview);
//        listView.setAdapter(myArrayAdapter);

        cameraPreview7 = (SurfaceView) findViewById(R.id.surfaceView2);
        txtResult7 = (TextView) findViewById(R.id.textView7);
        barcodeDetector7 = new BarcodeDetector.Builder(this).build();
        cameraSource7 = new CameraSource.Builder(this, barcodeDetector7).setRequestedPreviewSize(1080, 880).build();

        cameraPreview7.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RetriveReview.this,new String[]{Manifest.permission.CAMERA}, RequestCamerePermissionID);
                    return;
                }
                try {
                    cameraSource7.start(cameraPreview7.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

                cameraSource7.stop();

            }
        });

        barcodeDetector7.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();

                if(qrcodes.size() != 0)
                {
                    txtResult7.post(new Runnable() {
                        @Override
                        public void run() {

                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            txtResult7.setText(qrcodes.valueAt(0).displayValue);
                            cameraSource7.stop();
                            Intent intentyash = new Intent(RetriveReview.this, crats.mvcbaseproject.view.ListView.class);
                            Bundle extras = new Bundle();
                            extras.putString("qrcode",qrcodes.valueAt(0).displayValue);
                            intentyash.putExtras(extras);

                            startActivity(intentyash);



                        }
                    });
                }
            }
        });

















    }



}
