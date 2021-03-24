package com.example.deko.roomtutorial;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.deko.roomtutorial.Database.Note;
import com.example.deko.roomtutorial.Database.NoteViewModel;
import com.example.deko.roomtutorial.UI.NoteItemRecyclerAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NoteViewModel noteViewModel;
    NoteItemRecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    FloatingActionButton buttonAddNote;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.noteItemRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(floatingClick);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {

                Toast.makeText(getApplicationContext(), "" + notes.size(), Toast.LENGTH_LONG).show();
                recyclerAdapter = new NoteItemRecyclerAdapter(getApplicationContext(), notes);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Add New Note");

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(recyclerAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    View.OnClickListener floatingClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
        }
    };



    public Death deathNote(String howToDie){
        // Code for Anime: Death Note
        if (howToDie.isEmpty()){
            return new Death("Heart Attack");
        }
        else{
            return new Death(howToDie);
        }
    }

    class Death{
        public Death(String deathWay){

        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == ADD_NOTE_REQUEST || requestCode == EDIT_NOTE_REQUEST) && (resultCode == RESULT_OK)){
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            int noteId = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);

            Note note = new Note(title, description, priority);

            if (requestCode == EDIT_NOTE_REQUEST){
                if(noteId == -1){
                    Toast.makeText(this, "Note Cannot Be Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    note.setId(noteId);
                    noteViewModel.update(note);
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                noteViewModel.insert(note);
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
            }
        }

        else{
            Toast.makeText(this, "Note not Saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        noteViewModel.deleteAll();
        switch (item.getItemId()){
            case R.id.menu_delete_all_notes:

        }
        return true;

    }



}
