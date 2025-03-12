package com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.controller;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/refreshscope")
@Getter
@Setter
@RefreshScope
public class RefreshScopeController {

    @Value("${app.testProp}")
    private String testProp;

    // cambio del valor desde github a través del sistema config-server
    // @RefreshScope: Refrescando las Configuraciones para ejecurtar los cambios en tiempo de ejecución
    // POST a //192.168.100.X:port/actuator/refresh
    //          localhost:9091/actuator/refresh
    // y luego 192.168.100.14:port/v1/refreshscope/test


    @RequestMapping("/test")
    public String test() {
        return testProp;
    }

    // Acceso a las propiedades de configuración del microservicio HL7-FHIR-PATIENT-ORACLE en el entorno dev
    // http://localhost:8888/HL7-FHIR-PATIENT-ORACLE/dev
}
