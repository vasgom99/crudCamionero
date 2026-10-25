package com.mycompany.crudcamionero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Métodos adaptados de las plantillas DAO de NetBeans. */
public class DaoPaquete {
    public List<Paquete> listar() throws SQLException {
        List<Paquete> lista = new ArrayList<>();
        String sql = "SELECT idPaquete, Descripcion, Destinatario, DireccionEnvio, idCamionero, idCiudad FROM Paquete ORDER BY idPaquete DESC";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Paquete objeto = new Paquete();
                objeto.setIdPaquete(rs.getInt("idPaquete"));
                objeto.setDescripcion(rs.getString("Descripcion"));
                objeto.setDestinatario(rs.getString("Destinatario"));
                objeto.setDireccionEnvio(rs.getString("DireccionEnvio"));
                objeto.setIdCamionero(rs.getInt("idCamionero"));
                objeto.setIdCiudad(rs.getInt("idCiudad"));
                lista.add(objeto);
            }
        }
        return lista;
    }

    public void insertar(Paquete objeto) throws SQLException {
        validar(objeto);
        String sql = "INSERT INTO Paquete (Descripcion, Destinatario, DireccionEnvio, idCamionero, idCiudad) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, objeto.getDescripcion());
            ps.setString(2, objeto.getDestinatario());
            ps.setString(3, objeto.getDireccionEnvio());
            ps.setInt(4, objeto.getIdCamionero());
            ps.setInt(5, objeto.getIdCiudad());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el registro.", "02000");
            }
        }
    }

    public void actualizar(Paquete objeto) throws SQLException {
        validar(objeto);
        Validaciones.id(objeto.getIdPaquete(), "El identificador");
        String sql = "UPDATE Paquete SET Descripcion = ?, Destinatario = ?, DireccionEnvio = ?, idCamionero = ?, idCiudad = ? "
                + "WHERE idPaquete = ?";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, objeto.getDescripcion());
            ps.setString(2, objeto.getDestinatario());
            ps.setString(3, objeto.getDireccionEnvio());
            ps.setInt(4, objeto.getIdCamionero());
            ps.setInt(5, objeto.getIdCiudad());
            ps.setInt(6, objeto.getIdPaquete());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró el registro para actualizar.", "02000");
            }
        }
    }

    public void eliminar(int id) throws SQLException {
        Validaciones.id(id, "El identificador");
        String sql = "DELETE FROM Paquete WHERE idPaquete = ?";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró el registro para eliminar.", "02000");
            }
        }
    }

    public void validar(Paquete objeto) {
        if (objeto == null) {
            throw new IllegalArgumentException("Ingrese los datos del registro.");
        }
        objeto.setDescripcion(Validaciones.texto(objeto.getDescripcion(), "La descripción", 45));
        objeto.setDestinatario(Validaciones.texto(objeto.getDestinatario(), "El destinatario", 45));
        objeto.setDireccionEnvio(Validaciones.texto(objeto.getDireccionEnvio(), "La dirección de envío", 45));
        Validaciones.id(objeto.getIdCamionero(), "El camionero");
        Validaciones.id(objeto.getIdCiudad(), "La ciudad");
    }
}
