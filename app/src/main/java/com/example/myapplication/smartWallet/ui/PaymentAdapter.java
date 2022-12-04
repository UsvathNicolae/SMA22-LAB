package com.example.myapplication.smartWallet.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.smartWallet.model.Payment;
import com.example.myapplication.smartWallet.types.PaymentType;

import java.util.List;

public class PaymentAdapter extends ArrayAdapter<Payment> {

    private Context context;
    private List<Payment> payments;
    private int layoutResID;

    public PaymentAdapter(Context context, int layoutResourceID, List<Payment> payments) {
        super(context, layoutResourceID, payments);
        this.context = context;
        this.payments = payments;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolder.tIndex = (TextView) view.findViewById(R.id.tIndex);
            itemHolder.tName = (TextView) view.findViewById(R.id.tName);
            itemHolder.lHeader = (RelativeLayout) view.findViewById(R.id.lHeader);
            itemHolder.tDate = (TextView) view.findViewById(R.id.tDate);
            itemHolder.tTime = (TextView) view.findViewById(R.id.tTime);
            itemHolder.tCost = (TextView) view.findViewById(R.id.tCost);
            itemHolder.tType = (TextView) view.findViewById(R.id.tType);
            itemHolder.iDelete = (ImageView) view.findViewById(R.id.iDelete);
            itemHolder.iEdit = (ImageView) view.findViewById(R.id.iEdit);

            view.setTag(itemHolder);

        } else {
            itemHolder = (ItemHolder) view.getTag();
        }

        final Payment pItem = payments.get(position);

        itemHolder.tIndex.setText(String.valueOf(position + 1));
        itemHolder.tName.setText(pItem.getName());
        itemHolder.lHeader.setBackgroundColor(PaymentType.getColorFromPaymentType(pItem.getType()));
        itemHolder.tCost.setText(pItem.getCost() + " LEI");
        itemHolder.tType.setText(pItem.getType());
        itemHolder.tDate.setText("Date: " + pItem.timestamp.substring(0, 10));
        itemHolder.tTime.setText("Time: " + pItem.timestamp.substring(11));
        itemHolder.iEdit.setBackgroundColor(PaymentType.getColorFromPaymentType(pItem.getType()));
        itemHolder.iDelete.setBackgroundColor(PaymentType.getColorFromPaymentType(pItem.getType()));


        itemHolder.iEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "editing ", Toast.LENGTH_SHORT).show();
            }
        });

        itemHolder.iDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "deleting ", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private static class ItemHolder {
        TextView tIndex;
        TextView tName;
        RelativeLayout lHeader;
        TextView tDate, tTime;
        TextView tCost, tType;
        ImageView iEdit, iDelete;
    }
}