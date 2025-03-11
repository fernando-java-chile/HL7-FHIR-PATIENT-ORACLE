package com.fichaClinica.HL7FHIR_SAMPLES_ORACLE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Hl7FhirSamplesOracleApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hl7FhirSamplesOracleApplication.class, args);
	}

}
