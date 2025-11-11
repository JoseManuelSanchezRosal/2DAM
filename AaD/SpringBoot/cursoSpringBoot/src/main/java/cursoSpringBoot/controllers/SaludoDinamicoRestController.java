package cursoSpringBoot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludoDinamicoRestController {

    @GetMapping("saludo/{name}")
    // PARA DECIRLE QUE EL PARAMETRO DINAMICO "NAME" VIENE DESDE LA URl (saludo personalizado) USAMOS @PATHVARIABLE
    public String greeting(@PathVariable String name){
        return "Hola " + name;
    }
}
