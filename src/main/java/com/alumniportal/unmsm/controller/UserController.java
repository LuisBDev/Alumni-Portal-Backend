package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.model.User;
import com.alumniportal.unmsm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PostMapping("/loginAcademic")
    public ResponseEntity<?> login(@RequestBody User user) {
        User userFound = userService.findByEmail(user.getEmail());
        if (userFound == null) {
            return ResponseEntity.badRequest().body("Error: Email no encontrado!");
        } else {
            if (userFound.getPassword().equals(user.getPassword())) {
                return ResponseEntity.ok("Login successful!");
            } else {
                return ResponseEntity.badRequest().body("Error: Password incorrecto!");
            }
        }
    }

    @PostMapping("/registerAcademic")
    public ResponseEntity<?> save(@RequestBody User user) {
        boolean emailExists = userService.existsByEmail(user.getEmail());
        if (emailExists) {
            return ResponseEntity.badRequest().body("Error: Email ya existe en la bd!");
        } else {
            user.setCreatedAt(new Date());
            userService.save(user);
            return ResponseEntity.ok("User saved successfully!");
        }
    }


}
