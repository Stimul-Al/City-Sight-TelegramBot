package by.home.telegrambot.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cities")
public class City extends AbstractBaseEntity {

    @Column(name = "city")
    private String city;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "city")
    private List<Sight> sights = new ArrayList<>();
}
