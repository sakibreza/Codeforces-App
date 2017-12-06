package progworms.sample;

import android.content.Intent;
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

public class Start extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    databaseHelp mDatabaseHelper;
    Button click;
    static TextView data;
    String str = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button b1=(Button) findViewById(R.id.button);
        Button b2=(Button) findViewById(R.id.button2);
        Button b3=(Button) findViewById(R.id.history);
        Button b4=(Button) findViewById(R.id.save);
        mDatabaseHelper = new  databaseHelp(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("Info");
                EditText a = (EditText) findViewById(R.id.handle);
                str = a.getText().toString();
                Intent i=new Intent(Start.this,info.class);
                i.putExtra("Handle",str);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("Contests");
                EditText a = (EditText) findViewById(R.id.handle);
                str = a.getText().toString();
                Intent i = new Intent(Start.this, display.class);
                i.putExtra("Handle", str);
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("History");
                Intent i = new Intent(Start.this, listview.class);
                startActivity(i);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("Save");
                EditText a = (EditText) findViewById(R.id.handle);
                str = a.getText().toString();
                if (a.length() != 0) {
                    AddData(str);
                    a.setText("");
                }
            }
        });




    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);
        toastMessage("Handle Inserted");
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
