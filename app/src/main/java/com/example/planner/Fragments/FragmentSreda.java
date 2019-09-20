package com.example.planner.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planner.DataBasePackage.DataBaseAdapter;
import com.example.planner.DataBasePackage.DataBaseHelper;
import com.example.planner.R;
import com.example.planner.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FragmentSreda extends Fragment implements View.OnCreateContextMenuListener{

    ListView lv;
    Button refreshBtn;
    ArrayAdapter<Task> adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public static FragmentSreda newInstance()
    {
        return new FragmentSreda();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_sreda,null);

        initializeViews(rootView);
        loadData();

        return rootView;
    }

    private void initializeViews(View rootView)
    {
        lv= (ListView) rootView.findViewById(R.id.sre_LV);
        refreshBtn= (Button) rootView.findViewById(R.id.Refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
    }

    private void loadData()
    {
        DataBaseAdapter db=new DataBaseAdapter(getActivity());
        adapter=new ArrayAdapter<Task>(getActivity(),android.R.layout.simple_list_item_1,db.retrieveTask("Среда"));
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

    }

    @Override
    public String toString() {
        return "Среда";
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.sre_LV) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.contextmenusre, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.delete3:
                DataBaseHelper db=new DataBaseHelper(getContext());
                Task task = adapter.getItem(info.position);
                db.deleteTask(task);
                adapter.remove(task);
                myRef.child("Planner").child(task.getCategory()).child(task.getName()).removeValue();
                break;

            default:
                return super.onContextItemSelected(item);
        }
        return super.onContextItemSelected(item);
    }
}