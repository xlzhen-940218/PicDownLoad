package com.example.picdownload.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.picdownload.R;


/**
 * 自定义Toast显示，主要处理不同分辨率的toast弹出字体大小
 * @date:   2012-10-1
 * @type:   ToastView
 */
public class ToastView {
	static Toast toast =null;
	static View layout = null;
	 public static void showToast(String result,Context context)
	 {
    	if(null ==layout)
    	{
    		LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        layout = inflater.inflate(R.layout.toast,null);
    	}
    	if(null == toast)
    	{
    		toast = new Toast(context);
    		toast.setDuration(Toast.LENGTH_SHORT);
    	}
    	 TextView title = (TextView) layout.findViewById(R.id.tvToastContent);
         title.setText(result);
    	 toast.setView(layout);
    	 toast.setGravity(Gravity.CENTER, 0, 0);
    	 toast.show();
	 }
	 
	 public static void showToast(int strId,Context context)
	 {
    	if(null ==layout)
    	{
    		LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        layout = inflater.inflate(R.layout.toast,null);
    	}
    	if(null == toast)
    	{
    		toast = new Toast(context);
    		toast.setDuration(Toast.LENGTH_SHORT);
    	}
    	 TextView title = (TextView) layout.findViewById(R.id.tvToastContent);
         title.setText(strId);
    	 toast.setView(layout);
    	 toast.show();
	 }
}
