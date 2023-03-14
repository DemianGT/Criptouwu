import java.util.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.text.DecimalFormat;
import java.util.Scanner;
public class AlgoritmosBasicos{

    String textoOriginal = "";
    String texto = "";
    ArrayList<String[]> historial = new ArrayList<>();

    /**
     * 0] Preprocesamiento de texto 
     * 1] Pasar de minusculas a mayusculas
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
     * 2] Contar letras y trabajar un poco con esas estadisticas
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
        for(int i=0;i<keys.size();i++){
            if(i<keys.size()-1){
                porcentajesString+=keys.get(i)+"="+values.get(i)+",";
            }else{
                porcentajesString+=keys.get(i)+"="+values.get(i)+"}";
            }
            
        }
        System.out.println(porcentajesString);
    }


    /**
     * 2] Contar letras y trabajar un poco con esas estadisticas
     * Metodo que cuenta los caracteres de un texto y los pone en una tabla de frecuencias
     * @param texto el texto a contar caracteres
     * @return una tabla de frecuencias
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
        for(int i=0;i<keys.size();i++){
            if(i<keys.size()-1){
                frecuenciaString+=keys.get(i)+"="+values.get(i)+",";
            }else{
                frecuenciaString+=keys.get(i)+"="+values.get(i)+"}";
            }
            
        }
        System.out.println(frecuenciaString);
    }

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
        for(int i=0;i<keysF.size();i++){
                aparicionesString+="\u001B[35m\u001B[1m"+keysF.get(i)+"\u001B[0m = [\u001B[31m\u001B[1m"+valuesF.get(i)+"\u001B[0m-\u001B[32m\u001B[1m"+valuesP.get(i)+"\u001B[0m] \n";
        }
        System.out.println("\n"+aparicionesString+"\n");
    }

    /**
     * 3] Calcular inversos multiplicativos modulo n
     */
    public static int mcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return mcd(b, a % b);
        }
    }
    
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

    public static int inversoMultiplicativo(int a, int n){
        int mcd = mcd(a, n);
        if (mcd != 1) {
            System.out.println("El inverso modular de " + a + " módulo " + n + " no existe.");
            return 0;
        } else {
            int[] coeficientes = euclidesExtendido(a, n);
            int inverso = coeficientes[0];
            while (inverso < 0) {
                inverso += n;
            }
            System.out.println("El inverso modular de " + a + " módulo " + n + " es " + inverso + ".");
            return inverso;
        }
    }

    public static int congruente(int n){
        int nCongruente = n;
        while(nCongruente<0){
            nCongruente+=26;
        }
        System.out.println("This"+nCongruente);
        return nCongruente;
    }

    // Método que descifra el texto cifrado utilizando el cifrado Afín
    public static String cifrar(String texto, int m, int a) {
        String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String resultado = "";

        
        for(int i=0; i<texto.length(); i++){
            int y = abecedario.indexOf(texto.charAt(i));
            int afin = (m*y)+a;
            int cifrado = afin % 26 ;
            resultado+=abecedario.charAt(cifrado);
        }

        return resultado;
    }

    // Método que descifra el texto cifrado utilizando el cifrado Afín
    public static String descifrar(String texto, int m, int a) {
        String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String resultado = "";

        int inverso = inversoMultiplicativo(m, 26);
        
        for(int i=0; i<texto.length(); i++){
            int y = abecedario.indexOf(texto.charAt(i));
            int afinInverso = (inverso*y)+congruente(inverso*(-a));
            int descifrado = afinInverso % 26 ;
            resultado+=abecedario.charAt(descifrado);
        }

        return resultado;
    }

    

    /**
     * 4] Sustituir letras por otras letras en el mismo texto
     * Metodo que modifica el texto original a trabajar
     * @param textoOriginal el texto a modificar
     */
    public void modificaTextoOriginal(String textoOriginal){
        this.texto = textoOriginal;
        this.textoOriginal = textoOriginal;
        System.out.println("\n---- TEXTO A TRABAJAR ----\n");
        System.out.println(colorTexto(texto)+"\n");
    }
    /**
     * 4] Sustituir letras por otras letras en el mismo texto
     * Metodo que modifica una letra del texto por otra
     * @param anterior la letra a remplazar
     * @param nuevo la letra que remplazara
     */
    public void modificaLetras(String anterior, String nuevo){
        if(texto.contains(anterior)){
            String[] ultimoPaso = new String[2];
            ultimoPaso[0] = anterior;
            ultimoPaso[1] = nuevo;
            this.texto=this.texto.replace(anterior, nuevo);
            System.out.println("\nLetras modificadas exitosamente uwu.\n");
            System.out.println("\n---- TEXTO A TRABAJAR ----\n");
            System.out.print(colorTexto(texto)+"\n");
            historial.add(ultimoPaso);
            System.out.println("\n---- HISTORIAL DE CAMBIOS ----\n");
            imprimeHistorial();
        }else{
            System.out.println("\nNo existe esa letra en el texto, asegurate de estar escribiendola bien uwu\n");
        }
        
        
    }
    /**
     * 4] Sustituir letras por otras letras en el mismo texto
     * Metodo que imprime el historial de cambios
     */
    public void imprimeHistorial(){
        for(String[] pasos: historial){
            System.out.println("["+pasos[0]+"->"+pasos[1]+"]");
        }
    }
    /**
     * 4] Sustituir letras por otras letras en el mismo texto
     * Metodo que colorea las letras minusculas de color verde y las mayusculas de color blanco
     * @param texto el texto a colorear
     * @return el texto coloreado
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
     * 4] Sustituir letras por otras letras en el mismo texto
     * Metodo que regresa al ultimo cambio hecho sobre el texto
     */
    public void ultimoPaso(){
        if(historial.size()!=0){
            String[] ultimoPaso = historial.get(historial.size()-1);
            texto=this.texto.replace(ultimoPaso[1], ultimoPaso[0]);
            System.out.println("\nÚltimo paso revertido exitosamente uwu.\n");
            historial.remove(historial.size()-1);
            System.out.println("\n---- TEXTO A TRABAJAR ----\n");
            System.out.print(colorTexto(texto)+"\n");
            System.out.println("\n---- HISTORIAL DE CAMBIOS ----\n");
            imprimeHistorial();
        }else{
            System.out.println("\nYa estas en el ultimo paso uwu\n");
            System.out.println("\n---- TEXTO A TRABAJAR ----\n");
            System.out.print(colorTexto(texto)+"\n");
            System.out.println("\n---- HISTORIAL DE CAMBIOS ----\n");
            imprimeHistorial();
        }
        
    }
    /**
     * 4] Sustituir letras por otras letras en el mismo texto
     * Metodo que regresa al texto a su estado sin cambios
     */
    public void regresaAlTextoOriginal(){
        this.texto=this.textoOriginal;
        historial.clear();
        System.out.println("\nTexto Restaurado uwu\n");

        System.out.println("\n---- TEXTO A TRABAJAR ----\n");
        System.out.print(colorTexto(texto)+"\n");
        System.out.println("\n---- HISTORIAL DE CAMBIOS ----\n");
        imprimeHistorial();
    }


    /**
     * 5] Separar un texto en bloques de r columnas
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AlgoritmosBasicos ab = new AlgoritmosBasicos();

        int opcion = 0;
        String textoOriginal = "";
        String texto = "";
        String anterior = "";
        String nuevo = "";

        int m = 0;
        int a = 0;

        while (opcion != 20) {
            System.out.println("\u001B[1m---- MENÚ ALGORITMOS BÁSICOS ----\u001B[0m");
            System.out.println("1. Modificar texto original");
            System.out.println("2. Modificar letras");
            System.out.println("3. Último paso");
            System.out.println("4. Regresar al texto original\n");

            System.out.println("5. Limpia texto");
            System.out.println("6. Separa texto");
            System.out.println("7. Conteo y porcentajes de apariciones");

            System.out.println("8 Cifrar mediante una funcion afin");
            System.out.println("9 Descifrar mediante una funcion afin");
            
            System.out.print("\nElige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    System.out.print("\n\u001B[31m\u001B[1m---- MODIFICAR TEXTO ORIGINAL ----\u001B[0m\n\n");
                    System.out.print("Ingresa el texto original: ");
                    textoOriginal = sc.nextLine();
                    ab.modificaTextoOriginal(textoOriginal);
                    
                    break;
                case 2:
                    System.out.print("\n\u001B[38;5;166m\u001B[1m---- MODIFICAR LETRAS ----\u001B[0m\n\n");
                    System.out.print("Ingresa el caracter anterior: ");
                    anterior = sc.nextLine();
                    System.out.print("Ingresa el caracter nuevo: ");
                    nuevo = sc.nextLine();
                    ab.modificaLetras(anterior, nuevo);
                    
                    break;
                case 3:
                    System.out.print("\n\u001B[93m\u001B[1m---- ULTIMO PASO ----\u001B[0m\n\n");
                    ab.ultimoPaso();
                    break;
                case 4:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- REGRESAR AL TEXTO ORIGINAL ----\u001B[0m\n\n");
                    ab.regresaAlTextoOriginal();
                    break;
                case 5:
                    System.out.print("\n\u001B[32m\u001B[1m---- LIMPIA TEXTO ----\u001B[0m\n\n");
                    System.out.print("Ingrese el texto a limpiar: ");
                    texto = sc.nextLine();
                    String textoLimpio = limpiaTexto(texto);
                    System.out.println("Texto limpio: " + textoLimpio);
                    break;
                case 6:
                    System.out.print("\n\u001B[38;5;99m \u001B[1m---- SEPARA TEXTO ----\u001B[0m\n\n");
                    System.out.print("Ingrese el texto a separar: ");
                    texto = sc.nextLine();
                    System.out.print("Ingrese el número de letras por separación: ");
                    int numLetras = sc.nextInt();
                    sc.nextLine(); // Consumir el salto de línea
                    String textoSeparado = separaTexto(texto, numLetras);
                    System.out.println("Texto separado: " + textoSeparado);
                    break;
                case 7:
                    System.out.println("\n\u001B[35m\u001B[1m---- CONTEO Y PORCENTAJES DE APARICIONES DE LETRAS ----\u001B[0m\n");
                    System.out.print("Ingrese el texto para calcular el porcentaje de apariciones de letra: ");
                    texto = sc.nextLine();
                    Hashtable<String, Integer> conteoCaracteres = conteoDeCaracteres(texto);
                    Hashtable<String, String> porcentajes = porcentajeLetra(conteoCaracteres, texto);
                    
                    tablaApariciones(conteoCaracteres,porcentajes);
                    break;
                case 8:
                    System.out.println("\n\u001B[35m\u001B[1m---- CIFRAR MEDIANTE FUNCION AFIN ----\u001B[0m\n");
                    System.out.print("Ingrese el texto a cifrar: \n");
                    texto = sc.nextLine();
                    System.out.print("Ingrese el valor que multiplica a x Ejemplo: Para m * x + a escribe m:\n");
                    m = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Ingrese el valor que suma a x Ejemplo: Para m * x + a escribe a:\n");
                    a = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.println(cifrar(texto, m, a));
                    break;
                case 9:
                    System.out.println("\n\u001B[35m\u001B[1m---- DESCIFRAR MEDIANTE FUNCION AFIN ----\u001B[0m\n");
                    System.out.print("Ingrese el texto a descifrar:\n");
                    texto = sc.nextLine();
                    System.out.print("Ingrese el valor que multiplica a x Ejemplo: Para m * x + a escribe m:\n");
                    m = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Ingrese el valor que suma a x Ejemplo: Para m * x + a escribe a:\n");
                    a = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.println(descifrar(texto, m, a));
                    break;
                
                case 10:
                    System.out.println("Adios uwu");
                    break;
                default:
                    System.out.println("Opción inválida, intenta de nuevo.");
                    break;
            }

            System.out.println();
        }

        sc.close();
    }
}
