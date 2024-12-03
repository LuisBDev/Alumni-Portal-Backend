package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.RequestDTO.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.UserCVResponseDTO;
import com.alumniportal.unmsm.dto.ResponseDTO.UserResponseDTO;
import com.alumniportal.unmsm.service.interfaces.IUserService;
import com.alumniportal.unmsm.util.CVGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    //    Inyeccion por constructor mediante la anotacion @RequiredArgsConstructor
    private final IUserService userService;

//    @PostMapping("/loginAcademic")
//    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
//        UserResponseDTO userDTO = userService.validateLogin(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
//        if (userDTO == null) {
//            return ResponseEntity.status(401).body("Invalid email or password");
//        }
//        return ResponseEntity.ok(userDTO);
//
//    }
//
//    @PostMapping("/registerAcademic")
//    public ResponseEntity<?> save(@RequestBody User user) {
//        try {
//            userService.saveUser(user);
//            return ResponseEntity.ok("User registered successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }


    @GetMapping("/all")
    public List<UserResponseDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.findById(id);
        if (userResponseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userResponseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.findById(id);
        if (userResponseDTO == null) {
            return ResponseEntity.status(404).body("Error: User not found!");
        }
        userService.deleteById(id);
        return ResponseEntity.ok("User deleted successfully!");
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            userService.updateUser(id, updates);
            return ResponseEntity.ok("User updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/cv/download/{userId}")
    public ResponseEntity<?> downloadUserCV(@PathVariable Long userId) throws IOException {

        UserCVResponseDTO cv = userService.getUserCV(userId);

        byte[] pdfContent = CVGenerator.generateCV(cv);

        // Encabezados para la respuesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "UserCV_" + userId + "_" + cv.getName() + ".pdf");
        headers.setContentLength(pdfContent.length);

        // Se retorna el PDF en la respuesta como array de bytes
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @PostMapping("/updatePassword/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) {
        try {
            userService.updatePassword(id, passwordChangeRequestDTO);
            return ResponseEntity.ok("Password updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
