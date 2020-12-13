package com.danilove.kimura.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.danilove.kimura.R;
import com.danilove.kimura.databinding.ItemContainerSliderImageBinding;
import com.danilove.kimura.listeners.TvShowsListener;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {

    private List<String> imagesUrls;
    private LayoutInflater layoutInflater;
    private TvShowsListener tvShowsListener;

    public ImageSliderAdapter(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerSliderImageBinding itemContainerSliderImageBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_slider_image, parent, false
        );
        return new ImageSliderViewHolder(itemContainerSliderImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindSliderImage(imagesUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return imagesUrls.size();
    }

    class ImageSliderViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerSliderImageBinding itemContainerSliderImageBinding;

        public ImageSliderViewHolder(ItemContainerSliderImageBinding itemContainerSliderImageBinding) {
            super(itemContainerSliderImageBinding.getRoot());
            this.itemContainerSliderImageBinding = itemContainerSliderImageBinding;
        }

        public void bindSliderImage(String imageUrl) {
            itemContainerSliderImageBinding.setImageUrl(imageUrl);
        }
    }
}
