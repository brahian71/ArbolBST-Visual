package vista;

import modelo.ArbolBST;
import modelo.Nodo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private final ArbolBST arbol;
    private PanelArbol panelArbol;
    private JTextField campoDato;
    private JTextArea areaResultados;

    private JLabel etqPeso, etqAltura, etqHojas, etqMenor, etqMayor, etqVacio;

    public VentanaPrincipal() {
        super("Árbol Binario de Búsqueda — Estructuras de Datos");
        this.arbol = new ArbolBST();
        cargarEjemplo();
        construirUI();
        actualizarEstadisticas();
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void cargarEjemplo() {
        for (int v : new int[]{ 34, 70, 22, 40, 30, 80, 10, 35, 65, 90})
            arbol.agregar(v);
    }

    private void construirUI() {
        setLayout(new BorderLayout(8, 8));
        add(panelOperaciones(), BorderLayout.NORTH);
        panelArbol = new PanelArbol(arbol);
        add(new JScrollPane(panelArbol), BorderLayout.CENTER);
        add(panelLateral(), BorderLayout.EAST);
        add(panelResultados(), BorderLayout.SOUTH);
    }

    private JPanel panelOperaciones() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        p.setBorder(new TitledBorder("Operaciones"));
        p.add(new JLabel("Valor:"));
        campoDato = new JTextField(8);
        p.add(campoDato);

        agregarBoton(p, "Insertar",        e -> accionInsertar());
        agregarBoton(p, "Eliminar",        e -> accionEliminar());
        agregarBoton(p, "Buscar",          e -> accionBuscar());
        agregarBoton(p, "Nivel del nodo",  e -> accionNivel());
        agregarBoton(p, "Quitar resaltado",e -> panelArbol.limpiarResaltado());
        agregarBoton(p, "Borrar árbol",    e -> accionBorrarArbol());
        return p;
    }

    private void agregarBoton(JPanel p, String texto, java.awt.event.ActionListener al) {
        JButton btn = new JButton(texto);
        btn.addActionListener(al);
        p.add(btn);
    }

    private JPanel panelLateral() {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setPreferredSize(new Dimension(230, 0));

        JPanel stats = new JPanel(new GridLayout(6, 1, 0, 4));
        stats.setBorder(new TitledBorder("Estadísticas"));
        etqPeso   = new JLabel(); etqAltura = new JLabel();
        etqHojas  = new JLabel(); etqMenor  = new JLabel();
        etqMayor  = new JLabel(); etqVacio  = new JLabel();
        for (JLabel l : new JLabel[]{etqPeso, etqAltura, etqHojas, etqMenor, etqMayor, etqVacio})
            stats.add(l);

        JPanel recorridos = new JPanel(new GridLayout(4, 1, 0, 6));
        recorridos.setBorder(new TitledBorder("Recorridos"));
        agregarBoton(recorridos, "Inorden",   e -> log("Inorden",   arbol.recorrerInorden()));
        agregarBoton(recorridos, "Preorden",  e -> log("Preorden",  arbol.recorrerPreorden()));
        agregarBoton(recorridos, "Postorden", e -> log("Postorden", arbol.recorrerPostorden()));
        agregarBoton(recorridos, "Amplitud",  e -> log("Amplitud",  arbol.imprimirAmplitud()));

        contenedor.add(stats);
        contenedor.add(recorridos);
        return contenedor;
    }

    private JScrollPane panelResultados() {
        areaResultados = new JTextArea(6, 60);
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane sp = new JScrollPane(areaResultados);
        sp.setBorder(new TitledBorder("Resultados"));
        return sp;
    }

    private void accionInsertar() {
        Integer v = leerValor(); if (v == null) return;
        if (arbol.existe(v)) { log("Insertar", v + " ya existe."); return; }
        arbol.agregar(v);
        log("Insertar", v + " insertado.");
        refrescar();
    }

    private void accionEliminar() {
        Integer v = leerValor(); if (v == null) return;
        if (!arbol.existe(v)) { log("Eliminar", v + " no existe."); return; }
        arbol.eliminar(v);
        log("Eliminar", v + " eliminado.");
        refrescar();
    }

    private void accionBuscar() {
        Integer v = leerValor(); if (v == null) return;
        Nodo nodo = arbol.buscarNodo(v);
        if (nodo == null) {
            log("Buscar", v + " no encontrado.");
            panelArbol.limpiarResaltado();
            return;
        }
        log("Buscar", v + " encontrado en nivel " + arbol.obtenerNivel(v) + ".");
        panelArbol.resaltarNodo(nodo);
    }

    private void accionNivel() {
        Integer v = leerValor(); if (v == null) return;
        int nivel = arbol.obtenerNivel(v);
        log("Nivel", nivel < 0 ? v + " no existe." : v + " está en nivel " + nivel + ".");
    }

    private void accionBorrarArbol() {
        int op = JOptionPane.showConfirmDialog(this, "¿Borrar todo el árbol?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op != JOptionPane.YES_OPTION) return;
        arbol.borrarArbol();
        log("Borrar", "Árbol vaciado.");
        refrescar();
    }

    private Integer leerValor() {
        String txt = campoDato.getText().trim();
        if (txt.isEmpty()) { log("Aviso", "Ingresa un valor."); return null; }
        try { return Integer.parseInt(txt); }
        catch (NumberFormatException e) { log("Aviso", "'" + txt + "' no es un entero."); return null; }
    }

    private void refrescar() {
        actualizarEstadisticas();
        panelArbol.limpiarResaltado();
        panelArbol.repaint();
    }

    private void actualizarEstadisticas() {
        etqPeso.setText("  Peso: "   + arbol.obtenerPeso());
        etqAltura.setText("  Altura: " + arbol.obtenerAltura());
        etqHojas.setText("  Hojas: "  + arbol.contarHojas());
        etqMenor.setText("  Menor: "  + (arbol.obtenerMenor() == null ? "—" : arbol.obtenerMenor()));
        etqMayor.setText("  Mayor: "  + (arbol.obtenerMayor() == null ? "—" : arbol.obtenerMayor()));
        etqVacio.setText("  Vacío: "  + (arbol.estaVacio() ? "Sí" : "No"));
    }

    private void log(String titulo, String msg) {
        areaResultados.append("[" + titulo + "] " + msg + "\n");
        areaResultados.setCaretPosition(areaResultados.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}