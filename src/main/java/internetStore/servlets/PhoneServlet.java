package internetStore.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import internetStore.dao.PhoneDao;
import internetStore.models.Phone;
import internetStore.util.ContextPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/phones/*")
public class PhoneServlet extends HttpServlet {
    private final PhoneDao phoneDao = PhoneDao.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contextPath = req.getPathInfo();
        resp.setContentType("application/json");

        if (contextPath == null || contextPath.equals("/")) {
            List<Phone> allPhones = phoneDao.getAllPhone();
            String json = objectMapper.writeValueAsString(allPhones);
            resp.getWriter().println(json);
        } else {
            try {
                int phoneId = ContextPath.idFromPath(contextPath);
                Phone phone = phoneDao.getById(phoneId);
                if (phone != null) {
                    String json = objectMapper.writeValueAsString(phone);
                    resp.getWriter().println(json);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println("Phone not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Invalid phone ID format");
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (BufferedReader bufferedReader = req.getReader()) {
            String json = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
            Phone phone = objectMapper.readValue(json, Phone.class);
            phoneDao.savePhone(phone);
            System.out.println(json);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Error reading request body");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contextPath = req.getPathInfo();
        if (contextPath != null && contextPath.startsWith("/")) {
            try {
                int phoneId = ContextPath.idFromPath(contextPath);

                Phone phoneToDelete = phoneDao.getById(phoneId);
                if (phoneToDelete != null) {
                    phoneDao.deleteById(phoneId);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println("The Phone with ID " + phoneId + " was deleted.");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println("Phone not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Invalid phone ID format");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Invalid format path.");
        }
    }
}
