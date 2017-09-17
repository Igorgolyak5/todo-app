package com.todo.repository;

import com.todo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.stream.Stream;

/**
 * @author Igor Holiak
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Task task SET task.name = :name, task.date = :date WHERE task.id = :id")
    void update(@Param("id") final Long id, @Param("name") final String name, @Param("date") final Date date);

    @Query("SELECT task FROM Task task ORDER BY task.date DESC")
    Stream<Task> getAllSortDate();
}
