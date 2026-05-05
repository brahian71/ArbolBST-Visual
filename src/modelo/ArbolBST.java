package modelo;

import java.util.LinkedList;
import java.util.Queue;

public class ArbolBST {

    private Nodo raiz;

    public ArbolBST() {
        this.raiz = null;
    }

    public Nodo getRaiz() { return raiz; }

    public boolean estaVacio() {
        return raiz == null;
    }

    public void borrarArbol() {
        raiz = null;
    }

    public void agregar(int dato) {
        raiz = agregarRec(raiz, dato);
    }

    private Nodo agregarRec(Nodo actual, int dato) {
        if (actual == null) return new Nodo(dato);
        if (dato < actual.getDato())
            actual.setIzquierdo(agregarRec(actual.getIzquierdo(), dato));
        else if (dato > actual.getDato())
            actual.setDerecho(agregarRec(actual.getDerecho(), dato));
        return actual;
    }

    public boolean existe(int dato) {
        return buscarNodo(raiz, dato) != null;
    }

    public Nodo buscarNodo(int dato) {
        return buscarNodo(raiz, dato);
    }

    private Nodo buscarNodo(Nodo actual, int dato) {
        if (actual == null) return null;
        if (dato == actual.getDato()) return actual;
        if (dato < actual.getDato()) return buscarNodo(actual.getIzquierdo(), dato);
        return buscarNodo(actual.getDerecho(), dato);
    }

    public void eliminar(int dato) {
        raiz = eliminarRec(raiz, dato);
    }

    private Nodo eliminarRec(Nodo actual, int dato) {
        if (actual == null) return null;
        if (dato < actual.getDato()) {
            actual.setIzquierdo(eliminarRec(actual.getIzquierdo(), dato));
        } else if (dato > actual.getDato()) {
            actual.setDerecho(eliminarRec(actual.getDerecho(), dato));
        } else {
            if (actual.getIzquierdo() == null) return actual.getDerecho();
            if (actual.getDerecho() == null) return actual.getIzquierdo();
            int sucesor = menorDato(actual.getDerecho());
            actual.setDato(sucesor);
            actual.setDerecho(eliminarRec(actual.getDerecho(), sucesor));
        }
        return actual;
    }

    private int menorDato(Nodo desde) {
        Nodo actual = desde;
        while (actual.getIzquierdo() != null)
            actual = actual.getIzquierdo();
        return actual.getDato();
    }

    public int obtenerPeso() {
        return contarNodos(raiz);
    }

    private int contarNodos(Nodo actual) {
        if (actual == null) return 0;
        return 1 + contarNodos(actual.getIzquierdo()) + contarNodos(actual.getDerecho());
    }

    public int obtenerAltura() {
        return calcularAltura(raiz);
    }

    private int calcularAltura(Nodo actual) {
        if (actual == null) return -1;
        return 1 + Math.max(calcularAltura(actual.getIzquierdo()), calcularAltura(actual.getDerecho()));
    }

    public int obtenerNivel(int dato) {
        return calcularNivel(raiz, dato, 0);
    }

    private int calcularNivel(Nodo actual, int dato, int nivel) {
        if (actual == null) return -1;
        if (dato == actual.getDato()) return nivel;
        if (dato < actual.getDato()) return calcularNivel(actual.getIzquierdo(), dato, nivel + 1);
        return calcularNivel(actual.getDerecho(), dato, nivel + 1);
    }

    public int contarHojas() {
        return contarHojasRec(raiz);
    }

    private int contarHojasRec(Nodo actual) {
        if (actual == null) return 0;
        if (actual.getIzquierdo() == null && actual.getDerecho() == null) return 1;
        return contarHojasRec(actual.getIzquierdo()) + contarHojasRec(actual.getDerecho());
    }

    public Integer obtenerMenor() {
        if (raiz == null) return null;
        return menorDato(raiz);
    }

    public Integer obtenerMayor() {
        if (raiz == null) return null;
        Nodo actual = raiz;
        while (actual.getDerecho() != null)
            actual = actual.getDerecho();
        return actual.getDato();
    }

    public Nodo obtenerNodoMayor() {
        if (raiz == null) return null;
        Nodo actual = raiz;
        while (actual.getDerecho() != null)
            actual = actual.getDerecho();
        return actual;
    }

    public String recorrerInorden() {
        StringBuilder sb = new StringBuilder();
        inorden(raiz, sb);
        return sb.toString().trim();
    }

    private void inorden(Nodo actual, StringBuilder sb) {
        if (actual == null) return;
        inorden(actual.getIzquierdo(), sb);
        sb.append(actual.getDato()).append(" ");
        inorden(actual.getDerecho(), sb);
    }

    public String recorrerPreorden() {
        StringBuilder sb = new StringBuilder();
        preorden(raiz, sb);
        return sb.toString().trim();
    }

    private void preorden(Nodo actual, StringBuilder sb) {
        if (actual == null) return;
        sb.append(actual.getDato()).append(" ");
        preorden(actual.getIzquierdo(), sb);
        preorden(actual.getDerecho(), sb);
    }

    public String recorrerPostorden() {
        StringBuilder sb = new StringBuilder();
        postorden(raiz, sb);
        return sb.toString().trim();
    }

    private void postorden(Nodo actual, StringBuilder sb) {
        if (actual == null) return;
        postorden(actual.getIzquierdo(), sb);
        postorden(actual.getDerecho(), sb);
        sb.append(actual.getDato()).append(" ");
    }

    public String imprimirAmplitud() {
        if (raiz == null) return "";
        StringBuilder sb = new StringBuilder();
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            sb.append(actual.getDato()).append(" ");
            if (actual.getIzquierdo() != null) cola.add(actual.getIzquierdo());
            if (actual.getDerecho() != null) cola.add(actual.getDerecho());
        }
        return sb.toString().trim();
    }
}