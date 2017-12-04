package com.example.hooney.tailing_week_two.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hooney.tailing_week_two.CameraActivity;
import com.example.hooney.tailing_week_two.DialogFragment.AddInfoFragment;
import com.example.hooney.tailing_week_two.MainPageActivity;
import com.example.hooney.tailing_week_two.Net.SendPost;
import com.example.hooney.tailing_week_two.R;
import com.example.hooney.tailing_week_two.gridview_home.dressItem;
import com.example.hooney.tailing_week_two.gridview_home.homeGridAdapter;
import com.example.hooney.tailing_week_two.temppa.CameraSurfaceView;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static java.util.Base64.getEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    final static int SIGNAL_toGallery = 4004;
    final static int SIGNAL_toCamera = 4005;

    private View view;
    private homeGridAdapter adapter;
    private ArrayList<dressItem> list;

    private GridView gridView;


    private Button insertBTN;

    private boolean isVisible;

    private LinearLayout insertLinearLayout;
    private Button Camera;
    private Button Gallery;

    private byte[] data_byte;



    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = ((MainPageActivity)getActivity()).getList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        adapter = new homeGridAdapter(getContext(), R.layout.item_home_girdview, list, getActivity().getSupportFragmentManager());
        gridView.setAdapter(adapter);

        return view;
    }

    public void init(){
        isVisible = false; //F : 안보임 T : 보임
        gridView = (GridView) view.findViewById(R.id.home_gridview);

        insertBTN = (Button) view.findViewById(R.id.home_insert_button);

        insertLinearLayout = (LinearLayout) view.findViewById(R.id.home_insert_Layout);
        Camera = (Button) view.findViewById(R.id.home_camera_btn);
        Gallery = (Button) view.findViewById(R.id.home_gallery_btn);

        event();
    }

    public void event(){
        insertBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVisible){
                    insertLinearLayout.setVisibility(View.GONE);
                    isVisible = false;
                }else{
                    insertLinearLayout.setVisibility(View.VISIBLE);
                    isVisible = true;
                }
            }
        });

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SIGNAL_toGallery);
            }
        });

        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CameraActivity.class);
                startActivityForResult(intent, SIGNAL_toCamera);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int id, long position) {
                Toast.makeText(getContext(), "ID : "+ id, Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                AddInfoFragment fragment = new AddInfoFragment();
                fragment.setArguments(bundle);

                fragment.show(getActivity().getSupportFragmentManager(), "AddInfoFragment");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGNAL_toGallery){
            if(resultCode == Activity.RESULT_OK){
                data_byte = data.getByteArrayExtra("data");
                Log.d("Imgae Data : " , "Data : " + data.getData());
                Log.d("Imgae Data : " , "Byte : " + data_byte);

                parser(data.getDataString());

                dressItem di = new dressItem();
                di.setSeason(new int[]{1,2});
                di.setImgURL(data.getData()+""); //스트링을 넣기
                di.setDressName("Dress " +(list.size()));
                list.add(di);
                adapter.notifyDataSetChanged();
                ((MainPageActivity)getActivity()).setList(list);

            }else{
                //실패
            }
            showUpLayout();
        }else if(requestCode == SIGNAL_toCamera){
            if(resultCode == Activity.RESULT_OK){
                Log.d("Camera Data Set", "Camera url : " + data.getStringExtra("URI").toString());
                data_byte = data.getByteArrayExtra("data");
                Log.d("Imgae Data : " , "Data : " + data.getData());
                Log.d("Imgae Data : " , "Byte : " + data_byte);
                dressItem di = new dressItem();
                di.setSeason(new int[]{1,2});
                di.setImgURL(data.getStringExtra("URI").toString()); //스트링을 넣기









                di.setDressName("Dress " +(list.size()));
                list.add(di);
                adapter.notifyDataSetChanged();
                ((MainPageActivity)getActivity()).setList(list);
            }else{
                //실패
            }
            showUpLayout();
        }else{

        }
    }

    public void showUpLayout(){
        if(isVisible){
            insertLinearLayout.setVisibility(View.GONE);
            isVisible = false;
        }else{
            insertLinearLayout.setVisibility(View.VISIBLE);
            isVisible = true;
        }
    }

    public void doFileUpload() {
        DataOutputStream dos = null;
        try {
            URL url = new URL("http://localhost:65005/upload");
            Log.i("doFileUpload", "http://localhost/upload" );
            // open connection
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setDoInput(true); //input 허용
            con.setDoOutput(true);  // output 허용
            con.setUseCaches(false);   // cache copy를 허용하지 않는다.
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");

            // write data
            dos = new DataOutputStream(con.getOutputStream());
            Log.i("doFileUpload", "Open OutputStream" );

        } catch (Exception e) {
            Log.i("doFileUpload", "exception " + e.getMessage());
            // TODO: handle exception
        }
        Log.i("doFileUpload", data_byte.length+"bytes written successed ... finish!!" );
        try { dos.close(); } catch(Exception e){}

    }

    private void parser(String data){
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(data));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            final String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

            Log.d("Image Base64 Byte", "BASE 64 : " + imageEncoded);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    new SendPost("uploadimg.php", "?image=", imageEncoded).SendPost();
                }
            });

            t.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
