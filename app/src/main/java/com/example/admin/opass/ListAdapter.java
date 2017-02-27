package com.example.admin.opass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 16/02/2017.
 */

public class ListAdapter extends ArrayAdapter {
    List list=new ArrayList();
    static class LayoutHandler
    {
        TextView text_name,text_email,text_pass;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutHandler layoutHandler;
        if(row==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler=new LayoutHandler();
            layoutHandler.text_name= (TextView) row.findViewById(R.id.user_name);
            layoutHandler.text_email= (TextView) row.findViewById(R.id.user_email);
            layoutHandler.text_pass= (TextView) row.findViewById(R.id.user_password);
            row.setTag(layoutHandler);
        }
        else
        {
            layoutHandler= (LayoutHandler) row.getTag();

        }
        DataProvider dataProvider= (DataProvider) this.getItem(position);
        layoutHandler.text_name.setText(dataProvider.getDname());
        layoutHandler.text_email.setText(dataProvider.getDemail());
        layoutHandler.text_pass.setText(dataProvider.getDpass());
        return row;

    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    public ListAdapter(Context context, int resource) {
        super(context, resource);

    }
}
