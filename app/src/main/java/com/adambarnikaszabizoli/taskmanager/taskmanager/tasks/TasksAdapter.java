package com.adambarnikaszabizoli.taskmanager.taskmanager.tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.adambarnikaszabizoli.taskmanager.taskmanager.R;
import com.adambarnikaszabizoli.taskmanager.taskmanager.TaskListActivity;
import com.adambarnikaszabizoli.taskmanager.taskmanager.TaskManagerApplication;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.TaskRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.UpdateTaskRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.VolleyRequestManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksAdapter extends ArrayAdapter<Task> {
    TaskListActivity context;
    List<Task> tasks;

    public TasksAdapter(TaskListActivity context, List<Task> tasks) {
        super(context, R.layout.activity_task_list, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_view, parent,
                false);

        TextView taskName = (TextView) rowView.findViewById(R.id.taskName);
        Button editButton = (Button) rowView.findViewById(R.id.editButton);
        CheckBox statusBox = (CheckBox) rowView.findViewById(R.id.statusBox);

        statusBox.setChecked(tasks.get(position).isStatus());
        taskName.setText(tasks.get(position).getName());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.goToManageTask(tasks.get(position).getId());
            }
        });

        statusBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTaskStatus(tasks.get(position));
            }
        });

        return rowView;
    }

    protected void updateTaskStatus(Task task) {
        Map<String,String> taskStatus = new HashMap<String, String>();
        taskStatus.put("id", Integer.toString(task.getId()));
        taskStatus.put("status", Integer.toString((!task.isStatus())? 1 :0 ));
        taskStatus.put("token", TaskManagerApplication.getToken().toString());
        System.out.println("The token = " +TaskManagerApplication.getToken());
        System.out.println(task);
        UpdateTaskRequest taskRequest = new UpdateTaskRequest(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println("TASK STATUS UPDATED RESPONSE:");
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("TASK STATUS UPDATE FAILED RESPONSE:");
                System.out.println(error.getMessage());
                System.out.println(error.networkResponse.data);
                System.out.println(error);
            }
        }, taskStatus);
        VolleyRequestManager.getSharedInstance(context).getRequestQueue().add(taskRequest);
    }
}
