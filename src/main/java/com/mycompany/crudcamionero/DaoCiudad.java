package com.mycompany.crudcamionero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Ciudad se usa como catálogo para el destino de los paquetes. */
public class DaoCiudad {
    public List<Ciudad> listar() throws SQLException {
        List<Ciudad> lista = new ArrayList<>();
        String sql = "SELECT idCiudad, Nombre FROM Ciudad ORDER BY Nombre";

        try (Connection cn = ConexionDB.abrir();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ciudad ciudad = new Ciudad();
                ciudad.setIdCiudad(rs.getInt("idCiudad"));
                ciudad.setNombre(rs.getString("Nombre"));
                lista.add(ciudad);
            }
        }
        return lista;
    }
}
