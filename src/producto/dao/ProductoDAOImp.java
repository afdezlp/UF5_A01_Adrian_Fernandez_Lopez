
package producto.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import producto.dominio.Producto;


public class ProductoDAOImp implements ProductoDAO{
    
    
    List<Producto> productos;
    String archivoProductos = "productos.txt";

   
    public ProductoDAOImp() {
        
        
        List<Producto> listaProductos = new ArrayList<>();
       
        
        try (BufferedReader archivo = Files.newBufferedReader(Paths.get(archivoProductos))){
            
            int codigoProducto = 0;
            String nombreProducto = null;
            String descripcionProducto = null;
            double precioProducto = 0.0;
            
            
            while(archivo.readLine() != null){
                
                
                archivo.readLine();
                codigoProducto = Integer.parseInt(archivo.readLine().trim());
                
                
                archivo.readLine();
                nombreProducto = archivo.readLine().trim();
                
                
                archivo.readLine();
                descripcionProducto = archivo.readLine().trim();
                
                
                archivo.readLine();
                precioProducto = Double.parseDouble(archivo.readLine().trim().replace(',','.'));
                
                listaProductos.add(new Producto(codigoProducto,nombreProducto,descripcionProducto,precioProducto));
                  
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        setProductos(listaProductos);
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    

    @Override
    public List<Producto> leerProductos() {
        
        
        
        Producto productosObject = new Producto();
        List<Producto> listaProductos = new ArrayList<>();
        NumberFormat formatoNumero = NumberFormat.getInstance(Locale.FRANCE);
        
        Number numero;
        String lineaConDatos;
        
        try (BufferedReader archivo = Files.newBufferedReader(Paths.get(archivoProductos))){
            while(archivo.readLine() != null){
                
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();   
                numero = formatoNumero.parse(lineaConDatos);
                int codigo = numero.intValue();
                
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String nombre = lineaConDatos;
                
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String descripcion = lineaConDatos;
                
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                numero = formatoNumero.parse(lineaConDatos);
                double precio = numero.doubleValue();
                
                
                productosObject = new Producto(codigo, nombre,descripcion,precio);        
                listaProductos.add(new Producto(codigo, nombre,descripcion,precio));
                  
            }
        }catch (ParseException e){
            System.err.println("Error de formato en " + archivoProductos);
            System.exit(1);
            
        }catch(IOException e){
            System.err.println("Error de lectura en " + archivoProductos);
            System.exit(1);
        }
        
        return (List<Producto>) productosObject;   
    }
    
    

    
    public void modificarNombreProd(int codigo_producto, String nuevoNombre) {
        
        Producto prod = new Producto();
        for( int i = 0; i < productos.size();i++){
            if(productos.get(i).getCodigo() == codigo_producto){
                prod.setCodigo(productos.get(i).getCodigo());
                prod.setDescripcion(productos.get(i).getDescripcion());
                prod.setPrecio(productos.get(i).getPrecio());
                prod.setNombre(nuevoNombre);
                
                productos.set(i,prod);
            }
        }
        sobreescribirBuffer();
    }

    
    public void modificarCodigoProd(int codigo_producto, int nuevoCodigo) {
        
        Producto prod = new Producto();
        for( int i = 0; i < productos.size();i++){
            if(productos.get(i).getCodigo() == codigo_producto){
                prod.setNombre(productos.get(i).getNombre());
                prod.setDescripcion(productos.get(i).getDescripcion());
                prod.setPrecio(productos.get(i).getPrecio());
                prod.setCodigo(nuevoCodigo);
                
                productos.set(i,prod);
            }
        }
        sobreescribirBuffer();
    }

    
    public void modificarPrecioProd(int codigo_producto, double nuevoPrecio) {
        
        Producto prod = new Producto();
        for( int i = 0; i < productos.size();i++){
            if(productos.get(i).getCodigo() == codigo_producto){
                prod.setCodigo(productos.get(i).getCodigo());
                prod.setNombre(productos.get(i).getNombre());
                prod.setDescripcion(productos.get(i).getDescripcion());
                prod.setPrecio(nuevoPrecio);
                
                productos.set(i,prod);
            }
        }
        sobreescribirBuffer();
    }

    
    @Override
    public void sobreescribirBuffer() {
        String listaProductos = toString();
        String archivoProductos = "productos.txt";
        
        try {
            BufferedWriter escribirArchivo = Files.newBufferedWriter(Paths.get(archivoProductos));
            escribirArchivo.write(listaProductos);
            escribirArchivo.close();
            
        }catch(IOException e){
            System.err.println("Error en el archivo " + archivoProductos);
            System.exit(1);
        }
    }
    
    @Override
    public String toString() {

        String listaProductos = "";
        for (Producto prod : productos) {
            listaProductos
                    += "[producto]"
                    + "\n [codigo]\n "
                    + prod.getCodigo()
                    + "\n [nombre]\n "
                    + prod.getNombre()
                    + "\n [descripcion]\n "
                    + prod.getDescripcion()
                    + "\n [precio]\n "
                    + prod.getPrecio()
                    + "\n";
        }
        return listaProductos;
    }
}
    
    
