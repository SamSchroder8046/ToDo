package com.todo.task;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.masterdetaillayout.MasterDetailLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.time.format.DateTimeFormatter;

@PageTitle("Tasks")
@Route("")
@RouteAlias("/tasks")
public class TasksView extends MasterDetailLayout {
    TasksView(TaskItemRepository repository) {

        // Create components
        var searchField = new TextField();
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setValueChangeMode(ValueChangeMode.LAZY);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm");

        var grid = new Grid<TaskItem>();
        grid.addColumn(TaskItem::description).setHeader("Task").setSortProperty(TaskItem.SORT_PROPERTY_DESCRIPTION);
        grid.addComponentColumn(task -> {
            Checkbox checkbox = new Checkbox();
            checkbox.setValue(task.complete());
            return checkbox;
        }).setHeader("Complete").setSortProperty(TaskItem.SORT_PROPERTY_COMPLETE);
        grid.addColumn(task -> task.timestampDue() != null ? task.timestampDue().format(formatter) : "No deadline").setHeader("Due").setSortProperty(TaskItem.SORT_PROPERTY_TIMESTAMP_DUE);
        grid.addColumn(task -> task.timestampCreated() != null ? task.timestampCreated().format(formatter) : "").setHeader("Created").setSortProperty(TaskItem.SORT_PROPERTY_TIMESTAMP_CREATED);
        grid.setItemsPageable(pageable -> repository.findByDescriptionContainingIgnoreCase(searchField.getValue(), pageable).getContent());

        var drawer = new TaskDrawer();

        searchField.addValueChangeListener(e -> grid.getDataProvider().refreshAll());

        // Layout view
        setSizeFull();
        setDetailSize("300px");
        setExpandMaster(true);

        var listLayout = new VerticalLayout(new H1("Tasks"), searchField, grid);
        listLayout.setSizeFull();
        grid.setSizeFull();

        setMaster(listLayout);
        setDetail(drawer);
    }
}
