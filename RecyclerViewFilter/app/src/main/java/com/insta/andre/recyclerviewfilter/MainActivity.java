package com.insta.andre.recyclerviewfilter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Person> persons;
    private EditText etSearchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView audioRv = (RecyclerView) findViewById(R.id.audio_rv);
        etSearchBox = (EditText) findViewById(R.id.etSearchBox);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        audioRv.setLayoutManager(linearLayoutManager);
        populatePersons();
        final ListAdapter listAdapter = new ListAdapter(persons);
        audioRv.setAdapter(listAdapter);


        etSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listAdapter.getFilter().filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
