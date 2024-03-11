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
            System.out.printf("TABLA LÉXICA\n| %-4s | %-10s | %-15s | %-10s | %-10s%n", "No°", "Linea", "TOKEN", "Tipo", "Código");

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
            System.out.printf("\nTABLA DE CONSTANTES\n| %-4s | %-10s | %-4s | %-10s%n", "No°", "Constante", "Tipo", "Valor");
            for(int j = 0; j < tokens.size(); j++){
                if(tokens.get(j).getTipo() == 6){
                    System.out.printf("| %-4s | %-10s | %-4s | %-10s%n", tokens.get(j).getId(),
                            tokens.get(j).getNombre(), tokens.get(j).getTipo(), tokens.get(j).getValor());
                }
            }

            //TABLA IDENTIFICADORES EJEMPLO (PRUEBA)
            System.out.printf("\nTABLA DE IDENTIFICADORES\n| %-17s | %-5s | %-10s%n", "Identificador", "Valor", "Linea");
            for(int j = 0; j < tokens.size(); j++){
                if(tokens.get(j).getTipo() == 4){
                    System.out.printf("| %-17s | %-5s | %-10s%n", tokens.get(j).getNombre(), tokens.get(j).getValor(), tokens.get(j).getNoLinea());
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
            tokens.add(new Token(i, lineMatch, token, tipoValor.getTipo(), tipoValor.getValor()));
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
            return new TipoValor(6, obtenerCodigoConstante(palabra, constantes));
        }

        // Identificar constantes numéricas
        if (palabra.matches("\\b\\d+\\b")) {
            return new TipoValor(6, obtenerCodigoConstante(palabra, constantes));
        }

        // Identificar identificadores
        if (palabra.matches("\\b[a-zA-Z_]\\w*\\b")) {
            return new TipoValor(4, obtenerCodigoConstante(palabra, identificadores));
        }

        return new TipoValor(0, 0);
    }
    private static int obtenerCodigoConstante(String constante, Map<String, Integer> mapa) {
        if (!mapa.containsKey(constante)) {
            if (constante.matches("'\\w+'")) {
                mapa.put(constante, valorConstante++);
            } else if (constante.matches("\\b\\d+\\b")) {
                mapa.put(constante, valorConstante++);
            } else {
                mapa.put(constante, valorIdentificador++);
            }
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
