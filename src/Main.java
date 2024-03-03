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

        //Variable para controlar la ejecucion del programa
        boolean programa = false;

        //Ciclo para mantener la ejecucion del programa
        while(!programa){
            // Crea objeto Scanner para leer la entrada del usuario
            Scanner scanner = new Scanner(System.in);

            // Solicita al usuario que ingresar texto
            System.out.print("Ingrese un texto alfanumérico largo: ");
            String textoIngresado = scanner.nextLine();

            // Imprime el texto ingresado
            System.out.println("Texto ingresado: " + textoIngresado);



            //Decision del usuario si salir o no
            System.out.print("Deseas ejecutar otra sentencia? 1-SI, 2-NO: ");
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


}
