package gm.zona_fit.gui;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.ClienteServicio;
import gm.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ZonaFitForma extends JFrame{
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton eliminarButton;
    IClienteServicio clienteServicio;
    private DefaultTableModel tablaModeloClientes;
    private Integer idCliente;

    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
        guardarButton.addActionListener(e -> guardarCliente());
        limpiarButton.addActionListener(e -> limpiarFormulario());
        eliminarButton.addActionListener(e -> eliminarCliente());
        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
    }

    private void iniciarForma() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }

    /* Es un metodo especial que se ejecuta antes del constructor.
    * Por eso se realizo la inyeccion de dependencias antes del constructor
    * y no con un atributo como antes (ejemplo ZonaZitSpring)
    * para que ya esten las depencencias cargadas.
    * En este metodo se crean de manera manual los componentes
    * de swing que se marquen con la opcion custom Create.
     */
    private void createUIComponents() {
        this.tablaModeloClientes = new DefaultTableModel(0, 4) {
            //para desactivar la escritura en el campo de texto
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] cabeceras = {"Id", "Nombre", "Apellido", "Membresia"};
        this.tablaModeloClientes.setColumnIdentifiers(cabeceras);
        this.clientesTabla = new JTable(tablaModeloClientes);

        //Permitir selección de filas completas
        this.clientesTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.clientesTabla.setRowSelectionAllowed(true);
        this.clientesTabla.setColumnSelectionAllowed(false);
        listarClientes();
    }

    private void listarClientes() {
        this.tablaModeloClientes.setRowCount(0);
        var listaClientes = this.clienteServicio.listarClientes();
        listaClientes.forEach(cliente -> {
            Object[] renglonCliente = {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()
            };
            this.tablaModeloClientes.addRow(renglonCliente);
        });
    }

    private void guardarCliente() {

        if(nombreTexto.getText().isEmpty()){
            mostrarMensaje("Proporciona un nombre");
            nombreTexto.requestFocusInWindow();
            return;
        }
        if(apellidoTexto.getText().isEmpty()){
            mostrarMensaje("Proporciona un apellido");
            apellidoTexto.requestFocusInWindow();
            return;
        }
        if(membresiaTexto.getText().isEmpty()){
            mostrarMensaje("Proporciona una membresia");
            membresiaTexto.requestFocusInWindow();
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombreTexto.getText().strip());
        cliente.setApellido(apellidoTexto.getText().strip());

        try{
            var membresia = Integer.parseInt(membresiaTexto.getText().strip());
            /* si la membresia es igual a la que el mismo cliente tiene
            * ignora el control de membresia existente
            */
            var mismaMembresia = true;

            //si el cliente ya existe
            if (idCliente != null) {
                cliente.setId(idCliente);
                mismaMembresia = clienteServicio.buscarClientePorId(idCliente)
                        .getMembresia() != membresia;
            }
            var existe = clienteServicio.buscarClientePorMembresia(membresia);

            if(existe && mismaMembresia){
                mostrarMensaje("La membresia proporsionada ya existe");
                membresiaTexto.requestFocusInWindow();
                return;
            }

            cliente.setMembresia(membresia);
            clienteServicio.guardarCliente(cliente);
            mostrarMensaje("Cliente guardado exitosamente");
            limpiarFormulario();
            listarClientes();

        }catch (Exception e){
            mostrarMensaje("Ingresa solo numeros enteros");
            membresiaTexto.requestFocusInWindow();
            System.out.println(e);
        }
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void limpiarFormulario(){
        this.idCliente = null;
        this.nombreTexto.setText("");
        this.apellidoTexto.setText("");
        this.membresiaTexto.setText("");
    }

    private void cargarClienteSeleccionado(){
        var renglon = clientesTabla.getSelectedRow();
        //-1 significa que no se selecciono ningun registro
        if(renglon != -1){
            var id = clientesTabla.getModel()
                    .getValueAt(renglon, 0).toString();
            var nombre = clientesTabla.getModel()
                    .getValueAt(renglon, 1).toString();
            var apellido = clientesTabla.getModel()
                    .getValueAt(renglon, 2).toString();
            var membresia = clientesTabla.getModel()
                    .getValueAt(renglon, 3).toString();
            idCliente = Integer.parseInt(id);
            nombreTexto.setText(nombre);
            apellidoTexto.setText(apellido);
            membresiaTexto.setText(membresia);
        }
    }

    private void eliminarCliente(){
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1) {
            var id = clientesTabla.getModel()
                    .getValueAt(renglon, 0).toString();
            idCliente = Integer.parseInt(id);
            Cliente cliente = new Cliente();
            cliente.setId(idCliente);
            clienteServicio.eliminarCliente(cliente);
            listarClientes();
            limpiarFormulario();
        }else
            mostrarMensaje("Selecciona un cliente de la tabla para eliminarlo");
    }
}
