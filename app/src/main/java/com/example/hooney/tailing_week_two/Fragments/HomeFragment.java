package com.example.hooney.tailing_week_two.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hooney.tailing_week_two.MainPageActivity;
import com.example.hooney.tailing_week_two.R;
import com.example.hooney.tailing_week_two.gridview_home.dressItem;
import com.example.hooney.tailing_week_two.gridview_home.homeGridAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    final static int SIGNAL_toGallery = 4004;

    private View view;
    private homeGridAdapter adapter;
    private ArrayList<dressItem> list;

    private GridView gridView;


    private Button insertBTN;
    private boolean isVisible;
    private LinearLayout insertLinearLayout;

    private Button Camera;
    private Button Gallery;

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
        adapter = new homeGridAdapter(getContext(), R.layout.item_home_girdview, list);
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGNAL_toGallery){
            if(resultCode == Activity.RESULT_OK){
                dressItem di = new dressItem();
                di.setImgURL(data.getData()+""); //스트링을 넣기
                di.setDressName("Dress " +(list.size()));
                list.add(di);
                adapter.notifyDataSetChanged();
                ((MainPageActivity)getActivity()).setList(list);
            }else{
                //실패
            }
        }else{

        }
    }
}
