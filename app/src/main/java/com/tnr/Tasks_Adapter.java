package com.tnr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Tasks_Adapter extends RecyclerView.Adapter<Tasks_Adapter.Task_View_Holder>{

    private ArrayList<Tasks_Card_Data> tskList;
    private Context tContext;
    private OnItemClickListener tListener;

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.tListener = listener;
    }

    public interface OnItemClickListener
    {
        void OnItemClicked(int position);
        void OnItemDeleted(int position);
        void OnItemShared(int position);
    }

    public static class Task_View_Holder extends RecyclerView.ViewHolder
    {
        private TextView tsktitle;
        private TextView tskdescription;
        private TextView tskdateandtime;
        private ImageView tskdelete;
        private ImageView tskshare;

        public Task_View_Holder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            tsktitle = itemView.findViewById(R.id.tsk_title);
            tskdescription = itemView.findViewById(R.id.tsk_description);
            tskdateandtime = itemView.findViewById(R.id.tsk_date_and_time);
            tskdelete = itemView.findViewById(R.id.tsk_card_delete);
            tskshare = itemView.findViewById(R.id.tsk_card_share);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnItemClicked(position);
                        }
                    }
                }
            });
            tskshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnItemShared(position);
                        }
                    }
                }
            });
            tskdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnItemDeleted(position);
                        }
                    }
                }
            });

        }
    }

    public Tasks_Adapter(ArrayList<Tasks_Card_Data> arrayList, Context context)
    {
        this.tskList = arrayList;
        this.tContext = context;
    }

    @NonNull
    @Override
    public Task_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_item,parent,false);
        Tasks_Adapter.Task_View_Holder tvh = new Tasks_Adapter.Task_View_Holder(v,tListener);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull Tasks_Adapter.Task_View_Holder holder, int position) {
        Tasks_Card_Data currentItem = tskList.get(position);

        holder.tsktitle.setText(currentItem.getTitle());
        holder.tskdescription.setText(currentItem.getDescription());
        holder.tskdateandtime.setText(currentItem.getDate()+" ; "+currentItem.getTime());

    }

    @Override
    public int getItemCount() {
        return tskList.size();
    }

}
