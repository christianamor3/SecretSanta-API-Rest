package ChristianAmorSecretSanta.controllers;

import ChristianAmorSecretSanta.model.domain.Player;
import ChristianAmorSecretSanta.model.service.SecretSantaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/secretSanta")
public class WebSecretSantaController {
    
    @Autowired
    private SecretSantaServiceImpl service;

    @GetMapping({"", "/"})
    public String listAllPlayers(Model modelo){
        modelo.addAttribute("players", service.findAll());
        return "secretSanta";
    }

    @GetMapping("/add")
    public String getSecretSantaForm(Model modelo){
        modelo.addAttribute("player", new Player());
        return "create_player";
    }

    @PostMapping("/add")
    public String addPlayer(@ModelAttribute Player player){
        try {
            service.save(player);
            return "redirect:/secretSanta";
        } catch (RuntimeException e){
            return "create_player";
        }
    }

    @GetMapping("/updateName/{id}")
    public String getUpdateNameForm(@PathVariable Long id, Model modelo){
        Player playerFound = service.findById(id);

        if (playerFound != null){
            modelo.addAttribute("playerFound", playerFound);
            return "update_name";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/updateName/{id}")
    public String updateName(@PathVariable Long id, @ModelAttribute("playerFound") Player player){
        try {
            service.updateName(id, SecretSantaServiceImpl.toDTOName(player));
            return "redirect:/secretSanta";
        } catch (RuntimeException e){
            return "redirect:/error";
        }
    }

    @GetMapping("/updateNotMatch/{id}")
    public String getUpdateNotMatchForm(@PathVariable Long id, Model modelo){
        Player playerFound = service.findById(id);

        if (playerFound != null){
            modelo.addAttribute("playerFound", playerFound);
            return "update_notMatch";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/updateNotMatch/{id}")
    public String updateNotMatch(@PathVariable Long id, @ModelAttribute("playerFound") Player player){
        try {
            service.updateNotMatch(id, SecretSantaServiceImpl.toDTONotMatch(player));
            return "redirect:/secretSanta";
        } catch (RuntimeException e){
            return "redirect:/error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePlayer(@PathVariable Long id){
        service.deleteById(id);
        return "redirect:/secretSanta";
    }

    @GetMapping("/play")
    public String playGame(Model modelo){
        service.play();
        List<Player> players = service.findAllPlayers();
        modelo.addAttribute("players", players);
        return "result";
    }





}
