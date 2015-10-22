/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alumno
 */
@WebServlet(urlPatterns = {"/IngresarPaciente"})
public class IngresarPaciente extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    HttpSession sesion = request.getSession(false);
    String nss = (String) sesion.getAttribute("nss");
    int inscripcion = Integer.parseInt(request.getParameter("inscripcion"));
    String apellido = request.getParameter("apellido");
    String direccion = request.getParameter("direccion");
    String fechaNac = request.getParameter("fecha_nac");
    String sexo = request.getParameter("sexo");
    
    try {
      Paciente paciente = new Paciente(nss, inscripcion, apellido, direccion,
          fechaNac, sexo);
      if (paciente.grabar())
        response.sendRedirect("correcto.html");
      else
        response.sendRedirect("error.html");
    } catch (SQLException ex) {
      response.sendRedirect("error.html");
      Logger.getLogger(IngresarPaciente.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
}
