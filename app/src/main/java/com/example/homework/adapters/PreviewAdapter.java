package com.example.homework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework.R;
import com.example.homework.data.Review;

import java.util.List;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.ViewHolder> {

    private List<Review> reviews;
    private Context context;

    public PreviewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public PreviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_card, parent, false);
        return new PreviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewAdapter.ViewHolder holder, int position) {
        if (reviews.size() > 0) {
            Review review = reviews.get(position);
            holder.bind(review);
        } else {
            holder.bind(null);
        }
    }

    @Override
    public int getItemCount() {
        if (reviews.size() > 0) {
            return reviews.size();
        }
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.userName);
            mComment = itemView.findViewById(R.id.commentText);
        }

        public void bind(Review review) {
            if (reviews.size() > 0) {
                mName.setText(review.getAuthor());
                mComment.setText(review.getContent());
            } else {
                mName.setText(context.getString(R.string.nocomm));
            }
        }
    }
}
