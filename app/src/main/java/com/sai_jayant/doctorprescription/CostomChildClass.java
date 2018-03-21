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
    String frequency[] = {"never", "once daily(OD)", "twice daily(TD)", "3 times daily", "4 times daily", "6 times daily", "7 times daily", "8 times daily", "9 times daily", "10 times daily", "every 2 hour", "every 3 hour"};
    String cycle[] = {"one day", "two days", "3 days", "4 days", "one week", "two weeks", "1 month", "2 months", "3 months", "6 month", "9 month", "1 year", "2 year", "always"};
    String food_habbit[] = {"before Food", "after Food", "only in morning", "only in night", "any time", "nothing specific", "immediately"};
    private String medicine_name, medicine_description, medicine_type, setFood, setDosages, setFrequency, setDays;
    int position;
    String from_cos;
    String med_id;


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
        med_id = data.getStringExtra("med_id");


        if (from.equalsIgnoreCase("daily_dosages")) {
            adapter = new ArrayAdapter<String>(this,
                    R.layout.single_liner_show, daily_dosages);
        } else if (from.equalsIgnoreCase("frequency")) {
            adapter = new ArrayAdapter<String>(this,
                    R.layout.single_liner_show, frequency);
        } else if (from.equalsIgnoreCase("cycle")) {
            adapter = new ArrayAdapter<String>(this,
                    R.layout.single_liner_show, cycle);
        }else if (from.equalsIgnoreCase("food_habbit")) {
            adapter = new ArrayAdapter<String>(this,
                    R.layout.single_liner_show, food_habbit);
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

                } else if (from.equalsIgnoreCase("food_habbit")) {
                    temp = food_habbit;
                    from_cos = "food_habbit";

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
                intent.putExtra("med_id", med_id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}