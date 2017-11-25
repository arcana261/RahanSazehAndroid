package com.example.arcana.rahansazeh.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.arcana.rahansazeh.RahanSazehApp;
import com.example.arcana.rahansazeh.model.DaoSession;

/**
 * Created by arcana on 11/25/17.
 */

public abstract class TaskFragment<ACTIVITY, Progress, Result> extends Fragment {
    protected abstract void onProgressUpdate(ACTIVITY activity, Progress progress);
    protected abstract void onCancelled(ACTIVITY activity);
    protected abstract void onPostExecute(ACTIVITY activity, Result result);
    protected abstract Result doInBackground();
    protected abstract void onParseArguments(@Nullable Bundle arguments);
    protected abstract void onInitialize(ACTIVITY activity);
    protected abstract void onDetached(ACTIVITY activity);

    private InternalTask task;
    private ACTIVITY activity = null;
    private Application application = null;
    private Progress lastProgress = null;
    private Result result = null;
    private boolean hasCalledCancel = false;
    private boolean hasCalledPostExecute = false;
    private boolean hasResult = false;
    private boolean hasProgress = false;
    private boolean isCanceled = false;
    private boolean hasInitialized = false;

    protected void publishProgress(Progress progress) {
        if (task != null) {
            task.manualPublishProgress(progress);
        }
    }

    protected Application getApplication() {
        return application;
    }

    protected DaoSession getDaoSession() {
        return ((RahanSazehApp)application).getDaoSession();
    }

    /**
     * Hold a reference to the parent Activity so we can report the
     * task's current progress and results. The Android framework
     * will pass us a reference to the newly created Activity after
     * each configuration change.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (ACTIVITY)activity;
        this.application = activity.getApplication();

        if (isCanceled) {
            if (!hasCalledCancel) {
                if (!hasInitialized) {
                    hasInitialized = true;
                    onInitialize(this.activity);
                }

                hasCalledCancel = true;
                onCancelled(this.activity);
            }
        }
        else if (hasResult) {
            if (!hasCalledPostExecute) {
                if (!hasInitialized) {
                    hasInitialized = true;
                    onInitialize(this.activity);
                }

                hasCalledPostExecute = true;
                onPostExecute(this.activity, result);
            }
        }
        else if (hasProgress) {
            if (!hasInitialized) {
                hasInitialized = true;
                onInitialize(this.activity);
            }

            onProgressUpdate(this.activity, lastProgress);
        }
    }

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);

        if (getArguments() != null) {
            onParseArguments(getArguments());
        }

        // Create and execute the background task.
        task = new InternalTask();
        task.execute();
    }

    /**
     * Set the callback to null so we don't accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();

        onDetached(activity);
        this.activity = null;
    }

    /**
     * A dummy task that performs some (dumb) background work and
     * proxies progress updates and results back to the Activity.
     *
     * Note that we need to check if the callbacks are null in each
     * method in case they are invoked after the Activity's and
     * Fragment's onDestroy() method have been called.
     */
    private class InternalTask extends AsyncTask<Void, Progress, Result> {

        @Override
        protected void onPreExecute() {
            if (activity != null) {
                hasInitialized = true;
                onInitialize(activity);
            }
        }

        public void manualPublishProgress(Progress progress) {
            publishProgress(progress);
        }

        /**
         * Note that we do NOT call the callback object's methods
         * directly from the background thread, as this could result
         * in a race condition.
         */
        @Override
        protected Result doInBackground(Void... ignore) {
            return TaskFragment.this.doInBackground();
        }

        @Override
        protected void onProgressUpdate(Progress... percent) {
            if (percent.length > 0) {
                lastProgress = percent[0];
                hasProgress = true;

                if (activity != null) {
                    if (!hasInitialized) {
                        hasInitialized = true;
                        onInitialize(activity);
                    }

                    TaskFragment.this.onProgressUpdate(activity, lastProgress);
                }
            }
        }

        @Override
        protected void onCancelled() {
            isCanceled = true;

            if (activity != null) {
                if (!hasInitialized) {
                    hasInitialized = true;
                    onInitialize(activity);
                }

                hasCalledCancel = true;
                TaskFragment.this.onCancelled(activity);
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            TaskFragment.this.result = result;
            hasResult = true;

            if (activity != null) {
                if (!hasInitialized) {
                    hasInitialized = true;
                    onInitialize(activity);
                }

                hasCalledPostExecute = true;
                TaskFragment.this.onPostExecute(activity, result);
            }
        }
    }
}
