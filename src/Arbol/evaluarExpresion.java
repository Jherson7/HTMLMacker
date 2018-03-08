/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Jherson
 */
public  class evaluarExpresion {
    
    public static Object evaluar_expresion(nodo raiz){
        switch(raiz.hijos.size()){
            case 2:
                switch(raiz.nombre){
                    //casos relacionales
                    case "<":
                        return evaluarMENOR(raiz.hijos.get(0),raiz.hijos.get(1));
                    case "<=":
                        return evaluarMENORIGUAL(raiz.hijos.get(0),raiz.hijos.get(1));
                    case ">":
                        return evaluarMAYOR(raiz.hijos.get(0),raiz.hijos.get(1));
                    case ">=":
                        return evaluarMAYORIGUAL(raiz.hijos.get(0),raiz.hijos.get(1));
                    case "==":
                        return evaluarIGUAL(raiz.hijos.get(0), raiz.hijos.get(1));
                    case "!=":
                        return evaluarDIFERENTE(raiz.hijos.get(0), raiz.hijos.get(1));
                    //******casoss logicos
                    case "||":
                        return evaluarOR(raiz.hijos.get(0), raiz.hijos.get(1));
                    case "&&":
                        return evaluarAND(raiz.hijos.get(0), raiz.hijos.get(1));
                    //*******casos aritmeticos
                    case "+":
                        return evaluarSUMA(raiz.hijos.get(0), raiz.hijos.get(1));
                    case "-":
                        return evaluarRESTA(raiz.hijos.get(0), raiz.hijos.get(1));
                    case "*":
                        return evaluarMULTIPLICAR(raiz.hijos.get(0), raiz.hijos.get(1));
                    case "/":
                        return evaluarDIVIDIR(raiz.hijos.get(0), raiz.hijos.get(1));
                    case "^":
                    case "pot":
                        return evaluarPOTENCIAR(raiz.hijos.get(0), raiz.hijos.get(1));
                    case "%":
                        break;
                }
                break;
            case 1:
                switch(raiz.nombre){
                    case "UNARIO":
                        return evaluarUNARIO(raiz);
                    case "!":
                        return evaluarNOT(raiz.hijos.get(0));
                    case "CALLFUN":
//                        return ejecutarCALLFUN(raiz);
//                    case "ACCESO_VECTOR"://falta implementar
//                        return accesoVector(raiz);
//                    case "VECTOR":
//                        return crearVECTOR(raiz);
//                    case "ATEXTO":
//                        return convertirATEXTO(raiz);
//                    case "CONTEO":
//                        return contarVECTOR(raiz);
                }
                break;
            case 0:
                switch (raiz.nombre) {
                    case "NUM":
                       return Double.parseDouble(raiz.valor);
                    case "STRING":
                        return raiz.valor;
//                    case "ID":
//                        return evaluarID(raiz.valor);
                    case "TRUE":
                        return true;
                    case "FALSE":
                        return false;
//                    case "ACCESO_VECTOR"://falta implementar
//                        return accesoVector(raiz);
//                    case "VECTOR":
//                        return crearVECTOR(raiz);
//                    case "ATEXTO":
//                        return convertirATEXTO(raiz);
                    
                }
                break;
        }
        return null;
    }
     
    /************** SECCION DE OPERADORES RELACIONALES***************/
    
    private static Object evaluarOR(nodo izq, nodo der) {
           try
            {
                boolean val1, val2;
                val1 = (boolean)(evaluar_expresion(izq));
                val2 = (boolean)(evaluar_expresion(der));
                return val1 || val2;
            }
            catch (Exception e)
            {
                System.out.println("Error al evaluar OR");
                return null;
            }
    }

    private static Object evaluarAND(nodo izq, nodo der) {
        try
            {
                boolean val1, val2;
                val1 = (boolean)(evaluar_expresion(izq));
                val2 = (boolean)(evaluar_expresion(der));
                return val1 && val2;
            }
            catch (Exception e)
            {
                System.out.println("Error al evaluar and");
                return null;
            }
    }

    private  static Object evaluarIGUAL(nodo izq, nodo der) {
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        try {
            return uno.equals(dos);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Error al evaluar ==: " + izq.linea); 
        }
    }

    private static Object evaluarDIFERENTE(nodo izq, nodo der) {
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        try {
            return !uno.equals(dos);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Error al evaluar !=: " + izq.linea); 
        }
    }

    private static Object evaluarNOT(nodo izq) {
        try {
            boolean val1 =(boolean)evaluar_expresion(izq);
            return !val1;
        } catch (Exception e) {
             System.out.println("Error al evaluar NOT");
             throw new UnsupportedOperationException("Error al evaluar NOT: " + izq.linea); 
        }
    }

    
    //****************expresiones relacionales
    private static Object evaluarMENOR(nodo izq, nodo der) {
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        try {
            int tipo1= retornarTipo(uno);
            int tipo2= retornarTipo(dos);
      
            String a = uno.toString();
            String b = dos.toString();
            
            if (tipo1 == tipo2) {
                switch (tipo1) {
                    case 1:
                        return a.compareTo(b) > 0;
                    case 2:
                        return Double.parseDouble(a)< Double.parseDouble(b);
                    case 3:
                        return Date.parse(a) < Date.parse(b);   
                    default:
                        System.out.println("No se puede con el tipo: ");
                        return null;
                }
            }else{
                System.out.println("No son del mismo tipo");
                return null;
            }
            
        } catch (Exception e) {
             throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de <  cerca: " + izq.linea); 
        }
    }
    
    private static Object evaluarMAYOR(nodo izq, nodo der) {
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        try {
            int tipo1= retornarTipo(uno);
            int tipo2= retornarTipo(dos);
      
            String a = uno.toString();
            String b = dos.toString();
            
            if (tipo1 == tipo2) {
                switch (tipo1) {
                    case 1:
                        return a.compareTo(b) < 0;//no entiendo porque van alreves
                    case 2:
                        return Double.parseDouble(a)> Double.parseDouble(b);
                    case 3:
                        return Date.parse(a) > Date.parse(b);   
                    default:
                        System.out.println("No se puede con el tipo: ");
                        return null;
                }
            }else{
                System.out.println("No son del mismo tipo");
                return null;
            }
            
        } catch (Exception e) {
             throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de >  cerca: " + izq.linea); 
        }
    }

    
    private static Object evaluarMENORIGUAL(nodo izq, nodo der) {
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        try {
            int tipo1= retornarTipo(uno);
            int tipo2= retornarTipo(dos);
      
            String a = uno.toString();
            String b = dos.toString();
            
            if (tipo1 == tipo2) {
                switch (tipo1) {
                    case 1:
                        return a.compareTo(b) >= 0;
                    case 2:
                        return Double.parseDouble(a)<= Double.parseDouble(b);
                    case 3:
                        return Date.parse(a) <= Date.parse(b);   
                    default:
                        System.out.println("No se puede con el tipo: ");
                        return null;
                }
            }else{
                System.out.println("No son del mismo tipo");
                return null;
            }
            
        } catch (Exception e) {
             throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de <=  cerca: " + izq.linea); 
        }
    }
     
    private static Object evaluarMAYORIGUAL(nodo izq, nodo der) {
        
        
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        try {
            int tipo1= retornarTipo(uno);
            int tipo2= retornarTipo(dos);
      
            String a = uno.toString();
            String b = dos.toString();
            
            if (tipo1 == tipo2) {
                switch (tipo1) {
                    case 1:
                        int val = a.compareTo(b);
                        System.out.println(val);
                        return a.compareTo(b) <= 0;
                    case 2:
                        return Double.parseDouble(a)>= Double.parseDouble(b);
                    case 3:
                        return Date.parse(a) >= Date.parse(b);   
                    default:
                        System.out.println("No se puede con el tipo: ");
                        return null;
                }
            }else{
                System.out.println("No son del mismo tipo");
                return null;
            }
            
        } catch (Exception e) {
            throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de >=  cerca: " + izq.linea); 
        }
    }
     
    
    //*****************metodos para retonrnnar los tipos***********/////////
    private static int retornarTipo(Object a){//probar retornar tipo
        if(a instanceof String)
            return 1;
        else if (a instanceof Double)
            return 2;
        else if (a instanceof Boolean)
            return 3;
        else if (a instanceof Date)
            return 4;
        else if (a instanceof LinkedList)
            return 5;
        return -1;
    }

    private static String retornarTipoString(Object a){//probar retornar tipo
        if(a instanceof String)
            return "String";
        else if (a instanceof Double)
            return "Numeric";
        else if (a instanceof Boolean)
            return "Boolean";
        else if (a instanceof Date)
            return "Date";
        else if (a instanceof LinkedList)
            return "Vector";
        return "undefined";
    }
    
    private static Object evaluarSUMA(nodo izq, nodo der) {
       Object uno = evaluar_expresion(izq);
       Object dos = evaluar_expresion(der);
       
       int valor1 = retornarTipo(uno);
       int valor2 = retornarTipo(dos);
       
       if(valor1==-1||valor2==-1){
          System.out.println("Gravisimo error en ejecucioon cerca de la linea: "+izq.linea);
          throw new UnsupportedOperationException("Gravisimo error en la suma de tipos de datos cerca: " + izq.linea); 
       }
       if(valor1==1||valor2==1){
           return String.valueOf(uno) + String.valueOf(dos);
       }else if(valor1<4 && valor2<4){
           if(valor1==2||valor2==2){
               if(valor1==3)
                   uno =((boolean)uno? 1 : 0)*1.0;
               if(valor2==3)
                   dos =((boolean)dos? 1 : 0)*1.0;
               return (double)uno + (double)dos;
           }
           else
               return (boolean)uno || (boolean)dos;
       }else{
           System.out.println("Gravisimo error en la suma de tipos de datos cerca: "+izq.linea);
           throw new UnsupportedOperationException("Gravisimo error en la suma de tipos de datos cerca: " + izq.linea); 
       }
    }
    
    private static Object evaluarRESTA(nodo izq, nodo der) {
       Object uno = evaluar_expresion(izq);
       Object dos = evaluar_expresion(der);
       
       int valor1 = retornarTipo(uno);
       int valor2 = retornarTipo(dos);
       
       if(valor1==-1||valor2==-1){
           System.out.println("Gravisimo error en ejecucioon cerca de la linea: "+izq.linea);
          throw new UnsupportedOperationException("Gravisimo error en la resta de tipos de datos cerca: " + izq.linea); 
       }
        if (valor1 == 1 || valor2 == 1 || valor1 == 4 || valor2 == 4) {
            System.out.println("Gravisimo error en la resta de tipos de datos cerca: " + izq.linea);
            throw new UnsupportedOperationException("Gravisimo error en la resta de tipos de datos cerca: " + izq.linea); 
        }
        if (valor1 == 2 || valor2 == 2) {
             if(valor1==3)
                   uno =((boolean)uno? 1 : 0)*1.0;
               if(valor2==3)
                   dos =((boolean)dos? 1 : 0)*1.0;
            return (double) uno - (double) dos;
        } else {
            System.out.println("Gravisimo error en la resta de tipos de datos cerca: " + izq.linea);
            throw new UnsupportedOperationException("Gravisimo error en la restar de tipos de datos cerca: " + izq.linea); 
        }
    }
    
    private static Object evaluarMULTIPLICAR(nodo izq, nodo der) {
       Object uno = evaluar_expresion(izq);
       Object dos = evaluar_expresion(der);
       
       int valor1 = retornarTipo(uno);
       int valor2 = retornarTipo(dos);
       
       if(valor1==-1||valor2==-1){
           System.out.println("Gravisimo error en ejecucioon cerca de la linea: "+izq.linea);
          throw new UnsupportedOperationException("Gravisimo error en la multiplicacion de tipos de datos cerca: " + izq.linea); 
       }
        if (valor1 == 1 || valor2 == 1 || valor1 == 4 || valor2 == 4) {
            System.out.println("Gravisimo error en la resta de tipos de datos cerca: " + izq.linea);
            throw new UnsupportedOperationException("Gravisimo error en la multiplicacion de tipos de datos cerca: " + izq.linea); 
        }
        if (valor1 == 2 || valor2 == 2) {
             if(valor1==3)
                   uno =((boolean)uno? 1 : 0)*1.0;
               if(valor2==3)
                   dos =((boolean)dos? 1 : 0)*1.0;
            return (double) uno * (double) dos;
        } else {
           return (boolean)uno && (boolean)dos;
        }
    }
    
    private static Object evaluarDIVIDIR(nodo izq, nodo der) {
       Object uno = evaluar_expresion(izq);
       Object dos = evaluar_expresion(der);
       
       int valor1 = retornarTipo(uno);
       int valor2 = retornarTipo(dos);
       
       if(valor1==-1||valor2==-1){
           System.out.println("Gravisimo error en la DIVIDR de tipos de datos cerca: " + izq.linea);
           throw new UnsupportedOperationException("Gravisimo error en la DIVIDR de tipos de datos cerca: " + izq.linea); 
       }
        if (valor1 == 1 || valor2 == 1 || valor1 == 4 || valor2 == 4) {
            System.out.println("Gravisimo error en la DIVIDR de tipos de datos cerca: " + izq.linea);
            throw new UnsupportedOperationException("Gravisimo error en la DIVIDR de tipos de datos cerca: " + izq.linea); 
        }
        if (valor1 == 2 || valor2 == 2) {
             if(valor1==3)
                   uno =((boolean)uno? 1 : 0)*1.0;
               if(valor2==3)
                   dos =((boolean)dos? 1 : 0)*1.0;
            return (double) uno / (double) dos;
        } else {
            System.out.println("Gravisimo error en la DIVIDR de tipos de datos cerca: " + izq.linea);
            throw new UnsupportedOperationException("Gravisimo error en la DIVIDR de tipos de datos cerca: " + izq.linea); 
        }
    }
    
    private static Object evaluarPOTENCIAR(nodo izq, nodo der) {
       Object uno = evaluar_expresion(izq);
       Object dos = evaluar_expresion(der);
       
       int valor1 = retornarTipo(uno);
       int valor2 = retornarTipo(dos);
       
       if(valor1==-1||valor2==-1){
           System.out.println("Gravisimo error en ejecucioon cerca de la linea: "+izq.linea);
           throw new UnsupportedOperationException("Not supported yet.");
       }
        if (valor1 == 1 || valor2 == 1 || valor1 == 4 || valor2 == 4) {
            System.out.println("Gravisimo error en la potenciar de tipos de datos cerca: " + izq.linea);
            throw new UnsupportedOperationException("Not supported yet.");
        }
        if (valor1 == 2 || valor2 == 2) {
             if(valor1==3)
                   uno =((boolean)uno? 1 : 0)*1.0;
               if(valor2==3)
                   dos =((boolean)dos? 1 : 0)*1.0;
            return Math.pow((double) uno,  (double) dos);
        } else {
            System.out.println("Gravisimo error en la potenciar de tipos de datos cerca: " + izq.linea);
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

//    //***************EVALUAR ID
//    
//    private Object evaluarID(String nombre) {
//        variable var = getVariable(nombre);
//        if(var!=null)
//            return var.valor;
//        return null;
//    }

    private static  Object evaluarUNARIO(nodo raiz) {
    
        Object val = evaluar_expresion(raiz.hijos.get(0));
        int tipo =retornarTipo(val);
        
        if(tipo==2||tipo==3){
            return (double)val * -1;
        }else{
           throw new UnsupportedOperationException("Error en el unario por el tipo!"); //To change body of generated methods, choose Tools | Templates. 
        }
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
