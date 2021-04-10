package com.tcs.indoorvicinity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customer_View_Item extends AppCompatActivity implements PersonAdapter.ItemClicked {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Products> people;
    String category,name,temp="";
    TextView tvtopic;

    ArrayList<ArrayList<String>> ar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__view__item);
        recyclerView=findViewById(R.id.rec_view);
        tvtopic=findViewById(R.id.tvtopic);
        Intent intent=getIntent();
        category=intent.getStringExtra("category");
        name=intent.getStringExtra("name");
        if(name==null) {
            temp="fromsearchingbar";
            name = intent.getStringExtra("names");
            category = intent.getStringExtra("categories");
        }

        Toast.makeText(this, category+"  "+name, Toast.LENGTH_SHORT).show();
        //System.out.println("Ayush Awasthi........   "+name+" " +category);
        tvtopic.setText(category+" : "+name);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(category.equals("product")) {
            people = new ArrayList<Products>();
            System.out.println("Category to product hai..");
            if(temp.equals("fromsearchingbar"))
            {
                //System.out.println("From Searching Bar");
                try {
                  //  System.out.println("try");
                    validate(name);
                }
                catch(Exception e)
                {
                    //System.out.println("try->catch");
                    try {
                      //  System.out.println("try>catch->try");
                        shopvalidate(name);
                    }
                    catch(Exception ef)
                    {
                        //System.out.println("try>catch->try->catch");
                        Toast.makeText(this, "No Product/Shop found", Toast.LENGTH_SHORT).show();
                    }

                }

            }
            else
                validate(name);

        }
        else
        {
            //System.out.println("Category to shop hai..");
            people = new ArrayList<Products>();
            shopvalidate(name);
        }


/* Hardcoded entries
        people.add(new Products("Jeans", "Peter England", "10"));
        people.add(new Products("Shoes", "Woodland", "20"));
        people.add(new Products("Shirt", "Monte Carlo", "10"));
        people.add(new Products("TShirt", "Raymond", "15"));


*/


        myAdapter = new PersonAdapter(this, people);

        recyclerView.setAdapter(myAdapter);

    }

    private void validate(final String name) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://inroute.onlinewebshop.net/cust_view_products.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                response=response.substring(0,response.length()-1);
                if(response.equals(""))
                {
                    shopvalidate(name);
                }
                else {
                  //  System.out.println("------->" + response + "<-----");
                    int i, j;
                    String ar[] = response.split("-");
                    //System.out.println(ar.length);
                    for (i = 0; i < ar.length; i++) {
                        String arr[] = ar[i].split(",");
                      //  System.out.println(arr[0]);
                       // System.out.println(arr[2]);
                        people.add(new Products(name, arr[2], arr[0]));


                    }
                    myAdapter.notifyDataSetChanged();
                    Toast.makeText(Customer_View_Item.this, response, Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Database not able to talk");
                Toast.makeText(Customer_View_Item.this, "Cannot talk with database", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("prodname",name);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



    private void shopvalidate(final String name) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://inroute.onlinewebshop.net/cust_view_shop.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                //System.out.println("-------------------------------------------------");
                //System.out.println("-->"+response);
                if(response.length()==0)
                    System.out.println(0);
                else {



                    response = response.substring(0, response.length() - 1);
                    if(response.equals(""))
                    {
                        Toast.makeText(Customer_View_Item.this, " No Product/Shop Available", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int i, j;
                        String ar[] = response.split("-");
                  //      System.out.println(ar.length);
                        for (i = 0; i < ar.length; i++) {
                            String arr[] = ar[i].split(",");
                    //        System.out.println(arr[0]);
                      //      System.out.println(arr[2]);
                            people.add(new Products(name, arr[2], arr[0]));
                        }
                        myAdapter.notifyDataSetChanged();
                        Toast.makeText(Customer_View_Item.this, response, Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("/////////////////////////////Error///////");
                Toast.makeText(Customer_View_Item.this, "Cannot talk with database", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("shopname",name);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



    private void openQR(String shop) {
        Intent i=new Intent(Customer_View_Item.this,com.tcs.indoorvicinity.ScanQR.class);
        i.putExtra("shop",shop);
        startActivity(i);
    }

    @Override
    public void onItemClicked(int index) {
        Toast.makeText(this, "clicked item is +++"+index, Toast.LENGTH_SHORT).show();
        if(index==0) {
            openQR("inroute1");
        }
        else if(index==1) {
            openQR("inroute2");
        }

    }
}