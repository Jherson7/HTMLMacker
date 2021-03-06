package vistas;

import Arbol.Graficador;
import Arbol.control;
import Arbol.nodo;
import gramatica_js.Lexico_js;
import gramatica_js.Sintactico_js;
import interpretes.interprete_js;
import java.io.Reader;
import java.io.StringReader;
import javax.swing.JOptionPane;

/**
 *
 * @author Jherson
 */
public class probador_js extends javax.swing.JFrame {

    /**
     * Creates new form probador_js
     */
    public probador_js() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("COMPILAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String texto = jTextArea1.getText();
        analizar(texto);

    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void analizar(String texto) {
           
                
        Reader reader = new StringReader(texto);
        Lexico_js analizador_lexico =  new Lexico_js(reader);
        Sintactico_js analizador_sintactico = new Sintactico_js(analizador_lexico);
        
        try 
        {            
            
            analizador_sintactico.parse();
            Graficador a = new Graficador();
            nodo raiz= analizador_sintactico.raiz;
            a.graficar(raiz);
//            String arbol = a.getGraph();
//            System.out.println(arbol);
//            
            
            //control.getJS().consola.setLength(0);
            interprete_js nuevo = new interprete_js(raiz.get(0));
           // nuevo.ejecutar(raiz.get(0));
            jTextArea1.setText(nuevo.consola.toString());
            //jTextArea1.setText(control.getJS().consola.toString());
            
        } 
        catch (Exception ex) 
        {
            System.out.println("ERROR a la hora de analizar el archivo: "+ex.getMessage());
            System.out.println("! ============================================================ Analisis Abortado");
            JOptionPane.showMessageDialog(null, "Ocurrio un grave problema: "+ex.getMessage(),"Ejemplo",2);
            return;
        }        
        
        System.out.println("! ============================================================ Analisis Completado");
        JOptionPane.showMessageDialog(null, "Analisis Completo","Proyecto 2",1);
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(probador_js.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(probador_js.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(probador_js.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(probador_js.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new probador_js().setVisible(true);
            }
        });
    }

    private int fibonacci(int n){
        if(n<=1)
            return 1;
        else
            return fibonacci(n-1) + fibonacci(n-2);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
