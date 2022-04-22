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
@Table(name = "task_addresses")
public class AddressTask {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;

     @Column(length = 20, nullable = false)
     private String city;

     @Column(length = 20, nullable = false)
     private String street;

     @Column(length = 6, nullable = false)
     private String house;

     @Column(length = 6)
     private String apartment;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressTask that = (AddressTask) o;
        return city.equals(that.city) &&
                street.equals(that.street) &&
                house.equals(that.house) &&
                Objects.equals(apartment, that.apartment);
    }


    @Override
    public int hashCode() {
        return Objects.hash(city, street, house);
    }


    @Override
    public String toString() {
        return "AddressTask{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house=" + house +
                ", apartment=" + apartment +
                '}';
    }


}
