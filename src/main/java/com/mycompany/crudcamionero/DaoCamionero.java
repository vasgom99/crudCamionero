package com.mycompany.crudcamionero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Métodos adaptados de las plantillas DAO de NetBeans. */
public class DaoCamionero {
    public List<Camionero> listar() throws SQLException {
        List<Camionero> lista = new ArrayList<>();
        String sql = "SELECT idCamionero, Nombre, Telefono, Direccion, Salario, Poblacion FROM Camionero ORDER BY idCamionero DESC";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Camionero objeto = new Camionero();
                objeto.setIdCamionero(rs.getInt("idCamionero"));
                objeto.setNombre(rs.getString("Nombre"));
                objeto.setTelefono(rs.getString("Telefono"));
                objeto.setDireccion(rs.getString("Direccion"));
                objeto.setSalario(rs.getDouble("Salario"));
                objeto.setPoblacion(rs.getString("Poblacion"));
                lista.add(objeto);
            }
        }
        return lista;
    }

    public void insertar(Camionero objeto) throws SQLException {
        validar(objeto);
        String sql = "INSERT INTO Camionero (Nombre, Telefono, Direccion, Salario, Poblacion) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, objeto.getNombre());
            ps.setString(2, objeto.getTelefono());
            ps.setString(3, objeto.getDireccion());
            ps.setDouble(4, objeto.getSalario());
            ps.setString(5, objeto.getPoblacion());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el registro.", "02000");
            }
        }
    }

    public void actualizar(Camionero objeto) throws SQLException {
        validar(objeto);
        Validaciones.id(objeto.getIdCamionero(), "El identificador");
        String sql = "UPDATE Camionero SET Nombre = ?, Telefono = ?, Direccion = ?, Salario = ?, Poblacion = ? "
                + "WHERE idCamionero = ?";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, objeto.getNombre());
            ps.setString(2, objeto.getTelefono());
            ps.setString(3, objeto.getDireccion());
            ps.setDouble(4, objeto.getSalario());
            ps.setString(5, objeto.getPoblacion());
            ps.setInt(6, objeto.getIdCamionero());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró el registro para actualizar.", "02000");
            }
        }
    }

    public void eliminar(int id) throws SQLException {
        Validaciones.id(id, "El identificador");
        String sql = "DELETE FROM Camionero WHERE idCamionero = ?";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró el registro para eliminar.", "02000");
            }
        }
    }

    public void validar(Camionero objeto) {
        if (objeto == null) {
            throw new IllegalArgumentException("Ingrese los datos del registro.");
        }
        objeto.setNombre(Validaciones.texto(objeto.getNombre(), "El nombre", 60));
        objeto.setTelefono(Validaciones.texto(objeto.getTelefono(), "El teléfono", 20));
        objeto.setDireccion(Validaciones.texto(objeto.getDireccion(), "La dirección", 100));
        objeto.setPoblacion(Validaciones.texto(objeto.getPoblacion(), "La población", 45));
        if (!objeto.getTelefono().matches("[+()0-9 .-]+")
                || objeto.getTelefono().replaceAll("\\D", "").length() < 7) {
            throw new IllegalArgumentException("El teléfono debe contener al menos 7 dígitos.");
        }
        Validaciones.salario(objeto.getSalario());
    }
}
