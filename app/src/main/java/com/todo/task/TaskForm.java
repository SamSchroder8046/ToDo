package com.todo.task;

import com.vaadin.copilot.shaded.checkerframework.checker.nullness.qual.Nullable;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.Optional;

public class TaskForm extends Composite<FormLayout> {

    private final Binder<TaskDetails> binder;

    TaskForm () {
        // Create components
        var descriptionField = new TextField("Task");
        var dueField = new DateTimePicker();
        dueField.setLabel("Task Due");

        // Layout form
        var layout = getContent();
        layout.add(descriptionField);
        layout.add(dueField);

        // Bind fields
        binder = new Binder<>();
        binder.forField(descriptionField).bind(TaskDetails::getDescription, TaskDetails::setDescription);
        binder.forField(dueField).bind(TaskDetails::getDatetimeDue, TaskDetails::setDatetimeDue);
//        binder.setReadOnly(true);
    }

    public void setFormDataObject(@Nullable TaskDetails taskDetails) {
        binder.setBean(taskDetails);
    }

    public Optional<TaskDetails> getFormDataObject() {
        if (binder.getBean() == null) {
            throw new IllegalStateException("No form data object");
        }
        if (binder.validate().isOk()) {
            return Optional.of(binder.getBean());
        } else {
            return Optional.empty();
        }
    }
}
