package com.todo.task;

import com.vaadin.flow.component.template.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.Date;

@Table("TASKS")
record TaskItem(
        @Id Long taskId,
        String description,
        OffsetDateTime timestampCreated,
        OffsetDateTime timestampDue,
        boolean complete
) {
    static final String SORT_PROPERTY_DESCRIPTION = "description";
    static final String SORT_PROPERTY_TIMESTAMP_CREATED = "timestamp_created";
    static final String SORT_PROPERTY_TIMESTAMP_DUE = "timestamp_due";
    static final String SORT_PROPERTY_COMPLETE = "complete";
}
