package com.example.abdelrahman.note;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abdel Rahman on 29-Mar-18.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context context;
    private ArrayList<item> arrayList;
    private ArrayList<item> arrayList_copy;

    public Adapter(Context context, ArrayList<item> arrayList, ArrayList<item> arrayList_copy) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayList_copy = arrayList;
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView et_title, et_note, et_date;

        public Holder(View itemView) {
            super(itemView);

            et_title = (TextView) itemView.findViewById(R.id.et_title);
            et_note = (TextView) itemView.findViewById(R.id.et_note);
            et_date = (TextView) itemView.findViewById(R.id.et_date);
        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);


        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        final item item = arrayList.get(position);
        holder.et_title.setText(item.getTitle());
        holder.et_note.setText(item.getNote());
        holder.et_date.setText(item.getDate());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Activity_show.class);

                intent.putExtra("intent_id_key", item.getKey());
                intent.putExtra("intent_id_note", item.getNote());
                intent.putExtra("intent_id_title", item.getTitle());
                context.startActivity(intent);


//                System.out.println(item.getKey());

            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }




    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                ArrayList<item> array = new ArrayList<>();
                if (constraint.length() == 0) {
                    array = arrayList_copy;

                } else {

                    array = getItemFilter(constraint.toString().toLowerCase());
                }
                    FilterResults filterResults=new FilterResults();
                    filterResults.values=array;


                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                arrayList= (ArrayList<item>) results.values;
                Adapter.this.notifyDataSetChanged();
            }
        };


    }

    public ArrayList<item> getItemFilter(String text) {

        ArrayList<item> result = new ArrayList<>();
        for (item it : arrayList_copy) {

            if (it.getTitle().toLowerCase().contains(text)) {

                result.add(it);

            }
        }


        return result;
    }

}
