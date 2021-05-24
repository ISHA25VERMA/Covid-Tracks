package android.example.covidtracks;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NetworkWork {
    private final RequestQueue requestQueue;
    private static Context context;
    private JsonObjectRequest jsonObjectRequest;

    NetworkWork(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

//    private RequestQueue getRequestQueue(){
//        if (requestQueue == null){
//            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
//        }
//        return requestQueue;
//    }

//    private  void CountryData(String url){
//        JsonObjectRequest countryData = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray statewise = response.getJSONArray("statewise");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//    }

    private  List<DataCovid> JsonStateWise_countryData(String url){
//        requestQueue.add(jsonObjectRequest);
        List<DataCovid> StateWiseList = new ArrayList<>();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray statewise = response.getJSONArray("statewise");
                    for (int i = 1 ; i<statewise.length();i++){
                        JSONObject stateA = statewise.getJSONObject(i);
                        String state = stateA.getString("state");
                        String confirmed = stateA.getString("confirmed");
                        String recovered = stateA.getString("recovered");
                        String death = stateA.getString("deaths");
                        DataCovid dataCovid = new DataCovid(state,confirmed,recovered,death);
                        StateWiseList.add(dataCovid);
                        Log.d("myapp","a"+confirmed+" "+death);
                    }

                } catch (JSONException e) {
                    Log.d("myapp","error in json");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myapp","error in fetching data");
            }
        });
        requestQueue.add(jsonObjectRequest);
        return StateWiseList;
    }

//    private void addToRequestQueue(JsonObjectRequest State){
//        requestQueue.add(State);
//    }

    public List<DataCovid> fetchData(String requestURL){

        List<DataCovid>  updates = JsonStateWise_countryData(requestURL);
        return updates;

    }
}
