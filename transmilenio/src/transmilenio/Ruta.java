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
        protected Vector m_vnIDEstaciones;
        protected Horarios m_LV;
        protected Horarios m_S;
        protected Horarios m_F;
        public int m_nParadas;
        
        public Ruta(String nombre,String descripcion) {
            m_szNombre = nombre;
            m_vEstaciones= new Vector();
            m_vnIDEstaciones= new Vector();
            m_szDescripcion= descripcion;
            m_nParadas=0;
            m_LV= m_S= m_F= null;
        }
        public void SetHorarioLV(int desde, int hasta) {
            m_LV= new Horarios(desde, hasta);
        }
        public void SetHorarioS(int desde, int hasta) {
            m_S= new Horarios(desde, hasta);
        }
        public void SetHorarioF(int desde, int hasta) {
            m_F= new Horarios(desde, hasta);
        }
        public Horarios GetHorarioLV() {
            return m_LV;
        }
        public Horarios GetHorarioS() {
            return m_S;
        }
        public Horarios GetHorarioF() {
            return m_F;
        }
        public String GetDescripcion() {
            return m_szDescripcion;
        }
        public void AddEstacion(String estacion, String id) {
            m_vEstaciones.addElement(estacion);
            m_vnIDEstaciones.addElement(id);
        }
        public Vector GetIDEstaciones() {
            return m_vnIDEstaciones;
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
        public boolean CalcularID(String ubicacion, String destino) {
         
            int i=0;
            m_nParadas=0;
            for(i=0; i<m_vEstaciones.size(); i++) {
                if(ubicacion.equals(m_vnIDEstaciones.elementAt(i)))
                    break;
            }
            for(;i<m_vEstaciones.size(); i++,m_nParadas++) {
                if(destino.equals(m_vnIDEstaciones.elementAt(i))) {
                    return true;
                }
            }
            return false;
        }
}
