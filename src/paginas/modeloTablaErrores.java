package paginas;

import interpretes.error;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jherson
 */
public class modeloTablaErrores  extends AbstractTableModel{

    private LinkedList<error> errores;
    private String[]cabecera ={"TIPO","DESCRIPCION","LINEA","COLUMNA"};
    
    public modeloTablaErrores(LinkedList<error> lista) {
        this.errores=lista;
        
    
    }

    
    
    @Override
    public int getRowCount() {
        return errores.size();
    }

    @Override
    public int getColumnCount() {
        return cabecera.length;
    }

   @Override
    public Object getValueAt(int a, int b) {
      error au = errores.get(a);
      String resultado=null;
      
      switch(b){
          case 0:
              resultado = au.tipo;
              break;
          case 1:
              resultado = au.men;
              break;
          case 2:
              resultado = String.valueOf(au.linea);
              break;
          case 3:
              resultado = String.valueOf(au.col);
              break;
      }
      return resultado;
    }
    @Override
    public String getColumnName(int i) {
        return cabecera[i];
    }
    
}
