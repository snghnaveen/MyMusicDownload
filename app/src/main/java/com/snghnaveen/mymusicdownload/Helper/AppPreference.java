package com.snghnaveen.mymusicdownload.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.snghnaveen.mymusicdownload.Models.ApiResponses.XYZResponse;
import com.snghnaveen.mymusicdownload.R;

public class AppPreference {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public AppPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(
                String.valueOf(R.string.xyz_preference),
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setXYZResponse(XYZResponse resp) {
        editor.putString(String.valueOf(
                R.string.xyz_resp),
                (new Gson()).toJson(resp));

        editor.commit();
    }

    public XYZResponse getXYZResponse() {
        String strResp = sharedPreferences.getString(
                String.valueOf(R.string.xyz_resp), "");

        return ((new Gson()).fromJson(strResp, XYZResponse.class));
    }

}
