package com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.service;

import com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.entity.FhirPatientClEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FhirPatientClService {
    // Método para buscar un paciente por tipo y valor de identificador
    public Optional<FhirPatientClEntity> buscarPaciente(String identifierTypeCode, String identifierValue);

    // Método para buscar todos los pacientes paginados
    public Page<FhirPatientClEntity> buscarPacientesPaginados(Pageable pageable);

    public void insertarPaciente(FhirPatientClEntity patient);

    public void actualizarPaciente(FhirPatientClEntity patient);


}
