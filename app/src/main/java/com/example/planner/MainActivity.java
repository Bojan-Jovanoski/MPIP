package com.example.planner;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.planner.DataBasePackage.Constants;
import com.example.planner.DataBasePackage.DataBaseAdapter;
import com.example.planner.DataBasePackage.DataBaseHelper;
import com.example.planner.Fragments.FragmentChetvrtok;
import com.example.planner.Fragments.FragmentPetok;
import com.example.planner.Fragments.FragmentPonedelnik;
import com.example.planner.Fragments.FragmentSreda;
import com.example.planner.Fragments.FragmentVtornik;
import com.example.planner.ViewAdapter.MyPagerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    int i=1;
    private TabLayout tab;
    private ViewPager vp;
    int currentPos=0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    DataBaseHelper db;

    EditText nameEditText;
    Button saveBtn;
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //VIEWPAGER AND TABS
        vp = (ViewPager) findViewById(R.id.viewpager);
        addPages();

        //SETUP TAB
        tab = (TabLayout) findViewById(R.id.tabs);
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        tab.setupWithViewPager(vp);
        tab.addOnTabSelectedListener(this);



    }



    /*
    DISPLAY INPUT DIALOG
    SAVE
     */
    private void displayDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("SQLITE DATA");
        d.setContentView(R.layout.dialog_layout);

        //INITIALIZE VIEWS
        nameEditText= (EditText) d.findViewById(R.id.nameEditTxt);
        saveBtn= (Button) d.findViewById(R.id.saveBtn);
        sp = (Spinner) d.findViewById(R.id.category_SP);

        //SPINNER ADAPTER
        final String[] categories = {FragmentPonedelnik.newInstance().toString(),
                FragmentVtornik.newInstance().toString(),
                FragmentSreda.newInstance().toString(),
                FragmentChetvrtok.newInstance().toString(),
                FragmentPetok.newInstance().toString()};
        sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories));

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task s = new Task();
                s.setName(nameEditText.getText().toString());
                s.setCategory(sp.getSelectedItem().toString());
                s.setId(Integer.toString(i));


                myRef.child("Planner").child(sp.getSelectedItem().toString()).child(nameEditText.getText().toString()).setValue("true");


                i++;
                if (new DataBaseAdapter(MainActivity.this).saveTask(s)) {
                    nameEditText.setText("");
                    sp.setSelection(0);
                } else {
                    Toast.makeText(MainActivity.this, "Not Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //SHOW DIALOG
        d.show();


    }


    //FILL TAB PAGES
    private void addPages()
    {
        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.addPage(FragmentPonedelnik.newInstance());
        myPagerAdapter.addPage(FragmentVtornik.newInstance());
        myPagerAdapter.addPage(FragmentSreda.newInstance());
        myPagerAdapter.addPage(FragmentChetvrtok.newInstance());
        myPagerAdapter.addPage(FragmentPetok.newInstance());

        vp.setAdapter(myPagerAdapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp.setCurrentItem(currentPos=tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.addMenu) {
            displayDialog();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    // Write a message to the database


}