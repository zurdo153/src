package reporte;

import java.util.HashMap;

import javax.swing.UIManager;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import SQL.Connexion;

public class Reporte_Impresion_de_Cuadrantes {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Reporte_Impresion_de_Cuadrantes() {
		try {
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Reportes\\Reporte_Impresion_Cuadrante.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), new Connexion().conexion());
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Reporte_Impresion_de_Cuadrantes();
		}catch(Exception e){
			
		}
		
	}

}
