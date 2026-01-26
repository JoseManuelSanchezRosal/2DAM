package org.AF5_PSP_Dual_JMSR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Af5PspDualJmsrApplication {

	public static void main(String[] args) {
		// Arrancamos el servicio Spring Boot (RA4)
		SpringApplication.run(Af5PspDualJmsrApplication.class, args);
		System.out.println("--- SERVICIO REST INICIADO EN PUERTO 8080 ---");
	}

}