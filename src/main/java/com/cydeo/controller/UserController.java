package com.cydeo.controller;



import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/users")
public class UserController {

    private final RoleService roleService;
    private final UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }




    @GetMapping("/list")
    public String listUser(Model model){


        model.addAttribute("users", userService.listAllUsers());

        return "/user/user-list";
    }




    @GetMapping("/create")
    public String createUser(Model model){

        model.addAttribute("newUser",new UserDTO());
        model.addAttribute("userRoles",roleService.listAllRoles());


        return "/user/user-create";

    }

    @PostMapping("/create")
    public String insertUser(@ModelAttribute("newUser") UserDTO user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            return "/user/user-create";

        }

        userService.save(user);

        return "redirect:/users/create";

    }



    @GetMapping("/update/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {

        model.addAttribute("user", userService.findById(id));
        model.addAttribute("userRoles", roleService.listAllRoles());

        return "/user/user-update";

    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") UserDTO user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("userRoles", roleService.listAllRoles());
            model.addAttribute("users", userService.listAllUsers());

            return "/user/user-update";

        }

        userService.update(user);

        return "redirect:/users/create";

    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {


            userService.delete(id);
        return "redirect:/users/list";
    }





}
