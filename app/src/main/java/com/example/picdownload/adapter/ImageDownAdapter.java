package com.example.picdownload.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.picdownload.BaseApplication;
import com.example.picdownload.R;
import com.example.picdownload.entity.DownEvent;
import com.example.picdownload.framework.SimpleAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by xlzhen on 1/25 0025.
 */
public class ImageDownAdapter extends SimpleAdapter<DownEvent> {
    public ImageDownAdapter(Context context, List<DownEvent> data) {
        super(context, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relativeLayout:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(((TextView)v.findViewById(R.id.titleView)).getText().toString());
                intent.setData(content_url);
                context.startActivity(intent);
                break;
        }
    }

    public void addData(DownEvent data) {
        boolean isHave=false;
        for(int i=0;i<this.data.size();i++)
            if(data.getTaskId()==this.data.get(i).getTaskId()) {
                this.data.set(i, data);
                isHave=true;
            }

        if(!isHave)
            this.data.add(data);

        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_image_down_layout,parent,false);
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageView);

            viewHolder.titleView=(TextView)convertView.findViewById(R.id.titleView);
            viewHolder.loadingView=(ProgressBar)convertView.findViewById(R.id.loadingView);
            viewHolder.typeView=(TextView)convertView.findViewById(R.id.typeView);
            convertView.setOnClickListener(this);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(data.get(position).getImageUrl(), viewHolder.imageView, BaseApplication.options);

        viewHolder.titleView.setText(data.get(position).getImageUrl());
        viewHolder.loadingView.setProgress(data.get(position).getLoading());
        switch (data.get(position).getType()){
            case complete:
                viewHolder.typeView.setText("图片下载完成");
                viewHolder.typeView.setTextColor(Color.GREEN);
                break;
            case error:
                viewHolder.typeView.setText("图片下载失败");
                viewHolder.typeView.setTextColor(Color.RED);
                break;
            case loading:
                viewHolder.typeView.setText("图片正在");
                viewHolder.typeView.setTextColor(Color.CYAN);
                break;
        }
        return convertView;
    }
    private class ViewHolder{
        private ImageView imageView;
        private TextView titleView,typeView;
        private ProgressBar loadingView;
    }
}
