package mysupercompany.photomapapp;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {


    private MapView map;
    private MyLocationNewOverlay myLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CompassOverlay mCompassOverlay;
    private IMapController mapController;
    private RadiusMarkerClusterer photosMarkers;
    private RadiusMarkerClusterer notesMarkers;
    private RadiusMarkerClusterer videosMarkers;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        map = (MapView) view.findViewById(R.id.map);
        Log.d("MAP ", map.toString());

        initializeMap();
        setZoom();
        setOverlays();
        putMarkers();

        map.invalidate();

        return view;
    }

    private void putMarkers() {
        setupMarkerOverlay();

        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://multimediaimgvid.firebaseio.com/");


        final Firebase notes = ref.child("notes");

        notes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Note nota = postSnapshot.getValue(Note.class);
                    Log.e("NOTE", nota.toString());

                    Marker marker = new Marker(map);

                    GeoPoint point = new GeoPoint(nota.getLon(),nota.getLat());

                    marker.setPosition(point);
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    //marker.setIcon(getResources().getDrawable(R.drawable.marker_default));
                    ContextCompat.getDrawable(getActivity(), R.drawable.marker_default);
                    marker.setTitle(nota.getTitle());
                    marker.setSubDescription(nota.getMessage());
                    //marker.setAlpha(0.6f);

                    notesMarkers.add(marker);
                    marker.showInfoWindow();
                    Log.e("point", point.toString());
                    Log.e("Marker", marker.toString());
                }
                notesMarkers.invalidate();
                map.invalidate();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("The read failed: " , firebaseError.getMessage());
            }
        });

        final Firebase fotos = ref.child("fotos");

        fotos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Photo foto = postSnapshot.getValue(Photo.class);
                    Log.d("PHOTO", foto.toString());

                    Marker marker = new Marker(map);
                    GeoPoint point = new GeoPoint(foto.getLat(), foto.getLon());

                    marker.setPosition(point);
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    //marker.setIcon(getResources().getDrawable(R.drawable.marker_default));
                    ContextCompat.getDrawable(getActivity(), R.drawable.marker_default);
                    marker.setTitle(foto.getName());
                    //marker.setAlpha(0.6f);

                    photosMarkers.add(marker);
                }
                photosMarkers.invalidate();
                map.invalidate();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        final Firebase videos = ref.child("videos");

        videos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Video video = postSnapshot.getValue(Video.class);
                    Log.d("VIDEO", video.toString());

                    Marker marker = new Marker(map);
                    GeoPoint point = new GeoPoint(video.getLat(), video.getLon());

                    marker.setPosition(point);
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    //marker.setIcon(getResources().getDrawable(R.drawable.marker_default));
                    ContextCompat.getDrawable(getActivity(), R.drawable.marker_default);
                    marker.setTitle(video.getName());
                    //marker.setAlpha(0.6f);
                    Log.d("videosMarkers.isEnabled", String.valueOf(videosMarkers.isEnabled()));

                    videosMarkers.add(marker);
                }
                videosMarkers.invalidate();
                map.invalidate();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    private void setupMarkerOverlay() {
        photosMarkers = new RadiusMarkerClusterer(getContext());
        notesMarkers = new RadiusMarkerClusterer(getContext());
        videosMarkers = new RadiusMarkerClusterer(getContext());
        map.getOverlays().add(photosMarkers);
        map.getOverlays().add(notesMarkers);
        map.getOverlays().add(videosMarkers);
        //Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_default);
        Drawable clusterIconD = ContextCompat.getDrawable(getActivity(), R.drawable.marker_default_focused_base);
        Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();
        notesMarkers.setIcon(clusterIcon);
        notesMarkers.setRadius(100);
        photosMarkers.setIcon(clusterIcon);
        photosMarkers.setRadius(100);
        videosMarkers.setIcon(clusterIcon);
        videosMarkers.setRadius(100);
    }

    private void initializeMap() {
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setTilesScaledToDpi(true);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
    }

    private void setZoom() {
        mapController = map.getController();
        mapController.setZoom(10);
        GeoPoint startPoint = new GeoPoint(41.3818, 2.1685);
        mapController.setCenter(startPoint);
    }

    private void setOverlays() {
        final DisplayMetrics dm = getResources().getDisplayMetrics();

        myLocationOverlay = new MyLocationNewOverlay(
                getContext(),
                new GpsMyLocationProvider(getContext()),
                map
        );
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                mapController.animateTo(myLocationOverlay
                        .getMyLocation());
            }
        });


/*
        mMinimapOverlay = new MinimapOverlay(getContext(), map.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);
*/

        mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);

        mCompassOverlay = new CompassOverlay(
                getContext(),
                new InternalCompassOrientationProvider(getContext()),
                map
        );
        mCompassOverlay.enableCompass();

        map.getOverlays().add(myLocationOverlay);
        //map.getOverlays().add(this.mMinimapOverlay);
        map.getOverlays().add(this.mScaleBarOverlay);
        map.getOverlays().add(this.mCompassOverlay);
    }
}
