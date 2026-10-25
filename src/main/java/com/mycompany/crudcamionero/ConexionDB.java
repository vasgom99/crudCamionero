package com.mycompany.crudcamionero;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {
    public static Connection abrir() throws SQLException {
        Properties datos = new Properties();
        File archivo = new File("database.properties");

        if (archivo.isFile()) {
            try (FileInputStream entrada = new FileInputStream(archivo)) {
                datos.load(entrada);
            } catch (IOException ex) {
                throw new SQLException("No se pudo leer database.properties.", ex);
            }
        }

        String host = datos.getProperty("db.host", "127.0.0.1").trim();
        String puerto = datos.getProperty("db.port", "3306").trim();
        String base = datos.getProperty("db.name", "db_paqueteria").trim();
        String usuario = datos.getProperty("db.user", "root").trim();
        String clave = datos.getProperty("db.password", "zaxc");

        if (host.isEmpty() || base.isEmpty() || usuario.isEmpty()) {
            throw new SQLException("Revise host, base de datos y usuario en database.properties.");
        }
        try {
            int numero = Integer.parseInt(puerto);
            if (numero < 1 || numero > 65535) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            throw new SQLException("El puerto de MySQL debe estar entre 1 y 65535.", ex);
        }

        String url = "jdbc:mysql://" + host + ":" + puerto + "/" + base
                + "?useSSL=false&allowPublicKeyRetrieval=true"
                + "&serverTimezone=America/Guatemala&characterEncoding=UTF-8"
                + "&useAffectedRows=false&connectTimeout=5000&socketTimeout=10000";
        return DriverManager.getConnection(url, usuario, clave);
    }
}
