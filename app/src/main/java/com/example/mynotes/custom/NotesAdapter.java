package com.example.mynotes.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.models.Note;
import com.example.mynotes.views.DetailsActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Andrei on 27.01.2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final Context context;
    private final int layout;
    private final List<Note> list;

    public NotesAdapter(Context context, List<Note> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext()).inflate(
                layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id = list.get(position).getId();
        holder.title.setText(list.get(position).getTitle());
        holder.description = list.get(position).getDescription();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault());
        holder.date.setText(sdf.format(list.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        int id;
        String description;
        TextView title;
        TextView date;

        ViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);

                ((Activity)context).overridePendingTransition(R.anim.enter_right_in, R.anim.exit_left_out);
            });
        }
    }
}
