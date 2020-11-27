package co.edu.eci.ieti.android.view.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import co.edu.eci.ieti.R;
import co.edu.eci.ieti.android.model.Task;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder>  {

    private List<Task> taskList;

    public TasksAdapter( List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType )
    {
        System.out.println( "onCreateViewHolder");
        return new ViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.task_row, parent, false ) );
    }

    private String dateToString(Date date) {

        String stringDate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if(date != null){
            stringDate = sdf.format(date);
        }
        else {
            stringDate = sdf.format(new Date());
        }

        return stringDate;
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position )
    {
        Task task = taskList.get( position );
        //TODO implement update view holder using the task values
        holder.taskDescription.setText(String.valueOf(task.getDescription()));
        holder.taskPriority.setText(String.valueOf(task.getPriority()));
        holder.taskDudate.setText(task.getDueDate());
    }

    @Override
    public int getItemCount()
    {
        return taskList != null ? taskList.size() : 0;
    }

    public void updateTasks(List<Task> tasks){
        this.taskList = tasks;
        this.notifyDataSetChanged();

    }

    class ViewHolder
            extends RecyclerView.ViewHolder
    {

        TextView taskDescription;
        TextView taskPriority;
        TextView taskDudate;

        ViewHolder( @NonNull View itemView )
        {

            super( itemView );
            System.out.println("--------------------------------ViewHolder Part------------------------------------");

            this.taskDescription = itemView.findViewById(R.id.taskDesc);
            this.taskPriority = itemView.findViewById(R.id.taskPrior);
            this.taskDudate = itemView.findViewById(R.id.taskDuedate);
        }
    }

}