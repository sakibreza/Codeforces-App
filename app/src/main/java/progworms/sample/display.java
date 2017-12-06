package progworms.sample;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


class FetchData3 extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed = "";
    String singleParsed = "";
    String Handle = "";

    public FetchData3(String handle) {
        Handle=handle;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String temp = "http://codeforces.com/api/user.rating?handle="+Handle;
            URL url = new URL(temp);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }


            JSONObject JOO = new JSONObject(data);
            JSONArray JA=JOO.getJSONArray("result");
            for(int i = JA.length()-1 ;i >=0; i--){
                JSONObject JO = (JSONObject) JA.get(i);
                int ratingChange = JO.getInt("newRating") - JO.getInt("oldRating");
                singleParsed =  "Handle : " + JO.get("handle")+ "\n"+
                        "Contest : " + JO.get("contestName") + "\n"+
                        "Rank : " + JO.get("rank") + "\n"+
                        "New Rating : " + JO.get("newRating") + "\n"+
                        "Old Rating : " + JO.get("oldRating") + "\n"+
                        "Rating Change : " + ratingChange + "\n";

                dataParsed = dataParsed + singleParsed +"\n" ;



            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        display.data.setText(this.dataParsed);
    }
}

public class display extends AppCompatActivity {

    Button click;
    static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        String handle = getIntent().getStringExtra("Handle");

        
        data=(TextView) findViewById(R.id.fetchdata2);


        FetchData3 process = new FetchData3(handle);
        process.execute();


    }


}