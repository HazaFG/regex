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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fase2 {

    private static int valorIdentificador = 401;
    private static int valorConstante = 600;
    private static Map<String, Integer> identificadores = new HashMap<>();
    private static Map<String, Integer> constantes = new HashMap<>();
    static ArrayList<Token> tokens = new ArrayList<>();

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
                            + "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b", i);

            //TABLA CONSTANTES EJEMPLO (PRUEBA)
            for(int j = 0; j < tokens.size(); j++){
                if(tokens.get(j).getTipo() == 6){
                    System.out.print(tokens.get(j).getNombre());
                }
            }


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
            System.out.printf("| %-4s | %-10s | %-15s | %-10s | %-10s%n", i, lineMatch, token, tipoValor.getTipo(),
                    tipoValor.getValor());
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
                tokens.add(new Token(palabra, 1, 10));
                return new TipoValor(1, 10);
            case "FROM":
                tokens.add(new Token(palabra, 1, 11));
                return new TipoValor(1, 11);
            case "WHERE":
                tokens.add(new Token(palabra, 1, 12));
                return new TipoValor(1, 12);
            case "IN":
                tokens.add(new Token(palabra, 1, 13));
                return new TipoValor(1, 13);
            case "AND":
                tokens.add(new Token(palabra, 1, 14));
                return new TipoValor(1, 14);
            case "OR":
                tokens.add(new Token(palabra, 1, 15));
                return new TipoValor(1, 15);
            case "CREATE":
                tokens.add(new Token(palabra, 1, 16));
                return new TipoValor(1, 16);
            case "TABLE":
                tokens.add(new Token(palabra, 1, 17));
                return new TipoValor(1, 17);
            case "CHAR":
                tokens.add(new Token(palabra, 1, 18));
                return new TipoValor(1, 18);
            case "NUMERIC":
                tokens.add(new Token(palabra, 1, 19));
                return new TipoValor(1, 19);
            case "NOT":
                tokens.add(new Token(palabra, 1, 20));
                return new TipoValor(1, 20);
            case "NULL":
                tokens.add(new Token(palabra, 1, 21));
                return new TipoValor(1, 21);
            case "CONSTRAINT":
                tokens.add(new Token(palabra, 1, 22));
                return new TipoValor(1, 22);
            case "KEY":
                tokens.add(new Token(palabra, 1, 23));
                return new TipoValor(1, 23);
            case "PRIMARY":
                tokens.add(new Token(palabra, 1, 24));
                return new TipoValor(1, 24);
            case "FOREIGN":
                tokens.add(new Token(palabra, 1, 25));
                return new TipoValor(1, 25);
            case "REFERENCES":
                tokens.add(new Token(palabra, 1, 26));
                return new TipoValor(1, 26);
            case "INSERT":
                tokens.add(new Token(palabra, 1, 27));
                return new TipoValor(1, 27);
            case "INTO":
                tokens.add(new Token(palabra, 1, 28));
                return new TipoValor(1, 28);
            case "VALUES":
                tokens.add(new Token(palabra, 1, 29));
                return new TipoValor(1, 29);
            case ",":
                tokens.add(new Token(palabra, 5, 50));
                return new TipoValor(5, 50);
            case ".":
                tokens.add(new Token(palabra, 5, 51));
                return new TipoValor(5, 51);
            case "(":
                tokens.add(new Token(palabra, 5, 52));
                return new TipoValor(5, 52);
            case ")":
                tokens.add(new Token(palabra, 5, 53));
                return new TipoValor(5, 53);
            case "‘":
                tokens.add(new Token(palabra, 5, 54));
                return new TipoValor(5, 54);
            case "+":
                tokens.add(new Token(palabra, 7, 70));
                return new TipoValor(7, 70);
            case "-":
                tokens.add(new Token(palabra, 7, 71));
                return new TipoValor(7, 71);
            case "*":
                tokens.add(new Token(palabra, 7, 72));
                return new TipoValor(7, 72);
            case "/":
                tokens.add(new Token(palabra, 7, 73));
                return new TipoValor(7, 73);
            case ">":
                tokens.add(new Token(palabra, 8, 81));
                return new TipoValor(8, 81);
            case "<":
                tokens.add(new Token(palabra, 8, 82));
                return new TipoValor(8, 82);
            case "=":
                tokens.add(new Token(palabra, 8, 83));
                return new TipoValor(8, 83);
            case ">=":
                tokens.add(new Token(palabra, 8, 84));
                return new TipoValor(8, 84);
            case "<=":
                tokens.add(new Token(palabra, 8, 85));
                return new TipoValor(8, 85);
            default:
                break;
        }

        // Identificar constantes alfanuméricas
        if (palabra.matches("'\\w+'")) {
            tokens.add(new Token(palabra, 6, obtenerCodigoConstante(palabra, constantes)));
            return new TipoValor(6, obtenerCodigoConstante(palabra, constantes));
        }

        // Identificar constantes numéricas
        if (palabra.matches("\\b\\d+\\b")) {
            tokens.add(new Token(palabra, 6, obtenerCodigoConstante(palabra, constantes)));
            return new TipoValor(6, obtenerCodigoConstante(palabra, constantes));
        }

        // Identificar identificadores
        if (palabra.matches("\\b[a-zA-Z_]\\w*\\b")) {
            tokens.add(new Token(palabra, 4, obtenerCodigoConstante(palabra, constantes)));
            return new TipoValor(4, obtenerCodigoConstante(palabra, identificadores));
        }

        return new TipoValor(0, 0);
    }

    private static int obtenerCodigoConstante(String constante, Map<String, Integer> mapa) {
        if (!mapa.containsKey(constante)) {
            mapa.put(constante, constante.startsWith("'") ? valorConstante++ : valorIdentificador++);
        }
        return mapa.get(constante);
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
