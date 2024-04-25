package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.service.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    private Player loggedUser;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(String login, String password){
        if(userService.authenticate(login, password)) {
            loggedUser = userService.getPlayer(login);
            return "redirect:/numberlink";
        }
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(){
        loggedUser = null;
        return "redirect:/";
    }

    public Player getLoggedUser(){
        return loggedUser;
    }

    public boolean isLogged(){
        return loggedUser != null;
    }
}
