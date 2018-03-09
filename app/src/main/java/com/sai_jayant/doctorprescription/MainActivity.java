package com.sai_jayant.doctorprescription;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout add_medicine_layout = (LinearLayout) findViewById(R.id.add_medicine_layout);
        Button add_medicine_button = (Button) findViewById(R.id.add_medicine_button);
        Button new_prescription_button = (Button) findViewById(R.id.new_prescription_button);
        Button patient_history_button = (Button) findViewById(R.id.patient_history_button);

        add_medicine_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddMedicineActivity.class);
                startActivity(i);
            }
        });

        new_prescription_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddPatientActivity.class);
                startActivity(i);
            }
        });
        patient_history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PatientHistoryActivity.class);
                startActivity(i);
            }
        });
    }
}
