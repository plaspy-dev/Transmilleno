/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Vector;
/**
 *
 * @author JuHU
 */
public class Ruta {
        protected String m_szNombre;
        protected String m_szDescripcion;
        protected Vector m_vEstaciones;
        public int m_nParadas;
        public Ruta(String nombre,String descripcion) {
            m_szNombre = nombre;
            m_vEstaciones= new Vector();
            m_szDescripcion= descripcion;
            m_nParadas=0;
        }
        public String GetDescripcion() {
            return m_szDescripcion;
        }
        public void AddEstacion(String estacion) {
            m_vEstaciones.addElement(estacion);
        }
        public String GetNombre() {
            return m_szNombre;
        }
        public Vector GetEstaciones() {
            return m_vEstaciones;
        }
        public int GetParadas() {
            return m_nParadas;
        }
        public boolean Calcular(String ubicacion, String destino) {
         
            int i=0;
            m_nParadas=0;
            for(i=0; i<m_vEstaciones.size(); i++) {
                if(ubicacion.compareTo(m_vEstaciones.elementAt(i).toString().toLowerCase())==0)
                    break;
            }
            for(;i<m_vEstaciones.size(); i++,m_nParadas++) {
                if(destino.compareTo(m_vEstaciones.elementAt(i).toString().toLowerCase())==0) {
                    return true;
                }
            }
            return false;
        }
}
