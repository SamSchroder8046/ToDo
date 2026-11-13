package com.todo.task;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.concurrent.Callable;

public class TaskDrawer extends Composite<VerticalLayout> {

    @FunctionalInterface
    interface SaveCallback {
        TaskDetails save(TaskDetails taskDetails);
    }

    @FunctionalInterface
    interface DeleteCallback {
        void delete(TaskDetails taskDetails);
    }

    @FunctionalInterface
    interface ErrorCallback {
        void handleException(RuntimeException e);
    }

    private final SaveCallback saveCallback;
    private final DeleteCallback deleteCallback;
    private final ErrorCallback errorCallback;
    private final Callable backFunc;
    private final TaskForm form;
    private TaskDetails taskDetails;

    TaskDrawer(SaveCallback saveCallback, DeleteCallback deleteCallback, ErrorCallback errorCallback, Callable backFunc) {
        this.saveCallback = saveCallback;
        this.deleteCallback = deleteCallback;
        this.errorCallback = errorCallback;
        this.backFunc = backFunc;

        var header = new H2("Task Details");
        form = new TaskForm();

        var saveButton = new Button("Save", e -> save());
        var deleteButton = new Button("Delete", e -> delete());
        deleteButton.addThemeVariants(ButtonVariant.ERROR);

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
        layout.add(new HorizontalLayout(saveButton, deleteButton));
        layout.setHeightFull();
    }

    public void setTaskDetails(TaskDetails taskDetails) {
        this.taskDetails = taskDetails;
        form.setFormDataObject(taskDetails);
    }

    private void save() {
        form.getFormDataObject().ifPresent(taskDetails -> {
            try {
                var saved = saveCallback.save(taskDetails);
                setTaskDetails(saved);
            } catch (RuntimeException e) {
                errorCallback.handleException(e);
            }
        });
    }

    private void delete() {
        var dialog = new ConfirmDialog();
        dialog.setHeader("Delete Product");
        dialog.setText("Are you sure you want to permanently delete this product?");
        dialog.setCancelable(true);
        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");
        dialog.addConfirmListener(event -> {
            try {
                deleteCallback.delete(taskDetails);
            } catch (RuntimeException e) {
                errorCallback.handleException(e);
            }
        });
        dialog.open();
    }

}
