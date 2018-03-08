package paginas;

import Arbol.evaluarExpresion;
import Arbol.nodo;
import interpretes.PanelSazo;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Jherson
 */
public class editor_html extends JScrollPane{
    //JPanel html;
    PanelSazo html;
    int puntero;
    int alto;
    int ancho;
    int puntero_alto;
    int x;
    
    public LinkedList<PanelSazo> paneles;
    
    HashMap<String,estilos>lista_css;
    estilos actual;
    
    
    public void aumentar_panel(PanelSazo panel){
        this.paneles.addFirst(panel);
    }
    
    public void disminuir_panel(){
        this.paneles.removeFirst();
    }
    
    public editor_html(HashMap<String,estilos> lista) {
        
        this.lista_css=lista;
        //creo la lista de paneles
        this.paneles= new LinkedList<>();
        
        this.setPreferredSize(new Dimension(925, 525));//tamanio del html que se va a mostrar
        
        //this.html = new JPanel();
        this.html = new PanelSazo();
        
        //this.html.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.html.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.html.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        //this.html.setLayout(null);
        this.html.setVisible(true);
        this.html.setPreferredSize(new Dimension(900,650));//tamanio de la pagina html
        
        this.html.setBackground(Color.white);
        
        
        this.setViewportView(this.html);
        
        this.alto=this.ancho=1;
        this.puntero_alto=650;
        aumentar_panel(this.html);
        
    }
    
    
    public void agregarParrafo(Parrafo nueva) {
        actual=nueva.estilo;
        pintar_elemento(nueva);
        if (!"".equals(actual.texto)) {
            nueva.setText(actual.texto);
        }
        agregar_elemento(nueva);

    }
    
    public void agregarTextBox(textEdit nueva ){
       
        actual=nueva.estilo;
            
        pintar_elemento(nueva);
        if(!"".equals(actual.texto))
            nueva.setText(actual.texto);
        
        agregar_elemento(nueva);         

    }
    
    public void agregarTextArea(textArea nueva ){
      
        actual=nueva.estilo;
        
        pintar_elemento(nueva);
        
        if(!"".equals(actual.texto))
            nueva.setText(actual.texto);
        
        agregar_elemento(nueva);        
    }
    
    public void agregarEnlance(enlace nuevo){
     
        actual=nuevo.estilo;
               
        pintar_elemento(nuevo);
        if(!"".equals(actual.texto))
            nuevo.setText(actual.texto);
        
        agregar_elemento(nuevo);        
    }
    
    public void agregarBoton(boton nuevo){
        actual=nuevo.estilo;
        
        pintar_elemento(nuevo);
        if(!"".equals(actual.texto))
            nuevo.setText(actual.texto);
        
        agregar_elemento(nuevo);
    }
    
    public void agregarSpinner(Spinner nuevo){
        agregar_elemento(nuevo);
    }
    
    public void agregarCaja(ComboBox nuevo){
        actual=nuevo.estilo;
        
        pintar_elemento(nuevo);
        agregar_elemento(nuevo);
    }
    
    public void agregarTabla(TablaSazo tabla, nodo raiz){
        agregar_elemento(tabla);
    }
    
    
    public void agregarImagen(imagen img){
        
        agregar_elemento(img);
      
    }

    
    public void aplicar_css(nodo raiz) {
        switch (raiz.nombre) {
            case "FIN":
                aplicar_css(raiz.get(0));
                break;
            case "ATRIBUTOS":
                for (nodo r : raiz.hijos) {
                    aplicar_css(r);
                }
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
                actual= lista_css.get(id);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No existe el estilo : ");
        }
    }
   
    public void agregar_elemento(JComponent nuevo){
        ancho+=nuevo.getPreferredSize().width;
        
        if(nuevo.getPreferredSize().height>alto)
            alto=nuevo.getPreferredSize().height;
       
        if(ancho>=900){
            ancho=0;
            //alto++;
            puntero_alto+=alto;
            alto=0;
            this.html.setPreferredSize(new Dimension(900,puntero_alto));
        }
           
        this.paneles.getFirst().add(nuevo);
        this.paneles.getFirst().repaint();
    }

    public void agregarPanel(PanelSazo panel) {
        //verificar su estilo etc
        
        if(panel.estilo.fondo_elemento!=null)
            panel.setBackground(panel.estilo.fondo_elemento);
        if(panel.estilo.x!=400||panel.estilo.y!=400)
            panel.setPreferredSize(new Dimension(panel.estilo.x, panel.estilo.y));
        
            
        if(panel.estilo.izquierda){
            panel.setLayout(new FlowLayout(FlowLayout.LEADING));
            panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        }
        if(panel.estilo.derecha){
            panel.setLayout(new FlowLayout(FlowLayout.LEADING));
            panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
        
//        
//        if(panel.estilo.tam_borde>0){
//            Double aux=actual.tam_borde;
//            int tam_borde = (int)aux.doubleValue();
//            panel.setBorder(new javax.swing.border.LineBorder(actual.color_borde, tam_borde, actual.curva));
//        }
        
//        if(!panel.estilo.visiblidad)
//            panel.setVisible(false);
//        
//        
//        //aplicando la opacidad
//        panel.setOpaque(panel.estilo.opaco);
        
        agregar_elemento(panel);
        aumentar_panel(panel);
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

    /***************aplicando css*********************/
    /////pintando los elementos
    private void pintar_elemento(JComponent nueva) {
        Double aux;
        if(actual.fondo_elemento!=null){//aplicando fondo elemento
            nueva.setBackground(actual.fondo_elemento);
        }
        
        if (actual.color_texto != null) //aplicando color de texto
            nueva.setForeground(actual.color_texto);
        
        int tam_letra = 12;
        
        if (actual.tam_texto > 12) {//ver si es tam_tex || tam_letra
             aux= actual.tam_texto;
            tam_letra = (int) aux.doubleValue();
        }
        
        //aplicando tipo de letra y tamanio de letra
        int tipo_formato=0;
        if(actual.negrita&&actual.cursiva)
            tipo_formato=3;
        else if(actual.cursiva)
            tipo_formato=2;
        else if(actual.negrita)
            tipo_formato=1;
        
        
        nueva.setFont(new java.awt.Font(actual.tipo_letra, tipo_formato, tam_letra));
        
        //aplicando borde
        if(actual.tam_borde>0){
            aux=actual.tam_borde;
            int tam_borde = (int)aux.doubleValue();
            nueva.setBorder(new javax.swing.border.LineBorder(actual.color_borde, tam_borde, actual.curva));
        }
        
        if(!actual.visiblidad)
            nueva.setVisible(false);
        
        //aplicando la opacidad
        nueva.setOpaque(actual.opaco);
        
        if(actual.x!=0&&actual.y!=0)
            nueva.setPreferredSize(new Dimension(actual.x,actual.y));
        
        
    }

    private void aplicarALTO(nodo raiz) {
         try {
            if(x!=30)
                return;
            String altox = evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();
            int xx = Integer.parseInt(altox);
            actual.y=xx;
        } catch (Exception e) {
            //throw new UnsupportedOperationException("Error al evaluar alto " + raiz.linea);
            System.out.println("Error al evaluar el alto");
        }
    }

    private void aplicarANCHO(nodo raiz) {
          try {
              if(actual.x!=200)
                  return;
            String altox = evaluarExpresion.evaluar_expresion(raiz.get(0)).toString();
            int y = Integer.parseInt(altox);
            actual.x=y;
            
        } catch (Exception e) {
              System.out.println("Error al evaluar el ancho");
                    
        }

    }

    
    /***************************
     * public void agregarComponente(JComponent a){
        Color arreglo[] = {Color.yellow,Color.black,Color.blue,Color.green,Color.red};
        if(puntero>4)
            puntero=0;
       
        panel ax = new panel(300, 300,300,arreglo[puntero++]);
       // x=x-50;
//        alto=alto+200;
//        
//        if(alto>600)
//        {
//            this.html.setPreferredSize(new Dimension(950, alto));
//            
//        }
        
        this.html.add(ax);//cambiar a ax si hay problemas
        this.html.repaint();
    }*/
    
    
}
