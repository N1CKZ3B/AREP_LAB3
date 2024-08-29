/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.springeci;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author NICOLAS.ACHURI-M
 */
public class Springeci {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, MalformedURLException {
        Class c = Class.forName(args[0]);
        Map<String,Method> services = new HashMap();
        
        if (c.isAnnotationPresent(RestController.class)){
            Method[] methods = c.getDeclaredMethods();
            for (Method m: methods){
                if(m.isAnnotationPresent(GetMapping.class)){
                    String key = m.getAnnotation(GetMapping.class).value();
                    services.put(key,m);
                }
            }
        }  
        URL serviceurl = new URL("http://localhost8080/App/hello");

        String path = serviceurl.getPath();
        System.out.println(path);
        String serviceName = path.substring(4);
        System.out.println("serviceName: "+ serviceName);

        Method ms = services.get(serviceName);

        System.out.println("Rta: "+ ms.invoke(ms));
    }
    
   
    
}
