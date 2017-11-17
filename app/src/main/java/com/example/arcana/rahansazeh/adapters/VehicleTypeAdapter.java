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
import com.example.arcana.rahansazeh.model.DaoSession;
import com.example.arcana.rahansazeh.model.VehicleType;
import com.example.arcana.rahansazeh.model.VehicleTypeDao;

import java.util.List;

/**
 * Created by arcana on 11/11/17.
 */

public class VehicleTypeAdapter extends ArrayAdapter<VehicleType> {
    private DaoSession session;
    private Context context;

    private static VehicleType[] getVehicleTypes(DaoSession session) {
        VehicleTypeDao vehicleTypeDao = session.getVehicleTypeDao();

        List<VehicleType> vehicleTypeList = vehicleTypeDao.loadAll();
        vehicleTypeList.add(0,
                new VehicleType(1-1l, "انتخاب نوع خودرو", -1l));

        return vehicleTypeList.toArray(new VehicleType[0]);
    }

    public VehicleTypeAdapter(@NonNull Context context, DaoSession session) {
        super(context, R.layout.row_vehicletype, getVehicleTypes(session));

        this.context = context;
    }

    private static class ViewHolder {
        public TextView txtVehicleType;
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView,
                 @NonNull ViewGroup parent) {
        VehicleType vehicleType = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_vehicletype, parent, false);

            viewHolder.txtVehicleType = convertView.findViewById(R.id.txtVehicleType);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (VehicleTypeAdapter.ViewHolder)convertView.getTag();
            result = convertView;
        }

        viewHolder.txtVehicleType.setText(vehicleType.getTitle());

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
