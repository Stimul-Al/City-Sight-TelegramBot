package by.home.telegrambot.model;

import by.home.telegrambot.bot.State;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "chat_id")
    private Integer chatId;

    @Column(name = "name", unique = true, nullable = false)
    @NotBlank
    private String name;

    @NotBlank
    @Enumerated(value = EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    public User(int chatId, String name, State state) {
        this.chatId = chatId;
        this.name = name;
        this.state = state;
    }
}
