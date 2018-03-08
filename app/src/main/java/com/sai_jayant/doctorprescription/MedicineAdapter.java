package com.sai_jayant.doctorprescription;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Preet on 1/12/18.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.UriViewHolder> {
    private final Context ctx;
    private final ArrayList<Medicine> listName;


    public MedicineAdapter(Context applicationContext, ArrayList<Medicine> contactNameLists) {
        listName = contactNameLists;
        ctx = applicationContext;
    }


    @Override
    public MedicineAdapter.UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MedicineAdapter.UriViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final MedicineAdapter.UriViewHolder holder, final int position) {


//
        holder.medicine_name.setText(listName.get(position).getMedicine_name());
        holder.description.setText(listName.get(position).getMedicine_description());
        holder.day_after_food.setText(listName.get(position).getDaytime_after_food());
        holder.day_before_food.setText(listName.get(position).getDaytime_before_food());
        holder.night_after_food.setText(listName.get(position).getNighttime_after_food());
        holder.night_before_food.setText(listName.get(position).getNighttime_before_food());
        holder.night_before_food.setText(listName.get(position).getNighttime_before_food());
        holder.med_type_.setText(listName.get(position).getMedicine_type());

        if (listName.get(position).getSelected() == true) {
            holder.chk_selected.setVisibility(View.VISIBLE);
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

                // Remove the item on remove/button click
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


//            holder.mUri.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
//            holder.mPath.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
    }


    @Override
    public int getItemCount() {
        return listName == null ? 0 : listName.size();
    }


    static class UriViewHolder extends RecyclerView.ViewHolder {

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
            medicine_name = (TextView) contentView.findViewById(R.id.medicine_name);
            description = (TextView) contentView.findViewById(R.id.description);
            day_after_food = (TextView) contentView.findViewById(R.id.day_after_food);
            day_before_food = (TextView) contentView.findViewById(R.id.day_before_food);
            night_before_food = (TextView) contentView.findViewById(R.id.night_before_food);
            night_after_food = (TextView) contentView.findViewById(R.id.night_after_food);
            med_type_ = (TextView) contentView.findViewById(R.id.med_type_);


        }
    }
}