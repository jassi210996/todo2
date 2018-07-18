package com.example.kingj.todo;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;




public class SettingActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    public  static String Check_k="key";
    String chck;
    public int requet_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Switch switchButton = findViewById(R.id.switch_Settings);


        sharedPreferences=getSharedPreferences("my_sp",MODE_PRIVATE);
        chck= sharedPreferences.getString(Check_k,null);

        if(chck!=null) {
            if (chck.matches("1")) {
                switchButton.setChecked(true);
            } else if (chck.matches("-1")) {
                switchButton.setChecked(false);
            }
        }

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked==true){

                        requet_code=1;
                    if( !(ActivityCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) ){

                        String[] permissions = {Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
                        ActivityCompat.requestPermissions(SettingActivity.this,permissions,1011);
                    }
                }
                else if(isChecked==false)
                {
                    requet_code=2;
//                        Toast.makeText(this, "Grant Permissions", Toast.LENGTH_SHORT).show();
                }
            }
    });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requet_code==1)
        {
            chck="1";
            SharedPreferences.Editor edt = sharedPreferences.edit();
            edt.putString(Check_k,chck);
            edt.commit();
        }
        else
        {
            chck="-1";
            SharedPreferences.Editor edt = sharedPreferences.edit();
            edt.putString(Check_k,chck);
            edt.commit();
        }

        if(requestCode == 1011){

            int smsReadPermission = grantResults[0];
            int smsReceivePermission = grantResults[1];

            if(smsReadPermission == PackageManager.PERMISSION_GRANTED && smsReceivePermission == PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this, "Permissions Granted!", Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(this, "Permissions Denied!", Toast.LENGTH_SHORT).show();


    }
}
