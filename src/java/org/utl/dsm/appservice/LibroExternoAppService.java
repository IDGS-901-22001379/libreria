package org.utl.dsm.appservice;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.utl.dsm.viewmodel.LibroViewModel;

public class LibroExternoAppService {
    //DECLAREN ESTA VARIABLE GLOBAL PARA RECIBIR LAS URLS

    private final List<String> urls;

    public LibroExternoAppService() {
        //LUEGO AQUI AGREGUEN TODAS LOS ENDPOINTS QUE CONSUMIRAN
        urls = new ArrayList<>();
        //LANDIN
        urls.add("http://10.16.22.59:8080/AdministracionPersonal/rest/libro/getAll");
        //ASCENCIO
//        urls.add("http://10.16.25.50:8080/bibliotecaproyecto/api/libro/getAllPublic");
//        //CARLOS
//        urls.add("http://10.16.21.34:8080/ProyectoRoca/api/libros/getAll");
//        //ANDRE
//        urls.add("http://10.16.25.226:8080/librosPDF/api/libro/getAllLibros");
//        //YAEL
//        urls.add("http://10.16.9.244:8080/escuelaMilitar/api/libro/getAll");

    }

    public List<LibroViewModel> getAll() {
        List<LibroViewModel> librosExternos = new ArrayList<>();
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();

        //AQUI HACEN UN CICLO QUE RECORRA EL REQUEST DE LOS CLIENTES
        for (String url : urls) {
            try {

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .GET()
                        .header("Content-Type", "application/json")
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Verificar si la solicitud fue exitosa (código 200)
                if (response.statusCode() == 200) {
                    // Convertir la respuesta JSON a una lista de LibroViewModel y agregar a librosExternos
                    List<LibroViewModel> libros = gson.fromJson(response.body(), new TypeToken<List<LibroViewModel>>() {
                    }.getType());
                    librosExternos.addAll(libros);
                } else {
                    System.err.println("Error al consultar el API externo en " + url + ": Código de respuesta " + response.statusCode());
                }
            } catch (Exception e) {
                System.err.println("Error al consultar el API externo en " + url + ": " + e.getMessage());
            }
        }

        return librosExternos;
    }
}
