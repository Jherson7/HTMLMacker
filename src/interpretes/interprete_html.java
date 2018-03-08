package interpretes;

import Arbol.control;
import Arbol.evaluarExpresion;
import Arbol.nodo;
import gramatica_css.Lexico_css;
import gramatica_css.Sintactico_css;
import gramatica_js.Lexico_js;
import gramatica_js.Sintactico_js;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import paginas.ComboBox;
import paginas.Parrafo;
import paginas.ScrollPane;
import paginas.Spinner;
import paginas.TablaSazo;
import paginas.boton;
import paginas.enlace;
import paginas.estilos;
import paginas.imagen;
import paginas.opcion;
import paginas.textArea;
import paginas.textEdit;
import vistas.Html;


/**
 *
 * @author Jherson
 */
public class interprete_html {

    ScrollPane html;
    HashMap<String, estilos>lista_css;
    //probar a que cada pestania tenga su propio inteprete
    public interprete_js ejecutor_js;
    LinkedList<LinkedList<JComponent> >tabla_actual;
    LinkedList<JComponent> apuntador_tabla;
    LinkedList<JComponent> cabecera_tabla;
    estilos actual;
    
    public interprete_html(ScrollPane panel,HashMap<String,estilos>lista_) {
        this.html=panel;
        this.lista_css=lista_;
    }

    public interprete_html(ScrollPane panel,HashMap<String,estilos>lista_,interprete_js interprete) {
        this.html=panel;
        this.lista_css=lista_;
        this.ejecutor_js=interprete;
    }
    
    public void inicio(nodo raiz){
        System.out.println(raiz.nombre);  
        switch(raiz.nombre){
            case "HTML":
                for(nodo a:raiz.hijos)
                    inicio(a);
                break;
            case "LISTA_HEAD":
                ejecutar_head(raiz);
                break;
            case "CUERPO":
                ejecutar_cuerpo(raiz);
                break;
            
        }
    }

    private void ejecutar_head(nodo raiz) {
        switch(raiz.nombre){
            case "LISTA_HEAD":
                for(nodo r: raiz.hijos)
                    ejecutar_head(r);
                break;
            case "CONTENIDO_HEAD":
                for(nodo r: raiz.hijos)
                    ejecutar_head(r);
                break;
            case "CSS":
                intepretarCSS(raiz);
                break;
            case "TITULO":
                Html.cambiarTitulo(raiz.valor.trim());
                break;
            case "CJS":
                interpretarCJS(raiz);
                break;
                
                        
                
        }
    }

    private void ejecutar_cuerpo(nodo raiz) {
        switch(raiz.nombre){
            case "CUERPO":
                 for(nodo a:raiz.hijos)
                    ejecutar_cuerpo(a);
                 break;
            case "SENTENCIAS":
                 for(nodo a:raiz.hijos)
                    ejecutar_cuerpo(a);
                break;
            case "PANEL":
                ejecutar_panel(raiz);
                break;
            case "IMAGEN":
                ejecutar_IMAGEN(raiz);
                break;
            case "CAJA_TEXTO":
                ejecutar_CAJA_TEXTO(raiz);
                break;
            case "BOTON":
                ejecutar_BOTON(raiz);
                break;
            case "ENLANCE":
                ejecutar_ENLANCE(raiz);
                break;
            case "TEXTO_AREA":
                ejecutar_TEXTO_AREA(raiz);
                break;
            case "CAJA":
                ejecutar_CAJA(raiz);
                break;
            case "SPINNER":
                ejecutar_SPINNER(raiz);
                break;
            case "SALTO":
                ejecutar_SALTO(raiz);
                break;
            case "TABLA":
                ejecutar_TABLA(raiz);
                break;
            case "TEXTO":
                ejecutar_TEXTO(raiz);
                break;
        }
    }

    private void ejecutar_panel(nodo raiz) {
        PanelSazo panel = new PanelSazo();
        actual= panel.estilo;
        if(raiz.get(0).hijos.size()>0)
            agregar_css(raiz.get(0).get(0),panel);
        
        
        this.html.cuerpo_html.agregarPanel(panel);
        //ejecutar sus sentencias
        ejecutar_cuerpo(raiz.get(1));
        //disminuir ambito
        this.html.cuerpo_html.disminuir_panel();
        
    }

    private void ejecutar_TABLA(nodo raiz) {
        
        tabla_actual = new LinkedList<>();
        cabecera_tabla= new LinkedList<>();
        llenar_contenido(raiz.get(1));
        tabla_actual.addFirst(cabecera_tabla);
        
        TablaSazo tabla = new TablaSazo(tabla_actual);
        this.html.cuerpo_html.agregarTabla(tabla,raiz);
    }

    private void llenar_contenido(nodo raiz){
        switch(raiz.nombre){
            case "LISTA_FILAS":
                for(nodo r: raiz.hijos)
                    llenar_contenido(r);
                break;
            case "FILA":
                 tabla_actual.addLast(new LinkedList<>());
                 apuntador_tabla=tabla_actual.getLast();
                 
                 for(nodo r: raiz.get(1).hijos){
                    JComponent aux = elemento_tabla(r);
                    if(aux!=null)
                        apuntador_tabla.add(aux);
                 }
        }
    }
    
    private JComponent elemento_tabla(nodo raiz){
        switch(raiz.nombre){
            
            case "CB":
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JLabel x = new JLabel(raiz.get(1).valor);
                x.setFont(new java.awt.Font("Arial", 2, 16));
                panel.add(x);
                panel.setBackground(Color.LIGHT_GRAY);
                
                //cabecera_tabla.add(new JLabel(raiz.get(1).valor));
                cabecera_tabla.add(panel);
                return null;
            case "CT":
                switch (raiz.get(1).nombre) {
                    case "BOTON":
                        raiz=raiz.get(1);
                        boton nuevo = new boton(raiz.valor);
                        actual = nuevo.estilo;
                        if (raiz.get(0).hijos.size() > 0) {
                            agregar_css(raiz.get(0).get(0), nuevo);
                        }
                        return nuevo;
                    case "IMAGEN":
                        imagen  img= new imagen(raiz.valor);
                        actual = img.estilo;
                        if (raiz.get(0).hijos.size() > 0) {
                            agregar_css(raiz.get(0).get(0), img);
                        }
                        return img;
                    default:
                        return new JLabel(raiz.get(1).valor); 
                }
        }
        return null;
    }
    
    private void ejecutar_SALTO(nodo raiz) {
        JPanel nuevo = new JPanel();
        nuevo.setBackground(new Color(0, 0, 0,0));
        //nuevo.setBackground(Color.black);
        nuevo.setPreferredSize(new Dimension(900, 20));
        //nuevo.setPreferredSize(new Dimension(html.getPreferredSize().width, 20));
        this.html.cuerpo_html.agregar_elemento(nuevo);
    }

    private void ejecutar_SPINNER(nodo raiz) {
        Spinner nuevo = new Spinner();
         
        try {
            String valor = evaluarExpresion.evaluar_expresion(raiz.get(1)).toString();
            int val= (int)Double.parseDouble(valor);
            nuevo.setValue(val);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        this.html.elementos.addLast(nuevo);
        this.html.cuerpo_html.agregarSpinner(nuevo);
    }

    private void ejecutar_IMAGEN(nodo raiz) {
        imagen  nuevo= new imagen(raiz.valor);
        this.html.elementos.addLast(nuevo);
        this.html.cuerpo_html.agregarImagen(nuevo);
    }

    private void ejecutar_CAJA_TEXTO(nodo raiz) {//textEdit
        textEdit nuevo = new textEdit(raiz.valor);
        actual= nuevo.estilo;
        if(raiz.get(0).hijos.size()>0)
            agregar_css(raiz.get(0).get(0),nuevo);
        
        this.html.elementos.addLast(nuevo);
        this.html.cuerpo_html.agregarTextBox(nuevo);
    }

    private void ejecutar_BOTON(nodo raiz) {
        boton nuevo = new boton(raiz.valor);
        actual= nuevo.estilo;
        if(raiz.get(0).hijos.size()>0)
            agregar_css(raiz.get(0).get(0),nuevo);
        
        this.html.elementos.addLast(nuevo);
        this.html.cuerpo_html.agregarBoton(nuevo);
    }

    private void ejecutar_ENLANCE(nodo raiz) {
        enlace nuevo = new enlace(raiz.valor);
        actual= nuevo.estilo;
        if(raiz.get(0).hijos.size()>0)
            agregar_css(raiz.get(0).get(0),nuevo);
        
        this.html.elementos.addLast(nuevo);
        this.html.cuerpo_html.agregarEnlance(nuevo);
    }

    private void ejecutar_TEXTO_AREA(nodo raiz) {
        textArea nuevo = new textArea(raiz.valor);
        
        actual = nuevo.estilo;
        if(raiz.get(0).hijos.size()>0)
            agregar_css(raiz.get(0).get(0),null);
        
        this.html.elementos.addLast(nuevo);
        this.html.cuerpo_html.agregarTextArea(nuevo);
        
    }

    private void ejecutar_CAJA(nodo raiz) {
        LinkedList<opcion> lista= getValoresCaja(raiz.get(1));
        ComboBox nuevo = new ComboBox(lista);
        
        actual=nuevo.estilo;
        
        if(raiz.get(0).hijos.size()>0)
            agregar_css(raiz.get(0),null);
        
        
        this.html.elementos.addLast(nuevo);
        this.html.cuerpo_html.agregarCaja(nuevo);
    }

    private void ejecutar_TEXTO(nodo raiz) {/////////////////////////////ahorita lo cambio
        Parrafo nuevo = new Parrafo(raiz.valor.trim());
        
        actual=nuevo.estilo;
        actual.texto=nuevo.getText();
        
        if(raiz.get(0).hijos.size()>0)
            agregar_css(raiz.get(0),null);
        
        String texto =actual.texto;
        if(actual.mayuscula)
            actual.texto=texto.toUpperCase();
        if(actual.miniscula)
            actual.texto=texto.toLowerCase();
        if(actual.capital)
            actual.texto=convertir_a_capital(texto);
        nuevo.setText(actual.texto);
        this.html.elementos.addLast(nuevo);
        this.html.cuerpo_html.agregarParrafo(nuevo);
        
    }
    
    public LinkedList<opcion> getValoresCaja(nodo raiz){
        LinkedList<opcion> lista = new LinkedList<>();
        for(nodo r: raiz.hijos)
            lista.add(new opcion(r.valor, r.valor));
        return lista;
    
    }

    private String convertir_a_capital(String cadena){
        char[] caracteres = cadena.toCharArray();
        caracteres[0] = Character.toUpperCase(caracteres[0]);
        for (int i = 0; i < cadena.length() - 2; i++) // Es 'palabra'
            if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') // Reemplazamos
                caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
        return new String(caracteres);
    }
    
    private static String retornarContenidoRuta(String ruta){
        String linea, contenido= "";
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(ruta));
            //leeendo linea a linea
            while ((linea = bufferreader.readLine())!=null)
            {
                contenido += linea +"\n";
            }
            System.out.println(contenido);
            bufferreader.close();
        } catch (IOException ex) {
            Logger.getLogger(control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contenido;
    }

    private void intepretarCSS(nodo raiz) {
        String ruta = raiz.get(0).get(0).valor;
        String cont = retornarContenidoRuta(ruta);
        analizarCSS(cont);
    }
    
    //interprete de css
    private void analizarCSS(String texto){
        Reader reader = new StringReader(texto);
        Lexico_css analizador_lexico =  new Lexico_css(reader);
        Sintactico_css analizador_sintactico = new Sintactico_css(analizador_lexico);
        
        try 
        {            
            analizador_sintactico.parse();
            nodo raiz = analizador_sintactico.raiz;
//            Graficador a = new Graficador(); con esto grafico
//            a.graficar(raiz);
            html.codigo_css+=texto;
            interprete_css es = new interprete_css(raiz,lista_css);
        } 
        catch (Exception ex) 
        {
            System.out.println("ERROR al analizar el archivo CCSS: "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR al analizar el archivo CCSS: "+ex.getMessage(),"COMPILADORES2",2);
        }        
        
    }

    public void ejecutarFuncion(String id){
        ejecutor_js.ejecutar_desde_afuera(id);
    }

    private void interpretarCJS(nodo raiz) {
        try {
            String ruta = evaluarExpresion.evaluar_expresion(raiz.get(0).get(0)).toString();
            String cont = retornarContenidoRuta(ruta);
            analizarCJS(cont) ; 
        } catch (Exception ex) {
            System.out.println("ERROR al analizar el archivo JavaScript: "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR al analizar el archivo CCSS: "+ex.getMessage(),"COMPILADORES2",2);
        }
    }
    
    public void analizarCJS(String texto){
        Reader reader = new StringReader(texto);
        Lexico_js analizador_lexico =  new Lexico_js(reader);
        Sintactico_js analizador_sintactico = new Sintactico_js(analizador_lexico);
        
        try 
        {            
            analizador_sintactico.parse();
            nodo raiz = analizador_sintactico.raiz;
//            Graficador a = new Graficador();
//            a.graficar(raiz);
            html.codigo_js+=texto;
            ejecutor_js.guardarFunciones(raiz.get(0));
            ejecutor_js.ejecutar(raiz.get(0));
            
        } 
        catch (Exception ex) 
        {
            System.out.println("ERROR al analizar el archivo JavaScript: "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR al analizar el archivo JavaScript: "+ex.getMessage(),"COMPILADORES2",2);
        }        
        
    }

    private void agregar_css(nodo raiz,JComponent component) {
        switch(raiz.nombre){
            case "ATRIBUTOS":
                for(nodo r: raiz.hijos)
                    agregar_css(r,component);
            break;
            case "CLICK":
                try {
                    String metodo = evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();
                    int tipo =1;
                    if(metodo.contains("("))
                        tipo=0;
                    metodo=metodo.replaceAll("\\(", "").replace(")","");
                    if(component instanceof boton){
                        ((boton)component).valor=metodo;//.replaceAll(("("), "").replaceAll(")", "");
                        ((boton)component).setTipo(tipo);
                    }
                    else if (component instanceof imagen){
                         ((imagen)component).valor=metodo;
                    }
                       
                    actual.click=metodo;
                } catch (Exception e) {
                    System.out.println("Error al obtener la ruta o metodo del componente"+ e.getMessage());
                }
                break;
            case "RUTA":
                 try {
                    String ruta = evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();
                    int tipo =1;
                    
                    if(component instanceof boton){
                        ((boton)component).valor=ruta;
                        ((boton)component).setTipo(tipo);
                    }
                    else if (component instanceof enlace){
                         ((enlace)component).ruta=ruta;
                    }
                    actual.click=ruta;
                } catch (Exception e) {
                    System.out.println("Error al obtener la ruta o metodo del componente"+ e.getMessage());
                }
                break;
            case "FIN":
                agregar_css(raiz.get(0),component);
                break;
            case "IDEN":
                aplicarIDEN(raiz);
                break;
            case "GRUPO":
                aplicarGRUPO(raiz);
                break;
            case "ALTO":
                aplicarALTO(raiz);
                break;
            case "ANCHO":
                aplicarANCHO(raiz);
                break;
            case "ALINEADO":
                break;
            case "VALOR":
                break;
        }
    }

    private void aplicarIDEN(nodo raiz) {
        try {
            String id = evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();
            
            id="iden_"+id;
            if(lista_css.containsKey(id)){
                copiar_estilo_css(lista_css.get(id));
                //actual= lista_css.get(id);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No existe el estilo : ");
        }
    }

    private void aplicarGRUPO(nodo raiz) {
         try {
            
             String id = evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();//guardar todos los estilos en lowercase
            
            id="grupo_"+id;
            if(lista_css.containsKey(id)){
                copiar_estilo_css(lista_css.get(id));
               // actual= lista_css.get(id);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No existe el estilo : ");
        }
    }
    
    private void aplicarALTO(nodo raiz) {
         try {
            String altox = evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();
            int xx = Integer.parseInt(altox);
            actual.y=xx;
        } catch (Exception e) {
            System.out.println("Error al evaluar el alto");
        }
    }

    private void aplicarANCHO(nodo raiz) {
          try {
//              if(actual.x!=200)
//                  return;
            String altox = evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();
            int y = Integer.parseInt(altox);
            actual.x=y;
            
        } catch (Exception e) {
              System.out.println("Error al evaluar el ancho");
        }
    }
    
    private void copiar_estilo_css(estilos nuevo) {
        if(nuevo.capital)
            actual.capital=nuevo.capital;
        if(nuevo.centrado)
            actual.centrado=nuevo.centrado;
        if(nuevo.color_borde!=null)
            actual.color_borde=nuevo.color_borde;
        if(nuevo.color_texto!=null)
            actual.color_texto=nuevo.color_texto;
        if(nuevo.cursiva)
            actual.cursiva=nuevo.cursiva;
        if(nuevo.curva)
            actual.curva=nuevo.curva;
        if(nuevo.derecha)
            actual.derecha=nuevo.derecha;
        if(nuevo.fondo_elemento!=null)
            actual.fondo_elemento=nuevo.fondo_elemento;
        if(nuevo.izquierda)
            actual.izquierda=nuevo.izquierda;
        if(nuevo.justificado)
            actual.justificado=nuevo.justificado;
        if(nuevo.mayuscula)
            actual.mayuscula=nuevo.mayuscula;
        if(nuevo.miniscula)
            actual.miniscula=nuevo.miniscula;
        if(nuevo.negrita)
            actual.negrita=nuevo.negrita;
        if(nuevo.opaco)
            actual.opaco=nuevo.opaco;
        if(nuevo.tam_borde>0)
            actual.tam_borde=nuevo.tam_borde;
        if(nuevo.tam_elemento!=null)
            actual.tam_elemento=nuevo.tam_elemento;
        if(nuevo.tam_letra>0)
            actual.tam_letra=nuevo.tam_letra;
        if(nuevo.tam_texto>0)
            actual.tam_texto=nuevo.tam_texto;
        if(!"".equals(nuevo.texto))
            actual.texto=nuevo.texto;
        if(!"".equals(nuevo.tipo_letra))
            actual.tipo_letra=nuevo.tipo_letra;
        actual.utilizado=nuevo.utilizado;
        if(nuevo.visiblidad)
            actual.visiblidad=nuevo.visiblidad;
        actual.x=nuevo.x;
        actual.y=nuevo.y;
    }

}
