package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.model.User;
import app.service.UserService;

@Controller
@RequestMapping(path = "/users")
public class UserController {
    @Autowired private UserService userService;

    @GetMapping(path = "/{login}/foto")
    @ResponseBody
    public String getUserFotoByLogin(@PathVariable(name = "login", required = true) String login) {
        return this.userService.getFotoByLogin(login);
    }

    @GetMapping(path = "")
    @ResponseBody
    public List<User> getListUsers() {
        return this.userService.findAllUsers();
    }
}
