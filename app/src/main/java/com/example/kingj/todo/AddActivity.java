package com.example.kingj.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTaskTitle;
    EditText editTaskTime;
    EditText editTaskDate;
    Button button;
    TextView error;
    TaskDao taskDao;

    Intent intent;
    Bundle bundle;
    public  static  int Result_code_for_add=1;
    public static String TITLE_KEY="TASK TITLE";
    public static String TIME_KEY="TASK TIME";
    public static String DATE_KEY="DUE DATE";
    public static String CDATE_KEY="DUE DATE1";
    public static String CMONTH_KEY="DUE DATE2";
    public static String CHOUR_KEY="DUE DATE3";
    public static String CMIN_KEY="DUE DATE4";
    public static String CYEAR_KEY="DUE DATE5";

    String title,date,descrip,time;
    public int hour;
    public int min;
    public int month;
    public int day;
    public int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        error=findViewById(R.id.error);
        editTaskTitle=findViewById(R.id.taskTitle);
        editTaskTime=findViewById(R.id.taskTime);
        editTaskDate=findViewById(R.id.taskDate);
        button=findViewById(R.id.addTask);

        button.setOnClickListener(this);

        intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null)
        {
            Log.i("Intent received","0000");
//                    Bundle bundle = intent.getExtras();
            String title = intent.getStringExtra(Intent.EXTRA_TEXT);

            if (title!=null)
            {
//                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                        String date = sdf.format(new Date());
//
//                        sdf = new SimpleDateFormat("HH:mm");
//                        String str = sdf.format(new Date());
                          editTaskTitle.setText(title);
//                        ettime.setText(str);
//                        etdate.setText(date);
            }
        }

        editTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });
        editTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });


    }


    public void setDate()
    {
        Calendar calendar =Calendar.getInstance();

        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yearIn, int monthOfyear, int dayOfmonth) {

                ++month;

                year=yearIn;
                month=monthOfyear;
                day=dayOfmonth;

                date = day + "/" + month + "/" + year;
                editTaskDate.setText(date);
            }
        },year,month,day);

        datePickerDialog.show();
    }

    public void setTime()
    {
        Calendar calendar = Calendar.getInstance();
        hour =calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfday, int minOfHour) {
                hour = hourOfday;
                min=minOfHour;
                time = hourOfday + ":" + minOfHour;

                editTaskTime.setText(time);

            }
        },hour,min,true);

        timePickerDialog.show();
    }


    @Override
    public void onClick(View v) {


        title=editTaskTitle.getText().toString();
        String timeString=editTaskTime.getText().toString();
        String taskDate = editTaskDate.getText().toString();

        if (intent.getAction()!=null && intent.getAction().equals(Intent.ACTION_SEND)) {

            if(title.matches("")||timeString.matches("")||taskDate.matches("")) {

                error.setVisibility(View.VISIBLE);
            }
            else
            {
                Task task = new Task(taskDate, title, timeString);
                taskDao.addTasks(task);
            }
        }
        else
        {

            if(title.matches("")||timeString.matches("")||taskDate.matches("")) {
                error.setVisibility(View.VISIBLE);

            }
            else
            {
                Intent intent1 = new Intent();
                intent1.putExtra(TITLE_KEY, title);
                intent1.putExtra(DATE_KEY, date);
                intent1.putExtra(CHOUR_KEY, hour);
                intent1.putExtra(CDATE_KEY, day);
                intent1.putExtra(CMONTH_KEY, month);
                intent1.putExtra(CMIN_KEY, min);
                intent1.putExtra(CYEAR_KEY, year);
                intent1.putExtra(TIME_KEY, timeString);

                setResult(Result_code_for_add, intent1);
                finish();
            }

        }

    }
}
