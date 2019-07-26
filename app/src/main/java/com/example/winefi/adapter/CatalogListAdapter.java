package com.example.winefi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.winefi.R;
import com.example.winefi.activity.InformationActivity;
import com.example.winefi.objects.WinefiData;

import java.util.ArrayList;

public class CatalogListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<WinefiData> data = new ArrayList<>();
    Activity mContext;


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 2;

    public CatalogListAdapter(Activity mContext, ArrayList<WinefiData> data) {
        this.mContext = mContext;
        this.data = data;


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_catalog, viewGroup, false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_catalog, viewGroup, false);
            return new HeaderViewHolder(view);
        } else return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HeaderViewHolder) {
            final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;


        } else if (holder instanceof ViewHolder) {
            ViewHolder itemViewHolder = (ViewHolder) holder;
            try {


                itemViewHolder.tvName.setText(data.get(position).getName());

                Glide.with(mContext).load(data.get(position).getIcon())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .into(itemViewHolder.ivLogo);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {


        public HeaderViewHolder(View view) {
            super(view);

        }
    }


    @Override
    public int getItemCount() {
        return this.data.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return TYPE_HEADER;
//        }
        return TYPE_ITEM;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivLogo;
        TextView tvName;
        LinearLayout lout_main;


        public ViewHolder(View v) {
            super(v);
            ivLogo = (ImageView) v.findViewById(R.id.ivLogo);
            tvName = (TextView) v.findViewById(R.id.tvName);
            lout_main = (LinearLayout) v.findViewById(R.id.lout_main);
        }

    }


}
