package cz.upce.nnpia.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @Column
    @CreationTimestamp
    protected LocalDateTime created;
    @Column
    @UpdateTimestamp
    protected LocalDateTime updated;

}
