package SQL;

import java.sql.ResultSet;

import objetos.Obj_Bono;
import objetos.Obj_Empleado;
import objetos.Obj_Establecimiento;
import objetos.Obj_Puesto;
import objetos.Obj_Sueldo;
import objetos.Obj_Usuario;
import objetos.Obj_fuente_sodas_rh;

public class BuscarSQL extends Connexion{
	
	public Obj_Establecimiento Establecimiento(int folio){
		Obj_Establecimiento establecimiento = new Obj_Establecimiento();
		
		
		String query = "select * from tb_establecimiento where folio ="+ folio;
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				establecimiento.setFolio(rs.getInt("folio"));
				establecimiento.setNombre(rs.getString("nombre").trim());
				establecimiento.setAbreviatura(rs.getString("abreviatura").trim());
				establecimiento.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return establecimiento;
	}
	
	public Obj_Establecimiento Establecimiento_Nuevo(){
		Obj_Establecimiento establecimiento = new Obj_Establecimiento();
		String query = "select max(folio) as 'Maximo' from tb_establecimiento";
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				establecimiento.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return establecimiento;
	}
	
	public Obj_Sueldo Sueldo(int folio){
		Obj_Sueldo sueldo = new Obj_Sueldo();
		
		
		String query = "select * from tb_sueldo where folio ="+ folio;
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sueldo.setFolio(rs.getInt("folio"));
				sueldo.setSueldo(rs.getFloat("sueldo"));
				sueldo.setAbreviatura(rs.getString("abreviatura").trim());
				sueldo.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return sueldo;
	}
	
	public Obj_Sueldo Sueldo_Nuevo(){
		Obj_Sueldo sueldo = new Obj_Sueldo();
		String query = "select max(folio) as 'Maximo' from tb_sueldo";
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sueldo.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return sueldo;
	}
	
	public Obj_Bono Bono(int folio){
		Obj_Bono bono = new Obj_Bono();
		
		
		String query = "select * from tb_bono where folio ="+ folio;
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("folio"));
				bono.setBono(rs.getFloat("bono"));
				bono.setAbreviatura(rs.getString("abreviatura").trim());
				bono.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return bono;
	}
	
	public Obj_Bono Bono_Nuevo(){
		Obj_Bono bono = new Obj_Bono();
		String query = "select max(folio) as 'Maximo' from tb_bono";
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return bono;
	}
	
	public Obj_Puesto Puesto(int folio){
		Obj_Puesto puesto = new Obj_Puesto();
				
		String query = "select * from tb_puesto where folio ="+ folio;
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				puesto.setFolio(rs.getInt("folio"));
				puesto.setPuesto(rs.getString("nombre").trim());
				puesto.setAbreviatura(rs.getString("abreviatura").trim());
				puesto.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return puesto;
	}
	
	public Obj_Puesto Puesto_Nuevo(){
		Obj_Puesto puesto = new Obj_Puesto();
		String query = "select max(folio) as 'Maximo' from tb_puesto";
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				puesto.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return puesto;
	}
	
	public Obj_Empleado Empleado(int folio){
		Obj_Empleado empleado = new Obj_Empleado();
		
		
		String query = "select * from tb_empleado where folio ="+ folio;
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				empleado.setFolio(rs.getInt("folio"));
				empleado.setNo_checador(rs.getInt("no_checador"));
				empleado.setNombre(rs.getString("nombre").trim());
				empleado.setAp_paterno(rs.getString("ap_paterno").trim());
				empleado.setAp_materno(rs.getString("ap_materno").trim());
				empleado.setEstablecimiento(rs.getInt("establecimiento_id"));
				empleado.setPuesto(rs.getInt("puesto_id"));
				empleado.setSueldo(rs.getInt("sueldo_id"));				
				empleado.setBono(rs.getInt("bono_id"));
				empleado.setFuente_sodas(rs.getBoolean("fuente_sodas") ? true : false);
				empleado.setGafete(rs.getBoolean("gafete") ? true : false);
				empleado.setStatus(rs.getInt("status"));
				empleado.setFecha(rs.getString("fecha"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return empleado;
	}
	
	public Obj_Empleado Empleado_Nuevo(){
		Obj_Empleado empleado = new Obj_Empleado();
		String query = "select max(folio) as 'Maximo' from tb_empleado";
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				empleado.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return empleado;
	}
	
	public Obj_Usuario Usuario(int folio){
		Obj_Usuario usuario = new Obj_Usuario();
		String query = "select * from tb_usuario where folio ="+folio;
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
			
				usuario.setFolio(rs.getInt("folio"));
				usuario.setNombre_completo(rs.getString("nombre_completo").trim());
				usuario.setContrasena(rs.getString("contrasena").trim());
				usuario.setPermiso_id(rs.getInt("permiso_id"));
				usuario.setFecha_alta(rs.getString("fecha").trim());
				usuario.setStatus(rs.getInt("status"));
						
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return usuario;
	}
	
	public Obj_Usuario Maximo(){
		Obj_Usuario usuario = new Obj_Usuario();
		String query = "select max(folio) as 'Maximo' from tb_usuario";
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				usuario.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return usuario;
	}
	
	public Obj_fuente_sodas_rh MaximoFuente(){
		Obj_fuente_sodas_rh bono = new Obj_fuente_sodas_rh();
		String query = "select max(folio) as 'Maximo' from tb_fuente_sodas_rh";
		try {
			java.sql.Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return bono;
	}
	
}