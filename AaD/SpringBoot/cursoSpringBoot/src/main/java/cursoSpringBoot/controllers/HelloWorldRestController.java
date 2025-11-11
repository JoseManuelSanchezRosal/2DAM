package cursoSpringBoot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 1 DECIMOS A NUESTRA CLASE QUE SERA REST CONTROLLER (HTTP)
@RestController

public class HelloWorldRestController {

    // 2 DECIRLE EL ENDPOINT DONDE INVOCAREMOS NUESTRO METODO
    // SI COLOCAMOS VARIAS RUTAS ENTRE LLAVES, PODREMOS INVOCAR ESTE METODO CON LOS ENDPOINTS QUE LE DIGAMOS, EJEMPLO
    @GetMapping({"/hello", "hw", "hola"})
    public String helloWorld(){
        System.out.println("Solicitud Ejecutada!!");
        return "Hello World";
    }
}
