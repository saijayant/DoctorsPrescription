package com.sai_jayant.doctorprescription;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by macbookpro on 03/01/18.
 */

public class AddMedicine extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;

    ImageView plus, minus, edit_trigger, backbutton, tick_buttom;

    private AppBarLayout appBarLayout;
    private TextView medicine_name, medicine_description;
    private Spinner day_after_food, day_before_food, night_after_food, night_before_food;
    private Spinner medicine_type;


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
        setContentView(R.layout.add_medicine_child);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        initData();


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
                throwResultBack();

            }
        });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void throwResultBack() {

        if (!medicine_name.getText().toString().equalsIgnoreCase("")) {
            if (!medicine_description.getText().toString().equalsIgnoreCase("")) {

                Intent intent = new Intent();
                intent.putExtra("medicine_name", medicine_name.getText().toString());
                intent.putExtra("medicine_description", medicine_description.getText().toString());
                intent.putExtra("day_after_food", day_after_food.getSelectedItem().toString());
                intent.putExtra("day_before_food", day_before_food.getSelectedItem().toString());
                intent.putExtra("night_before_food", night_before_food.getSelectedItem().toString());
                intent.putExtra("night_after_food", night_after_food.getSelectedItem().toString());
                intent.putExtra("medicine_type", medicine_type.getSelectedItem().toString());
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Please Enter Medicine Description", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Please Enter Medicine Name", Toast.LENGTH_SHORT).show();
        }
    }


    private void initData() {
        backbutton = (ImageView) findViewById(R.id.backbutton);
        tick_buttom = (ImageView) findViewById(R.id.tick_buttom);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        medicine_name = (TextView) findViewById(R.id.medicine_name);
        medicine_description = (TextView) findViewById(R.id.medicine_description);
        day_after_food = (Spinner) findViewById(R.id.day_after_food);
        day_before_food = (Spinner) findViewById(R.id.day_before_food);
        night_after_food = (Spinner) findViewById(R.id.night_after_food);
        night_before_food = (Spinner) findViewById(R.id.night_before_food);
        medicine_type = (Spinner) findViewById(R.id.medicine_type);


        String colors[] = {"Never", "Once", "Twice", "3 Times", "4 Times", "5 Times", "6 times", "7 Time", "8 Times", "9 Time", "10 Times"};
        String type[] = {
                "Tablets ",
                "Syrups (Liquid)",
                "Pills",
                "Capsules",
                "Injectable",
                "Creams",
                "Lotions",
                "Ointment",
        };

// Selection of the spinner

// Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        day_after_food.setAdapter(spinnerArrayAdapter);
        day_before_food.setAdapter(spinnerArrayAdapter);
        night_after_food.setAdapter(spinnerArrayAdapter);
        night_before_food.setAdapter(spinnerArrayAdapter);

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        medicine_type.setAdapter(spinnerArrayAdapter1);
    }

    @Override
    public void onBackPressed() {

        finish();
    }


}

