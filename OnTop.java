/** change to your own package path */
package com.poya.ontop;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

/**
 * OnTop Notifications API
 * Created by Poya R. on 2016-04-12.
 * Use this api to send notifications to yourself through OnTop Notification mobile app.
 * For more info visit:
 *      https://github.com/ontopapp/ontop-push
 *      https://ontop.tech
 */
public class OnTop
{
    public static final String TAG = "OnTop";
    public static final int VERSION_CODE = 1;
    private final String PUSH_END_POINT = "https://ontop.tech/api/push";

    private String appId = "";
    private String appSecret = "";
    private String message = "";
    private String category = "";
    private String action = "";
    private String react_url = "";
    private JSONObject custom = new JSONObject();

    public OnTop(String appId, String appSecret)
    {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    /**
     * Message that will primarily be shown on the notification
     * Max 250 characters
     *
     * @param message   the text to show on the notification
     * @return self
     */
    public OnTop setMessage(String message)
    {
        this.message = message;
        return this;
    }

    /**
     * (Optional)
     * Add a category to represent the entities/categories. (i.e. "user", "song")
     * Can contain only alphabet, underscore, and numbers with max 64 characters.
     *
     * @param category      Name of the category
     * @return self
     */
    public OnTop setCategory(String category)
    {
        this.category = category;
        return this;
    }

    /**
     * Add a action tag to represent the action performed. (i.e. "like", "add", "share")
     * Can contain only alphabet, underscore, and numbers with max 64 characters.
     *
     * @param action        Name of the action
     * @return self
     */
    public OnTop setAction(String action)
    {
        this.action = action;
        return this;
    }

    /**
     * You can add any external url to your notification.
     * This will show up under your notification as a clickable action that'll take you to the
     * supplied URL.
     *
     * @param url        Target URL
     * @return self
     */
    public OnTop setReact(String url)
    {
        this.react_url = url;
        return this;
    }

    /**
     * Add a custom field to hold any meta data for future reference.
     *
     * @param key       the key of the value being sent (i.e. "user_name")
     * @param value     the value being sent (i.e. "John Doe")
     * @return self
     */
    public OnTop setCustom(String key, String value)
    {
        try {
            this.custom.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add a custom field to hold any meta data for future reference.
     *
     * @param key       the key of the value being sent (i.e. "is_new")
     * @param value     the value being sent (i.e. true)
     * @return self
     */
    public OnTop setCustom(String key, boolean value)
    {
        try {
            this.custom.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add a custom field to hold any meta data for future reference.
     *
     * @param key       the key of the value being sent (i.e. "user_id")
     * @param value     the value being sent (i.e. 343)
     * @return self
     */
    public OnTop setCustom(String key, int value)
    {
        try {
            this.custom.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add a custom field to hold any meta data for future reference.
     *
     * @param key       the key of the value being sent (i.e. "date")
     * @param value     the value being sent (i.e. 1459836752)
     * @return self
     */
    public OnTop setCustom(String key, long value)
    {
        try {
            this.custom.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add a custom field to hold any meta data for future reference.
     *
     * @param key       the key of the value being sent (i.e. "latitude")
     * @param value     the value being sent (i.e. 79.3456)
     * @return self
     */
    public OnTop setCustom(String key, double value)
    {
        try {
            this.custom.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Returns the very base API call url with authentication info to be used for either GET/POST
     * You don't need this if you're using either {@link OnTop#getCompiledUrl()}
     * or {@link OnTop#send()}
     *
     * @return Base url with required auth info
     */
    private String getBaseUrl()
    {
        String url = PUSH_END_POINT
                + "?id="        + this.appId
                + "&key="       + this.appSecret
                + "&api_ver="   + VERSION_CODE;
        return url;
    }

    /**
     * Returns a compiled GET call url based on current values. Note it'll issue a warning
     * to use POST if the URL is too long for a GET call
     * You dont need to use this if you're using {@link OnTop#send()}
     *
     * @return Compiled URL
     */
    public String getCompiledUrl()
    {
        String url = getBaseUrl();
        if (!category.equals(""))   url += "&category=" + this.category;
        if (!action.equals(""))     url += "&action=" + this.action;
        if (!message.equals(""))    url += "&message=" + URLEncoder.encode(this.message);
        if (!react_url.equals(""))  url += "&react_url=" + URLEncoder.encode(this.react_url);
        url += "&custom=" + URLEncoder.encode(custom.toString());

        return url;
    }

    /**
     * Sends the notification through a POST call.
     * If you need to make a GET call use {@link OnTop#getCompiledUrl()}
     * If you want to build you're own POST call use {@link OnTop#getBaseUrl()}
     * to get the base url required for either GET/POST
     */
    public void send()
    {
        final String url = getBaseUrl() + "&is_post=1";

        RequestParams params = new RequestParams();
        if (!category.equals(""))   params.put("category", this.category);
        if (!action.equals(""))     params.put("action", this.action);
        if (!message.equals(""))    params.put("message", this.message);
        if (!react_url.equals(""))  params.put("react_url", this.react_url);
        params.put("custom", custom.toString());

        Log.i(TAG, "POST Url: " + url);
        Log.i(TAG, "POST params: " + params.toString());

        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.post(url, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                Log.i(TAG, "Sending: " + url);
            }

            @Override
            public void onFinish()
            {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response)
            {
                String str_response="";
                try {
                    str_response = new String(response, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try{
                    JSONObject data = new JSONObject(str_response);
                    if (data.getString("status").equals("ok"))
                        Log.i(TAG, "Received: status " + data.getString("status") + " - " + data.getString("msg"));
                    else
                        Log.e(TAG, "Received: status " + data.getString("status") + " - " + data.getString("msg"));

                } catch (Exception t) {
                    Log.e(TAG, "Malformed json response. Received: " + str_response);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    Log.e(TAG, "Call failed. Possibly due to no connection or timeout. Received: " + new String(responseBody, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
