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
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

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


class FetchData4 extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed = "";
    String singleParsed = "";
    String Handle = "";
    String loc="";
    public FetchData4(String handle) {
        Handle=handle;
    }
    public String getloc(){return  loc;}
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String temp = "http://codeforces.com/api/user.info?handles="+Handle;
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
            for(int i =0 ;i <JA.length(); i++){
            JSONObject JO = (JSONObject) JA.get(i);
            singleParsed = "CF Handle : " + Handle + "\n" + "Name : " + JO.get("firstName")+" "+JO.get("lastName") + "\n"+
            "Country : " + JO.get("country") + "\n"+
            "Handle : " + JO.get("handle") + "\n"+
            "Friend : " + JO.get("friendOfCount") + "\n"+
            "Rating : " + JO.get("rating") + "\n"+
            "Rank : " + JO.get("rank") + "\n"+
            "University : " + JO.get("organization") + "\n";

            dataParsed = dataParsed + singleParsed +"\n";
                loc= (String) JO.get("city");

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

        info.data.setText(dataParsed);
    }
}

public class info extends AppCompatActivity {

    Button click;
    static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        String handle = getIntent().getStringExtra("Handle");


        data=(TextView) findViewById(R.id.fetchdata3);


        final FetchData4 process = new FetchData4(handle);
        process.execute();

       // setContentView(R.layout.activity_maps);
        Button bx=(Button) findViewById(R.id.map);
        bx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("Map");
                Intent i = new Intent(info.this, MapsActivity.class);
                String location = process.getloc();
                i.putExtra("Loc", location);
                startActivity(i);
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}