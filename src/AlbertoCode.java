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

import java.beans.SimpleBeanInfo;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlbertoCode {

    private static int valorIdentificador = 401;
    private static int valorConstante = 600;
    private static Map<String, Integer> identificadores = new HashMap<>();
    private static Map<String, Integer> constantes = new HashMap<>();
    private static boolean opcion = true;

    static ArrayList<Token> tokens = new ArrayList<>();

    static ArrayList<Token> errores = new ArrayList<>();


    private static final String[] PALABRAS_CLAVE = {
            "SELECT", "FROM", "WHERE", "IN", "AND", "OR", "CREATE", "TABLE", "CHAR", "NUMERIC",
            "NOT", "NULL", "CONSTRAINT", "KEY", "PRIMARY", "FOREIGN", "REFERENCES", "INSERT",
            "INTO", "VALUES", ",", ".", "(", ")", "‘", "’", "+", "-", "*", "/", ">", "<", "=", ">=", "<=", "'", "#"
    };

    private static final int[] CODIGOS = {
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
            50, 51, 52, 53, 54, 70, 71, 72, 73, 81, 82, 83, 84, 85
    };


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        StringBuilder textoIngresado;

        do {
            valorIdentificador = 401;
            valorConstante = 600;
            identificadores = new HashMap<>();
            constantes = new HashMap<>();
            tokens = new ArrayList<>();

            // Solicita texto
            System.out.print("Ingrese un texto: ");
            textoIngresado = new StringBuilder();

            // Lee líneas de texto hasta que aparezca una en blanco
            String linea;

            while (!(linea = scanner.nextLine()).isEmpty()) {
                textoIngresado.append(linea).append("\n");
            }

            //System.out.println(textoIngresado);
            int i = 1;

            valorIdentificador = 401;
            valorConstante = 600;
            identificadores = new HashMap<>();
            constantes = new HashMap<>();
            tokens = new ArrayList<>();

            verificarTexto(textoIngresado.toString());

            // Tabla 1
            System.out.printf("\n\nTABLA LÉXICA\n| %-4s | %-10s | %-15s | %-10s | %-10s%n", "No°", "Linea", "TOKEN", "Tipo", "Código");
            // Buscar y mostrar tokens encontrados con su tipo y código
            i = buscarYMostrarTokens(textoIngresado.toString(),
                    "\\b(SELECT|FROM|WHERE|AND|OR|INSERT|UPDATE|DELETE|IN|CREATE|TABLE|CHAR|NUMERIC|NOT|NULL|CONSTRAINT|KEY|PRIMARY|FOREIGN|REFERENCES|INTO)\\b|"
                            + "[,‘]|"
                            + "[+\\-*/]|"
                            + ">=?|<=?|[><=]|"
                            + "'\\w+'|"
                            + "\\b\\d+\\b|"
                            + "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b", i);

            // Tabla 2
            //TABLA CONSTANTES EJEMPLO (PRUEBA)
            System.out.printf("\nTABLA DE CONSTANTES\n| %-4s | %-10s | %-4s | %-10s%n", "No°", "Constante", "Tipo", "Valor");
            for (int j = 0; j < tokens.size(); j++) {
                if (tokens.get(j).getTipo() == 6) {
                    System.out.printf("| %-4s | %-10s | %-4s | %-10s%n", tokens.get(j).getId(),
                            tokens.get(j).getNombre(), tokens.get(j).getTipo(), tokens.get(j).getValor());
                }
            }

            // Tabla de Identificadores
            System.out.printf("\nTABLA DE IDENTIFICADORES\n| %-17s | %-5s | %-10s%n", "Identificador", "Valor", "Linea");
            Map<String, Set<Integer>> identificadoresRepetidos = new HashMap<>();

            for (int j = 0; j < tokens.size(); j++) {
                if (tokens.get(j).getTipo() == 4) {
                    String identificador = tokens.get(j).getNombre();
                    int valor = tokens.get(j).getValor();
                    int lineaa = tokens.get(j).getNoLinea();

                    identificadores.put(identificador, valor);

                    if (!identificadoresRepetidos.containsKey(identificador)) {
                        identificadoresRepetidos.put(identificador, new HashSet<>());
                    }
                    identificadoresRepetidos.get(identificador).add(lineaa);
                }
            }

            identificadoresRepetidos.forEach((identificador, lineas) -> {
                System.out.printf("| %-17s | %-5s | %-10s%n", identificador, identificadores.get(identificador), lineas);
            });

            if (!errores.isEmpty()) {
                // Mostrar la tabla de errores
                System.out.println("\nTABLA DE ERRORES");
                System.out.println("| Identificador  | Linea");
                for (Token error : errores) {
                    System.out.printf("| %-17s | %-12s%n", error.getNombre(), error.getNoLinea());
                }
                // Limpiar la lista de errores para la próxima entrada de texto
                errores.clear();
            }

            // Limpiar el búfer del scanner
            scanner.nextLine();
        } while (opcion == true);

        // Cerrar el scanner
        scanner.close();
    }
    // Método para buscar y mostrar tokens con su tipo y código
    private static int buscarYMostrarTokens(String texto, String regex, int startIndex) {
        if (errores.isEmpty()) {
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
        else{
            return 0;
        }
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
    private static AlbertoCode.TipoValor determinarTipoYValor(String palabra) {
        for (int i = 0; i < PALABRAS_CLAVE.length; i++) {
            if (palabra.equalsIgnoreCase(PALABRAS_CLAVE[i])) {
                return new AlbertoCode.TipoValor(1, CODIGOS[i]);
            }
        }

        // Identificar constantes alfanuméricas
        if (palabra.matches("'\\w+'")) {
            return new AlbertoCode.TipoValor(6, obtenerCodigoConstante(palabra, constantes));
        }

        // Identificar constantes numéricas
        if (palabra.matches("\\b\\d+\\b")) {
            return new AlbertoCode.TipoValor(6, obtenerCodigoConstante(palabra, constantes));
        }

        // Identificar identificadores
        if (palabra.matches("\\b[a-zA-Z_]\\w*\\b")) {
            return new AlbertoCode.TipoValor(4, obtenerCodigoConstante(palabra, identificadores));
        }

        return new AlbertoCode.TipoValor(0, 0);
    }






    public static void verificarTexto(String texto) {
        Scanner scanner = new Scanner(texto);
        int noLinea = 1;

        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine().trim(); // Obtener la siguiente línea y eliminar espacios en blanco al inicio y al final

            // Verificar si la línea contiene solo símbolos, excluyendo las palabras clave
            if (linea.matches(".*[^a-zA-Z0-9\\s" + getExpresionPalabrasClave() + "].*")) {
                errores.add(new Token(linea, noLinea));
                System.out.println("\n\nError en la línea " + noLinea + ": " + linea);
            }
            noLinea++; // Incrementar el número de línea
        }

        scanner.close();
    }

    private static String getExpresionPalabrasClave() {
        StringBuilder expresion = new StringBuilder();
        for (String palabraClave : PALABRAS_CLAVE) {
            expresion.append(Pattern.quote(palabraClave));
        }
        return expresion.toString();
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