package com.todo.task;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.masterdetaillayout.MasterDetailLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.dao.OptimisticLockingFailureException;

import java.time.format.DateTimeFormatter;

@Route("")
@RouteAlias("/tasks")
@PageTitle("Tasks")
public class TasksView extends MasterDetailLayout {
    TasksView(TaskService service) {

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
        grid.setItemsPageable(pageable -> service.findItems(searchField.getValue(), pageable)).setIdentifierProvider(TaskItem::taskId);

        var drawer = new TaskDrawer(taskDetails -> {
            var saved = service.save(taskDetails);
            grid.getDataProvider().refreshAll();
            setDetail(null);
            grid.deselectAll();
            return saved;
        }, this::handleException,
                () -> {
            setDetail(null);
            return null;
        });

        searchField.addValueChangeListener(e -> grid.getDataProvider().refreshAll());

        grid.addSelectionListener(e -> {
            var taskDetails = e.getFirstSelectedItem().flatMap(item -> service.findDetailsById(item.taskId()));
            taskDetails.ifPresent(drawer::setTaskDetails);
            setDetail(taskDetails.isPresent() ? drawer : null);
        });
        addBackdropClickListener(e -> grid.deselectAll());
        addDetailEscapePressListener(e -> grid.deselectAll());

        // Layout view
        setSizeFull();
        setDetailSize("400px");
        setExpandMaster(true);

        var listLayout = new VerticalLayout(new H1("Tasks"), searchField, grid);
        listLayout.setSizeFull();
        grid.setSizeFull();

        setMaster(listLayout);
        setDetail(null);
    }

    private void handleException(RuntimeException exception) {
        if (exception instanceof OptimisticLockingFailureException) {
            var notification = new Notification("Another user has edited the same product. "
            + "Please refresh and try again.");
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.WARNING);
            notification.setDuration(3000);
            notification.open();
        } else {
            throw exception;
        }
    }
}
