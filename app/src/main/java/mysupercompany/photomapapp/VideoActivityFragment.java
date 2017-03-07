package mysupercompany.photomapapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.client.Firebase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A placeholder fragment containing a simple view.
 */
public class VideoActivityFragment extends Fragment {

    private Button bt_hacerVideo;
    private Firebase videos;
    private String videoPath;
    private String videoName;

    public VideoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_video, container, false);

        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://multimediaimgvid.firebaseio.com/");

        videos = ref.child("videos");
        bt_hacerVideo = (Button) view.findViewById(R.id.button_take_video);

        bt_hacerVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "marc");
                Log.d("ENV ", String.valueOf(Environment.getExternalStorageDirectory()));
                imagesFolder.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                videoName = "VID_" + timeStamp + ".mp4";

                File image = new File(imagesFolder, videoName);
                videoPath = image.getAbsolutePath();
                Log.d("videoPath", videoPath);
                Log.d("videoName", videoName);
                Uri uriSavedImage = Uri.fromFile(image);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                startActivityForResult(cameraIntent, 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data); 

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        final Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        Video video = new Video();
        video.setPath(videoPath);
        video.setName(videoName);
        video.setLat(location.getLatitude());
        video.setLon(location.getLongitude());
        Firebase newVideo = videos.push();
        newVideo.setValue(video);
        Log.d("video DATA", video.getPath());

        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {

            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.d("onActivityResult", "requestCode == 1 && resultCode == getActivity().RESULT_OK");
                return;
            }

        }
    }
}
