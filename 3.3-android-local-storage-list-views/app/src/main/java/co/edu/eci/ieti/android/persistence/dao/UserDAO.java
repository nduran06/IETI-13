package co.edu.eci.ieti.android.persistence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.edu.eci.ieti.android.model.User;

@Dao
public interface UserDAO {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();
}

