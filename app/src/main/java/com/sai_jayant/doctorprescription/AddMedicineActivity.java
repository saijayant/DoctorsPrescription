package com.sai_jayant.doctorprescription;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
    private DbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private EditText search;

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

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScroll = appBarLayout.getTotalScrollRange();
                int currentScroll = totalScroll + verticalOffset;
                int scrollRange = -1;
                boolean isShow = false;


                if ((currentScroll) < 50) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.clip_color));
                        collapsingToolbarLayout.setTitle("Trip Expense ");
                        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window w = getWindow();
                        w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                        w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                        collapsingToolbarLayout.setTitle(" ");
                        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);


                    }
                }
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
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        search = (EditText) findViewById(R.id.search);

        dbHelper = new DbHelper(this);
        sqLiteDatabase = dbHelper.getReadableDatabase();

        contactNumberLists = new ArrayList<Medicine>();
        contactNumberLists = dbHelper.GetAllData(sqLiteDatabase);

        LinearLayoutManager horizontal
                = new LinearLayoutManager(AddMedicineActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(horizontal);

        adapter1 = new MedicineAdapter(AddMedicineActivity.this, contactNumberLists);

        recyclerView.setAdapter(adapter1);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

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
                dbHelper = new DbHelper(this);
                sqLiteDatabase = dbHelper.getWritableDatabase();
                dbHelper.insertData(sqLiteDatabase, medicine_name, medicine_description, day_after_food, day_before_food,
                        night_after_food, night_before_food, "false", medicine_type);


                Medicine m = new Medicine();
                m.setMedicine_name(medicine_name);
                m.setMedicine_description(medicine_description);
                m.setDaytime_after_food(day_after_food);
                m.setDaytime_before_food(day_before_food);
                m.setNighttime_after_food(night_after_food);
                m.setNighttime_before_food(night_before_food);
                m.setMedicine_type(medicine_type);
                m.setSelected(false);


                int position = adapter1.getItemCount();
                // Add an item to animals list

                contactNumberLists.clear();

                dbHelper = new DbHelper(this);
                sqLiteDatabase = dbHelper.getReadableDatabase();

                contactNumberLists = new ArrayList<Medicine>();
                contactNumberLists = dbHelper.GetAllData(sqLiteDatabase);

//                contactNumberLists.add(position, m);
//                adapter1.notifyItemInserted(position);
//                // Scroll to newly added item position
//                recyclerView.scrollToPosition(position);
                adapter1 = new MedicineAdapter(AddMedicineActivity.this, contactNumberLists);

                recyclerView.setAdapter(adapter1);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setHasFixedSize(true);                // Show the added item label
//                    Toast.makeText(, "Added : " + city, Toast.LENGTH_SHORT).show();

                Log.d("citylist", "onActivityResult: " + medicine_name.toString());

            }

        }
    }
    void filter(String text) {
        ArrayList<Medicine> temp = new ArrayList<>();

        for (Medicine d :contactNumberLists ) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            Log.d("filter", "filter: text " + ((d.getMedicine_name()) + ""));
            Log.d("filter", "filter: serch view " + text);

            if (((d.getMedicine_name()) + "").contains(text)) {

                temp.add(d);

            }
            Log.d("filter", "filter: size " + temp.size());
            Log.d("filter", "filter: size " + temp);


        }
        //update recyclerview
        adapter1.updateList(temp);
    }
}




