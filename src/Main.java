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

public class Main {
    public static void main(String[] args) {
        
        // Crea objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Solicita al usuario que ingresar texto
        System.out.print("Ingrese un texto alfanumérico largo: ");
        String textoIngresado = scanner.nextLine();

        // Imprime el texto ingresado
        System.out.println("Texto ingresado: " + textoIngresado);

        // Cierra el scanner
        scanner.close();
    }


}
