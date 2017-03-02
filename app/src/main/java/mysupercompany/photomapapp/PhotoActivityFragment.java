package mysupercompany.photomapapp;

import android.Manifest;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.io.File;
import java.io.FileNotFoundException;
import cz.msebera.android.httpclient.Header;

/**
 * A placeholder fragment containing a simple view.
 */
public class PhotoActivityFragment extends Fragment {

    private Button bt_hacerfoto;
    private Firebase fotos;
    private String fotopath;
    private String fotoname;

    public PhotoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://multimediaimgvid.firebaseio.com/");

        fotos = ref.child("fotos");

        bt_hacerfoto = (Button) view.findViewById(R.id.button1);

        //apunts clase
        bt_hacerfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "marc");
                Log.d("ENV ", String.valueOf(Environment.getExternalStorageDirectory()));
                imagesFolder.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                fotoname = "IMG_" + timeStamp + ".jpg";

                File image = new File(imagesFolder, fotoname);
                fotopath = image.getAbsolutePath();
                Log.d("fotopath", fotopath);
                Log.d("fotoname", fotoname);
                Uri uriSavedImage = Uri.fromFile(image);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                startActivityForResult(cameraIntent, 1);
            }
        });
        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            final Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            AsyncHttpClient client = new AsyncHttpClient();

            File file = new File(fotopath);
            RequestParams params = new RequestParams();
            try {
                params.put("UploadPhoto", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            client.post("https://multimediaimgvid.firebaseio.com/fotos/", params, new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.print("Failed..");
                    getActivity().finish();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    System.out.print("Success..");
                    String ruta = new String(responseBody);
                    Photo foto = new Photo();
                    foto.setPath(ruta);
                    foto.setName(fotoname);
                    foto.setLat(location.getLatitude());
                    foto.setLon(location.getLongitude());
                    Firebase newNota = fotos.push();
                    newNota.setValue(foto);
                    getActivity().finish();
                }
            });
        }
    }
}
