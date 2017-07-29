package android.tauqeer.syed.movieforweekend.RecyclerView;
/**
 * Copyright (c) 2017 Syed Tauqeer Hasan
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software only for academic purposes and subject to the following conditions:
 * The above copyright notice, the author name and this permission notice shall be included in all copies or substantial portions of the Software.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.tauqeer.syed.movieforweekend.R;
import android.tauqeer.syed.movieforweekend.Utils.NetworkUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Syed Tauqeer Hasan <syedtauqeer.h@gmail.com> on 13/05/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder>{

    private Context context;
    private List<String> postersPath;
    private ItemClickListener itemClickListener;

    public RecyclerViewAdapter(Context context , List<String> postersPaths) {
        this.context = context;
        this.postersPath = postersPaths;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View rcItem = inflater.inflate(R.layout.recycler_view_holder , parent , false);
        ImageViewHolder viewHolder = new ImageViewHolder(rcItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        String posterUrl = NetworkUtils.getMoviePosterUrlString(postersPath.get(position));
        Picasso.with(context).load(posterUrl).into(holder.rcImageView);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position);
            }
        };

        holder.rcImageView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return postersPath.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView rcImageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            rcImageView = (ImageView) itemView.findViewById(R.id.rcimageview);
        }
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

}
