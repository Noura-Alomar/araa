package com.example.araa;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Araa {
    private static final OkHttpClient client = new OkHttpClient();
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
}
