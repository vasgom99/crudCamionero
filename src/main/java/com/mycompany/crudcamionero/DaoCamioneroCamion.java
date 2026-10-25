package com.mycompany.crudcamionero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Métodos adaptados de las plantillas DAO de NetBeans. */
public class DaoCamioneroCamion {
    public List<CamioneroCamion> listar() throws SQLException {
        List<CamioneroCamion> lista = new ArrayList<>();
        String sql = "SELECT idCamioneroCamion, idCamionero, idCamion FROM CamioneroCamion ORDER BY idCamioneroCamion DESC";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CamioneroCamion objeto = new CamioneroCamion();
                objeto.setIdCamioneroCamion(rs.getInt("idCamioneroCamion"));
                objeto.setIdCamionero(rs.getInt("idCamionero"));
                objeto.setIdCamion(rs.getInt("idCamion"));
                lista.add(objeto);
            }
        }
        return lista;
    }

    public void insertar(CamioneroCamion objeto) throws SQLException {
        validar(objeto);
        comprobarAsignacion(objeto, 0);
        String sql = "INSERT INTO CamioneroCamion (idCamionero, idCamion) "
                + "VALUES (?, ?)";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, objeto.getIdCamionero());
            ps.setInt(2, objeto.getIdCamion());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el registro.", "02000");
            }
        }
    }

    public void actualizar(CamioneroCamion objeto) throws SQLException {
        validar(objeto);
        Validaciones.id(objeto.getIdCamioneroCamion(), "El identificador");
        comprobarAsignacion(objeto, objeto.getIdCamioneroCamion());
        String sql = "UPDATE CamioneroCamion SET idCamionero = ?, idCamion = ? "
                + "WHERE idCamioneroCamion = ?";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, objeto.getIdCamionero());
            ps.setInt(2, objeto.getIdCamion());
            ps.setInt(3, objeto.getIdCamioneroCamion());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró el registro para actualizar.", "02000");
            }
        }
    }

    public void eliminar(int id) throws SQLException {
        Validaciones.id(id, "El identificador");
        String sql = "DELETE FROM CamioneroCamion WHERE idCamioneroCamion = ?";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró el registro para eliminar.", "02000");
            }
        }
    }

    public void validar(CamioneroCamion objeto) {
        if (objeto == null) {
            throw new IllegalArgumentException("Ingrese los datos del registro.");
        }
        Validaciones.id(objeto.getIdCamionero(), "El camionero");
        Validaciones.id(objeto.getIdCamion(), "El camión");
    }

    private void comprobarAsignacion(CamioneroCamion objeto, int idActual) throws SQLException {
        String sql = "SELECT 1 FROM CamioneroCamion WHERE idCamionero = ? AND idCamion = ? "
                + "AND idCamioneroCamion <> ? LIMIT 1";
        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, objeto.getIdCamionero());
            ps.setInt(2, objeto.getIdCamion());
            ps.setInt(3, idActual);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    throw new IllegalArgumentException("El camionero ya está asignado a ese camión.");
                }
            }
        }
    }
}
