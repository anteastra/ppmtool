package com.anteastra.ppmtool.web;

import com.anteastra.ppmtool.domain.Project;
import com.anteastra.ppmtool.services.ProjectService;
import com.anteastra.ppmtool.services.ValidationMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ValidationMapService validationMapService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationMapService.mapValidation(bindingResult);
        if (errorMap != null) return errorMap;

        Project projectResponse = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(projectResponse, HttpStatus.CREATED);
    }
}
