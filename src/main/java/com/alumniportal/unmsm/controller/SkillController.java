package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.response.SkillResponseDTO;
import com.alumniportal.unmsm.model.Skill;
import com.alumniportal.unmsm.service.interfaces.ISkillService;
import lombok.RequiredArgsConstructor;
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
    public List<SkillResponseDTO> findAll() {
        return skillService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        SkillResponseDTO skillResponseDTO = skillService.findById(id);
        if (skillResponseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skillResponseDTO);
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<?> save(@RequestBody Skill skill, @PathVariable Long userId) {

        try {
            skillService.saveSkill(skill, userId);
            return ResponseEntity.ok("Skill saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            skillService.updateSkill(id, updates);
            return ResponseEntity.ok("Skill updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        skillService.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<SkillResponseDTO> findSkillsByUserId(@PathVariable Long userId) {
        return skillService.findSkillsByUserId(userId);
    }


}
