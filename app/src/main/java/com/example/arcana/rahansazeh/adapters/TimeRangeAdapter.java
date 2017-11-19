package com.example.arcana.rahansazeh.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.arcana.rahansazeh.R;
import com.example.arcana.rahansazeh.model.TimeRange;
import com.example.arcana.rahansazeh.utils.NumberPadder;

import java.util.List;

/**
 * Created by arcana on 11/18/17.
 */

public class TimeRangeAdapter extends ArrayAdapter<TimeRange> {
    private Context context;

    private static TimeRange[] getTimeRanges(List<TimeRange> list) {
        list.add(0,
                new TimeRange(-1l, 0, 0, 0, 0));

        return list.toArray(new TimeRange[0]);
    }

    public TimeRangeAdapter(@NonNull Context context, List<TimeRange> list) {
        super(context, R.layout.row_vehicletype, getTimeRanges(list));

        this.context = context;
    }

    private static class ViewHolder {
        public TextView txtVehicleType;
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView,
                 @NonNull ViewGroup parent) {
        TimeRange timeRange = getItem(position);
        TimeRangeAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new TimeRangeAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_vehicletype, parent, false);

            viewHolder.txtVehicleType = convertView.findViewById(R.id.txtVehicleType);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (TimeRangeAdapter.ViewHolder)convertView.getTag();
            result = convertView;
        }

        if (timeRange.getId() < 0) {
            viewHolder.txtVehicleType.setText("انتخاب بازه زمانی");
        }
        else {
            StringBuilder builder = new StringBuilder();

            builder.append(timeRange.getEndHour())
                    .append(":")
                    .append(NumberPadder.formatNumber(timeRange.getEndMinute(), 2));

            builder.append(" - ");

            builder.append(timeRange.getStartHour())
                    .append(":")
                    .append(NumberPadder.formatNumber(timeRange.getStartMinute(), 2));

            viewHolder.txtVehicleType.setText(builder.toString());
        }

        if (position < 0) {
            viewHolder.txtVehicleType.setTextColor(Color.GRAY);
        }
        else {
            viewHolder.txtVehicleType.setTextColor(Color.BLACK);
        }

        return result;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
