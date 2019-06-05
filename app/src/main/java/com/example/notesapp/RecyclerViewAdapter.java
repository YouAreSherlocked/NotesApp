package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notesapp.model.Note;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Note> mNotes;
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<Note> mNotes) {
        this.mNotes = mNotes;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.text.setText(mNotes.get(position).getContent());
        holder.title.setText(mNotes.get(position).getTitle());
        holder.notesListR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoteDetail.class);
                intent.putExtra("TITLE", mNotes.get(position).getTitle());
                intent.putExtra("TEXT", mNotes.get(position).getContent());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView text;
        ConstraintLayout notesListR;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.noteTitle);
            text = itemView.findViewById(R.id.noteText);
            notesListR = itemView.findViewById(R.id.notesList);
        }
    }

}