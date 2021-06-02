package android.example.covidtracks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.ListView;
import android.widget.TextView;

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

    private ListView listView;
    private DataAdapter dataAdapter;
    private TextView confirmedC;
    private TextView recoveredC;
    private TextView deathC;
    private RequestQueue requestQueue;
    View loadingIndicator;
    private TextView emptyText;

    private final static String REQUEST_URL = "https://api.covid19india.org/data.json";
    List<DataCovid> StateWiseList = new ArrayList<DataCovid>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyText = findViewById(R.id.empty_view);
        View loadingIndicator = findViewById(R.id.loading_indicator);

        requestQueue = requestSingleton.getInstance(this).getmRequestQueue();

        Log.d("myapp","length : "+StateWiseList.size());

        dataAdapter = new DataAdapter(this, StateWiseList);

        listView = findViewById(R.id.list);
        listView.setEmptyView(emptyText);
        Log.d("myapp","length : "+StateWiseList.size());
        jsonParsing();
        loadingIndicator.setVisibility(View.GONE);
    }

    public void setDataAdapter(){

        listView.setAdapter(dataAdapter);
    }

    private void jsonParsing(){

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
                        Log.d("myapp",state +" "+confirmed+" "+death +" "+StateWiseList.size());
                    }

                    JSONObject country = statewise.getJSONObject(0);
                    String dConfirmed = country.getString("confirmed");
                    String dRecovered = country.getString("recovered");
                    String dDeath  = country.getString("deaths");
                    confirmedC = findViewById(R.id.confirmedC);
                    confirmedC.setText(dConfirmed);
                    recoveredC = findViewById(R.id.recoveredC);
                    recoveredC.setText(dRecovered);
                    deathC = findViewById(R.id.deathC);
                    deathC.setText(dDeath);

                    setDataAdapter();

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
    }

}