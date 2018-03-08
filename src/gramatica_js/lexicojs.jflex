package gramatica_js;

import java_cup.runtime.*;
import java.util.ArrayList;
//import Acciones.ErrorT;

%%

%{
    //public ArrayList<ErrorT> lista_errores;
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
fechas = "'" {digito}+ ({digito}|":"|"/"|" ")+"'"

//*** Estados

%state COMENT_SIMPLE
%state COMENT_MULTI

%%
/*-------------------------------------------------------------------
----------------------------------------- Area de Reglas Lexicas
-------------------------------------------------------------------*/

//*** Comentarios

<YYINITIAL> "'/"            {yybegin(COMENT_MULTI);}
<COMENT_MULTI> ["\n"]       {}
<COMENT_MULTI> [^"/'"]      {}
<COMENT_MULTI> "/'"         {yybegin(YYINITIAL);}

<YYINITIAL> "'"             {yybegin(COMENT_SIMPLE);}
<COMENT_SIMPLE> [^"\n"]     {}
<COMENT_SIMPLE> "\n"        {yybegin(YYINITIAL);}


//*** Simbolos_js

//<YYINITIAL> "="        			{  return new Symbol(Simbolos_js.igual1, yycolumn, yyline, yytext());}

<YYINITIAL> ","         		{ return new Symbol(Simbolos_js.coma, yycolumn, yyline, yytext());}


<YYINITIAL> ":"         		{ return new Symbol(Simbolos_js.dosp, yycolumn, yyline, yytext());}

<YYINITIAL> ";"                 { return new Symbol(Simbolos_js.ppt, yycolumn, yyline, yytext());}

<YYINITIAL> "."                 { return new Symbol(Simbolos_js.punto, yycolumn, yyline, yytext());}


//palabras reservadas del lenguaje

<YYINITIAL> "DimV"              { return new Symbol(Simbolos_js.dim, yycolumn, yyline, yytext());}

<YYINITIAL> "si"         		{ return new Symbol(Simbolos_js.si, yycolumn, yyline, yytext());}

<YYINITIAL> "sino"         		{ return new Symbol(Simbolos_js.sino, yycolumn, yyline, yytext());}

<YYINITIAL> "mientras"          { return new Symbol(Simbolos_js.mientras, yycolumn, yyline, yytext());}

<YYINITIAL> "para"              { return new Symbol(Simbolos_js.para, yycolumn, yyline, yytext());}

<YYINITIAL> "selecciona"        { return new Symbol(Simbolos_js.selecciona, yycolumn, yyline, yytext());}

<YYINITIAL> "caso"              { return new Symbol(Simbolos_js.caso, yycolumn, yyline, yytext());}

<YYINITIAL> "defecto"           { return new Symbol(Simbolos_js.defecto, yycolumn, yyline, yytext());}

<YYINITIAL> "verdadero"         { return new Symbol(Simbolos_js.TRUE, yycolumn, yyline, yytext());}

<YYINITIAL> "falso"             { return new Symbol(Simbolos_js.FALSE, yycolumn, yyline, yytext());}

<YYINITIAL> "detener"           { return new Symbol(Simbolos_js.detener, yycolumn, yyline, yytext());}

<YYINITIAL> "continuar"         { return new Symbol(Simbolos_js.continuar, yycolumn, yyline, yytext());}

<YYINITIAL> "imprimir"          { return new Symbol(Simbolos_js.imprimir, yycolumn, yyline, yytext());}

<YYINITIAL> "funcion"           { return new Symbol(Simbolos_js.funcion, yycolumn, yyline, yytext());}

<YYINITIAL> "retornar"          { return new Symbol(Simbolos_js.retornar, yycolumn, yyline, yytext());}

<YYINITIAL> "mensaje"           { return new Symbol(Simbolos_js.mensaje, yycolumn, yyline, yytext());}

<YYINITIAL> "aTexto"           { return new Symbol(Simbolos_js.aTexto, yycolumn, yyline, yytext());}

<YYINITIAL> "conteo"           { return new Symbol(Simbolos_js.conteo, yycolumn, yyline, yytext());}

//****DOM

<YYINITIAL> "documento"         { return new Symbol(Simbolos_js.documento, yycolumn, yyline, yytext());}

<YYINITIAL> "obtener"           { return new Symbol(Simbolos_js.obtener, yycolumn, yyline, yytext());}

<YYINITIAL> "setElemento"       { return new Symbol(Simbolos_js.setElemento, yycolumn, yyline, yytext());}

<YYINITIAL> "observador"        { return new Symbol(Simbolos_js.observador, yycolumn, yyline, yytext());}


//*** Operadores Aritmeticos

<YYINITIAL> "+"         {   //System.out.println("Reconocido: <<"+yytext()+">>, mas");
                            return new Symbol(Simbolos_js.mas, yycolumn, yyline, yytext());}

<YYINITIAL> "-"         {  // System.out.println("Reconocido: <<"+yytext()+">>, menos");
                            return new Symbol(Simbolos_js.menos, yycolumn, yyline, yytext());}

<YYINITIAL> "*"         {  // System.out.println("Reconocido: <<"+yytext()+">>, por");
                            return new Symbol(Simbolos_js.por, yycolumn, yyline, yytext());}

<YYINITIAL> "/"         {   //System.out.println("Reconocido: <<"+yytext()+">>, dividir");
                            return new Symbol(Simbolos_js.dividir, yycolumn, yyline, yytext());}

<YYINITIAL> "^"         {   return new Symbol(Simbolos_js.pot, yycolumn, yyline, yytext());}                        

<YYINITIAL> "++"        {  return new Symbol(Simbolos_js.aumento, yycolumn, yyline, yytext());}                           

<YYINITIAL> "--"        {  return new Symbol(Simbolos_js.decremento, yycolumn, yyline, yytext());}                           
                            

 
<YYINITIAL> "("        {    return new Symbol(Simbolos_js.apar, yycolumn, yyline, yytext());}

<YYINITIAL> ")"        {    return new Symbol(Simbolos_js.cpar, yycolumn, yyline, yytext());}

<YYINITIAL> "{"        {    return new Symbol(Simbolos_js.alla, yycolumn, yyline, yytext());}

<YYINITIAL> "}"        {    return new Symbol(Simbolos_js.clla, yycolumn, yyline, yytext());}

//<YYINITIAL> "["        {    return new Symbol(Simbolos_js.cora, yycolumn, yyline, yytext());}

//<YYINITIAL> "]"        {    return new Symbol(Simbolos_js.corc, yycolumn, yyline, yytext());}


//OPERADORES LOGICOS 
<YYINITIAL> "||"        {   return new Symbol(Simbolos_js.OR, yycolumn, yyline, yytext());}
<YYINITIAL> "&&"        {   return new Symbol(Simbolos_js.AND, yycolumn, yyline, yytext());}
<YYINITIAL> "! "        {   return new Symbol(Simbolos_js.NOT, yycolumn, yyline, yytext());}

//OPERADORES RELACIONALES

<YYINITIAL> "=="        {   return new Symbol(Simbolos_js.iigual, yycolumn, yyline, yytext());}

<YYINITIAL> "!="        {   return new Symbol(Simbolos_js.noig, yycolumn, yyline, yytext());}

<YYINITIAL> ">"         {   return new Symbol(Simbolos_js.mayor, yycolumn, yyline, yytext());}

<YYINITIAL> "<"         {   return new Symbol(Simbolos_js.menor, yycolumn, yyline, yytext());}

<YYINITIAL> ">="|"=>"   {   return new Symbol(Simbolos_js.mayorigual, yycolumn, yyline, yytext());}

<YYINITIAL> "<="|"=<"   {   return new Symbol(Simbolos_js.menorigual, yycolumn, yyline, yytext());}


<YYINITIAL> "true"   {   return new Symbol(Simbolos_js.TRUE, yycolumn, yyline, yytext());}

<YYINITIAL> "false"   {   return new Symbol(Simbolos_js.FALSE, yycolumn, yyline, yytext());}


//**declaracion de mis tipos de dato                  
<YYINITIAL> {numero}        {  // System.out.println("Reconocido: <<"+yytext()+">>, numero ");
                                return new Symbol(Simbolos_js.numero, yycolumn, yyline, yytext());}

<YYINITIAL> {tstring}       {   //System.out.println("Reconocido: <<"+yytext()+">>, tstring ");
                                return new Symbol(Simbolos_js.tstring, yycolumn, yyline, yytext());}

<YYINITIAL> {fechas}         {   //System.out.println("Reconocido: <<"+yytext()+">>, tchar ");
                                return new Symbol(Simbolos_js.fechas, yycolumn, yyline, yytext());}

<YYINITIAL> {id}            {   //System.out.println("Reconocido: <<"+yytext()+">>, id ");
                                return new Symbol(Simbolos_js.id, yycolumn, yyline, yytext());}


<YYINITIAL> {tchar}            {   //System.out.println("Reconocido: <<"+yytext()+">>, id ");
                                return new Symbol(Simbolos_js.tchar, yycolumn, yyline, yytext());}

[ \t\r\n\f]                 {/* ignore white space. */ }
 
.                           {   System.out.println("Error Lexico: <<"+yytext()+">> ["+yyline+" , "+yycolumn+"]"); }


