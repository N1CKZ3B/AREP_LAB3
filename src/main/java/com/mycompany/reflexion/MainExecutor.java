/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reflexion;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 *
 * @author NICOLAS.ACHURI-M
 */
public class MainExecutor {
     public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
         Class c = Class.forName(args[0]);
         Class[] mainParamTypes = {String[].class};
         Method main = c.getDeclaredMethod("main", mainParamTypes);
         
         String[] parms = {args[1],args[2]};
         main.invoke(null,(Object)parms);
         
     }
}
