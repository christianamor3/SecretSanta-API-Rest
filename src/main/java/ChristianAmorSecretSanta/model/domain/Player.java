package ChristianAmorSecretSanta.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long player_id;

    @Column(name = "player_name")
    private String player_name;

    @Column(name = "secretSanta")
    private String secretSanta;

    @Column(name = "notMatch")
    private String notMatch;

}
