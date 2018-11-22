package com.android.soneli.ses_almacen;

public class Pedido {
    private String PedidoId;
    private String ArticuloCodigo;
    private String ArticuloDescripcion;
    private String TPedido;

    public String getPedidoId() {
        return PedidoId;
    }

    public void setPedidoId(String pedidoId) {
        PedidoId = pedidoId;
    }

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

    public String getTPedido() {
        return TPedido;
    }

    public void setTPedido(String TPedido) {
        this.TPedido = TPedido;
    }

}
