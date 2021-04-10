package com.tcs.indoorvicinity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    Button b1;
    TextView email;
    EditText e1,e2,e3,e4,e5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        b1=findViewById(R.id.btn_sign_up);
        email=findViewById(R.id.vendor_email_retriev);
        e1=findViewById(R.id.vendor_name);
        e2=findViewById(R.id.vendor_phone_number);
        e3=findViewById(R.id.vendor_shop_name);
        e4=findViewById(R.id.vendor_password);
        e5=findViewById(R.id.shop_id);

        String email1=getIntent().getStringExtra("email");
        email.setText(email1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_account();

            }
        });
    }
    void create_account()
    {
        String name,phone,shop_name,password,email2,shopid;
        name=e1.getText().toString().trim();
        email2=e2.getText().toString().trim();
        shop_name=e3.getText().toString().trim();
        password=e4.getText().toString().trim();
        phone=email.getText().toString().trim();
        shopid=e5.getText().toString().trim();
        if(TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(shop_name) && TextUtils.isEmpty(password)&& TextUtils.isEmpty(shopid) )
        {
            Toast.makeText(this, "Please fill out fields carefully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            validate(name,email2,password,phone,shop_name,shopid);


        }
    }
    private void validate(final String name, final String email2, final String password,final String phone,final String shop_name, final String shopid1) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://inroute.onlinewebshop.net/signup.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
               // System.out.println(response);
                //Toast.makeText(SignUp.this, response, Toast.LENGTH_SHORT).show();
                if(response.contains("1"))
                {
                    Intent i =new Intent(SignUp.this,com.tcs.indoorvicinity.Login.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(SignUp.this, "Error in connecting", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("name",name);
                parms.put("email",email2);
                parms.put("password",password);
                parms.put("phone",phone);
                parms.put("shopname",shop_name);
                parms.put("shopid",shopid1);


                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    }

