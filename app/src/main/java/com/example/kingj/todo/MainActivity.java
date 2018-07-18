package com.example.kingj.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String Title_k = "title";
    public static String Time_k ="time";
    public static String Date_k ="date";
    public static String Id_k ="id";

    public static int Request_code_for_edit=2;

    RecyclerView recyclerView;
    TaskAdapter adapter;
    public static int Request_code_for_add=1;
    Intent intent;
    ImageView delete;
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

        delete=findViewById(R.id.delete);
        recyclerView=findViewById(R.id.recyclerView);
//        deleteTask.setOnClickListener(this);

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

        adapter=new TaskAdapter(this, taskArrayList, new OnItemClick() {
            @Override
            public void itemClick(View v, int position) {
//                Log.d("main","clicked position:" + position);
                int viewId = v.getId();
                if(viewId==R.id.taskTitle) {

                    Toast.makeText(MainActivity.this,"edit",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, EditActivity.class);

                            Task itemTask = taskArrayList.get(position);
                            String title = itemTask.getTaskTitle();
                            String date = itemTask.getTaskDate();
                            String time = itemTask.getTaskTime();
                            long id = itemTask.getId();

                            intent.putExtra(Title_k, title);
                            intent.putExtra(Date_k, date);
                            intent.putExtra(Time_k, time);
                            intent.putExtra(Id_k, id);
                            intent.putExtra("pos", position);

                            startActivityForResult(intent, Request_code_for_edit);
                }
                else if(viewId==R.id.delete)
                {

                    final Task task = taskArrayList.get(position);
                    final int positionArray=position;

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Delete");
                    builder.setCancelable(false);
                    builder.setMessage("Do you really want to remove selected task?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            long id = task.getId();
                         taskDao.deleteTasks(task);
//                            long id = task.getId();
//                            String[] selectionArgs = {id + ""};
                            taskArrayList.remove(positionArray);
                            adapter.notifyDataSetChanged();

                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            Intent intent = new Intent(MainActivity.this,MyReceiver.class);
                            PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,1,intent,0);
//                            Calendar calendar = Calendar.getInstance();
                            pendingIntent.cancel();
                            alarmManager.cancel(pendingIntent);


                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog dialog=builder.create();
                    dialog.show();
                }

            }


        });
        recyclerView.setAdapter(adapter);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
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

            long id = taskDao.getMaxId();

           Task task1=taskDao.getTaskAtMax(id);

           taskArrayList.set(taskArrayList.size()-1,task1);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(this,MyReceiver.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day,hour,min);
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
         }

         if(resultCode== EditActivity.Result_code_for_edit)
         {

             String title = data.getStringExtra(EditActivity.Title_k);
             String dateString = data.getStringExtra(EditActivity.Date_k);
             String time =data.getStringExtra(EditActivity.Time_k);
             int year  = data.getIntExtra(EditActivity.CYEAR_KEY,-1);
             int month  = data.getIntExtra(EditActivity.CMONTH_KEY,-1);
             int min  = data.getIntExtra(EditActivity.CMIN_KEY,-1);
             int day  = data.getIntExtra(EditActivity.CDATE_KEY,-1);
             int hour  = data.getIntExtra(EditActivity.CHOUR_KEY,-1);
             long id = data.getLongExtra(EditActivity.Cid_KEY,-1);
             Log.d("thefuck"," = =" + dateString +"=" + hour + "=" +month+" = "+title+" = "+time);


             int id1 = (int)id;

//             Task task = new Task(dateString,title,time);
//            task.setId();

             Task task1=new Task(dateString,title,time);
             task1.setId(id1);


             taskArrayList.set(data.getIntExtra("pos",0),task1);
             adapter.notifyDataSetChanged();

            // Task task=taskArrayList.get(id1);

             taskDao.updateTask(task1);

             AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
             Intent intent = new Intent(this,MyReceiver.class);
             PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);
             Calendar calendar = Calendar.getInstance();
             calendar.set(year,month,day,hour,min);

             alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),10000,pendingIntent);
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




    @Override
    public void onClick(View v) {

    }
}
