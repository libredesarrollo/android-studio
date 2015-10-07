package net.desarrollolibre.recyclerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView audioRv = (RecyclerView) findViewById(R.id.audio_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        audioRv.setLayoutManager(linearLayoutManager);
        populatePersons();
        ListAdapter listAdapter = new ListAdapter(persons);
        audioRv.setAdapter(listAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void populatePersons(){
        persons = new ArrayList<>();
        persons.add(new Person("Arya Stark","11 años, mujer","#00FF00"));
        persons.add(new Person("Jon Snow","16 años, hombre","#774499"));
        persons.add(new Person("Sansa Stark","14 años, mujer","#00FF99"));
        persons.add(new Person("Eddard Stark","45 años, hombre","#DDFF00"));
        persons.add(new Person("Gusano Gris","22 años, hombre","#774499"));
        persons.add(new Person("Catelyn Tully","40 años, mujer","#0066FF"));
    }
}
