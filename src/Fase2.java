// Ingenieria en Desarrollo de Software
// Programacion de Sistemas
// IDS 6 TM
// 09/03/2024

// Integrantes:
// Mario Alberto Castellanos Martinez
// Hazael Flores Gastelum
// Ricardo Garayzar Ortega
// Andrea Lucero Trasviña
// Miguel Angel Lazcano Ortega
// Carlos André Campa Molina


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fase2 {

    private static int valorIdentificador = 401;
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Solicita texto
            System.out.print("Ingrese un texto: ");
            StringBuilder textoIngresado = new StringBuilder();

            // Lee líneas de texto hasta que aparezca una en blanco
            String linea;

            while (!(linea = scanner.nextLine()).isEmpty()) {
                textoIngresado.append(linea).append("\n");
            }

            System.out.println(textoIngresado);

            // Tabla 1
            int i = 1;
            System.out.printf("| %-4s | %-10s | %-15s | %-10s | %-10s%n", "No°", "Linea", "TOKEN", "Tipo", "Código");

            // Buscar y mostrar tokens encontrados con su tipo y código
            i = buscarYMostrarTokens(textoIngresado.toString(), 
            "\\b(SELECT|FROM|WHERE|AND|OR|INSERT|UPDATE|DELETE|IN|CREATE|TABLE|CHAR|NUMERIC|NOT|NULL|CONSTRAINT|KEY|PRIMARY|FOREIGN|REFERENCES|INTO)\\b|"
            + "[,‘]|"
            + "[+\\-*/]|"
            + ">=?|<=?|[><=]|"
            + "'\\w+'|"
            + "\\b\\d+\\b|"
            + "\\b[a-zA-Z0-9]+\\b", i);


            System.out.print("\n¿Quieres repetir el proceso? (1: Sí, 2: No): ");

            int opcion = scanner.nextInt();

            if (opcion == 2) {
                System.out.println("\nPrograma cerrado\n");
                break; // Salir del bucle
            } else if (opcion != 1) {
                System.out.println("\nOpción no válida. Por favor, ingresa 1 o 2\n");
            }

            // Limpiar el búfer del scanner
            scanner.nextLine();
        }

        // Cerrar el scanner
        scanner.close();
    }

    // Método para buscar y mostrar tokens con su tipo y código
    private static int buscarYMostrarTokens(String texto, String regex, int startIndex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(texto);

        int i = startIndex;
        while (matcher.find()) {
            int lineMatch = obtenerLineaCoincidente(texto, matcher.start());
            String token = matcher.group();
            TipoValor tipoValor = determinarTipoYValor(token);
            System.out.printf("| %-4s | %-10s | %-15s | %-10s | %-10s%n", i, lineMatch, token, tipoValor.getTipo(), tipoValor.getValor());
            i++;
        }

        return i;
    }

    // Método para determinar la línea donde se encuentra la coincidencia
    private static int obtenerLineaCoincidente(String texto, int posicion) {
        int numeroLinea = 1;
        for (int i = 0; i < posicion; i++) {
            if (texto.charAt(i) == '\n') {
                numeroLinea++;
            }
        }
        return numeroLinea;
    }

    // Método para determinar el tipo y valor de la palabra
    private static TipoValor determinarTipoYValor(String palabra) {
        switch (palabra.toUpperCase()) {
            case "SELECT":
                return new TipoValor(1, 10);
            case "FROM":
                return new TipoValor(1, 11);
            case "WHERE":
                return new TipoValor(1, 12);
            case "IN":
                return new TipoValor(1, 13);
            case "AND":
                return new TipoValor(1, 14);
            case "OR":
                return new TipoValor(1, 15);
            case "CREATE":
                return new TipoValor(1, 16);
            case "TABLE":
                return new TipoValor(1, 17);
            case "CHAR":
                return new TipoValor(1, 18);
            case "NUMERIC":
                return new TipoValor(1, 19);
            case "NOT":
                return new TipoValor(1, 20);
            case "NULL":
                return new TipoValor(1, 21);
            case "CONSTRAINT":
                return new TipoValor(1, 22);
            case "KEY":
                return new TipoValor(1, 23);
            case "PRIMARY":
                return new TipoValor(1, 24);
            case "FOREIGN":
                return new TipoValor(1, 25);
            case "REFERENCES":
                return new TipoValor(1, 26);
            case "INSERT":
                return new TipoValor(1, 27);
            case "INTO":
                return new TipoValor(1, 28);
            case "VALUES":
                return new TipoValor(1, 29);
            case ",":
                return new TipoValor(5, 50);
            case ".":
                return new TipoValor(5, 51);
            case "(":
                return new TipoValor(5, 52);
            case ")":
                return new TipoValor(5, 53);
            case "‘":
                return new TipoValor(5, 54);
            case "+":
                return new TipoValor(7, 70);
            case "-":
                return new TipoValor(7, 71);
            case "*":
                return new TipoValor(7, 72);
            case "/":
                return new TipoValor(7, 73);
            case ">":
                return new TipoValor(8, 81);
            case "<":
                return new TipoValor(8, 82);
            case "=":
                return new TipoValor(8, 83);
            case ">=":
                return new TipoValor(8, 84);
            case "<=":
                return new TipoValor(8, 85);
            default:
                break;
        }

            // Identificar constantes alfanuméricas
        if (palabra.matches("'\\w+'")) {
            return new TipoValor(6, 62); // Constante alfanumérica
        }

        // Identificar constantes numéricas
        if (palabra.matches("\\b\\d+\\b")) {
            return new TipoValor(6, 61); // Constante numérica
        }

        // Identificar identificadores
        if (palabra.matches("\\b[a-zA-Z_]\\w*\\b")) {
            return new TipoValor(4, valorIdentificador++); // Identificador (inicia en 401)
        }

        return new TipoValor(0, 0);
    }

    // Clase para representar el tipo y valor de un token
    private static class TipoValor {
        private final int tipo;
        private final int valor;

        public TipoValor(int tipo, int valor) {
            this.tipo = tipo;
            this.valor = valor;
        }

        public int getTipo() {
            return tipo;
        }

        public int getValor() {
            return valor;
        }
    }
}
