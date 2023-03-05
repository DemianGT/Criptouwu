import java.util.*;

public class AlgoritmosCifrado extends AlgoritmosBasicos{


	/**
     * Método que cifra un texto mediante el algoritmo de Cesar.
     * @param texto es el texto a cifrar.
     * @param rotacion es el número de veces que se rotará el texto a la derecha.
     * @return el texto cifrado.
     */
	public static String cifradoCesar(String texto, int rotacion) {
        StringBuilder textoCifrado = new StringBuilder();
        rotacion = rotacion % 26;
        texto = limpiaTexto(texto);
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z') {
                if ((texto.charAt(i) + rotacion) > 'Z') {
                    textoCifrado.append((char) (texto.charAt(i) + rotacion - 26));
                } else {
                    textoCifrado.append((char) (texto.charAt(i) + rotacion));
                }
            } 
        }
        return textoCifrado.toString();
    }

    /**
     * Método que descifra un texto mediante el algoritmo de Cesar.
     * @param texto es el texto a descifrar.
     * @param rotacion es el número de veces que se rotará el texto a la izquierda.
     * @return el texto descifrado.
     */
    public static String descifradoCesar(String texto, int rotacion) {
        StringBuilder textoDescifrado = new StringBuilder();
        rotacion = rotacion % 26;
        texto = limpiaTexto(texto);
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z') {
                if ((texto.charAt(i) - rotacion) < 'A') {
                    textoDescifrado.append((char) (texto.charAt(i) - rotacion + 26));
                } else {
                    textoDescifrado.append((char) (texto.charAt(i) - rotacion));
                }
            } 
        }
        return textoDescifrado.toString();
    }


    public static void main(String[] args){

        System.out.println("\nTEXTO:");
        String texto = "hola mucho gusto uwu";
        System.out.println(texto);
        System.out.println("______________________");

        //  Prueba del método de Algoritmo de Cesar: CIFRADO
        System.out.println("\n PRUEBA ALGORITMO DE CESAR \"CIFRADO\"");
        System.out.println(cifradoCesar(texto, 4));
        System.out.println("______________________");

        //  Prueba del método de Algoritmo de Cesar: DESCIFRADO
        System.out.println("\n PRUEBA ALGORITMO DE CESAR \"DESCIFRADO\"");
        System.out.println(descifradoCesar(cifradoCesar(texto, 4), 4));
        System.out.println("______________________");

    } 
	
}