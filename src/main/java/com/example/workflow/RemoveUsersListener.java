package com.example.workflow;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.task.Task;

import javax.inject.Named;
import java.util.List;

@Named("RemoveUsersFromTasks")
public class RemoveUsersListener implements ExecutionListener {


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

        TaskService taskService = delegateExecution.getProcessEngineServices().getTaskService();

        String busKey = (String) delegateExecution.getBusinessKey();

        List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(busKey).list();
        for (Task task : taskList) {
            taskService.setAssignee(task.getId(), null);
        }

    }
}
