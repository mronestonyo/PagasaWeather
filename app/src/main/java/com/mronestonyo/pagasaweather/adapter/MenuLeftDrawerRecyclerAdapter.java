package com.mronestonyo.pagasaweather.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mronestonyo.pagasaweather.R;
import com.mronestonyo.pagasaweather.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MenuLeftDrawerRecyclerAdapter extends RecyclerView.Adapter<MenuLeftDrawerRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mGridData;

    public MenuLeftDrawerRecyclerAdapter(Context viewContext) {
        mContext = viewContext;
        mGridData = new ArrayList<>();
    }

    public void add(String item) {
        mGridData.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<String> mGridData) {
        for (String item : mGridData) {
            add(item);
        }
    }

    public void clear() {
        this.mGridData.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuLeftDrawerRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_left_drawer_location_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuLeftDrawerRecyclerAdapter.ViewHolder viewHolder, int i) {
        String location = mGridData.get(i);
        viewHolder.txvWeatherLocation.setText(location);
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txvWeatherLocation;
        private LinearLayout linearLocationItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvWeatherLocation = itemView.findViewById(R.id.txvWeatherLocation);
            linearLocationItem = itemView.findViewById(R.id.linearLocationItem);
            txvWeatherLocation.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            switch (view.getId()) {
                case R.id.txvWeatherLocation: {
                    Log.d("antonhttp", "LOCATION: " + String.valueOf(position));
                    Bundle args = new Bundle();
                    args.putString("LOCATION_NAME", mGridData.get(position));
                    ((MainActivity) mContext).displayView(0, args);
                    break;
                }
            }
        }
    }
}
