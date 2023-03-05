import java.util.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.text.DecimalFormat;

public class AlgoritmosBasicos{


    /**
     * Método que le quita espacios, dígitos y caracteres especiales a un texto.
     * @param texto es el texto a limpiar.
     * @return el texto limpio convertido a mayúsculas.
     */
    public static String limpiaTexto(String texto){
        texto = texto.toUpperCase();
        String textoSinEspacio = texto.replace(" ", "");
        String textoLimpio = textoSinEspacio.replaceAll("[^A-Z]", "");
        return textoLimpio;
    }


    /**
     * Método que divide en subcadenas un texto.
     * @param texto es el texto a dividir.
     * @param numLetras es el número de letras en el que será divido el texto.
     * @return el texto dividido de acuerdo al número de letras.
     */
    public static String separaTexto(String texto, int numLetras){
        texto = limpiaTexto(texto);
        int contador = numLetras;
        String textoAux = "";
        for (int i = 0; i < texto.length(); i++){
            String letra = "" + texto.charAt(i);
            textoAux += letra;
            if(contador == (i+1)){
                textoAux += " ";
                contador += numLetras;
            } 
        }
        return textoAux;
    }


    /**
     * Método que nos da el porcentaje de apariciones de una letra en el texto.
     * @param conteoCaracteres es la tabla hash que contiene las aparciones de la letra en el texto.
     * @param texto es el texto a dividir.
     * @return una tabla has con la letra y su porcentaje de apariciones.
     */
    public static Hashtable<String, String> porcentajeLetra(Hashtable<String, Integer> conteoCaracteres, String texto){
        Hashtable<String, String> porcentajes = new Hashtable<>();  
        texto = limpiaTexto(texto);
        int longTexto = texto.length();
        DecimalFormat formato = new DecimalFormat("#.000");

        //Para iterar en la tabla hash
        Enumeration<String> enu = conteoCaracteres.keys();

        while (enu.hasMoreElements()) {
            String key = enu.nextElement();
            int repeticiones = conteoCaracteres.get(key);
            double porcentaje = (double) (repeticiones * 100) / longTexto;
            String porcFormato = formato.format(porcentaje) + "%";
            porcentajes.put(key, porcFormato);
        }
        return porcentajes;  
    }



    public static Hashtable<String, Integer> conteoDeCaracteres(String texto){
        Hashtable<String, Integer> frecuencias = new Hashtable<>();  
        texto = limpiaTexto(texto);
        for(int i = 0; i < texto.length(); i++){
            String letra = "" + texto.charAt(i);
            if(frecuencias.containsKey(letra)){
                frecuencias.put(letra, frecuencias.get(letra) + 1);
            } else {
                frecuencias.put(letra, 1);
            }
        }
        return frecuencias;  
    }


    /**
     * MÉTODO QUE TENÍA ANTES MI POMPA BONITA. 
     * ES IGUAL AL DE ARRIBA PERO LE BORRÉ LINEAS DE CÓDIGO DONDE SE VERIFICAN 
     * ESPACIOS PORQUE YA LO HACE EL MÉTODO QUE LIMPIA EL TEXTO.
     */

    //private static Hashtable<String, Integer> conteoDeCaracteres(String texto){
    //    Hashtable<String, Integer> frecuencias = new Hashtable<>();  
    //    for(int i=0; i<texto.length(); i++){
    //        String letra =""+texto.charAt(i);
    //        if(texto.charAt(i)+" " ==" "){
    //            frecuencias.put(" ", 1);
    //        }else{
    //            if(frecuencias.containsKey(letra)==true){
    //                frecuencias.put(letra, frecuencias.get(letra)+1);
    //            }else{
    //                frecuencias.put(letra, 1);
    //            } 
    //        }
    //    }
    //    return frecuencias;  
    //}


    public static void main(String[] args){

        System.out.println("\nTEXTO:");
        String texto = "hola mucho gusto uwu";
        System.out.println(texto);
        System.out.println("______________________");


        //  Prueba del método que cuenta las frecuencias de una letra en el texto.
        System.out.println("\n PRUEBA DE FRECUENCIAS EN EL TEXTO");
        Hashtable<String, Integer> frecuencias = new Hashtable<>(); 
        frecuencias = conteoDeCaracteres(texto);
        System.out.println(frecuencias);
        System.out.println("______________________");

        //  Prueba del método que limpia texto
        System.out.println("\n PRUEBA DE LIMPIAR TEXTO");
        String oracion = "Hola, # muCh0o @ ! Gusto $u/w.u";
        System.out.println("Texto original: " + oracion);
        System.out.println("Texto limpio: " + limpiaTexto(oracion));
        System.out.println("______________________");

        //  Prueba del método que divide el texto en n letras.
        System.out.println("\n PRUEBA DE DIVIDIR TEXTO EN 2");
        System.out.println(separaTexto(texto, 2));
        System.out.println("______________________");

        //  Prueba del método que da el porcentaje de cuánto aparece una letra en el texto.
        System.out.println("\n PRUEBA DE PORCENTAJE DE APARICIÓN DE LETRA");
        Hashtable<String, String> porcentajes = new Hashtable<>(); 
        porcentajes = porcentajeLetra(frecuencias, texto);
        System.out.println(porcentajes);
        System.out.println("______________________");
    } 
}