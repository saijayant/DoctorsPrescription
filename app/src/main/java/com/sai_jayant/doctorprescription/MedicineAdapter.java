package com.sai_jayant.doctorprescription;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Preet on 1/12/18.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.UriViewHolder> implements Filterable {
    private final Context ctx;
    private ArrayList<Medicine> listName;
    private DbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private RadioButton lastCheckedRB = null;
    Activity ac;


    public MedicineAdapter(Activity applicationContext, ArrayList<Medicine> contactNameLists) {
        listName = contactNameLists;
        ctx = applicationContext;
        ac = applicationContext;
    }


    @Override
    public UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UriViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final UriViewHolder holder, final int position) {


//
        holder.medicine_name.setText(listName.get(position).getMedicine_name());
        holder.description.setText(listName.get(position).getMedicine_description());
//        holder.day_after_food.setText(listName.get(position).getDaytime_after_food());
//        holder.day_before_food.setText(listName.get(position).getDaytime_before_food());
//        holder.night_after_food.setText(listName.get(position).getNighttime_after_food());
//        holder.night_before_food.setText(listName.get(position).getNighttime_before_food());
//        holder.night_before_food.setText(listName.get(position).getNighttime_before_food());
        holder.med_type_.setText(listName.get(position).getMedicine_type());


        Log.d("true", "onBindViewHolder: " + listName.get(position).getSelected());

        if (listName.get(position).getSelected() == true) {
            holder.chk_selected.setVisibility(View.VISIBLE);
        } else if (listName.get(position).getSelected() == false) {
            holder.chk_selected.setVisibility(View.INVISIBLE);

        }


        holder.csb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (holder.chk_selected.getVisibility() == View.INVISIBLE) {
                    holder.chk_selected.setVisibility(View.VISIBLE);
                    listName.get(position).setSelected(true);

                } else if (holder.chk_selected.getVisibility() == View.VISIBLE) {

                    holder.chk_selected.setVisibility(View.INVISIBLE);
                    holder.chk_selected.setSelected(false);
                    listName.get(position).setSelected(false);

                }

            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item label
                AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage("Are sure you want to delete ?");
                alertbox.setTitle("Warning !");

                alertbox.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                // Remove the item on remove/button click
                                dbHelper = new DbHelper(ctx);
                                sqLiteDatabase = dbHelper.getWritableDatabase();
                                dbHelper.DeleteRecords(sqLiteDatabase, listName.get(position).getId());

                                listName.remove(position);

                /*
                    public final void notifyItemRemoved (int position)
                        Notify any registered observers that the item previously located at position
                        has been removed from the data set. The items previously located at and
                        after position may now be found at oldPosition - 1.

                        This is a structural change event. Representations of other existing items
                        in the data set are still considered up to date and will not be rebound,
                        though their positions may be altered.

                    Parameters
                        position : Position of the item that has now been removed
                */
                                notifyItemRemoved(position);

                /*
                    public final void notifyItemRangeChanged (int positionStart, int itemCount)
                        Notify any registered observers that the itemCount items starting at
                        position positionStart have changed. Equivalent to calling
                        notifyItemRangeChanged(position, itemCount, null);.

                        This is an item change event, not a structural change event. It indicates
                        that any reflection of the data in the given position range is out of date
                        and should be updated. The items in the given range retain the same identity.

                    Parameters
                        positionStart : Position of the first item that has changed
                        itemCount : Number of items that have changed
                */
                                notifyItemRangeChanged(position, listName.size());

                                // Show the removed item label
                                notifyDataSetChanged();
//                changeSetting(position,holder,mDataName);

                            }
                        });
                alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertbox.show();


            }
        });


        holder.daily_dosages.setText(listName.get(position).getDosages());
        holder.frequency.setText(listName.get(position).getFrequency());
        holder.cycle.setText(listName.get(position).getDays());
        holder.food_habbit.setText(listName.get(position).getFood());


        holder.daily_dosages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ac, CostomChildClass.class);
                i.putExtra("medicine_name", listName.get(position).getMedicine_name());
                i.putExtra("medicine_description", listName.get(position).getMedicine_description());
                i.putExtra("medicine_type", listName.get(position).getMedicine_type());
                i.putExtra("setFood", listName.get(position).getFood());
                i.putExtra("setDosages", holder.daily_dosages.getText().toString());
                i.putExtra("setFrequency", holder.frequency.getText().toString());
                i.putExtra("cycle", holder.cycle.getText().toString());
                i.putExtra("from", "daily_dosages");
                i.putExtra("position", position);
                i.putExtra("med_id", listName.get(position).getMed_id());

                ac.startActivityForResult(i, 5);
            }
        });

        holder.frequency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(ac, CostomChildClass.class);
                i.putExtra("medicine_name", listName.get(position).getMedicine_name());
                i.putExtra("medicine_description", listName.get(position).getMedicine_description());
                i.putExtra("medicine_type", listName.get(position).getMedicine_type());
                i.putExtra("setFood", listName.get(position).getFood());
                i.putExtra("setDosages", holder.daily_dosages.getText().toString());
                i.putExtra("setFrequency", holder.frequency.getText().toString());
                i.putExtra("cycle", holder.cycle.getText().toString());
                i.putExtra("from", "frequency");
                i.putExtra("position", position);
                i.putExtra("med_id", listName.get(position).getMed_id());


                ac.startActivityForResult(i, 5);
            }
        });

        holder.cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(ac, CostomChildClass.class);
                i.putExtra("medicine_name", listName.get(position).getMedicine_name());
                i.putExtra("medicine_description", listName.get(position).getMedicine_description());
                i.putExtra("medicine_type", listName.get(position).getMedicine_type());
                i.putExtra("setFood", listName.get(position).getFood());
                i.putExtra("setDosages", holder.daily_dosages.getText().toString());
                i.putExtra("setFrequency", holder.frequency.getText().toString());
                i.putExtra("cycle", holder.cycle.getText().toString());
                i.putExtra("from", "cycle");
                i.putExtra("position", position);
                i.putExtra("med_id", listName.get(position).getMed_id());

                ac.startActivityForResult(i, 5);
            }
        });
        holder.food_habbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(ac, CostomChildClass.class);
                i.putExtra("medicine_name", listName.get(position).getMedicine_name());
                i.putExtra("medicine_description", listName.get(position).getMedicine_description());
                i.putExtra("medicine_type", listName.get(position).getMedicine_type());
                i.putExtra("setFood", listName.get(position).getFood());
                i.putExtra("setDosages", holder.daily_dosages.getText().toString());
                i.putExtra("setFrequency", holder.frequency.getText().toString());
                i.putExtra("cycle", holder.cycle.getText().toString());
                i.putExtra("from", "food_habbit");
                i.putExtra("position", position);
                i.putExtra("med_id", listName.get(position).getMed_id());

                ac.startActivityForResult(i, 5);
            }
        });


        holder.expand_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.expand_shrink.getVisibility() == View.GONE) {
                    holder.expand_shrink.setVisibility(View.VISIBLE);
                    holder.expand.setBackground(ctx.getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                } else if (holder.expand_shrink.getVisibility() == View.VISIBLE) {
                    holder.expand_shrink.setVisibility(View.GONE);
                    holder.expand.setBackground(ctx.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));

                } else {
                    holder.expand_shrink.setVisibility(View.VISIBLE);
                    holder.expand.setBackground(ctx.getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));

                }

            }

        });


//            holder.mUri.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
//            holder.mPath.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
    }


    @Override
    public int getItemCount() {
        return listName == null ? 0 : listName.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public void updateList(ArrayList<Medicine> temp) {
        listName = temp;
        notifyDataSetChanged();

    }

    static class UriViewHolder extends RecyclerView.ViewHolder {

        private TextView food_habbit;
        private RadioButton none;
        private RadioButton before;
        private RadioButton after;
        private RadioGroup medicine_habit;
        private TextView cycle;
        private TextView daily_dosages;
        private TextView frequency;

        private LinearLayout expand_layout;
        private RelativeLayout expand_shrink;
        private ImageView expand;
        private ImageView chk_selected;
        private ImageView delete;
        private LinearLayout csb;
        private TextView medicine_name;
        private TextView description;
        private TextView day_after_food;
        private TextView day_before_food;
        private TextView night_before_food;
        private TextView night_after_food;
        private TextView med_type_;

        UriViewHolder(View contentView) {
            super(contentView);


            chk_selected = (ImageView) contentView.findViewById(R.id.chk_selected);
            delete = (ImageView) contentView.findViewById(R.id.delete);
            csb = (LinearLayout) contentView.findViewById(R.id.csb);
            expand_layout = (LinearLayout) contentView.findViewById(R.id.expand_layout);
            expand = (ImageView) contentView.findViewById(R.id.expand);
            expand_shrink = (RelativeLayout) contentView.findViewById(R.id.expand_shrink);
            medicine_name = (TextView) contentView.findViewById(R.id.medicine_name);
            description = (TextView) contentView.findViewById(R.id.description);
            med_type_ = (TextView) contentView.findViewById(R.id.med_type_);


            daily_dosages = (TextView) contentView.findViewById(R.id.daily_dosages);
            cycle = (TextView) contentView.findViewById(R.id.cycle);
            frequency = (TextView) contentView.findViewById(R.id.frequency);
            food_habbit = (TextView) contentView.findViewById(R.id.food_habbit);


        }
    }
}