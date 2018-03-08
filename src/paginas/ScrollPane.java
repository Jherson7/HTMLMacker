package paginas;

import Arbol.Graficador;
import Arbol.control;
import Arbol.control_paginas;
import Arbol.nodo;
import com.sun.glass.events.KeyEvent;
import gramaticas.Lexico_html;
import gramaticas.Sintactico_html;
import interpretes.error;
import interpretes.interprete_html;
import interpretes.interprete_js;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import vistas.estadisticas;

/**
 *
 * @author Jherson
 */
public class ScrollPane extends JScrollPane{
    
    JPanel pagina;
    JPanel panel_ruta;
    JPanel favoritos;
    JTextField ruta;
    JButton btn_atras;
    JButton btn_adelante;
    JButton btn_refresh;
    public editor_html cuerpo_html;
    JButton btn_opciones;
    JButton btn_historial;
    JButton agregar_fav;
    public interprete_html ejecutor_html;
    public HashMap<String,estilos> lista;
    public interprete_js inteprete;
    
    public LinkedList<JComponent> elementos;
    
    public String codigo_html="";
    public String codigo_css="";
    public String codigo_js="";
    
    public  LinkedList<error> errores;
    
    public ScrollPane() {
        
        //inicializo el ejecutor
        lista= new HashMap<>();
        errores=new LinkedList<>();
        
        this.elementos= new LinkedList<>();
        this.inteprete= new interprete_js();
        this.ejecutor_html= new interprete_html(this,lista,inteprete);//anterior solo le mandaba 
        
        this.pagina = new JPanel();
        this.panel_ruta= new JPanel();
        this.cuerpo_html = new editor_html(lista);//le envio la lista de css propia de esta pagina
        this.favoritos= new JPanel();
        
        this.panel_ruta.setLayout(new FlowLayout());
        this.panel_ruta.setVisible(true);
        this.panel_ruta.setPreferredSize(new Dimension(750,80));
        this.panel_ruta.setBackground(Color.GRAY);
        
        this.favoritos.setLayout(new FlowLayout());
        this.favoritos.setVisible(true);
        this.favoritos.setPreferredSize(new Dimension(750,30));
        this.favoritos.setBackground(Color.GRAY);
        actualizar_paginas_favoritas();
        
        
        cargar_ruta(panel_ruta);
        cargar_botones(panel_ruta);
        
        this.pagina.add(panel_ruta);
        this.pagina.add(favoritos);
        this.pagina.add(cuerpo_html);
        
        this.pagina.setPreferredSize(new Dimension(600,800));
        this.pagina.setVisible(true);
        
        this.pagina.setBackground(Color.GRAY);
        this.setViewportView(this.pagina);
        
    }
    
    
    private void cargar_ruta(JPanel contenedor){
        
        this.ruta = new JTextField();
        this.ruta.setPreferredSize(new Dimension(620,30));
        this.ruta.setBackground(Color.LIGHT_GRAY);
        this.ruta.setText("C:\\Users\\Jherson\\Documents\\UNIVERSIDAD\\1 SEM 2018\\COMPILADORES2\\ArchivosProyecto1\\html.chtml");
        
        this.ruta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                 if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                     
                     if(ruta.getText().isEmpty())
                         return;
                     setearScrollPaneActual();
                     analizar(ruta.getText());
                     
                     
                     
                 }
            }

           
        });
        contenedor.add(ruta);
    }
    
    
    public void analizar(String ruta){
        this.pagina.remove(cuerpo_html);
        cuerpo_html= new editor_html(lista);
        this.pagina.add(cuerpo_html)  ;
        control_paginas.agregar_a_historial(ruta);
        analizar_html(ruta);
    }
    
    private void cargar_botones(JPanel contenedor){
        
        this.btn_atras = new JButton("<<");
        this.btn_adelante = new JButton(">>");
        this.btn_refresh = new JButton("?");
        this.btn_opciones = new JButton("OPCIONES");
        this.btn_historial = new JButton("HISTORIAL");
        this.agregar_fav = new JButton("AGREGAR FAV");
        
        
        contenedor.add(btn_atras);
        contenedor.add(btn_adelante);
        contenedor.add(btn_refresh);
        contenedor.add(btn_opciones);
        contenedor.add(btn_historial);
        contenedor.add(agregar_fav);
        
        
        
        btn_historial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               control_paginas.mostrar_historial();//se muestra el historial de todas las paginas visitadas
            }
        });
        
        btn_opciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               
                estadisticas nueva = new estadisticas(codigo_html, codigo_css, codigo_js);
                nueva.setVisible(true);
            }
        });
        
        agregar_fav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(ruta.getText().isEmpty())
                    return;
                control_paginas.agregar_pagina_favorita(ruta.getText());
                actualizar_paginas_favoritas();
            }
        });
        
    }
    
    
    public void analizar_html(String ruta){
        
        String texto = retornarContenidoRuta(ruta);
        if("".equals(texto)){
            JOptionPane.showMessageDialog(null, "Error al abrir la ruta: "+ruta);
            return;
        }
        
        codigo_html=texto;
        Reader reader = new StringReader(texto);
        Lexico_html analizador_lexico =  new Lexico_html(reader);
        Sintactico_html analizador_sintactico = new Sintactico_html(analizador_lexico);
        
        try 
        {            
          analizador_sintactico.parse();
          nodo r = analizador_sintactico.raiz;
          Graficador a = new Graficador();
          a.graficar(r);
          
          
          ejecutor_html.inicio(r);
          
       
        } 
        catch (Exception ex) 
        {
            System.out.println("ERROR a la hora de analizar el archivo: "+ex.getMessage());
            System.out.println("! ============================================================ Analisis Abortado");
            JOptionPane.showMessageDialog(null, "Ocurrio un grave problema","Ejemplo",2);
            return;
        }        
        
        System.out.println("! ============================================================ Analisis Completado");
        JOptionPane.showMessageDialog(null, "Analisis Completo","Proyecto 2",1);
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
    
     
     private void actualizar_paginas_favoritas(){
         LinkedList<boton> hola = control_paginas.get_paginas();
         this.favoritos.removeAll();
         for(boton a : hola)
             this.favoritos.add(a);
         this.favoritos.repaint();
         this.pagina.repaint();
     }
     
      private void setearScrollPaneActual() {
          control.setearInterpreteHtml(ejecutor_html);
          control.setear_actual(this);
          control_paginas.set_pagina_actual(this);
     }
}
