package com.alhusseiny.till_man;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Message> temporaryArray;
    ArrayList<Message> permanentArray;

    public MyAdapter(Context context, ArrayList<Message> data) {
        this.context = context;
        this.temporaryArray = data;
        this.permanentArray=new ArrayList<>();
        this.permanentArray.addAll(data);
    }


    @Override
    public int getCount() {
        return temporaryArray.size();
    }

    @Override
    public Object getItem(int position) {
        return temporaryArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void filter(String text) {

        text=text.toLowerCase();
        temporaryArray.clear();

        if(text.trim().length()==0)
        {
            temporaryArray.addAll(permanentArray);
        }
        else
        {
            for (Message p:permanentArray)
            {
                //|| (p.getCode()+"").contains(text) || (p.getPrice()+"").contains(text)
                if(p.getDate().toLowerCase().contains(text) || p.getFrom().toLowerCase().contains(text)
                        || p.getAmount().toLowerCase().contains(text)   || p.getCode().toLowerCase().contains(text)  )
                {
                    temporaryArray.add(p);
                }
            }
            Log.d("SEARCH","COUNT "+temporaryArray.size());
        }
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;


        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.txtCode = convertView.findViewById(R.id.txtCode);
            viewHolder.txtFrom = convertView.findViewById(R.id.txtFrom);
            viewHolder.txtAmount = convertView.findViewById(R.id.txtAmount);
            viewHolder.txtDate = convertView.findViewById(R.id.txtDate);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Message message = temporaryArray.get(position);
        viewHolder.txtCode.setText(message.getCode());
        viewHolder.txtFrom.setText(message.getFrom());
        viewHolder.txtAmount.setText(message.getAmount());
        viewHolder.txtDate.setText(message.getDate());
        return convertView;
    }

    static class  ViewHolder{
        TextView txtCode;
        TextView txtFrom;
        TextView txtAmount;
        TextView txtDate;
    }
}
