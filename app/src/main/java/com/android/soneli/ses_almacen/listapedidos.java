package com.android.soneli.ses_almacen;

public class listapedidos {

    String pedidosid;
    String proveedorNombre;
    String fechainsert;
    String estatus;

    public listapedidos(String pedidosid, String proveedorNombre, String fechainsert, String estatus) {
        this.pedidosid = pedidosid;
        this.proveedorNombre = proveedorNombre;
        this.fechainsert = fechainsert;
        this.estatus = estatus;
    }

    public String getPedidosid() {
        return pedidosid;
    }

    public void setPedidosid(String pedidosid) {
        this.pedidosid = pedidosid;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public String getFechainsert() {
        return fechainsert;
    }

    public void setFechainsert(String fechainsert) {
        this.fechainsert = fechainsert;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
