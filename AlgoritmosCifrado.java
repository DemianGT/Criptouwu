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

    /**
     * Método que crea la la matríz de 5x5 para el cifrado de Playfair.
     * NOTA: Para este algoritmo suponemos que la letra "X" y "W" están en la misma casilla.
     * por lo que W será tomada como X.
     * @param clave es la clave con la cual empezaremos a llenar la matriz de caracteres.
     * @return matriz cuyos elementos son los caracteres de la clave más los restantes del alfabeto.
     */
    public static char[][] generaMatriz(String clave) {
        clave = limpiaTexto(clave);
        // Llenamos la matriz comenzando con la clave.
        char[][] matriz = new char[5][5];
        int k = 0;
        boolean[] usedChars = new boolean[26];
        for (int i = 0; i < clave.length(); i++) {
            char c = clave.charAt(i);
            if (!usedChars[c - 'A']) {
                matriz[k / 5][k % 5] = c;
                usedChars[c - 'A'] = true;
                k++;
            }
        }
        // Llenamos la matriz con las letras restantes.
        for (int i = 0; i < 26; i++) {
            char c = (char) (i + 'A');
            if (c == 'W') {
                continue;
            }
            if (!usedChars[c - 'A']) {
                matriz[k / 5][k % 5] = c;
                usedChars[c - 'A'] = true;
                k++;
            }
        }
        return matriz;
    }

    /**
     * Método que cifra un texto mediante el algoritmo de Playfair.
     * @param texto es el texto a cifrar.
     * @param clave es la clave para el algoritmo.
     * @return el texto cifrado.
     */
    public static String cifradoPlayfair(String texto, String clave) {
        texto = limpiaTexto(texto);
        char[][] matriz = generaMatriz(clave);
        // Agregar una X al final si la longitud del texto es impar
        if (texto.length() % 2 != 0) {
            texto += "X";
        }
        // Cifrar el texto.
        StringBuilder textoCifrado = new StringBuilder();
        for (int i = 0; i < texto.length(); i += 2) {
            char c1 = texto.charAt(i);
            char c2 = texto.charAt(i + 1);

            int[] c1Pos = encuentraPosicion(c1, matriz);
            int[] c2Pos = encuentraPosicion(c2, matriz);

            // CASO 1: Las dos letras están en la misma fila de la matriz.
            if (c1Pos[0] == c2Pos[0]) {
                c1Pos[1] = (c1Pos[1] + 1) % 5;
                c2Pos[1] = (c2Pos[1] + 1) % 5;
            }
            // CASO 2: Las dos letras están en la misma columna de la matriz.
            else if (c1Pos[1] == c2Pos[1]) {
                c1Pos[0] = (c1Pos[0] + 1) % 5;
                c2Pos[0] = (c2Pos[0] + 1) % 5;
            }
            // CASO 3: Las dos letras están en difernte fila y renglón.
            else {
                int temp = c1Pos[1];
                c1Pos[1] = c2Pos[1];
                c2Pos[1] = temp;
            }
            textoCifrado.append(matriz[c1Pos[0]][c1Pos[1]]);
            textoCifrado.append(matriz[c2Pos[0]][c2Pos[1]]);
        }

        // Si hay una 'X' en la cadena la cambia a 'W'
        String cifradoFinal = "";
        for (int k = 0; k < textoCifrado.length() ;k++ ) {
            char aux = textoCifrado.charAt(k);
            if(aux == 'X'){
                aux = 'W';
            }
            cifradoFinal += aux;  
        }
        return cifradoFinal;
    }

    /**
     * Método auxiliar que dado un caracter busca su posición dentro de la matriz de Playfair.
     * @param c es el caracter a buscar.
     * @param matriz es la matriz de Playfair.
     * @return un arreglo donde en el indice 0 tiene la posición de X y en el indice 1 la de Y.
     */
    public static int[] encuentraPosicion(char c, char[][] matriz) {
        int[] pos = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matriz[i][j] == c) {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return pos;
    }

    /**
     * Método auxiliar que imprime una matriz bidimensional de carateres.
     * @param matriz es la matriz a imprimir.
     * @return los elementos de la matriz bidimensional.
     */
    public static void imprimeMatriz(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(" " + matriz[i][j] + " ");
            }
            System.out.println();
        }
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
        texto = limpiaTexto(texto);
        clave = limpiaTexto(clave);
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
        texto = limpiaTexto(texto);
        clave = limpiaTexto(clave);
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
            System.out.println(" 9. Cifrado Playfair");
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
                case 9:
                    System.out.print("\n\u001B[38;5;82m\u001B[1m---- CIFRAR MEDIANTE PLAYFAIR ----\u001B[0m\n\n");
                    System.out.print(" • Ingrese el texto a cifrar: \n");
                    texto = sc.nextLine();
                    System.out.print("\n • Ingrese una clave: ");
                    clave = sc.nextLine();
                    System.out.print("\n\u001B[93m\u001B[1m---- MATRIZ ----\u001B[0m\n\n");
                    char[][] matriz = generaMatriz(clave);
                    imprimeMatriz(matriz);
                    System.out.print("\n\u001B[93m\u001B[1m---- TEXTO CIFRADO ----\u001B[0m\n\n");
                    System.out.println(PURPLE + " (Tomamos que las letras \"W\" y \"X\" están en la misma casilla, por lo que una letra W será tomada como X)." + RESET);
                    System.out.println("\n " + cifradoPlayfair(texto, clave));
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
