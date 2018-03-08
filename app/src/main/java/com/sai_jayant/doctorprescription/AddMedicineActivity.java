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
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class AddMedicineActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;

    RecyclerView recyclerView;
    private ImageView tick_buttom;
    private ArrayList<Medicine> contactNumberLists;
    private MedicineAdapter adapter1;
    FloatingActionButton panic_fab;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        }


        setContentView(R.layout.add_medicine);


        intUi();





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

                                             Intent i = new Intent(AddMedicineActivity.this, AddMedicine.class);
                                             startActivityForResult(i, 5);

                                         }
                                     }
        );


    }

    private void intUi() {
        tick_buttom = (ImageView) findViewById(R.id.tick_buttom);
        panic_fab = (FloatingActionButton) findViewById(R.id.panic_fab);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);


        contactNumberLists = new ArrayList<Medicine>();

        LinearLayoutManager horizontal
                = new LinearLayoutManager(AddMedicineActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(horizontal);

        adapter1 = new MedicineAdapter(getApplicationContext(), contactNumberLists);

        recyclerView.setAdapter(adapter1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == 5) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null) {
                String medicine_name = data.getStringExtra("medicine_name");
                String medicine_description = data.getStringExtra("medicine_description");
                String day_after_food = data.getStringExtra("day_after_food");
                String day_before_food = data.getStringExtra("day_before_food");
                String night_before_food = data.getStringExtra("night_before_food");
                String night_after_food = data.getStringExtra("night_after_food");
                String medicine_type = data.getStringExtra("medicine_type");


                Medicine m = new Medicine();
                m.setMedicine_name(medicine_name);
                m.setMedicine_description(medicine_description);
                m.setDaytime_after_food(day_after_food);
                m.setDaytime_before_food(day_before_food);
                m.setNighttime_after_food(night_after_food);
                m.setNighttime_before_food(night_before_food);
                m.setMedicine_type(medicine_type);
                m.setSelected(true);


                int position = adapter1.getItemCount();
                // Add an item to animals list

                contactNumberLists.add(position, m);
                adapter1.notifyItemInserted(position);
                // Scroll to newly added item position
                recyclerView.scrollToPosition(position);
                adapter1.notifyDataSetChanged();
                // Show the added item label
//                    Toast.makeText(, "Added : " + city, Toast.LENGTH_SHORT).show();

                Log.d("citylist", "onActivityResult: " + medicine_name.toString());

            }

        }
    }

}




