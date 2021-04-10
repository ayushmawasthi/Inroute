package com.tcs.indoorvicinity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class UserHome extends AppCompatActivity implements UserHomeAdapter1.ItemClicked,UserHomeAdapter2.ItemClicked {
    LinearLayout l1,l2;
    RecyclerView recyclerView,recyclerView2;
    RecyclerView.Adapter myAdapter,myAdapter2;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    ArrayList<Products> product;
    ArrayList<String> homeprod,homeshop;

    ArrayAdapter<String> arrayAdapter;
    ImageView productview,searchbutton;
    AutoCompleteTextView etsearch;
    ArrayList<String> temp=new ArrayList<String>();
    String names[]=new String[200];
    String namevalue;
    int k=0;
    int firstone=0;

    TextView t1temp;

    String responsefromphp;


    @Override
    public void onItemClicked(int index,String s1)
    {
        if(s1=="product")
        {
            Toast.makeText(this, s1+"   "+homeprod.get(index), Toast.LENGTH_SHORT).show();
            Intent i =new Intent(UserHome.this,com.tcs.indoorvicinity.Customer_View_Item.class);
            i.putExtra("category",s1);
            i.putExtra("name",homeprod.get(index));
            startActivity(i);

        }
        else
        {
            Toast.makeText(this, s1+"   "+homeshop.get(index), Toast.LENGTH_SHORT).show();
            Intent i =new Intent(UserHome.this,com.tcs.indoorvicinity.Customer_View_Item.class);
            i.putExtra("category",s1);
            i.putExtra("name",homeshop.get(index));
            startActivity(i);
        }
     //   System.out.println("dhjsjjhdsfjs"+s1);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        l1=findViewById(R.id.llproduct);
        l2=findViewById(R.id.llshop);
        etsearch =(AutoCompleteTextView) findViewById(R.id.userhomesearchbar);
        recyclerView=findViewById(R.id.userhomeproduct1);
        recyclerView2=findViewById(R.id.userhomeshop1);
        searchbutton=findViewById(R.id.userhomesearchbutton);
        t1temp=findViewById(R.id.temp1);

        t1temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(UserHome.this,com.tcs.indoorvicinity.Customer_View_Item.class);
                startActivity(i);
            }
        });
//        recyclerView.setHasFixedSize(true);

        layoutManager =new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        layoutManager2 =new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);

//        layoutManager =new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        product=new ArrayList<Products>();
        homeprod=new ArrayList<String>();
        homeshop=new ArrayList<String>();

        /* Hard Coded entries
        product.add(new Products("T Shirt","Raymond","50"));
        product.add(new Products("Shirt","LeeCooper","70"));
        product.add(new Products("Jeans","Lewis","50"));
        product.add(new Products("Socks","Puma","50"));
        product.add(new Products("Shoes","Reebok","20"));
        product.add(new Products("Tie","Raymond","60"));
*/
        fetchprod();

        myAdapter=new UserHomeAdapter1(this,homeprod);
        recyclerView.setAdapter(myAdapter);

        fetchshop();
        myAdapter2=new UserHomeAdapter2(this,homeshop);
        recyclerView2.setAdapter(myAdapter2);

        etsearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return false;
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    Intent i =new Intent(UserHome.this,com.tcs.indoorvicinity.Customer_View_Item.class);
                    i.putExtra("names",etsearch.getText().toString());
                    i.putExtra("categories","product");
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etsearch.getVisibility()!=View.VISIBLE) {
                    etsearch.setVisibility(View.VISIBLE);

                }
                else
                {


                 //   System.out.println("Searching text box : "+etsearch.getText());
                    if(etsearch.getText().equals(""))
                    {
                        Toast.makeText(UserHome.this, "Enter valid name", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent i =new Intent(UserHome.this,com.tcs.indoorvicinity.Customer_View_Item.class);
                        System.out.println("=====>"+etsearch.getText());
                        i.putExtra("names",etsearch.getText().toString());
                        i.putExtra("categories","product");
                        startActivity(i);
                    }
                }

            }
        });



    }

    public void fetchprod()
    {

        firstone=1;
        StringRequest request=new StringRequest(Request.Method.POST, "http://inroute.onlinewebshop.net/home_product.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(UserHome.this, response, Toast.LENGTH_SHORT).show();
               // System.out.println("12322123344"+response);

                int i,j,n=response.length();
                if(response.length()<=4)
                {
                    System.out.println("Kuch nahi hai yaha");
                    Toast.makeText(UserHome.this, "Kuch nahi hai yaha", Toast.LENGTH_SHORT).show();
                }
                else{
                    responsefromphp=response.substring(0,response.length()-1);

                    String sar[]=responsefromphp.split("-");


                    for(i=0;i<sar.length;i++)
                    {
                        String sarr[]=sar[i].split(",");
                        homeprod.add(sarr[0]);
                        //temp.add(sarr[0]);
                        names[k]=sarr[0];

                       // product.add(new Products(pn,pp,pd));
                 //       System.out.println("--------->"+names[k]);
                        k++;

                    }
                    if(firstone==2)
                        testing();
              //      arrayAdapter.notifyDataSetChanged();
                    myAdapter.notifyDataSetChanged();
                    myAdapter2.notifyDataSetChanged();
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
                params.put("shopid","shopid");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }


    public void fetchshop()
    {

        StringRequest request=new StringRequest(Request.Method.POST, "http://inroute.onlinewebshop.net/home_shop.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //    Toast.makeText(UserHome.this, response, Toast.LENGTH_SHORT).show();
             //   System.out.println("Shop response"+response);

                int i,j,n=response.length();
                if(response.length()<=4)
                {
                    System.out.println("Kuch nahi hai yaha");
                    Toast.makeText(UserHome.this, "Kuch nahi hai yaha", Toast.LENGTH_SHORT).show();
                }
                else{
                    responsefromphp=response.substring(0,response.length()-1);
                    String pn,pd,pp;
                    String sar[]=responsefromphp.split("-");

                    for(i=0;i<sar.length;i++)
                    {
                        String sarr[]=sar[i].split(",");
                        homeshop.add(sarr[0]);
                        // product.add(new Products(pn,pp,pd));
                        names[k]=sarr[0];
                        //temp.add(sarr[0]);
                      //  System.out.println("--------->"+names[k]);
                        k++;

                    }
                    if(firstone==1)
                        testing();
                    firstone=2;


                  //  arrayAdapter.notifyDataSetChanged();
                    myAdapter.notifyDataSetChanged();
                    myAdapter2.notifyDataSetChanged();
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
                params.put("shopid","shopid");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }
    public void testing()
    {
        System.out.println("Inside Testing");
        for(String stt:names)
        {
            System.out.println("***"+stt+"***");
        }
        System.out.println(k);
        String tempar[]=new String[k];
        for (int i=0;i<k;i++)
        {
            if(names[i]!=null)
            {
                tempar[i]=names[i];
            }
        }
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,tempar);
        etsearch.setThreshold(1);
        etsearch.setAdapter(arrayAdapter);

    }

}
