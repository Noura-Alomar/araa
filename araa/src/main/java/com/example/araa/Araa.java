package com.example.araa;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Araa {
    private static final OkHttpClient client = new OkHttpClient();
    // variable to hold context
    private Context mycontext;


    public Araa(Context ctx)
    {
        mycontext = ctx;//No need to save the context if you aren't reusing it after this.
        getAAID(mycontext);
    }
    public static void getLocation(){

        FormBody formBody = new FormBody.Builder()
                .add("name", "Noura")
                .add("email", "Noura@Noura.com")
                .add("phone", "098888890")
                .add("type", "typeC")
                .build();


        Request request2 = new Request.Builder()
                .url("https://www.theappsdr.com/contact/create")
                .post(formBody)
                .build();

        client.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    //String body = responseBody.string();
                    Log.d("TAG49", "onResponse: "+ responseBody.string());
                }

            }
        });



    }


    public static void getAAID(Context cn){
        getUIDs(cn);

    }

    public static void getUIDs(Context cn)
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(cn);
                    String myId = adInfo != null ? adInfo.getId() : null;

                    Log.i("UIDMY", myId);

                    HttpUrl.Builder builder = new HttpUrl.Builder();
                    HttpUrl url2 = builder.scheme("http")
                            .host("www.theappsdr.com")
                            .addPathSegment("contacts")
                            .addPathSegment("search")
                            .addQueryParameter("name",myId).build();
                    Log.d("TAG45", "onResponse: "+ url2);
                    createContact("it", myId, "nouraaaid@it.com", "999000887");
                    //setTextView(myId);
                } catch (Exception e) {
                    //Toast toast = Toast.makeText(MainActivity.this.getApplicationContext(), "error occurred ", Toast.LENGTH_SHORT);
                    //toast.setGravity(Gravity.TOP, 0,0);
                    //toast.show();
                }
            }
        });
    }

    public static void createContact(String name, String email, String phone, String type){
        FormBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("aaid", email)
                .add("phone", phone)
                .add("type", type)
                .build();


        Request request2 = new Request.Builder()
                .url("https://www.theappsdr.com/contact/create")
                .post(formBody)
                .build();

        client.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    //String body = responseBody.string();
                    Log.d("TAG49", "onResponse: "+ responseBody.string());
                }

            }
        });



    }

}
