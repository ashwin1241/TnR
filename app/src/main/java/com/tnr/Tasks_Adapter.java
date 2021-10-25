package com.tnr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Tasks_Adapter extends RecyclerView.Adapter<Tasks_Adapter.Task_View_Holder>{

    private List<Tasks_Card_Data> tskList;
    private Context tContext;
    private OnTaskItemClickListener tListener;

    public void setOnItemClickListener(OnTaskItemClickListener listener)
    {
        this.tListener = listener;
    }

    public interface OnTaskItemClickListener
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

        public Task_View_Holder(@NonNull View itemView, OnTaskItemClickListener listener) {
            super(itemView);

            tsktitle = itemView.findViewById(R.id.tsk_title);
            tskdescription = itemView.findViewById(R.id.tsk_description);
            tskdateandtime = itemView.findViewById(R.id.tsk_date_and_time);
            tskdelete = itemView.findViewById(R.id.tsk_card_delete);
            tskshare = itemView.findViewById(R.id.tsk_card_share);

        }
    }

    public Tasks_Adapter(List<Tasks_Card_Data> arrayList, Context context)
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        tListener.OnItemClicked(position);
                    }
                }
            }
        });
        holder.tskshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        tListener.OnItemShared(position);
                    }
                }
            }
        });
        holder.tskdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        tListener.OnItemDeleted(position);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tskList.size();
    }

}
