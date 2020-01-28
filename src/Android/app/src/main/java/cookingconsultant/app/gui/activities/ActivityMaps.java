package cookingconsultant.app.gui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cookingconsultant.app.R;

public class ActivityMaps extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final int INITIAL_REQUEST=1337;
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        linearLayout = findViewById(R.id.dummy_layout_for_snackbar);
        progressBar = findViewById(R.id.progressBar2);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }


        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(LOCATION_PERMS, INITIAL_REQUEST);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        progressBar.setVisibility(ProgressBar.VISIBLE);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            progressBar.setVisibility(ProgressBar.VISIBLE);
        } else {
            finish();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ihr GPS scheint deaktiviert zu sein. Wollen Sie es aktivieren?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(myLocation).title("Mein Standort").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(myLocation);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+location.getLatitude()+","+location.getLongitude());
        stringBuilder.append("&radius="+1200);
        stringBuilder.append("&keyword=supermarkt");
        stringBuilder.append("&key=AIzaSyCO1h8QavoazUTONOjrAYT1nG3V6ArkeNk");

        String url = stringBuilder.toString();
        Object data[] = new Object[2];
        data[0]= url;

        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
        getNearbyPlaces.execute(data);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void setMap(List<MarkerOptions> markerOptionsList){
        for(MarkerOptions markerOptions : markerOptionsList) {
            mMap.addMarker(markerOptions);
        }
    }

    public void finish(View view) {
        finish();
    }

    private class GetNearbyPlaces extends AsyncTask<Object,String,String>{


        @Override
        protected String doInBackground(Object... objects) {
            String url = (String)objects[0];

            try {
                URL myurl = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)myurl.openConnection();
                httpURLConnection.connect();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String line ="";

                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                String data = stringBuilder.toString();
                return data;
            }
            catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            try {
                List<MarkerOptions> markerOptionsList = new ArrayList<>();
                JSONObject parentObject = new JSONObject(s);
                JSONArray resultArray = parentObject.getJSONArray("results");
                int i;
                if(resultArray!=null) {
                    for (i = 0; i < resultArray.length(); i++) {
                        JSONObject jsonObject = resultArray.getJSONObject(i);
                        JSONObject locationObj = jsonObject.getJSONObject("geometry").getJSONObject("location");

                        String latitude = locationObj.getString("lat");
                        String longitude = locationObj.getString("lng");

                        JSONObject nameObj = resultArray.getJSONObject(i);

                        String nameMarkt = nameObj.getString("name");
                        String vic = nameObj.getString("vicinity");


                        LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                        MarkerOptions markerOptions = new MarkerOptions();

                        markerOptions.title(nameMarkt + " - " + vic);
                        markerOptions.position(latLng);
                        markerOptionsList.add(markerOptions);
                    }
                    setMap(markerOptionsList);
                    String string = i+" Shops in deiner Nähe gefunden";
                    if(i==0) string = "Kostenloses Limit erreicht - Bitte erneut aufrufen.";
                    Snackbar.make(linearLayout,string,Snackbar.LENGTH_LONG).show();
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                }

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
