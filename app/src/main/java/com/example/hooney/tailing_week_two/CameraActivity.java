package com.example.hooney.tailing_week_two;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hooney.tailing_week_two.Fragments.HomeFragment;
import com.example.hooney.tailing_week_two.R;
import com.example.hooney.tailing_week_two.temppa.CameraSurfaceView;

public class CameraActivity extends AppCompatActivity {
    private FrameLayout previewFrame;
    private Button takeBTN;
    private Button saveBTN;
    private Button retakeBTN;
    private final CameraSurfaceView cameraView = new CameraSurfaceView(getApplicationContext());

    private boolean isCameraRunning;
    private String outUriStr;

    private Camera cntCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        init();

        previewFrame.addView(cameraView);
        event();

    }

    private void init(){
        isCameraRunning = true;
        previewFrame = (FrameLayout) findViewById(R.id.previewFrame);
        takeBTN = (Button) findViewById(R.id.Camera_takeBTN);
        saveBTN = (Button) findViewById(R.id.Camera_saveBTN);
        retakeBTN = (Button) findViewById(R.id.Camera_retakeBTN);
    }

    private void event(){
        takeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView.capture(new Camera.PictureCallback(){

                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        try{
                            cntCamera = camera;
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            outUriStr = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
                                    "Captured Image", "Captured Image using Camera");

                            if(outUriStr == null){
                                Toast.makeText(getApplicationContext(), "카메라 이미지를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                                Log.e("Camera Activity", "Camera Image Error");
                                return;
                            }else{
                                Uri uri = Uri.parse(outUriStr);
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                                Toast.makeText(getApplicationContext(), "갤러리 저장 완료!", Toast.LENGTH_SHORT).show();
                                isCameraRunning =false;
                            }
                        }catch (Exception e){
                            Log.e("Camera Activity", "Camera Capture Error~~!!!", e);
                        }
                    }
                });
            }
        });

        retakeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCameraRunning){
                    Toast.makeText(getApplicationContext(), "카메라가 실행되고 있습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    cntCamera.startPreview();
                }
            }
        });

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeFragment.class);
                intent.putExtra("URI", outUriStr);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
