package com.tnr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Lists extends AppCompatActivity {

    private ArrayList<Lists_Card_Data> lstList;
    private RecyclerView lRecyclerView;
    private Lists_Adapter lAdapter;
    private RecyclerView.LayoutManager lLayoutManager;
    private ImageButton add_lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Lists");

        loadData();
        buildRecyclerView();

        add_lst = findViewById(R.id.add_list);
        add_lst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstList.add(lstList.size(),new Lists_Card_Data(System.currentTimeMillis(),"New List"));
                lAdapter.notifyItemInserted(lstList.size());
                saveData();
            }
        });

    }

    private void buildRecyclerView()
    {
        lRecyclerView = findViewById(R.id.list_rec_view);
        lLayoutManager = new LinearLayoutManager(this);
        lAdapter = new Lists_Adapter(lstList, Lists.this);
        lRecyclerView.setHasFixedSize(true);
        lRecyclerView.setLayoutManager(lLayoutManager);
        lRecyclerView.setAdapter(lAdapter);

        lAdapter.setOnItemClickListener(new Lists_Adapter.OnListItemClickListener() {
            @Override
            public void OnItemClicked(int position) {
                Intent intent = new Intent(Lists.this,Lists_Inner_List.class);
                intent.putExtra("card_id",lstList.get(position).getId());
                intent.putExtra("title",lstList.get(position).getTitle());
                startActivity(intent);
            }

            @Override
            public void OnItemDeleted(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Lists.this);
                builder.setTitle("Delete list")
                        .setMessage("Are you sure you want to delete this list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lstList.remove(position);
                                lAdapter.notifyItemRemoved(position);
                                saveData();
                                Toast.makeText(Lists.this, "List deleted", Toast.LENGTH_SHORT).show();
                                buildRecyclerView();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
            }

            @Override
            public void OnItemShared(int position) {
                Toast.makeText(Lists.this, "Share clicked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnTitleClicked(int position) {

            }
        });

    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("list_activity_sp",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("lst_list",null);
        Type type = new TypeToken<ArrayList<Lists_Card_Data>>(){}.getType();
        lstList = gson.fromJson(json,type);
        if(lstList==null)
        {
            lstList = new ArrayList<Lists_Card_Data>();
        }
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("list_activity_sp",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lstList);
        editor.putString("lst_list",json);
        editor.apply();
    }

}