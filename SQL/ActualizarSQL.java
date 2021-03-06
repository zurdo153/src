package SQL;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import ObjetoChecador.ObjHorario;
import ObjetoChecador.Obj_Dias_Inhabiles;
import ObjetoChecador.Obj_Mensaje_Personal;
import ObjetoChecador.Obj_Permisos_Checador;

import objetos.Obj_Actividad;
import objetos.Obj_Alimentacion_Denominacion;
import objetos.Obj_Asignacion_Mensajes;
import objetos.Obj_Asistencia_Puntualidad;
import objetos.Obj_Atributos;
import objetos.Obj_Auto_Auditoria;
import objetos.Obj_Auto_Finanzas;
import objetos.Obj_Bono_Complemento_Sueldo;
import objetos.Obj_Configuracion_Sistema;
import objetos.Obj_Cuadrante;
import objetos.Obj_Denominaciones;
import objetos.Obj_Directorios;
import objetos.Obj_Divisa_Y_TipoDeCambio;
import objetos.Obj_Diferencia_Cortes;
import objetos.Obj_Empleado;
import objetos.Obj_Empleados_Cuadrantes;
import objetos.Obj_Equipo_Trabajo;
import objetos.Obj_Establecimiento;
import objetos.Obj_Jefatura;
import objetos.Obj_Mensajes;
import objetos.Obj_Nivel_Critico;
import objetos.Obj_Nivel_Jerarquico;
import objetos.Obj_Nomina;
import objetos.Obj_OpRespuesta;
import objetos.Obj_Ponderacion;
import objetos.Obj_Prestamo;
import objetos.Obj_Puesto;
import objetos.Obj_Rango_Prestamos;
import objetos.Obj_Sueldo;
import objetos.Obj_Temporada;
import objetos.Obj_Tipo_Banco;
import objetos.Obj_Turno;
import objetos.Obj_Usuario;
import objetos.Obj_fuente_sodas_auxf;
import objetos.Obj_fuente_sodas_rh;

public class ActualizarSQL {
	String Qbitacora ="exec sp_insert_bitacora ?,?,?,?,?";
	PreparedStatement pstmtb = null;
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	public boolean Empleado(Obj_Empleado empleado, int folio){
		String query = "update tb_empleado set no_checador=?, nombre=?, ap_paterno=?, ap_materno=?, establecimiento_id=?, puesto_id=?, turno_id=?, descanso=?, dia_dobla=?, sueldo_id=?, bono_id=?, rango_prestamo_id=?," +
				" pension_alimenticia=?, infonavit=?, fuente_sodas=?, gafete=?, status=?, observaciones=?, foto=?, targeta_nomina =?, tipo_banco_id=?, fecha_nacimiento=?, imss=?, status_imss=?, fecha_ingreso=?, telefono_familiar=?, cuadrante_parcial=?," +
				" horario=?, status_2h=?, horario_2=?  where folio=" + folio;

		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			// insert bitacora
			String pc = InetAddress.getLocalHost().getHostName();
			String ip = InetAddress.getLocalHost().getHostAddress();
			pstmtb = con.prepareStatement(Qbitacora);
			pstmtb.setString(1, pc);
			pstmtb.setString(2, ip);
			pstmtb.setInt(3, usuario.getFolio());
			pstmtb.setString(4, "sp_insert_empleado modificar Num:"+folio +empleado.getNombre().toUpperCase()+" "+empleado.getAp_paterno().toUpperCase()+" sueldo:"+ empleado.getSueldo()+" Bono:"+empleado.getBono()+ " hor:"+empleado.getTurno()+"Estab:"+ empleado.getEstablecimiento()+"Puesto:"+empleado.getPuesto()+ "INF:"+empleado.getInfonavit()+" FNAC"+empleado.getFecha_nacimiento() );
			pstmtb.setString(5, "Empleados Actualiza");
			pstmtb.executeUpdate();
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
		
			pstmt.setInt(1, empleado.getNo_checador());
			pstmt.setString(2, empleado.getNombre().toUpperCase());
			pstmt.setString(3, empleado.getAp_paterno().toUpperCase());
			pstmt.setString(4, empleado.getAp_materno().toUpperCase());
			pstmt.setInt(5, empleado.getEstablecimiento());
			pstmt.setInt(6, empleado.getPuesto());
			pstmt.setInt(7, empleado.getTurno());
			pstmt.setInt(8, empleado.getDescanso());
			pstmt.setInt(9, empleado.getDobla());
			pstmt.setInt(10, empleado.getSueldo());
			pstmt.setInt(11, empleado.getBono());
			pstmt.setInt(12, empleado.getPrestamo());
			pstmt.setFloat(13, empleado.getPension_alimenticia());
			pstmt.setFloat(14, empleado.getInfonavit());
			pstmt.setInt(15, (empleado.getFuente_sodas())?1:0);
			pstmt.setBoolean(16, (empleado.getGafete())? true: false);
			pstmt.setInt(17, empleado.getStatus());
			pstmt.setString(18,empleado.getObservasiones());
			
			FileInputStream stream_foto = new FileInputStream(empleado.getFoto());
			pstmt.setBinaryStream(19, stream_foto, empleado.getFoto().length());
			
			pstmt.setString(20, empleado.getTargeta_nomina());
			pstmt.setInt(21, empleado.getTipo_banco());
			pstmt.setString(22, empleado.getFecha_nacimiento());
			pstmt.setString(23,empleado.getImss()+"");
			pstmt.setInt(24, empleado.getStatus_imss());
			pstmt.setString(25, empleado.getFecha_ingreso());
			pstmt.setString(26, empleado.getTelefono_familiar());
			pstmt.setInt(27, empleado.isCuadrante_parcial() ? 1 : 0);
			
			pstmt.setInt(28, empleado.getTurno());
			pstmt.setInt(29, empleado.getStatus_2h());
			pstmt.setInt(30, empleado.getTurno2());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Denom(Obj_Alimentacion_Denominacion denom, String asignacion, int folioDenom){
		
		String query = "update tb_alimentacion_denominaciones set asignacion=?, folio_empleado=?, " +
				"folio_denominacion=?, denominacion=?, valor=?, cantidad=?, fecha=? where asignacion='"+asignacion+"' and " +
						"folio_denominacion='"+folioDenom+"'";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;

		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
		
			pstmt.setString(1, denom.getAsignacion().toUpperCase());
			pstmt.setInt(2,denom.getFolio_empleado());
			pstmt.setInt(3, denom.getFolio_denominacion());
			pstmt.setString(4,denom.getDenominacion().toUpperCase());
			pstmt.setFloat(5, denom.getValor());
			pstmt.setFloat(6, denom.getCantidad());
			pstmt.setString(7, denom.getFecha());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Establecimiento(Obj_Establecimiento establecimiento, int folio){
		String query = "update tb_establecimiento set nombre=?, abreviatura=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, establecimiento.getNombre().toUpperCase());
			pstmt.setString(2, establecimiento.getAbreviatura().toUpperCase());
			pstmt.setString(3, (establecimiento.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	
	public boolean Usuario(Obj_Usuario usuario, int folio){
		String query = "update tb_usuario set nombre_completo=?,contrasena=?, permiso_id=?, fecha_actu=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, usuario.getNombre_completo().toUpperCase());
			pstmt.setString(2, usuario.getContrasena());
			pstmt.setInt(3, usuario.getPermiso_id());
			String fecha = new Date().toString();
			pstmt.setString(4, fecha);
			pstmt.setInt(5, usuario.getStatus());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Bono(Obj_Bono_Complemento_Sueldo bono, int folio){
		String query = "update tb_bono set bono=?, abreviatura=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setFloat(1, bono.getBono());
			pstmt.setString(2, bono.getAbreviatura().toUpperCase());
			pstmt.setString(3, (bono.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Puesto(Obj_Puesto puesto, int folio){
		String query = "update tb_puesto set nombre=?, abreviatura=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, puesto.getPuesto().toUpperCase());
			pstmt.setString(2, puesto.getAbreviatura().toUpperCase());
			pstmt.setString(3, (puesto.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean DiaInHabil(Obj_Dias_Inhabiles diaIA, int folio){
		String query = "update tb_dias_inhabiles set fecha=?, descripcion=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, diaIA.getFecha());
			pstmt.setString(2, diaIA.getDescripcion().toUpperCase());
//			pstmt.setString(3, (puesto.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Atributos(Obj_Atributos atrib, int folio){
		String query = "update tb_atributo set descripcion=?, valor=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, atrib.getDescripcion().toUpperCase());
			pstmt.setFloat(2, atrib.getValor());
			pstmt.setString(3, (atrib.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Jefatura(Obj_Jefatura jefat, int folio){
		String query = "update tb_jefatura set descripcion=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, jefat.getDescripcion().toUpperCase());
			pstmt.setString(2, (jefat.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Eq_Trabajo(Obj_Equipo_Trabajo EqTrabajo, int folio){
		String query = "update tb_equipo_trabajo set descripcion=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, EqTrabajo.getDescripcion().toUpperCase());
			pstmt.setString(2, (EqTrabajo.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Ponderacion(Obj_Ponderacion pond, int folio){
		String query = "update tb_ponderacion set descripcion=?, valor=?, fecha_in=?, fecha_fin=?, domingo=?, lunes=?, martes=?, miercoles=?, jueves=?, viernes=?, sabado=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, pond.getDescripcion().toUpperCase());
			pstmt.setFloat (2, pond.getValor());
			pstmt.setString(3, pond.getFechaIn()+"");
			pstmt.setString(4, pond.getFechaFin()+"");
			pstmt.setString(5, pond.getStatus()?"1":"0");
			pstmt.setString(6, pond.isDomingo()?"1":"0");
			pstmt.setString(7, pond.isLunes()?"1":"0");
			pstmt.setString(8, pond.isMartes()?"1":"0");
			pstmt.setString(9, pond.isMiercoles()?"1":"0");
			pstmt.setString(10,pond.isJueves()?"1":"0");
			pstmt.setString(11,pond.isViernes()?"1":"0");
			pstmt.setString(12,pond.isSabado()?"1":"0");
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Nivel(Obj_Nivel_Critico nc, int folio){
		String query = "update tb_nivel_critico set descripcion=?, valor=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, nc.getDescripcion().toUpperCase());
			pstmt.setFloat(2, nc.getValor());
			pstmt.setString(3, (nc.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Tipo_Banco(Obj_Tipo_Banco banck, int folio){
		String query = "update tb_tipo_banco set nombre=?, abreviatura=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, banck.getBanco().toUpperCase());
			pstmt.setString(2, banck.getAbreviatura().toUpperCase());
			pstmt.setString(3, (banck.getStatus())?"1":"0");
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}

			
	public boolean Divisas(Obj_Divisa_Y_TipoDeCambio divisas, int folio){
		String query = "update tb_divisas_tipo_de_cambio set nombre_divisas=?, valor=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, divisas.getNombre().toUpperCase());
			pstmt.setFloat(2, divisas.getValor());
			pstmt.setString(3, (divisas.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Denominaciones(Obj_Denominaciones denominaciones, int folio){
		String query = "update tb_denominaciones set nombre=?, moneda=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, denominaciones.getNombre().toUpperCase());
			pstmt.setString(2, denominaciones.getMoneda());
			pstmt.setString(3, (denominaciones.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Sueldo(Obj_Sueldo sueldo, int folio){
		String query = "update tb_sueldo set sueldo=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setFloat(1, sueldo.getSueldo());
			pstmt.setString(2, (sueldo.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean eliminarListaFuenteSodas(int id){
		String query = "update tb_fuente_sodas_rh set status=? where folio="+id;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean eliminarListaFuenteSodas_auxf(int id){
		String query = "update tb_fuente_sodas_auxf set status=? where folio="+id;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean eliminarPrestamo(int id){
		String query = "update tb_prestamo set status=? where folio="+id;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "0");
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean fuente_sodas(Obj_fuente_sodas_rh ftsds, int folio){
		String query = "update tb_fuente_sodas_rh set fecha=?, cantidad=? where folio="+folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, ftsds.getFecha());
			pstmt.setDouble(2, ftsds.getCantidad());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean fuente_sodas_Rh(){
		String query = "update tb_fuente_sodas_rh set status_ticket=? where status=?; update tb_fuente_sodas_auxf set status_ticket=? where status=?;";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "1");
			pstmt.setInt(2, 1);
			pstmt.setString(3, "1");
			pstmt.setInt(4, 1);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean fuente_sodas_auxf(Obj_fuente_sodas_auxf ftsds, int folio){
		String query = "update tb_fuente_sodas_auxf set fecha=?, cantidad=? where folio="+folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, ftsds.getFecha());
			pstmt.setDouble(2, ftsds.getCantidad());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}	
	
	public boolean prestamo(Obj_Prestamo pres, int folio){
		String query = "update tb_prestamo set fecha=?, cantidad=?, descuento=?, status=? where folio="+folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, pres.getFecha());
			pstmt.setDouble(2, pres.getCantidad());
			pstmt.setDouble(3, pres.getDescuento());
			pstmt.setInt(4, pres.getStatus());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}	
	
	public boolean Rango_Prestamos(Obj_Rango_Prestamos rango_prestamo, int folio){
		String query = "update tb_rango_prestamos set minimo=?, maximo=?, descuento=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setDouble(1, rango_prestamo.getPrestamo_minimo());
			pstmt.setDouble(2, rango_prestamo.getPrestamo_maximo());
			pstmt.setDouble(3, rango_prestamo.getDescuento());
			pstmt.setString(4, (rango_prestamo.isStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Asistecia_Puntualidad(Obj_Asistencia_Puntualidad asistencia_puntualidad, int folio){
		String query = "update tb_asistencia_puntualidad set asistencia=?, puntualidad=?, gafete=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setFloat(1, asistencia_puntualidad.getValorAsistencia());
			pstmt.setFloat(2, asistencia_puntualidad.getValorPuntualidad());
			pstmt.setFloat(3, asistencia_puntualidad.getValorGafete());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Actualizar(Obj_Diferencia_Cortes pres, int folio){
		String query = "update tb_diferencia_cortes set fecha=?, cantidad=?, descuento=?, status=? where folio="+folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, pres.getFecha());
			pstmt.setDouble(2, pres.getCantidad());
			pstmt.setDouble(3, pres.getDescuento());
			pstmt.setInt(4, pres.getStatus());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}	
	
	public boolean Turno(Obj_Turno turno, int folio){
		String query = "update tb_turno set nombre=?, horario=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, turno.getNombre().toUpperCase());
			pstmt.setString(2, turno.getHorario().toUpperCase());
			pstmt.setString(3, (turno.isStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Configurar_Sistema(Obj_Configuracion_Sistema configs){
		String query = "update tb_configuracion_sistema set bono_10_12=?, bono_dia_extra=?";
				
		System.out.println(query);
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
		
			pstmt.setString(1, (configs.isBono_10_12())? "true" : "false");
			pstmt.setString(2, (configs.isBono_dia_extra())? "true" : "false");
						
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}

	
	
	public boolean Auditoria(Obj_Auto_Auditoria auditoria){
		String query = "update tb_autorizaciones set autorizar_auditoria=? ";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, (auditoria.isAutorizar())? "true" : "false");
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Autorizar_Finanzas(Obj_Auto_Finanzas auditoria){
		String query = "update tb_autorizaciones set autorizar_finanzas=? ";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, (auditoria.isAutorizar())? "true" : "false");
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean PermisoUsuario(String Nombre_Completo, Vector Permisos){
		String update = "update tb_permisos set status_submenu = ? " +
				" where nombre_completo = '"+Nombre_Completo+"' and nombre_submenu = ?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(update);
		
			for(int i=0; i<Permisos.size(); i++){
				String[] arreglo = Permisos.get(i).toString().split("/");
				pstmt.setString(1, arreglo[1]);
				pstmt.setString(2, arreglo[0]);
				pstmt.execute();
			}
			
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
		
	public boolean Actualizar(Obj_Nomina nomina, String Establecimiento, int Folio){
		String update = "update tb_nomina set nomina = ?, pago_linea = ?, cheque_nomina = ?, lista_raya = ?, diferecia = ? where establecimiento = '"+Establecimiento+"' and folio_lista ="+Folio;
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(update);
			
			pstmt.setString(1, nomina.getNomina());
			pstmt.setString(2, nomina.getPago_linea());
			pstmt.setString(3, nomina.getCheque_nomina());
			pstmt.setString(4, nomina.getLista_raya());
			pstmt.setString(5, nomina.getDiferencia());
			
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean TemporadaActualizar(Obj_Temporada temporada, int folio){
		String query = "update tb_temporada set descripcion=?, fecha_in=?, fecha_fin=?, dia=?, status=? where folio=" + folio;
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
		
			pstmt.setString(1, temporada.getDescripcion());
			pstmt.setString(2, temporada.getFecha_in());
			pstmt.setString(3, temporada.getFecha_fin());
			pstmt.setString(4, temporada.getDia());
			pstmt.setInt(5, temporada.isStatus() ? 1 : 0);
							
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
//*Agregando update para telefonos*///
	public boolean ActualizarTelefono(Obj_Directorios directorio, int folio){
		String query = "update tb_direccion_telefonicos set numero=? where folio_empleado=" + folio;
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
		
			pstmt.setString(1, directorio.getTelefono());
							
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	/*Actualizar nivel gerarquico*/
	
	public boolean NivelG(Obj_Nivel_Jerarquico pond, int folio){
		String query = "update tb_nivel_gerarquico set descripcion=?, puestoprincipal=?, puestodependiente=?, status=? where folio=" + folio;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, pond.getDescripcion().toUpperCase());
			pstmt.setString(2, pond.getPuesto_principal());
			pstmt.setString(3, pond.getPuesto_dependiente());
			pstmt.setString(4, pond.isStatus()?"1":"0");
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Actualizar_Actividad(Obj_Actividad actividad, int folio){
		
		String query = "exec sp_update_actividad ?,?,?,?,?,?,?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, actividad.getActividad().toUpperCase());
			pstmt.setString(2, actividad.getDescripcion().toUpperCase());
			pstmt.setString(3, actividad.getRespuesta());
			pstmt.setString(4, actividad.getAtributos());
			pstmt.setString(5, actividad.getNivel_critico());
			pstmt.setString(6, actividad.getTemporada());
			pstmt.setInt(7, actividad.isCarga()? 1 : 0);
			pstmt.setInt(8, actividad.getRepetir());
			pstmt.setInt(9, folio);
			
			
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
		
	
//	public boolean Actualizar_Actividad(Obj_Actividad actividad, int folio){
//		String query = "update tb_actividad "+
//							"set actividad=?, descripcion=?, respuesta=?, atributo=?, nivel_critico=?, domingo=?, "+
//							"lunes=?, martes=?, miercoles=?, jueves=?, viernes=?, sabado=?, hora_inicio=?, "+
//							"hora_final=?, temporada=?, carga=?, repetir=?, status=? "+ 
//					    "where folio ="+folio;
//		
//		Connection con = new Connexion().conexion();
//		PreparedStatement pstmt = null;
//		try {
//			con.setAutoCommit(false);
//			pstmt = con.prepareStatement(query);
//			
//			pstmt.setString(1, actividad.getActividad().toUpperCase());
//			pstmt.setString(2, actividad.getDescripcion().toUpperCase());
//			pstmt.setString(3, actividad.getRespuesta());
//			pstmt.setString(4, actividad.getAtributos());
//			pstmt.setString(5, actividad.getNivel_critico());
//			pstmt.setInt(6, actividad.getDomingo());
//			pstmt.setInt(7, actividad.getLunes());
//			pstmt.setInt(8, actividad.getMartes());
//			pstmt.setInt(9, actividad.getMiercoles());
//			pstmt.setInt(10, actividad.getJueves());
//			pstmt.setInt(11, actividad.getViernes());
//			pstmt.setInt(12, actividad.getSabado());
//			pstmt.setString(13, actividad.getHora_inicio());
//			pstmt.setString(14, actividad.getHora_final());
//			pstmt.setString(15, actividad.getTemporada());
//			pstmt.setBoolean(16, actividad.isCarga());
//			pstmt.setInt(17, actividad.getRepetir());
//			pstmt.setInt(18, actividad.isStatus()? 1 : 0);
//			
//			pstmt.executeUpdate();
//			con.commit();
//		} catch (Exception e) {
//			System.out.println("SQLException: "+e.getMessage());
//			if(con != null){
//				try{
//					System.out.println("La transacción ha sido abortada");
//					con.rollback();
//				}catch(SQLException ex){
//					System.out.println(ex.getMessage());
//				}
//			}
//			return false;
//		}finally{
//			try {
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}		
//		return true;
//	}
	
	
	public boolean Cuadrante(Obj_Cuadrante cuadrante, String[][] tabla){
		String queryDelete ="delete tb_tabla_cuadrante where folio_cuadrante = ?";
		String query = "exec sp_update_cuadrante ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		String querytabla = "exec sp_insert_tabla_cuadrante ?,?,?,?,?,?,?";
		
		Connection con = new Connexion().conexion();
		
		PreparedStatement pstmtDelete = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtTabla = null;
		
		try {
			con.setAutoCommit(false);
			
			pstmtDelete = con.prepareStatement(queryDelete);
			pstmt = con.prepareStatement(query);
			pstmtTabla = con.prepareStatement(querytabla);
		
			pstmt.setString(1, cuadrante.getCuadrante().toUpperCase());
			pstmt.setString(2, cuadrante.getPerfil().toUpperCase());
			pstmt.setString(3, cuadrante.getJefatura());
			pstmt.setString(4, cuadrante.getNivel_gerarquico());
			pstmt.setString(5, cuadrante.getEquipo_trabajo());
			pstmt.setString(6, cuadrante.getEstablecimiento());
			pstmt.setInt(7, cuadrante.getDomingo());
			pstmt.setInt(8, cuadrante.getLunes());
			pstmt.setInt(9, cuadrante.getMartes());
			pstmt.setInt(10, cuadrante.getMiercoles());
			pstmt.setInt(11, cuadrante.getJueves());
			pstmt.setInt(12, cuadrante.getViernes());
			pstmt.setInt(13, cuadrante.getSabado());
			pstmt.setInt(14, cuadrante.getStatus());
			pstmt.setInt(15, cuadrante.getFolio());
			
			pstmt.executeUpdate();
			
			pstmtDelete.setInt(1, cuadrante.getFolio());
			pstmtDelete.executeUpdate();
					
			for(int i=0; i<tabla.length; i++){
				pstmtTabla.setString(1, cuadrante.getCuadrante().toUpperCase());
				pstmtTabla.setInt(2, Integer.parseInt(tabla[i][0].toString().trim()));
				pstmtTabla.setString(3, tabla[i][2].toString().trim());
				pstmtTabla.setInt(4, Boolean.parseBoolean(tabla[i][3]) ? 1 : 0);
				pstmtTabla.setString(5, tabla[i][4]);
				pstmtTabla.setString(6, tabla[i][5]);
				pstmtTabla.setString(7, tabla[i][6]);
				
				pstmtTabla.executeUpdate();
			}

			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada Actualizar - Cuadrante");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean nivelGerarquico(Obj_Nivel_Jerarquico niv, int folio){
		
//		String queryUpdate ="update tb_nivel_jerarquico set tb_nivel_jerarquico.descripcion = "+niv.getDescripcion()+"where tb_nivel_jerarquico.folio = "+folio;
		String queryDEP = "exec sp_insert_tabla_nivel_jerarquico ?,?,?";
		Connection con = new Connexion().conexion();
		
//		PreparedStatement pstmtNivelGerarquico = null;
		
//		PreparedStatement pstmt = null;
		PreparedStatement pstmtabla = null;
		
		try {
			con.setAutoCommit(false);
			
//			pstmtNivelGerarquico = con.prepareStatement(queryUpdate);
//			pstmtNivelGerarquico.executeUpdate();
			
//			pstmt = con.prepareStatement(queryClear);
//			pstmt.executeUpdate();
			
			pstmtabla = con.prepareStatement(queryDEP);
				
				pstmtabla.setInt (1, folio);
				pstmtabla.setString (2, niv.getPuesto_dependiente());
				pstmtabla.setString (3, niv.getEstablecimiento());
				
				pstmtabla.executeUpdate();

				con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		return true;
	}
	
	public boolean mensajePersonal(Obj_Mensaje_Personal msjPersonal, int folio){
		 
		String queryDEP = "exec sp_update_mensaje_personal  ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		
		PreparedStatement pstmtabla = null;
		
		try {
			con.setAutoCommit(false);
			
			pstmtabla = con.prepareStatement(queryDEP);
			
				pstmtabla.setInt (1, folio);
				pstmtabla.setString (2, msjPersonal.getFechaInicial());
				pstmtabla.setString (3, msjPersonal.getFechaFin());
				pstmtabla.setString (4, msjPersonal.getAsunto());
				pstmtabla.setString (5, msjPersonal.getMensaje());
				pstmtabla.setString (6,(msjPersonal.getStatus())?"1":"0");
				
				pstmtabla.executeUpdate();

				con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		return true;
	}
	
	public boolean permiso(Obj_Permisos_Checador Permiso, int folio){
		 
		String queryDEP = "exec sp_update_permiso_checador  ?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		
		PreparedStatement pstmtabla = null;
		
		try {
			con.setAutoCommit(false);
			
			pstmtabla = con.prepareStatement(queryDEP);
			
			
			pstmtabla.setInt (1,folio);
			pstmtabla.setInt (2, Permiso.getFolio_empleado());
			pstmtabla.setInt (3, Permiso.getFolio_usuario());			
			pstmtabla.setString(4,Permiso.getFecha());
			
			pstmtabla.setInt(5, Permiso.getTipo_de_permiso());
			pstmtabla.setString(6, Permiso.getMotivo().toUpperCase().trim());
			pstmtabla.setBoolean(7, (Permiso.isStatus())? true: false);
			
				pstmtabla.executeUpdate();

				con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		return true;
	}
	
	public boolean nivelGerarquico2(Obj_Nivel_Jerarquico niv, String[][]tabla){
		String query = "exec sp_insert_tabla_nivel_jerarquico ?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmtabla = null;
		
		try {
			con.setAutoCommit(false);
			pstmtabla = con.prepareStatement(query);
			
			for (int i = 0; i < tabla.length; i++) {

				pstmtabla.setInt (1, niv.getFolio());
				
				System.out.print(tabla[i][0] +"   ");	System.out.println(tabla[i][1]);
				
				pstmtabla.setString (2, tabla[i][0]);
				pstmtabla.setString (3, tabla[i][1]);
				pstmtabla.executeUpdate();
				
			}
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		return true;
	}

	public boolean mensajePersonal2(Obj_Mensaje_Personal msjPersonal, String[] tabla){
		String queryClear = "delete from tb_tabla_empleado_mensaje_personal where folio_mensaje = "+msjPersonal.getFolioMensaje();
		String query = "exec sp_insert_tabla_empleado_mensaje ?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtabla = null;
		
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(queryClear);
			pstmtabla = con.prepareStatement(query);
			
			pstmt.executeUpdate();
			
			for (int i = 0; i < tabla.length; i++) {

				pstmtabla.setInt (1, msjPersonal.getFolioMensaje());
				pstmtabla.setString (2, tabla[i]);
				
				pstmtabla.executeUpdate();
				
			}
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		return true;
	}
	
	public boolean EmpleadoCuadrante(Obj_Empleados_Cuadrantes empleado_cuadrante, String[] tabla){
		String queryClear = "delete from tb_tabla_empleado_cuadrante where folio_cuadrante ="+empleado_cuadrante.getFolio();
		String queryUpdate = "update tb_empleado_cuadrante set cuadrante=?,	status=? where folio = ?";
		String querytabla = "exec sp_insert_tabla_empleado_cuadrante ?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmtDelete = null;
		PreparedStatement pstmtUpdate = null;
		PreparedStatement pstmtTabla = null;
		try {
			con.setAutoCommit(false);
			
			pstmtDelete = con.prepareStatement(queryClear);
			pstmtDelete.executeUpdate();
			
			pstmtUpdate = con.prepareStatement(queryUpdate);
			
			pstmtUpdate.setString(1, empleado_cuadrante.getCuadrante());
			pstmtUpdate.setInt(2, empleado_cuadrante.isStatus() ? 1 : 0);
			pstmtUpdate.setInt(3, empleado_cuadrante.getFolio());
			
			pstmtUpdate.executeUpdate();
			
			pstmtTabla = con.prepareStatement(querytabla);

			for(int i=0; i<tabla.length; i++){
				pstmtTabla.setString(1, empleado_cuadrante.getCuadrante().toUpperCase().trim());
				pstmtTabla.setInt(2, Integer.parseInt(tabla[i]));
				pstmtTabla.executeUpdate();
			}
						
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean opcion_respuesta(Obj_OpRespuesta respuesta, int folio){
		String query = "exec sp_update_opcion_respuesta ?,?,?,?;";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
		
			pstmt.setString(1, respuesta.getNombre().toUpperCase().trim());
			pstmt.setInt(2, respuesta.getTipo_opcion().equals("Opción Libre") ? 0 : 1);
			pstmt.setInt(3, respuesta.isStatus() ? 1 : 0);
			pstmt.setInt(4, folio);
				
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	//*Agregando update para mensajes*///
			public boolean ActualizarMensajes(Obj_Mensajes msj, int folio){
				String query = "update tb_mensajes set mensaje=? where folio=" + folio;
				
				Connection con = new Connexion().conexion();
				PreparedStatement pstmt = null;
				try {
					con.setAutoCommit(false);
					pstmt = con.prepareStatement(query);
				
					pstmt.setString(1, msj.getMensaje());
									
					pstmt.executeUpdate();
					con.commit();
				} catch (Exception e) {
					System.out.println("SQLException: "+e.getMessage());
					if(con != null){
						try{
							System.out.println("La transacción ha sido abortada");
							con.rollback();
						}catch(SQLException ex){
							System.out.println(ex.getMessage());
						}
					}
					return false;
				}finally{
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}		
				return true;
			}
	
			//*Agregando update para asignacion de mensajes*///
			public boolean ActualizarAsignacion(Obj_Asignacion_Mensajes msj, int folio){
				String query = "update tb_asignacion_mensaje set mensaje=?,mensajearea=?,puesto=?,equipo=?,jefatura=?,empleado=? where folio=" + folio;
				
				Connection con = new Connexion().conexion();
				PreparedStatement pstmt = null;
				try {
					con.setAutoCommit(false);
					pstmt = con.prepareStatement(query);
					
					pstmt.setInt(1,msj.getNo_mensajes());
					pstmt.setString(2, msj.getMensaje());
					pstmt.setString(3,msj.getPuesto());
					pstmt.setString(4,msj.getEquipo());
					pstmt.setString(5,msj.getJefatura());
					pstmt.setString(6, msj.getEmpleado());
									
					pstmt.executeUpdate();
					con.commit();
				} catch (Exception e) {
					System.out.println("SQLException: "+e.getMessage());
					if(con != null){
						try{
							System.out.println("La transacción ha sido abortada");
							con.rollback();
						}catch(SQLException ex){
							System.out.println(ex.getMessage());
						}
					}
					return false;
				}finally{
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}		
				return true;
			}

			public boolean Horario(ObjHorario horario_emp, int folio){

				String query = "exec sp_update_horarios ?,?,?,?,?,?,?,?,?,?," +
													"	?,?,?,?,?,?,?,?,?,?," +
													"	?,?,?,?,?,?,?,?,?,?," +
													"	?,?,?,?,?,?,?,?,?,?," +
													"	? ";

				Connection con = new Connexion().conexion();
				PreparedStatement pstmt = null;
				try {
					con.setAutoCommit(false);
					pstmt = con.prepareStatement(query);
								
					int i=1;	
					
					pstmt.setInt(i, folio);
					pstmt.setString(i+=1, horario_emp.getNombre());
					pstmt.setInt(i+=1, horario_emp.getDescanso());
					pstmt.setInt(i+=1, horario_emp.getDiaDobla());
					pstmt.setInt(i+=1, horario_emp.getDiaDobla2());
					pstmt.setInt(i+=1, horario_emp.getDiaDobla3());
					

					//////////////////////////////////////////////////////////////
//					pstmt.setString(i+=1, "DOMINGO");
					pstmt.setString(i+=1, horario_emp.getDomingo1());
					pstmt.setString(i+=1, horario_emp.getDomingo2());
					pstmt.setString(i+=1, horario_emp.getDomingo3());
					pstmt.setString(i+=1, horario_emp.getDomingo4());
					pstmt.setString(i+=1, horario_emp.getDomingo5());
					
//					pstmt.setString(i+=1, "LUNES");
					pstmt.setString(i+=1, horario_emp.getLunes1());
					pstmt.setString(i+=1, horario_emp.getLunes2());
					pstmt.setString(i+=1, horario_emp.getLunes3());
					pstmt.setString(i+=1, horario_emp.getLunes4());
					pstmt.setString(i+=1, horario_emp.getLunes5());
					
//					pstmt.setString(i+=1, "MARTES");
					pstmt.setString(i+=1, horario_emp.getMartes1());
					pstmt.setString(i+=1, horario_emp.getMartes2());
					pstmt.setString(i+=1, horario_emp.getMartes3());
					pstmt.setString(i+=1, horario_emp.getMartes4());
					pstmt.setString(i+=1, horario_emp.getMartes5());
					
//					pstmt.setString(i+=1, "MIERCOLES");
					pstmt.setString(i+=1, horario_emp.getMiercoles1());
					pstmt.setString(i+=1, horario_emp.getMiercoles2());
					pstmt.setString(i+=1, horario_emp.getMiercoles3());
					pstmt.setString(i+=1, horario_emp.getMiercoles4());
					pstmt.setString(i+=1, horario_emp.getMiercoles5());
					
//					pstmt.setString(i+=1, "JUEVES");
					pstmt.setString(i+=1, horario_emp.getJueves1());
					pstmt.setString(i+=1, horario_emp.getJueves2());
					pstmt.setString(i+=1, horario_emp.getJueves3());
					pstmt.setString(i+=1, horario_emp.getJueves4());
					pstmt.setString(i+=1, horario_emp.getJueves5());
					
//					pstmt.setString(i+=1, "VIERNES");
					pstmt.setString(i+=1, horario_emp.getViernes1());
					pstmt.setString(i+=1, horario_emp.getViernes2());
					pstmt.setString(i+=1, horario_emp.getViernes3());
					pstmt.setString(i+=1, horario_emp.getViernes4());
					pstmt.setString(i+=1, horario_emp.getViernes5());
					
//					pstmt.setString(i+=1, "SABADO");
					pstmt.setString(i+=1, horario_emp.getSabado1());
					pstmt.setString(i+=1, horario_emp.getSabado2());
					pstmt.setString(i+=1, horario_emp.getSabado3());
					pstmt.setString(i+=1, horario_emp.getSabado4());
					pstmt.setString(i+=1, horario_emp.getSabado5());
					
					pstmt.executeUpdate();
					con.commit();
				} catch (Exception e) {
					System.out.println("SQLException: "+e.getMessage());
					if(con != null){
						try{
							System.out.println("La transacción ha sido abortada");
							con.rollback();
						}catch(SQLException ex){
							System.out.println(ex.getMessage());
						}
					}
					return false;
				}finally{
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}		
				return true;
			}
	
	
	
}
