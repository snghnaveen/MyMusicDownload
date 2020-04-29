package com.snghnaveen.mymusicdownload.Services;

import com.snghnaveen.mymusicdownload.AppConstant.ApiConstant;
import com.snghnaveen.mymusicdownload.Models.ApiResponses.XYZResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Search {

    @Headers({
            "user-agent:" + ApiConstant.XYX_MUSIC.HEADER
    })
    @GET("search")
    Call<XYZResponse> searchSong(
            @Query("query") String name,
            @Query("page") String page
    );

}
