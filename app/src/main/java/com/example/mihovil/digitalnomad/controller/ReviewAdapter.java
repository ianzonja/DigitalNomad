package com.example.mihovil.digitalnomad.controller;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.Interface.ClickListener;
import com.example.mihovil.digitalnomad.Interface.LongPressListener;
import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.models.Review;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Davor on 15.1.2018..
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    public static class ReviewViewHolder extends RecyclerView.ViewHolder{
        public CardView rCardView;
        public TextView name;
        public TextView lastName;
        public RatingBar grade;
        public TextView comment;
        public int position;

        ReviewViewHolder(final View itemView){
            super(itemView);
            rCardView = (CardView) itemView.findViewById(R.id.cvr);
            name = (TextView) itemView.findViewById(R.id.name);
            lastName = (TextView) itemView.findViewById(R.id.lastName);
            grade = (RatingBar) itemView.findViewById(R.id.reviewRatingBar);
            comment = (TextView) itemView.findViewById(R.id.textReview);
        }
    }

    List<Review> reviews;
    LongPressListener longListener;
    ClickListener clickListener;

    public ReviewAdapter(List<Review> reviews, LongPressListener listener, ClickListener clistener){
        this.reviews = reviews;
        longListener = listener;
        clickListener = clistener;
    }

    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_reviews, parent, false);
        ReviewViewHolder rvh = new ReviewViewHolder(v);
        return rvh;
    }

    public void onBindViewHolder(final ReviewViewHolder holder, int position){
        holder.name.setText(reviews.get(position).name);
        holder.lastName.setText(reviews.get(position).lastName);
        holder.grade.setRating(reviews.get(position).grade);
        holder.comment.setText(reviews.get(position).comment);

        holder.itemView.setClickable(true);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "Position is " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                holder.position = holder.getAdapterPosition();
                clickListener.onPressAction(holder.getAdapterPosition());
            }
        });
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public int getItemCount(){
        return reviews.size();
    }
}
