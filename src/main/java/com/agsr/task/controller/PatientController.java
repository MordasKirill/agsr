package com.agsr.task.controller;

import com.agsr.task.entity.Patient;
import com.agsr.task.repository.PatientRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Rest")
@RestController
@RequestMapping("/api/patients")
@SecurityRequirement(name = "Keycloak")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Operation(description = "Get all patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient list retrieved successfully")
    })
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Operation(description = "Get patient by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient retrieved successfully")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        return patientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(description = "Create patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient created successfully")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public Patient createPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientRepository.save(patient);
        log.info("Patient saved successfully, patient id:{}", patient.getId());
        return savedPatient;
    }

    @Operation(description = "Update patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String id, @RequestBody Patient updatedPatient) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setName(updatedPatient.getName());
                    patient.setGender(updatedPatient.getGender());
                    patient.setBirthDate(updatedPatient.getBirthDate());
                    return ResponseEntity.ok(patientRepository.save(patient));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(description = "Delete patient by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patientRepository.delete(patient);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

