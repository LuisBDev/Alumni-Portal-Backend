package com.alumniportal.unmsm.controller;

import com.alumniportal.unmsm.dto.SkillDTO;
import com.alumniportal.unmsm.model.Skill;
import com.alumniportal.unmsm.service.ISkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skill")
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class SkillController {

    @Autowired
    private ISkillService skillService;

    @GetMapping("/all")
    public List<SkillDTO> findAll() {
        return skillService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        SkillDTO skillDTO = skillService.findById(id);
        if (skillDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skillDTO);
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> updates)
    {
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
    public List<SkillDTO> findSkillsByUser_Id(@PathVariable Long userId) {
        return skillService.findSkillsByUser_Id(userId);
    }


}
