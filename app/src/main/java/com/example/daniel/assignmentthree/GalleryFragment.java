package com.example.daniel.assignmentthree;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class GalleryFragment extends Fragment {
    // Keep all Images in array
    CapturePicFragment capture;
    GridView gridView;
    ImageView imageview;
    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery2, container, false);
        this.capture = new CapturePicFragment();
        gridView = (GridView) view.findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(getActivity()));
        imageview = (ImageView) view.findViewById(R.id.imageviewer);
        listener();
        return view;
    }

    private void listener() {
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 imageview.setImageBitmap(capture.retrieveImage());

            }
        });
    }


}


