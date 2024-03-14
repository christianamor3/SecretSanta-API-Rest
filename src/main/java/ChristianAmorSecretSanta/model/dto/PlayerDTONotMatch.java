package ChristianAmorSecretSanta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerDTONotMatch {

    private long player_id;

    private String player_name;

    private String notMatch;

}
