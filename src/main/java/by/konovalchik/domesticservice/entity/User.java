package by.konovalchik.domesticservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20, nullable = false)
    private String firstName;

    @Column(length = 20, nullable = false)
    private String lastName;

    @Column(length = 20, nullable = false)
    private String email;

    @Column(length = 500)
    private String picture;

    @Column(length = 20, nullable = false)
    private String  username;

    @JsonIgnore
    @Column(length = 500, nullable = false)
    private String password;

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private UserRating rating;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Telephone telephone;

    @ManyToMany(mappedBy = "users",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER )
    private Set<Role> roles = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) &&
                password.equals(user.password);
    }


    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rating=" + rating +
                ", telephone=" + telephone +
                ", roles=" + roles +
                '}';
    }


}
