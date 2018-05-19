package com.dreampany.todo.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.util.StringUtil;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dreampany.frame.data.model.Base;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

@Entity
public class Task extends Base {

    @PrimaryKey
    @NonNull
    private final String id;
    @Nullable
    private String title;
    @Nullable
    private String description;
    private boolean completed;

    @Ignore
    public Task(@NonNull String id) {
        this(id, null);
    }

    @Ignore
    public Task(@NonNull String id, @Nullable String title) {
        this(id, title, null);
    }

    public Task(@NonNull String id, @Nullable String title, @Nullable String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    @Ignore
    private Task(Parcel in) {
        super(in);
        id = in.readString();
        title = in.readString();
        description = in.readString();
        completed = in.readByte() == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (completed ? 1 : 0));
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public boolean equals(Object inObject) {
        if (this == inObject) {
            return true;
        }
        if (inObject == null || getClass() != inObject.getClass()) {
            return false;
        }
        Task task = (Task) inObject;
        return Objects.equal(id, task.id) &&
                Objects.equal(title, task.title) &&
                Objects.equal(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, description);
    }

    @Override
    public String toString() {
        return "Task: " + title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(title)) {
            return title;
        } else {
            return description;
        }
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isActive() {
        return !completed;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(title) && Strings.isNullOrEmpty(description);
    }


}
