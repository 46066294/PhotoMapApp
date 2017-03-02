package mysupercompany.photomapapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragmentAdapter extends Fragment {

    FirebaseListAdapter firebaseListAdapter;
    ListView noteList;

    public NoteFragmentAdapter() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        noteList = (ListView) view.findViewById(R.id.list);

        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://multimediaimgvid.firebaseio.com/");
        Firebase notes = ref.child("notes");

        firebaseListAdapter = new FirebaseListAdapter<Note>(getActivity(), Note.class, android.R.layout.two_line_list_item, notes) {
            @Override
            protected void populateView(View view, Note nota, int position) {
                ((TextView)view.findViewById(android.R.id.text1)).setText(nota.getTitle());
                ((TextView)view.findViewById(android.R.id.text2)).setText(nota.getMessage());
            }
        };
        noteList.setAdapter(firebaseListAdapter);

        return view;
    }

}
