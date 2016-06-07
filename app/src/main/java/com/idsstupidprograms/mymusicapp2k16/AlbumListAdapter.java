package com.idsstupidprograms.mymusicapp2k16;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class AlbumListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Album> mAlbumList;

    public AlbumListAdapter(Context mContext, List<Album> mAlbumList) {
        this.mContext = mContext;
        this.mAlbumList = mAlbumList;
    }


    @Override
    public int getCount() {
        return mAlbumList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAlbumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.row, null);

        TextView largeText = (TextView)v.findViewById(R.id.largeText);
        TextView smallText = (TextView)v.findViewById(R.id.smallText);
        TextView textEvaluation = (TextView)v.findViewById(R.id.textEvaluation);

        //Set text:
        largeText.setText(mAlbumList.get(position).getAlbum());
        smallText.setText(mAlbumList.get(position).getArtist());
        textEvaluation.setText(new Integer(mAlbumList.get(position).getEvaluation()).toString());

        return v;
    }
}
