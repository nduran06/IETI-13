package co.edu.eci.ieti.android.view;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.edu.eci.ieti.android.database.TaskDatabase;
import co.edu.eci.ieti.android.model.Task;
import co.edu.eci.ieti.android.network.RetrofitNetwork;
import co.edu.eci.ieti.android.persistence.repo.TaskRepository;
import co.edu.eci.ieti.android.view.adapter.TasksAdapter;
import retrofit2.Response;

import static co.edu.eci.ieti.android.database.TaskDatabase.databaseWriteExecutor;

public class TaskViewModel extends ViewModel {

    private TaskRepository taskRepository;
    private List<Task> taskList;
    private RetrofitNetwork retrofit;
    private Object dynamicObject;

    public TaskViewModel () {
        this.dynamicObject = new Object();
    }

    private void loadAllTasks (Context context){

        ExecutorService executor = Executors.newFixedThreadPool(1);

        executor.execute( new Runnable() {

            @Override
            public void run() {
                try {
                    synchronized (dynamicObject){
                        Response<List<Task>> response = retrofit.getTaskService().getTaskList().execute();
                        taskList = response.body();
                        System.out.println("--------------------------------Load all tasks------------------------------------");
                        System.out.println(taskList);
                        repoSave(context, taskList);
                        dynamicObject.notify();

                    }

                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        } );
    }

    private void repoSave(Context context, List<Task> taskList){
        this.taskRepository = new TaskRepository(context);

        databaseWriteExecutor.execute(()->{

            for(Task task: taskList){
                taskRepository.insert(task);
                System.out.println("------------- Inserted task ----------------------------------");
                System.out.println(task);
            }

            System.out.println("---------------------- taskList taskRepository ----------------------------------");
            System.out.println(taskRepository.getAllTasks());

        });

    }

    public List<Task> getAllTasks(Context context, String token) {

        this.retrofit = new RetrofitNetwork(token);

        if (this.taskList == null) {
            taskList = new ArrayList<Task>();
            loadAllTasks(context);
        }
        return this.taskList;
    }


    public void insert(Task task) { this.taskRepository.insert(task); }

}
