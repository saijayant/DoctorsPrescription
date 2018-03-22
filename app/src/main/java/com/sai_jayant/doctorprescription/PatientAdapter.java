package com.sai_jayant.doctorprescription;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Preet on 1/12/18.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.UriViewHolder> implements Filterable {
    private final Context ctx;
    private ArrayList<Patient> listName;
    private DbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;


    public PatientAdapter(Context applicationContext, ArrayList<Patient> contactNameLists) {
        listName = contactNameLists;
        ctx = applicationContext;
    }


    @Override
    public UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UriViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final UriViewHolder holder, final int position) {


//
        holder.patient_name.setText(listName.get(position).getPatient_name());
        holder.age.setText(listName.get(position).getAge());
        holder.gender.setText(listName.get(position).getGender());
        holder.weight.setText(listName.get(position).getWeight());
        holder.mobile.setText(listName.get(position).getNumber());
        holder.adress.setText(listName.get(position).getAdress());
        holder.medicines.setText(listName.get(position).getMedicines());
        holder.time.setText(listName.get(position).getDate());

        holder.csb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss-dd-MM-yy-EE");

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(listName.get(position).getDate());
            date2 = sdf.parse(sdf.format(System.currentTimeMillis()));


        } catch (ParseException e) {
            e.printStackTrace();
        }


        long difference = date2.getTime() - date1.getTime();

        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference) / 1000;
        int sec = (int) TimeUnit.SECONDS.toSeconds(difference) / 1000;

        if (minutes < 0) minutes += 1440;

        int time = sec;
        String time_string = " second";
        if(time>=2){
            time_string = time + " seconds";

        }

        if (sec > 60) {
            time = sec / 60;
            time_string = time + " minute";
            if(time>=2){
                time_string = time + " minutes";

            }
            if (sec > 3600) {
                time = sec / 3600;
                time_string = time + " hour";
                if(time>=2){
                    time_string = time + " hours";

                }
                if (sec > 3600*24) {
                    time = sec / 3600*24;
                    time_string = time + " day";
                    if(time>=2){
                        time_string = time + " days";

                    }
                    if (sec > 3600*24*7) {
                        time = sec / 3600*24*7;
                        time_string = time + " week";
                        if(time>=2){
                            time_string = time + " weeks";

                        }

                    }
                    if (sec > 3600*24*360) {
                        time = sec / 3600*24*360;
                        time_string = time + " year";
                        if(time>=2){
                            time_string = time + " years";

                        }

                    }
                }
            }
        }


        holder.time.setText("Prescribed " + time_string + " ago");

















        holder.csb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (holder.chk_selected.getVisibility() == View.INVISIBLE) {
                    holder.chk_selected.setVisibility(View.VISIBLE);

                } else if (holder.chk_selected.getVisibility() == View.VISIBLE) {

                    holder.chk_selected.setVisibility(View.INVISIBLE);
                    holder.chk_selected.setSelected(false);

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
                                dbHelper.DeletePatientRecords(sqLiteDatabase, listName.get(position).getId());

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

    public void updateList(ArrayList<Patient> temp) {
        listName = temp;
        notifyDataSetChanged();

    }

    static class UriViewHolder extends RecyclerView.ViewHolder {

        private Button print;
        private LinearLayout expand_layout;
        private RelativeLayout expand_shrink;
        private ImageView expand;
        private ImageView chk_selected;
        private ImageView delete;
        private LinearLayout csb;
        private TextView patient_name;
        private TextView time;
        private TextView age;
        private TextView weight;
        private TextView gender;
        private TextView mobile;
        private TextView adress;
        private TextView medicines;


        UriViewHolder(View contentView) {
            super(contentView);


            chk_selected = (ImageView) contentView.findViewById(R.id.chk_selected);
            delete = (ImageView) contentView.findViewById(R.id.delete);
            csb = (LinearLayout) contentView.findViewById(R.id.csb);
            expand_layout = (LinearLayout) contentView.findViewById(R.id.expand_layout);
            expand = (ImageView) contentView.findViewById(R.id.expand);
            expand_shrink = (RelativeLayout) contentView.findViewById(R.id.expand_shrink);
            patient_name = (TextView) contentView.findViewById(R.id.patient_name);
            time = (TextView) contentView.findViewById(R.id.time);
            age = (TextView) contentView.findViewById(R.id.age);
            weight = (TextView) contentView.findViewById(R.id.weight);
            gender = (TextView) contentView.findViewById(R.id.gender);
            mobile = (TextView) contentView.findViewById(R.id.number);
            adress = (TextView) contentView.findViewById(R.id.address);
            medicines = (TextView) contentView.findViewById(R.id.medicine);
            print = (Button) contentView.findViewById(R.id.print);


        }
    }
}