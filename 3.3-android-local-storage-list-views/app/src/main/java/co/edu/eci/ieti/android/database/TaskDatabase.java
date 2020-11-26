package co.edu.eci.ieti.android.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.edu.eci.ieti.android.model.Task;
import co.edu.eci.ieti.android.model.User;
import co.edu.eci.ieti.android.persistence.dao.TaskDAO;
import co.edu.eci.ieti.android.persistence.dao.UserDAO;

@Database(entities = {Task.class, User.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDAO taskDAO();
    public abstract UserDAO userDAO();

    private static volatile TaskDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static TaskDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "task_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}