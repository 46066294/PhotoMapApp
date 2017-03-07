package mysupercompany.photomapapp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteActivityFragment extends Fragment {

    private EditText etTitle;
    private EditText etDescription;
    private Firebase notas;

    public NoteActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        //FIreBase
        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://multimediaimgvid.firebaseio.com/");

        notas = ref.child("notes");

        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etDescription = (EditText) view.findViewById(R.id.etDescription);

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            //Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            //Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
            //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.e("TAG", LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                Log.e("TAG", "GPS is on");
                Note nota = new Note();
                nota.setTitle(etTitle.getText().toString());
                nota.setMessage(etDescription.getText().toString());
                //Log.d("location latitude", String.valueOf(location.getLatitude()));
                nota.setLat(location.getLatitude());
                nota.setLon(location.getLongitude());
                //nota.setLat(41.398272);
                //nota.setLon(2.2033569);
                Firebase newNota = notas.push();
                newNota.setValue(nota);
                //getActivity().finish();
                //Toast.makeText(MainActivity.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
            }
            else{
                //locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, true), 1000, 0, getContext());
                Log.e("Location", "Location is null");
            }
            return;
        }
        else Log.d("nada", "nada");
    }

    public void addNota(){

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            //Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            //Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
            //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.e("TAG", LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                Log.e("TAG", "GPS is on");
                Note nota = new Note();
                nota.setTitle(etTitle.getText().toString());
                nota.setMessage(etDescription.getText().toString());
                //Log.d("location latitude", String.valueOf(location.getLatitude()));
                nota.setLat(location.getLatitude());
                nota.setLon(location.getLongitude());
                //nota.setLat(41.398272);
                //nota.setLon(2.2033569);
                Firebase newNota = notas.push();
                newNota.setValue(nota);
                //getActivity().finish();
                //Toast.makeText(MainActivity.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
            }
            else{
                //locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, true), 1000, 0, getContext());
                Log.e("Location", "Location is null");
            }
            return;
        }
        else Log.d("nada", "nada");

        //Intent i = new Intent(getContext(), MainActivity.class);
        //startActivity(i);
    }

}
