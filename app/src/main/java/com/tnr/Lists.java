package com.tnr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Lists extends AppCompatActivity {

    private List<Lists_Card_Data> lstList;
    private RecyclerView lRecyclerView;
    private Lists_Adapter lAdapter;
    private RecyclerView.LayoutManager lLayoutManager;
    private ImageButton add_lst;
    private App_Databse databse;
    private List_Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Lists");

        class loadData extends AsyncTask<Void,Void,Void>
        {
            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                buildRecyclerView();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                databse = Room.databaseBuilder(Lists.this,App_Databse.class,"TnR_App_Database").build();
                dao = databse.list_dao();
                lstList = dao.getAll();
                return null;
            }
        }
        new loadData().execute();

        add_lst = findViewById(R.id.add_list);
        add_lst.setOnClickListener(new View.OnClickListener() {
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
                        dao.insert(new Lists_Card_Data(System.currentTimeMillis(),"New List"));
                        lstList = dao.getAll();
                        return null;
                    }
                }
                new AddList().execute();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.share: shareAll();
                break;
            case android.R.id.home: startActivity(new Intent(Lists.this,Dashboard.class));
                break;
            case R.id.delete_all_lists: deleteall();
                break;
        }
        return true;
    }

    private void deleteall()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Lists.this);
        builder.setTitle("Delete")
        .setMessage("Delete (this/all) list(s)?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                class DeleteAll extends AsyncTask<Void,Void,Void>
                {
                    @Override
                    protected void onPostExecute(Void unused) {
                        super.onPostExecute(unused);
                        buildRecyclerView();
                        Toast.makeText(Lists.this, "All list(s) cleared", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        dao.deleteAll();
                        dao.deleteAllInner();
                        lstList = dao.getAll();
                        return null;
                    }
                }
                new DeleteAll().execute();
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
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
                Intent intent = new Intent(Lists.this, Inner_List.class);
                intent.putExtra("card",lstList.get(position));
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
                                class DeleteList extends AsyncTask<Void,Void,Void>
                                {
                                    @Override
                                    protected void onPostExecute(Void unused) {
                                        super.onPostExecute(unused);
                                        Toast.makeText(Lists.this, "List deleted", Toast.LENGTH_SHORT).show();
                                        buildRecyclerView();
                                    }

                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        dao.deleteInnerSpecific(lstList.get(position).getId());
                                        dao.delete(lstList.get(position));
                                        lstList = dao.getAll();
                                        return null;
                                    }
                                }
                                new DeleteList().execute();
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
                intent.putExtra(Intent.EXTRA_SUBJECT,lstList.get(position).getTitle());
                intent.putExtra(Intent.EXTRA_TEXT,getList(lstList.get(position).getId(),position));
                startActivity(Intent.createChooser(intent,"Share list with.."));
            }

            @Override
            public void OnTitleClicked(int position) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_edit,null);
                EditText titleContainer = view.findViewById(R.id.title_edit_et);
                titleContainer.setText(lstList.get(position).getTitle());
                AlertDialog.Builder builder = new AlertDialog.Builder(Lists.this);
                builder.setTitle("Edit title")
                .setView(view)
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!(titleContainer.getText().toString().trim().equals("")||titleContainer.getText().toString().trim()==null))
                        {
                            class UpdateTitle extends AsyncTask<Void,Void,Void>
                            {
                                @Override
                                protected void onPostExecute(Void unused) {
                                    super.onPostExecute(unused);
                                    buildRecyclerView();
                                }

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    lstList.get(position).setTitle(titleContainer.getText().toString().trim());
                                    dao.update(lstList.get(position));
                                    lstList = dao.getAll();
                                    return null;
                                }
                            }
                            new UpdateTitle().execute();
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

    private String getList(long card_id,int position)
    {
        String finalList = lstList.get(position).getTitle()+"\n";
        ArrayList<Lists_Card_Data> innerList;
        SharedPreferences sharedPreferences = getSharedPreferences("list_sub_sp_"+card_id,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("inner_lst_list",null);
        Type type = new TypeToken<ArrayList<Lists_Card_Data>>(){}.getType();
        innerList = gson.fromJson(json,type);
        if(innerList==null)
        {
            innerList = new ArrayList<Lists_Card_Data>();
        }
        int i=1;
        for(Lists_Card_Data item : innerList)
        {
            finalList += "    "+i+") "+item.getTitle()+"\n";
            i++;
        }
        return finalList;
    }

    private void shareAll()
    {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_edit,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chose a title for final list")
        .setView(view)
        .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String full_list = "";
                int i;
                for(i=0;i<lstList.size();i++)
                {
                    full_list+="    "+String.valueOf(i+1)+") ";
                    String finalList = lstList.get(i).getTitle()+"\n";
                    ArrayList<Lists_Card_Data> innerList;
                    SharedPreferences sharedPreferences = getSharedPreferences("list_sub_sp_"+lstList.get(i).getId(),MODE_PRIVATE);
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("inner_lst_list",null);
                    Type type = new TypeToken<ArrayList<Lists_Card_Data>>(){}.getType();
                    innerList = gson.fromJson(json,type);
                    if(innerList==null)
                    {
                        innerList = new ArrayList<Lists_Card_Data>();
                    }
                    int j=1;
                    for(Lists_Card_Data item1 : innerList)
                    {
                        finalList += "        "+j+") "+item1.getTitle()+"\n";
                        j++;
                    }
                    full_list+=finalList;
                }
                EditText editText = view.findViewById(R.id.title_edit_et);
                String listTitle = editText.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,listTitle);
                intent.putExtra(Intent.EXTRA_TEXT,listTitle+"\n"+full_list);
                startActivity(Intent.createChooser(intent,"Share list with"));
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}