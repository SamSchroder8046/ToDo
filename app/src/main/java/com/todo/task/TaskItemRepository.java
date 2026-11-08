package com.todo.task;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

interface TaskItemRepository extends PagingAndSortingRepository<TaskItem, Long> {

//  The findByNameContainingIgnoreCase method uses Spring Data’s query derivation mechanism to generate a query that searches for products with names containing the specified string, ignoring case. See the Spring Data Relational documentation for more information on query derivation.
    Slice<TaskItem> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
}