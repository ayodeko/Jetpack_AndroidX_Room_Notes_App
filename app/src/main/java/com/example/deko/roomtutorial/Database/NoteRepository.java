package com.example.deko.roomtutorial.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {

    NoteDao noteDao;
    LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application.getApplicationContext());
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getALlNotes();
    }

    public void insert(Note note){
        InsertNoteAsyncTask insertNoteAsyncTask = new InsertNoteAsyncTask(noteDao);
        insertNoteAsyncTask.execute(note);
    }

    public void update(Note note){
        UpdateNoteAsyncTask updateNoteAsyncTask = new UpdateNoteAsyncTask(noteDao);
        updateNoteAsyncTask.execute(note);
    }

    public void delete(Note note){
        DeleteNoteAsyncTask deleteNoteAsyncTask = new DeleteNoteAsyncTask(noteDao);
        deleteNoteAsyncTask.execute(note);
    }

    public void deleteAllNotes(){
        DeleteAllNoteAsyncTask deleteAllNoteAsyncTask = new DeleteAllNoteAsyncTask(noteDao);
        deleteAllNoteAsyncTask.execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }


    public static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    public static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    public static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    public static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void>{

        NoteDao noteDao;

        public DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }



}
