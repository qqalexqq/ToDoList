package com.amikheev.todolist.helpers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amikheev.todolist.R;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    Context context;
    int layoutResourceId;
    List<Task> data = null;

    public TaskAdapter(Context context, int layoutResourceId, List<Task> data) {
        super(context, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskHolder holder = null;

        if (row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TaskHolder();
            holder.title = (TextView) row.findViewById(R.id.taskTitle);
            holder.date = (TextView) row.findViewById(R.id.taskDate);

            row.setTag(holder);
        }
        else
        {
            holder = (TaskHolder)row.getTag();
        }

        Task task = data.get(position);
        holder.title.setText(task.getTitle());
        holder.title.setTag(task.getId());
        holder.date.setText(task.getDate());

        return row;
    }

    static class TaskHolder
    {
        TextView title;
        TextView date;
    }
}
