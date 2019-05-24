
package empleado.dao;

import empleado.dominio.Empleado;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class EmpleadoDAOImp implements EmpleadoDAO{
    
    
    private List<Empleado> empleados;

    public EmpleadoDAOImp() {
        
        String archivoEmpleados = "empleados.txt";
        Path path = Paths.get(archivoEmpleados);
        
        List<Empleado> empleados = new ArrayList<>();
        
        
        try (BufferedReader archivo = Files.newBufferedReader(path)){
            
            int codigoEmpleado= 0;
            String nombreEmpleado = null;
            String apellidoEmpleado = null;
            String contraseñaEmpleado = null;
            
            while(archivo.readLine() != null){
                
                
                archivo.readLine();
                codigoEmpleado = Integer.parseInt(archivo.readLine().trim());
                
                
                archivo.readLine();
                nombreEmpleado = archivo.readLine().trim();
                
                
                archivo.readLine();
                apellidoEmpleado = archivo.readLine().trim();
                
                
                archivo.readLine();
                contraseñaEmpleado = archivo.readLine().trim();
                
                empleados.add(new Empleado(codigoEmpleado,nombreEmpleado,apellidoEmpleado,contraseñaEmpleado));
                
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
        setEmpleados(empleados);
           
    }

    public List<Empleado> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
    
    

    @Override
    public List<Empleado> leerEmpleados() {
        String archivoEmpleados = "empleados.txt";
        List<Empleado> empleados = new ArrayList<>();
        NumberFormat formatoNumero = NumberFormat.getInstance(Locale.FRANCE);
        
        Number numero;
        String lineaConDatos;
        
        try (BufferedReader archivo = Files.newBufferedReader(Paths.get(archivoEmpleados))){
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
                String apellidos = lineaConDatos;
                
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String password = lineaConDatos;
                
                empleados.add(new Empleado(codigo, nombre,apellidos,password));
                
            }
        }catch (ParseException e){
            System.err.println("Error de formato en " + archivoEmpleados);
            System.exit(1);
            
        }catch(IOException e){
            System.err.println("Error de lectura en " + archivoEmpleados);
            System.exit(1);
        }
        return empleados;
        
    }
    
    @Override
    public Empleado getEmpleadoPorCodigo(int codigo) {
        
        String archivoEmpleados = "empleados.txt";
        Empleado empleado = new Empleado();
        NumberFormat formatoNumero = NumberFormat.getInstance(Locale.FRANCE);
        
        Number numero;
        String lineaConDatos;
        
        try (BufferedReader archivo = Files.newBufferedReader(Paths.get(archivoEmpleados))){
            while(archivo.readLine() != null){
                
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();   
                numero = formatoNumero.parse(lineaConDatos);
                int codigo_acceso = numero.intValue();
                
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String nombre = lineaConDatos;
                
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String apellidos = lineaConDatos;
                
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String password = lineaConDatos;
                
                if(codigo_acceso == codigo){
                    empleado = new Empleado(codigo, nombre,apellidos,password);
                }
                
            }
        }catch (ParseException e){
            System.err.println("Error de formato en " + archivoEmpleados);
            System.exit(1);
            
        }catch(IOException e){
            System.err.println("Error de lectura en " + archivoEmpleados);
            System.exit(1);
        }
        return empleado; 
    }
    
    
     public void modificarContraseña(Empleado empleado, String empPassword) {
        
        for(int i= 0; i < getEmpleados().size(); i++){
            if(getEmpleados().get(i).getCodigo() == empleado.getCodigo()){
                empleados.get(i).setPassword(empPassword);
            }
        }
        sobreescribirBuffer();
    }
    
    
    
    public void sobreescribirBuffer() {
        String empleadosTienda = toString();
        String archivoEmpleados = "empleados.txt";
        
        try (BufferedWriter escribirArchivo = Files.newBufferedWriter(Paths.get(archivoEmpleados))) {
            escribirArchivo.write(empleadosTienda);
            escribirArchivo.close();
            
        }catch(IOException e){
            System.err.println("Error en el archivo " + archivoEmpleados);
            System.exit(1);
        }
    }

    @Override
    public String toString() {
        String listaEmpleados = "";
        
        for(Empleado emp : empleados){
            listaEmpleados
                    += "[empleado]"
                    + "\n [codigo]\n "
                    + emp.getCodigo()
                    + "\n [nombre]\n "
                    + emp.getNombre()
                    + "\n [apellidos]\n "
                    + emp.getApellidos()
                    + "\n [contraseña]\n "
                    + emp.getPassword()
                    + "\n";
        }
        
        return listaEmpleados;
    }
    
    

    

    
   

   

    

    
    
     
    

   

    
   

    
    
    
}
    



    

   
    
    



