package com.cradlevendor.fcm;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cradlevendor.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

;

public class FcmNotificationsSender {
    String fcmNewID;
    ArrayList<String> userFcmToken;
    String title;
    String body;
    String mail;
    String query;
    String serverKey;
    Activity mActivity;

    private RequestQueue requestQueue;
    private final String postUrl = "https://fcm.googleapis.com/fcm/send";

    public FcmNotificationsSender(String fcmNewID,ArrayList<String> userFcmToken, String title, String body, String emailId, String serverKey,String queryId, Activity mActivity) {
        this.fcmNewID = fcmNewID;
        this.userFcmToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.mActivity = mActivity;
        this.mail = emailId;
        this.serverKey = serverKey;
        this.query = queryId;
    }

    public void SendNotifications() {
        requestQueue = Volley.newRequestQueue(mActivity);
        JSONObject mainObj = new JSONObject();
        JSONObject notification = new JSONObject();
        JSONArray fcmId = new JSONArray();
        try {
            for (int i = 0; i < userFcmToken.size(); i++) {
                fcmId.put(userFcmToken.get(0));
            }
          // fcmId.put(fcmNewID);

            String chatMessage = body;
            String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
            Pattern p  = Pattern.compile(URL_REGEX);
            Matcher m  = p.matcher(body);
            if (m.find()) {
                String extension = body.substring(body.lastIndexOf("."));
                if (extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png") || extension.equals(".pdf") || extension.equals(".docx") || extension.equals(".xlsx")) {
                    if (extension.equals(".jpg") || extension.equals( ".jpeg") || extension.equals( ".png")) {
                            chatMessage = "You have received an Image";
                        }
                    else  {
                            chatMessage = "You have received a File";
                        }
                    }
            }
            mainObj.put("registration_ids", fcmId);
            mainObj.put("priority", "high");
            JSONObject data = new JSONObject();
            notification.put("title", title);
            notification.put("body", chatMessage);
            notification.put("sound", "default");
            notification.put("email", mail);
            notification.put("queryID", query);
            mainObj.put("notification", notification);
            data.put("title", title);
            data.put("body", chatMessage);
            data.put("email", mail);
            data.put("queryID", query);
            data.put("icon", "app_icon");
            mainObj.put("data", data);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // code run is got response
                    Log.d("response", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", error.toString());

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + serverKey);
                    return header;
                }
            };
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
