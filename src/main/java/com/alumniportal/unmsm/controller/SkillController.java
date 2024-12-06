package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.request.SkillRequestDTO;
import com.alumniportal.unmsm.dto.response.SkillResponseDTO;
import com.alumniportal.unmsm.model.Skill;
import com.alumniportal.unmsm.service.interfaces.ISkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skill")
public class SkillController {

    private final ISkillService skillService;

    @GetMapping("/all")
    public ResponseEntity<List<SkillResponseDTO>> findAll() {
        List<SkillResponseDTO> skillResponseDTOList = skillService.findAll();
        return ResponseEntity.ok(skillResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> findById(@PathVariable Long id) {
        SkillResponseDTO skillResponseDTO = skillService.findById(id);
        return ResponseEntity.ok(skillResponseDTO);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<Void> save(@RequestBody SkillRequestDTO skillRequestDTO, @PathVariable Long userId) {
        skillService.saveSkill(skillRequestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        skillService.updateSkill(id, updates);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        skillService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SkillResponseDTO>> findSkillsByUserId(@PathVariable Long userId) {
        List<SkillResponseDTO> skillsByUserId = skillService.findSkillsByUserId(userId);
        return ResponseEntity.ok(skillsByUserId);
    }


}
