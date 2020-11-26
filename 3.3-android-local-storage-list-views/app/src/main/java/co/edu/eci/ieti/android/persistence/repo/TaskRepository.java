package co.edu.eci.ieti.android.persistence.repo;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import co.edu.eci.ieti.android.database.TaskDatabase;
import co.edu.eci.ieti.android.model.Task;
import co.edu.eci.ieti.android.persistence.dao.TaskDAO;

public class TaskRepository {

    private TaskDAO taskDAO;
    private List<Task> allTasks;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public TaskRepository(Context context) {
        TaskDatabase taskDatabase = TaskDatabase.getDatabase(context);
        this.taskDAO = taskDatabase.taskDAO();
        this.allTasks = taskDAO.getAllTasks();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public List<Task> getAllTasks() {
        return this.allTasks;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> {
            taskDAO.insert(task);
        });
    }
}
