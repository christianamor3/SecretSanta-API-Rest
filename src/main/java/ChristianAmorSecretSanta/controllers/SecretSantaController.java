package ChristianAmorSecretSanta.controllers;

import ChristianAmorSecretSanta.exceptions.MinimumPlayersException;
import ChristianAmorSecretSanta.exceptions.PlayerShouldHaveNameException;
import ChristianAmorSecretSanta.model.domain.Player;
import ChristianAmorSecretSanta.model.dto.PlayerDTOName;
import ChristianAmorSecretSanta.model.dto.PlayerDTONotMatch;
import ChristianAmorSecretSanta.model.service.SecretSantaServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secretSanta")
public class SecretSantaController {

    @Autowired
    SecretSantaServiceImpl service;


    @PostMapping("/addPlayer")
    public ResponseEntity<Player> addPlayer (@RequestBody Player player){

        try {
            return ResponseEntity.ok(service.save(player));
        } catch (PlayerShouldHaveNameException pshne){
            System.out.println(pshne.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateName/{id}")
    public ResponseEntity<PlayerDTOName> updateName (@PathVariable Long id, @RequestBody PlayerDTOName playerdto){

        try {
            return ResponseEntity.ok(service.updateName(id, playerdto));
        } catch (EntityNotFoundException enfe){
            System.out.println(enfe.getMessage());
            return ResponseEntity.notFound().build();
        } catch (PlayerShouldHaveNameException pshne){
            System.out.println(pshne.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateNotMatch/{id}")
    public ResponseEntity<PlayerDTONotMatch> updateNotMatch (@PathVariable Long id, @RequestBody PlayerDTONotMatch playerdto){

        try {
            return ResponseEntity.ok(service.updateNotMatch(id, playerdto));
        } catch (EntityNotFoundException enfe){
            System.out.println(enfe.getMessage());
            return ResponseEntity.notFound().build();
        } catch (PlayerShouldHaveNameException pshne){
            System.out.println(pshne.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlayer (@PathVariable Long id){

        try {
            return ResponseEntity.ok(service.deleteById(id));
        } catch (EntityNotFoundException enfe){
            System.out.println(enfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<PlayerDTONotMatch>> findAll(){

        try {
            return ResponseEntity.ok(service.findAll());
        } catch (EntityNotFoundException enfe){
            System.out.println(enfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/play")
    public ResponseEntity<String> play (){

        try {
            return ResponseEntity.ok(service.play());
        } catch (EntityNotFoundException enfe) {
            System.out.println(enfe.getMessage());
            return ResponseEntity.notFound().build();
        } catch (MinimumPlayersException mpe){
            System.out.println(mpe.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}

