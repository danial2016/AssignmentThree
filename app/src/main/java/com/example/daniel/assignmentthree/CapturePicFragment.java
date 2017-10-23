package com.example.daniel.assignmentthree;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/*
    ansambassamabdulhamid

    User capturing pictures. Pictures being saved in the database.
 */
public class CapturePicFragment extends Fragment {
    private Button btnCapturePic;
    private ImageView ivTaken;
    private Uri pictureUri;
    private int CAMERA_CAPTURE_IMAGE_REQUEST = 100;
    private String TAG = "CapturePicFragment";
    private DatabaseHelper myDb;

    public CapturePicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_capture_pic, container, false);
        btnCapturePic = (Button) view.findViewById(R.id.button);
        ivTaken = (ImageView) view.findViewById(R.id.ivTake);
        this.myDb = new DatabaseHelper(getActivity());
        if (savedInstanceState != null) {
            pictureUri = savedInstanceState.getParcelable("Uri");
        }
        btnCapturePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pictureUri != null) {
            String pathToPicture = pictureUri.getPath();
            ivTaken.setImageBitmap(getScaled(pathToPicture, 100, 100));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("Uri", pictureUri);
    }

    /* In here the camera is launched, and the user is able to take a picture */
    public void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File path = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            pictureUri = Uri.fromFile(new File(path, "PHOTOS"));
            Log.d(TAG, "Picture  " + pictureUri.getPath());
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
            startActivityForResult(takePictureIntent, CAMERA_CAPTURE_IMAGE_REQUEST);
            Toast.makeText(getActivity(), "I am to take a picture!", Toast.LENGTH_SHORT).show();
        }
    }

    /* När du ska hämta bilder får du omvandla från sträng till Uri
     * Så här:
     *             s = strängen du hämtar från databasen med getString och columnIndex
     *          Uri mUri = Uri.parse(s)
      *          pictureUri = mUri
      *          String myPic = mUri.getPath();
      *
      *          myPic innehåller dina bilder, du väljer sen hur du ska visa dem....*/

    @Override
    /*  */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CAMERA_CAPTURE_IMAGE_REQUEST) && (resultCode == RESULT_OK)) {
            /* Path holding the pictures  */
            String pathToPicture = pictureUri.getPath();
            /* Getting the Uri in string format */
            String s = pictureUri.toString();
            /* Adding taken pictures to the database  */
            myDb.insertImageToMyDb(s);
            /* setting the already existing imageView with the new captured one */
            ivTaken.setImageBitmap(getScaled(pathToPicture, 100, 100));
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity(), "User canceled image capturing", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to capture image", Toast.LENGTH_SHORT).show();
        }
    }

    /* Resizing the resulting bitmap */
    public Bitmap getScaled(String pathToPic, int targetWidth, int targetHeight) {

        //getting the dimensions of the bitmap
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathToPic, bitmapOptions);
        int photoWidth = bitmapOptions.outWidth;
        int photoHeight = bitmapOptions.outHeight;

        //determining how much to scale the pic
        int scaleFactor = Math.min(photoWidth / targetWidth, photoHeight / targetHeight);

        // Decode the image file into a Bitmap sized to fill the View
        bitmapOptions.inJustDecodeBounds = false;
        bitmapOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(pathToPic, bitmapOptions);
        return bitmap;
    }
}
