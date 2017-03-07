package mysupercompany.photomapapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragmentAdapter extends Fragment {

    FirebaseListAdapter mAdapter;
    ListView photoList;

    public PhotoFragmentAdapter() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        photoList = (ListView) view.findViewById(R.id.list);

        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://multimediaimgvid.firebaseio.com/");

        final Firebase fotos = ref.child("fotos");

        mAdapter = new FirebaseListAdapter<Photo>(getActivity(), Photo.class, R.layout.photo_row, fotos) {
            @Override
            protected void populateView(View view, Photo photo, int position) {
                ((TextView)view.findViewById(R.id.tvRuta)).setText(photo.getName());

                Glide
                        .with(getContext())
                        .load(photo.getPath())
                        .into(((ImageView)view.findViewById(R.id.iFoto)));

                //Log.d("load Photo", photo.getPath());
            }
        };
        photoList.setAdapter(mAdapter);


        return view;
    }


}
