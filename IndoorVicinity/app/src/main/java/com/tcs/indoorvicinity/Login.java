package com.tcs.indoorvicinity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button b1;
    EditText phnum,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1=findViewById(R.id.login_next);
        phnum=findViewById(R.id.vendor_email);
        pass=findViewById(R.id.login_vendor_password);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();


            }
        });
    }

    private void LoginUser() {
        final String phone=phnum.getText().toString().trim();
        final String passw=pass.getText().toString().trim();
        if(TextUtils.isEmpty(phone)  && TextUtils.isEmpty(passw))
        {
            Toast.makeText(this, "Please fill out fields carefully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StringRequest request=new StringRequest(Request.Method.POST, "http://inroute.onlinewebshop.net/login.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                    //System.out.println(response);
                    String shopid="";
                    int tmp=0;
                    for(int i =0;i<response.length();i++)
                    {
                        if(response.charAt(i)==',' && tmp==0)
                        {
                            tmp=1;
                        }
                        else if(tmp==1 && response.charAt(i)==','){
                            break;                  // when second , is encountered

                        }
                        else if(tmp==1) {
                            shopid=shopid+response.charAt(i);
                            }
                    }
                    //System.out.println("shopid");
                    //System.out.println("1233333"+shopid);
                    if(response.equals("2") || response.equals("02"))
                    {
                        Toast.makeText(Login.this, "Phone Number Do not exists in Database", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(Login.this,com.tcs.indoorvicinity.SignUp.class);
                        i.putExtra("email",phone);
                        startActivity(i);
                    }
                    else if(response.contains("1"))
                    {
                        Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(Login.this,com.tcs.indoorvicinity.Vendor_add_item.class);
                        i.putExtra("shopid", shopid);
                        startActivity(i);

                    }
                    else
                    {
                        Toast.makeText(Login.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                    }
                    //System.out.println(response);

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
                    params.put("username",phone);
                    params.put("password",passw);
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(request);


        }
    }

}
