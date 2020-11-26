package co.edu.eci.ieti.android.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task {

    @NonNull
    @PrimaryKey
    private long id;

    private String description;

    private int priority;

    private String dueDate;

    public Task()
    {
    }

    public Task( long id, String description, int priority, Date dueDate )
    {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.dueDate = "dueDate";
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority( int priority )
    {
        this.priority = priority;
    }

    public String getDueDate()
    {
        return dueDate;
    }

    public void setDueDate( String dueDate )
    {
        this.dueDate = dueDate;
    }

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Task{" + "description='" + description + '\'' + ", priority=" + priority + ", dueDate=" + dueDate + '}';
    }
}
