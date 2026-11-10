package com.todo.task;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TaskDrawer extends Composite<VerticalLayout> {

    @FunctionalInterface
    interface SaveCallback {
        TaskDetails save(TaskDetails taskDetails);
    }

    private final SaveCallback saveCallback;
    private final TaskForm form;

    TaskDrawer(SaveCallback saveCallback) {
        this.saveCallback = saveCallback;

        var header = new H2("Task Details");
        form = new TaskForm();

        var saveButton = new Button("Save", e -> save());

        var layout = getContent();
        layout.add(header);
        layout.add(new Scroller(form));
        layout.add(saveButton);
        layout.setHeightFull();
    }

    public void setTaskDetails(TaskDetails taskDetails) {
        form.setFormDataObject(taskDetails);
    }

    private void save() {
        form.getFormDataObject().ifPresent(taskDetails -> {
            var saved = saveCallback.save(taskDetails);
            form.setFormDataObject(saved);
        });
    }

}
