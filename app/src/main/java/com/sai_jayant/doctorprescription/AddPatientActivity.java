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

                if (!patient_name.getText().toString().equalsIgnoreCase("")) {


                    if (!age.getText().toString().equalsIgnoreCase("")) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy hh.mm.aa");
                        String formattedDate = dateFormat.format(new Date()).toString();
                        System.out.println(formattedDate);
                        date_now = formattedDate;
                        String med = "";
                        if (contactNumberLists.size() > 0) {
                            med = "";
                            int count=0;
                            for (int i = 0; i < contactNumberLists.size(); i++) {
                                if (contactNumberLists.get(i).getSelected() == true) {

                                    int pos = count + 1;

                                    med = med + "\n\nMedicine " + pos + "---" + "" + contactNumberLists.get(i).getMedicine_name() + " (" + contactNumberLists.get(i).getMedicine_type() + ")" + "\n\n" + "Before food (Morning) - " + contactNumberLists.get(i).getDaytime_before_food() + "  " + "\nAfter lunch - " + contactNumberLists.get(i).getDaytime_after_food() + "\nBefore food (Evening) - " + contactNumberLists.get(i).getNighttime_after_food() + "\nAfter dinner - " + contactNumberLists.get(i).getNighttime_after_food() + " \n...................................................................";
                                }
                            }
                        }


                        dbHelper.insertPatientDetails(sqLiteDatabase, patient_name.getText().toString(),
                                gender.getSelectedItem().toString(), age.getText().toString(), weight.getText().toString(),
                                number.getText().toString(), address.getText().toString(), date_now, med);

                        Toast.makeText(AddPatientActivity.this, "Patient dat saved ", Toast.LENGTH_SHORT).show();
                        finish();

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

        adapter1 = new MedicineAdapter(getApplicationContext(), contactNumberLists);

        recyclerView.setAdapter(adapter1);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);


    }


}


