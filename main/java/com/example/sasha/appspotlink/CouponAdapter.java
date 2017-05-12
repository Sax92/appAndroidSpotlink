package com.example.sasha.appspotlink;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by sasha on 01/08/16.
 */
public class CouponAdapter extends BaseAdapter {
    Context context;
    String [] text;
    private static LayoutInflater inflater=null;
    public CouponAdapter(Activity couponactivity, String [] testo) {
        // TODO Auto-generated constructor stub
        context=couponactivity;
        text=testo;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return text.length;
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

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.stile_riga_coupon, null);
        holder.var=(TextView) rowView.findViewById(R.id.campoC);
        holder.var.setText(text[position]);
        /*rowView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        // TODO Auto-generated method stub
            In,InfoCouponActivity.class);
            CouponActivity.this.startActivity(i);


        }
        });*/
        return rowView;
    }


}
