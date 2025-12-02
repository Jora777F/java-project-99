package hexlet.code.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "labels")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 3, max = 1000)
    @Column(name = "name", unique = true, length = 1000)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
}
