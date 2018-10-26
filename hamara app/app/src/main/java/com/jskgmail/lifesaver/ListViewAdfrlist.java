
package com.jskgmail.lifesaver;

/**
 * Created by JASPREET SINGH on 18-10-2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JASPREET SINGH on 06-08-2018
 */

public class ListViewAdfrlist extends BaseAdapter {
    Activity mcontext;
    ArrayList<String> title;
    ArrayList<String> description;

int numb=1;
    public ListViewAdfrlist(MainActivity context, ArrayList<String> arrayList, ArrayList<String> arrayList1) {
        mcontext=context;
        title=arrayList;
        description=arrayList1;

    }





    @Override
    public int getCount() {

        return title.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView txtviewtitle;
        TextView txtviewdesc;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        LayoutInflater inflater=mcontext.getLayoutInflater();
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.myfriendslist,null);
            holder=new ViewHolder();
            holder.txtviewtitle=(TextView)convertView.findViewById(R.id.textView68);

            holder.txtviewdesc=(TextView)convertView.findViewById(R.id.textView69);

holder.txtviewtitle.setText(title.get(position));
            holder.txtviewdesc.setText(description.get(position));



        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }




        return convertView;
    }



}

