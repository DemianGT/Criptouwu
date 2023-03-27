import java.util.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.text.DecimalFormat;
import java.util.Scanner;

public class AlgoritmosBasicos {

    String textoOriginal = "";
    String texto = "";
    ArrayList<String[]> historial = new ArrayList<>();

    /** Atributos que nos ayudará a dar color al texto en la consola */
    public static final String RED = "\033[31m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String CYAN = "\033[36m";
    public static final String RESET = "\u001B[0m";

    /**
     * 0] Preprocesamiento de texto 
     * 1] Pasar de minusculas a mayusculas
     * Método que le quita espacios, dígitos y caracteres especiales a un texto.
     * @param texto es el texto a limpiar.
     * @return el texto limpio convertido a mayúsculas.
     */
    public static String limpiaTexto(String texto) {
        texto = texto.toUpperCase();
        // Usamos Normalizer para quitar acentos.
        String textoLimpio = Normalizer.normalize(texto, Normalizer.Form.NFD);
        textoLimpio = textoLimpio.replaceAll("[^\\p{ASCII}]", "");

        // Quitamos espacios, números y caracteres especiales
        textoLimpio = textoLimpio.replaceAll("\\s", "");
        textoLimpio = textoLimpio.replaceAll("[^A-Z]", "");

        return textoLimpio;
    }

    /**
     * 2] Contar letras y trabajar un poco con esas estadisticas
     * Método que nos da el porcentaje de apariciones de una letra en el texto.
     * @param conteoCaracteres es la tabla hash que contiene las aparciones de una letra en el texto.
     * @param texto es el texto a dividir.
     * @return una tabla hash con la letra y su porcentaje de apariciones.
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

    /**
     * 2] Contar letras y trabajar un poco con esas estadisticas
     * Metodo que ordena una tabla de porcentajes para su lectura
     * @param porcentajes una tabla de porcentajes del tipo {A=100%, B=0%}
     */
    public static void ordenaTablaPorcentajes(Hashtable<String, String> porcentajes){
        ArrayList<String> keys = new ArrayList<String>(porcentajes.keySet());
        ArrayList<String> values = new ArrayList<String>(porcentajes.values());
        Collections.sort(values, (s1, s2) -> {
            double v1 = Double.parseDouble(s1.replace("%", ""));
            double v2 = Double.parseDouble(s2.replace("%", ""));
            return Double.compare(v1, v2);
        });
        Collections.sort(keys, (k1, k2) -> {
            String v1 = porcentajes.get(k1);
            String v2 = porcentajes.get(k2);
            double n1 = Double.parseDouble(v1.replace("%", ""));
            double n2 = Double.parseDouble(v2.replace("%", ""));
            return Double.compare(n1, n2);
        });
        String porcentajesString = "{";
        for(int i = 0; i < keys.size(); i++){
            if(i < keys.size() - 1){
                porcentajesString += keys.get(i) + "=" + values.get(i) + ",";
            } else {
                porcentajesString += keys.get(i) + "=" + values.get(i) + "}";
            }  
        }
        System.out.println(porcentajesString);
    }

    /**
     * 2] Contar letras y trabajar un poco con esas estadisticas
     * Método que cuenta los caracteres de un texto y los pone en una tabla de frecuencias.
     * @param texto es el texto a contar sus caracteres.
     * @return una tabla de frecuencias de las letras.
     */
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
     * 2] Contar letras y trabajar un poco con esas estadísticas.
     * Método que ordena una tabla de frecuencias para su lectura.
     * @param frecuencias una tabla de frecuencias del tipo {A=2, B=1, ..., Z=0}
     */
    public static void ordenaTablaFrecuencias(Hashtable<String, Integer> frecuencias){
        ArrayList<String> keys = new ArrayList<String>(frecuencias.keySet());
        ArrayList<Integer> values = new ArrayList<Integer>(frecuencias.values());
        Collections.sort(values);
        Collections.sort(keys, new Comparator<String>() {
            @Override
            public int compare(String key1, String key2) {
                Integer value1 = frecuencias.get(key1);
                Integer value2 = frecuencias.get(key2);
                return value1.compareTo(value2);
            }
        });
        String frecuenciaString = "{";
        for(int i = 0; i < keys.size(); i++){
            if(i < keys.size() - 1) {
                frecuenciaString += keys.get(i) + "=" + values.get(i) + ",";
            } else {
                frecuenciaString += keys.get(i) + "=" + values.get(i) + "}";
            }   
        }
        System.out.println(frecuenciaString);
    }

    /**
     * 2] Contar letras y trabajar un poco con esas estadísticas.
     * Método que ordena una tabla de porcentajes y frecuencias en una tabla de apariciones de la forma
     * {A = [1-100%], B = [2-30%], ..., Z = [0-0%]}
     * @param porcentajes una tabla de porcentajes del tipo {A=100%, B=0%, ...}
     * @param frecuencias una tabla de frecuencias del tipo {A=1, B=0, ...}
     */
    public static void tablaApariciones(Hashtable<String, Integer> frecuencias, Hashtable<String, String> porcentajes){
        ArrayList<String> keysF = new ArrayList<String>(frecuencias.keySet());
        ArrayList<Integer> valuesF = new ArrayList<Integer>(frecuencias.values());
        Collections.sort(valuesF);
        Collections.sort(keysF, new Comparator<String>() {
            @Override
            public int compare(String key1, String key2) {
                Integer value1 = frecuencias.get(key1);
                Integer value2 = frecuencias.get(key2);
                return value1.compareTo(value2);
            }
        });
        ArrayList<String> keysP = new ArrayList<String>(porcentajes.keySet());
        ArrayList<String> valuesP = new ArrayList<String>(porcentajes.values());
        Collections.sort(valuesP, (s1, s2) -> {
            double v1 = Double.parseDouble(s1.replace("%", ""));
            double v2 = Double.parseDouble(s2.replace("%", ""));
            return Double.compare(v1, v2);
        });
        Collections.sort(keysP, (k1, k2) -> {
            String v1 = porcentajes.get(k1);
            String v2 = porcentajes.get(k2);
            double n1 = Double.parseDouble(v1.replace("%", ""));
            double n2 = Double.parseDouble(v2.replace("%", ""));
            return Double.compare(n1, n2);
        });
        String aparicionesString = "";
        for(int i = 0 ; i < keysF.size(); i++){
                aparicionesString += "\u001B[35m\u001B[1m " + keysF.get(i) + "\u001B[0m = [\u001B[31m\u001B[1m" + valuesF.get(i) + "\u001B[0m - \u001B[32m\u001B[1m" + valuesP.get(i) + "\u001B[0m] \n";
        }
        System.out.println("\n"+aparicionesString+"\n");
    }

    /**
     * 3] Calcular inversos multiplicativos modulo n.
     * Método que obtiene el máximo comun divisor entre dos números.
     * @param a el primer número a sacar el máximo común divisor.
     * @param b el segundo número a sacar el máximo común divisor.
     * @return el máximo común divisor de los dos números.
     */
    public static int mcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return mcd(b, a % b);
        }
    }

    /**
     * 3] Calcular inversos multiplicativos modulo n
     * Método que implementa el algoritmo de euclides.
     * @param a el primer número a sacar el valor.
     * @param b el segundo número a sacar el valor.
     * @return los coeficientes de bezout y el máximo común divisor entre ellos.
     */
    public static int[] euclidesExtendido(int a, int b) {
        if (b == 0) {
            return new int[] { 1, 0, a };
        } else {
            int[] coeficientesAnteriores = euclidesExtendido(b, a % b);
            int x = coeficientesAnteriores[1];
            int y = coeficientesAnteriores[0] - (a / b) * coeficientesAnteriores[1];
            int mcd = coeficientesAnteriores[2];
            return new int[] { x, y, mcd };
        }
    }

    /**
     * 3] Calcular inversos multiplicativos módulo n.
     * Método que calcula el inverso multiplicativo de un número.
     * @param m el número a sacar el inverso.
     * @param n el rango de valores para sacar el inverso de a, para los cifrados se recomienda 26.
     * @return el inverso multiplicativo del valor m.
     */
    public static int inversoMultiplicativo(int m, int n){
        int mcd = mcd(m, n);
        if (mcd != 1) {
            return 0;
        } else {
            int[] coeficientes = euclidesExtendido(m, n);
            int inverso = coeficientes[0];
            while (inverso < 0) {
                inverso += n;
            }
            return inverso;
        }
    }

    /**
     * 4] Sustituir letras por otras letras en el mismo texto.
     * Método que modifica el texto original a trabajar.
     * @param textoOriginal el texto a modificar.
     */
    public void modificaTextoOriginal(String textoOriginal){
        this.texto = textoOriginal;
        this.textoOriginal = textoOriginal;
        System.out.print("\n\u001B[93m\u001B[1m---- TEXTO A TRABAJAR ----\u001B[0m\n");
        System.out.println(colorTexto(texto)+"\n");
    }

    /**
     * 4] Sustituir letras por otras letras en el mismo texto.
     * Método que modifica una letra del texto por otra.
     * @param anterior la letra a remplazar.
     * @param nuevo la letra que remplazará.
     */
    public void modificaLetras(String anterior, String nuevo){
        if(texto.contains(anterior)){
            String[] ultimoPaso = new String[2];
            ultimoPaso[0] = anterior;
            ultimoPaso[1] = nuevo;
            this.texto = this.texto.replace(anterior, nuevo);
            System.out.println("\n Letras modificadas exitosamente (/^▽^)/\n");
            System.out.print("\n\u001B[93m\u001B[1m---- TEXTO A TRABAJAR ----\u001B[0m\n\n");
            System.out.print(colorTexto(texto)+"\n");
            historial.add(ultimoPaso);
            System.out.print("\n\u001B[93m\u001B[1m---- HISTORIAL DE CAMBIOS ----\u001B[0m\n\n");
            imprimeHistorial();
        } else {
            System.out.println("\n No existe esa letra en el texto (╥_╥), asegurate de estar escribiéndola bien.\n");
        }      
    }

    /**
     * 4] Sustituir letras por otras letras en el mismo texto.
     * Método que imprime el historial de cambios.
     */
    public void imprimeHistorial(){
        for(String[] pasos: historial){
            System.out.println(" [" + pasos[0] + "->" + pasos[1] + "]");
        }
    }

    /**
     * 4] Sustituir letras por otras letras en el mismo texto.
     * Método que colorea las letras minúsculas de color verde y las mayúsculas de color blanco
     * @param texto el texto a colorear.
     * @return el texto coloreado.
     */
    public static String colorTexto(String texto) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (Character.isLowerCase(c)) {
                sb.append("\u001B[38;5;82m\u001B[1m").append(c).append("\u001B[0m");
            } else {
                sb.append("\u001B[1m").append(c).append("\u001B[0m");
            }
        }
        return sb.toString();
    }

    /**
     * 4] Sustituir letras por otras letras en el mismo texto.
     * Método que regresa al último cambio hecho sobre el texto.
     */
    public void ultimoPaso(){
        if(historial.size()!=0){
            String[] ultimoPaso = historial.get(historial.size()-1);
            texto=this.texto.replace(ultimoPaso[1], ultimoPaso[0]);
            System.out.println("\n Último paso revertido exitosamente (/^▽^)/\n");
            historial.remove(historial.size() - 1);
            System.out.print("\n\u001B[93m\u001B[1m---- TEXTO A TRABAJAR ----\u001B[0m\n\n");
            System.out.print(colorTexto(texto) + "\n");
            System.out.print("\n\u001B[93m\u001B[1m---- HISTORIAL DE CAMBIOS ----\u001B[0m\n\n");
            imprimeHistorial();
        } else {
            System.out.println("\nYa estas en el último paso ´･ᴗ･`\n");
            System.out.print("\n\u001B[93m\u001B[1m---- TEXTO A TRABAJAR ----\u001B[0m\n\n");
            System.out.print(colorTexto(texto) + "\n");
            System.out.print("\n\u001B[93m\u001B[1m---- HISTORIAL DE CAMBIOS ----\u001B[0m\n\n");
            imprimeHistorial();
        }   
    }

    /**
     * 4] Sustituir letras por otras letras en el mismo texto.
     * Método que regresa al texto a su estado sin cambios.
     */
    public void regresaAlTextoOriginal(){
        this.texto = this.textoOriginal;
        historial.clear();
        System.out.println("\nTexto Restaurado ´･ᴗ･`\n");
        System.out.print("\n\u001B[93m\u001B[1m---- TEXTO A TRABAJAR ----\u001B[0m\n\n");    
        System.out.print(colorTexto(texto)+"\n");
        System.out.print("\n\u001B[93m\u001B[1m---- HISTORIAL DE CAMBIOS ----\u001B[0m\n\n");
        imprimeHistorial();
    }


    /**
     * 5] Separar un texto en bloques de r columnas.
     * Método que divide en bloques un texto.
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
     * 6] Funcion Afin inversa.
     * Método que devuelve un numero congruente positivo con el modulo 26.
     * @param n el número a devolver la congruencia, por ejemplo: -7 es congruente con 19, devolvera 19 si n=-7.
     * @return el número congruente de hacer la operación n mod 26.
     */
    public static int congruente(int n){
        int nCongruente = n;
        while(nCongruente < 0){
            nCongruente += 26;
        }
        return nCongruente;
    }

    /**
     * 6] Función Afin inversa.
     * Método que obtiene el valor de la función afin inversa de una función del tipo 11x+1, 
     * su funcion afin seria x = inverso*(y-1)
     * @param inverso el inverso multiplicativo del valor m, en la funcion anterior 11 es m
     * @param y el valor a despejar de la ecuacion, en este caso para descifrar "y" sería la 
     * posición de la letra en el alfabeto.
     * @param a el aditivo de la función, en la función anterior el valor sería 1 sin signo
     * ya que el valor negativo lo controlamos en la inversa.
     * @return el valor al que equivale después de pasar por la función afin inversa.
     */
    public static int afinInverso(int inverso, int y, int a){
        return (inverso * y) + congruente(inverso * (-a));
    }
    
    public static String funcionAfinInversa(int m, int a){
    	String funcionInversa = inversoMultiplicativo(m,26)+"y-"+inversoMultiplicativo(m,26)*a;
    	return funcionInversa;
    }


    // ----------------------- MAIN -----------------------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AlgoritmosBasicos ab = new AlgoritmosBasicos();
        int opcion = -1;
        String textoOriginal = "";
        String texto = "";
        String anterior = "";
        String nuevo = "";
        int m = 0;
        int a = 0;
        while (opcion != 0) {
            System.out.println(BLUE + "\n\u001B[1m---- MENÚ ALGORITMOS BÁSICOS ----\u001B[0m" + RESET);
            System.out.println(" 1. Ingresa texto original");
            System.out.println(" 2. Modificar letras del texto original");
            System.out.println(" 3. Revertir último paso");
            System.out.println(" 4. Regresar al texto original\n");

            System.out.println(" 5. Inverso multiplicativo de X mod Y");
            System.out.println(" 6. Valor de la función afin inversa\n");
            System.out.println(" 7. Función afin inversa\n");
            

            System.out.println(" 8. Limpia texto");
            System.out.println(" 9. Separa texto");
            System.out.println(" 10. Conteo y porcentajes de apariciones");
            System.out.println(" 0. Salir");
            
            System.out.print(CYAN + "\n Elige una opción: " + RESET);
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    System.out.print("\n\u001B[31m\u001B[1m---- INGRESAR TEXTO ORIGINAL ----\u001B[0m\n\n");
                    System.out.print(" • Ingresa el texto original: ");
                    textoOriginal = sc.nextLine();
                    ab.modificaTextoOriginal(textoOriginal);
                    break;
                case 2:
                    System.out.print("\n\n\u001B[31m\u001B[1m---- MODIFICAR LETRAS DEL TEXTO ORIGINAL----\u001B[0m\n\n");
                    System.out.print(" • Ingresa el caracter anterior: ");
                    anterior = sc.nextLine();
                    System.out.print(" • Ingresa el caracter nuevo: ");
                    nuevo = sc.nextLine();
                    ab.modificaLetras(anterior, nuevo);
                    break;
                case 3:
                    System.out.print("\n\u001B[31m\u001B[1m---- REVERTIR ULTIMO PASO ----\u001B[0m\n\n");
                    ab.ultimoPaso();
                    break;
                case 4:
                    System.out.print("\n\u001B[31m\u001B[1m---- REGRESAR AL TEXTO ORIGINAL ----\u001B[0m\n\n");
                    ab.regresaAlTextoOriginal();
                    break;
                case 5:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- INVERSO MULTIPLICATIVO DE X MOD Y ----\u001B[0m\n\n");
                    System.out.print(" • Ingresa el valor para X: ");
                    int x = sc.nextInt();
                    System.out.print(" • Ingresa el valor para Y: ");
                    int y = sc.nextInt();
                    int inversoMul = inversoMultiplicativo(x,y);
                    System.out.println("\n • El inverso multiplicativo de " + x + " mod " + y + " es: " + inversoMul);
                    break;
                case 6:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- VALOR DE LA FUNCIÓN AFIN INVERSA ----\u001B[0m\n\n");
                    System.out.println(YELLOW + " * INSTRUCCIONES: " + RESET + "La función es de tipo Ax+B, la función afin sería x = inverso*(y-B), vamos a obtener el valor de x.\n");
                    System.out.print(" • Ingresa el valor para A: ");
                    int a1 = sc.nextInt();
                    System.out.print(" • Ingresa el valor para B: ");
                    int b1 = sc.nextInt();
                    System.out.print(" • Ingresa el valor para Y (es la posición de la letra en el alfabeto): ");
                    int y1 = sc.nextInt();
                    int afinInverso = afinInverso(a1,y1,b1);
                    System.out.println("\n • El valor de x es: " + afinInverso);
                    break;
                    
                case 7:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- FUNCION AFIN INVERSA ----\u001B[0m\n\n");
                    System.out.println(YELLOW + " * INSTRUCCIONES: " + RESET + "La función es de tipo Ax+B, la función afin sería x = inverso*(y-B), vamos a obtener la funcion inversa inverso*(y-B).\n");
                    System.out.print(" • Ingresa el valor para A: ");
                    m = sc.nextInt();
                    System.out.print(" • Ingresa el valor para B: ");
                    a = sc.nextInt();
                    System.out.println("\n • La funcion afin inversa de "+m+"x+"+a+" es: "+funcionAfinInversa(m,a));
                
                    break;
                case 8:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- LIMPIA TEXTO ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto a limpiar: ");
                    texto = sc.nextLine();
                    String textoLimpio = limpiaTexto(texto);
                    System.out.print("\n\u001B[93m\u001B[1m---- TEXTO LIMPIO ----\u001B[0m\n\n");
                    System.out.println(textoLimpio);
                    break;
                case 9:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- SEPARA TEXTO ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto a separar: ");
                    texto = sc.nextLine();
                    System.out.print("\n • Ingrese el número de letras por separación: ");
                    int numLetras = sc.nextInt();
                    sc.nextLine(); // Consumir el salto de línea
                    String textoSeparado = separaTexto(texto, numLetras);
                    System.out.print("\n\u001B[93m\u001B[1m---- TEXTO SEPARADO ----\u001B[0m\n\n");
                    System.out.println(textoSeparado);
                    break;
                case 10:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- CONTEO Y PORCENTAJES DE APARICIONES DE LETRAS ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto para calcular el porcentaje de apariciones de letra: ");
                    texto = sc.nextLine();
                    Hashtable<String, Integer> conteoCaracteres = conteoDeCaracteres(texto);
                    Hashtable<String, String> porcentajes = porcentajeLetra(conteoCaracteres, texto);
                    tablaApariciones(conteoCaracteres,porcentajes);
                    break;
                case 0:
                    System.out.println(YELLOW + "\n  Adiós ヾ(＾∇＾)" + RESET);
                    break;
                default:
                    System.out.println(RED + "\n Opción inválida (╥_╥), intenta de nuevo." + RESET);
                    break;
            }
            System.out.println();
        }
        sc.close();
    }
}
