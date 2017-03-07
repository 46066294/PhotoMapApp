package mysupercompany.photomapapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

import java.io.Serializable;

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
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Video video = (Video) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(video.getPath()), "video/*");
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, video.getPath());
                //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                //startActivityForResult(intent, 1);
                startActivity(intent);
            }
        });

        return view;
    }

}
