package com.example.companyemployeespring.controllor;

import com.example.companyemployeespring.entity.User;
import com.example.companyemployeespring.entity.UserRole;
import com.example.companyemployeespring.repository.UserRepository;
import com.example.companyemployeespring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public String users(ModelMap modelMap){
        List<User> usersList = userService.findAllUser();
        modelMap.addAttribute("users", usersList);
        return "users";
    }

    @GetMapping("/user/add")
    public String addUserPage(ModelMap modelMap){
        UserRole[] values = UserRole.values();
        modelMap.addAttribute("role", values);
        return "addUser";
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute User user, ModelMap modelMap){
        Optional<User> byEmail = userService.findByEmail(user.getEmail());
        if(byEmail.isPresent()){
            modelMap.addAttribute("errorMessage", "email already in use");
            return "addUser";
        }
        userService.saveUserWithPass(user);
        return "redirect:/users";
    }

}
