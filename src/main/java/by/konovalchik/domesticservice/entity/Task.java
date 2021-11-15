package by.konovalchik.domesticservice.entity;


import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryOfTask category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(length = 10, nullable = false)
    private double price;

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    TelephoneTask telephoneTask;

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    AddressTask addressTask;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<User> users;

    private boolean express;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", localDateTime=" + localDateTime +
                ", category=" + category +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", telephoneTask=" + telephoneTask +
                ", addressTask=" + addressTask +
                ", users=" + users +
                ", express=" + express +
                '}';
    }
}
