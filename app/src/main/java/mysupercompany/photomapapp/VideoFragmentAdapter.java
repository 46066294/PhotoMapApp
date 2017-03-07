package mysupercompany.photomapapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragmentAdapter extends Fragment {

    FirebaseListAdapter mAdapter;
    ListView videoList;

    public VideoFragmentAdapter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        videoList = (ListView) view.findViewById(R.id.list);

        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://multimediaimgvid.firebaseio.com/");

        final Firebase videos = ref.child("videos");

        mAdapter = new FirebaseListAdapter<Video>(getActivity(), Video.class, R.layout.video_row, videos) {
            @Override
            protected void populateView(View view, Video video, int position) {
                ((TextView)view.findViewById(R.id.tvRuta)).setText(video.getName());

                //Log.d("load Video", video.getPath());
            }
        };
        videoList.setAdapter(mAdapter);


        return view;
    }

}
