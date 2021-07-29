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

public class Lists_Adapter extends RecyclerView.Adapter<Lists_Adapter.List_View_Holder>{

    private ArrayList<Lists_Card_Data> lstList;
    private OnListItemClickListener lListener;
    private Context lContext;

    public void setOnItemClickListener(OnListItemClickListener listener)
    {
        this.lListener = listener;
    }

    public interface OnListItemClickListener
    {
        void OnItemClicked(int position);
        void OnItemDeleted(int position);
        void OnItemShared(int position);
        void OnTitleClicked(int position);
    }

    public static class List_View_Holder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView index;
        private ImageButton delete;
        private ImageButton share;

        public List_View_Holder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Title_sub_card);
            index = itemView.findViewById(R.id.card_sub_index);
            delete = itemView.findViewById(R.id.card_sub_delete);
            share = itemView.findViewById(R.id.card_sub_share);

        }
    }

    public Lists_Adapter(ArrayList<Lists_Card_Data> arrayList1, Context context1)
    {
        this.lstList = arrayList1;
        this.lContext = context1;
    }

    @NonNull
    @Override
    public List_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lists_item,parent,false);
        Lists_Adapter.List_View_Holder lvh = new Lists_Adapter.List_View_Holder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull List_View_Holder holder, int position) {
        Lists_Card_Data currentItem = lstList.get(position);
        holder.title.setText(currentItem.getTitle());
        holder.index.setText(String.valueOf(position+1)+")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        lListener.OnItemClicked(position);
                    }
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        lListener.OnItemDeleted(position);
                    }
                }
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        lListener.OnItemShared(position);
                    }
                }
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        lListener.OnTitleClicked(position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstList.size();
    }

}
