package vista;

import modelo.ArbolBST;
import modelo.Nodo;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class PanelArbol extends JPanel {

    private static final int RADIO = 22;
    private static final int ESPACIO_VERTICAL = 75;
    private static final int MARGEN_VERTICAL = 45;

    private ArbolBST arbol;
    private Nodo nodoResaltado;

    public PanelArbol(ArbolBST arbol) {
        this.arbol = arbol;
        this.nodoResaltado = null;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 500));
    }

    public void resaltarNodo(Nodo n) {
        this.nodoResaltado = n;
        repaint();
    }

    public void limpiarResaltado() {
        this.nodoResaltado = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));

        if (arbol == null || arbol.estaVacio()) {
            g2.setColor(Color.GRAY);
            String msg = "El árbol está vacío.";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
            return;
        }
        dibujarSubarbol(g2, arbol.getRaiz(), 0, getWidth(), MARGEN_VERTICAL);
    }

    private void dibujarSubarbol(Graphics2D g, Nodo nodo, int xMin, int xMax, int y) {
        if (nodo == null) return;
        int x = (xMin + xMax) / 2;
        int yHijos = y + ESPACIO_VERTICAL;

        g.setColor(new Color(80, 80, 80));
        g.setStroke(new BasicStroke(1.5f));
        if (nodo.getIzquierdo() != null) {
            g.drawLine(x, y, (xMin + x) / 2, yHijos);
            dibujarSubarbol(g, nodo.getIzquierdo(), xMin, x, yHijos);
        }
        if (nodo.getDerecho() != null) {
            g.drawLine(x, y, (x + xMax) / 2, yHijos);
            dibujarSubarbol(g, nodo.getDerecho(), x, xMax, yHijos);
        }
        dibujarNodo(g, nodo, x, y);
    }

    private void dibujarNodo(Graphics2D g, Nodo nodo, int x, int y) {
        boolean resaltado = (nodo == nodoResaltado);
        g.setColor(resaltado ? new Color(255, 215, 0) : new Color(173, 216, 230));
        g.fillOval(x - RADIO, y - RADIO, RADIO * 2, RADIO * 2);
        g.setColor(new Color(0, 70, 110));
        g.setStroke(new BasicStroke(2f));
        g.drawOval(x - RADIO, y - RADIO, RADIO * 2, RADIO * 2);

        String texto = String.valueOf(nodo.getDato());
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.BLACK);
        g.drawString(texto, x - fm.stringWidth(texto) / 2, y + fm.getAscent() / 2 - 2);
    }
}