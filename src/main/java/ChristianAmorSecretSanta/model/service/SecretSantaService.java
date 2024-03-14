package ChristianAmorSecretSanta.model.service;

import ChristianAmorSecretSanta.model.domain.Player;
import ChristianAmorSecretSanta.model.dto.PlayerDTOName;
import ChristianAmorSecretSanta.model.dto.PlayerDTONotMatch;

import java.util.List;

public interface SecretSantaService {

    Player save (Player player);

    PlayerDTOName updateName (Long id, PlayerDTOName playerdto);
    PlayerDTONotMatch updateNotMatch (Long id, PlayerDTONotMatch playerdto);

    String deleteById (Long id);

    List<PlayerDTONotMatch> findAll();

    String play();


}
