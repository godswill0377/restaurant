package com.example.winefi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.winefi.R;
import com.example.winefi.activity.InformationActivity;
import com.example.winefi.objects.WinefiData;

import java.util.ArrayList;

public class RangeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<WinefiData> data = new ArrayList<>();
    Activity mContext;


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 2;

    int pos;

    public RangeListAdapter(Activity mContext, ArrayList<WinefiData> data, int pos) {
        this.mContext = mContext;
        this.data = data;
        this.pos = pos;


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_range, viewGroup, false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_range, viewGroup, false);
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

                itemViewHolder.lout_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, InformationActivity.class);
                        intent.putExtra("name", "" + data.get(position).getName());
                        intent.putExtra("alias", "" + data.get(position).getAlis());
                        mContext.startActivityForResult(intent, 11);

                    }
                });


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
        ImageView ivArrow;
        TextView tvName;
        RelativeLayout lout_main;


        public ViewHolder(View v) {
            super(v);
            ivLogo = (ImageView) v.findViewById(R.id.ivLogo);
            ivArrow = (ImageView) v.findViewById(R.id.ivArrow);
            tvName = (TextView) v.findViewById(R.id.tvName);
            lout_main = (RelativeLayout) v.findViewById(R.id.lout_main);
        }

    }


}
