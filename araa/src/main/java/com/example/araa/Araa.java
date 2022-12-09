package com.example.araa;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public static String APIKey;
    private static LocationRequest locationRequest;
    private static boolean ARAA_COPPA;
    private static boolean ARAA_Consent;
    private static String ARAA_CCPA= "opted_in";



    public Araa(Context ctx)
    {
        mycontext = ctx;//No need to save the context if you aren't reusing it after this.
        ARAA_COPPA = false;
        ARAA_CCPA = "opted_in";
        ARAA_Consent = false;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        AppRun model = new AppRun("TTT","COPPAAAA");
        model.setKey("Nouramm");
        model.setCOPPA("COPPA");
        double random = Math.random();
        DatabaseReference myRef = database.getReference("message").child("users");
        DatabaseReference newRef = myRef.push();
        newRef.setValue(model);


        getAAID(mycontext);
    }

    public static boolean activateAPIKey(String myAPIKey){
        APIKey = myAPIKey;
        return true;


    }

    public static void setCOPPA(boolean COPPAFlag){
        ARAA_COPPA = COPPAFlag;
    }

    public static void setConsent(boolean GDPRFlag){
        ARAA_Consent = GDPRFlag;
    }

    public static void setCCPA(String CCPAFlag){
        ARAA_CCPA = CCPAFlag;
    }





//----------------------------------Location things end here------------------------
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

                    if(ARAA_COPPA){

                        myId="000000000000";
                    }

                    HttpUrl.Builder builder = new HttpUrl.Builder();
                    HttpUrl url2 = builder.scheme("http")
                            .host("www.theappsdr.com")
                            .addPathSegment("contacts")
                            .addPathSegment("search")
                            .addQueryParameter("name",myId).build();
                    Log.d("TAG45", "onResponse: "+ url2);
                    createContact("it", myId, "nouraaaid@it.com", "999000887", APIKey);
                    //setTextView(myId);
                } catch (Exception e) {
                    //Toast toast = Toast.makeText(MainActivity.this.getApplicationContext(), "error occurred ", Toast.LENGTH_SHORT);
                    //toast.setGravity(Gravity.TOP, 0,0);
                    //toast.show();
                }
            }
        });
    }

    public static void createContact(String name, String email, String phone, String type, String apiKey){

        if(ARAA_COPPA){

            email="000000000000";
        }

        FormBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("aaid", email)
                .add("phone", phone)
                .add("type", type)
                .add("APIKey", apiKey)
                .add("ARAA_COPPA", String.valueOf(ARAA_COPPA))
                .add("ARAA_Consent", String.valueOf(ARAA_Consent))
                .add("ARAA_CCPA", String.valueOf(ARAA_CCPA))
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
