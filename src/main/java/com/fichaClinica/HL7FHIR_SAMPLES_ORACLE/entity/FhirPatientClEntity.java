package com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "FHIR_PATIENT_CL_1_8_5")
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
@Builder // Patrón Builder para crear instancias de la clase
public class FhirPatientClEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PATIENT_ID")
    private Long patientId;

    @Column(name = "ACTIVE", columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private String active;

    @Column(name = "GENDER", length = 10)
    private String gender;

    @Column(name = "BIRTH_DATE")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "DECEASED_BOOLEAN", columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String deceasedBoolean;

    @Column(name = "DECEASED_DATETIME")
    private Timestamp deceasedDateTime;

    @Column(name = "NAME_FAMILY", length = 100)
    private String nameFamily;

    @Column(name = "NAME_GIVEN", length = 100)
    private String nameGiven;

    @Column(name = "NAME_PREFIX", length = 50)
    private String namePrefix;

    @Column(name = "NAME_SUFFIX", length = 50)
    private String nameSuffix;

    @Column(name = "TELECOM_SYSTEM", length = 20)
    private String telecomSystem;

    @Column(name = "TELECOM_VALUE", length = 100)
    private String telecomValue;

    @Column(name = "ADDRESS_LINE", length = 200)
    private String addressLine;

    @Column(name = "ADDRESS_CITY", length = 100)
    private String addressCity;

    @Column(name = "ADDRESS_STATE", length = 100)
    private String addressState;

    @Column(name = "ADDRESS_POSTAL_CODE", length = 20)
    private String addressPostalCode;

    @Column(name = "ADDRESS_COUNTRY", length = 100)
    private String addressCountry;

    @Column(name = "MARITAL_STATUS", length = 50)
    private String maritalStatus;

    @Column(name = "MULTIPLE_BIRTH_BOOLEAN", columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String multipleBirthBoolean;

    @Column(name = "MULTIPLE_BIRTH_INTEGER")
    private Integer multipleBirthInteger;

    @Column(name = "PHOTO_URL", length = 500)
    private String photoUrl;

    @Column(name = "CREATED_DATE", columnDefinition = "TIMESTAMP DEFAULT SYSTIMESTAMP")
    private Timestamp createdDate;

    @Column(name = "LAST_UPDATED", columnDefinition = "TIMESTAMP DEFAULT SYSTIMESTAMP")
    private Timestamp lastUpdated;

    @Column(name = "IDENTIDAD_GENERO_CODE", length = 50)
    private String identidadGeneroCode;

    @Column(name = "IDENTIDAD_GENERO_DISPLAY", length = 100)
    private String identidadGeneroDisplay;

    @Column(name = "SEXO_BIOLOGICO_CODE", length = 50)
    private String sexoBiologicoCode;

    @Column(name = "SEXO_BIOLOGICO_DISPLAY", length = 100)
    private String sexoBiologicoDisplay;

    @Column(name = "NACIONALIDAD_CODE", length = 50)
    private String nacionalidadCode;

    @Column(name = "NACIONALIDAD_DISPLAY", length = 100)
    private String nacionalidadDisplay;

    @Column(name = "IDENTIFIER_USE", length = 20)
    private String identifierUse;

    @Column(name = "IDENTIFIER_TYPE_CODE", length = 50)
    private String identifierTypeCode;

    @Column(name = "IDENTIFIER_TYPE_DISPLAY", length = 100)
    private String identifierTypeDisplay;

    @Column(name = "IDENTIFIER_VALUE", length = 100)
    private String identifierValue;

    @Column(name = "NOMBRE_OFICIAL_FAMILY", length = 100)
    private String nombreOficialFamily;

    @Column(name = "NOMBRE_OFICIAL_GIVEN", length = 100)
    private String nombreOficialGiven;

    @Column(name = "SEGUNDO_APELLIDO", length = 100)
    private String segundoApellido;

    @Column(name = "NOMBRE_SOCIAL_GIVEN", length = 100)
    private String nombreSocialGiven;

    @Column(name = "CONTACT_RELATIONSHIP_CODE", length = 50)
    private String contactRelationshipCode;

    @Column(name = "CONTACT_RELATIONSHIP_DISPLAY", length = 100)
    private String contactRelationshipDisplay;

    @Column(name = "CONTACT_NAME_FAMILY", length = 100)
    private String contactNameFamily;

    @Column(name = "CONTACT_NAME_GIVEN", length = 100)
    private String contactNameGiven;

    @Column(name = "COMMUNICATION_LANGUAGE_CODE", length = 50)
    private String communicationLanguageCode;

    @Column(name = "COMMUNICATION_LANGUAGE_DISPLAY", length = 100)
    private String communicationLanguageDisplay;

    @Column(name = "GENERAL_PRACTITIONER_REFERENCE", length = 500)
    private String generalPractitionerReference;

    @Column(name = "GENERAL_PRACTITIONER_DISPLAY", length = 100)
    private String generalPractitionerDisplay;
}
