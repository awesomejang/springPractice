package com.springpractice.dao.entities.main;

import com.springpractice.dao.entities.BaseEntity;
import com.springpractice.dao.entities.BaseTimeEntity;
import com.springpractice.dao.enums.TaskStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "async_tasks")
public class AsyncTaskEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_type", nullable = false)
    private String taskType;

    @Lob
    @Column(name = "task_payload", nullable = false)
    private String taskPayload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus taskStatus;
}
