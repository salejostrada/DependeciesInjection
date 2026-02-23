package base.servlet;

import base.context.AppContext;
import base.entity.Artist;
import base.entity.Track;
import base.service.ArtistService;
import base.service.impl.ArtistServiceImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/artists")
public class ServletArtist extends HttpServlet {

    private ArtistService artistService;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config); // Buena práctica de iniciar
        artistService = (ArtistService) AppContext.getContext().getBean("artistServiceImpl");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        InputStream is = getClass().getClassLoader().getResourceAsStream("index.html");
        if (is == null) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("No se encontró artists.html en src/main/resources");
            return;
        }
        String content = new String(is.readAllBytes());        //Armar listas
        StringBuilder artistsHtmlSb = new StringBuilder();

        for (Artist artist : artistService.getAllArtists()) {
            artistsHtmlSb.append("<li>")
                    .append(artist.getId()).append(" - ")
                    .append(artist.getName()).append(" (")
                    .append(artist.getNationality()).append(")")
                    .append("</li>");
        }
        // 3) Reemplazar placeholder
        content = content.replace("$artists", artistsHtmlSb.toString());

        //Mostrar resultado

         artistsHtmlSb.toString();

        String searchResultHtml = "";
        String searched = req.getParameter("searched"); // "true" si vienes de buscar
        if ("true".equalsIgnoreCase(searched)) {
            String name = req.getParameter("name");
            if (name == null || name.isBlank()) {
                searchResultHtml = "<p><b>Error:</b> no se recibió el nombre a buscar.</p>";
            } else {
                // OJO: tu service debería tener algo como findByNameWithTracks(name)
                // (puede devolver null si no existe)
                Artist found = artistService.getArtistByName(name);

                if (found == null) {
                    searchResultHtml = "<p>No se encontró artista con nombre: <b></b></p>";
                } else {
                    StringBuilder tracksSb = new StringBuilder("<ul>");
                    for (Track t : found.getTracks()) {
                        tracksSb.append("<li>")
                                .append(t.getTitle())
                                .append(" - ").append(t.getGenre())
                                .append(" (").append(t.getDuration()).append("s)")
                                .append(" | Album: ").append(t.getAlbumTitle())
                                .append("</li>");
                    }
                    tracksSb.append("</ul>");

                    searchResultHtml =
                            "<h3>Resultado búsqueda</h3>" +
                                    "<p><b>Id:</b> " + found.getId() + "<br>" +
                                    "<b>Nombre:</b> " + found.getName() + "<br>" +
                                    "<b>Nacionalidad:</b> " + found.getNationality() + "</p>" +
                                    "<h4>Canciones</h4>" +
                                    tracksSb;
                }
            }
        }
        content = content.replace("$searchResult", searchResultHtml);



        //mostrar
        resp.getWriter().println(content);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || action.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Falta parámetro action");
            return;
        }
        switch (action) {
            case "create":
                handleCreate(req, resp);
                break;
            case "search":
                handleSearch(req, resp);
                break;
            case "delete":
                handleDelete(req, resp);
                break;
            default:
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Action inválida: " + action);
        }
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String nationality = req.getParameter("nationality");

        // Validación web (presencia + formato)
        if (idStr == null || idStr.isBlank()
                || name == null || name.isBlank()
                || nationality == null || nationality.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Campos obligatorios: id, name, nationality");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("El id debe ser un número.");
            return;
        }

        Artist artist = new Artist();
        artist.setId(id);
        artist.setName(name.trim());
        artist.setNationality(nationality.trim());

        // Regla de negocio: la debería validar el service también (duplicados, etc.)
        artistService.createArtist(artist);

        resp.sendRedirect(req.getContextPath() + "/artists?msg=created");
    }

    private void handleSearch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");

        if (name == null || name.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Campo obligatorio: name");
            return;
        }

        // En vez de imprimir aquí, rediriges para que el doGet muestre el resultado
        resp.sendRedirect(req.getContextPath() + "/artists?searched=true&name=" + java.net.URLEncoder.encode(name.trim(), "UTF-8"));
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Campo obligatorio: id");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("El id debe ser un número.");
            return;
        }

        artistService.deleteArtist(id);

        resp.sendRedirect(req.getContextPath() + "/artists?msg=deleted");
    }
}
