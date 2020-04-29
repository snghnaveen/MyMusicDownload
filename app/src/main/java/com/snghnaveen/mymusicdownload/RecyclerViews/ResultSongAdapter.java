package com.snghnaveen.mymusicdownload.RecyclerViews;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.snghnaveen.mymusicdownload.Models.ApiResponses.Song;
import com.snghnaveen.mymusicdownload.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResultSongAdapter extends RecyclerView.Adapter<ResultSongAdapter.ResultSongHolder> {

    private List<Song> songs;
    private OnItemClickListener mListner;

    public ResultSongAdapter(List<Song> songs) {
        this.songs = songs;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListener listner) {
        mListner = listner;
    }


    @NonNull
    @Override
    public ResultSongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card_view, parent, false);
        return new ResultSongHolder(v, mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultSongHolder holder, int position) {
        Song song = songs.get(position);
        holder.setDetails(song);

    }

    @Override
    public int getItemCount() {
        if (songs != null) {
            return songs.size();
        }
        return 0;
    }


    class ResultSongHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private CardView cardView;
        private TextView tvSongName, tvArtistName;
        private ImageView ivThumbnail;

        public ResultSongHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.song_card_container);
            tvSongName = itemView.findViewById(R.id.song_tv);
            tvArtistName = itemView.findViewById(R.id.artist_tv);
            ivThumbnail = itemView.findViewById(R.id.thumbnail);
            ivThumbnail = itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }


        void setDetails(Song song) {
            tvSongName.setText(song.getTitle());
            tvArtistName.setText(song.getArtist());

            Picasso picasso = new Picasso.Builder(cardView.getContext())
                    .listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            exception.printStackTrace();
                        }
                    }).downloader(new OkHttp3Downloader(cardView.getContext())).build();

            picasso.setLoggingEnabled(true);

            picasso.load(song.getCover()).
                    placeholder(R.drawable.ic_thumbnail).
                    into(ivThumbnail, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            // show img if success
                            ivThumbnail.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            // hide or do nothing on error
                        }
                    });
        }
    }

}
