package com.tnr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Inner_List_Adapter extends RecyclerView.Adapter<Inner_List_Adapter.Inner_List_View_Holder>{

    private Context context12;
    private List<Inner_List_Card_Data> inner_list12;
    private Inner_List_Adapter.OniListItemClickListener ilListener;

    public void setilOnItemClickListener(Inner_List_Adapter.OniListItemClickListener listener)
    {
        this.ilListener = listener;
    }

    public interface OniListItemClickListener
    {
        void OnItemClicked(int position);
        void OnItemDeleted(int position);
        void OnItemShared(int position);
        void OnTitleClicked(int position);
    }

    public Inner_List_Adapter(List<Inner_List_Card_Data> inner_list, Context context)
    {
        this.context12 = context;
        this.inner_list12 = inner_list;
    }

    @Override
    public Inner_List_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lists_item,parent,false);
        Inner_List_View_Holder ilvh = new Inner_List_View_Holder(view);
        return ilvh;
    }

    @Override
    public void onBindViewHolder(@NonNull Inner_List_View_Holder holder, int position) {
        Inner_List_Card_Data currentItem = inner_list12.get(position);
        holder.title.setText(currentItem.getTitle());
        holder.index.setText(String.valueOf(position+1)+")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ilListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        ilListener.OnItemClicked(position);
                    }
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ilListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        ilListener.OnItemDeleted(position);
                    }
                }
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ilListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        ilListener.OnItemShared(position);
                    }
                }
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ilListener!=null)
                {
                    int position = holder.getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        ilListener.OnTitleClicked(position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return inner_list12.size();
    }

    public static class Inner_List_View_Holder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView index;
        private ImageButton delete;
        private ImageButton share;

        public Inner_List_View_Holder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Title_sub_card);
            index = itemView.findViewById(R.id.card_sub_index);
            delete = itemView.findViewById(R.id.card_sub_delete);
            share = itemView.findViewById(R.id.card_sub_share);

        }
    }

}
