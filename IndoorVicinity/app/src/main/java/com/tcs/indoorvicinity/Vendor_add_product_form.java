package com.tcs.indoorvicinity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Vendor_add_product_form extends AppCompatActivity {
    ImageView product_img;
    private static final int PICK_IMAGE = 1;
    Uri imageuri;
    EditText e1,e2,e3,e4;
    Button addnew;
    String p_name,p_category,p_brand,p_discount,save_cuurent_date,getSave_cuurent_time;
    String productRamdomkey,downloadImageUrl;
    String shopid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_add_product_form);
        product_img=findViewById(R.id.add_product_image);
        e1=findViewById(R.id.form_name_of_product);
        e2=findViewById(R.id.form_categ_of_product);
        e3=findViewById(R.id.form_brand_of_product);
        e4=findViewById(R.id.form_discount_of_product);
        addnew=findViewById(R.id.btn_add_product);
        Intent intent=getIntent();
        shopid=intent.getStringExtra("shopid");
        product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);*/
             OpenGalley();
            }
        });
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validateproductdata();
            }
        });



    }
    void OpenGalley()
    {
        Intent gallery=new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, PICK_IMAGE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null)
        {
            imageuri=data.getData();
            try {
                //Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                //product_img.setImageBitmap(bitmap);
                product_img.setImageURI(imageuri);

            }
            catch (Exception e)
            {
                e.printStackTrace();

            }

        }
    }
    private void Validateproductdata()

    {
        p_name=e1.getText().toString().trim();
        p_category=e2.getText().toString().trim();
        p_brand=e3.getText().toString().trim();
        p_discount=e4.getText().toString().trim();
        if(imageuri==null && TextUtils.isEmpty(p_name) && TextUtils.isEmpty(p_category) && TextUtils.isEmpty(p_brand) && TextUtils.isEmpty(p_discount))
        {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
        }
        else {
            storeproductinfo();
        }
    }
    private void storeproductinfo()
    {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://inroute.onlinewebshop.net/add_prod.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
              //  System.out.println(response);
                Toast.makeText(Vendor_add_product_form.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("product_name",p_name);
                parms.put("product_image","abc.jpeg");
                parms.put("product_price",p_category);
                parms.put("product_brand",p_brand);
                parms.put("product_discount",p_discount);
                parms.put("shopid",shopid);


                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}


