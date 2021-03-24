package com.example.deko.roomtutorial.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deko.roomtutorial.AddEditNoteActivity;
import com.example.deko.roomtutorial.Database.Note;
import com.example.deko.roomtutorial.MainActivity;
import com.example.deko.roomtutorial.R;

import java.util.ArrayList;
import java.util.List;

public class NoteItemRecyclerAdapter extends RecyclerView.Adapter<NoteItemRecyclerAdapter.ViewHolder> {

    List<Note> notes = new ArrayList<>();
    Context mContext;

    public NoteItemRecyclerAdapter(Context mContext, List<Note> notes) {
        this.notes = notes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.note_item,viewGroup,false);

        return new NoteItemRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        Note note = notes.get(position);
        viewHolder.noteTitle.setText(note.getTitle());
        viewHolder.noteDescription.setText(note.getDescription());
        viewHolder.notePriority.setText(String.valueOf(note.getPriority()));


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note1 = getNoteAt(position);
                Intent intent = new Intent(mContext, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note1.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note1.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note1.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note1.getPriority());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                //((Activity) mContext).startActivityForResult(intent, MainActivity.EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public Note getNoteAt(int position){
        return notes.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView noteDescription;
        TextView notePriority;
        TextView noteTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteDescription = itemView.findViewById(R.id.descriptionText);
            notePriority = itemView.findViewById(R.id.priorityText);
            noteTitle = itemView.findViewById(R.id.titleText);
        }


    }
}
