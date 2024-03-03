// Ingenieria en Desarrollo de Software
// Programacion de Sistemas
// IDS 6 TM
// 02/03/2024

// Integrantes:
// Mario Alberto Castellanos Martinez
// Hazael Flores Gastelum
// Ricardo Garayzar Ortega
// Andrea Lucero Trasviña
// Miguel Angel Lazcano Ortega

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        //Variable para controlar la ejecucion del programa
        boolean programa = false;

        //Ciclo para mantener la ejecucion del programa
        while(!programa){
            // Crea objeto Scanner para leer la entrada del usuario
            Scanner scanner = new Scanner(System.in);

            // Solicita al usuario que ingresar texto
            System.out.print("Ingrese un texto alfanumérico largo: ");
            String textoIngresado = scanner.nextLine();

            // Expresión regular para identificar números válidos
            String regex = "\\b(\\d+([,.]\\d+([\\w+.]\\d*)?)?)\\b%?";

            // Patrón y matcher para buscar números en el texto
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(textoIngresado);

            // Buscar y mostrar los números encontrados con su tipo
            int i = 1;
            while (matcher.find()) {
                String numero = matcher.group();
                int lineMatch = obtenerLineaCoincidente(textoIngresado, matcher.start());
                String tipo = determinarTipo(numero);
                System.out.printf("\n%d - Línea %d - %s - %s\n", i, lineMatch, numero, tipo);
                i++;
            }

            // Imprime el texto ingresado
            //System.out.println("Texto ingresado: " + textoIngresado);

            //Decision del usuario si salir o no
            System.out.print("\nDeseas ejecutar otra sentencia? 1-SI, 2-NO: ");
            int decision = scanner.nextInt();

            //SI ES IGUAL A 1 EL PROGRAMA SE VA A CERRAR
            if (decision == 1){
                programa = false;
            }else {
                //SALIR
                programa = true;

                // Cierra el scanner
                scanner.close();
            }
        }

    }

    //Metodo para determinar la linea donde se encuentre la coinicidencia
    private static int obtenerLineaCoincidente(String texto, int posicion) {
        int numeroLinea = 1;
        for (int i = 0; i < posicion; i++) {
            if (texto.charAt(i) == '\n') {
                numeroLinea++;
            }
        }
        return numeroLinea;
    }

    // Método para determinar el tipo de número
    private static String determinarTipo(String numero) {
        if (numero.matches("\\b\\d+[,.]\\d+\\b")){
            return "Numero real";
        }

        if (numero.matches("\\b\\d+\\b")){
            return "Numero natural";
        }

        if (numero.matches("\\b\\d+[,.]\\d+E\\d+\\b")){
            return "Numero exponencial";
        }

        if (numero.matches("\\b\\d+\\b%")){
            return "Numero porcentaje";
        }

        return "Numero invalido";
    }

}