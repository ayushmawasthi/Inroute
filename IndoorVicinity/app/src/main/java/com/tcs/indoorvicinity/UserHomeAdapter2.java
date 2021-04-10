package com.tcs.indoorvicinity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class UserHomeAdapter2 extends RecyclerView.Adapter<UserHomeAdapter2.ViewHolder> {
    private ArrayList<String> product;
    ItemClicked activity;
    public interface ItemClicked
    {
        public void onItemClicked(int index,String s1);
    }



    public UserHomeAdapter2(Context context, ArrayList<String> list) {
        product=list;
        activity = (ItemClicked) context;

    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv1;
        ImageView itempic;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.userhometext1);

            itempic=itemView.findViewById(R.id.userhomeimg1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

             //       System.out.println("Item clicked " +getItemCount() );
                 //   System.out.println("2 "+getAbsoluteAdapterPosition());
                    activity.onItemClicked(product.indexOf((String) v.getTag()),"shop");


                }
            });
        }

    }


    @NonNull
    @Override
    public UserHomeAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.userhomeadapterlist1,parent,false);
        return new UserHomeAdapter2.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHomeAdapter2.ViewHolder holder, int position) {
        holder.itemView.setTag(product.get(position));
        holder.tv1.setText(product.get(position));
        holder.itempic.setImageResource(R.drawable.car1);



    }

    @Override
    public int getItemCount() {
        return product.size();

    }
}
