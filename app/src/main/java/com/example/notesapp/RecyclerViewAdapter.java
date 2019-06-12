package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notesapp.model.Note;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Note> mNotes;
    private Context mContext;
    private int userId;

    public RecyclerViewAdapter(Context mContext, ArrayList<Note> mNotes, int userId) {
        this.mNotes = mNotes;
        this.mContext = mContext;
        this.userId = userId;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.id.setText(mNotes.get(position).getId());
        holder.text.setText(mNotes.get(position).getContent());
        holder.title.setText(mNotes.get(position).getTitle());
        holder.star.setImageDrawable(mContext.getDrawable(!mNotes.get(position).getFavourite() ? R.drawable.ic_star_black_24dp : R.drawable.ic_star_border_black_24dp));
        holder.notesListR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoteDetail.class);

                intent.putExtra("ID", mNotes.get(position).getId());
                intent.putExtra("TITLE", mNotes.get(position).getTitle());
                intent.putExtra("TEXT", mNotes.get(position).getContent());
                intent.putExtra("FAV", mNotes.get(position).getFavourite());
                intent.putExtra("USERID", userId);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView title;
        TextView text;
        ImageView star;
        ConstraintLayout notesListR;

        public ViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.noteId);
            title = itemView.findViewById(R.id.noteTitle);
            text = itemView.findViewById(R.id.noteText);
            star = itemView.findViewById(R.id.noteStar);
            notesListR = itemView.findViewById(R.id.notesList);
        }
    }

}