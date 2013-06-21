package reporte;

import java.util.HashMap;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import SQL.Connexion;

@SuppressWarnings("serial")
public class Reporte_Fuente_de_Sodas_Desarrollo_Humano extends JFrame {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Reporte_Fuente_de_Sodas_Desarrollo_Humano() {
		try {
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\Reportes\\Reporte_Fuente_de_Sodas_Desarrollo_Humano.jrxml");
			
			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), new Connexion().conexion());
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}