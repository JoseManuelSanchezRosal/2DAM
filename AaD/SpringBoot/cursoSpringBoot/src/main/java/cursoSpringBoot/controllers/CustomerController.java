package cursoSpringBoot.controllers;

import cursoSpringBoot.domain.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class CustomerController {
    // Lista de Clientes (Recursos, simulando bbdd)
    private List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer(123, "Gerardo Lopez", "gerardol", "constrasena123"),
            new Customer(456, "Alejandra Garcia", "alegarcia", "clave456"),
            new Customer(789, "Laura Sanchez", "lauras", "secreto789"),
            new Customer(234, "Carlos Martinez", "carlosm", "password234")
    ));

    /**
     * Metodo para devolver lista de clientes
     * @return lista de clientes
     */
    @GetMapping("/clientes")
    public List<Customer> getCustomers(){

        return customers;
    }

    /**
     * Metodo para devolver cliente por USERNAME con PathVarible
     * @param username a devolver
     * @return cliente
     */
    @GetMapping("/clientes/{username}")
    public Customer getCliente(@PathVariable String username) {
        for (Customer c : customers) {
            if (c.getUserName().equalsIgnoreCase(username)) {

                return c;
            }
        }
        return null;
    }

    // Ingresamos tipo de request POST y en parametro ingresamos @RequestBody para decirle a nuestro programa que recibira el JSON con el nuevo cliente

    /**
     * Metodo para anadir un cliente  con Request body
     * @param customer anadido
     * @return cliente
     */
    @PostMapping("/clientes")
    public Customer postCliente(@RequestBody Customer customer){
        customers.add(customer);
        return customer;
    }

    /**
     * Metodo para modificar un cliente por su id con RequestBody
     * @param customer cliente a modificar
     * @return cliente modificado
     */
    @PutMapping("/clientes")
    public Customer putMapping(@RequestBody Customer customer){
        for(Customer c : customers){
            if(c.getID() == customer.getID()){
                c.setName(customer.getName());
                c.setUserName(customer.getUserName());
                c.setPassword(customer.getPassword());

                return c;
            }
        }
        return null;
    }

    /**
     * Metodo para borrar un cliente por su ID con PathVariable
     * @param id id del cliente a borrar
     * @return cliente borrado, si lo encuentra, sino devuelve null
     */
    @DeleteMapping("/clientes/{id}")
    public Customer deleteMapping(@PathVariable int id){
        for (Customer c : customers){
            if (c.getID() == id){
                customers.remove(c);

                return c;
            }
        }
        return null;
    }
}
// VIDEO 15