package com.dreampany.todo.ui.model;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dreampany.frame.ui.adapter.SmartAdapter;
import com.dreampany.frame.ui.model.BaseItem;
import com.dreampany.todo.R;
import com.dreampany.todo.data.model.Task;

import java.io.Serializable;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * Created by Hawladar Roman on 1/5/18.
 * Dreampany
 * dreampanymail@gmail.com
 */
public class TaskItem extends BaseItem<Task, TaskItem.ViewHolder> {

    public TaskItem(Task item) {
        super(item, R.layout.item_task);
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int position, List<Object> payloads) {
        holder.complete.setChecked(item.isCompleted());
        holder.title.setText(item.getTitle());
    }

    @Override
    public boolean filter(Serializable constraint) {
        return false;
    }

    static final class ViewHolder extends SmartAdapter.SmartViewHolder {

        CheckBox complete;
        TextView title;

        ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            complete = view.findViewById(R.id.checkComplete);
            title = view.findViewById(R.id.viewTitle);
        }
    }
}
