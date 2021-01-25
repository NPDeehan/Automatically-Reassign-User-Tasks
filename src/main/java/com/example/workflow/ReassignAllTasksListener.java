package com.example.workflow;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;

import javax.inject.Named;
import java.util.List;

@Named("Reassign")
public class ReassignAllTasksListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        TaskService taskService = delegateExecution.getProcessEngineServices().getTaskService();

        String processWorker = (String) delegateExecution.getVariable("ProcessWorker");
        String busKey = (String) delegateExecution.getBusinessKey();

        List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(busKey).list();
        for (Task task : taskList) {
            taskService.setAssignee(task.getId(), processWorker);
        }

    }
}
