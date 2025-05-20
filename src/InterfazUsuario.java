import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfazUsuario {

    private Scanner scanner = new Scanner(System.in);

    public void mostrarMenuPrincipal() {
        System.out.println("------------------------ CONVERSOR DE MONEDAS ------------------------");
        System.out.println();
        System.out.println("Bienvenidos a su APP para convertir monedas");
        System.out.println();
        System.out.println("****************************");
        System.out.println("Opciones de cambio");
        System.out.println("****************************");
        System.out.println("1. Dólar (USD)");
        System.out.println("2. Peso Argentino (ARS)");
        System.out.println("3. Real Brasileño (BRL)");
        System.out.println("4. Peso Chileno (CLP)");
        System.out.println("5. Peso Colombiano (COP)");
        System.out.println("6. Euro (EUR)");
        System.out.println("7. Conversión personalizada usando abreviatura (ej: EUR)");
        System.out.println("8. Salir");
        System.out.println();
        System.out.print("Elija una opción de cambio: ");
    }

    public int obtenerOpcionPrincipal() {
        int opcion = -1;
        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingresa un número.");
            scanner.next();
        }
        return opcion;
    }

    public void mostrarMenuDestino(String monedaOrigen) {
        System.out.println("Convertir de " + monedaOrigen + " a: ");
        System.out.println();
        System.out.println("1. Dólar (USD)");
        System.out.println("2. Peso Argentino (ARS)");
        System.out.println("3. Real Brasileño (BRL)");
        System.out.println("4. Peso Chileno (CLP)");
        System.out.println("5. Peso Colombiano (COP)");
        System.out.println("6. Euro (EUR)");
        System.out.println("7. Otra moneda (ingresar abreviatura ej: EUR)");
        System.out.println();
        System.out.print("Elija opción a convertir: ");
    }

    public int obtenerOpcionDestino() {
        int opcion = -1;
        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingresa un número.");
            scanner.next();
        }
        return opcion;
    }

    public double obtenerMonto(String monedaOrigen) {
        double monto = -1;
        System.out.printf("Ingresa el monto en %s para su conversion: ", monedaOrigen);
        try {
            monto = scanner.nextDouble();
            if (monto <= 0) {
                System.out.println("El monto debe ser mayor que cero.");
                return -1;
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingresa un número válido.");
            scanner.next();
            return -1;
        }
        return monto;
    }

    public String obtenerClavePais() {
        return scanner.next().toUpperCase(); // La pregunta ahora se hace directamente en ConversorDeMoneda para la opción 7
    }

    public void mostrarResultado(double montoOrigen, String monedaOrigen, double montoDestino, String monedaDestino) {
        System.out.println("***********************************************************************************");
        System.out.printf("El valor de >>> %.2f %s corresponde al valor de >>> %.2f %s%n",
                montoOrigen, monedaOrigen, montoDestino, monedaDestino);
        System.out.println("***********************************************************************************");
    }

    public void mostrarError(String mensaje) {
        System.err.println("Error: " + mensaje);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void cerrarScanner() {
        scanner.close();
    }
}