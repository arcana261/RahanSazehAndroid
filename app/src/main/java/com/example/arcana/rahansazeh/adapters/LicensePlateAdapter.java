package com.example.arcana.rahansazeh.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.arcana.rahansazeh.BaseActivity;
import com.example.arcana.rahansazeh.R;
import com.example.arcana.rahansazeh.model.DaoSession;
import com.example.arcana.rahansazeh.model.LicensePlate;
import com.example.arcana.rahansazeh.model.Vehicle;
import com.example.arcana.rahansazeh.model.VehicleDao;
import com.example.arcana.rahansazeh.utils.LicensePlateFormatter;
import com.example.arcana.rahansazeh.utils.NumberPadder;
import com.gurkashi.lava.lambdas.Selector;
import com.gurkashi.lava.queries.stracture.Queriable;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.Collection;
import java.util.List;

/**
 * Created by arcana on 11/11/17.
 */

public class LicensePlateAdapter extends BaseAdapter implements Filterable, ThemedSpinnerAdapter, SpinnerAdapter {
    private Context context;
    private DaoSession session;
    private String criteria;
    private LicensePlateFilter filter;
    private Resources.Theme spinnerTheme;

    public LicensePlateAdapter(Context context, DaoSession session) {
        this.context = context;
        this.session = session;
        this.criteria = null;
        this.filter = null;
        this.spinnerTheme = null;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private QueryBuilder<Vehicle> createBaseQuery(String criteria) {
        VehicleDao vehicleDao = session.getVehicleDao();
        QueryBuilder<Vehicle> result = vehicleDao.queryBuilder();

        if (criteria != null) {
            criteria = criteria
                    .replace(" ", "")
                    .replace("'", "");

            if (criteria.length() > 0) {
                Collection<WhereCondition> allConditions = Queriable.create(criteria.split(" "))
                        .map(new Selector<String, WhereCondition>() {
                            @Override
                            public WhereCondition select(String s) {
                                return new WhereCondition.StringCondition(
                                        VehicleDao.Properties.License.columnName + " LIKE '%" + s + "%'");
                            }
                        }).execute();
                WhereCondition firstCondition = Queriable.create(allConditions).first().execute();
                WhereCondition[] restConditions = Queriable.create(allConditions)
                        .take(1, allConditions.size() - 1)
                        .execute()
                        .toArray(new WhereCondition[0]);

                result = result.where(firstCondition, restConditions);
            }
        }

        result = result.orderAsc(VehicleDao.Properties.License);

        return result;
    }

    private Vehicle getVehicle(int position) {
        return Queriable.create(
            createBaseQuery(criteria)
                    .limit(1)
                    .offset(position)
                    .list()
        ).first().execute();
    }

    @Override
    public int getCount() {
        return (int) createBaseQuery(criteria).count();
    }

    @Override
    public Object getItem(int position) {
        return getVehicle(position);
    }

    @Override
    public long getItemId(int position) {
        return getVehicle(position).getId();
    }

    public Vehicle getVehicleById(long id) {
        VehicleDao vehicleDao = session.getVehicleDao();

        return Queriable.create(
            vehicleDao.queryBuilder()
                .where(VehicleDao.Properties.Id.eq(id))
                .limit(1)
                .offset(0)
                .list()
        ).first().execute();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vehicle vehicle = getVehicle(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_licenseplate, parent, false);

            viewHolder.txtLicensePlate = convertView.findViewById(R.id.txtLicensePlate);
            viewHolder.txtVehicleType = convertView.findViewById(R.id.txtVehicleType);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
            result = convertView;
        }

        viewHolder.txtLicensePlate.setText(LicensePlateFormatter.toString(vehicle.getLicense()));
        viewHolder.txtVehicleType.setText(vehicle.getVehicleType().getTitle());

        return result;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new LicensePlateFilter();
        }

        return filter;
    }

    @Override
    public void setDropDownViewTheme(@Nullable Resources.Theme theme) {
        this.spinnerTheme = theme;
    }

    @Nullable
    @Override
    public Resources.Theme getDropDownViewTheme() {
        return spinnerTheme;
    }

    private static class ViewHolder {
        public TextView txtLicensePlate;
        public TextView txtVehicleType;
    }

    private class LicensePlateFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            result.values = constraint != null ? constraint.toString() : null;

            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            criteria = (String)results.values;

            if (getCount() > 0) {
                notifyDataSetChanged();
            }
            else {
                notifyDataSetInvalidated();
            }
        }
    }
}
