package com.example.marmm.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.marmm.popularmovie.R;
import com.example.marmm.popularmovie.model.Video;

import java.util.List;

/**
 * Created by marmm on 24/01/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private VideoAdapter.VideoClickListener mVideoClickListener;
    private List<Video> mVideos;
    private Context mContext;


    public void setVideoData(List<Video> videoData) {
        mVideos = videoData;
        notifyDataSetChanged();
    }

    public interface VideoClickListener {
        void VideoOnClick(Video video);
    }

    public VideoAdapter(VideoAdapter.VideoClickListener mVideoClickListener) {
        this.mVideoClickListener = mVideoClickListener;

    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.video_item, parent, shouldAttachToParentImmediately);

        // Return a new holder instance
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {
        Video video = mVideos.get(position);
        holder.textViewVideo.setText(video.getName());
    }


    @Override
    public int getItemCount() {
        if (mVideos == null) return 0;
        return mVideos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageButton play;
        public TextView textViewVideo;

        public ViewHolder(View itemView) {
            super(itemView);
            play = itemView.findViewById(R.id.playButton);
            textViewVideo = itemView.findViewById(R.id.textViewVideo);
            play.setOnClickListener(this);
            textViewVideo.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mVideoClickListener.VideoOnClick(mVideos.get(clickedPosition));

        }


    }

}


