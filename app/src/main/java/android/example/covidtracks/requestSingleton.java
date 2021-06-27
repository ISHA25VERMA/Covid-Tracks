package android.example.covidtracks;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class requestSingleton {
    // only one oject of this class can be made // can be used for th elifetime of our app
    private static  requestSingleton instance;
    private RequestQueue mRequestQueue;



    private requestSingleton(Context context){
         mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized  requestSingleton getInstance(Context context){
        if (instance == null){
            instance = new requestSingleton(context);
        }
        return instance;
    }

    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }

}
