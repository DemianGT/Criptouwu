import java.util.*;

public class AlgoritmosCifrado extends AlgoritmosBasicos{

    /** Atributos que nos ayudará a dar color al texto en la consola */
    public static final String RED = "\033[31m";
    public static final String BLUE = "\033[34m";
    public static final String CYAN = "\033[36m";
    public static final String PURPLE = "\033[35m";
    public static final String RESET = "\u001B[0m";

	/**
     * Método que cifra un texto mediante el algoritmo de Cesar.
     * @param texto es el texto a cifrar.
     * @param rotacion es el número de veces que se rotará el texto a la derecha.
     * @return el texto cifrado.
     */
	public static String cifradoCesar(String texto, int rotacion) {
        texto = texto.toUpperCase();
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
        texto = texto.toUpperCase();
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

    /**
     * Método que cifra un texto mediante el algoritmo de Vigenere.
     * @param texto es el texto a cifrar.
     * @param clave es la clave para el algoritmo.
     * @return el texto cifrado.
     */
    public static String cifradoVigenere(String texto, String clave){
        String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String textoCifrado = "";
        texto = limpiaTexto(texto);
        clave = limpiaTexto(clave);
        int[] indexTexto = new int[texto.length()];
        int[] indexClave = new int[texto.length()];
        int[] indexCifrado = new int[texto.length()];

        for (int i = 0; i < texto.length(); i++) {
            indexTexto[i] = abecedario.indexOf(texto.substring(i, i + 1));
            indexClave[i] = abecedario.indexOf(clave.substring(i % clave.length(), (i % clave.length()) + 1));
            indexCifrado[i] = (indexTexto[i] + indexClave[i]) % abecedario.length();
            textoCifrado += abecedario.substring(indexCifrado[i], indexCifrado[i] + 1);
        }
        return separaTexto(textoCifrado, clave.length());
    }

    /**
     * Método que descifra un texto mediante el algoritmo de Vigenere.
     * @param texto es el texto a descifrar.
     * @param clave es la clave para el algoritmo.
     * @return el texto descifrado.
     */
    public static String descifradoVigenere(String texto, String clave){
        String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String textoDescifrado = "";
        texto = limpiaTexto(texto);
        clave = limpiaTexto(clave);
        int[] indexTexto = new int[texto.length()];
        int[] indexClave = new int[texto.length()];
        int[] indexCifrado = new int[texto.length()];
        int n;
        for (int i = 0; i < texto.length(); i++) {
            indexTexto[i] = abecedario.indexOf(texto.substring(i, i + 1));
            indexClave[i] = abecedario.indexOf(clave.substring(i % clave.length(), (i % clave.length()) + 1));
            n = (indexTexto[i] - indexClave[i]);
            if(n < 0){
                n += abecedario.length();
            }
            indexCifrado[i] = n % abecedario.length();
            textoDescifrado += abecedario.substring(indexCifrado[i], indexCifrado[i] + 1);
        }
        return textoDescifrado;
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

    public static String abecedarioConClave(String clave){

        String claveSinRepetir = "";

        for (int i = 0; i < clave.length(); i++) {
            char c = clave.charAt(i);
            if(claveSinRepetir.indexOf(c) == -1){
                claveSinRepetir+=c;
            }
        }

        String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(abecedario);
        for (int i = 0; i < claveSinRepetir.length(); i++) {
            char c = claveSinRepetir.charAt(i);
            int indice = sb.indexOf(String.valueOf(c));
            if (indice != -1) {
                sb.deleteCharAt(indice);
            }
        }
        String abecedarioConClave = claveSinRepetir + sb.toString();
        return abecedarioConClave;
    }

    public static String cifradoClave(String texto, String clave) {
        String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String abecedarioConClave = "";
        abecedarioConClave = abecedarioConClave(clave);
        System.out.println("Abecedario con clave: " + abecedarioConClave);
        String resultado = "";
        for (int i = 0; i < texto.length(); i++) {
            int indiceOriginal = abecedario.indexOf(texto.charAt(i));
            resultado+=abecedarioConClave.charAt(indiceOriginal);
        }
        return resultado;
    }

    public static String descifradoClave(String texto, String clave) {
        String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String abecedarioConClave = "";
        abecedarioConClave = abecedarioConClave(clave);
        System.out.println("Abecedario con clave: " + abecedarioConClave);
        String resultado = "";
        for (int i = 0; i < texto.length(); i++) {
            int indiceOriginal = abecedarioConClave.indexOf(texto.charAt(i));
            resultado+=abecedario.charAt(indiceOriginal);
        }
        return resultado;
    }


    // ----------------------- MAIN -----------------------
    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AlgoritmosCifrado ac = new AlgoritmosCifrado();
        int opcion = -1;
        String textoOriginal = "";
        String texto = "";
        String anterior = "";
        String nuevo = "";
        int rotacion = 0;
        String clave = "";
        int a = 0;
        int b = 0;

        while (opcion != 0) {
            System.out.println(BLUE + "\n \u001B[1m---- MENÚ ALGORITMOS DE CIFRADO Y DESCIFRADO----\u001B[0m" + RESET);
            System.out.println(" 1. Cifrado Cesar");
            System.out.println(" 2. Descifrado Cesar\n");
            System.out.println(" 3. Cifrado Afin");
            System.out.println(" 4. Descifrado Afin\n");
            System.out.println(" 5. Cifrado clave");
            System.out.println(" 6. Descifrado clave\n");
            System.out.println(" 7. Cifrado Vigenere");
            System.out.println(" 8. Descifrado Vigenere\n");
            System.out.println(" 0. Salir\n");
            
            System.out.print("\n Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- CIFRAR MEDIANTE CESAR ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto a cifrar: \n");
                    texto = sc.nextLine();
                    System.out.print("\n • Ingrese la rotacion del texto: ");
                    rotacion = sc.nextInt();
                    sc.nextLine();
                    System.out.print("\n\u001B[93m\u001B[1m---- TEXTO CIFRADO ----\u001B[0m\n\n");
                    System.out.println(cifradoCesar(texto, rotacion));
                    break;
                case 2:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- DESCIFRAR MEDIANTE CESAR ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto a descifrar: \n");
                    texto = sc.nextLine();
                    System.out.print("\n • Ingrese la rotacion del texto: ");
                    rotacion = sc.nextInt();
                    sc.nextLine();
                    System.out.print("\n\u001B[93m\u001B[1m---- TEXTO DESCIFRADO ----\u001B[0m\n\n");
                    System.out.println(descifradoCesar(texto, rotacion));
                    break;
                case 3:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- CIFRAR MEDIANTE FUNCION AFIN ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto a cifrar: \n");
                    texto = sc.nextLine();
                    System.out.print("\n • Ingrese el valor que multiplica a \"x\". Ejemplo: Para la función Ax+B escribe el valor de A:\n"); 
                    a = sc.nextInt();
                    sc.nextLine();
                    System.out.print("\n • Ingrese el valor que suma a \"x\". Ejemplo: Para la función Ax+B escribe el valor de B:\n"); 
                    b = sc.nextInt();
                    sc.nextLine();
                    System.out.print("\n\u001B[93m\u001B[1m---- TEXTO CIFRADO ----\u001B[0m\n\n");
                    System.out.println(cifradoAfin(texto, a, b));
                    break;
                case 4:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- DESCIFRAR MEDIANTE FUNCION AFIN ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto a descifrar: \n");
                    texto = sc.nextLine();
                    System.out.print("\n • Ingrese el valor que multiplica a \"x\". Ejemplo: Para la función Ax+B escribe el valor de A:\n"); 
                    a = sc.nextInt();
                    sc.nextLine();
                    System.out.print("\n • Ingrese el valor que suma a \"x\". Ejemplo: Para la función Ax+B escribe el valor de B:\n"); 
                    b = sc.nextInt();
                    sc.nextLine();
                    System.out.print("\n\u001B[93m\u001B[1m---- TEXTO DESCIFRADO ----\u001B[0m\n\n");
                    System.out.println(descifradoAfin(texto, a, b));
                    break;
                case 5:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- CIFRAR MEDIANTE CLAVE ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto a cifrar:\n");
                    texto = sc.nextLine();
                    System.out.print("\n • Ingrese la clave con la que se cifrara: ");
                    clave = sc.nextLine();
                    System.out.println(cifradoClave(texto, clave));
                    break;
                case 6:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- DESCIFRAR MEDIANTE CLAVE ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto a descifrar:\n");
                    texto = sc.nextLine();
                    System.out.print("\n • Ingrese la clave con la que se descifrara: ");
                    clave = sc.nextLine();
                    System.out.println(descifradoClave(texto, clave));
                    break;
                case 7:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- CIFRAR MEDIANTE VIGENERE ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto a cifrar: \n");
                    texto = sc.nextLine();
                    System.out.print("\n • Ingrese una clave: ");
                    clave = sc.nextLine();
                    System.out.print("\n\u001B[93m\u001B[1m---- TEXTO CIFRADO ----\u001B[0m\n\n");
                    System.out.println(PURPLE + " (Separamos el texto en bloques del tamaño de la clave)" + RESET);
                    System.out.println("\n " + cifradoVigenere(texto, clave));
                    break;
                case 8:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- DESCIFRAR MEDIANTE VIGENERE ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto a descifrar: \n");
                    texto = sc.nextLine();
                    System.out.print("\n • Ingrese una clave: ");
                    clave = sc.nextLine();
                    System.out.print("\n\u001B[93m\u001B[1m---- TEXTO DESCIFRADO ----\u001B[0m\n\n");
                    System.out.println(descifradoVigenere(texto, clave));
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
