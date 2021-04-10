package com.tcs.indoorvicinity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private ArrayList<Products> people;
    ItemClicked activity;

    public interface ItemClicked
    {
        public void onItemClicked(int index);
    }

    public PersonAdapter(Context context, ArrayList<Products> list)
    {
        people=list;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView item_image;
        TextView brand_name,discount;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            discount = itemView.findViewById(R.id.discount);
            brand_name = itemView.findViewById(R.id.brand_name);
            item_image = itemView.findViewById(R.id.item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(people.indexOf((Products) v.getTag()));


                }
            });

        }
    }

    @NonNull
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_discount_list,viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(people.get(i));

        viewHolder.discount.setText(people.get(i).getProduct_discount()+"% of discount at ");

        viewHolder.brand_name.setText(people.get(i).getProduct_brand());

        if(people.get(i).getProduct_name().equals("shirt"))
        {
            //viewHolder.item_image.setImageResource(R.drawable.shirt);
        }
        else
        {
          //  viewHolder.item_image.setImageResource(R.drawable.tshirt);
        }

    }

    @Override
    public int getItemCount() {
        return people.size();
    }
}
