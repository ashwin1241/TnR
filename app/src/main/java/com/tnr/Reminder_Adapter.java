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

public class Reminder_Adapter extends RecyclerView.Adapter<Reminder_Adapter.Reminder_View_Holder> {

    private ArrayList<Reminder_card_data> arrayList;
    private OnItemClickListener rListener;
    private Context rContext;

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.rListener = listener;
    }

    public interface OnItemClickListener
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

    public Reminder_Adapter(ArrayList<Reminder_card_data> list, Context context)
    {
        this.arrayList = list;
        this.rContext = context;
    }

    @NonNull
    @Override
    public Reminder_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item,parent,false);
        Reminder_View_Holder rvh = new Reminder_View_Holder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull Reminder_Adapter.Reminder_View_Holder holder, int position) {
        Reminder_card_data currentCard = arrayList.get(position);
        holder.title.setText(currentCard.getTitle());
        holder.index.setText(String.valueOf(currentCard.getIndex())+")");
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

    public void filterList(ArrayList<Reminder_card_data> filterList)
    {
        arrayList = filterList;
        notifyDataSetChanged();
    }

}