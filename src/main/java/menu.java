/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import com.mycompany.crudcamionero.Camion;
import com.mycompany.crudcamionero.Camionero;
import com.mycompany.crudcamionero.DaoCamion;
import com.mycompany.crudcamionero.DaoCamionero;
import com.mycompany.crudcamionero.DaoPaquete;
import com.mycompany.crudcamionero.Paquete;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cui
 */
public class menu extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(menu.class.getName());

    /**
     * Creates new form menu
     */
    public menu() {
        initComponents();
        prepararVentana();
    }

    private final DaoCamion daoCamion = new DaoCamion();
    private final DaoCamionero daoCamionero = new DaoCamionero();
    private final DaoPaquete daoPaquete = new DaoPaquete();

    private void prepararVentana() {
        setTitle("CRUD - Paquetería");
        setLocationRelativeTo(null);

        txtIdCamion.setEditable(false);
        txtIdCamionero.setEditable(false);
        txtIdPaquete.setEditable(false);

        btnInsertarCamion.addActionListener(e -> insertarCamion());
        btnActualizarCamion.addActionListener(e -> actualizarCamion());
        btnEliminarCamion.addActionListener(e -> eliminarCamion());
        btnListarCamion.addActionListener(e -> listarCamiones());
        btnLimpiarCamion.addActionListener(e -> limpiarCamion());
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                seleccionarCamion();
            }
        });

        btnInsertarCamionero.addActionListener(e -> insertarCamionero());
        btnActualizarCamionero.addActionListener(e -> actualizarCamionero());
        btnEliminarCamionero.addActionListener(e -> eliminarCamionero());
        btnListarCamionero.addActionListener(e -> listarCamioneros());
        btnLimpiarCamionero.addActionListener(e -> limpiarCamionero());
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                seleccionarCamionero();
            }
        });

        btnListarPaquete.addActionListener(e -> listarPaquetes());
        btnEliminarPaquete.addActionListener(e -> eliminarPaquete());
        btnLimpiarPaquete.addActionListener(e -> limpiarPaquete());
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                seleccionarPaquete();
            }
        });

        prepararTablas();
    }

    private void prepararTablas() {
        jTable2.setModel(new DefaultTableModel(
                new Object[]{"ID", "Placa", "Modelo", "Tipo", "Potencia"}, 0));
        jTable3.setModel(new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Teléfono", "Dirección", "Salario", "Población"}, 0));
        jTable4.setModel(new DefaultTableModel(
                new Object[]{"ID", "Descripción", "Destinatario", "Dirección", "ID Camionero", "ID Ciudad"}, 0));
    }

    private Camion leerCamion() {
        Camion camion = new Camion();
        if (!txtIdCamion.getText().isBlank()) {
            camion.setIdCamion(Integer.parseInt(txtIdCamion.getText()));
        }
        camion.setNoPlaca(txtPlaca.getText());
        camion.setModelo(txtModelo.getText());
        camion.setTipo(txtTipo.getText());
        camion.setPotencia(Integer.parseInt(txtPotencia.getText()));
        return camion;
    }

    private void insertarCamion() {
        try {
            daoCamion.insertar(leerCamion());
            mensaje("Camión insertado correctamente.");
            limpiarCamion();
            listarCamiones();
        } catch (Exception e) {
            error(e);
        }
    }

    private void actualizarCamion() {
        try {
            daoCamion.actualizar(leerCamion());
            mensaje("Camión actualizado correctamente.");
            limpiarCamion();
            listarCamiones();
        } catch (Exception e) {
            error(e);
        }
    }

    private void eliminarCamion() {
        try {
            int id = Integer.parseInt(txtIdCamion.getText());
            if (confirmarEliminar()) {
                daoCamion.eliminar(id);
                limpiarCamion();
                listarCamiones();
            }
        } catch (Exception e) {
            error(e);
        }
    }

    private void listarCamiones() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
            modelo.setRowCount(0);
            for (Camion camion : daoCamion.listar()) {
                modelo.addRow(new Object[]{camion.getIdCamion(), camion.getNoPlaca(),
                    camion.getModelo(), camion.getTipo(), camion.getPotencia()});
            }
        } catch (Exception e) {
            error(e);
        }
    }

    private void seleccionarCamion() {
        int fila = jTable2.getSelectedRow();
        if (fila >= 0) {
            txtIdCamion.setText(jTable2.getValueAt(fila, 0).toString());
            txtPlaca.setText(jTable2.getValueAt(fila, 1).toString());
            txtModelo.setText(jTable2.getValueAt(fila, 2).toString());
            txtTipo.setText(jTable2.getValueAt(fila, 3).toString());
            txtPotencia.setText(jTable2.getValueAt(fila, 4).toString());
        }
    }

    private void limpiarCamion() {
        txtIdCamion.setText("");
        txtPlaca.setText("");
        txtModelo.setText("");
        txtTipo.setText("");
        txtPotencia.setText("");
        jTable2.clearSelection();
    }

    private Camionero leerCamionero() {
        Camionero camionero = new Camionero();
        if (!txtIdCamionero.getText().isBlank()) {
            camionero.setIdCamionero(Integer.parseInt(txtIdCamionero.getText()));
        }
        camionero.setNombre(txtNombre.getText());
        camionero.setTelefono(txtTelefono.getText());
        camionero.setDireccion(txtDireccion.getText());
        camionero.setSalario(Double.parseDouble(txtSalario.getText()));
        camionero.setPoblacion(txtPoblacion.getText());
        return camionero;
    }

    private void insertarCamionero() {
        try {
            daoCamionero.insertar(leerCamionero());
            mensaje("Camionero insertado correctamente.");
            limpiarCamionero();
            listarCamioneros();
        } catch (Exception e) {
            error(e);
        }
    }

    private void actualizarCamionero() {
        try {
            daoCamionero.actualizar(leerCamionero());
            mensaje("Camionero actualizado correctamente.");
            limpiarCamionero();
            listarCamioneros();
        } catch (Exception e) {
            error(e);
        }
    }

    private void eliminarCamionero() {
        try {
            int id = Integer.parseInt(txtIdCamionero.getText());
            if (confirmarEliminar()) {
                daoCamionero.eliminar(id);
                limpiarCamionero();
                listarCamioneros();
            }
        } catch (Exception e) {
            error(e);
        }
    }

    private void listarCamioneros() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable3.getModel();
            modelo.setRowCount(0);
            for (Camionero camionero : daoCamionero.listar()) {
                modelo.addRow(new Object[]{camionero.getIdCamionero(), camionero.getNombre(),
                    camionero.getTelefono(), camionero.getDireccion(), camionero.getSalario(),
                    camionero.getPoblacion()});
            }
        } catch (Exception e) {
            error(e);
        }
    }

    private void seleccionarCamionero() {
        int fila = jTable3.getSelectedRow();
        if (fila >= 0) {
            txtIdCamionero.setText(jTable3.getValueAt(fila, 0).toString());
            txtNombre.setText(jTable3.getValueAt(fila, 1).toString());
            txtTelefono.setText(jTable3.getValueAt(fila, 2).toString());
            txtDireccion.setText(jTable3.getValueAt(fila, 3).toString());
            txtSalario.setText(jTable3.getValueAt(fila, 4).toString());
            txtPoblacion.setText(jTable3.getValueAt(fila, 5).toString());
        }
    }

    private void limpiarCamionero() {
        txtIdCamionero.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtSalario.setText("");
        txtPoblacion.setText("");
        jTable3.clearSelection();
    }

    private void listarPaquetes() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable4.getModel();
            modelo.setRowCount(0);
            for (Paquete paquete : daoPaquete.listar()) {
                modelo.addRow(new Object[]{paquete.getIdPaquete(), paquete.getDescripcion(),
                    paquete.getDestinatario(), paquete.getDireccionEnvio(),
                    paquete.getIdCamionero(), paquete.getIdCiudad()});
            }
        } catch (Exception e) {
            error(e);
        }
    }

    private void seleccionarPaquete() {
        int fila = jTable4.getSelectedRow();
        if (fila >= 0) {
            txtIdPaquete.setText(jTable4.getValueAt(fila, 0).toString());
            txtDescripcion.setText(jTable4.getValueAt(fila, 1).toString());
            txtDestinatario.setText(jTable4.getValueAt(fila, 2).toString());
            txtDireccionEnvio.setText(jTable4.getValueAt(fila, 3).toString());
        }
    }

    private void eliminarPaquete() {
        try {
            int id = Integer.parseInt(txtIdPaquete.getText());
            if (confirmarEliminar()) {
                daoPaquete.eliminar(id);
                limpiarPaquete();
                listarPaquetes();
            }
        } catch (Exception e) {
            error(e);
        }
    }

    private void limpiarPaquete() {
        txtIdPaquete.setText("");
        txtDescripcion.setText("");
        txtDestinatario.setText("");
        txtDireccionEnvio.setText("");
        jTable4.clearSelection();
    }

    private boolean confirmarEliminar() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el registro?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        return respuesta == JOptionPane.YES_OPTION;
    }

    private void mensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto);
    }

    private void error(Exception e) {
        String texto = e.getMessage();
        if (e instanceof NumberFormatException) {
            texto = "Revise los campos numéricos y seleccione un registro cuando sea necesario.";
        } else if (texto != null && texto.contains("Access denied")) {
            texto = "MySQL rechazó el usuario o la contraseña.\n"
                    + "Revise db.user y db.password en database.properties.";
        }
        JOptionPane.showMessageDialog(this, texto, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        AsignacionCamiones = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtIdCamionero = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        btnInsertarCamionero = new javax.swing.JButton();
        btnActualizarCamionero = new javax.swing.JButton();
        btnEliminarCamionero = new javax.swing.JButton();
        btnListarCamionero = new javax.swing.JButton();
        btnLimpiarCamionero = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtPoblacion = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtIdCamionCamionero = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtPlaca1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtModelo1 = new javax.swing.JTextField();
        btnInsertarCamion1 = new javax.swing.JButton();
        btnActualizarCamion1 = new javax.swing.JButton();
        btnEliminarCamion1 = new javax.swing.JButton();
        btnListarCamion1 = new javax.swing.JButton();
        btnLimpiarCamion1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtIdPaquete = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtDestinatario = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtDireccionEnvio = new javax.swing.JTextField();
        btnInsertarPaquete = new javax.swing.JButton();
        btnActualizarPaquete = new javax.swing.JButton();
        btnEliminarPaquete = new javax.swing.JButton();
        btnListarPaquete = new javax.swing.JButton();
        btnLimpiarPaquete = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtIdCamion = new javax.swing.JTextField();
        txtPlaca = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        txtTipo = new javax.swing.JTextField();
        txtPotencia = new javax.swing.JTextField();
        btnInsertarCamion = new javax.swing.JButton();
        btnActualizarCamion = new javax.swing.JButton();
        btnEliminarCamion = new javax.swing.JButton();
        btnListarCamion = new javax.swing.JButton();
        btnLimpiarCamion = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("ID");

        jLabel7.setText("nombre");

        jLabel8.setText("Telefono");

        jLabel9.setText("Direccion");

        jLabel10.setText("Salario");

        btnInsertarCamionero.setText("insertar");

        btnActualizarCamionero.setText("actualizar");

        btnEliminarCamionero.setText("eliminar");

        btnListarCamionero.setText("Listar");

        btnLimpiarCamionero.setText("limpiar");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jLabel11.setText("Poblacion");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIdCamionero)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                                    .addComponent(txtTelefono)
                                    .addComponent(txtDireccion)
                                    .addComponent(txtSalario)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnInsertarCamionero)
                                .addGap(36, 36, 36)
                                .addComponent(btnActualizarCamionero)
                                .addGap(59, 59, 59)
                                .addComponent(btnEliminarCamionero)
                                .addGap(52, 52, 52)
                                .addComponent(btnListarCamionero)
                                .addGap(34, 34, 34)
                                .addComponent(btnLimpiarCamionero))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(36, 36, 36)
                                .addComponent(txtPoblacion))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdCamionero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsertarCamionero)
                    .addComponent(btnActualizarCamionero)
                    .addComponent(btnEliminarCamionero)
                    .addComponent(btnListarCamionero)
                    .addComponent(btnLimpiarCamionero))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        AsignacionCamiones.addTab("Camionero", jPanel2);

        jLabel16.setText("ID");

        jLabel17.setText("Placa");

        jLabel18.setText("Modelo");

        btnInsertarCamion1.setText("insertar");

        btnActualizarCamion1.setText("actualizar");

        btnEliminarCamion1.setText("eliminar");

        btnListarCamion1.setText("Listar");

        btnLimpiarCamion1.setText("limpiar");

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 797, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(119, 119, 119)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel16)
                                .addComponent(jLabel17)
                                .addComponent(jLabel18))
                            .addGap(36, 36, 36)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtIdCamionCamionero)
                                .addComponent(txtPlaca1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                                .addComponent(txtModelo1)))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(btnInsertarCamion1)
                            .addGap(36, 36, 36)
                            .addComponent(btnActualizarCamion1)
                            .addGap(59, 59, 59)
                            .addComponent(btnEliminarCamion1)
                            .addGap(52, 52, 52)
                            .addComponent(btnListarCamion1)
                            .addGap(34, 34, 34)
                            .addComponent(btnLimpiarCamion1))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(82, 82, 82)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(120, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(48, 48, 48)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(txtIdCamionCamionero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel17)
                        .addComponent(txtPlaca1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(txtModelo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(100, 100, 100)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnInsertarCamion1)
                        .addComponent(btnActualizarCamion1)
                        .addComponent(btnEliminarCamion1)
                        .addComponent(btnListarCamion1)
                        .addComponent(btnLimpiarCamion1))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(49, 49, 49)))
        );

        AsignacionCamiones.addTab("Asignación de camiones", jPanel3);

        jLabel12.setText("ID");

        jLabel13.setText("Descripcion");

        txtDescripcion.addActionListener(this::txtDescripcionActionPerformed);

        jLabel14.setText("Destinatario");

        jLabel15.setText("Direccion envio");

        btnInsertarPaquete.setText("insertar");

        btnActualizarPaquete.setText("actualizar");

        btnEliminarPaquete.setText("eliminar");

        btnListarPaquete.setText("Listar");

        btnLimpiarPaquete.setText("limpiar");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIdPaquete, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                            .addComponent(txtDescripcion)
                            .addComponent(txtDestinatario)
                            .addComponent(txtDireccionEnvio)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnInsertarPaquete)
                        .addGap(36, 36, 36)
                        .addComponent(btnActualizarPaquete)
                        .addGap(59, 59, 59)
                        .addComponent(btnEliminarPaquete)
                        .addGap(52, 52, 52)
                        .addComponent(btnListarPaquete)
                        .addGap(34, 34, 34)
                        .addComponent(btnLimpiarPaquete))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtIdPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtDireccionEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsertarPaquete)
                    .addComponent(btnActualizarPaquete)
                    .addComponent(btnEliminarPaquete)
                    .addComponent(btnListarPaquete)
                    .addComponent(btnLimpiarPaquete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );

        AsignacionCamiones.addTab("Paquete", jPanel4);

        jLabel1.setText("ID");

        jLabel3.setText("Placa");

        jLabel4.setText("Modelo");

        jLabel5.setText("Tipo");

        jLabel6.setText("Potencia");

        btnInsertarCamion.setText("insertar");

        btnActualizarCamion.setText("actualizar");

        btnEliminarCamion.setText("eliminar");

        btnListarCamion.setText("Listar");

        btnLimpiarCamion.setText("limpiar");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIdCamion)
                            .addComponent(txtPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                            .addComponent(txtModelo)
                            .addComponent(txtTipo)
                            .addComponent(txtPotencia)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(btnInsertarCamion)
                        .addGap(36, 36, 36)
                        .addComponent(btnActualizarCamion)
                        .addGap(59, 59, 59)
                        .addComponent(btnEliminarCamion)
                        .addGap(52, 52, 52)
                        .addComponent(btnListarCamion)
                        .addGap(34, 34, 34)
                        .addComponent(btnLimpiarCamion))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdCamion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPotencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsertarCamion)
                    .addComponent(btnActualizarCamion)
                    .addComponent(btnEliminarCamion)
                    .addComponent(btnListarCamion)
                    .addComponent(btnLimpiarCamion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        AsignacionCamiones.addTab("Camion", jPanel5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(AsignacionCamiones, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AsignacionCamiones)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 87, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new menu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane AsignacionCamiones;
    private javax.swing.JButton btnActualizarCamion;
    private javax.swing.JButton btnActualizarCamion1;
    private javax.swing.JButton btnActualizarCamionero;
    private javax.swing.JButton btnActualizarPaquete;
    private javax.swing.JButton btnEliminarCamion;
    private javax.swing.JButton btnEliminarCamion1;
    private javax.swing.JButton btnEliminarCamionero;
    private javax.swing.JButton btnEliminarPaquete;
    private javax.swing.JButton btnInsertarCamion;
    private javax.swing.JButton btnInsertarCamion1;
    private javax.swing.JButton btnInsertarCamionero;
    private javax.swing.JButton btnInsertarPaquete;
    private javax.swing.JButton btnLimpiarCamion;
    private javax.swing.JButton btnLimpiarCamion1;
    private javax.swing.JButton btnLimpiarCamionero;
    private javax.swing.JButton btnLimpiarPaquete;
    private javax.swing.JButton btnListarCamion;
    private javax.swing.JButton btnListarCamion1;
    private javax.swing.JButton btnListarCamionero;
    private javax.swing.JButton btnListarPaquete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDestinatario;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDireccionEnvio;
    private javax.swing.JTextField txtIdCamion;
    private javax.swing.JTextField txtIdCamionCamionero;
    private javax.swing.JTextField txtIdCamionero;
    private javax.swing.JTextField txtIdPaquete;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtModelo1;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JTextField txtPlaca1;
    private javax.swing.JTextField txtPoblacion;
    private javax.swing.JTextField txtPotencia;
    private javax.swing.JTextField txtSalario;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables
}
