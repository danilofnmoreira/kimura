package com.danilove.kimura.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.danilove.kimura.R;
import com.danilove.kimura.databinding.ItemContainerTvShowBinding;
import com.danilove.kimura.listeners.WatchlistListener;
import com.danilove.kimura.models.TvShow;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.TvShowsViewHolder> {

    private List<TvShow> tvShows;
    private LayoutInflater layoutInflater;
    private WatchlistListener watchlistListener;

    public WatchlistAdapter(List<TvShow> tvShows, WatchlistListener watchlistListener) {
        this.tvShows = tvShows;
        this.watchlistListener = watchlistListener;
    }

    @NonNull
    @Override
    public TvShowsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding tvShowBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_tv_show, parent, false
        );
        return new TvShowsViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowsViewHolder holder, int position) {
        holder.bindTvShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class TvShowsViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerTvShowBinding itemContainerTvShowBinding;

        public TvShowsViewHolder(ItemContainerTvShowBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding = itemContainerTvShowBinding;
        }

        public void bindTvShow(TvShow tvShow) {
            itemContainerTvShowBinding.setTvShow(tvShow);
            itemContainerTvShowBinding.executePendingBindings();
            itemContainerTvShowBinding.getRoot().setOnClickListener(view -> watchlistListener.onTvShowClicked(tvShow));
            itemContainerTvShowBinding.imageDelete.setOnClickListener(view -> watchlistListener.removeTvShowFromWatchlist(tvShow, getAdapterPosition()));
            itemContainerTvShowBinding.imageDelete.setVisibility(View.VISIBLE);
        }
    }
}
