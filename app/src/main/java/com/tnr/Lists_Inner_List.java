package com.tnr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Lists_Inner_List extends AppCompatActivity {

    private ArrayList<Lists_Card_Data> innerList;
    private RecyclerView ilRecyclerView;
    private Lists_Adapter ilAdapter;
    private RecyclerView.LayoutManager ilLayoutManager;
    private ImageButton add_ilst;
    private String ctitle;
    private long cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_inner_list);
        getSupportActionBar().setHomeButtonEnabled(true);

        ctitle = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(ctitle);
        cid = getIntent().getLongExtra("card_id",0);

        loadData();
        buildRecyclerView();

        add_ilst = findViewById(R.id.add_sub_list);
        add_ilst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                innerList.add(innerList.size(),new Lists_Card_Data(System.currentTimeMillis(),"New List Item"));
                ilAdapter.notifyItemInserted(innerList.size());
                saveData();
            }
        });

    }

    private void buildRecyclerView()
    {
        ilRecyclerView = findViewById(R.id.list_sub_rec_view);
        ilLayoutManager = new LinearLayoutManager(this);
        ilAdapter = new Lists_Adapter(innerList, Lists_Inner_List.this);
        ilRecyclerView.setHasFixedSize(true);
        ilRecyclerView.setLayoutManager(ilLayoutManager);
        ilRecyclerView.setAdapter(ilAdapter);

        ilAdapter.setOnItemClickListener(new Lists_Adapter.OnListItemClickListener() {
            @Override
            public void OnItemClicked(int position) {

            }

            @Override
            public void OnItemDeleted(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Lists_Inner_List.this);
                builder.setTitle("Delete list item")
                        .setMessage("Are you sure you want to delete this list item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                innerList.remove(position);
                                ilAdapter.notifyItemRemoved(position);
                                saveData();
                                Toast.makeText(Lists_Inner_List.this, "List item deleted", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,ctitle+" list item");
                intent.putExtra(Intent.EXTRA_TEXT,innerList.get(position).getTitle());
                startActivity(Intent.createChooser(intent,"Share item with.."));
            }

            @Override
            public void OnTitleClicked(int position) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_edit,null);
                EditText titleContainer = view.findViewById(R.id.title_edit_et);
                titleContainer.setText(innerList.get(position).getTitle());
                AlertDialog.Builder builder = new AlertDialog.Builder(Lists_Inner_List.this);
                builder.setTitle("Edit title")
                        .setView(view)
                        .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!(titleContainer.getText().toString().trim().equals("")||titleContainer.getText().toString().trim()==null))
                                {
                                    innerList.get(position).setTitle(titleContainer.getText().toString().trim());
                                    saveData();
                                    buildRecyclerView();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
            }
        });
    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("list_sub_sp_"+cid,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("inner_lst_list",null);
        Type type = new TypeToken<ArrayList<Lists_Card_Data>>(){}.getType();
        innerList = gson.fromJson(json,type);
        if(innerList==null)
        {
            innerList = new ArrayList<Lists_Card_Data>();
        }
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("list_sub_sp_"+cid,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(innerList);
        editor.putString("inner_lst_list",json);
        editor.apply();
    }

}