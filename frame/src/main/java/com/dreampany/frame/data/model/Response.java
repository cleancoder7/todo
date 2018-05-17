package com.dreampany.frame.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dreampany.frame.data.enums.Status;
import com.google.common.base.Objects;

/**
 * Created by Hawladar Roman on 5/17/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */

public class Response<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public Response(@NonNull Status status, @Nullable String message, @Nullable T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @Override
    public boolean equals(Object inObject) {
        if (this == inObject) {
            return true;
        }
        if (inObject == null || getClass() != inObject.getClass()) {
            return false;
        }
        Response response = (Response) inObject;
        if (status != response.status) {
            return false;
        }
        if (message != null ? !message.equals(response.message) : response.message != null) {
            return false;
        }
        return data != null ? data.equals(response.data) : response.data == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(status, message, data);
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static <T> Response<T> success(@Nullable T data) {
        return new Response<>(Status.SUCCESS, null, data);
    }

    public static <T> Response<T> error(String message, @Nullable T data) {
        return new Response<>(Status.ERROR, message, data);
    }

    public static <T> Response<T> loading(@Nullable T data) {
        return new Response<>(Status.LOADING, null, data);
    }
}
