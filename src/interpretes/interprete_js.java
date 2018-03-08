package interpretes;

import Arbol.control;
import Arbol.nodo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author Jherson
 */

public final class interprete_js {

    public LinkedList<metodo>metodos;
    public HashMap<String,variable> lista_actual;
    public LinkedList<HashMap<String,variable>> lista_ambitos;
    
    
    //varible booleana break
    boolean detener;
    boolean bandera_retorno;
    public StringBuilder consola;
    Object retorno;
    
    
    public interprete_js(nodo raiz) {
        this.metodos= new LinkedList<>();
        this.lista_ambitos= new LinkedList<>();
        detener=false;
        consola=new StringBuilder();
        bandera_retorno=false;
        //lista_error=control.getLista();
        
        aumentar_ambito();
        guardarFunciones(raiz);
        ejecutar(raiz);
        
    }
     public interprete_js() {
        this.metodos= new LinkedList<>();
        this.lista_ambitos= new LinkedList<>();
        detener=false;
        consola=new StringBuilder();
        bandera_retorno=false;
//        lista_error=control.getLista();
        
        aumentar_ambito();
        
    }
    
    
    public void aumentar_ambito(){
        this.lista_ambitos.addFirst(new HashMap<>());
        this.lista_actual=lista_ambitos.getFirst();
    }
    
    public void disminuir_ambito(){
        this.lista_ambitos.removeFirst();
        this.lista_actual= lista_ambitos.getFirst();
    }
    
    
    public void guardarFunciones(nodo raiz) {
        switch(raiz.nombre){
         case "SENTENCIAS":
                for(nodo a:raiz.hijos){
                    guardarFunciones(a);
                }
                break;
            case "FUNCION":
                guardarFUNCION(raiz);
                break;
        }
       
    }
    
    ///**************/*/*/METODO EJECUTOR****************************////////////
    public void ejecutar(nodo raiz) {
        
        switch (raiz.nombre) {
            case "SENTENCIAS":
                for(nodo a:raiz.hijos){
                     if (detener)
                        break;
                     if(bandera_retorno)
                        return;
                    ejecutar(a);
                }
                break;
            
            case "BREAK":
                detener = true;
                break;
            case "continuar":
                break;
            case "DECLARAR":
                ejecutar_declarar(raiz);
                break;
            case "DECLARAR_ASIG":
                ejecutarDECLARAR_ASIG(raiz);
                break;
            case "ASIGNAR":
                ejecutarASIGNAR(raiz);
                break;
            case "IF":
                ejecutaIF(raiz);
                break;
            case "CASE":
                ejecutarCASE(raiz);
                break;
            case "AUMENTO":
                break;
            case "DECREMENTO":
                break;
            case "FOR":
                ejecutarFOR(raiz);
                break;
            case "WHILE":
                ejecutarWHILE(raiz);
                break;
            case "IMPRIMIR":
                ejecutarIMPRIMIR(raiz);
                break;
            case "CONTEO":
                break;
            case "MENSAJE":
                ejecutarMESAJE(raiz);
                break;
            case "RETORNAR":
                ejecutarRETORNAR(raiz);
                break;
            case "CALLFUN":
                ejecutarCALLFUN(raiz);
                break;
//            case "FUNCION":
//                guardarFUNCION(raiz);
//                break;
            case "DIMENSION":
                declararVECTOR(raiz);
                break;
            case "ASIGNAR_VECTOR":
                asignarVECTOR(raiz);
                break;
            case "++":
                aumento(raiz.get(0));
                break;
            case "--":
                decremento(raiz.get(0));
                break;
        }
    }

    private void ejecutar_declarar(nodo raiz) {
        if(lista_actual.containsKey(raiz.valor)){
            System.out.println("Ya existe la variable en el ambito actual");
            agregarError("Semantico","Ya existe la variable en el ambito actual: "+raiz.valor, raiz.linea, raiz.columna);
            return;
        }
        lista_actual.put(raiz.valor.toLowerCase(), new variable(raiz.valor.toLowerCase()));
    }
    
    
    private variable getVariable(String id){
        id=id.toLowerCase();//porque estoy guardando todas las variables ignorecase
        
        for(HashMap<String,variable> x: lista_ambitos){
            if(x.containsKey(id)){
                return x.get(id);
            }
        }
        agregarError("Semantico", "no se encuentra la variable: "+ id, 0,0);
        return null;
    }
    
    
    private int guardarVariable(String tipo,String id, Object val){
        if(lista_actual.containsKey(id)){
            System.out.println("La variable ya existe en el ambito actual");
            return 0;
        }
        //las guardo en lowercas
        lista_actual.put(id.toLowerCase(), new variable(tipo, id.toLowerCase(), val));
        return 1;
    }
    
    //*******************EVALUAR EXPRESION******************///////
    private Object evaluar_expresion(nodo raiz){
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
                        return evaluarMOD(raiz.hijos.get(0), raiz.hijos.get(1));
                }
                break;
            case 1:
                switch(raiz.nombre){
                    case "UNARIO":
                        return evaluarUNARIO(raiz);
                    case "!":
                        return evaluarNOT(raiz.hijos.get(0));
                    case "CALLFUN":
                        return ejecutarCALLFUN(raiz);
                    case "ACCESO_VECTOR"://falta implementar
                        return accesoVector(raiz);
                    case "VECTOR":
                        return crearVECTOR(raiz);
                    case "ATEXTO":
                        return convertirATEXTO(raiz);
                    case "CONTEO":
                        return contarVECTOR(raiz);
                    case "++":
                        return aumento(raiz.get(0));
                    case "--":
                        return decremento(raiz.get(0));
                }
                break;
            case 0:
                switch (raiz.nombre) {
                    case "NUM":
                       return Double.parseDouble(raiz.valor);
                    case "STRING":
                        return raiz.valor;
                    case "ID":
                        return evaluarID(raiz.valor);
                    case "TRUE":
                        return true;
                    case "FALSE":
                        return false;
                    case "ACCESO_VECTOR"://falta implementar
                        return accesoVector(raiz);
                    case "VECTOR":
                        return crearVECTOR(raiz);
                    case "ATEXTO":
                        return convertirATEXTO(raiz);
                    case "DATE":
                        return retornarDate(raiz);
                        
                    
                }
                break;
        }
        return null;
    }
     
    /************** SECCION DE OPERADORES RELACIONALES***************/
    
    private Object evaluarOR(nodo izq, nodo der) {
           try
            {
                boolean val1, val2;
                val1 = (boolean)(evaluar_expresion(izq));
                val2 = (boolean)(evaluar_expresion(der));
                return val1 || val2;
            }
            catch (Exception e)
            {
                agregarError("Semantico", "Error al evaluar  or los operandos", izq.linea, izq.columna);
                return null;
            }
    }

    private Object evaluarAND(nodo izq, nodo der) {
        try
            {
                boolean val1, val2;
                val1 = (boolean)(evaluar_expresion(izq));
                val2 = (boolean)(evaluar_expresion(der));
                return val1 && val2;
            }
            catch (Exception e)
            {
                agregarError("Semantico", "Error al evaluar and ", izq.linea, izq.columna);
                return null;
            }
    }

    private Object evaluarIGUAL(nodo izq, nodo der) {
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        
        int tipo1= retornarTipo(uno);
        int tipo2 = retornarTipo(dos);
        if (tipo1 == tipo2) {
            try {
                return uno.equals(dos);
            } catch (Exception e) {
                throw new UnsupportedOperationException("Error al evaluar ==: " + izq.linea);
            }
        }
        try {
      
            if(tipo1==2||tipo2==2){
                double val1= convertir_a_double(uno);
                double val2 = convertir_a_double(dos);
                return val1 == val2;
            }else if(tipo1==4||tipo2==4){//comparacion de date
                Object val1= convertir_a_date(uno,izq);
                Object val2 = convertir_a_date(dos, izq);
                
                return Date.parse(val1.toString())==Date.parse(val2.toString());
                
            }else if(tipo1==1||tipo2==1){//conversion con tipo cadena
                String val1=convertir_a_string(uno,izq);
                String val2=convertir_a_string(dos,izq);
                return val1.length() == val2.length();
            }else if(tipo1==3&&tipo2==3){//comparacion de boolean
                int val1= ((boolean)uno? 1 : 0);
                int val2= ((boolean)dos? 1 : 0);
                return val1==val2;
            }else{
                agregarError("Semantico", "Error al evaluar == los operandos", izq.linea, izq.columna);
               throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de ==  cerca: " + izq.linea);  
            }
        } catch (Exception e) {
            agregarError("Semantico", "Error al evaluar == los operandos", izq.linea, izq.columna);
             throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de ==  cerca: " + izq.linea); 
        }
       
    }

    private Object evaluarDIFERENTE(nodo izq, nodo der) {
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        int tipo1= retornarTipo(uno);
        int tipo2 = retornarTipo(dos);
        
        if (tipo1 == tipo2) {
            try {
                return !uno.equals(dos);
            } catch (Exception e) {
                agregarError("Semantico", "Error al evaluar != los operandos", izq.linea, izq.columna);
                throw new UnsupportedOperationException("Error al evaluar !=: " + izq.linea);
            }
        }
        try {
            if(tipo1==2||tipo2==2){
                double val1 = convertir_a_double(uno);
                double val2 = convertir_a_double(dos);
                return val1 != val2;
            }else if(tipo1==4||tipo2==4){//comparacion de date
                Object val1= convertir_a_date(uno,izq);
                Object val2 = convertir_a_date(dos, izq);
                
                return Date.parse(val1.toString())!=Date.parse(val2.toString());
                
            }else if(tipo1==1||tipo2==1){//conversion con tipo cadena
                String val1=convertir_a_string(uno,izq);
                String val2=convertir_a_string(dos,izq);
                
                return !val1.equals(val2);
            
            }else if(tipo1==3&&tipo2==3){//comparacion de boolean
                int val1= ((boolean)uno? 1 : 0);
                int val2= ((boolean)dos? 1 : 0);
                return val1!=val2;
            }else{
                agregarError("Semantico", "Error al evaluar != los operandos", izq.linea, izq.columna);
               throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de !=  cerca: " + izq.linea);  
            }
        } catch (Exception e) {
            agregarError("Semantico", "Error al evaluar 1= los operandos", izq.linea, izq.columna);
             throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de !=  cerca: " + izq.linea); 
        }
    }

    private Object evaluarNOT(nodo izq) {
        try {
            boolean val1 =(boolean)evaluar_expresion(izq);
            return !val1;
        } catch (Exception e) {
             agregarError("Semantico", "Error al evaluar not", izq.linea, izq.columna);
             throw new UnsupportedOperationException("Error al evaluar NOT: " + izq.linea); 
        }
    }

    
    //****************expresiones relacionales
    private Object evaluarMENOR(nodo izq, nodo der) {
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        try {
            int tipo1= retornarTipo(uno);
            int tipo2= retornarTipo(dos);
      
            if(tipo1==2||tipo2==2){
                double val1= convertir_a_double(uno);
                double val2 = convertir_a_double(dos);
                
                return val1 < val2;
            }else if(tipo1==4||tipo2==4){//comparacion de date
                Object val1= convertir_a_date(uno,izq);
                Object val2 = convertir_a_date(dos, izq);
                
                return Date.parse(val1.toString()) < Date.parse(val2.toString());
                
            }else if(tipo1==1||tipo2==1){//conversion con tipo cadena
                String val1=convertir_a_string(uno,izq);
                String val2=convertir_a_string(dos,izq);
                
                return val1.length() < val2.length();
            }else if(tipo1==3&&tipo2==3){//comparacion de boolean
                int val1= ((boolean)uno? 1 : 0);
                int val2= ((boolean)dos? 1 : 0);
                
                return val1 < val2;
            }else{
                agregarError("Semantico", "Error al evaluar < los operandos", izq.linea, izq.columna);
               throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de <  cerca: " + izq.linea);  
            }
        } catch (Exception e) {
            agregarError("Semantico", "Error al evaluar < los operandos", izq.linea, izq.columna);
             throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de <  cerca: " + izq.linea); 
        }
    }
    
    private Object evaluarMAYOR(nodo izq, nodo der) {
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        try {
            int tipo1= retornarTipo(uno);
            int tipo2= retornarTipo(dos);
      
            if(tipo1==2||tipo2==2){
                double val1= convertir_a_double(uno);
                double val2 = convertir_a_double(dos);
                return val1 > val2;
            }else if(tipo1==4||tipo2==4){//comparacion de date
                Object val1= convertir_a_date(uno,izq);
                Object val2 = convertir_a_date(dos, izq);
                
                return Date.parse(val1.toString()) >Date.parse(val2.toString());
                
            }else if(tipo1==1||tipo2==1){//conversion con tipo cadena
                String val1=convertir_a_string(uno,izq);
                String val2=convertir_a_string(dos,izq);
                return val1.length() > val2.length();
            }else if(tipo1==3&&tipo2==3){//comparacion de boolean
                int val1= ((boolean)uno? 1 : 0);
                int val2= ((boolean)dos? 1 : 0);
                return val1>val2;
            }else{
                agregarError("Semantico", "Error al evaluar > los operandos", izq.linea, izq.columna);
               throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de >  cerca: " + izq.linea);  
            }
        } catch (Exception e) {
            agregarError("Semantico", "Error al evaluar > los operandos", izq.linea, izq.columna);
             throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de >  cerca: " + izq.linea); 
        }
    }

    private Object evaluarMENORIGUAL(nodo izq, nodo der) {
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        try {
            int tipo1= retornarTipo(uno);
            int tipo2= retornarTipo(dos);
      
            if(tipo1==2||tipo2==2){
                double val1 = convertir_a_double(uno);
                double val2 = convertir_a_double(dos);
                
                return val1 <= val2;
            }else if(tipo1==4||tipo2==4){//comparacion de date
                Object val1= convertir_a_date(uno,izq);
                Object val2 = convertir_a_date(dos, izq);
                
                return Date.parse(val1.toString()) <= Date.parse(val2.toString());
                
            }else if(tipo1==1||tipo2==1){//conversion con tipo cadena
                String val1=convertir_a_string(uno,izq);
                String val2=convertir_a_string(dos,izq);
                
                return val1.length() <= val2.length();
            }else if(tipo1==3&&tipo2==3){//comparacion de boolean
                int val1= ((boolean)uno? 1 : 0);
                int val2= ((boolean)dos? 1 : 0);
                
                return val1<=val2;
            }else{
                agregarError("Semantico", "Error al evaluar <= los operandos", izq.linea, izq.columna);
               throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de <=  cerca: " + izq.linea);  
            }
        } catch (Exception e) {
            agregarError("Semantico", "Error al evaluar <= los operandos", izq.linea, izq.columna);
             throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de <=  cerca: " + izq.linea); 
        }
    }
     
    private Object evaluarMAYORIGUAL(nodo izq, nodo der) {
        
        Object uno = evaluar_expresion(izq);
        Object dos = evaluar_expresion(der);
        try {
            int tipo1= retornarTipo(uno);
            int tipo2= retornarTipo(dos);
      
            if(tipo1==2||tipo2==2){
                double val1= convertir_a_double(uno);
                double val2 = convertir_a_double(dos);
                return val1 >= val2;
            }else if(tipo1==4||tipo2==4){//comparacion de date
                Object val1= convertir_a_date(uno,izq);
                Object val2 = convertir_a_date(dos, izq);
                
                return Date.parse(val1.toString()) >= Date.parse(val2.toString());
                
            }else if(tipo1==1||tipo2==1){//conversion con tipo cadena
                String val1=convertir_a_string(uno,izq);
                String val2=convertir_a_string(dos,izq);
                return val1.length() >= val2.length();
            }else if(tipo1==3&&tipo2==3){//comparacion de boolean
                int val1= ((boolean)uno? 1 : 0);
                int val2= ((boolean)dos? 1 : 0);
                return val1>=val2;
            }else{
                agregarError("Semantico", "Error al evaluar >= los operandos", izq.linea, izq.columna);
               throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de >=  cerca: " + izq.linea);  
            }
        } catch (Exception e) {
             agregarError("Semantico", "Error al evaluar >= los operandos", izq.linea, izq.columna);
             throw new UnsupportedOperationException("Gravisimo error en  la evaluacion de >=  cerca: " + izq.linea); 
        }
    }
     
    
    //*****************metodos para retonrnnar los tipos***********/////////
    private int retornarTipo(Object a){//probar retornar tipo
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

    private String retornarTipoString(Object a){//probar retornar tipo
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
    
    private Object evaluarSUMA(nodo izq, nodo der) {
       Object uno = evaluar_expresion(izq);
       Object dos = evaluar_expresion(der);
       
       int valor1 = retornarTipo(uno);
       int valor2 = retornarTipo(dos);
       
       if(valor1==-1||valor2==-1){
          agregarError("Semantico", "Error al sumar los operandos", izq.linea, izq.columna);
          throw new UnsupportedOperationException("Gravisimo error en la suma de tipos de datos cerca: " + izq.linea); 
       }
       if(valor1==1||valor2==1){
           if(uno instanceof Date || dos instanceof Date){
               uno = convertirAString(uno,izq);
               dos = convertirAString(dos,izq);
           }
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
           agregarError("Semantico", "Error al sumar los operandos", izq.linea, izq.columna);
           throw new UnsupportedOperationException("Gravisimo error en la suma de tipos de datos cerca: " + izq.linea); 
       }
    }
    
    private Object evaluarRESTA(nodo izq, nodo der) {
       Object uno = evaluar_expresion(izq);
       Object dos = evaluar_expresion(der);
       
       int valor1 = retornarTipo(uno);
       int valor2 = retornarTipo(dos);
       
       if(valor1==-1||valor2==-1){
           agregarError("Semantico", "Error al restar los operandos", izq.linea, izq.columna);
          throw new UnsupportedOperationException("Gravisimo error en la resta de tipos de datos cerca: " + izq.linea); 
       }
        if (valor1 == 1 || valor2 == 1 || valor1 == 4 || valor2 == 4) {
            agregarError("Semantico", "Error al restar los operandos", izq.linea, izq.columna);
            throw new UnsupportedOperationException("Gravisimo error en la resta de tipos de datos cerca: " + izq.linea); 
        }
        if (valor1 == 2 || valor2 == 2) {
             if(valor1==3)
                   uno =((boolean)uno? 1 : 0)*1.0;
               if(valor2==3)
                   dos =((boolean)dos? 1 : 0)*1.0;
            return (double) uno - (double) dos;
        } else {
           agregarError("Semantico", "Error al restar los operandos", izq.linea, izq.columna);
            throw new UnsupportedOperationException("Gravisimo error en la restar de tipos de datos cerca: " + izq.linea); 
        }
    }
    
    private Object evaluarMULTIPLICAR(nodo izq, nodo der) {
       Object uno = evaluar_expresion(izq);
       Object dos = evaluar_expresion(der);
       
       int valor1 = retornarTipo(uno);
       int valor2 = retornarTipo(dos);
       
       if(valor1==-1||valor2==-1){
         agregarError("Semantico", "Error al multiplicar los operandos", izq.linea, izq.columna);
          throw new UnsupportedOperationException("Gravisimo error en la multiplicacion de tipos de datos cerca: " + izq.linea); 
       }
        if (valor1 == 1 || valor2 == 1 || valor1 == 4 || valor2 == 4) {
            agregarError("Semantico", "Error al multiplicar los operandos", izq.linea, izq.columna);
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
    
    private Object evaluarDIVIDIR(nodo izq, nodo der) {
       Object uno = evaluar_expresion(izq);
       Object dos = evaluar_expresion(der);
       
       int valor1 = retornarTipo(uno);
       int valor2 = retornarTipo(dos);
       
       if(valor1==-1||valor2==-1){
            agregarError("Semantico", "Error al dividir los operandos", izq.linea, izq.columna);
           throw new UnsupportedOperationException("Gravisimo error en la DIVIDR de tipos de datos cerca: " + izq.linea); 
       }
        if (valor1 == 1 || valor2 == 1 || valor1 == 4 || valor2 == 4) {
            agregarError("Semantico", "Error al dividir los operandos", izq.linea, izq.columna);
            throw new UnsupportedOperationException("Gravisimo error en la DIVIDR de tipos de datos cerca: " + izq.linea); 
        }
        if (valor1 == 2 || valor2 == 2) {
             if(valor1==3)
                   uno =((boolean)uno? 1 : 0)*1.0;
               if(valor2==3)
                   dos =((boolean)dos? 1 : 0)*1.0;
            return (double) uno / (double) dos;
        } else {
            agregarError("Semantico", "Error al dividir los operandos", izq.linea, izq.columna);
            throw new UnsupportedOperationException("Gravisimo error en la DIVIDR de tipos de datos cerca: " + izq.linea); 
        }
    }
    
    private Object evaluarPOTENCIAR(nodo izq, nodo der) {
       Object uno = evaluar_expresion(izq);
       Object dos = evaluar_expresion(der);
       
       int valor1 = retornarTipo(uno);
       int valor2 = retornarTipo(dos);
       
       if(valor1==-1||valor2==-1){
            agregarError("Semantico", "Error al potenciar los operandos", izq.linea, izq.columna);
           throw new UnsupportedOperationException("Not supported yet.");
       }
        if (valor1 == 1 || valor2 == 1 || valor1 == 4 || valor2 == 4) {
             agregarError("Semantico", "Error al potenciar los operandos", izq.linea, izq.columna);
            throw new UnsupportedOperationException("Not supported yet.");
        }
        if (valor1 == 2 || valor2 == 2) {
             if(valor1==3)
                   uno =((boolean)uno? 1 : 0)*1.0;
               if(valor2==3)
                   dos =((boolean)dos? 1 : 0)*1.0;
            return Math.pow((double) uno,  (double) dos);
        } else {
            agregarError("Semantico", "Error al potenciar los operandos", izq.linea, izq.columna);
            System.out.println("Gravisimo error en la potenciar de tipos de datos cerca: " + izq.linea);
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    
    private Object evaluarMOD(nodo izq, nodo der) {
       Object uno = evaluar_expresion(izq);
       Object dos = evaluar_expresion(der);
       
       int valor1 = retornarTipo(uno);
       int valor2 = retornarTipo(dos);
       
       if(valor1==-1||valor2==-1){
           System.out.println("Gravisimo error en ejecucioon cerca de la linea: "+izq.linea);
           agregarError("Semantico", "tipos incompatibles para modular", izq.linea, izq.columna);
          throw new UnsupportedOperationException("Gravisimo error en la MODULACION de tipos de datos cerca: " + izq.linea); 
       }
        if (valor1 == 1 || valor2 == 1 || valor1 == 4 || valor2 == 4) {
            agregarError("Semantico", "Error al operar la modulacion", izq.linea, izq.columna);
            System.out.println("Gravisimo error en la resta de tipos de datos cerca: " + izq.linea);
            throw new UnsupportedOperationException("Gravisimo error en la MODULACION de tipos de datos cerca: " + izq.linea); 
        }
        if (valor1 == 2 || valor2 == 2) {
            if (valor1 == 3) 
                uno = ((boolean) uno ? 1 : 0) * 1.0;
            if (valor2 == 3) 
                dos = ((boolean) dos ? 1 : 0) * 1.0;
            return (double) uno % (double) dos;
        } else if (valor1 == 3 && valor2 == 3) {
            uno = ((boolean) uno ? 1 : 0) * 1.0;
            dos = ((boolean) dos ? 1 : 0) * 1.0;
            return (double) uno % (double) dos;
        }
        else 
           throw new UnsupportedOperationException("Gravisimo error en la MODULACION con los tipos de datos cerca: " + izq.linea); 
        
    }
    
    //***************EVALUAR ID
    
    private Object evaluarID(String nombre) {
        variable var = getVariable(nombre);
        if(var!=null)
            return var.valor;
        return null;
    }

    private Object evaluarUNARIO(nodo raiz) {
    
        Object val = evaluar_expresion(raiz.hijos.get(0));
        int tipo =retornarTipo(val);
        
        if(tipo==2||tipo==3){
            return (double)val * -1;
        }else{
            agregarError("Semantico", "Error operar el unario por tipo incombatible", raiz.linea, raiz.columna);
           throw new UnsupportedOperationException("Error en el unario por el tipo!"); //To change body of generated methods, choose Tools | Templates. 
        }
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void ejecutarWHILE(nodo raiz) {
        try {
            aumentar_ambito();
            while ((boolean)evaluar_expresion(raiz.get(0))) {
                if(detener)
                    break;
                ejecutar(raiz.get(1));
            }
            disminuir_ambito();
            detener=false;
        } catch (Exception e) {
            agregarError("Semantico", "Error al evaluar la condicion del while", raiz.linea, raiz.columna);
            throw new UnsupportedOperationException("Error al evaluar la condicion del WHILE, linea: "+raiz.linea); //To change body of generated methods, choose Tools | Templates.
        }
    }

    private void ejecutarFOR(nodo raiz) {
        String id = raiz.get(0).valor;
        Object val = evaluar_expresion(raiz.get(0).get(0));
        guardarVariable(retornarTipoString(val),id , val);
        
        try {
            //ver donde tengo que hacer el aumento
            aumentar_ambito();
            while((boolean)evaluar_expresion(raiz.get(1))){
                if(detener)
                    break;
                ejecutar(raiz.get(3));
                ejecutarAUMENTOS_FOR(id, raiz.get(2).valor);
            }
            disminuir_ambito();
            detener=false;
        } catch (Exception e) {
            agregarError("Semantico", "Error al evaluar la condicion del for", raiz.linea, raiz.columna);
            throw new UnsupportedOperationException("Error al evaluar la condicion del FOR, linea: "+raiz.linea); 
        }
    }

    private void ejecutarCASE(nodo raiz) {
        Object res = evaluar_expresion(raiz.get(0));
        
        boolean dale=false;
        try {
            for (nodo caso : raiz.get(1).hijos) {
                if (!dale) {
                    Object val = evaluar_expresion(caso.get(0));
                    if (val.equals(res))
                        dale =true;
                } 
                    //preguntar si ya vino detener
                    if (detener) 
                        break;
                    if(dale)
                        ejecutar(caso.get(1));
                
            }
            if(!dale&&raiz.hijos.size()==3)
                ejecutar(raiz.get(2).get(0));//ejecutamos el default
            detener=false;
        } catch (Exception e) {
            agregarError("Semantico", "Error la ejecucion del case", raiz.linea, raiz.columna);
            throw new UnsupportedOperationException("Error en la ejecucion del Case en la linea: "+raiz.linea); //To change body of generated methods, choose Tools | Templates.
        }
    }

    private void ejecutaIF(nodo raiz) {
        try {
            Object res = evaluar_expresion(raiz.get(0));
            if((boolean)res){
                ejecutar(raiz.get(1));
            }else{
                if(raiz.hijos.size()==3)//preguntar si trae las sentencias del else
                    ejecutar(raiz.get(2).get(0));
            }
        } catch (Exception e) {
            agregarError("Semantico", "Error al evaluar la condicion del if", raiz.linea, raiz.columna);
            throw new UnsupportedOperationException("Error al evaluar la condicion del IF, linea: "+raiz.linea); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    private void ejecutarAUMENTOS_FOR(String id, String tipo){
        variable var = getVariable(id);
        double actual = (double)var.valor;
        if("++".equals(tipo))
        {
            var.valor=actual+1;
        }else
            var.valor = actual -1;
    }

    private void ejecutarIMPRIMIR(nodo raiz) {
        try {
            consola.append(String.valueOf(evaluar_expresion(raiz.get(0)) +"\n"));
            control.consola=consola.toString();
        } catch (Exception e) {
            agregarError("Semantico", "Error en imprimir ", raiz.linea, raiz.columna);
            //throw new UnsupportedOperationException("Error en IMPRIMIR, linea: "+raiz.linea); //To change body of generated methods, choose Tools | Templates.
        }
    }

    private void ejecutarMESAJE(nodo raiz) {
        try {
            String men = String.valueOf(evaluar_expresion(raiz.get(0)));
            JOptionPane.showMessageDialog(null,men);
            
        } catch (Exception e) {
            agregarError("Semantico", "Error al mostrar el mensaje", raiz.linea, raiz.columna);
            //throw new UnsupportedOperationException("Error en mostrar MENSAJE, linea: "+raiz.linea); //To change body of generated methods, choose Tools | Templates.
        }
        
    }

    private void ejecutarASIGNAR(nodo raiz) {
        variable var = getVariable(raiz.valor);
        
        if (var != null) {
            Object res = evaluar_expresion(raiz.get(0));

            if (res != null) {
                var.valor = res;
                var.tipo = retornarTipoString(res);
            }
        }
    }

    private void ejecutarDECLARAR_ASIG(nodo raiz) {
        String variable = raiz.valor.toLowerCase();
        
        if(lista_actual.containsKey(variable)){
            System.out.println("Ya existe la variable: 0"+raiz.valor+", en el ambito actual");
            agregarError("Semantico", "Ya existe la variable en el ambito acdtual: "+raiz.valor, raiz.linea, raiz.columna);
            return;
        }
        
        Object res = evaluar_expresion(raiz.get(0));
        
        if(res!=null){
            variable var = new variable(retornarTipoString(res), variable, res);
            lista_actual.put(variable, var);
        }
    }

    private void ejecutarRETORNAR(nodo raiz) {
        if(raiz.hijos.size()>0){
            retorno = evaluar_expresion(raiz.get(0));
        }
        bandera_retorno=true;
    }

    private void guardarFUNCION(nodo raiz) {
        if(!verificar_funcion(raiz)){
            metodo nuevo;

            if (raiz.hijos.size() == 2) {
                nuevo= new metodo(raiz.valor, raiz.get(1));
                for (nodo param : raiz.get(0).hijos) 
                    nuevo.parametros.add(param.nombre);
                
            }else//sino trae parametros el metodo
                nuevo= new metodo(raiz.valor, raiz.get(0));
            metodos.addLast(nuevo);
        }else{
            agregarError("Semantico", "El metodo ya existe", raiz.linea, raiz.columna);
            System.out.println("El metodo ya existe: "+raiz.valor);
        }
    }
    
    
    private boolean verificar_funcion(nodo raiz){
        int noParam=0;
        if(raiz.hijos.size()==2)
            noParam=raiz.get(0).hijos.size();
                    
        for (metodo met:metodos){
            if(met.nombre.equals(raiz.valor)){
                if(noParam==met.parametros.size())
                    return true;
            }
        }
        return false;//retorno false por que no existe el metodo
    }

    private Object ejecutarCALLFUN(nodo raiz) {
        metodo met = getFuncion(raiz);
        if(met!=null){
            aumentar_ambito();
            
            if(met.parametros.size()>0)
                llenar_parametros(met, raiz.get(0));
            
            for(nodo a: met.sentencias.hijos){
                if(bandera_retorno)
                    break;
                ejecutar(a);
            }
            bandera_retorno=false;
            disminuir_ambito();
            return retorno;
        }else{
            agregarError("Semantico", "No existe el metodo: "+raiz.valor, raiz.linea, raiz.columna);
            throw new UnsupportedOperationException("no existe metodo: "+raiz.valor); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    private metodo getFuncion(nodo raiz){
        int no_parametros =0;
        if(raiz.hijos.size()==1)
            no_parametros=raiz.get(0).hijos.size();
        
        for(metodo met : metodos){
            if(met.nombre.equals(raiz.valor))
                if(met.parametros.size()==no_parametros)
                    return met;
        }
        return null;
    }

    private void llenar_parametros(metodo met,nodo raiz){
        int cont;
        
        for(cont=0;cont<raiz.hijos.size();cont++){
            Object res = evaluar_expresion(raiz.get(cont));
            //ver que no sea nula
            guardarVariable(retornarTipoString(res), met.parametros.get(cont), res);
        }
    }

    private Object crearVECTOR(nodo raiz) {
        LinkedList<Object> valores = new LinkedList<>();
        for(nodo a:raiz.get(0).hijos){
            Object res = evaluar_expresion(a);
            if(res==null){
                agregarError("Semantico", "La evaluacion de los valores del vector dieron null", raiz.linea, raiz.columna);
                throw new UnsupportedOperationException("Gravisimo error evaluando vector: " + raiz.linea); 
            }
            valores.add(res);
        }
        return valores;
    }

    
    private void agregarError(String tipo,String men,int line,int colum){
      control.agregarError(new error(tipo, men, line, colum));
    }

    private Object accesoVector(nodo raiz) {
        variable var = getVariable(raiz.valor);
        
        if(var!=null){
            if(retornarTipo(var.valor)==5){
                LinkedList<Object> lista = (LinkedList<Object>)var.valor;
                Object pos = evaluar_expresion(raiz.get(0));
                try {
                    Double valor = (double)pos;
                    int posicion = (int)valor.doubleValue();
                    return lista.get(posicion);
                } catch (Exception e) {
                    agregarError("Semantico", "no se encuentra la variable: "+raiz.valor, raiz.linea, raiz.columna);
                    throw new UnsupportedOperationException("Gravisimo error accediendo al vector: " + raiz.linea); 
                }
            }
        }
        agregarError("Semantico", "no se encuentra la variable: "+raiz.valor, raiz.linea, raiz.columna);
        throw new UnsupportedOperationException("Gravisimo error accediendo al vector: " + raiz.linea); 
    }

    private void declararVECTOR(nodo raiz) {
        String variable = raiz.valor.toLowerCase();
        
        if(lista_actual.containsKey(variable)){
            agregarError("Semantico", "ya existe la variable en el ambito actual: "+raiz.valor, raiz.linea, raiz.columna);
            System.out.println("Ya existe la variable: 0"+raiz.valor+", en el ambito actual");
            return;
        }
        
        Object res = evaluar_expresion(raiz.get(0));
        
        Double val = (double)res;
        int cont = (int)val.doubleValue();
        
        LinkedList<Object> lista = new LinkedList<>();
        for(; cont>0;cont--)
            lista.add(new Object());
        
        if(res!=null){
            variable var = new variable("Vector", variable, lista);
            lista_actual.put(variable, var);
        }else
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void asignarVECTOR(nodo raiz) {
        variable var = getVariable(raiz.valor);
        if(var!=null&&"Vector".equals(var.tipo)){
            Object pos = evaluar_expresion(raiz.get(0));
            Object res =evaluar_expresion(raiz.get(1));
            Double val = (double) pos;
            int cont = (int) val.doubleValue();
            
            try {
                LinkedList<Object> lista = (LinkedList<Object>)var.valor;
                lista.set(cont, res);
            } catch (Exception e) {
                agregarError("Semantico", "Error al asingar el valor en vector", raiz.linea, raiz.columna);
                throw new UnsupportedOperationException("Error al asignar el valor en Vector: "+raiz.valor); //To change body of generated methods, choose Tools | Templates.
            }
        }else
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Object convertirATEXTO(nodo raiz) {
        Object var = evaluar_expresion(raiz.get(0));
        if(var!=null&&retornarTipo(var)==5){
            try {
                LinkedList<Object> lista = (LinkedList<Object>)var;
                String ret="";
                ret = lista.stream().map((a) -> a.toString()).reduce(ret, String::concat);//hay que ver si funca
                return ret;
            } catch (Exception e) {
                agregarError("Semantico", "Error al convertir a texto el valor del vector", raiz.linea, raiz.columna);
                throw new UnsupportedOperationException("Error al convertir a texto el valor en Vector: "+raiz.linea); //To change body of generated methods, choose Tools | Templates.
            }
        }else
            throw new UnsupportedOperationException("Error al convertir a texto el valor en Vector: "+raiz.linea); //To change body of generated methods, choose Tools | Templates.
    }

    private Object contarVECTOR(nodo raiz) {
        Object var = evaluar_expresion(raiz.get(0));
        if(var!=null&&retornarTipo(var)==5){
            try {
                LinkedList<Object> lista = (LinkedList<Object>)var;
                double tam = lista.size()*1.0;
               // return lista.size() *1.0;
               return tam;
            } catch (Exception e) {
                agregarError("Semantico", "Error al contar vector", raiz.linea, raiz.columna);
                throw new UnsupportedOperationException("Error al contar el valor en Vector: "+raiz.linea); //To change body of generated methods, choose Tools | Templates.
            }
        }else{
        agregarError("Semantico", "Error al contar vector", raiz.linea, raiz.columna);
        throw new UnsupportedOperationException("Error al contar  en Vector: "+raiz.linea);
        }
            
    }
    
    
    private metodo getFuncionSoloNombre(String raiz){
        int no_parametros =0;
           
        for(metodo met : metodos){
            if(met.nombre.equals(raiz))
                if(met.parametros.size()==no_parametros)
                    return met;
        }
        return null;
    }
    
    public Object ejecutar_desde_afuera(String id){
        metodo met = getFuncionSoloNombre(id);
        if(met!=null){
            aumentar_ambito();
            
           /* if(met.parametros.size()>0)
                llenar_parametros(met, raiz.get(0));*/
            
            for(nodo a: met.sentencias.hijos){
                if(bandera_retorno)
                    break;
                ejecutar(a);
            }
            bandera_retorno=false;
            disminuir_ambito();
            return retorno;
        }else{
            agregarError("Semantico", "no existe el metodo "+id, 0,0);
            throw new UnsupportedOperationException("no existe metodo: "+id); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    
    
    /******* metodo orginal de relacionales****************
     * private Object evaluarMENOR(nodo izq, nodo der) {
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
    */

    private double convertir_a_double(Object uno) {
        switch(retornarTipo(uno)){
            case 1:
                return convertir_cadena_double(uno.toString());
            case 2: 
                return Double.parseDouble(uno.toString());
            case 3:
                return ((boolean)uno? 1 : 0)*1.0;
            default:
                agregarError("Semantico", "Imposible operarar relacional ", 0, 0);
                throw new UnsupportedOperationException("Gravisimo error en  la evaluacion relacional: "); 
        }
    }
    
    private double convertir_cadena_double(String cadena){
        return cadena.length()*1.0;
    }

    private String convertir_a_string(Object uno,nodo raiz) {
         switch(retornarTipo(uno)){
            case 1:
                return uno.toString();
            case 2: 
            case 3:
            case 4:
                return String.valueOf(uno);
            default:
                agregarError("Semantico", "Imposible operarar relacional ", raiz.linea, raiz.columna);
                throw new UnsupportedOperationException("Gravisimo error en  la evaluacion relacional: "+raiz.linea+","+raiz.columna); 
        }
    }

    
    //convierte a Date string y tipo Date
    private Object convertir_a_date(Object fecha,nodo raiz) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        int caso = retornarTipo(fecha);
        switch (caso) {
            case 1:
                try {
                    return formato.parse(fecha.toString());
                } catch (ParseException ex) {
                    System.out.println(ex);
                    formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    try {
                        return formato.parse(fecha.toString());
                    } catch (Exception e) {
                        agregarError("Semantico", "Imposible convertir a date", raiz.linea, raiz.columna);
                        throw new UnsupportedOperationException("Gravisimo error en  conversion a fecha: " + raiz.linea + "," + raiz.columna);
                    }
                }
            case 4:
                return (Date) fecha;
            default:
                agregarError("Semantico", "Imposible convertir a date", raiz.linea, raiz.columna);
                throw new UnsupportedOperationException("Gravisimo error en  conversion a fecha: " + raiz.linea + "," + raiz.columna);
        }
    }

    //metodo para convertir a  fechas
    private Object retornarDate(nodo raiz) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formato.parse(raiz.valor);
        } catch (ParseException ex) {
            System.out.println(ex);
            formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            try {
                return formato.parse(raiz.valor);
            } catch (Exception e) {
                System.out.println("No es de tipo fecha");
                agregarError("Semantico", "Error al convertir la fecha", raiz.linea, raiz.columna);
                throw new UnsupportedOperationException("Gravisimo error el convertir a fecha: " + raiz.linea + "," + raiz.columna);
            }
        }

    }

    //metodo para ver si es tipo date lo convierte a dd/mm/yyyy
    private Object convertirAString(Object uno,nodo raiz) {
        
        if(uno instanceof Date){
            Date val1 = (Date)uno;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return formato.format(val1);
            } catch (Exception e) {
                 formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                 try {
                    return formato.format(val1);
                } catch (Exception ex) {
                    agregarError("Semantico", "Error al convertir la fecha", raiz.linea, raiz.columna);
                    throw new UnsupportedOperationException("Gravisimo error el convertir a fecha: " + raiz.linea + "," + raiz.columna);
                }
                 
            }
        }
        
        return uno;
    }

    private Object aumento(nodo raiz) {
        if(raiz.nombre.equals("ID")){
            variable var = getVariable(raiz.valor);
            if (var != null) {
                try {
                    Double valor = convertir_a_double(var.valor);
                    var.valor = valor + 1;
                    return var.valor;
                } catch (Exception e) {
                     agregarError("Semantico", "Error al realizar el aumento de la variable tipo incompatible", raiz.linea, raiz.columna);
                    throw new UnsupportedOperationException("Error al realizar el aumento de la variable tipo incompatible de: " + raiz.linea + "," + raiz.columna);
                }
            }else{
                agregarError("Semantico", "No existe la variable para realizar el aumento", raiz.linea, raiz.columna);
               throw new UnsupportedOperationException("No existe la variable para realizar el aumento: " + raiz.linea + "," + raiz.columna); 
            }
        }else{
            Object res = evaluar_expresion(raiz);
            try {
                Double val = convertir_a_double(res);
                return val+1;
            } catch (Exception e) {
                agregarError("Semantico", "Error al realizar el aumento", raiz.linea, raiz.columna);
                throw new UnsupportedOperationException("Error al realizar el aumento cerca de: " + raiz.linea + "," + raiz.columna);
            }
        }
    }

    private Object decremento(nodo raiz) {
        if(raiz.nombre.equals("ID")){
            variable var = getVariable(raiz.valor);
            if (var != null) {
                try {
                    Double valor = convertir_a_double(var.valor);
                    var.valor = valor - 1;
                    return var.valor;
                } catch (Exception e) {
                    throw new UnsupportedOperationException("Error al realizar el aumento de la variable tipo incompatible de: " + raiz.linea + "," + raiz.columna);
                }
            }else{
               throw new UnsupportedOperationException("No existe la variable para realizar el aumento: " + raiz.linea + "," + raiz.columna); 
            }
        }else{
            Object res = evaluar_expresion(raiz);
            try {
                Double val = convertir_a_double(res);
                return val- 1;
            } catch (Exception e) {
                throw new UnsupportedOperationException("Error al realizar el aumento cerca de: " + raiz.linea + "," + raiz.columna);
            }
        }
    }

    
   
    
}
