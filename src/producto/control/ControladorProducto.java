
package producto.control;

import java.util.List;
import producto.dao.ProductoDAO;
import producto.dao.ProductoDAOImp;
import producto.dominio.Producto;


public class ControladorProducto {
    
    public List<Producto> leerProductos() {
        ProductoDAO pdao = new ProductoDAOImp();
        return pdao.leerProductos();
    }
    
    public void sobreescribirBuffer(){
        ProductoDAO pdao = new ProductoDAOImp();
    }
}
