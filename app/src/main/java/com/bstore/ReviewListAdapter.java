package com.bstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class ReviewListAdapter extends ArrayAdapter<Review> {

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private LayoutInflater inflater;
    private int layout;
    private List<Review> reviews;

    public ReviewListAdapter(Context context, int resource, List<Review> reviews) {
        super(context, resource, reviews);
        this.reviews = reviews;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder( convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Review review = reviews.get(position);

        viewHolder.review_author.setText("Unknown user");
        viewHolder.review_body.setText(review.Text);

        return convertView;
    }

    private class ViewHolder {
        final RatingBar ratingBar;
        final TextView review_author, review_date, review_body;

        ViewHolder(View view) {
            ratingBar = view.findViewById(R.id.review_rating);
            review_author = view.findViewById(R.id.review_author);
            review_body = view.findViewById(R.id.review_body);
            review_date = view.findViewById(R.id.review_date);
        }
    }
}
