package com.snghnaveen.mymusicdownload.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.snghnaveen.mymusicdownload.AppConstant.ApiConstant;
import com.snghnaveen.mymusicdownload.Models.ApiResponses.XYZResponse;
import com.snghnaveen.mymusicdownload.R;
import com.snghnaveen.mymusicdownload.Services.ServiceInterface;
import com.snghnaveen.mymusicdownload.Services.RestClient;
import com.snghnaveen.mymusicdownload.Services.Search;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    private ServiceInterface searchFromActivityListener;
    private EditText songInput;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.fragment_home,
                container,
                false);


        songInput = view.findViewById(R.id.song_input);
        Button searchBtn = view.findViewById(R.id.search_btn);


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processSearch();
            }
        });

        return view;
    }

    private void processSearch() {

        if (songInput.getText().toString().trim().equalsIgnoreCase("")) {
            DynamicToast.makeWarning(getActivity(), "Please enter a song name").show();

            return;
        }

        String searchQuery = songInput.getText().toString().trim();


        Search restClient = RestClient.
                getApiClient(ApiConstant.XYX_MUSIC.BASE_URL).
                create(Search.class);


        Call<XYZResponse> searchCall = restClient.
                searchSong(searchQuery,
                        ApiConstant.XYX_MUSIC.DEAFULT_PAGE_SIZE);


        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Searching");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();

        if (getView() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }


        searchCall.enqueue(new Callback<XYZResponse>() {
            @Override
            public void onResponse(Call<XYZResponse> call, Response<XYZResponse> response) {

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().data.isEmpty()) {
                    progress.dismiss();
                    DynamicToast.makeError(getActivity(), "No result found!").show();
                    return;
                }

                progress.dismiss();
                searchFromActivityListener.searchSong(response.body());

            }

            @Override
            public void onFailure(Call<XYZResponse> call, Throwable t) {
                progress.dismiss();
                DynamicToast.makeError(getActivity(), "Something went wrong!").show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        searchFromActivityListener = (ServiceInterface) activity;
    }

}


