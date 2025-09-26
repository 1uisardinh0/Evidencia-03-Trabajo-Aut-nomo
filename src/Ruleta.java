import java.util.Random;
import java.util.Scanner;

public class Ruleta {

    public static final int MAX_HISTORIAL = 100;
    public static final int MIN_NUMERO = 0;
    public static final int MAX_NUMERO = 36;

    public static int[] historialNumeros = new int[MAX_HISTORIAL];
    public static int[] historialApuestas = new int[MAX_HISTORIAL];
    public static boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    public static int historialSize = 0;

    public static Random rng = new Random();
    public static int[] numerosRojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner in = new Scanner(System.in);
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion(in);
            ejecutarOpcion(opcion, in);
        } while (opcion != 3);
        in.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n=== RULETA CASINO BLACK CAT ===");
        System.out.println("1. Iniciar ronda");
        System.out.println("2. Ver estadísticas");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public static int leerOpcion(Scanner in) {
        return in.nextInt();
    }

    public static void ejecutarOpcion(int opcion, Scanner in) {
        switch (opcion) {
            case 1:
                iniciarRonda(in);
                break;
            case 2:
                mostrarEstadisticas();
                break;
            case 3:
                System.out.println("¡Gracias por jugar!");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    public static void iniciarRonda(Scanner in) {
        if (historialSize >= MAX_HISTORIAL) {
            System.out.println("Límite de rondas alcanzado.");
            return;
        }

        char tipo = leerTipoApuesta(in);
        System.out.print("Ingrese monto a apostar: ");
        int monto = in.nextInt();

        int numero = girarRuleta();
        boolean acierto = evaluarResultado(numero, tipo);

        registrarResultado(numero, monto, acierto);
        mostrarResultado(numero, tipo, monto, acierto);
    }

    public static char leerTipoApuesta(Scanner in) {
        char tipo;
        do {
            System.out.print("Seleccione tipo de apuesta (R: Rojo, N: Negro, P: Par, I: Impar): ");
            tipo = in.next().toUpperCase().charAt(0);
        } while (tipo != 'R' && tipo != 'N' && tipo != 'P' && tipo != 'I');
        return tipo;
    }

    public static int girarRuleta() {
        return rng.nextInt(MAX_NUMERO + 1);
    }

    public static boolean evaluarResultado(int numero, char tipo) {
        if (numero == 0) return false; // El cero no es rojo/negro/par/impar

        return switch (tipo) {
            case 'R' -> esRojo(numero);
            case 'N' -> !esRojo(numero);
            case 'P' -> numero % 2 == 0;
            case 'I' -> numero % 2 == 1;
            default -> false;
        };
    }

    public static boolean esRojo(int n) {
        for (int rojo : numerosRojos) {
            if (rojo == n) return true;
        }
        return false;
    }

    public static void registrarResultado(int numero, int apuesta, boolean acierto) {
        historialNumeros[historialSize] = numero;
        historialApuestas[historialSize] = apuesta;
        historialAciertos[historialSize] = acierto;
        historialSize++;
    }

    public static void mostrarResultado(int numero, char tipo, int monto, boolean acierto) {
        System.out.println("\n--- Resultado de la ronda ---");
        System.out.println("Número: " + numero);
        System.out.println("Apuesta: " + tipo + " | Monto: " + monto);
        System.out.println("Resultado: " + (acierto ? "GANÓ" : "PERDIÓ"));
    }

    public static void mostrarEstadisticas() {
        if (historialSize == 0) {
            System.out.println("No hay rondas jugadas.");
            return;
        }

        int totalRondas = historialSize;
        int totalApostado = 0;
        int totalAciertos = 0;

        for (int i = 0; i < historialSize; i++) {
            totalApostado += historialApuestas[i];
            if (historialAciertos[i]) totalAciertos++;
        }

        double porcentajeAciertos = (double) totalAciertos / totalRondas * 100;
        int gananciaNeta = (totalAciertos * 2) - totalApostado; // Se paga el doble al ganar

        System.out.println("\n--- Estadísticas ---");
        System.out.println("Rondas jugadas: " + totalRondas);
        System.out.println("Total apostado: " + totalApostado);
        System.out.println("Total de aciertos: " + totalAciertos);
        System.out.printf("Porcentaje de aciertos: %.2f%%\n", porcentajeAciertos);
        System.out.println("Ganancia/Pérdida neta: " + gananciaNeta);
    }
}
