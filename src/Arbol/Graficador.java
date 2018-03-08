/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author Jherson
 */
public class Graficador {
    public String desktop;
    private StringBuilder graphivz;
    private int contador;

    public Graficador() {
    }

    public void graficar(nodo arbol) {
        graphivz = new StringBuilder();
        contador = 0;
        graphivz.append("digraph G {\r\n node[shape=rectangle, style=filled, color=khaki1, fontcolor=black]; \r\n");
        Arbol_listar_enlazar(arbol, contador);
        graphivz.append("}");
        graficar2(graphivz.toString(), "arbol");

    }

    private void Arbol_listar_enlazar(nodo nodo, int num) {
        if (nodo != null) {
            graphivz.append("node" + num + " [ label = \"" + nodo.nombre + "\"];\r\n");
            int tam = nodo.hijos.size();
            int actual;
            for (int i = 0; i < tam; i++) {
                contador = contador + 1;
                actual = contador;
                Arbol_listar_enlazar(nodo.hijos.get(i), contador);
                graphivz.append("\"node" + num + "\"->\"node" + actual + "\";\r\n");
            }
        }
    }
    
    public String getGraph(){
        return graphivz.toString();
    }
    
    
    private void graficar2(String cadena, String nombre) {
        try {
            cadena = cadena.replace("\"\"", "\"");

            FileWriter fichero = null;
            PrintWriter pw = null;
            try {
                fichero = new FileWriter("C:\\Users\\Jherson\\Documents\\UNIVERSIDAD\\1 SEM 2018\\COMPILADORES2\\ArchivosProyecto1\\arboles\\" + nombre + ".dot");
                pw = new PrintWriter(fichero);
                pw.write(cadena);
            } catch (Exception g) {

            }
            fichero.close();

            String comando = "cd \"C:\\Graphviz\\bin\" \r\n"
                    + "dot -Tpng \"C:\\Users\\Jherson\\Documents\\UNIVERSIDAD\\1 SEM 2018\\COMPILADORES2\\ArchivosProyecto1\\arboles\\" + nombre + ".dot\" -o \"C:\\Users\\Jherson\\Documents\\UNIVERSIDAD\\1 SEM 2018\\COMPILADORES2\\ArchivosProyecto1\\arboles\\" + nombre + ".png\"\n" + "";

            try {
                fichero = new FileWriter("C:\\Users\\Jherson\\Documents\\UNIVERSIDAD\\1 SEM 2018\\COMPILADORES2\\ArchivosProyecto1\\arboles\\graficar.cmd");
                pw = new PrintWriter(fichero);
                pw.write(comando);
            } catch (Exception g) {

            }
            fichero.close();
            
            File f = new File("C:\\Users\\Jherson\\Documents\\UNIVERSIDAD\\1 SEM 2018\\COMPILADORES2\\ArchivosProyecto1\\arboles\\graficar.cmd");
            Desktop.getDesktop().open(f);

        } catch (Exception e) {

        }
    }
}
