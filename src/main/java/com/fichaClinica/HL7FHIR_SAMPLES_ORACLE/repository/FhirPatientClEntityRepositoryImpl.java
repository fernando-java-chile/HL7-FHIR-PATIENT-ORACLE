package com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.repository;

import com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.controller.FhirPatientClController;
import com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.entity.FhirPatientClEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class FhirPatientClEntityRepositoryImpl implements FhirPatientClRepository {

    private static final Logger logger = LoggerFactory.getLogger(FhirPatientClEntityRepositoryImpl.class);
    private final String SCHEMA_NAME = "FVALDS";
    private final String CATALOG_NAME = "FHIR_PATIENT_PKG_v_1_8_5";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Page<FhirPatientClEntity> buscarPacientesPaginados(Pageable pageable) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(SCHEMA_NAME)               // Esquema (owner)
                .withCatalogName(CATALOG_NAME)    // Package
                .withProcedureName("BUSCAR_PACIENTES_PAGINADOS") // Procedimiento
                .declareParameters(
                        new SqlParameter("p_page_number", Types.NUMERIC),
                        new SqlParameter("p_page_size", Types.NUMERIC),
                        new SqlOutParameter("p_total_patients", Types.NUMERIC),
                        new SqlOutParameter("p_patients", Types.REF_CURSOR)
                )
                .returningResultSet("p_patients", this::mapRowToFhirPatientClEntity);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("p_page_number", pageable.getPageNumber() + 1) // Oracle cuenta desde 1
                .addValue("p_page_size", pageable.getPageSize());
        try{
            Map<String, Object> result = jdbcCall.execute(params);

            // Obtener los resultados
            //int totalPatients = (int) result.get("p_total_patients");
            int totalPatients = ((BigDecimal) result.get("p_total_patients")).intValue();
            List<FhirPatientClEntity> pacientes = (List<FhirPatientClEntity>) result.get("p_patients");

            // Crear un objeto Page
            return new PageImpl<>(pacientes, pageable, totalPatients);
        } catch (Exception e) {
            // Manejo de excepciones
            throw new RuntimeException("Error al ejecutar el procedimiento BUSCAR_PACIENTE", e);
        }
    };

    @SuppressWarnings("unchecked") // Suprime la advertencia de "unchecked cast"
    @Override
    public Optional<FhirPatientClEntity> buscarPaciente(String identifierTypeCode, String identifierValue) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(SCHEMA_NAME)               // Esquema (owner)
                .withCatalogName(CATALOG_NAME)      // Package
                .withProcedureName("BUSCAR_PACIENTE")      // Procedimiento
                .declareParameters(
                        new SqlParameter("p_identifier_type_code", Types.VARCHAR),
                        new SqlParameter("p_identifier_value", Types.VARCHAR),
                        new SqlOutParameter("p_patient", Types.REF_CURSOR)
                )
                .returningResultSet("p_patient", this::mapRowToFhirPatientClEntity);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("p_identifier_type_code", identifierTypeCode)
                .addValue("p_identifier_value", identifierValue);

        try {
            Map<String, Object> result = jdbcCall.execute(params);
            // Suprime la advertencia en esta línea
            @SuppressWarnings("unchecked")
            List<FhirPatientClEntity> pacientes = (List<FhirPatientClEntity>) result.get("p_patient");
            return pacientes.isEmpty() ? Optional.empty() : Optional.of(pacientes.get(0));
        } catch (Exception e) {
            // Manejo de excepciones
            throw new RuntimeException("Error al ejecutar el procedimiento BUSCAR_PACIENTE", e);
        }
    }

    private FhirPatientClEntity mapRowToFhirPatientClEntity(ResultSet rs, int rowNum) throws SQLException {
        return FhirPatientClEntity.builder()
                .patientId(rs.getLong("PATIENT_ID"))
                .active(rs.getString("ACTIVE"))
                .gender(rs.getString("GENDER"))
                .birthDate(rs.getDate("BIRTH_DATE"))
                .deceasedBoolean(rs.getString("DECEASED_BOOLEAN"))
                .deceasedDateTime(rs.getTimestamp("DECEASED_DATETIME"))
                .nameFamily(rs.getString("NAME_FAMILY"))
                .nameGiven(rs.getString("NAME_GIVEN"))
                .namePrefix(rs.getString("NAME_PREFIX"))
                .nameSuffix(rs.getString("NAME_SUFFIX"))
                .telecomSystem(rs.getString("TELECOM_SYSTEM"))
                .telecomValue(rs.getString("TELECOM_VALUE"))
                .addressLine(rs.getString("ADDRESS_LINE"))
                .addressCity(rs.getString("ADDRESS_CITY"))
                .addressState(rs.getString("ADDRESS_STATE"))
                .addressPostalCode(rs.getString("ADDRESS_POSTAL_CODE"))
                .addressCountry(rs.getString("ADDRESS_COUNTRY"))
                .maritalStatus(rs.getString("MARITAL_STATUS"))
                .multipleBirthBoolean(rs.getString("MULTIPLE_BIRTH_BOOLEAN"))
                .multipleBirthInteger(rs.getInt("MULTIPLE_BIRTH_INTEGER"))
                .photoUrl(rs.getString("PHOTO_URL"))
                .identidadGeneroCode(rs.getString("IDENTIDAD_GENERO_CODE"))
                .identidadGeneroDisplay(rs.getString("IDENTIDAD_GENERO_DISPLAY"))
                .sexoBiologicoCode(rs.getString("SEXO_BIOLOGICO_CODE"))
                .sexoBiologicoDisplay(rs.getString("SEXO_BIOLOGICO_DISPLAY"))
                .nacionalidadCode(rs.getString("NACIONALIDAD_CODE"))
                .nacionalidadDisplay(rs.getString("NACIONALIDAD_DISPLAY"))
                .identifierUse(rs.getString("IDENTIFIER_USE"))
                .identifierTypeCode(rs.getString("IDENTIFIER_TYPE_CODE"))
                .identifierTypeDisplay(rs.getString("IDENTIFIER_TYPE_DISPLAY"))
                .identifierValue(rs.getString("IDENTIFIER_VALUE"))
                .nombreOficialFamily(rs.getString("NOMBRE_OFICIAL_FAMILY"))
                .nombreOficialGiven(rs.getString("NOMBRE_OFICIAL_GIVEN"))
                .segundoApellido(rs.getString("SEGUNDO_APELLIDO"))
                .nombreSocialGiven(rs.getString("NOMBRE_SOCIAL_GIVEN"))
                .contactRelationshipCode(rs.getString("CONTACT_RELATIONSHIP_CODE"))
                .contactRelationshipDisplay(rs.getString("CONTACT_RELATIONSHIP_DISPLAY"))
                .contactNameFamily(rs.getString("CONTACT_NAME_FAMILY"))
                .contactNameGiven(rs.getString("CONTACT_NAME_GIVEN"))
                .communicationLanguageCode(rs.getString("COMMUNICATION_LANGUAGE_CODE"))
                .communicationLanguageDisplay(rs.getString("COMMUNICATION_LANGUAGE_DISPLAY"))
                .generalPractitionerReference(rs.getString("GENERAL_PRACTITIONER_REFERENCE"))
                .generalPractitionerDisplay(rs.getString("GENERAL_PRACTITIONER_DISPLAY"))
                .build();
    }

    @Override
    public void insertarPaciente(FhirPatientClEntity patient) {
        SimpleJdbcCall jdbcCall = configureJdbcCall("INSERT_PATIENT");
        MapSqlParameterSource params = buildCommonParameters(patient);

        try {
            jdbcCall.execute(params);
        } catch (Exception e) {
            handleException(e, "INSERT_PATIENT");
        }
    }

    @Override
    public void actualizarPaciente(FhirPatientClEntity patient) {
        SimpleJdbcCall jdbcCall = configureJdbcCall("UPDATE_PATIENT")
                .declareParameters(new SqlParameter("p_patient_id", Types.NUMERIC)); // Parámetro adicional para UPDATE

        MapSqlParameterSource params = buildCommonParameters(patient)
                .addValue("p_patient_id", patient.getPatientId()); // Solo para UPDATE

        try {
            jdbcCall.execute(params);
        } catch (Exception e) {
            handleException(e, "UPDATE_PATIENT");
        }
    }

    private void handleException(Exception e, String procedureName) {
        Throwable cause = e.getCause();
        if (cause instanceof SQLException) {
            SQLException sqlEx = (SQLException) cause;
            int errorCode = sqlEx.getErrorCode();
            String errorMessage = sqlEx.getMessage();
            logger.error("Error SQL (code {}): {}", errorCode, errorMessage);
        }
        throw new RuntimeException("Error al ejecutar el procedimiento " + procedureName, e);
    }

    private SimpleJdbcCall configureJdbcCall(String procedureName) {
        return new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(SCHEMA_NAME)               // Esquema (owner)
                .withCatalogName(CATALOG_NAME)    // Package
                .withProcedureName(procedureName)       // Nombre del procedimiento
                .declareParameters(
                        new SqlParameter("p_active", Types.CHAR),
                        new SqlParameter("p_gender", Types.VARCHAR),
                        new SqlParameter("p_birth_date", Types.DATE),
                        new SqlParameter("p_deceased_boolean", Types.CHAR),
                        new SqlParameter("p_deceased_datetime", Types.TIMESTAMP),
                        new SqlParameter("p_name_family", Types.VARCHAR),
                        new SqlParameter("p_name_given", Types.VARCHAR),
                        new SqlParameter("p_name_prefix", Types.VARCHAR),
                        new SqlParameter("p_name_suffix", Types.VARCHAR),
                        new SqlParameter("p_telecom_system", Types.VARCHAR),
                        new SqlParameter("p_telecom_value", Types.VARCHAR),
                        new SqlParameter("p_address_line", Types.VARCHAR),
                        new SqlParameter("p_address_city", Types.VARCHAR),
                        new SqlParameter("p_address_state", Types.VARCHAR),
                        new SqlParameter("p_address_postal_code", Types.VARCHAR),
                        new SqlParameter("p_address_country", Types.VARCHAR),
                        new SqlParameter("p_marital_status", Types.VARCHAR),
                        new SqlParameter("p_multiple_birth_boolean", Types.CHAR),
                        new SqlParameter("p_multiple_birth_integer", Types.INTEGER),
                        new SqlParameter("p_photo_url", Types.VARCHAR),
                        new SqlParameter("p_identidad_genero_code", Types.VARCHAR),
                        new SqlParameter("p_identidad_genero_display", Types.VARCHAR),
                        new SqlParameter("p_sexo_biologico_code", Types.VARCHAR),
                        new SqlParameter("p_sexo_biologico_display", Types.VARCHAR),
                        new SqlParameter("p_nacionalidad_code", Types.VARCHAR),
                        new SqlParameter("p_nacionalidad_display", Types.VARCHAR),
                        new SqlParameter("p_identifier_use", Types.VARCHAR),
                        new SqlParameter("p_identifier_type_code", Types.VARCHAR),
                        new SqlParameter("p_identifier_type_display", Types.VARCHAR),
                        new SqlParameter("p_identifier_value", Types.VARCHAR),
                        new SqlParameter("p_nombre_oficial_family", Types.VARCHAR),
                        new SqlParameter("p_nombre_oficial_given", Types.VARCHAR),
                        new SqlParameter("p_segundo_apellido", Types.VARCHAR),
                        new SqlParameter("p_nombre_social_given", Types.VARCHAR),
                        new SqlParameter("p_contact_relationship_code", Types.VARCHAR),
                        new SqlParameter("p_contact_relationship_display", Types.VARCHAR),
                        new SqlParameter("p_contact_name_family", Types.VARCHAR),
                        new SqlParameter("p_contact_name_given", Types.VARCHAR),
                        new SqlParameter("p_communication_language_code", Types.VARCHAR),
                        new SqlParameter("p_communication_language_display", Types.VARCHAR),
                        new SqlParameter("p_general_practitioner_reference", Types.VARCHAR),
                        new SqlParameter("p_general_practitioner_display", Types.VARCHAR)
                );
    }

    private MapSqlParameterSource buildCommonParameters(FhirPatientClEntity patient) {
        return new MapSqlParameterSource()
                .addValue("p_active", patient.getActive())
                .addValue("p_gender", patient.getGender())
                .addValue("p_birth_date", patient.getBirthDate())
                .addValue("p_deceased_boolean", patient.getDeceasedBoolean())
                .addValue("p_deceased_datetime", patient.getDeceasedDateTime())
                .addValue("p_name_family", patient.getNameFamily())
                .addValue("p_name_given", patient.getNameGiven())
                .addValue("p_name_prefix", patient.getNamePrefix())
                .addValue("p_name_suffix", patient.getNameSuffix())
                .addValue("p_telecom_system", patient.getTelecomSystem())
                .addValue("p_telecom_value", patient.getTelecomValue())
                .addValue("p_address_line", patient.getAddressLine())
                .addValue("p_address_city", patient.getAddressCity())
                .addValue("p_address_state", patient.getAddressState())
                .addValue("p_address_postal_code", patient.getAddressPostalCode())
                .addValue("p_address_country", patient.getAddressCountry())
                .addValue("p_marital_status", patient.getMaritalStatus())
                .addValue("p_multiple_birth_boolean", patient.getMultipleBirthBoolean())
                .addValue("p_multiple_birth_integer", patient.getMultipleBirthInteger())
                .addValue("p_photo_url", patient.getPhotoUrl())
                .addValue("p_identidad_genero_code", patient.getIdentidadGeneroCode())
                .addValue("p_identidad_genero_display", patient.getIdentidadGeneroDisplay())
                .addValue("p_sexo_biologico_code", patient.getSexoBiologicoCode())
                .addValue("p_sexo_biologico_display", patient.getSexoBiologicoDisplay())
                .addValue("p_nacionalidad_code", patient.getNacionalidadCode())
                .addValue("p_nacionalidad_display", patient.getNacionalidadDisplay())
                .addValue("p_identifier_use", patient.getIdentifierUse())
                .addValue("p_identifier_type_code", patient.getIdentifierTypeCode())
                .addValue("p_identifier_type_display", patient.getIdentifierTypeDisplay())
                .addValue("p_identifier_value", patient.getIdentifierValue())
                .addValue("p_nombre_oficial_family", patient.getNombreOficialFamily())
                .addValue("p_nombre_oficial_given", patient.getNombreOficialGiven())
                .addValue("p_segundo_apellido", patient.getSegundoApellido())
                .addValue("p_nombre_social_given", patient.getNombreSocialGiven())
                .addValue("p_contact_relationship_code", patient.getContactRelationshipCode())
                .addValue("p_contact_relationship_display", patient.getContactRelationshipDisplay())
                .addValue("p_contact_name_family", patient.getContactNameFamily())
                .addValue("p_contact_name_given", patient.getContactNameGiven())
                .addValue("p_communication_language_code", patient.getCommunicationLanguageCode())
                .addValue("p_communication_language_display", patient.getCommunicationLanguageDisplay())
                .addValue("p_general_practitioner_reference", patient.getGeneralPractitionerReference())
                .addValue("p_general_practitioner_display", patient.getGeneralPractitionerDisplay());
    }






}