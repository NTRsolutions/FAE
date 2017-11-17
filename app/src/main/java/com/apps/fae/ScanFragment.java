package com.apps.fae;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public  class ScanFragment extends Fragment {
    DecoratedBarcodeView barcodeView;
    private RequestQueue mQueue;
    public ScanFragment() {
    }
    static final int REQUEST_VIDEO_CAPTURE = 1;

    static final int REQUEST_IMAGE_CAPTURE = 2;

    static final int REQUEST_Voice_CAPTURE = 3;

    static final int REQUEST_Photo_CAPTURE = 4;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ScanFragment newInstance() {
        return new ScanFragment();
    }

    public void OpenFlash(boolean on)
    {
        if(on==true){
            barcodeView.setTorchOn();}
        else{
            barcodeView.setTorchOff();
    }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_scanning, container, false);

        int permission = ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
//            // 無權限，向使用者請求
//            ActivityCompat.requestPermissions(
//                    this,
//                    new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE
//            );


        } else {

            permission = ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA);

            if (permission != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{android.Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE
                );

            } else {
                barcodeView = (DecoratedBarcodeView)rootView.findViewById(R.id.barcode_view);
                barcodeView.decodeContinuous(new BarcodeCallback() {
                    @Override
                    public void barcodeResult(BarcodeResult result) {

                    }

                    @Override
                    public void possibleResultPoints(List<ResultPoint> resultPoints) {

                    }
                });

                barcodeView.decodeContinuous(new BarcodeCallback() {
                    @Override
                    public void barcodeResult(BarcodeResult result) {


                        Search_Project(result.getText());

                        barcodeView.pause();
//                String scanContent = scanningResult.getContents();
//                String scanFormat = scanningResult.getFormatName();
                    }

                    @Override
                    public void possibleResultPoints(List<ResultPoint> resultPoints) {

                    }
                });
            }
        }


        return rootView;
    }

    private void Search_Project(String QR_Content) {


        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(getContext());
        }

        String Path ="http://wtsc.msi.com.tw/IMS/FAE_App_Service.asmx/Find_Model_Info?SN=" + QR_Content;

        GetServiceData.getString(Path, mQueue, new GetServiceData.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {


                Go_To_New_Issue(result.toString());
            }
        });
    }
    private void Go_To_New_Issue(String JsonString) {
        try {
            JSONObject QR_Object = new JSONObject(JsonString);

            JSONArray ProjectArray = new JSONArray(QR_Object.getString("Key"));

            if (JsonString.length() > 0) {


                JSONObject ProjectData = ProjectArray.getJSONObject(0);

                String model = ProjectData.getString("model");

                String marketName = ProjectData.getString("marketName");

                Bundle bundle = new Bundle();

                bundle.putString("model", model);

                bundle.putString("marketName", marketName);

                Intent intent = new Intent(getContext(), NewIssue.class);

                intent.putExtras(bundle);

                startActivity(intent);
            }

        } catch (JSONException ex) {

        }


    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(barcodeView != null) {
            if (isVisibleToUser) {
                barcodeView.resume();
            } else {
                barcodeView.pauseAndWait();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pauseAndWait();
    }

    @Override
    public void onResume() {
        super.onResume();
        barcodeView.resume();
    }
}
