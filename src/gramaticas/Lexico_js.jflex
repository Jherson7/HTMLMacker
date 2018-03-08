package gramaticas;

import java_cup.runtime.*;
import java.util.ArrayList;


%%

%{
    
%}


/*----------------------------------------------------------------------------
----------------------------------------- Area de Opciones y Declaraciones
----------------------------------------------------------------------------*/

//*** Directivas

%public
%class Lexico_js
%cupsym Simbolos_js
%cup
%char
%column
%full
%ignorecase
%line
%unicode

//*** Expresiones Regulares

digito = [0-9]
numero = {digito}+("." {digito}+)?
tstring = "\"" ~"\""
tchar = "'" ~"'"
letra = [a-zA-ZñÑ]
id = {letra}+({letra}|{digito}|"_")*

//*** Estados

%state COMENT_SIMPLE
%state COMENT_MULTI

%%
/*-------------------------------------------------------------------
----------------------------------------- Area de Reglas Lexicas
-------------------------------------------------------------------*/

//*** Comentarios

<YYINITIAL>     "<//-"              {yybegin(COMENT_MULTI);}
<COMENT_MULTI>  ["\n"]              {}
<COMENT_MULTI>  [^"*/"]             {}
<COMENT_MULTI>  "-//>"              {yybegin(YYINITIAL);}

<YYINITIAL> "//"                    {yybegin(COMENT_SIMPLE);}
<COMENT_SIMPLE> [^"\n"]             {}
<COMENT_SIMPLE> "\n"                {yybegin(YYINITIAL);}


//*** Simbolos_html

<YYINITIAL> "="                     {  return new Symbol(Simbolos_html.igual1, yycolumn, yyline, yytext());}

<YYINITIAL> ","                     { return new Symbol(Simbolos_html.coma, yycolumn, yyline, yytext());}

<YYINITIAL> ":"                     { return new Symbol(Simbolos_html.dosp, yycolumn, yyline, yytext());}

<YYINITIAL> ";"                     { return new Symbol(Simbolos_html.ppt, yycolumn, yyline, yytext());}


//*** Operadores Aritmeticos

<YYINITIAL> "+"                 { return new Symbol(Simbolos_html.mas, yycolumn, yyline, yytext());}

<YYINITIAL> "-"                 { return new Symbol(Simbolos_html.menos, yycolumn, yyline, yytext());}

<YYINITIAL> "*"                 { return new Symbol(Simbolos_html.por, yycolumn, yyline, yytext());}

<YYINITIAL> "/"                 { return new Symbol(Simbolos_html.dividir, yycolumn, yyline, yytext());}

<YYINITIAL> "^"                 { return new Symbol(Simbolos_html.pot, yycolumn, yyline, yytext());}                        

<YYINITIAL> "++"                { return new Symbol(Simbolos_html.aumento, yycolumn, yyline, yytext());}                           

<YYINITIAL> "--"                { return new Symbol(Simbolos_html.decremento, yycolumn, yyline, yytext());}                           
                            

 
<YYINITIAL> "("                 {    return new Symbol(Simbolos_html.apar, yycolumn, yyline, yytext());}

<YYINITIAL> ")"                 {    return new Symbol(Simbolos_html.cpar, yycolumn, yyline, yytext());}

//**declaracion de mis tipos de dato                  
<YYINITIAL> {numero}                        {  return new Symbol(Simbolos_html.numero, yycolumn, yyline, yytext());}

<YYINITIAL> {tstring}                       {  return new Symbol(Simbolos_html.tstring, yycolumn, yyline, yytext());}

<YYINITIAL> {tchar}                         {  return new Symbol(Simbolos_html.tchar, yycolumn, yyline, yytext());}

<YYINITIAL> {id}                            {  return new Symbol(Simbolos_html.id, yycolumn, yyline, yytext());}

//Lenguaje CHTML

<YYINITIAL> "<CHTML>"                       {   return new Symbol(Simbolos_html.html, yycolumn, yyline, yytext());}

<YYINITIAL> "<FIN-HTML>"                    {   return new Symbol(Simbolos_html.chtml, yycolumn, yyline, yytext());}

<YYINITIAL> "<ENCABEZADO>"                  {   return new Symbol(Simbolos_html.head, yycolumn, yyline, yytext());}

<YYINITIAL> "<FIN-ENCABEZADO>"              {   return new Symbol(Simbolos_html.fin_head, yycolumn, yyline, yytext());}

<YYINITIAL>  "<CUERPO>"                     {   return new Symbol(Simbolos_html.cuerpo, yycolumn, yyline, yytext());}
<YYINITIAL> "<FIN-CUERPO>"                  {   return new Symbol(Simbolos_html.fincuerpo, yycolumn, yyline, yytext());}
<YYINITIAL>"<CJS"                           {   return new Symbol(Simbolos_html.cjs, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-CJS>"                      {   return new Symbol(Simbolos_html.fincjs, yycolumn, yyline, yytext());}
<YYINITIAL>"<CCSS>"                         {   return new Symbol(Simbolos_html.css, yycolumn, yyline, yytext());}
<YYINITIAL>"<TITULO>"                       {   return new Symbol(Simbolos_html.titulo, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-TITULO>"                   {   return new Symbol(Simbolos_html.fin_titulo, yycolumn, yyline, yytext());}
<YYINITIAL>"<PANEL>"                        {   return new Symbol(Simbolos_html.panel, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-PANEL>"                    {   return new Symbol(Simbolos_html.fin_panel, yycolumn, yyline, yytext());}
<YYINITIAL>"<TEXTO"                         {   return new Symbol(Simbolos_html.texto, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-TEXTO>"                    {   return new Symbol(Simbolos_html.fin_texto, yycolumn, yyline, yytext());}
<YYINITIAL>"<IMAGEN"                        {   return new Symbol(Simbolos_html.imagen, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-IMAGEN>"                   {   return new Symbol(Simbolos_html.fin_imagen, yycolumn, yyline, yytext());}
<YYINITIAL>"<BOTON"                         {   return new Symbol(Simbolos_html.boton, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-BOTON>"                    {   return new Symbol(Simbolos_html.fin_boton, yycolumn, yyline, yytext());}
<YYINITIAL>"<ENLACE"                        {   return new Symbol(Simbolos_html.enlace, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-ENLACE>"                   {   return new Symbol(Simbolos_html.fin_enlace, yycolumn, yyline, yytext());}
<YYINITIAL>"<TABLA"                         {   return new Symbol(Simbolos_html.tabla, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-TABLA>"                    {   return new Symbol(Simbolos_html.fin_tabla, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIL T"                         {   return new Symbol(Simbolos_html.filt, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-FIL T>"                    {   return new Symbol(Simbolos_html.fin_filt, yycolumn, yyline, yytext());}
<YYINITIAL>"<CB"                            {   return new Symbol(Simbolos_html.cb, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-CB>"                       {   return new Symbol(Simbolos_html.fin_cb, yycolumn, yyline, yytext());}
<YYINITIAL>"<CT"                            {   return new Symbol(Simbolos_html.ct, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-CT>"                       {   return new Symbol(Simbolos_html.fin_ct, yycolumn, yyline, yytext());}
<YYINITIAL>"<TEXTO_A"                       {   return new Symbol(Simbolos_html.texto_a, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-TEXTO_A>"                  {   return new Symbol(Simbolos_html.fin_texto_a, yycolumn, yyline, yytext());}
<YYINITIAL>"<CAJA TEXTO>"                   {   return new Symbol(Simbolos_html.cajatexto, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-CAJA TEXTO>"               {   return new Symbol(Simbolos_html.fin_cajatexto, yycolumn, yyline, yytext());}
<YYINITIAL>"<CAJA"                          {   return new Symbol(Simbolos_html.caja, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-CAJA>"                     {   return new Symbol(Simbolos_html.fin_caja, yycolumn, yyline, yytext());}
<YYINITIAL>"<OPCION"                        {   return new Symbol(Simbolos_html.opcion, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-OPCION>"                   {   return new Symbol(Simbolos_html.fin_opcion, yycolumn, yyline, yytext());}
<YYINITIAL>"<SPINNER"                       {   return new Symbol(Simbolos_html.spinner, yycolumn, yyline, yytext());}
<YYINITIAL>"<FIN-SPINNER>"                  {   return new Symbol(Simbolos_html.fin_spinner, yycolumn, yyline, yytext());}
<YYINITIAL>"<SALTO-FIN>"                    {   return new Symbol(Simbolos_html.salto, yycolumn, yyline, yytext());}
<YYINITIAL>"valor"                          {   return new Symbol(Simbolos_html.valor, yycolumn, yyline, yytext());}
<YYINITIAL>"click"                          {   return new Symbol(Simbolos_html.click, yycolumn, yyline, yytext());}
<YYINITIAL>"ruta"                           {   return new Symbol(Simbolos_html.ruta, yycolumn, yyline, yytext());}
<YYINITIAL>"id"                             {   return new Symbol(Simbolos_html.t_id, yycolumn, yyline, yytext());}
<YYINITIAL>"grupo"                          {   return new Symbol(Simbolos_html.grupo, yycolumn, yyline, yytext());}
<YYINITIAL>"ancho"                          {   return new Symbol(Simbolos_html.ancho, yycolumn, yyline, yytext());}
<YYINITIAL>"alto"                           {   return new Symbol(Simbolos_html.alto, yycolumn, yyline, yytext());}
<YYINITIAL>"alineado"                       {   return new Symbol(Simbolos_html.alineado, yycolumn, yyline, yytext());}

[ \t\r\n\f]                                 {/* ignore white space. */ }
 
.                                           {   System.out.println("Error Lexico: <<"+yytext()+">> ["+yyline+" , "+yycolumn+"]"); }
