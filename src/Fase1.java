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

public class Fase1 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Solicita al usuario que ingresar texto
            System.out.print("Ingrese un texto alfanumérico largo: ");
            StringBuilder textoIngresado = new StringBuilder();

            //Lee lineas de texto hasta que aparezca una en blanco
            String linea;
            while (!(linea = scanner.nextLine()).isEmpty()) {
                textoIngresado.append(linea).append("\n");
            }

            // Expresión regular para identificar números válidos
            String regex = "\\bSELECT\\b.*\\bFROM\\b";

            // Patrón y matcher para buscar números en el texto
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(textoIngresado);

            //Buscar y mostrar los números encontrados con su tipo
            int i = 1;
            System.out.printf("| %-4s | %-10s | %-15s | %-10s%n", "No°", "No Fila", "Cadena", "Tipo");

            while (matcher.find()) {
                String numero = matcher.group();
                int lineMatch = obtenerLineaCoincidente(textoIngresado.toString(), matcher.start());
                String tipo = determinarTipo(numero);

                System.out.printf("| %-4d | %-10d | %-15s | %-10s%n", i, lineMatch, numero, tipo);

                i++;
            }

            //Decision del usuario si salir o no
            System.out.print("\nDeseas ejecutar otra sentencia? 1-SI, 2-NO: ");
            int decision = scanner.nextInt();
            scanner.nextLine();

            //Si es igual a 2, salir del bucle infinito
            if (decision == 2) {
                break;
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

    //Método para determinar el tipo de número
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