package com.example.kingj.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTaskTitle;
    EditText editTaskTime;
    EditText editTaskDate;
    TextView error;
    Button button;

    public static String Title_k="title";
    public static String Time_k="time";
    public static String Date_k ="date";
    public static String CDATE_KEY="DUE DATE1";
    public static String CMONTH_KEY="DUE DATE2";
    public static String CHOUR_KEY="DUE DATE3";
    public static String CMIN_KEY="DUE DATE4";
    public static String CYEAR_KEY="DUE DATE5";
    public static String Cid_KEY="id";


    public int hour;
    public int min;
    public int month;
    public int day;
    public int year;

    int position;

    public static int Result_code_for_edit = 2;

    Intent intent;
    String title;
    String date;
    String time;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTaskDate=findViewById(R.id.taskDate);
        editTaskTime=findViewById(R.id.taskTime);
        editTaskTitle=findViewById(R.id.taskTitle);
        error=findViewById(R.id.error);
        button=findViewById(R.id.addTask);

        intent=getIntent();

        title=intent.getStringExtra(MainActivity.Title_k);
        date=intent.getStringExtra(MainActivity.Date_k);
        time=intent.getStringExtra(MainActivity.Time_k);
        id=intent.getLongExtra(MainActivity.Id_k,1);

        position = intent.getIntExtra("pos",0);


        editTaskTitle.setText(title);
        editTaskDate.setText(date);
        editTaskTime.setText(time);

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

        button.setOnClickListener(this);

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

        TimePickerDialog timePickerDialog = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        title = editTaskTitle.getText().toString();
        time = editTaskTime.getText().toString();
        date = editTaskDate.getText().toString();

        if (title.matches("") || time.matches("") || date.matches(""))
        {
            error.setVisibility(View.VISIBLE);
        }
        else {
            Intent data = new Intent();

            data.putExtra(Title_k, title);
            data.putExtra(Time_k, time);
            data.putExtra(Date_k, date);
            data.putExtra(CHOUR_KEY, hour);
            data.putExtra(CDATE_KEY, day);
            data.putExtra(CMONTH_KEY, month);
            data.putExtra(CMIN_KEY, min);
            data.putExtra(CYEAR_KEY, year);
            data.putExtra(Cid_KEY, id);
            data.putExtra("pos", position);

            setResult(Result_code_for_edit, data);
            finish();
        }

    }
}
