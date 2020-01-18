package com.example.panoramademo;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GoaMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private Location mlocation;
    double mlat,mlng;
    JSONArray places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goa_map);

        fetchPlacesfromDB();

        gpsTracker = new GPSTracker(getApplicationContext());
        mlocation = gpsTracker.getLocation();
        mlat = mlocation.getLatitude();
        mlng = mlocation.getLongitude();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Goa, India.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String lat,lng;
        String msg;
        if(canAccessLocation()) {
            mMap.setMyLocationEnabled(true);
            System.out.println(".................GPS permission given............");
        }

        // Add a marker in Goa and move the camera
        LatLng goa = new LatLng(15.372027, 74.116882);
        mMap.addMarker(new MarkerOptions().position(goa).title("Marker in Goa : "+15.372027+" , "+74.116882));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(goa));

        LatLng myLatLng = new LatLng(mlat,mlng);
        mMap.addMarker(new MarkerOptions().position(myLatLng).title("You are here : "+mlat+" , "+mlng));

        if(places!=null)
        for(int i=0;i<places.length();i++)
        {
            try {
                JSONObject obj  = places.getJSONObject(i);
                msg = obj.getString("message");
                if(msg.equals("Places fetched")){
                    System.out.println("....Place :: "+obj.getString("place"));
                    markLocation(obj.getString("place"),obj.getString("latitude"),obj.getString("longitude"));
                }
                else
                {
                    System.out.println("Message : " +msg);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




    }

    private boolean hasPermission(String perm) {
        System.out.println("inside hasPermission............................");
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) && hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    void markLocation(String label,String lat,String lng){
        LatLng myLatLng = new LatLng(Double.valueOf(lat),Double.valueOf(lng));
        mMap.addMarker(new MarkerOptions().position(myLatLng).title(label+" : "+lat+" , "+lng));
    }

    void fetchPlacesfromDB(){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, Constants.PLACES, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String responsemsg = null;
                System.out.println("Response length===========>"+response.length());
                places = response;
                System.out.println("PLACES :::: "+places);
                for(int i=0;i<places.length();i++)
                {
                    try {
                        JSONObject obj  = places.getJSONObject(i);
                        responsemsg = obj.getString("message");
                        if(responsemsg.equals("Places fetched")){
                            System.out.println("....Place :: "+obj.getString("place"));
                            markLocation(obj.getString("place"),obj.getString("latitude"),obj.getString("longitude"));
                        }
                        else
                        {
                            System.out.println("Message : " +responsemsg);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("..........Some error here........"+error);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
