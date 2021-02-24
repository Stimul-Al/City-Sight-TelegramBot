package by.home.telegrambot.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sights")
public class Sight extends AbstractBaseEntity {

    @Column(name = "sight")
    private String sight;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}

