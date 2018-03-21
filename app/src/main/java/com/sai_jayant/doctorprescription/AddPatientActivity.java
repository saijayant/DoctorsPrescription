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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
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
    private EditText patient_name, weight, age, number, address;
    RecyclerView medicineList;
    Spinner gender;
    FloatingActionButton panic_fab;
    private DbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String date_now;
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

        setContentView(R.layout.add_patient_child);
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

                int count = 0;

                if (!patient_name.getText().toString().equalsIgnoreCase("")) {


                    if (!age.getText().toString().equalsIgnoreCase("")) {

                        if (!age.getText().toString().equalsIgnoreCase("")) {

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy hh.mm.aa");
                            String formattedDate = dateFormat.format(new Date()).toString();
                            System.out.println(formattedDate);
                            date_now = formattedDate;
                            String med = "";


                            if (contactNumberLists.size() > 0) {
                                med = "";
                                for (int i = 0; i < contactNumberLists.size(); i++) {
                                    if (contactNumberLists.get(i).getSelected() == true) {

                                        count = count + 1;

                                        med = med + "\n" + contactNumberLists.get(i).getMedicine_name() + " (" + contactNumberLists.get(i).getMedicine_type() + ")" + "\n\n" + contactNumberLists.get(i).getDosages() + "  " + contactNumberLists.get(i).getMedicine_type() + " " + ", " + contactNumberLists.get(i).getFrequency() + "  " + " For " + contactNumberLists.get(i).getDays() + "  " + "\nTake this medicine  " + contactNumberLists.get(i).getFood() + "  " + " \n ........................................................................\n";

                                        String medd = med;
                                    } else {
                                    }
                                }
                            }

                            if (count > 0) {
                                dbHelper.insertPatientDetails(sqLiteDatabase, patient_name.getText().toString(),
                                        gender.getSelectedItem().toString(), age.getText().toString(), weight.getText().toString(),
                                        number.getText().toString(), address.getText().toString(), date_now, med);

                                Toast.makeText(AddPatientActivity.this, "Patient dat saved ", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddPatientActivity.this, "No medicine is selected please select at least one Medicine .", Toast.LENGTH_SHORT).show();

                            }
                        }


                    } else {
                        Toast.makeText(AddPatientActivity.this, "Enter age", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AddPatientActivity.this, "Enter Patient name", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void intUi() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        patient_name = (EditText) findViewById(R.id.patient_name);
        weight = (EditText) findViewById(R.id.weight);
        age = (EditText) findViewById(R.id.age);
        number = (EditText) findViewById(R.id.number);
        address = (EditText) findViewById(R.id.address);
        search = (EditText) findViewById(R.id.search);
        gender = (Spinner) findViewById(R.id.gender);
        tick_buttom = (ImageView) findViewById(R.id.tick_buttom);
        recyclerView = (RecyclerView) findViewById(R.id.medicineList);
        dbHelper = new DbHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        String type[] = {
                "Male ",
                "Female",
                "Others",

        };


        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(this, R.layout.single_liner_show, type);
        spinnerArrayAdapter1.setDropDownViewResource(R.layout.single_liner); // The drop down view
        gender.setAdapter(spinnerArrayAdapter1);

        dbHelper = new DbHelper(this);
        sqLiteDatabase = dbHelper.getReadableDatabase();

        contactNumberLists = new ArrayList<Medicine>();
        contactNumberLists = dbHelper.GetAllData(sqLiteDatabase);

        LinearLayoutManager horizontal
                = new LinearLayoutManager(AddPatientActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(horizontal);

        adapter1 = new MedicineAdapter(AddPatientActivity.this, contactNumberLists);

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


    void filter(String text) {
        ArrayList<Medicine> temp = new ArrayList<>();

        for (Medicine d : contactNumberLists) {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == 5) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null) {
                String medicine_name = data.getStringExtra("medicine_name");
                String medicine_description = data.getStringExtra("medicine_description");
                String medicine_type = data.getStringExtra("medicine_type");

                String setFood = data.getStringExtra("setFood");
                String setDosages = data.getStringExtra("setDosages");
                String setFrequency = data.getStringExtra("setFrequency");
                String setDays = data.getStringExtra("setDays");
                String from_cos = data.getStringExtra("from_cos");
                String med_id = data.getStringExtra("med_id");

                int position = data.getIntExtra("position", 0);


                if (from_cos.equalsIgnoreCase("daily_dosages")) {
                    setDosages = data.getStringExtra("item");
                } else if (from_cos.equalsIgnoreCase("frequency")) {
                    setFrequency = data.getStringExtra("item");

                } else if (from_cos.equalsIgnoreCase("cycle")) {
                    setDays = data.getStringExtra("item");


                } else if (from_cos.equalsIgnoreCase("food_habbit")) {
                    setFood = data.getStringExtra("item");
                }


                Medicine m = new Medicine();
                m.setMedicine_name(medicine_name);
                m.setMedicine_description(medicine_description);
                m.setMedicine_type(medicine_type);
                m.setSelected(false);
                m.setFood(setFood);
                m.setDosages(setDosages);
                m.setFrequency(setFrequency);
                m.setDays(setDays);
                m.setMed_id(med_id);


                for (int i = 0; i < contactNumberLists.size(); i++) {
                    if (med_id.equalsIgnoreCase(contactNumberLists.get(i).getMed_id())) {
                        position = i;
                    }
                }

                contactNumberLists.remove(position);
                contactNumberLists.add(position, m);
                adapter1.notifyItemInserted(position);
                adapter1.notifyDataSetChanged();

            }

        }
    }
}


