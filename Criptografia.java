import java.util.ArrayList;
import java.util.Hashtable;

public class Criptografia {

    private static Hashtable<String, Integer> conteoDeCaracteres(String texto){
        Hashtable<String, Integer> frecuencias = new Hashtable<>();  
        for(int i=0; i<texto.length(); i++){
            String letra =""+texto.charAt(i);
            if(texto.charAt(i)+" " ==" "){
                frecuencias.put(" ", 1);
            }else{
                if(frecuencias.containsKey(letra)==true){
                    frecuencias.put(letra, frecuencias.get(letra)+1);
                }else{
                    frecuencias.put(letra, 1);
                }
                
            }
        }
        return frecuencias;
        
    }

    private static void porLetra(Hashtable<String, Integer> frecuencias){
        
    }

    public static void main(String[] args){
        Hashtable<String, Integer> frecuencias = new Hashtable<>(); 
        frecuencias = conteoDeCaracteres("hola mucho gusto uwu");
        System.out.println(frecuencias);
        
    }
    
}