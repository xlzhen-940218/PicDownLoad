package com.example.picdownload.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.picdownload.BaseApplication;
import com.example.picdownload.R;
import com.example.picdownload.activity.MainActivity;
import com.example.picdownload.entity.ImageEntity;
import com.example.picdownload.framework.SimpleAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xlzhen on 1/24 0024.
 */
public class ImageAdapter extends SimpleAdapter<ImageEntity> {
    public ImageAdapter(Context context, List<ImageEntity> data) {
        super(context, data);
    }

    @Override
    public void onClick(View v) {
        int position=(int)v.getTag();
        switch (v.getId()){
            case R.id.imageView:
            case R.id.checkbox:
                data.get(position).setIsCheck(!data.get(position).isCheck());
                boolean isSingCheck=false;
                for(int i=0;i<data.size();i++)
                    if(data.get(i).isCheck())
                        isSingCheck=true;

                if(isSingCheck)
                    ((MainActivity)context).changeFloatingActionButton(View.VISIBLE);
                notifyDataSetChanged();
                break;
        }
    }

    public List<ImageEntity> getSelectImage(){
        List<ImageEntity> imageEntities=new ArrayList<>();
        for(int i=0;i<data.size();i++)
            if(data.get(i).isCheck())
                imageEntities.add(data.get(i));

        return imageEntities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_image_layout,parent,false);
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageView);
            viewHolder.imageView.setOnClickListener(this);
            viewHolder.checkBox=(CheckBox)convertView.findViewById(R.id.checkbox);
            viewHolder.checkBox.setOnClickListener(this);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(data.get(position).getImageUrl(), viewHolder.imageView, BaseApplication.options);

        if(data.get(position).isCheck())
            viewHolder.checkBox.setChecked(true);
        else
            viewHolder.checkBox.setChecked(false);

        viewHolder.imageView.setTag(position);
        viewHolder.checkBox.setTag(position);
        return convertView;
    }
    private class ViewHolder{
        private ImageView imageView;
        private CheckBox checkBox;
    }
}
