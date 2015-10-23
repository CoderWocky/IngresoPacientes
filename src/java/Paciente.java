/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.time.LocalDate;

/**
 *
 * @author alumno
 */
public class Paciente {
  private Connection conexion;
  private int nss;
  private int inscripcion;
  private String apellido;
  private String direccion;
  private Date fechaNac;
  private String sexo;
  
  public Paciente(String nss) throws SQLException {
    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    this.conexion = DriverManager.getConnection(
        "jdbc:oracle:thin:@localhost:1521:XE", "system", "javaoracle");
    this.nss = Integer.parseInt(nss);
  }
  
  public Paciente(String nss, int inscripcion, String apellido, String direccion, 
      String fechaNac, String sexo) throws SQLException {
    this(nss);
    this.inscripcion = inscripcion;
    this.apellido = apellido;
    this.direccion = direccion;
    this.fechaNac = Date.valueOf(LocalDate.parse(fechaNac));
    this.sexo = sexo;
  }
  
  public boolean isIngresado() throws SQLException {
    String query = "{ ? = call PacienteIngresado(?) }";
    CallableStatement sql = this.conexion.prepareCall(query);
    sql.registerOutParameter(1, java.sql.Types.INTEGER);
    sql.setInt(2, this.nss);
    sql.executeQuery();
    sql.close();
    return (sql.getInt(1) != 0);
  }
  
  public boolean grabar() {
    String query = "{ call InsertaEnfermo(?, ?, ?, ?, ?, ?) }";
    try {
      CallableStatement sql = this.conexion.prepareCall(query);
      sql.setInt(1, this.inscripcion);
      sql.setString(2, this.apellido);
      sql.setString(3, this.direccion);
      sql.setDate(4, this.fechaNac);
      sql.setString(5, this.sexo);
      sql.setInt(6, this.nss);
      
      sql.executeUpdate();
      return true;
    } catch (SQLException ex) {
      return false;
    }
  }
}
