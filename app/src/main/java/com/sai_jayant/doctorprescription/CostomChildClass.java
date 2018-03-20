package com.sai_jayant.doctorprescription;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by macbookpro on 20/03/18.
 */

public class CostomChildClass extends Activity {
    String from;
    ArrayAdapter adapter;
    // Array of strings...
    String daily_dosages[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "11", "12", "13", "14", "15","16","17","18","19"};
    String frequency[] = {"Never", "Once Daily(OD)", "Twice Daily(TD)", "3 Times Daily", "4 Times Daily", "6 Times Daily", "7 Times Daily", "8 Times Daily", "9 Times Daily", "10 Times Daily", "Every 2 hour", "Every 3 hour"};
    String cycle[] = {"One Day", "Two Day", "3 day", "4 day", "One Week", "Two Week", "1 Month", "2 Months", "3 Months", "6 Month", "9 Month", "1 year", "2 Year", "Always"};
    private String medicine_name, medicine_description, medicine_type, setFood, setDosages, setFrequency, setDays;
    int position;
    String from_cos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_child);

        from = getIntent().getStringExtra("from");
        Intent data = getIntent();
        medicine_name = data.getStringExtra("medicine_name");
        medicine_description = data.getStringExtra("medicine_description");
        medicine_type = data.getStringExtra("medicine_type");

        setFood = data.getStringExtra("setFood");
        setDosages = data.getStringExtra("setDosages");
        setFrequency = data.getStringExtra("setFrequency");
        setDays = data.getStringExtra("setDays");
        position = data.getIntExtra("position", 0);


        if (from.equalsIgnoreCase("daily_dosages")) {
            adapter = new ArrayAdapter<String>(this,
                    R.layout.single_liner_show, daily_dosages);
        } else if (from.equalsIgnoreCase("frequency")) {
            adapter = new ArrayAdapter<String>(this,
                    R.layout.single_liner_show, frequency);
        } else if (from.equalsIgnoreCase("cycle")) {
            adapter = new ArrayAdapter<String>(this,
                    R.layout.single_liner_show, cycle);
        }

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                String temp[] = null;
                if (from.equalsIgnoreCase("daily_dosages")) {
                    temp = daily_dosages;
                    from_cos = "daily_dosages";
                } else if (from.equalsIgnoreCase("frequency")) {
                    temp = frequency;
                    from_cos = "frequency";

                } else if (from.equalsIgnoreCase("cycle")) {
                    temp = cycle;
                    from_cos = "cycle";

                }

                assert temp != null;
                intent.putExtra("item", temp[i]);
                intent.putExtra("medicine_name", medicine_name);
                intent.putExtra("medicine_description", medicine_description);
                intent.putExtra("medicine_type", medicine_type);
                intent.putExtra("setFood", setFood);
                intent.putExtra("setDosages", setDosages);
                intent.putExtra("setFrequency", setFrequency);
                intent.putExtra("cycle", setDays);
                intent.putExtra("from", "daily_dosages");
                intent.putExtra("position", position);
                intent.putExtra("from_cos", from_cos);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}