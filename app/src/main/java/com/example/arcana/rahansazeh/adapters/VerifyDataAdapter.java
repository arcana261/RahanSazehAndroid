package com.example.arcana.rahansazeh.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.arcana.rahansazeh.R;
import com.example.arcana.rahansazeh.model.OutgoingVehicleRecord;
import com.example.arcana.rahansazeh.validation.TextValidator;

import java.util.ArrayList;

/**
 * Created by arcana on 11/8/17.
 */

/*
public class VerifyDataAdapter extends ArrayAdapter<OutgoingVehicleRecord> implements View.OnClickListener {
    private ArrayList<Record> dataSet;
    private Context context;

    private static class ViewHolder {
        TextView txtArrivalTime;
        TextView txtDepartureTime;
    }

    public VerifyDataAdapter(ArrayList<Record> records, Context context) {
        super(context, R.layout.row_record, records);

        this.dataSet = records;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Record record = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_record, parent, false);

            viewHolder.txtArrivalTime = convertView.findViewById(R.id.txtArrivalTime);
            viewHolder.txtDepartureTime = convertView.findViewById(R.id.txtDepartureTime);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
            result = convertView;
        }

        viewHolder.txtArrivalTime.setText(record.getArrivalTime().toString());
        viewHolder.txtDepartureTime.setText(record.getDepartureTime().toString());

        return result;
    }

    @Override
    public void onClick(View v) {

    }
}

**/
