package android.example.covidtracks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DataAdapter extends ArrayAdapter<DataCovid> {

    public DataAdapter(@NonNull Context context,  @NonNull List<DataCovid> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataCovid stateData =getItem(position);

        View list_items = convertView;
        if (list_items == null) {
            list_items = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }


        TextView state = (TextView) list_items.findViewById(R.id.state);
        state.setText(stateData.getState());

        TextView confirmed = (TextView) list_items.findViewById(R.id.confirmed);
        confirmed.setText(stateData.getConfirmed());

        TextView recovered = (TextView) list_items.findViewById(R.id.recovered);
        recovered.setText(stateData.getRecovered());

        TextView death = (TextView) list_items.findViewById(R.id.death);
        death.setText(stateData.getDeath());


        return list_items;
    }
}
