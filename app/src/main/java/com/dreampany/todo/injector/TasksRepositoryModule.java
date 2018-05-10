package com.dreampany.todo.injector;

import android.app.Application;

import com.dreampany.frame.executor.AppExecutors;
import com.dreampany.todo.data.source.Local;
import com.dreampany.todo.data.source.Remote;
import com.dreampany.todo.data.source.TasksDataSource;
import com.dreampany.todo.data.source.local.DatabaseManager;
import com.dreampany.todo.data.source.local.LocalTasksDataSource;
import com.dreampany.todo.data.source.local.TaskDao;
import com.dreampany.todo.data.source.remote.RemoteTasksDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hawladar Roman on 2/5/18.
 * Dreampany
 * dreampanymail@gmail.com
 */
@Module
public class TasksRepositoryModule {

    @Singleton
    @Provides
    @Local
    TasksDataSource provideLocalTasksDataSource(AppExecutors executors, TaskDao taskDao) {
        return new LocalTasksDataSource(executors, taskDao);
    }

    @Singleton
    @Provides
    @Remote
    TasksDataSource provideRemoteTasksDataSource() {
        return new RemoteTasksDataSource();
    }

    @Singleton
    @Provides
    DatabaseManager provideDatabase(Application application) {
        return DatabaseManager.onInstance(application.getApplicationContext());
    }

    @Singleton
    @Provides
    TaskDao provideTaskDao(DatabaseManager database) {
        return database.taskDao();
    }

    @Singleton
    @Provides
    AppExecutors provideExecutors() {
        return new AppExecutors(
                new AppExecutors.MainThreadExecutor(),
                new AppExecutors.DiskIOThreadExecutor(),
                new AppExecutors.NetworkIOThreadExecutor()
        );
    }
}
