package com.bstore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.List;

public class BookListAdapter extends ArrayAdapter<BookPreview> {

    private LayoutInflater inflater;
    private int layout;
    private List<BookPreview> books;
    private View.OnClickListener onClickListener;

    public BookListAdapter(Context context, int resource, List<BookPreview> books, View.OnClickListener _cl) {
        super(context, resource, books);
        this.books = books;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = _cl;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder( convertView);
            convertView.setTag(viewHolder);
            convertView.setOnClickListener(onClickListener);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BookPreview bookPreview = books.get(position);

        viewHolder.id = bookPreview._id;
        viewHolder.imageView.setImageBitmap(bookPreview._img);
        viewHolder.book_name.setText(bookPreview._name);
        viewHolder.author.setText(bookPreview._author);

        return convertView;
    }

    public class ViewHolder {
        final ImageView imageView;
        final TextView book_name, author;
        public int id;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.bookPreview);
            book_name = view.findViewById(R.id.bookName);
            author = view.findViewById(R.id.bookAuthor);
        }
    }
}