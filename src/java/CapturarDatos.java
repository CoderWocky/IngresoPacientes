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
@WebServlet(urlPatterns = {"/CapturarDatos"})
public class CapturarDatos extends HttpServlet {

  private boolean pacienteIngresado(String nss) {
    
    return false;
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String nss = request.getParameter("nss");
    HttpSession sesion = request.getSession();
    sesion.setAttribute("nss", nss);
    try {
      Paciente paciente = new Paciente(nss);
      if (paciente.isIngresado())
        response.sendRedirect("yaexistente.html");
      else
        response.sendRedirect("formulario.html");
    } catch (SQLException ex) {
      response.sendRedirect("error.html");
      Logger.getLogger(CapturarDatos.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
