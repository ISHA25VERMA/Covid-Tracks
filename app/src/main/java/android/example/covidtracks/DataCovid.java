package android.example.covidtracks;

public class DataCovid {
    private String state;
    private String confirmed;
    private String recovered;
    private String death;

    public DataCovid(String state, String confirmed, String recovered, String death){
        this.state = state;
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.death = death;
    }

    public String getState() {
        return state;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeath() {
        return death;
    }
}
