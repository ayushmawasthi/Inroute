package com.tcs.indoorvicinity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Product_adapter extends RecyclerView.Adapter<Product_adapter.ViewHolder> {
    private ArrayList<Products> product;
    public Product_adapter(Context context, ArrayList<Products> list) {
        product=list;

    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv1,tv2,tv3;
        ImageView itempic;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.vendor_product_name);
            tv2=itemView.findViewById(R.id.vendor_product_price);
            tv3=itemView.findViewById(R.id.vendor_product_discount);



            itempic=itemView.findViewById(R.id.vendor_product_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("Item clicked " +getItemCount() );
                    System.out.println("2 "+getAbsoluteAdapterPosition());


                }
            });
        }

    }


    @NonNull
    @Override
    public Product_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_v,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Product_adapter.ViewHolder holder, int position) {
        holder.itemView.setTag(product.get(position));
        holder.tv1.setText(product.get(position).getProduct_name());
        holder.tv2.setText(product.get(position).getProduct_brand());
        holder.tv3.setText(product.get(position).getProduct_discount()+"% off");
        holder.itempic.setImageResource(R.drawable.car1);



    }

    @Override
    public int getItemCount() {
        return product.size();

    }
}
