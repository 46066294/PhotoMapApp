package mysupercompany.photomapapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A placeholder fragment containing a simple view.
 */
public class VideoDetailActivityFragment extends Fragment {

    public VideoDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_detail, container, false);

        Intent i = getActivity().getIntent();
        Bundle bundle = i.getExtras();
        Log.d("Bundle", bundle.toString());
        String video = (String) i.getSerializableExtra("videoPath");

        if (video != null) {
            Log.d("VIDEO", video);
            i.setDataAndType(Uri.parse(video), "video/*");
        }

        return view;
    }
}
