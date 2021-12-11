package com.itsol.recruit_managerment.controller;


import com.itsol.recruit_managerment.model.Academic_Level;
import com.itsol.recruit_managerment.service.Academiclevelimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user/academic")
public class AcademicLevelController {
    @Autowired
    Academiclevelimpl academiclevelimpl;

    @GetMapping()
    public List<Academic_Level> getAcademic() {
        return academiclevelimpl.getAll();
    }

    @PostMapping()
    public ResponseEntity<Object> add(@Valid @RequestBody Academic_Level academic_level) {
        try {
            academiclevelimpl.save(academic_level);
            return ResponseEntity.ok().body(academic_level);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("failed to create academic_level");
        }
    }

    @GetMapping("/{id}")
    public Academic_Level getAcademicById(@PathVariable Long id) {
        return academiclevelimpl.getById(id);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Object> deleteAcademic(@PathVariable Long id) {
        academiclevelimpl.deleteById(id);
        return ResponseEntity.badRequest().body("abc");
    }
}
