package com.amikheev.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amikheev.todolist.helpers.SwipeDetector;
import com.amikheev.todolist.helpers.Task;
import com.amikheev.todolist.helpers.TaskAdapter;
import com.amikheev.todolist.helpers.TaskDAO;

import java.util.List;


public class MainActivity extends Activity {

    private ListView taskListView;
    private TaskAdapter taskListAdapter;
    private TaskDAO taskDAO;
    private List<Task> tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDAO = new TaskDAO(this);
        tasksList = taskDAO.getTasks();
        taskListAdapter = new TaskAdapter(this,
                R.layout.listview_item_row, tasksList);
        taskListView = (ListView)findViewById(R.id.taskList);

        final SwipeDetector swipeDetector = new SwipeDetector();

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (swipeDetector.isSwipeDetected()) {
                    if (swipeDetector.getAction() == SwipeDetector.Action.RIGHT_TO_LEFT) {
                        TextView taskTitleView = (TextView) ((LinearLayout) view).findViewById(R.id.taskTitle);
                        final String taskTitle = taskTitleView.getText().toString();
                        final long taskId = (long) taskTitleView.getTag();

                        AlertDialog.Builder deletionAlert = new AlertDialog.Builder(MainActivity.this);

                        deletionAlert.setTitle("Delete");
                        deletionAlert.setMessage(
                                "Are you sure want to delete task '"
                                        + taskTitle
                                        + "' ?"
                        );

                        deletionAlert.setNegativeButton("Cancel", null);
                        deletionAlert.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                taskDAO.deleteTask(taskId);
                                tasksList.remove(position - 1);
                                taskListAdapter.notifyDataSetChanged();

                                Toast.makeText(
                                        getApplicationContext(),
                                        "Task '" + taskTitle + "' was successfully deleted.",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        });

                        deletionAlert.show();
                    }
                }
            }
        };

        View header = (View) getLayoutInflater().inflate(R.layout.listview_header_row, null);

        taskListView.addHeaderView(header, null, false);
        taskListView.setAdapter(taskListAdapter);
        taskListView.setOnTouchListener(swipeDetector);
        taskListView.setOnItemClickListener(listener);

        findViewById(R.id.addTaskButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addNew();
            }
        });
    }


    private void addNew() {
        Intent intent = new Intent(this, AddTask.class);

        startActivity(intent);

        this.finish();

        taskDAO.close();
    }
}
