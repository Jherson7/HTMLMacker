package generadores;

import java.io.File;

/**
 *
 * @author Joaquin
 */
public class AGeneradorLexico 
{
    
    public static void main(String[] args) 
    {
        
        int cual=1;
        String path;
        switch (cual){
            case 1:
                path="src\\gramaticas\\lexico_html.jflex";
                break;
            case 2:
                path="src\\gramatica_js\\lexicojs.jflex";
                break;
            default:
                path="src\\gramatica_css\\lexico_css.flex";
                break;
        }
        //**************generador lexico de html
        
        generarLexer(path);
        
        
    }    
    public static void generarLexer(String path)
    {
        File file=new File(path);
        jflex.Main.generate(file);
    } 
    
}
