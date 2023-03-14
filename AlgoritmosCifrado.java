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

    public static String cifradoAfin(String texto, int m, int a) {
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
    public static String descifradoAfin(String texto, int m, int a) {
        String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String resultado = "";

        int inverso = inversoMultiplicativo(m, 26);
        
        for(int i=0; i<texto.length(); i++){
            int y = abecedario.indexOf(texto.charAt(i));
            int afinInverso = afinInverso(inverso, y, a);
            int descifrado = afinInverso % 26 ;
            resultado+=abecedario.charAt(descifrado);
        }

        return resultado;
    }


    public static void main(String[] args){

        /*System.out.println("\nTEXTO:");
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
        System.out.println("______________________");*/

        Scanner sc = new Scanner(System.in);
        AlgoritmosCifrado ac = new AlgoritmosCifrado();

        int opcion = 0;
        String textoOriginal = "";
        String texto = "";
        String anterior = "";
        String nuevo = "";
        int rotacion = 0;
        int m = 0;
        int a = 0;

        while (opcion != 20) {
            System.out.println("\u001B[1m---- MENÚ ALGORITMOS DE CIFRADO ----\u001B[0m");
            System.out.println("1. Cifrado Cesar");
            System.out.println("2. Descifrado Cesar");
            System.out.println("3. Cifrado Afin");
            System.out.println("4. Descifrado Afin\n");
            
            System.out.print("\nElige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    System.out.println("\n\u001B[31m\u001B[1m---- CIFRAR MEDIANTE CESAR ----\u001B[0m\n");
                    System.out.print("Ingrese el texto a cifrar: \n");
                    texto = sc.nextLine();
                    System.out.print("Ingrese la rotacion del texto: \n");
                    rotacion = sc.nextInt();
                    sc.nextLine();
                    System.out.println(cifradoCesar(texto, rotacion));
                    break;
                case 2:
                    System.out.println("\n\u001B[31m\u001B[1m---- DESCIFRAR MEDIANTE CESAR ----\u001B[0m\n");
                    System.out.print("Ingrese el texto a descifrar: \n");
                    texto = sc.nextLine();
                    System.out.print("Ingrese la rotacion del texto: \n");
                    rotacion = sc.nextInt();
                    sc.nextLine();
                    System.out.println(descifradoCesar(texto, rotacion));
                    break;
                case 3:
                    System.out.println("\n\u001B[93m\u001B[1m---- CIFRAR MEDIANTE FUNCION AFIN ----\u001B[0m\n");
                    System.out.print("Ingrese el texto a cifrar: \n");
                    texto = sc.nextLine();
                    System.out.print("Ingrese el valor que multiplica a x Ejemplo: Para m * x + a escribe m:\n");
                    m = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Ingrese el valor que suma a x Ejemplo: Para m * x + a escribe a:\n");
                    a = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.println(cifradoAfin(texto, m, a));
                    break;
                case 4:
                    System.out.println("\n\u001B[93m\u001B[1m---- DESCIFRAR MEDIANTE FUNCION AFIN ----\u001B[0m\n");
                    System.out.print("Ingrese el texto a descifrar:\n");
                    texto = sc.nextLine();
                    System.out.print("Ingrese el valor que multiplica a x Ejemplo: Para m * x + a escribe m:\n");
                    m = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Ingrese el valor que suma a x Ejemplo: Para m * x + a escribe a:\n");
                    a = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.println(descifradoAfin(texto, m, a));
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