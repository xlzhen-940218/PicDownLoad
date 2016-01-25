package com.example.picdownload.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.picdownload.R;


/**
 * Created by xlzhen on 10/23 0023.
 * 只有一个按钮的弹窗
 */
public class OneBtnDialogView extends Dialog {
    private TextView titleTextView,contentTextView,OkButton;
    public OneBtnDialogView(Context context) {
        super(context, R.style.transceiver_dialog);

        setContentView(R.layout.chat_prompt_layout);
        titleTextView=(TextView)findViewById(R.id.titleTextView);
        contentTextView=(TextView)findViewById(R.id.contentTextView);
        OkButton=(TextView)findViewById(R.id.OkButton);
    }

    public void setOneBtnDialogView(String title,String content,String btn_text,View.OnClickListener DialogBtnOnClick){
        titleTextView.setText(title);
        contentTextView.setText(content);

        OkButton.setText(btn_text);
        OkButton.setOnClickListener(DialogBtnOnClick);
    }
}
