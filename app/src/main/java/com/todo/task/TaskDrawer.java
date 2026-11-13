package com.todo.task;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.concurrent.Callable;

public class TaskDrawer extends Composite<VerticalLayout> {

    @FunctionalInterface
    interface SaveCallback {
        TaskDetails save(TaskDetails taskDetails);
    }

    @FunctionalInterface
    interface ErrorCallback {
        void handleException(RuntimeException e);
    }

    private final SaveCallback saveCallback;
    private final ErrorCallback errorCallback;
    private final Callable backFunc;
    private final TaskForm form;

    TaskDrawer(SaveCallback saveCallback, ErrorCallback errorCallback, Callable backFunc) {
        this.saveCallback = saveCallback;
        this.errorCallback = errorCallback;
        this.backFunc = backFunc;

        var header = new H2("Task Details");
        form = new TaskForm();

        var saveButton = new Button("Save", e -> save());
        var backButton = new Button("",e -> {
            try {
                this.backFunc.call();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        var backIcon = VaadinIcon.ARROW_BACKWARD.create();
        backButton.setIcon(backIcon);

        var layout = getContent();
        layout.add(backButton);
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
            try {
                var saved = saveCallback.save(taskDetails);
                form.setFormDataObject(saved);
            } catch (RuntimeException e) {
                errorCallback.handleException(e);
            }
        });
    }

}
