package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mTexts = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mTitles, ArrayList<String> mTexts) {
        this.mTitles = mTitles;
        this.mTexts = mTexts;
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
        holder.text.setText(mTexts.get(position));
        holder.title.setText(mTitles.get(position));
        holder.notesListR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mTexts.get(position));
                Intent intent = new Intent(v.getContext(), NoteDetail.class);
                intent.putExtra("TITLE", mTitles.get(position));
                intent.putExtra("TEXT", mTexts.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mTexts.size();
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