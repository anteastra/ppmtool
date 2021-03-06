package com.anteastra.ppmtool.services;

import com.anteastra.ppmtool.domain.Project;
import com.anteastra.ppmtool.exceptions.ProjectIdException;
import com.anteastra.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    public Project findProjectByProjectIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectIdentifier.toUpperCase() + "' does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByProjectIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Cannot Delete Project ID '" + projectIdentifier.toUpperCase() + "'. This project does not exists");
        }
        projectRepository.delete(project);
    }
}
