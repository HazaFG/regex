import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalizadorSintactico {

  private static final Map<String, Integer> tablaSimbolos = new HashMap<>();
  static HashMap<Integer, HashMap<Integer, Integer[]>> tablaSintactica = new HashMap<>();
  private static boolean flujo = false;

  static {
    inicializarTablaSimbolos();
    inicializarTablaSintactica();
  }

  private static void inicializarTablaSimbolos() {
    // Llenamos la tabla de símbolos con los valores correspondientes
    // Palabras Reservadas
    tablaSimbolos.put("SELECT", 10);
    tablaSimbolos.put("FROM", 11);
    tablaSimbolos.put("WHERE", 12);
    tablaSimbolos.put("IN", 13);
    tablaSimbolos.put("AND", 14);
    tablaSimbolos.put("OR", 15);
    tablaSimbolos.put("CREATE", 16);
    tablaSimbolos.put("TABLE", 17);
    tablaSimbolos.put("CHAR", 18);
    tablaSimbolos.put("NUMERIC", 19);
    tablaSimbolos.put("NOT", 20);
    tablaSimbolos.put("NULL", 21);
    tablaSimbolos.put("CONSTRAINT", 22);
    tablaSimbolos.put("KEY", 23);
    tablaSimbolos.put("PRIMARY", 24);
    tablaSimbolos.put("FOREIGN", 25);
    tablaSimbolos.put("REFERENCES", 26);
    tablaSimbolos.put("INSERT", 27);
    tablaSimbolos.put("INTO", 28);
    tablaSimbolos.put("VALUES", 29);

    // Delimitadores
    tablaSimbolos.put(",", 50);
    tablaSimbolos.put(".", 51);
    tablaSimbolos.put("(", 52);
    tablaSimbolos.put(")", 53);
    tablaSimbolos.put("`", 54); // Este es un backtick, no una comilla simple
    tablaSimbolos.put("'", 55);

    // Operadores
    tablaSimbolos.put("+", 70);
    tablaSimbolos.put("-", 71);
    tablaSimbolos.put("*", 72);
    tablaSimbolos.put("/", 73);

    // Constantes
    tablaSimbolos.put("d", 61); // d (numéricos)
    tablaSimbolos.put("a", 62); // a (alfanuméricos)

    // Relacionales
    tablaSimbolos.put(">", 8);
    tablaSimbolos.put("<", 8);
    tablaSimbolos.put("=", 8);
    tablaSimbolos.put(">=", 8);
    tablaSimbolos.put("<=", 8);
  }

  private static void inicializarTablaSintactica() {
    // Tabla sintactica de la imagen del profe
    tablaSintactica.put(
        300,
        new HashMap<Integer, Integer[]>() {
          {
            put(10, new Integer[] {10, 301, 11, 306, 310});
          }
        });

    tablaSintactica.put(
        301,
        new HashMap<Integer, Integer[]>() {
          {
            put(400, new Integer[] {302});
            put(72, new Integer[] {72});
          }
        });

    tablaSintactica.put(
        302,
        new HashMap<Integer, Integer[]>() {
          {
            put(400, new Integer[] {304, 303});
          }
        });

    tablaSintactica.put(
        303,
        new HashMap<Integer, Integer[]>() {
          {
            put(11, new Integer[] {99});
            put(50, new Integer[] {50, 302});
            put(199, new Integer[] {99});
          }
        });

    tablaSintactica.put(
        304,
        new HashMap<Integer, Integer[]>() {
          {
            put(400, new Integer[] {400, 305});
          }
        });

    tablaSintactica.put(
        305,
        new HashMap<Integer, Integer[]>() {
          {
            put(8, new Integer[] {99});
            put(11, new Integer[] {99});
            put(13, new Integer[] {99});
            put(14, new Integer[] {99});
            put(15, new Integer[] {99});
            put(50, new Integer[] {99});
            put(51, new Integer[] {51, 400});
            put(53, new Integer[] {99});
            put(199, new Integer[] {99});
          }
        });

    tablaSintactica.put(
        306,
        new HashMap<Integer, Integer[]>() {
          {
            put(400, new Integer[] {308, 307});
          }
        });

    tablaSintactica.put(
        307,
        new HashMap<Integer, Integer[]>() {
          {
            put(12, new Integer[] {99});
            put(50, new Integer[] {50, 306});
            put(53, new Integer[] {99});
            put(199, new Integer[] {99});
          }
        });

    tablaSintactica.put(
        308,
        new HashMap<Integer, Integer[]>() {
          {
            put(400, new Integer[] {400, 309});
          }
        });

    tablaSintactica.put(
        309,
        new HashMap<Integer, Integer[]>() {
          {
            put(400, new Integer[] {400});
            put(12, new Integer[] {99});
            put(50, new Integer[] {99});
            put(53, new Integer[] {99});
            put(199, new Integer[] {99});
          }
        });

    tablaSintactica.put(
        310,
        new HashMap<Integer, Integer[]>() {
          {
            put(12, new Integer[] {12, 311});
            put(53, new Integer[] {99});
            put(199, new Integer[] {99});
          }
        });

    tablaSintactica.put(
        311,
        new HashMap<Integer, Integer[]>() {
          {
            put(400, new Integer[] {313, 312});
          }
        });

    tablaSintactica.put(
        312,
        new HashMap<Integer, Integer[]>() {
          {
            put(14, new Integer[] {317, 311});
            put(15, new Integer[] {317, 311});
            put(53, new Integer[] {99});
            put(199, new Integer[] {99});
          }
        });

    tablaSintactica.put(
        313,
        new HashMap<Integer, Integer[]>() {
          {
            put(400, new Integer[] {304, 314});
          }
        });

    tablaSintactica.put(
        314,
        new HashMap<Integer, Integer[]>() {
          {
            put(8, new Integer[] {315, 316});
            put(13, new Integer[] {13, 52, 300, 53});
          }
        });

    tablaSintactica.put(
        315,
        new HashMap<Integer, Integer[]>() {
          {
            put(8, new Integer[] {8});
          }
        });

    tablaSintactica.put(
        316,
        new HashMap<Integer, Integer[]>() {
          {
            put(400, new Integer[] {304});
            put(54, new Integer[] {54, 318, 54});
            put(62, new Integer[] {318});
            put(61, new Integer[] {319});
          }
        });

    tablaSintactica.put(317, new HashMap<Integer, Integer[]>() {{put(14, new Integer[] {14}); put(15, new Integer[] {15});}});

    tablaSintactica.put( 318,  new HashMap<Integer, Integer[]>() {{ put(62, new Integer[] {62});}});

    tablaSintactica.put(
        319,
        new HashMap<Integer, Integer[]>() {
          {
            put(61, new Integer[] {61});
          }
        });
  }

  static List<Token> tokenizarSentenciaSQL(String sentencia) {
    List<Token> tokens = new ArrayList<>();
    String[] lineas = sentencia.split("\\n");
    int numeroLinea = 1; // Número de línea inicial

    for (String linea : lineas) {
      // Divisor de palabras reservadas caracteres constantes operadores asi bien tryhard
      String[] partes = SunMicrosystems.GetFilteredWords(linea);

      for (String parte : partes) {
        parte = parte.trim();
        if (!parte.isEmpty()) {
          if (tablaSimbolos.containsKey(parte)) {
            tokens.add(new Token(parte, tablaSimbolos.get(parte), parte, numeroLinea));
          } else if (parte.matches("[0-9]+")) {
            tokens.add(new Token(parte, tablaSimbolos.get("d"), parte, numeroLinea));
          } else if (parte.matches("[a-zA-Z][a-zA-Z0-9]*")) {
            tokens.add(new Token(parte, 400, parte, numeroLinea));
          } else {
            // En el caso de encontrarse con identificadores de #
            if (parte.matches("[a-zA-Z#]*")) {
              tokens.add(new Token(parte, 400, parte, numeroLinea));
              // En el caso de encontrarse con constantes
            } else if (parte.matches("^(‘|’|').*(’|'|‘)$|\\d*")) {
              String regex = "\\d+";
              Pattern pattern = Pattern.compile(regex);
              Matcher matcher = pattern.matcher(parte);
              int tipoConstante = (matcher.matches()) ? 61 : 62;
              tokens.add(new Token(parte, tipoConstante, parte, numeroLinea));
            }
          }
        }
      }

      numeroLinea++; // Incrementar el número de línea después de procesar cada línea
    }

    tokens.add(
        new Token(
            "$", 199, "$", numeroLinea)); // Agregar token de fin de archivo con el número de línea

    return tokens;
  }

  public class SunMicrosystems {
    // Regex que filtra todo
    public static String regex =
        "(‘|’|') ?[a-zA-Z@!\\$\\%#\\\\\\(\\)_\\-0-9\\[\\]\\{\\}?"
            + " ]*(’|'|‘)|>=|<=|[a-zA-Z0-9\\$@#!\\-\\*]+|(,|.)";

    // Funcion para filtrar las palabras de SQL
    public static String[] GetFilteredWords(String text) {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(text);
      ArrayList<String> words = new ArrayList<>();
      while (matcher.find()) {
        if (!matcher.group().isBlank()) {
          words.add(matcher.group());
        }
      }
      return toStringArray(words.toArray());
    }

    // Convierte un array de objetos a un array de strings
    public static String[] toStringArray(Object[] array) {
      String[] stringArray = new String[array.length];
      for (int i = 0; i < array.length; i++) {
        if (array[i] == null) {
          stringArray[i] = "UNDEFINED";
          continue;
        }
        stringArray[i] = array[i].toString();
      }
      return stringArray;
    }
  }

  public class AnalizadorSintacticoLL {
    private Stack<Integer> pila;
    private int apuntadorToken;
    private List<Token> tokens;

    public AnalizadorSintacticoLL(List<Token> tokens) {
      this.pila = new Stack<>();
      this.pila.push(199); // Fin de archivo
      this.pila.push(300); // Símbolo inicial de la gramática
      this.tokens = tokens;
      this.apuntadorToken = 0;
    }

    public boolean analizar() {
      while (pila.peek() != 199) {
        int x = pila.pop();
        Token tokenActual = tokens.get(apuntadorToken);
        int a = tokenActual.getTipo();

        if (esTerminal(x) || x == 199) { // Comprobamos si es un terminal o el final del archivo
          if (x == a) {
            apuntadorToken++;
          } else {
            error(x, tokenActual.getNumeroLinea()); // No hay coincidencia, error sintáctico
            return false;
          }
        } else {
          // Buscamos la producción en la tabla sintáctica. Aquí es donde probablemente necesitemos
          // ajustar.
          if (tablaSintactica.get(x) != null && tablaSintactica.get(x).get(a) != null) {
            Integer[] produccion = tablaSintactica.get(x).get(a);
            if (produccion.length != 0) { // Verificamos que la producción no sea lambda
              for (int i = produccion.length - 1; i >= 0; i--) {
                Integer simboloProduccion = produccion[i];
                if (!esEpsilon(simboloProduccion)) {
                  pila.push(simboloProduccion); // Apilamos la producción en orden inverso
                }
              }
            }
          } else {
            Set<Integer> esperados = tablaSintactica.get(x).keySet();
            esperados.forEach(
                esperado -> {
                  error(
                      esperado,
                      tokenActual.getNumeroLinea()); // No se encontró producción, error sintáctico
                });
            return false;
          }
        }
      }
      return true; // Verificamos si hemos consumido todos los tokens
    }
  }

  private boolean esTerminal(int simbolo) {
    // Implementar lógica para verificar si simbolo es terminal
    // base case
    if (simbolo == 400) {
      return true;
    }
    ;
    return simbolo < 300;
  }

  private boolean esEpsilon(int simbolo) {
    return simbolo == 99; // Suponiendo que epsilon está definido con el código 99
  }

  private void error(int codigoEsperado, int linea) {
    String descripcionError;
    int tipoError = 2; // Por defecto, error de tipo 2 (sintáctico)
    int codigoError = 0;
    switch (codigoEsperado) {
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
        descripcionError = "Se esperaba Palabra Reservada.";
        codigoError = 201;
        break;
      case 400: // Identificador
        descripcionError = "Se esperaba Identificador.";
        codigoError = 204;
        break;
      case 52:
      case 53:
      case 54:
      case 50:
      case 51:
        descripcionError = "Se esperaba Delimitador.";
        codigoError = 205;
        break;
      case 61:
      case 62:
        descripcionError = "Se esperaba Constante.";
        codigoError = 206;
        break;
      case 70:
      case 71:
      case 72:
      case 73:
        descripcionError = "Se esperaba Operador.";
        codigoError = 207;
        break;
      case 8:
        descripcionError = "Se esperaba Operador Relacional.";
        codigoError = 208;
        break;
      default:
        descripcionError = "Simbolo desconocido";
        codigoError = 101;
        tipoError = 1;
        break;
    }
    System.out.printf(
        "Error %d:%d Línea %d. %s%n", tipoError, codigoError, linea, descripcionError);
  }

  public static void main(String[] args) {
    do {
      System.out.println("\n");
      AnalizadorSintactico analizador = new AnalizadorSintactico();

      Scanner scanner = new Scanner(System.in);

      System.out.println("Ingrese una sentencia SQL:");
      StringBuilder sentenciaBuilder = new StringBuilder();

      String linea;
      while (!(linea = scanner.nextLine()).isEmpty()) {
        sentenciaBuilder.append(linea).append("\n");
      }

      List<Token> tokens = analizador.tokenizarSentenciaSQL(String.valueOf(sentenciaBuilder));
      AnalizadorSintacticoLL analizadorLL = analizador.new AnalizadorSintacticoLL(tokens);
      boolean esValido = analizadorLL.analizar();
      String mensaje = (esValido) ? "No hay errores" : "Ocurrieron pedillos";
      System.out.println(mensaje);
    } while (!flujo);
  }

  // Clase interna para representar Tokens
  private static class Token {
    String lexema;
    Integer tipo; // Tipo ahora es Integer en lugar de String
    String valor;
    int numeroLinea; // Nuevo campo para el número de línea

    public Token(String lexema, Integer tipo, String valor, int numeroLinea) {
      this.lexema = lexema;
      this.tipo = tipo;
      this.valor = valor;
      this.numeroLinea = numeroLinea; // Inicializar el número de línea
    }

    public Integer getTipo() {
      return tipo;
    }

    public String getValor() {
      return valor;
    }

    public int getNumeroLinea() {
      return numeroLinea;
    }
  }
}
