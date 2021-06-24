package com.example.assignment_room.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.assignment_room.dao.NoteDao;
import com.example.assignment_room.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public static String DATABASE_NAME = "App Database";

    public abstract NoteDao noteDao();

    public static NoteDatabase INSTANCE;

    public static synchronized NoteDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, NoteDatabase.class, DATABASE_NAME)
                    .addCallback(callback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(INSTANCE).execute();
        }
    };

    static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {

        NoteDao noteDao;

        PopulateAsyncTask(NoteDatabase noteDatabase) {
            noteDao = noteDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1", "Description 1", 1));
            noteDao.insert(new Note("Title2", "Description 2", 2));
            noteDao.insert(new Note("Title3", "Description 3", 3));
            noteDao.insert(new Note("Title4", "Description 4", 4));
            noteDao.insert(new Note("Title5", "Description 5", 5));
            noteDao.insert(new Note("Title6", "Description 6", 6));
            noteDao.insert(new Note("Title7", "Description 7", 7));
            return null;
        }
    }
}
