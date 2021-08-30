package com.example.simpletodoapp1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }
    List<String> items;
    OnLongClickListener longClickListener;

    public ItemsAdapter(List<String> items,OnLongClickListener longClickListener) {
        this.items=items;
        this.longClickListener=longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creates each view
        //use layout inflator to inflate a view

        View todoView=LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);

        //wrap it in a viewHolder and return it
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //takes data at a certain position and binds it into a certain viewHolder

        //grab item at a position
        String item = items.get(position);
        //then bind the item into the specific ViewHolder
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        //tells the RV how many items are in the list
        //gets number of items that are avail in the data
        return items.size( );
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem=itemView.findViewById(android.R.id.text1);

        }

        //updates the view inside of the viewholder w/ this data String item
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}

