package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.ApplicationRequestDTO;
import com.alumniportal.unmsm.dto.response.ApplicationResponseDTO;
import com.alumniportal.unmsm.service.interfaces.IApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
public class ApplicationController {


    private final IApplicationService applicationService;

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationResponseDTO>> findAll() {
        List<ApplicationResponseDTO> applicationResponseDTOList = applicationService.findAll();
        return ResponseEntity.ok(applicationResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> findById(@PathVariable Long id) {
        ApplicationResponseDTO applicationResponseDTO = applicationService.findById(id);
        return ResponseEntity.ok(applicationResponseDTO);
    }

    @PostMapping("/save")
//    @ApiResponse(responseCode = "201", description = "Application created")
    public ResponseEntity<Void> save(@RequestBody ApplicationRequestDTO applicationRequestDTO) {
        applicationService.saveApplication(applicationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        applicationService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ApplicationResponseDTO>> findApplicationsByUserId(@PathVariable Long userId) {
        List<ApplicationResponseDTO> applicationsByUserId = applicationService.findApplicationsByUserId(userId);
        return ResponseEntity.ok(applicationsByUserId);
    }

    @GetMapping("/job-offer/{jobOfferId}")
    public ResponseEntity<List<ApplicationResponseDTO>> findApplicationsByJobOfferId(@PathVariable Long jobOfferId) {
        List<ApplicationResponseDTO> applicationsByJobOfferId = applicationService.findApplicationsByJobOfferId(jobOfferId);
        return ResponseEntity.ok(applicationsByJobOfferId);
    }

    @GetMapping("/user/{userId}/job-offer/{jobOfferId}")
    public ResponseEntity<ApplicationResponseDTO> findApplicationByUserIdAndJobOfferId(@PathVariable Long userId, @PathVariable Long jobOfferId) {
        ApplicationResponseDTO applicationResponseDTO = applicationService.findApplicationByUserIdAndJobOfferId(userId, jobOfferId);
        return ResponseEntity.ok(applicationResponseDTO);
    }


}
