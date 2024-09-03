/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reflexion;

import java.lang.reflect.Method;

/**
 *
 * @author NICOLAS.ACHURI-M
 */
public class JUnitECI {
    public static void main (String[] args) throws ClassNotFoundException{
        Class c = Class.forName(args[0]);
        
        Method[] methods = c.getDeclaredMethods();
        
        int passed = 0;
        int failed = 0;
        
         for (Method m: methods){
            if(m.isAnnotationPresent(Test.class)){
                try{
                    m.invoke(null);
                    System.out.println("Test " + m.getName() +" PASSED");
                    passed++;
                }catch (Exception e){
                    System.out.println("Test " + m.getName() + " FAILED");
                    failed++;
                }
            }
        }
         System.out.println("PASSED: "+passed);
         System.out.println("FAILED: "+failed);
    }
}
