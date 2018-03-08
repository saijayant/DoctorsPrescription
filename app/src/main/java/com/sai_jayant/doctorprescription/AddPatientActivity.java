package com.sai_jayant.doctorprescription;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Preet on 1/12/18.
 */

public class AddPatientActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 23;
    static final int OPEN_MEDIA_PICKER = 1;  // Request code
    private static final int PICK_FROM_GALLERY = 2;
    RecyclerView recyclerView;
    List<String> selectionResult;
    Button z;
    static WifiManager wifiManager;

    String[] s;
    private MedicineAdapter mAdapter;
    private TextView no_plans;
    private ImageView tick_buttom;
    private static final int PERMISSION_REQUEST_CONTACT = 5;

    private int RESULT_PICK_CONTACT = 9;
    private ArrayList<Medicine> contactNumberLists;
    private MedicineAdapter adapter1;
    private LocationManager manager;
    private LocationManager locationManager;
    private LocationListener myLocationListener;
    private Location location;
    private double longitude, latitude;
    private int MY_PERMISSIONS_REQUEST_LOCATION = 91;
    private Location mLocation;
    private boolean GpsStatus;
    String Holder;
    private Criteria criteria;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    private LocationManager locManager;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 8;
    private String Addresses;
    private double Lati;
    private double Long;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panic_layout);



            z = (Button) findViewById(R.id.select_img);
        tick_buttom = (ImageView) findViewById(R.id.tick_buttom);
        FloatingActionButton panic_fab = (FloatingActionButton) findViewById(R.id.panic_fab);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);


        contactNumberLists = new ArrayList<Medicine>();

        LinearLayoutManager horizontal
                = new LinearLayoutManager(AddPatientActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(horizontal);

        adapter1 = new MedicineAdapter(getApplicationContext(), contactNumberLists);

        recyclerView.setAdapter(adapter1);



        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        tick_buttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contactNumberLists.size() > 0) {


                }
            }
        });

        panic_fab.setOnClickListener(new View.OnClickListener() {
                                         @TargetApi(Build.VERSION_CODES.M)
                                         @Override
                                         public void onClick(View v) {

                                         }}
        );


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == OPEN_MEDIA_PICKER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null) {

                ArrayList<String> result = data.getStringArrayListExtra("result");

                if (selectionResult.size() + result.size() <= 20) {


                    String s;

                    for (int i = 0; i < result.size(); i++) {
                        int n = selectionResult.size() + 1;
                        s = result.get(i);


                        InputStream is = null;


                        Log.d("image", "onActivityResult: path " + s);
                        Log.d("image", "onActivityResult: " + s);
                        Log.d("image", "onActivityResult: mAdapter.getItemCount()+i " + mAdapter.getItemCount() + i);
                        selectionResult.add(mAdapter.getItemCount(), s);
                        mAdapter.notifyItemInserted(mAdapter.getItemCount());
                    }


                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Max limit is 20 ", Toast.LENGTH_LONG).show();
                }

                Log.d("media", "onActivityResult: " + selectionResult.toString());



            }
        }


        if (requestCode == RESULT_PICK_CONTACT) {
            if (resultCode == RESULT_OK) {


            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("User closed the picker without selecting items.");
            }
        }

        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && null != data) {


        }
    }


}


