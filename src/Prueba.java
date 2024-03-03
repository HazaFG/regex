import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prueba {
    public static void main(String[] args) {
        String texto = "Estos datos positivos también tienen su correlación con la salud. La tasa de mortalidad entre los recién nacidos era de 64,8 por cada 1.000 en 1990. Pero en 2016, la realidad era bien diferente, ya que solo 30,5B fallecían por cada 1.000 (en 26 años se ha reducido un 53%). Igual de positivo es la caída de la mortalidad entre los menores de 5A; en 26 años se ha pasado de una tasa de 93,4 fallecidos por 1.0E2 a un 40,8 (una reducción del 56%). De igual manera, el número de mujeres que fallece durante el parto también ha decrecido (en 1990 el número de muertes fue de 532.000 y en 2015 de 303.00.0, una disminución del 43%).";

        // Expresión regular para identificar números válidos
        String regex = "(?<![\\d.])(\\d+(\\.\\d+)?(e\\d+)?%?|\\.\\d+%?)(?![\\d.])";

        // Patrón y matcher para buscar números en el texto
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);

        // Buscar y mostrar los números encontrados con su tipo
        while (matcher.find()) {
            String numero = matcher.group();
            String tipo = determinarTipo(numero);
            System.out.println(numero + " - " + tipo);
        }
    }

    // Método para determinar el tipo de número
    private static String determinarTipo(String numero) {
        if (numero.contains(".")) {
            if (numero.contains("e")) {
                return "Número exponencial";
            } else {
                return "Número real";
            }
        } else if (numero.contains("%")) {
            return "Número porcentual";
        } else {
            return "Número natural";
        }
    }
}
