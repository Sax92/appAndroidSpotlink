package com.example.sasha.appspotlink;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sasha on 30/07/16.
 */
public class CustomAdapter extends BaseAdapter {
    String [] result;
    Context context;
    String [] text;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Activity activity, String[] input, String[] testo) {
        // TODO Auto-generated constructor stub
        result=input;
        context=activity;
        text=testo;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView var;
        TextView fisso;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.stile_riga_profilo, null);
        holder.var=(TextView) rowView.findViewById(R.id.campoP);
        holder.fisso=(TextView) rowView.findViewById(R.id.titolo);
        holder.var.setText(result[position]);
        holder.fisso.setText(text[position]);
        /**rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });*/
        return rowView;
    }

}
