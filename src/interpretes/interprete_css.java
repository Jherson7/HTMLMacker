package interpretes;

import Arbol.evaluarExpresion;
import Arbol.nodo;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.JOptionPane;
import paginas.estilos;

/**
 *
 * @author Jherson
 */
public final class interprete_css {
    
    private estilos actual;
    private HashMap<String,estilos> lista_estilos;
    
    public interprete_css() {
        
    }
    
    public interprete_css(nodo raiz,HashMap<String,estilos>  lista){
        this.lista_estilos=lista;
        inicio(raiz);
    }

    public void inicio(nodo raiz){
//        System.out.println(raiz.nombre);  
        switch(raiz.nombre){
            case "LISTA_CSS":
                for(nodo a:raiz.hijos)
                    inicio(a);
                break;
            case "IDEN":
                guardarIDEN(raiz);
                break;
            case "GRUPO":
                guardarGrupo(raiz);
                break;
            case "ELEMENTO":
                guardarElemento(raiz);
                break;
            
        }
    }

    

    private void guardarGrupo(nodo raiz) {
        actual = new estilos("grupo_"+raiz.valor.toLowerCase());
        recorrer(raiz.get(0));
        agregar_estilo(actual);
    }

    private void guardarIDEN(nodo raiz) {
        actual = new estilos("iden_"+raiz.valor.toLowerCase());
        recorrer(raiz.get(0));
        agregar_estilo(actual);
    }

    private void guardarElemento(nodo raiz) {
        actual = new estilos("elemento_"+raiz.valor.toLowerCase());
        recorrer(raiz.get(0));
        agregar_estilo(actual);
    }
    
    private void recorrer(nodo raiz){
        switch(raiz.nombre){
         case"SENTENCIAS"   :
               for(nodo a:raiz.hijos)
                    recorrer(a);
                break;
         case "ALINEADO":
             ejecutarAlienado(raiz);
             break;
         case "FORMATO":
             guardar_formato(raiz);
             break;
         case"LETRA":
             actual.tipo_letra= evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();
             break;
         case "TAMTEX":
             actual.tam_texto=(double)evaluarExpresion.evaluar_expresion(raiz.get(0));
             break;
         case "FONDO":
             String fondo = evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();
             actual.fondo_elemento= getColor(fondo);
             break;
         case "VISIBILIDAD":
             actual.visiblidad=(boolean)evaluarExpresion.evaluar_expresion(raiz.get(0));
             break;
         case "BORDE":
             guardarBorde(raiz);
             break;
         case "OPACO":
             actual.opaco=(boolean)evaluarExpresion.evaluar_expresion(raiz.get(0));
             break;
         case "COLOR":
             String color = evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();
             actual.color_texto= getColor(color);
             break;
         case "TEXTO":
             String texto =  evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();
             actual.texto=texto;
             break;
         case "TAMELEMENTO":
             try {
                 double x= (double)evaluarExpresion.evaluar_expresion(raiz.get(0));
                 double y= (double)evaluarExpresion.evaluar_expresion(raiz.get(1));
                 int xx= (int)x;
                 int yy=(int)y;
                 actual.x=xx;
                 actual.y=yy;
             } catch (Exception e) {
                 System.out.println(e.getMessage()+" >> error en tamanio de elemento");
             }
             break;
         case "IDELEMENT":
             break;

        }
    }

    private void ejecutarAlienado(nodo raiz) {
        
        switch (raiz.valor.toLowerCase()) {
            case "izquierda":
                actual.izquierda = true;
                break;
            case "derecha":
                actual.derecha = true;
                break;
            case "centrado":
                actual.centrado = true;
                break;
            case "justificado":
                actual.justificado = true;
                break;
        }
    }

    private void guardarBorde(nodo raiz) {
        try {
            actual.tam_borde = (double)evaluarExpresion.evaluar_expresion(raiz.get(0));
            String color = evaluarExpresion.evaluar_expresion(raiz.get(1)).toString();
            actual.color_borde = getColor(color);
            actual.curva=(boolean)evaluarExpresion.evaluar_expresion(raiz.get(2));;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el borde del estilo");
        }
    }
    
    private Color getColor(String color){
        
        try {
            return Color.decode(color);
        } catch (Exception e) {
            switch (color.toLowerCase()) {
                case "black":
                    return Color.black;
                case "blue":
                    return Color.blue;
                case "cyan":
                    return Color.cyan;
                case "dark_gray":
                    return Color.darkGray;
                case "green":
                    return Color.green;
                case "light_gray":
                    return Color.lightGray;
                case "magenta":
                    return Color.magenta;
                case "orange":
                    return Color.orange;
                case "pink":
                    return Color.pink;
                case "yellow":
                    return Color.yellow;
                case "white":
                    return Color.white;
                case "red":
                    return Color.red;
            }
            return null;
        }
        
    }
    
    public void guardar_formato(nodo raiz){
         for(nodo r:raiz.hijos){
            switch(r.nombre.toLowerCase()){
                case "negrita":
                case "negrilla":
                    actual.negrita=true;
                break;
                case "cursiva":
                    actual.cursiva=true;
                    break;
                case "capital":
                    actual.capital=true;
                    break;
                case "mayuscula":
                    actual.mayuscula=true;
                    break;
                case "minuscula":
                    actual.miniscula=true;
                    break;
            }
        }
    }
    
    
    public  void agregar_estilo(estilos nuevo){
        if(lista_estilos.containsKey(nuevo.id)){
            agregar_mas_cc(lista_estilos.get(nuevo.id), nuevo);
        }
        else
            lista_estilos.put(nuevo.id, nuevo);
    }
    
    
    private  void agregar_mas_cc(estilos nuevo, estilos antiguo){
        if(nuevo.capital)
            antiguo.capital=nuevo.capital;
        if(nuevo.centrado)
            antiguo.centrado=nuevo.centrado;
        if(nuevo.color_borde!=null)
            antiguo.color_borde=nuevo.color_borde;
        if(nuevo.color_texto!=null)
            antiguo.color_texto=nuevo.color_texto;
        if(nuevo.cursiva)
            antiguo.cursiva=nuevo.cursiva;
        if(nuevo.curva)
            antiguo.curva=nuevo.curva;
        if(nuevo.derecha)
            antiguo.derecha=nuevo.derecha;
        if(nuevo.fondo_elemento!=null)
            antiguo.fondo_elemento=nuevo.fondo_elemento;
        if(nuevo.izquierda)
            antiguo.izquierda=nuevo.izquierda;
        if(nuevo.justificado)
            antiguo.justificado=nuevo.justificado;
        if(nuevo.mayuscula)
            antiguo.mayuscula=nuevo.mayuscula;
        if(nuevo.miniscula)
            antiguo.miniscula=nuevo.miniscula;
        if(nuevo.negrita)
            antiguo.negrita=nuevo.negrita;
        if(nuevo.opaco)
            antiguo.opaco=nuevo.opaco;
        if(nuevo.tam_borde>0)
            antiguo.tam_borde=nuevo.tam_borde;
        if(nuevo.tam_elemento!=null)
            antiguo.tam_elemento=nuevo.tam_elemento;
        if(nuevo.tam_letra>0)
            antiguo.tam_letra=nuevo.tam_letra;
        if(nuevo.tam_texto>0)
            antiguo.tam_texto=nuevo.tam_texto;
        if(!"".equals(nuevo.texto))
            antiguo.texto=nuevo.texto;
        if(!"".equals(nuevo.tipo_letra))
            antiguo.tipo_letra=nuevo.tipo_letra;
        antiguo.utilizado=nuevo.utilizado;
        if(nuevo.visiblidad)
            antiguo.visiblidad=nuevo.visiblidad;
    }
       
    
    
}
