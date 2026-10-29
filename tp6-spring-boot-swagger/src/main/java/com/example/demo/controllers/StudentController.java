package com.example.demo.controllers;

import com.example.demo.entities.Student;
import com.example.demo.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("students")
@Tag(name = "Student Controller", description = "Endpoints de gestion des étudiants et statistiques")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "Enregistrer ou modifier un étudiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Étudiant créé avec succès")
    })
    @PostMapping("/save")
    public ResponseEntity<Student> save(@RequestBody Student student) {
        Student savedStudent = studentService.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @Operation(summary = "Supprimer un étudiant par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Étudiant supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        boolean deleted = studentService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Récupérer la liste complète des étudiants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAll() {
        List<Student> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Operation(summary = "Compter le nombre total d'étudiants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nombre d'étudiants retourné avec succès")
    })
    @GetMapping("/count")
    public ResponseEntity<Long> countStudent() {
        long count = studentService.countStudents();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @Operation(summary = "Récupérer la répartition des étudiants par année de naissance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistiques par année retournées avec succès")
    })
    @GetMapping("/byYear")
    public ResponseEntity<Collection<?>> findByYear() {
        Collection<?> studentsByYear = studentService.findNbrStudentByYear();
        return new ResponseEntity<>(studentsByYear, HttpStatus.OK);
    }
}
