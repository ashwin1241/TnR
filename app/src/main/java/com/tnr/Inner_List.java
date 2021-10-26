package com.tnr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.AsyncQueryHandler;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import java.util.List;

public class Inner_List extends AppCompatActivity {

    private List<Inner_List_Card_Data> innerList;
    private RecyclerView ilRecyclerView;
    private Inner_List_Adapter ilAdapter;
    private RecyclerView.LayoutManager ilLayoutManager;
    private ImageButton add_ilst;
    private String ctitle;
    private long parent_cid;
    private App_Databse databse;
    private Inner_List_Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_list);
        getSupportActionBar().setHomeButtonEnabled(true);

        Lists_Card_Data cardData = (Lists_Card_Data) getIntent().getSerializableExtra("card");
        ctitle = cardData.getTitle();
        getSupportActionBar().setTitle(ctitle);
        parent_cid = cardData.getId();

        class loadData extends AsyncTask<Void,Void,Void>
        {
            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                buildRecyclerView();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                databse = Room.databaseBuilder(Inner_List.this,App_Databse.class,"App_Database").build();
                dao = databse.inner_list_dao();
                innerList = dao.getAll(parent_cid);
                return null;
            }
        }
        new loadData().execute();

        add_ilst = findViewById(R.id.add_sub_list);
        add_ilst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class AddList extends AsyncTask<Void,Void,Void>
                {
                    @Override
                    protected void onPostExecute(Void unused) {
                        super.onPostExecute(unused);
                        buildRecyclerView();
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        dao.insert(new Inner_List_Card_Data(System.currentTimeMillis(),parent_cid,"New List Item"));
                        innerList = dao.getAll(parent_cid);
                        return null;
                    }
                }
                new AddList().execute();
            }
        });

    }

    private void buildRecyclerView()
    {
        ilRecyclerView = findViewById(R.id.list_sub_rec_view);
        ilLayoutManager = new LinearLayoutManager(this);
        ilAdapter = new Inner_List_Adapter(innerList,Inner_List.this);
        ilRecyclerView.setHasFixedSize(true);
        ilRecyclerView.setLayoutManager(ilLayoutManager);
        ilRecyclerView.setAdapter(ilAdapter);

        ilAdapter.setilOnItemClickListener(new Inner_List_Adapter.OniListItemClickListener(){
            @Override
            public void OnItemClicked(int position) {

            }

            @Override
            public void OnItemDeleted(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Inner_List.this);
                builder.setTitle("Delete list item")
                        .setMessage("Are you sure you want to delete this list item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                class DeleteItem extends AsyncTask<Void,Void,Void>
                                {
                                    @Override
                                    protected void onPostExecute(Void unused) {
                                        super.onPostExecute(unused);
                                        buildRecyclerView();
                                        Toast.makeText(Inner_List.this, "List item deleted", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        dao.delete(innerList.get(position));
                                        innerList = dao.getAll(parent_cid);
                                        return null;
                                    }
                                }
                                new DeleteItem().execute();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Inner_List.this);
                builder.setTitle("Edit title")
                        .setView(view)
                        .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!(titleContainer.getText().toString().trim().equals("")||titleContainer.getText().toString().trim()==null))
                                {
                                    class UpdateItem extends AsyncTask<Void,Void,Void>
                                    {
                                        @Override
                                        protected void onPostExecute(Void unused) {
                                            super.onPostExecute(unused);
                                            buildRecyclerView();
                                        }

                                        @Override
                                        protected Void doInBackground(Void... voids) {
                                            innerList.get(position).setTitle(titleContainer.getText().toString().trim());
                                            dao.update(innerList.get(position));
                                            innerList = dao.getAll(parent_cid);
                                            return null;
                                        }
                                    }
                                    new UpdateItem().execute();
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

}