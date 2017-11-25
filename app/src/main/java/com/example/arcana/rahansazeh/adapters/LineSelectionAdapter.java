package com.example.arcana.rahansazeh.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.arcana.rahansazeh.R;
import com.example.arcana.rahansazeh.model.DaoSession;
import com.example.arcana.rahansazeh.model.Project;
import com.example.arcana.rahansazeh.model.ProjectLine;
import com.example.arcana.rahansazeh.model.ProjectLineDao;
import com.gurkashi.lava.queries.stracture.Queriable;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arcana on 11/25/17.
 */

public class LineSelectionAdapter extends BaseAdapter implements Filterable, ThemedSpinnerAdapter, SpinnerAdapter {
    private Context context;
    private DaoSession session;
    private String criteria;
    private LineTitleFilter filter;
    private Resources.Theme spinnerTheme;
    private Project project;

    private Integer cachedCount;
    private static final int PAGE_SIZE = 20;
    private Map<Integer, List<ProjectLine>> cache;

    public LineSelectionAdapter(Context context, Project project, DaoSession session) {
        this.context = context;
        this.session = session;
        this.criteria = null;
        this.filter = null;
        this.spinnerTheme = null;
        this.project = project;
        this.cachedCount = null;
        this.cache = new HashMap<>();
    }

    private QueryBuilder<ProjectLine> createBaseQuery(String criteria) {
        ProjectLineDao projectLineDao = session.getProjectLineDao();
        WhereCondition filter = null;

        if (criteria != null) {
            criteria = criteria.trim();

            if (criteria.length() > 0) {
                filter = ProjectLineDao.Properties.Title.like(criteria + "%");
            }
        }

        QueryBuilder<ProjectLine> result = projectLineDao.queryBuilder();

        if (filter != null) {
            result = result.where(
                    ProjectLineDao.Properties.ProjectId.eq(project.getId()),
                    filter);
        } else {
            result = result.where(ProjectLineDao.Properties.ProjectId.eq(project.getId()));
        }

        result = result.orderAsc(ProjectLineDao.Properties.SortCriteria);

        return result;
    }

    private List<ProjectLine> getUnCachedBucket(int bucket) {
        return createBaseQuery(criteria)
                .limit(PAGE_SIZE)
                .offset(bucket * PAGE_SIZE)
                .list();
    }

    private List<ProjectLine> getBucket(int bucket) {
        if (cache.containsKey(bucket)) {
            return cache.get(bucket);
        } else {
            List<ProjectLine> result = getUnCachedBucket(bucket);
            cache.put(bucket, result);
            return result;
        }
    }

    private int getUnCachedCount() {
        return (int) createBaseQuery(criteria).count();
    }

    public ProjectLine getProjectLine(int position) {
        int bucket = position / PAGE_SIZE;
        int offset = position % PAGE_SIZE;

        return getBucket(bucket).get(offset);
    }

    public ProjectLine getProjectLineById(long id) {
        ProjectLineDao projectLineDao = session.getProjectLineDao();
        return Queriable.create(projectLineDao.queryBuilder()
                .where(ProjectLineDao.Properties.Id.eq(id))
                .limit(1)
                .offset(0)
                .list()).firstOrNull().execute();
    }

    @Override
    public boolean hasStableIds() {
        return true;
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

    @Override
    public int getCount() {
        if (cachedCount != null) {
            return cachedCount;
        } else {
            int result = getUnCachedCount();
            cachedCount = result;
            return result;
        }
    }

    @Override
    public Object getItem(int position) {
        return getProjectLine(position);
    }

    @Override
    public long getItemId(int position) {
        return getProjectLine(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectLine projectLine = getProjectLine(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_vehicletype, parent, false);

            viewHolder.txtVehicleType = convertView.findViewById(R.id.txtVehicleType);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtVehicleType.setText(projectLine.getTitle());

        return result;
    }

    private static class ViewHolder {
        private TextView txtVehicleType;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new LineTitleFilter();
        }

        return filter;
    }

    private class LineTitleFilter extends Filter {

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
