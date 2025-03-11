package com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.service;

import com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.entity.FhirPatientClEntity;
import com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.repository.FhirPatientClRepository;
import com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.validation.RutValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class FhirPatientClServiceImpl implements FhirPatientClService {

    private static final Set<String> VALID_GENDERS = Set.of("male", "female", "other", "unknown");
    @Autowired
    private FhirPatientClRepository fhirPatientClRepository;

    @Override
    public Optional<FhirPatientClEntity> buscarPaciente(String identifierTypeCode, String identifierValue) {
        return fhirPatientClRepository.buscarPaciente(identifierTypeCode, identifierValue);
    }

    @Override
    public void insertarPaciente(FhirPatientClEntity paciente) {
        // Validar el valor de GENDER
        if (!VALID_GENDERS.contains(paciente.getGender())) {
            throw new IllegalArgumentException("El valor de GENDER no es válido. Debe ser 'male', 'female', " +
                    "'other' o 'unknown'.");
        }

        // Validar formato del RUT si el tipo de identificador es CI
        if ("CI".equals(paciente.getIdentifierTypeCode())) {
            if(!RutValidator.validarRut(paciente.getIdentifierValue())) {
                throw new IllegalArgumentException("El RUT ingresado no es válido.");
            }
        }
        // Asignar valores predeterminados
        if (paciente.getActive() == null) {
            paciente.setActive("Y");
        }
        fhirPatientClRepository.insertarPaciente(paciente);
    }

    @Override
    public Page<FhirPatientClEntity> buscarPacientesPaginados(Pageable pageable) {
        return fhirPatientClRepository.buscarPacientesPaginados(pageable);
    }

    @Override
    public void actualizarPaciente(FhirPatientClEntity paciente) {
        fhirPatientClRepository.actualizarPaciente(paciente);
    }
}
