package com.dreampany.todo.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.dreampany.frame.data.dao.BaseDao;
import com.dreampany.todo.data.model.Task;

import java.util.List;

@Dao
public interface TaskDao extends BaseDao<Task> {

    @Query("select count(*) from task")
    int count();

    @Query("select * from task")
    LiveData<List<Task>> getTasks();

    @Query("select * from task where id = :id limit 1")
    LiveData<Task> getTaskById(String id);

    @Query("update task set completed = :completed WHERE id = :id")
    void updateCompleted(String id, boolean completed);

    @Query("delete from task where id = :id")
    int deleteById(String id);

    @Query("delete from task")
    void deleteAll();

    @Query("delete from task where completed = 1")
    int deleteCompleted();

}
