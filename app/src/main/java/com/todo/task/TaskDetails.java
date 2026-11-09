package com.todo.task;

import com.vaadin.flow.component.template.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("TASKS")
public class TaskDetails {

    @Id
    private Long taskId;
    @Version
    private Long version;
    private String description;
    private OffsetDateTime timestampCreated;
    private OffsetDateTime timestampDue;
    private boolean complete;

    public Long getTaskId() {
        return taskId;
    }

    public Long getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getTimestampCreated() {
        return timestampCreated;
    }

    public OffsetDateTime getTimestampDue() {
        return timestampDue;
    }

    public void setTimestampDue(OffsetDateTime timestampDue) {
        this.timestampDue = timestampDue;
    }

    public boolean getComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
