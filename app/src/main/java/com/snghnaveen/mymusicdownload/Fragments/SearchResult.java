package com.snghnaveen.mymusicdownload.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snghnaveen.mymusicdownload.Activity.MainActivity;
import com.snghnaveen.mymusicdownload.Models.ApiResponses.XYZResponse;
import com.snghnaveen.mymusicdownload.R;
import com.snghnaveen.mymusicdownload.RecyclerViews.ResultSongAdapter;

public class SearchResult extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(
                R.layout.recycler_view_fragment,
                container,
                false);


        final XYZResponse resp = MainActivity.appPreference.getXYZResponse();

        RecyclerView resultRv = view.findViewById(R.id.recycler_search_view_test);
        resultRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        ResultSongAdapter resultSongAdapter = new ResultSongAdapter(resp.data);
        resultRv.setAdapter(resultSongAdapter);

        resultSongAdapter.setOnItemClickListner(new ResultSongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {


                String songTitle = resp.data.get(position).getTitle();
                final String songURL = resp.data.get(position).getDownload();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Download :");
                builder.setMessage(songTitle);
                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(songURL));
                                startActivity(i);

                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

}

