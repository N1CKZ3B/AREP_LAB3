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

        URL serviceurl1 = new URL("http://localhost8080/App/mañana");

        String path1 = serviceurl1.getPath();
        System.out.println(path1);
        String serviceName1 = path1.substring(4);
        System.out.println("serviceName: "+ serviceName1);

        Method ms1 = services.get(serviceName1);

        System.out.println("Rta: "+ ms1.invoke(ms));

        URL serviceurl2 = new URL("http://localhost8080/App/euler");

        String path2 = serviceurl2.getPath();
        System.out.println(path2);
        String serviceName2 = path2.substring(4);
        System.out.println("serviceName: "+ serviceName2);

        Method ms2 = services.get(serviceName2);

        System.out.println("Rta: "+ ms2.invoke(ms));

        URL serviceurl3 = new URL("http://localhost8080/App/editor");

        String path3 = serviceurl3.getPath();
        System.out.println(path3);
        String serviceName3 = path3.substring(4);
        System.out.println("serviceName: "+ serviceName3);

        Method ms3 = services.get(serviceName3);

        System.out.println("Rta: "+ ms3.invoke(ms));
    }
    
   
    
}
