package servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Consulta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/tesla?user=root&password=mysqladmin";
            Connection connect = DriverManager.getConnection(url);
            Statement statement = connect.createStatement();
            String query = "SELECT * FROM vehiculo";
            ResultSet resultSet = statement.executeQuery(query);

            JsonArray jarray = new JsonArray();
            JsonObject gson = new JsonObject();
            while (resultSet.next()) {
                int id_vehiculo = resultSet.getInt("id_vehiculo");
                String marca = resultSet.getString("marca");
                int precio = resultSet.getInt("precio");
                String color = resultSet.getString("color");

                gson = new JsonObject();
                gson.addProperty("id_vehiculo", id_vehiculo);
                gson.addProperty("marca", marca);
                gson.addProperty("precio", precio);
                gson.addProperty("color", color);
                jarray.add(gson);
            }
            out.println(jarray.toString());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
