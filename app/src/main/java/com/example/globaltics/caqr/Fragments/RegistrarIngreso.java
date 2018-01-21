package com.example.globaltics.caqr.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.globaltics.caqr.Clases.RegistrarAsistencia.RAsistencia;
import com.example.globaltics.caqr.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.kosalgeek.android.md5simply.MD5;

import java.io.IOException;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarIngreso extends Fragment {


    public RegistrarIngreso() {
        // Required empty public constructor
    }
    SurfaceView cameraView;
    BarcodeDetector barcodeDetector;
    TextView barcodeInfo;
    CameraSource cameraSource;
    String mensaje0,accion= MD5.encrypt("rae");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_ingreso, container, false);
        cameraView = (SurfaceView) view.findViewById(R.id.camera_view);
        barcodeInfo = (TextView) view.findViewById(R.id.code_info);

        barcodeDetector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource
                .Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(5312,3984)
                .setAutoFocusEnabled(true)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // verificamos la version de ANdroid que sea al menos la M para mostrar
                        // el dialog de la solicitud de la camara
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.CAMERA)) ;
                        requestPermissions(new String[]{Manifest.permission.CAMERA},225);
                    }
                    return;
                } else {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }
                /*try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 225);
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }*/
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    mensaje0 = barcodes.valueAt(0).displayValue;
                    /*mensaje0 = barcodes.valueAt(0).displayValue;
                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            barcodeInfo.setText(mensaje0);
                        }

                    });*/
                    new RAsistencia(getActivity(),urla,accion,mensaje0,barcodeInfo).execute();
                }
            }
        });
        return view;
    }

}
