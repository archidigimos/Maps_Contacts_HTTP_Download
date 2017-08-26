package com.example.archismansarkar.mapsandcontacts;

import android.app.Activity;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MapsContactsActivity extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    public ContactsManipulation cm = new ContactsManipulation();
    public static final String PATH_TO_SERVER = "http://www.cs.columbia.edu/~coms6998-8/assignments/homework2/contacts/contacts.txt";

    public static String h;
    public static File filepath;
    public static FileWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_contacts);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DownloadFilesTask downloadFilesTask = new DownloadFilesTask();
        downloadFilesTask.execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
}

    private class DownloadFilesTask extends AsyncTask<URL, Void, String> {
        protected String doInBackground(URL... urls) {
            return downloadRemoteTextFileContent();
        }
        protected void onPostExecute(String result) {
            if(!TextUtils.isEmpty(result)){
                Log.d("URLfetch", result);
                String[] lines = result.split("&");
                int length = lines.length;
                for(int x=0;x<=length-1;x++){
                    String[] parts = lines[x].split(" ");
                    double latitude = Double.parseDouble(parts[2])/1000000;
                    double longitude = Double.parseDouble(parts[3])/1000000;

                    LatLng user = new LatLng(latitude,longitude);
                    mMap.addMarker(new MarkerOptions().position(user).title(parts[0]+", "+"E-Mail ID: "+parts[1]));
                }
            }
        }
    }
    private String downloadRemoteTextFileContent(){
        URL mUrl = null;
        String content = "";
        try {
            mUrl = new URL(PATH_TO_SERVER);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert mUrl != null;
            URLConnection connection = mUrl.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";

            h = DateFormat.format("MM-dd-yyyyy-h-mmssaa", System.currentTimeMillis()).toString();
            File root = new File(Environment.getExternalStorageDirectory(), "Contact_Log");
            if (!root.exists()) {
                root.mkdirs();
            }
            filepath = new File(root, h + ".txt");
            try{
                writer = new FileWriter(filepath);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            while((line = br.readLine()) != null){
                String[] parts = line.split(" ");
                cm.addContact(getApplicationContext(),parts[0],parts[3],parts[2],"",parts[1],"","","");
                content = content + line + "&";

                try {
                    writer.append(line+ "\n");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            br.close();
            try {
                writer.flush();
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
