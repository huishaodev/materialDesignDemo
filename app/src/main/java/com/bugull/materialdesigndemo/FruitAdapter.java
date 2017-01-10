package com.bugull.materialdesigndemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Fsh on 2017/1/6.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHodler> {

    private Context mContext;

    private List<Fruit> mFruitList;

    static class ViewHodler extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        CardView mCardView;

        public ViewHodler(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.fruit_image);
            mTextView = (TextView) view.findViewById(R.id.fruit_name);
            mCardView = (CardView) view;
        }
    }

    public FruitAdapter(Context mContext, List<Fruit> mFruitList) {
        this.mContext = mContext;
        this.mFruitList = mFruitList;
    }


    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false);
        final ViewHodler viewHodler = new ViewHodler(view);
        viewHodler.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHodler.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Intent intent = new Intent(mContext,FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId());
                mContext.startActivity(intent);
            }
        });
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.mTextView.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }


}
