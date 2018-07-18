package com.example.kingj.todo;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import static com.example.kingj.todo.SettingActivity.Check_k;

public class SmsReciever extends BroadcastReceiver {

    String name;
    long timeStamp;
    TaskDao taskDao;

    SharedPreferences sharedPreferences;
    public  static String Check_k="key";
    String chck;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        sharedPreferences=context.getSharedPreferences("my_sp",MODE_PRIVATE);
        chck= sharedPreferences.getString(Check_k,null);
        Bundle data = intent.getExtras();


        if(data != null) {
            Object[] pdus = (Object[]) data.get("pdus");

            for (int i = 0; i < pdus.length; ++i) {

                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i], "3gpp");
                name = smsMessage.getDisplayMessageBody();
                timeStamp = smsMessage.getTimestampMillis();

            }

            Task task = new Task(null, name, null);

            if(chck!=null) {
                if (chck.matches("1")) {

                    TaskDatabase database = Room.databaseBuilder(context, TaskDatabase.class, "task_db").allowMainThreadQueries().build();
                    taskDao = database.getTaskDao();
                    taskDao.addTasks(task);

                    Toast.makeText(context, "Todo Added!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
