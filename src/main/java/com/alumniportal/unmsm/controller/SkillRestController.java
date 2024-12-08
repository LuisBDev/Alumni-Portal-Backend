package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.SkillRequestDTO;
import com.alumniportal.unmsm.dto.response.SkillResponseDTO;
import com.alumniportal.unmsm.service.interfaces.ISkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skill")
public class SkillRestController {

    private final ISkillService skillService;

    @GetMapping("/all")
    @Operation(summary = "Get all skills")
    @ApiResponse(responseCode = "200", description = "List of skills")
    public ResponseEntity<List<SkillResponseDTO>> findAll() {
        List<SkillResponseDTO> skillResponseDTOList = skillService.findAll();
        return ResponseEntity.ok(skillResponseDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get skill by id")
    @ApiResponse(responseCode = "200", description = "Return skill by id")
    public ResponseEntity<SkillResponseDTO> findById(@PathVariable Long id) {
        SkillResponseDTO skillResponseDTO = skillService.findById(id);
        return ResponseEntity.ok(skillResponseDTO);
    }

    @PostMapping("/save/{userId}")
    @Operation(summary = "Save skill by user id")
    @ApiResponse(responseCode = "201", description = "Return skill saved")
    public ResponseEntity<SkillResponseDTO> save(@RequestBody SkillRequestDTO skillRequestDTO, @PathVariable Long userId) {
        SkillResponseDTO skillResponseDTO = skillService.saveSkill(skillRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(skillResponseDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update skill by skill id")
    @ApiResponse(responseCode = "200", description = "Return skill updated")
    public ResponseEntity<SkillResponseDTO> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        SkillResponseDTO skillResponseDTO = skillService.updateSkill(id, updates);
        return ResponseEntity.ok(skillResponseDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete skill by skill id")
    @ApiResponse(responseCode = "204", description = "Skill deleted")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        skillService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get skills by user id")
    @ApiResponse(responseCode = "200", description = "List of skills")
    public ResponseEntity<List<SkillResponseDTO>> findSkillsByUserId(@PathVariable Long userId) {
        List<SkillResponseDTO> skillsByUserId = skillService.findSkillsByUserId(userId);
        return ResponseEntity.ok(skillsByUserId);
    }


}
