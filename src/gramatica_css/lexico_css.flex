package gramatica_css;

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
%class Lexico_css
%cupsym Simbolos_css
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
tchar = "'" ~ "'"
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

<YYINITIAL>     "/*"              	{yybegin(COMENT_MULTI);}
COMENT_MULTI  ["\n"]              	{}
COMENT_MULTI  [^"*/"]            	{}
COMENT_MULTI  "*/"              	{yybegin(YYINITIAL);}

<YYINITIAL> "//"                    {yybegin(COMENT_SIMPLE);}
COMENT_SIMPLE [^"\n"]             	{}
COMENT_SIMPLE "\n"                	{yybegin(YYINITIAL);}


//*** Simbolos_css

<YYINITIAL> ":="                     		{  return new Symbol(Simbolos_css.asig, yycolumn, yyline, yytext());}

<YYINITIAL> ","                     		{ return new Symbol(Simbolos_css.coma, yycolumn, yyline, yytext());}

<YYINITIAL> ":"                     		{ return new Symbol(Simbolos_css.dosp, yycolumn, yyline, yytext());}

<YYINITIAL> ";"                     		{ return new Symbol(Simbolos_css.ppt, yycolumn, yyline, yytext());}

<YYINITIAL> "["                     		{ return new Symbol(Simbolos_css.cora, yycolumn, yyline, yytext());}

<YYINITIAL> "]"                     		{ return new Symbol(Simbolos_css.corc, yycolumn, yyline, yytext());}


//*** Operadores Aritmeticos

<YYINITIAL> "+"                 			{ return new Symbol(Simbolos_css.mas, yycolumn, yyline, yytext());}

<YYINITIAL> "-"                 			{ return new Symbol(Simbolos_css.menos, yycolumn, yyline, yytext());}

<YYINITIAL> "*"                 			{ return new Symbol(Simbolos_css.por, yycolumn, yyline, yytext());}

<YYINITIAL> "/"                 			{ return new Symbol(Simbolos_css.dividir, yycolumn, yyline, yytext());}

<YYINITIAL> "^"                 			{ return new Symbol(Simbolos_css.pot, yycolumn, yyline, yytext());}                        

<YYINITIAL> "++"                			{ return new Symbol(Simbolos_css.aumento, yycolumn, yyline, yytext());}                           

<YYINITIAL> "--"                			{ return new Symbol(Simbolos_css.decremento, yycolumn, yyline, yytext());}                           
                            

 
<YYINITIAL> "("                 			{    return new Symbol(Simbolos_css.apar, yycolumn, yyline, yytext());}

<YYINITIAL> ")"                 			{    return new Symbol(Simbolos_css.cpar, yycolumn, yyline, yytext());}




//Lenguaje CSS

<YYINITIAL> "izquierda"                     {   return new Symbol(Simbolos_css.izquierda, yycolumn, yyline, yytext());}
<YYINITIAL> "derecha"                    	{   return new Symbol(Simbolos_css.derecha, yycolumn, yyline, yytext());}
<YYINITIAL> "centrado"                   	{   return new Symbol(Simbolos_css.centrado, yycolumn, yyline, yytext());}
<YYINITIAL> "justificado"               	{   return new Symbol(Simbolos_css.justificado, yycolumn, yyline, yytext());}
<YYINITIAL> "texto"                   		{   return new Symbol(Simbolos_css.texto, yycolumn, yyline, yytext());}
<YYINITIAL> "Negrilla"                      {   return new Symbol(Simbolos_css.negrilla, yycolumn, yyline, yytext());}
<YYINITIAL> "Cursiva"                       {   return new Symbol(Simbolos_css.Cursiva, yycolumn, yyline, yytext());}
<YYINITIAL> "formato"                       {   return new Symbol(Simbolos_css.formato, yycolumn, yyline, yytext());}
<YYINITIAL> "Mayuscula"                    	{   return new Symbol(Simbolos_css.Mayuscula, yycolumn, yyline, yytext());}
<YYINITIAL> "Minuscula"                     {   return new Symbol(Simbolos_css.Minuscula, yycolumn, yyline, yytext());}
<YYINITIAL> "Capital-t"                     {   return new Symbol(Simbolos_css.capital, yycolumn, yyline, yytext());}
<YYINITIAL> "letra"                         {   return new Symbol(Simbolos_css.letra, yycolumn, yyline, yytext());}
<YYINITIAL> "TamTex"                        {   return new Symbol(Simbolos_css.TamTex, yycolumn, yyline, yytext());}
<YYINITIAL> "FondoElemento"                 {   return new Symbol(Simbolos_css.FondoElemento, yycolumn, yyline, yytext());}
<YYINITIAL> "Visible"                       {   return new Symbol(Simbolos_css.Visible, yycolumn, yyline, yytext());}
<YYINITIAL> "Borde"                         {   return new Symbol(Simbolos_css.Borde, yycolumn, yyline, yytext());}
<YYINITIAL> "opaque"                        {   return new Symbol(Simbolos_css.opaque, yycolumn, yyline, yytext());}
<YYINITIAL> "colortext"                     {   return new Symbol(Simbolos_css.colortext, yycolumn, yyline, yytext());}
<YYINITIAL> "grupo"                         {   return new Symbol(Simbolos_css.grupo, yycolumn, yyline, yytext());}
<YYINITIAL> "id"                         	{   return new Symbol(Simbolos_css.iden, yycolumn, yyline, yytext());}
<YYINITIAL> "autoredimension"              	{   return new Symbol(Simbolos_css.resize, yycolumn, yyline, yytext());}

//los que me faltan
<YYINITIAL> "alineado"                      {   return new Symbol(Simbolos_css.alineado, yycolumn, yyline, yytext());}
<YYINITIAL> "true"                     		{   return new Symbol(Simbolos_css.TRUE, yycolumn, yyline, yytext());}
<YYINITIAL> "false"                     	{   return new Symbol(Simbolos_css.FALSE, yycolumn, yyline, yytext());}
<YYINITIAL> "Tamelemento"                   {   return new Symbol(Simbolos_css.Tamelemento, yycolumn, yyline, yytext());}
<YYINITIAL> "idElemento"                    {   return new Symbol(Simbolos_css.idElemento, yycolumn, yyline, yytext());}

//**declaracion de mis tipos de dato                  
<YYINITIAL> {numero}                        {  return new Symbol(Simbolos_css.numero, yycolumn, yyline, yytext());}

<YYINITIAL> {tstring}                       {  return new Symbol(Simbolos_css.tstring, yycolumn, yyline, yytext());}

<YYINITIAL> {tchar}                         {  return new Symbol(Simbolos_css.tchar, yycolumn, yyline, yytext());}

<YYINITIAL> {id}                            {  return new Symbol(Simbolos_css.id, yycolumn, yyline, yytext());}


[ \t\r\n\f]                                 {/* ignore white space. */ }
 
.                                           {   System.out.println("Error Lexico: "+yytext()+" ["+yyline+" , "+yycolumn+"]"); }
