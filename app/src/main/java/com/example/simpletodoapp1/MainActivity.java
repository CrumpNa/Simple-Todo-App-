
package com.example.simpletodoapp1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd= findViewById(R.id.btnAdd);
        etItem= findViewById(R.id.etItem);
        rvItems= findViewById(R.id.rvItems);

        items=new ArrayList<>();
        items.add("Buy milk");
        items.add("Go to gym");
        items.add("Make sure to have fun");

        ItemsAdapter.OnLongClickListener onLongClickListener=new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //delete the item from the model
                items.remove(position);
                //notify the adapter that we deleted it
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        final ItemsAdapter itemsAdapter=new ItemsAdapter(items,onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                //add item to the model
                items.add(todoItem);
                //notify the adapter that item is inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");//clear edit text box
                Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
                //with stmt above, confirmation pops up that "Item was added"
                saveItems();
            }
        });
    }
    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");

    }
    //this function:
    //reads lines of data file and loads the items
    private void loadItems(){
        try {
            items=new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("Main Activity","Error reading items",e);
            items=new ArrayList<>();
        }

    }
    //saves items by writing them into the data file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("Main Activity","Error writing items",e);
        }


    }
}



