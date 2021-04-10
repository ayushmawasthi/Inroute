package com.tcs.indoorvicinity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Vendor_add_item extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Products> product;
    ImageButton add,myacount;
    String shopid;
    String responsefromphp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        shopid=intent.getStringExtra("shopid");

        setContentView(R.layout.activity_vendor_add_item);
        //fetchdata();
        recyclerView=findViewById(R.id.recyclerview1);
//        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        product=new ArrayList<Products>();
        fetchdata();
       // product.add(new Products("T Shirt","800","50"));
     //   product.add(new Products("Shirt","900","70"));
      //  product.add(new Products("Jeans","700","50"));
        product.add(new Products("Socks","300","50"));
        myAdapter=new Product_adapter(this,product);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        add=findViewById(R.id.additem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Vendor_add_item.this,com.tcs.indoorvicinity.Vendor_add_product_form.class);
                intent1.putExtra("shopid",shopid);
                startActivity(intent1);
                
            }
        });
        myacount=findViewById(R.id.gotomyaccount);
        myacount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Vendor_add_item.this,com.tcs.indoorvicinity.VendorAccount.class);
                startActivity(intent);
            }
        });
    }
    public void fetchdata()
    {

        StringRequest request=new StringRequest(Request.Method.POST, "http://inroute.onlinewebshop.net/vendor_fetch_prod.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(Vendor_add_item.this, response, Toast.LENGTH_SHORT).show();
               // System.out.println("12322123344"+response);

                int i,j,n=response.length();
                if(response.length()<=4)
                {
                    System.out.println("Kuch nahi hai yaha");
                    Toast.makeText(Vendor_add_item.this, "Kuch nahi hai yaha", Toast.LENGTH_SHORT).show();
                }
                else{
                    responsefromphp=response.substring(0,response.length()-1);
                    String pn,pd,pp;
                    String sar[]=responsefromphp.split("-");

                for(i=0;i<sar.length;i++)
                {
                    String sarr[]=sar[i].split(",");
                    pn=sarr[0];
                    pp=sarr[2];
                    pd=sarr[4];
                    product.add(new Products(pn,pp,pd));
                    //System.out.println(pn+" "+pp+" "+pd);
                    myAdapter.notifyDataSetChanged();
                }
                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Login", "Error: " + error
                        + "\nStatus Code " + error.networkResponse.statusCode
                        + "\nResponse Data " + error.networkResponse.data
                        + "\nCause " + error.getCause()
                        + "\nmessage" + error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
               params.put("shopid",shopid);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchdata();
    }
}