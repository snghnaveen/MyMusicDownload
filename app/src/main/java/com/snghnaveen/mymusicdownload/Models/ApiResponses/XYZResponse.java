package com.snghnaveen.mymusicdownload.Models.ApiResponses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class XYZResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    public ArrayList<Song> data;

}
