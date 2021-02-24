package by.home.telegrambot.model;

import by.home.telegrambot.bot.State;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(
        columnNames = "chat_user_id", name = "users_unique_chatid_idx"))
public class User extends AbstractBaseEntity{

    @NotNull
    @Column(name = "chat_user_id", unique = true, nullable = false)
    private Integer chatUserId;

    @Column(name = "name", unique = true, nullable = false)
    @NotBlank
    private String name;

    @NotBlank
    @Enumerated(value = EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    public User(String name, State state) {
        this.name = name;
        this.state = state;
    }
}
