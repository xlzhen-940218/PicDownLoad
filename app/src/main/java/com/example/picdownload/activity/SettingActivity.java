package com.example.picdownload.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.picdownload.R;
import com.example.picdownload.utils.sp.SavePreference;

/**
 * Created by xlzhen on 1/25 0025.
 */
public class SettingActivity extends AppCompatActivity {
    private Switch openMany;
    private Button modifySave;
    private EditText editText;
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        openMany=(Switch)findViewById(R.id.openMany);
        openMany.setChecked(SavePreference.getBoolean(this, "isOpenMany"));

        openMany.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SavePreference.save(SettingActivity.this, "isOpenMany", isChecked);
            }
        });

        editText=(EditText)findViewById(R.id.editText);
        editText.setHint(SavePreference.getStr(this,"SavePath")+"(只允许输入中英文，不可填写符号)");

        modifySave=(Button)findViewById(R.id.modifySave);
        modifySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreference.save(SettingActivity.this, "SavePath", editText.getText().toString());
                editText.setHint(SavePreference.getStr(SettingActivity.this, "SavePath") + "(只允许输入中英文，不可填写符号)");
            }
        });
    }
}
