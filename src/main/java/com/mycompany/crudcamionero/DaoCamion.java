package com.mycompany.crudcamionero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Métodos adaptados de las plantillas DAO de NetBeans. */
public class DaoCamion {
    public List<Camion> listar() throws SQLException {
        List<Camion> lista = new ArrayList<>();
        String sql = "SELECT idCamion, NoPlaca, Modelo, Tipo, Potencia FROM Camion ORDER BY idCamion DESC";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Camion objeto = new Camion();
                objeto.setIdCamion(rs.getInt("idCamion"));
                objeto.setNoPlaca(rs.getString("NoPlaca"));
                objeto.setModelo(rs.getString("Modelo"));
                objeto.setTipo(rs.getString("Tipo"));
                objeto.setPotencia(rs.getInt("Potencia"));
                lista.add(objeto);
            }
        }
        return lista;
    }

    public void insertar(Camion objeto) throws SQLException {
        validar(objeto);
        comprobarPlaca(objeto.getNoPlaca(), 0);
        String sql = "INSERT INTO Camion (NoPlaca, Modelo, Tipo, Potencia) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, objeto.getNoPlaca());
            ps.setString(2, objeto.getModelo());
            ps.setString(3, objeto.getTipo());
            ps.setInt(4, objeto.getPotencia());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el registro.", "02000");
            }
        }
    }

    public void actualizar(Camion objeto) throws SQLException {
        validar(objeto);
        Validaciones.id(objeto.getIdCamion(), "El identificador");
        comprobarPlaca(objeto.getNoPlaca(), objeto.getIdCamion());
        String sql = "UPDATE Camion SET NoPlaca = ?, Modelo = ?, Tipo = ?, Potencia = ? "
                + "WHERE idCamion = ?";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, objeto.getNoPlaca());
            ps.setString(2, objeto.getModelo());
            ps.setString(3, objeto.getTipo());
            ps.setInt(4, objeto.getPotencia());
            ps.setInt(5, objeto.getIdCamion());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró el registro para actualizar.", "02000");
            }
        }
    }

    public void eliminar(int id) throws SQLException {
        Validaciones.id(id, "El identificador");
        String sql = "DELETE FROM Camion WHERE idCamion = ?";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró el registro para eliminar.", "02000");
            }
        }
    }

    public void validar(Camion objeto) {
        if (objeto == null) {
            throw new IllegalArgumentException("Ingrese los datos del registro.");
        }
        objeto.setNoPlaca(Validaciones.texto(objeto.getNoPlaca(), "La placa", 45));
        objeto.setModelo(Validaciones.texto(objeto.getModelo(), "El modelo", 45));
        objeto.setTipo(Validaciones.texto(objeto.getTipo(), "El tipo", 45));
        if (!objeto.getNoPlaca().matches("[A-Za-z0-9][A-Za-z0-9 -]*")) {
            throw new IllegalArgumentException("La placa solo admite letras, números, espacios y guiones.");
        }
        if (objeto.getPotencia() <= 0) {
            throw new IllegalArgumentException("La potencia debe ser mayor que cero.");
        }
    }

    private void comprobarPlaca(String placa, int idActual) throws SQLException {
        String sql = "SELECT 1 FROM Camion WHERE NoPlaca = ? AND idCamion <> ? LIMIT 1";
        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, placa);
            ps.setInt(2, idActual);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    throw new IllegalArgumentException("Ya existe un camión con esa placa.");
                }
            }
        }
    }
}
