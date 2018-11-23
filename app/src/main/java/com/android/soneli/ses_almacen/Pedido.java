package com.android.soneli.ses_almacen;

public class Pedido {

    public Pedido (String ArticuloCodigo,String ArticuloDescripcion,int TPedido,int captura){
        this.ArticuloCodigo=ArticuloCodigo;
        this.ArticuloDescripcion=ArticuloDescripcion;
        this.TPedido=TPedido;
        this.captura=captura;
    }


     String ArticuloCodigo;
     String ArticuloDescripcion;
     int TPedido;
     int captura;

    public String getArticuloCodigo() {
        return ArticuloCodigo;
    }

    public void setArticuloCodigo(String articuloCodigo) {
        ArticuloCodigo = articuloCodigo;
    }

    public String getArticuloDescripcion() {
        return ArticuloDescripcion;
    }

    public void setArticuloDescripcion(String articuloDescripcion) {
        ArticuloDescripcion = articuloDescripcion;
    }

    public int getTPedido() {
        return TPedido;
    }

    public void setTPedido(int TPedido) {
        this.TPedido = TPedido;
    }

    public int getCaptura() {
        return captura;
    }

    public void setCaptura(int captura) {
        this.captura = captura;
    }
}
