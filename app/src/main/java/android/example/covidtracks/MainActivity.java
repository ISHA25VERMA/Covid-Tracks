package android.example.covidtracks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.textclassifier.ConversationActions;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String REQUEST_URL = "https://api.covid19india.org/data.json";
    ArrayList<DataCovid> StateWiseList = new ArrayList<DataCovid>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, REQUEST_URL, null, new Response.Listener<JSONObject>() {
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
                        StateWiseList.add(new DataCovid(state,confirmed,recovered,death));
                        Log.d("myapp",state +" "+confirmed+" "+death);
                    }


                } catch (JSONException e) {
                    Log.d("myapp","error in json");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myapp","error in establishing connection");
            }
        });

        requestQueue.add(jsonObjectRequest);

        Log.d("myapp","length : "+StateWiseList.size());

        DataAdapter dataAdapter = new DataAdapter(this, StateWiseList);
        ListView listView = findViewById(R.id.list);

        listView.setAdapter(dataAdapter);
        Log.d("myapp","length : "+StateWiseList.size());

    }

//    public ArrayList<DataCovid> json(){
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, REQUEST_URL, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray statewise = response.getJSONArray("statewise");
//                    for (int i = 1 ; i<statewise.length();i++){
//                        JSONObject stateA = statewise.getJSONObject(i);
//                        String state = stateA.getString("state");
//                        String confirmed = stateA.getString("confirmed");
//                        String recovered = stateA.getString("recovered");
//                        String death = stateA.getString("deaths");
//                        StateWiseList.add(new DataCovid(state,confirmed,recovered,death));
//                        Log.d("myapp",state +" "+confirmed+" "+death);
//                    }
//
//
//                } catch (JSONException e) {
//                    Log.d("myapp","error in json");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("myapp","error in establishing connection");
//            }
//        });
//
//    }

}