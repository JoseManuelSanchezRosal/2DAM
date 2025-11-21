package com.dam2.Practica1.config;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    // ThreadPoolExecutor para control de Peliculas
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
        ex.setCorePoolSize(3);
        ex.setMaxPoolSize(6);
        ex.setQueueCapacity(20);
        ex.setThreadNamePrefix("psp-");
        ex.initialize();
        return ex;
    }
    // Ejercicio 4
    /**
     * Configura un pool de hilos para las
     *
     *
     * votaciones del Jurado
     * @return un Executor configurado con el nombre "threadsJurado" listo para usarse
     */
    @Bean(name = "threadsJurado")
    public ThreadPoolTaskExecutor threadsJurado(){

        ThreadPoolTaskExecutor exJu = new ThreadPoolTaskExecutor();
        exJu.setCorePoolSize(10); // Maximo de hilos vivos
        exJu.setMaxPoolSize(15); // Maximo de hilos que se crean si se llena la cola
        exJu.setQueueCapacity(1000); // Capacidad de la cola de espera antes de ser rechazados
        exJu.setThreadNamePrefix("Thread-"); // Prefijo del hilo
        exJu.initialize(); // Ejecucion
        return exJu;
    }
}