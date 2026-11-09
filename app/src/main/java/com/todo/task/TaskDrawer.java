package com.todo.task;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TaskDrawer extends Composite<VerticalLayout> {

    TaskDrawer() {
        var header = new H2("Task Details");
        var form = new TaskForm();

        var layout = getContent();
        layout.add(header);
        layout.add(new Scroller(form));
        layout.setHeightFull();
    }

}
