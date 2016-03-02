package appoverview.pro.starpaid.com.locationdatamanagment;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import appoverview.pro.starpaid.com.locationdatamanagment.Utils.GeneralServices;

//package appoverview.pro.starpaid.com.locationdatamanagment.Utils;


public class PoiData extends ListActivity
{

    String[] photos_name_from_parsing  ;
    GeneralServices object_to_services  = new GeneralServices(PoiData.this);
    private ProgressDialog pDialog;
    //
////	// URL to get contacts JSON
    private static String url = "https://www.mockaroo.com/13998d00/download?count=4&key=56d09260";
//
//////// Photos names in drawable
// Array of integers points to images stored in /res/drawable-ldpi/


    // JSON Node names
    private static final String TAG_CONTACTS = "contacts";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME_PHOTO = "nameAndPhoto";
    private static final String TAG_NAME_ONLY = "name";
    private static final String TAG_PHOTO = "Photo";
    private static final String TAG_WEB_ADDRESS = "url";
    private static final String TAG_PHONE_LATITUDE = "latitude";
    private static final String TAG_PHONE_LONGITUDE = "longitude";



    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);

        contactList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();
// Listview on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                String url = ((TextView) view.findViewById(R.id.web_address))
                        .getText().toString();
                String latitude = ((TextView) view.findViewById(R.id.latitude))
                        .getText().toString();
                String longitude = ((TextView) view.findViewById(R.id.longitude))
                        .getText().toString();
                ImageView photo_view = ((ImageView) view.findViewById(R.id.image_poi));



             //   String selectedValue = (String) getListAdapter().getItem(position);

            //    Toast.makeText(PoiData.this, selectedValue, Toast.LENGTH_SHORT).show();

                //   int x = photo.getResources();
              //  Log.d("Mahmoud" ,"POP  "+ Integer.toString(x)) ;
                Log.d("Mahmoud", "POP  " + Integer.toString(R.drawable.castle)) ;
                Resources res = getResources();
                String mDrawableName = "castle";
                int resourceId = res.getIdentifier(photos_name_from_parsing[position] , "drawable", getPackageName());

              //  int resourceId = 0 ;
                // Starting single contact activity
                Intent in = new Intent(PoiData.this,
                        DetailsActivity.class);
                in.putExtra(TAG_NAME_ONLY, name);
                in.putExtra(TAG_WEB_ADDRESS, url);
               in.putExtra(TAG_PHONE_LATITUDE, latitude);
                in.putExtra(TAG_PHONE_LONGITUDE, longitude);
                in.putExtra(TAG_PHOTO , resourceId) ;
                startActivity(in);

            }
        });



        // Calling async task to get json
        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PoiData.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            Resources res = getResources();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);



            if (jsonStr != null)
            {

                try
                {
                    Log.d("Mahmoud: ", "No Object ");
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    //	JSONObject jsonObj = new JSONObject(jsonStr);
                    //	JSONArray  jsonArr =
                    // Getting JSON Array node
                    //contacts = jsonArray.getJSONArray(TAG_CONTACTS);
                    Log.d("Mahmoud: ", "No jsonArray.length() "+jsonArray.length());
                    // looping through All Contacts
                    photos_name_from_parsing  = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++)
                    {

                        JSONObject contact_obj = jsonArray.getJSONObject(i);

                        Log.d("Mahmoud: ", " No convert happend ") ;

                        //JSONObject c = contacts.getJSONObject(i);
                        //Log.d("Mahmoud: ", c.toString()) ;
                        //String id = c.getString(TAG_ID);
                        String contactStr = contact_obj.getString("contacts") ;
                        Log.d("Mahmoud: contactStr ", contactStr.toString()) ;

                        JSONObject c = new JSONObject(contactStr);

                        String nameAndPhoto = c.getString(TAG_NAME_PHOTO);
                        String url = c.getString(TAG_WEB_ADDRESS);
                        String latitude = c.getString(TAG_PHONE_LATITUDE);
                        String longitude = c.getString(TAG_PHONE_LONGITUDE);
                        //String gender = c.getString(TAG_GENDER);

                        String photo_name_from_json = object_to_services.get_photo_name_from_TAG_NAME_PHOTO(nameAndPhoto) ;
                      String title_name_from_json = object_to_services.get_title_name_from_TAG_NAME_PHOTO(nameAndPhoto);
                        String photo = object_to_services.get_image_from_drawable(photo_name_from_json) ;

                        photos_name_from_parsing[i] = photo ;
                     //  String photo = Integer.toString(poi_images[i]) ;

                        // Phone node is JSON Object
//						JSONObject phone = c.getJSONObject(TAG_PHONE);
//						String mobile = phone.getString(TAG_PHONE_LATITUDE);
//						String home = phone.getString(TAG_PHONE_HOME);
//						String office = phone.getString(TAG_PHONE_OFFICE);

                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        //contact.put(TAG_ID, id);
                        contact.put(TAG_NAME_PHOTO, title_name_from_json);
                        contact.put(TAG_WEB_ADDRESS, url);
                        contact.put(TAG_PHONE_LATITUDE, latitude);
                        contact.put(TAG_PHONE_LONGITUDE, longitude);
                        contact.put(TAG_PHOTO,  photo);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    PoiData.this, contactList,
                    R.layout.list_item, new String[] { TAG_NAME_PHOTO, TAG_WEB_ADDRESS,
                    TAG_PHONE_LATITUDE , TAG_PHONE_LONGITUDE , TAG_PHOTO}, new int[] { R.id.name,
                    R.id.web_address, R.id.latitude , R.id.longitude ,R.id.image_poi });

            setListAdapter(adapter);
        }

    }

}
