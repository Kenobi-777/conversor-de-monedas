public class ConversorDeMoneda {

    public static void main(String[] args) {
        InterfazUsuario ui = new InterfazUsuario();
        ServicioCambioMoneda servicioCambio = new ServicioCambioMoneda();
        CalculadoraConversion calculadora = new CalculadoraConversion();
        boolean continuar = true;

        while (continuar) {
            ui.mostrarMenuPrincipal();
            int opcionOrigen = ui.obtenerOpcionPrincipal();

            if (opcionOrigen >= 1 && opcionOrigen <= 8) {
                String monedaOrigen = "";
                String monedaDestino = "";

                switch (opcionOrigen) {
                    case 1, 2, 3, 4, 5, 6:
                        monedaOrigen = obtenerClaveMoneda(ui, opcionOrigen);
                        ui.mostrarMenuDestino(monedaOrigen);
                        int opcionDestinoPredefinida = ui.obtenerOpcionDestino();
                        if (opcionDestinoPredefinida >= 1 && opcionDestinoPredefinida <= 7) {
                            monedaDestino = obtenerClaveMonedaDestino(ui, opcionDestinoPredefinida, monedaOrigen);
                            if (!monedaDestino.isEmpty()) {
                                convertir(ui, servicioCambio, calculadora, monedaOrigen, monedaDestino);
                            }
                        }
                        break;
                    case 7: // Conversión personalizada
                        System.out.print("Ingresa la abreviatura de la moneda de origen (ej: EUR): ");
                        monedaOrigen = ui.obtenerClavePais().toUpperCase();
                        System.out.print("Ingresa la abreviatura de la moneda a la que deseas convertir (ej: ej: EUR): ");
                        monedaDestino = ui.obtenerClavePais().toUpperCase();
                        if (!monedaOrigen.isEmpty() && !monedaDestino.isEmpty() && !monedaOrigen.equalsIgnoreCase(monedaDestino)) {
                            convertir(ui, servicioCambio, calculadora, monedaOrigen, monedaDestino);
                        } else if (monedaOrigen.equalsIgnoreCase(monedaDestino)) {
                            ui.mostrarError("La moneda de origen no puede ser la misma que la moneda de destino.");
                        }
                        break;
                    case 8:
                        System.out.println();
                        System.out.println("Gracias por utilizar nuestra APP");
                        ui.mostrarMensaje("Saliendo del programa...");
                        System.out.println();
                        System.out.println("*********************************************************");
                        continuar = false;
                        break;
                }
            } else if (opcionOrigen != -1) {
                ui.mostrarMensaje("Opción incorrecta, Por favor ingrese una opción valida");
            }
            ui.mostrarMensaje(""); // Línea en blanco para mejor legibilidad
        }
        ui.cerrarScanner();
    }

    private static String obtenerClaveMoneda(InterfazUsuario ui, int opcion) {
        return switch (opcion) {
            case 1 -> "USD";
            case 2 -> "ARS";
            case 3 -> "BRL";
            case 4 -> "CLP";
            case 5 -> "COP";
            case 6 -> "EUR";
            default -> "";
        };
    }

    private static String obtenerClaveMonedaDestino(InterfazUsuario ui, int opcion, String monedaOrigen) {
        String monedaDestino = switch (opcion) {
            case 1 -> "USD";
            case 2 -> "ARS";
            case 3 -> "BRL";
            case 4 -> "CLP";
            case 5 -> "COP";
            case 6 -> "EUR";
            case 7 -> {
                System.out.print("Ingrese la abreviatura de la moneda a convertir (ej: EUR): ");
                String claveDestino = ui.obtenerClavePais().toUpperCase();
                if (claveDestino.equalsIgnoreCase(monedaOrigen)) {
                    ui.mostrarError("La moneda de destino no puede ser la misma que la moneda de origen.");
                    yield "";
                }
                yield claveDestino;
            }
            default -> "";
        };

        // CAMBIO: Agregar la verificación aquí
        if (!monedaDestino.isEmpty() && monedaDestino.equalsIgnoreCase(monedaOrigen)) {
            ui.mostrarError("La moneda de destino no puede ser la misma que la moneda de origen.");
            return "";
        }
        return monedaDestino;
    }

    private static void convertir(InterfazUsuario ui, ServicioCambioMoneda servicioCambio, CalculadoraConversion calculadora, String monedaOrigen, String monedaDestino) {
        double montoOrigen = ui.obtenerMonto(monedaOrigen);

        if (montoOrigen > 0) {
            try {
                double tasaCambio = servicioCambio.obtenerTasaDeCambio(monedaOrigen, monedaDestino);
                if (tasaCambio > 0) {
                    double montoDestino = calculadora.convertir(montoOrigen, tasaCambio);
                    ui.mostrarResultado(montoOrigen, monedaOrigen, montoDestino, monedaDestino);
                } else if (tasaCambio == -1) {

                } else {
                    ui.mostrarError("Error al obtener la tasa de cambio.");
                }
            } catch (Exception e) {
                ui.mostrarError("Ocurrió un error: " + e.getMessage());
            }
        }
    }
}