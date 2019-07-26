package com.example.winefi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.winefi.R;
import com.example.winefi.activity.RangeActivity;
import com.example.winefi.objects.WinefiData;
import com.example.winefi.utils.Constant;

import java.util.ArrayList;

public class WinefiListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<WinefiData> data = new ArrayList<>();
    Activity mContext;
    int[] some_array;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 2;

    public WinefiListAdapter(Activity mContext, ArrayList<WinefiData> data) {
        this.mContext = mContext;
        this.data = data;
        TypedArray ta = mContext.getResources().obtainTypedArray(R.array.add_text_color);
        some_array = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            some_array[i] = ta.getColor(i, 0);
        }
        ta.recycle();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_winefi, viewGroup, false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lout_header, viewGroup, false);
            return new HeaderViewHolder(view);
        } else return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        } else if (holder instanceof ViewHolder) {
            ViewHolder itemViewHolder = (ViewHolder) holder;
            try {

                if (position == 1) {
                    itemViewHolder.ivArrow.setVisibility(View.VISIBLE);
                } else {
                    itemViewHolder.ivArrow.setVisibility(View.GONE);
                }

                itemViewHolder.lout_main.setBackgroundColor(some_array[position - 1]);
                itemViewHolder.tvName.setText(data.get(position).getName());
                itemViewHolder.ivLogo.setBackgroundResource(Constant.home_logo[position - 1]);


                itemViewHolder.lout_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (position == 1) {
                            Intent intent = new Intent(mContext, RangeActivity.class);
                            intent.putExtra("pos", position - 1);
                            mContext.startActivityForResult(intent, 11);
                        } else {

                        }

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
        if (position == 0) {
            return TYPE_HEADER;
        }
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
            tvName = (TextView) v.findViewById(R.id.tvName);
            lout_main = (RelativeLayout) v.findViewById(R.id.lout_main);
            ivArrow = (ImageView) v.findViewById(R.id.ivArrow);
        }

    }
}
