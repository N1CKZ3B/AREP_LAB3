
package com.mycompany.springeci;

@RestController
public class HelloService{
    
    @GetMapping("/hello")
    public static String hello(){
        return "Hello World!";
    }
    
    @GetMapping("/mañana")
    public static String mañana(){
        return "Mañana es viernes";
    }
    
    @GetMapping("/euler")
    public static String euler(){
        return "euler es igual a 2,7182818284590";
    }
    
    @GetMapping("/editor")
    public static String editor(){
        return "El editor es Nicolas Sebastian Achuri Macias";
    }
    
    
    
    
}
