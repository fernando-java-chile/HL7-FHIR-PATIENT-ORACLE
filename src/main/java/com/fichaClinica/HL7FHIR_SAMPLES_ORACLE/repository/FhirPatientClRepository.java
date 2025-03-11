package com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.repository;

import com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.entity.FhirPatientClEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface FhirPatientClRepository {
    // Método para buscar un paciente por tipo y valor de identificador
    Optional<FhirPatientClEntity> buscarPaciente(String identifierTypeCode, String identifierValue);

    // Método para buscar todos los pacientes paginados
    public Page<FhirPatientClEntity> buscarPacientesPaginados(Pageable pageable);

    public void insertarPaciente(FhirPatientClEntity patient);

    public void actualizarPaciente(FhirPatientClEntity patient);
}
