package com.practicaPropuesta1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para verificar Palindromos
 */

@RestController
public class PalindromeController {

    /**
     * EndPoint para verificar si la palabra es o no palindromo
     * @param word la palabra a verificar
     * @return mensaje indicando si lo es o no
     */
    @GetMapping("validar-palindromo/{word}")
    public String Palindrome(@PathVariable String word){
        if (isPalindrome(word)) {
            return "La palabra " + word + " es Palindroma";
        }else{
            return "La palabra " + word + " NO es Palindroma";
        }
    }


    // ESTO NO ESTA BIEN, HAY QUE SEPARAR LAS RESPONSABILIDADES

    /**
     * Metodo para verificar si una palabra es palindromo
     * @param word palabra a verificar
     * @return true si lo es, false si no lo es
     */
    private boolean isPalindrome(String word){
        int length = word.length();
        // Para saber si una palabra es palindroma solo necesitamos analizar la mitad de la longitud de la palabra dinamica aportada desde la url
        for (int i = 0; i < length/2 ; i++) {
            // Con charAt comparamos el primer caracter de nuestra palabra
            // Si a la palabra le restamos i y -1 obtenemos el ultimo
            if (word.charAt(i) != word.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }
}