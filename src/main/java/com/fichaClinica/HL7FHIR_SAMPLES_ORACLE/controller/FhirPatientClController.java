package com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.controller;

import com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.entity.FhirPatientClEntity;
import com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.service.FhirPatientClService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/v1/patients")
public class FhirPatientClController {
    private static final Logger logger = LoggerFactory.getLogger(FhirPatientClController.class);

    @Autowired
    private FhirPatientClService fhirPatientClService;

    // Endpoint para insertar un paciente
    @PostMapping
    public ResponseEntity<?> insertPatient(@RequestBody FhirPatientClEntity patient) {
        try {
            fhirPatientClService.insertarPaciente(patient);
            return new ResponseEntity<>(patient, HttpStatus.CREATED);
        } catch (Exception e) {
            // Se registra el error en el log para su análisis
            logger.error("Error al insertar paciente: {}", e.getMessage());
            // Se retorna un mensaje de error junto con un código HTTP apropiado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al insertar paciente: " + e.getMessage());
        }
    }

    // Endpoint para buscar un paciente por tipo y valor de identificador
    // Endpoint para buscar un paciente por tipo y valor de identificador
    @GetMapping("/search")
    public ResponseEntity<FhirPatientClEntity> buscarPaciente(
            @RequestParam String identifierTypeCode,
            @RequestParam String identifierValue) {
        Optional<FhirPatientClEntity> paciente = fhirPatientClService.buscarPaciente(identifierTypeCode, identifierValue);
        return paciente.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/paginated")
    public Page<FhirPatientClEntity> buscarPacientesPaginados(Pageable pageable) {
        return fhirPatientClService.buscarPacientesPaginados(pageable);
    }

    @PutMapping
    public ResponseEntity<?> updatePatient(@RequestBody FhirPatientClEntity patient) {
        try {
            fhirPatientClService.actualizarPaciente(patient);
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } catch (Exception e) {
            // Se registra el error en el log para su análisis
            logger.error("Error al actualizar paciente: {}", e.getMessage());
            // Se retorna un mensaje de error junto con un código HTTP apropiado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar paciente: " + e.getMessage());
        }
    }

}
