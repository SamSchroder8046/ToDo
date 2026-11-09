package com.todo.task;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

public class TaskForm extends Composite<FormLayout> {

    TaskForm () {
        var descriptionField = new TextField("Task");
        var dueField = new DateTimePicker();
        dueField.setLabel("Task Due");

        var layout = getContent();
        layout.add(descriptionField);
        layout.add(dueField);
    }
}
