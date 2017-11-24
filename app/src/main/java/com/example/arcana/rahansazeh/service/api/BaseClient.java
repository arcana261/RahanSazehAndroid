package com.example.arcana.rahansazeh.service.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.ParseException;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

/**
 * Created by arcana on 11/23/17.
 */

public class BaseClient {
    private String baseUrl;
    private SyncHttpClient client;

    protected BaseClient(String baseUrl) {
        client = new SyncHttpClient();
        this.baseUrl = baseUrl;
    }

    private JsonHttpResponseHandler createHandler(final ResponseWrapper result) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        boolean status = response.getBoolean("status");

                        if (status) {
                            Object data = response.get("data");

                            if (data instanceof JSONObject) {
                                result.setResult((JSONObject)data);
                            }
                            else if (data instanceof JSONArray){
                                result.setResult((JSONArray)data);
                            }
                            else {
                                throw new Error("unexpected output");
                            }
                        }
                        else {
                            String description = response.getString("description");

                            throw new Error(description);
                        }
                    }
                    catch (Exception err) {
                        result.setError(err);
                    }
                }
                else {
                    result.setError(new Exception("status code not 200"));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                result.setError(new Exception("call failed"));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                result.setError(new Exception("call failed"));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                result.setError(new Exception("call failed"));
            }
        };
    }

    private ResponseWrapper get(String url) throws Exception {
        final ResponseWrapper result = new ResponseWrapper();

        client.get(getAbsoluteUrl(url), createHandler(result));

        if (result.error != null) {
            throw result.error;
        }
        else {
            return result;
        }
    }

    protected JSONObject getObject(String url) throws Exception {
        ResponseWrapper result = get(url);

        if (result.objectResult == null) {
            throw new Exception("unexpected result");
        }
        else {
            return result.objectResult;
        }
    }

    protected JSONArray getArray(String url) throws Exception {
        ResponseWrapper result = get(url);

        if (result.arrayResult == null) {
            throw new Exception("unexpected result");
        }
        else {
            return result.arrayResult;
        }
    }

    private static class ResponseWrapper {
        private JSONObject objectResult;
        private JSONArray arrayResult;
        private Exception error;

        public ResponseWrapper() {
            this.objectResult = null;
            this.arrayResult = null;
            this.error = null;
        }

        public void setResult(JSONObject result) {
            this.objectResult = result;
        }

        public void setResult(JSONArray result) {
            this.arrayResult = result;
        }

        public void setError(Exception error) {
            this.error = error;
        }
    }

    private ResponseWrapper post(String url, JSONObject params) throws Exception {
        final ResponseWrapper result = new ResponseWrapper();

        StringEntity entity = new StringEntity(params.toString(), "UTF-8");

        client.post(null, getAbsoluteUrl(url), new Header[0], entity,
                "application/json", createHandler(result));

        if (result.error != null) {
            throw result.error;
        }
        else {
            return result;
        }
    }

    protected JSONObject postObject(String url, JSONObject params) throws Exception {
        ResponseWrapper result = post(url, params);

        if (result.objectResult == null) {
            throw new Exception("unexpected response");
        }
        else {
            return result.objectResult;
        }
    }

    protected JSONArray postArray(String url, JSONObject params) throws Exception {
        ResponseWrapper result = post(url, params);

        if (result.arrayResult == null) {
            throw new Exception("unexpected response");
        }
        else {
            return result.arrayResult;
        }
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }
}
