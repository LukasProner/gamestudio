package sk.tuke.gamestudio.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserServiceRest {

    @Autowired
    private UserService userService;

    @GetMapping("/{login}")
    public Player getPlayer(@PathVariable String login) {
        return userService.getPlayer(login);
    }

    @PostMapping
    public void addPlayer(@RequestBody Player player) {
        userService.addPlayer(player);
    }
}