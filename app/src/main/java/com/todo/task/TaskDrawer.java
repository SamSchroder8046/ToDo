package com.todo.task;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TaskDrawer extends Composite<VerticalLayout> {

    private final TaskForm form;

    TaskDrawer() {
        var header = new H2("Task Details");
        form = new TaskForm();

        var layout = getContent();
        layout.add(header);
        layout.add(new Scroller(form));
        layout.setHeightFull();
    }

    public void setTaskDetails(TaskDetails taskDetails) {
        form.setFormDataObject(taskDetails);
    }

}
