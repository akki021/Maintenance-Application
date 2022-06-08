package com.aaiaero.emaintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class VEATListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    private DatabaseHelper mDatabaseHelper;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = mDatabaseHelper.getItemID(name); //get the id associated with that name
                int itemID = -1;
                //String a = "a", b="b", c="c";
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    //Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    //Cursor data1 = mDatabaseHelper.getCompleteItem(itemID);
                    String[] receivedData = mDatabaseHelper.getCompleteItem(itemID);
                    //String completeItem = mDatabaseHelper.getCompleteItem(itemID);
                    //Log.d(TAG, "onItemClick: The ID is: " + a);

                    //int day = 4;
                    Intent editScreenIntent;
                    switch (receivedData[1]) {
                        case "SurvADSBComsoftDailyActivity": //Add all activities here in the case
                            editScreenIntent = new Intent(VEATListDataActivity.this, SurvADSBComsoftDailyActivity.class);
                            editScreenIntent.putExtra("id", itemID);
                            editScreenIntent.putExtra("name", receivedData[0]);
                            editScreenIntent.putExtra("editTextData", receivedData[2]);
                            editScreenIntent.putExtra("switchData", receivedData[3]);
                            editScreenIntent.putExtra("spinnerData", receivedData[4]);
                            startActivity(editScreenIntent);
                            break;


                        case "ADSBComsoftDailyActivity":
                            editScreenIntent = new Intent(VEATListDataActivity.this, ADSBComsoftDailyActivity.class);
                            editScreenIntent.putExtra("id",itemID);
                            editScreenIntent.putExtra("name",receivedData[0]);
                            editScreenIntent.putExtra("editTextData",receivedData[2]);
                            editScreenIntent.putExtra("switchData", receivedData[3]);
                            editScreenIntent.putExtra("spinnerData", receivedData[4]);
                            startActivity(editScreenIntent);
                            break;

                        case "ADSBDailyActivity":
                            editScreenIntent = new Intent(VEATListDataActivity.this, ADSBDailyActivity.class);
                            editScreenIntent.putExtra("id",itemID);
                            editScreenIntent.putExtra("name",receivedData[0]);
                            editScreenIntent.putExtra("editTextData",receivedData[2]);
                            editScreenIntent.putExtra("switchData", receivedData[3]);
                            editScreenIntent.putExtra("spinnerData", receivedData[4]);
                            startActivity(editScreenIntent);
                            break;


                    }
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}