package com.company;

public class Main {

    public static void main(String args[]){
        int a=-2; int b=3; int c=-1;
        double delta;
           
        if(a==0){
            System.out.println("To nie jest funkcja kwadratowa");
        }else{
            delta=b*b-4*a*c;
            if (delta<0){
                System.out.println("Brak rozwiązań.");
            }if(delta==0){
                System.out.println("Równanie ma 1 pierwiastek, x1 = " + (-b/(2*a)) );
            }if(delta>0){
                System.out.println("x1 = " + (-b-Math.sqrt(delta))/(2*a) );
                System.out.println("x2 = " + (-b+Math.sqrt(delta))/(2*a) );
            }
        }
    }
    
}
