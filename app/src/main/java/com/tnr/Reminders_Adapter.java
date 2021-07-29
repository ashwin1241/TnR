package com.tnr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Reminders_Adapter extends RecyclerView.Adapter<Reminders_Adapter.Reminder_View_Holder> {

    private ArrayList<Reminders_Card_Data> arrayList;
    private OnReminderItemClickListener rListener;
    private Context rContext;

    public void setOnItemClickListener(OnReminderItemClickListener listener)
    {
        this.rListener = listener;
    }

    public interface OnReminderItemClickListener
    {
        void OnItemClicked(int position);
        void OnDeleteClicked(int position);
    }

    public static class Reminder_View_Holder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView index;
        private ImageButton delete;

        public Reminder_View_Holder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Title_card);
            index = itemView.findViewById(R.id.card_index);
            delete = itemView.findViewById(R.id.card_delete);

        }
    }

    public Reminders_Adapter(ArrayList<Reminders_Card_Data> list, Context context)
    {
        this.arrayList = list;
        this.rContext = context;
    }

    @NonNull
    @Override
    public Reminder_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminders_item,parent,false);
        Reminder_View_Holder rvh = new Reminder_View_Holder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull Reminders_Adapter.Reminder_View_Holder holder, int position) {
        Reminders_Card_Data currentItem = arrayList.get(position);

        holder.title.setText(currentItem.getTitle());
        holder.index.setText(String.valueOf(position+1)+")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(rListener!=null)
            {
                int position = holder.getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION)
                {
                    rListener.OnItemClicked(position);
                }
            }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        rListener.OnDeleteClicked(position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void filterList(ArrayList<Reminders_Card_Data> filterList)
    {
        arrayList = filterList;
        notifyDataSetChanged();
    }

}
