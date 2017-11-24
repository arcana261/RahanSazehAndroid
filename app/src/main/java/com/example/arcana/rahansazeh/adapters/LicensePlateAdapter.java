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
import com.example.arcana.rahansazeh.model.Project;
import com.example.arcana.rahansazeh.model.Vehicle;
import com.example.arcana.rahansazeh.model.VehicleDao;
import com.example.arcana.rahansazeh.utils.LicensePlateFormatter;
import com.example.arcana.rahansazeh.utils.NumberPadder;
import com.gurkashi.lava.lambdas.Accumulator;
import com.gurkashi.lava.lambdas.Selector;
import com.gurkashi.lava.queries.stracture.Queriable;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arcana on 11/11/17.
 */

public class LicensePlateAdapter extends BaseAdapter implements Filterable, ThemedSpinnerAdapter, SpinnerAdapter {
    private Context context;
    private DaoSession session;
    private String criteria;
    private LicensePlateFilter filter;
    private Resources.Theme spinnerTheme;
    private Project project;

    private Integer cachedCount;
    private static final int PAGE_SIZE = 20;
    private Map<Integer, List<Vehicle>> cache;

    public LicensePlateAdapter(Context context, Project project, DaoSession session) {
        this.context = context;
        this.session = session;
        this.criteria = null;
        this.filter = null;
        this.spinnerTheme = null;
        this.project = project;
        this.cachedCount = null;
        this.cache = new HashMap<>();
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
                    .replace("'", "")
                    .replace('۱', '1')
                    .replace('۲', '2')
                    .replace('۳', '3')
                    .replace('۴', '4')
                    .replace('۵', '5')
                    .replace('۶', '6')
                    .replace('۷', '7')
                    .replace('۸', '8')
                    .replace('۹', '9')
                    .replace('۰', '0')
                    .replace("\r", "")
                    .replace("\n", "");
        }

        WhereCondition projectCondition = VehicleDao.Properties.ProjectId.eq(project.getId());
        Collection<WhereCondition> allConditions = Collections.emptyList();

        if (criteria != null && criteria.length() > 0) {
            /*
            allConditions = Queriable.create(criteria.split(" "))
                    .map(new Selector<String, WhereCondition>() {
                        @Override
                        public WhereCondition select(String s) {
                            return new WhereCondition.StringCondition(
                                    VehicleDao.Properties.License.columnName + " LIKE '%" + s + "%'");
                        }
                    }).execute();*/

            criteria = Queriable.create(criteria.split("\\s+"))
                    .reverse()
                    .accumulate("", new Accumulator<String>() {
                        @Override
                        public String accumulate(String s, String t1) {
                            return s + t1;
                        }
                    }).execute();

            WhereCondition criteriaCondition = new WhereCondition.StringCondition(
                    VehicleDao.Properties.License.columnName + " LIKE '" + criteria + "%'");

            ArrayList<WhereCondition> helper = new ArrayList<>();
            helper.add(criteriaCondition);

            allConditions = helper;
        }

        if (allConditions.size() > 0) {
            result = result.where(projectCondition,
                    allConditions.toArray(new WhereCondition[0]));
        } else {
            result = result.where(projectCondition);
        }

        result = result.orderAsc(VehicleDao.Properties.License);

        return result;
    }

    private Vehicle getVehicle(int position) {
        int bucket = position / PAGE_SIZE;
        int offset = position % PAGE_SIZE;

        List<Vehicle> list;

        if (cache.containsKey(bucket)) {
            list = cache.get(bucket);
        }
        else {
            list = createBaseQuery(criteria)
                        .limit(PAGE_SIZE)
                        .offset(bucket * PAGE_SIZE)
                        .list();

            cache.put(bucket, list);
        }

        if (offset >= list.size()) {
            return null;
        }
        else {
            return list.get(offset);
        }
    }

    @Override
    public int getCount() {
        if (cachedCount != null) {
            return cachedCount;
        }
        else {
            cachedCount = (int) createBaseQuery(criteria).count();
            return cachedCount;
        }
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
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
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
            criteria = (String) results.values;
            cachedCount = null;
            cache = new HashMap<>();

            if (getCount() > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
