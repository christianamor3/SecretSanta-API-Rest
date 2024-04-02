package ChristianAmorSecretSanta.model.service;

import ChristianAmorSecretSanta.exceptions.MinimumPlayersException;
import ChristianAmorSecretSanta.exceptions.PlayerShouldHaveNameException;
import ChristianAmorSecretSanta.model.domain.Player;
import ChristianAmorSecretSanta.model.dto.PlayerDTOName;
import ChristianAmorSecretSanta.model.dto.PlayerDTONotMatch;
import ChristianAmorSecretSanta.model.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SecretSantaServiceImpl implements SecretSantaService {

    @Autowired
    PlayerRepository repo;

    @Override
    public Player save(Player player) {

        if (player.getPlayer_name().isEmpty()){
            throw new PlayerShouldHaveNameException("El jugador debe tener un nombre");
        } else {
            return repo.save(player);
        }
    }

    @Override
    public PlayerDTOName updateName(Long id, PlayerDTOName playerdto) {
        Optional<Player> playerFound = repo.findById(id);

        if (playerFound.isEmpty()){
            throw new EntityNotFoundException("No se ha encontrado el jugador");
        } else if (playerdto.getPlayer_name().isEmpty()){
            throw new PlayerShouldHaveNameException("El jugador debe tener un nombre");
        } else {
            playerFound.get().setPlayer_name(playerdto.getPlayer_name());
            return toDTOName(repo.save(playerFound.get()));
        }
    }

    @Override
    public PlayerDTONotMatch updateNotMatch(Long id, PlayerDTONotMatch playerdto) {
        Optional<Player> playerFound = repo.findById(id);

        if (playerFound.isEmpty()){
            throw new EntityNotFoundException("No se ha encontrado el jugador");
        } else if (playerdto.getNotMatch().isEmpty()){
            throw new PlayerShouldHaveNameException("Este campo debe tener un nombre");
        } else {
            playerFound.get().setNotMatch(playerdto.getNotMatch());
            return toDTONotMatch(repo.save(playerFound.get()));
        }
    }

    @Override
    public String deleteById(Long id) {

        Optional<Player> playerFound = repo.findById(id);

        if (playerFound.isEmpty()){
            throw new EntityNotFoundException("No se ha encontrado el jugador");
        } else {
            repo.deleteById(id);
            return "Se ha eliminado el jugador correctamente";
        }
    }

    @Override
    public List<PlayerDTONotMatch> findAll() {
        List<Player> playerList = repo.findAll();

        if (playerList.isEmpty()){
            throw new EntityNotFoundException("No se han encontrado jugadores");
        } else {
            return playerList.stream().map(SecretSantaServiceImpl::toDTONotMatch).collect(Collectors.toList());
        }
    }

    public List<Player> findAllPlayers() {
        List<Player> playerList = repo.findAll();

        if (playerList.isEmpty()){
            throw new EntityNotFoundException("No se han encontrado jugadores");
        } else {
            return playerList;
        }
    }

    public Player findById(Long id){
        Optional<Player> playerOptional = repo.findById(id);
        return playerOptional.get();
    }

    @Override
    public String play() {
        List<Player> playerList = repo.findAll();

        if (playerList.isEmpty()){
            throw new EntityNotFoundException("No se han encontrado jugadores");
        } else if (playerList.size()<=3){
            throw new MinimumPlayersException("No hay suficientes jugadores");
        } else {
            secretSanta(playerList);
            return result(playerList);
        }
    }


    public void secretSanta(List<Player> playerList){

        List<Integer> numsUsed = new ArrayList<>();

        Collections.shuffle(playerList);

        int i = 0;

        while (i<playerList.size()){

            Random random = new Random();
            int numRandom = random.nextInt(playerList.size());
            Player playerTurn = playerList.get(i);
            String playerSantaName = playerList.get(numRandom).getPlayer_name();

            if (playerTurn.getNotMatch()!=null){
                if (!numsUsed.contains(numRandom)
                        && numRandom!=i
                            && !playerTurn.getNotMatch().equalsIgnoreCase(playerSantaName)) {

                    playerTurn
                            .setSecretSanta(
                                    playerSantaName);

                    numsUsed.add(numRandom);
                    repo.save(playerList.get(i));
                    i++;

                }
            } else {
                if (!numsUsed.contains(numRandom)
                        && numRandom!=i){
                    playerTurn
                            .setSecretSanta(
                                    playerSantaName);

                    numsUsed.add(numRandom);
                    repo.save(playerList.get(i));
                    i++;
                }
            }
        }
    }

    public String result(List<Player> playerList){
        StringBuilder result = new StringBuilder();

        for (Player player : playerList) {
            result.append(player.getPlayer_name()).append(" - ").append(player.getSecretSanta()).append(" ");
        }

        return result.toString();
    }

    public static PlayerDTOName toDTOName(Player player){
        PlayerDTOName playerdto = new PlayerDTOName();

        playerdto.setPlayer_id(player.getPlayer_id());
        playerdto.setPlayer_name(player.getPlayer_name());

        return playerdto;
    }

    public static PlayerDTONotMatch toDTONotMatch(Player player){
        PlayerDTONotMatch playerdto = new PlayerDTONotMatch();

        playerdto.setPlayer_id(player.getPlayer_id());
        playerdto.setPlayer_name(player.getPlayer_name());
        playerdto.setNotMatch(player.getNotMatch());

        return playerdto;
    }

}
