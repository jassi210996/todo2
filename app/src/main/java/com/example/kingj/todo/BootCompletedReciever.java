package com.example.kingj.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.ALARM_SERVICE;

public class BootCompletedReciever extends BroadcastReceiver {

    TaskDao taskDao;
    Task task;
    List<Task> taskArrayList;
    public int hour;
    public int min;
    public int month;
    public int day;
    public int year;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {

            // It is better to reset alarms using Background IntentService
            Intent i = new Intent(context, BootService.class);
            ComponentName service = context.startService(i);

            if (null == service) {
                // something really wrong here
                Log.e(TAG, "Could not start service ");
            }
            else {

                TaskDatabase database = Room.databaseBuilder(context,TaskDatabase.class,"task_db").allowMainThreadQueries().build();
                taskDao = database.getTaskDao();
                taskArrayList = taskDao.getTasks();

                Toast.makeText(context,"Reboot",Toast.LENGTH_LONG).show();

                for(int j =0;j<taskArrayList.size();j++)
                {
                    task=taskArrayList.get(j);
                    hour=task.getHour();
                    min=task.getMin();
                    day=task.getDay();
                    month=task.getMonth();
                    year=task.getYear();

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                    Intent intent1 = new Intent(context,MyReceiver.class);
                    PendingIntent pendingIntent=PendingIntent.getBroadcast(context,1,intent1,0);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year,month,day,hour,min);
                    alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                }
                Log.e(TAG, "Successfully started service ");
            }

        } else {
            Log.e(TAG, "Received unexpected intent " + intent.toString());
        }


    }
}
