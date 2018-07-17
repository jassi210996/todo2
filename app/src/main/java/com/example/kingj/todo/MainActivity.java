package com.example.kingj.todo;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TaskAdapter adapter;
    public static int Request_code_for_add=1;
    Intent intent;
   List<Task> taskArrayList= new ArrayList<>();
    TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TaskDatabase database = Room.databaseBuilder(getApplicationContext(),TaskDatabase.class,"task_db").allowMainThreadQueries().build();
        taskDao = database.getTaskDao();
        taskArrayList = taskDao.getTasks();

        recyclerView=findViewById(R.id.recyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                intent= new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,Request_code_for_add);

            }
        });

        adapter=new TaskAdapter(this,taskArrayList);
        recyclerView.setAdapter(adapter);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==AddActivity.Result_code_for_add)
        {

            String title = data.getStringExtra(AddActivity.TITLE_KEY);
            String dateString = data.getStringExtra(AddActivity.DATE_KEY);
            String time =data.getStringExtra(AddActivity.TIME_KEY);
            int year  = data.getIntExtra(AddActivity.CYEAR_KEY,1);
            int month  = data.getIntExtra(AddActivity.CMONTH_KEY,1);
            int min  = data.getIntExtra(AddActivity.CMIN_KEY,1);
            int day  = data.getIntExtra(AddActivity.CDATE_KEY,1);
            int hour  = data.getIntExtra(AddActivity.CHOUR_KEY,1);

            Log.d("thefuck"," = =" + dateString +"=" + hour + "=" +month+" = "+title+" = "+time);

            Task task = new Task(dateString,title,time);
//            task.setId();
            taskDao.addTasks(task);
            taskArrayList.add(task);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
