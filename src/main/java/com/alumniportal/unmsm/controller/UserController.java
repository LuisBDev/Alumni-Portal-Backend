package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.PasswordChangeRequestDTO;
import com.alumniportal.unmsm.dto.response.UserCVResponseDTO;
import com.alumniportal.unmsm.dto.response.UserResponseDTO;
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


    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> userResponseDTOList = userService.findAll();
        return ResponseEntity.ok(userResponseDTOList);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.findById(id);
        return ResponseEntity.ok(userResponseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        userService.updateUser(id, updates);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) {
        userService.updatePassword(id, passwordChangeRequestDTO);
        return ResponseEntity.noContent().build();
    }


}
