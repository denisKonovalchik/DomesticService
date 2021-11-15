package by.konovalchik.domesticservice.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "grades")
public class UserGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 1, nullable = false)
    private int grade;

    @ManyToOne(fetch = FetchType.EAGER)
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    UserRating userRating;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGrade userGrade = (UserGrade) o;
        return id == userGrade.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "UserGrade{" +
                "id=" + id +
                ", grade=" + grade +
                ", user=" + user +
                ", userRating=" + userRating +
                '}';
    }
}
