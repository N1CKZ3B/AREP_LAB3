/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reflexion;

/**
 *
 * @author NICOLAS.ACHURI-M
 */
public class ClassToBeTested {
    @Test
     public static void m1(){
         System.out.println("Ok.");
     }
     
     public static void m2(){
         System.out.println("Ok.");
     }
     
     public static void m3(){
         System.out.println("Ok.");
     }
     
     @Test
     public static void m4() throws Exception{
         throw new Exception ("Error for m4");
     }
     
     @Test
     public static void m5() throws Exception{
         throw new Exception ("Error for m5");

     }
}
