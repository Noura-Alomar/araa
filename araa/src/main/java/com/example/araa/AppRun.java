package com.example.araa;

public class AppRun {

    String APIKey,COPPA;

    public AppRun(String apiKey, String coppa) {
        APIKey = apiKey;
        COPPA = coppa;
    }


    public String getKey() {
        return APIKey;
    }

    public void setKey(String key) {
        APIKey = key;
    }

    public String getCOPPA() {
        return COPPA;
    }

    public void setCOPPA(String coppa) {
        COPPA = coppa;
    }
}
