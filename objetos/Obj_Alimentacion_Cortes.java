package objetos;

import java.sql.SQLException;

import SQL.BuscarSQL;
import SQL.GuardarSQL;

public class Obj_Alimentacion_Cortes {
	
	private int folio_corte;
	private int folio_empleado;
	private String nombre;
	private String puesto;
	private String establecimiento;
	private String asignacion;
	private float corte_sistema;
	private float deposito;
	private float efectivo;
	private float diferencia_corte;
	private String fecha;
	private boolean status;

	public Obj_Alimentacion_Cortes(){
		this.folio_corte=0; this.folio_empleado=0; this.nombre=""; this.puesto=""; 
		this.establecimiento=""; this.asignacion=""; this.corte_sistema=0;
		this.deposito=0; this.efectivo=0; this.diferencia_corte=0; this.fecha=""; this.status=false;
	}

	public int getFolio_corte() {
		return folio_corte;
	}

	public void setFolio_corte(int folioCorte) {
		folio_corte = folioCorte;
	}

	public int getFolio_empleado() {
		return folio_empleado;
	}

	public void setFolio_empleado(int folioEmpleado) {
		folio_empleado = folioEmpleado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(String asignacion) {
		this.asignacion = asignacion;
	}

	public float getCorte_sistema() {
		return corte_sistema;
	}

	public void setCorte_sistema(float corteSistema) {
		corte_sistema = corteSistema;
	}

	public float getDeposito() {
		return deposito;
	}

	public void setDeposito(float deposito) {
		this.deposito = deposito;
	}

	public float getEfectivo() {
		return efectivo;
	}

	public void setEfectivo(float efectivo) {
		this.efectivo = efectivo;
	}
	

	public float getDiferencia_corte() {
		return diferencia_corte;
	}

	public void setDiferencia_corte(float diferenciaCorte) {
		diferencia_corte = diferenciaCorte;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	public Obj_Alimentacion_Cortes buscar(int folio){
		try {
			return new BuscarSQL().corte(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public boolean guardar(){ return new GuardarSQL().Guardar_Corte(this); }
}