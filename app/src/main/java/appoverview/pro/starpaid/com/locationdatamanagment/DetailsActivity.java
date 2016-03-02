package appoverview.pro.starpaid.com.locationdatamanagment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DetailsActivity  extends Activity
{

    // Google Map
    private GoogleMap googleMap;

	// JSON node keys
    // JSON Node names
    private static final String TAG_CONTACTS = "contacts";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME_PHOTO = "nameAndPhoto";
    private static final String TAG_NAME_ONLY = "name";
    private static final String TAG_PHOTO = "Photo";
    private static final String TAG_WEB_ADDRESS = "url";
    private static final String TAG_PHONE_LATITUDE = "latitude";
    private static final String TAG_PHONE_LONGITUDE = "longitude";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        
//        // getting intent data
        Intent in = getIntent();
//
//        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_NAME_ONLY);
        String url = in.getStringExtra(TAG_WEB_ADDRESS);
        String latitude_str = in.getStringExtra(TAG_PHONE_LATITUDE);
        String longitude_str = in.getStringExtra(TAG_PHONE_LONGITUDE);
        int  image_resource_number = in.getIntExtra(TAG_PHOTO, 0) ;
        Log.d("Mahmoud" , "getintExtra  "+ Integer.toString(image_resource_number)) ;
////        // Displaying all values on the screen
       TextView lblName = (TextView) findViewById(R.id.details_location_title);
        TextView lblWebAddress = (TextView) findViewById(R.id.details_web_address);
       TextView lblLatitude = (TextView) findViewById(R.id.details_latitude);
        TextView lblLongitude = (TextView) findViewById(R.id.details_longitude);
        ImageView img_details = (ImageView)findViewById(R.id.details_image) ;
//
        lblName.setText(name);
       lblWebAddress.setText(url);
        lblLatitude.setText(latitude_str);
        lblLongitude.setText(longitude_str);
        img_details.setImageResource(image_resource_number);
        Log.d("Mahmoud", "setImageResource is   " + Integer.toString(R.drawable.castle)) ;
     //   Log.d("Mahmoud", "setImageResource is   " + Integer.toString(R.drawable.castle)) ;

        try
        {
            // Loading map
            initilizeMap();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // create marker
        double latitude = Double.parseDouble(latitude_str);
        double longitude = Double.parseDouble(longitude_str);
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Upwork Mobile developer ");

        // ROSE color icon
        //	marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

// GREEN color icon
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

// Changing marker icon
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker_icon));

// adding marker
        googleMap.addMarker(marker);
        googleMap.setMyLocationEnabled(true); // false to disable
        googleMap.getUiSettings().setZoomControlsEnabled(true); // true to enable
    }
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap()
    {
        if (googleMap == null)
        {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.mahmoud_map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null)
            {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initilizeMap();
    }
}
