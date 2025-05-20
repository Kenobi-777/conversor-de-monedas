// ServicioCambioMoneda.java
import com.google.gson.Gson; // Importa la biblioteca Gson para trabajar con JSON.
import com.google.gson.JsonObject; // Importa la clase JsonObject de Gson.
import java.io.IOException; // Importa la excepción IOException para manejar errores de entrada/salida.
import java.net.URI; // Importa la clase URI para construir URLs.
import java.net.http.HttpClient; // Importa la clase HttpClient para realizar peticiones HTTP.
import java.net.http.HttpRequest; // Importa la clase HttpRequest para construir peticiones HTTP.
import java.net.http.HttpResponse; // Importa la clase HttpResponse para manejar respuestas HTTP.


 // Clase responsable de obtener la tasa de cambio de una API externa.

public class ServicioCambioMoneda {

    private static final String API_KEY = "TU_API_KEY"; // Reemplaza "TU_API_KEY" con tu clave de API real.
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/"; // URL base de la API.
    private final Gson gson = new Gson(); // Crea un objeto Gson para parsear JSON.


     // Obtiene la tasa de cambio desde la API para una moneda origen y destino.

    public double obtenerTasaDeCambio(String monedaOrigen, String monedaDestino) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient(); // Crea un cliente HTTP.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + monedaOrigen)) // Construye la URL de la API.
                .build(); // Crea la petición HTTP.

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // Envía la petición y obtiene la respuesta.

        if (response.statusCode() == 200) { // Verifica si la petición fue exitosa (código 200 OK).
            String responseBody = response.body(); // Obtiene el cuerpo de la respuesta.
            JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class); // Parsea el JSON con Gson.
            JsonObject rates = jsonResponse.getAsJsonObject("rates"); // Obtiene el objeto "rates" del JSON.
            if (rates != null && rates.has(monedaDestino)) { // Verifica si existen las tasas y la moneda destino.
                return rates.get(monedaDestino).getAsDouble(); // Devuelve la tasa de cambio.
            } else {
                System.out.println("La moneda destino '" + monedaDestino + "' no es soportada para la moneda origen '" + monedaOrigen + "'.");
                return -1; // Devuelve -1 para indicar que la moneda no es soportada.
            }
        } else {
            System.out.println("Error al obtener la tasa de cambio. Código de respuesta: " + response.statusCode());
            return -1; // Devuelve -1 para indicar un error.
        }
    }
}