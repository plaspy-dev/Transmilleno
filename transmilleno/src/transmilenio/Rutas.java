/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Vector;
/**
 *
 * @author JuHU
 */
public class Rutas {
    
    protected Vector m_vRutas;
    
    public Vector GetRutas() {
        return m_vRutas;
    }

    public Ruta GetRuta(String szRuta) {

        for(int i=0; i<m_vRutas.size(); i++) {
            Ruta ruta= (Ruta)m_vRutas.elementAt(i);
            if(ruta.GetNombre().equals(szRuta)) {
                return ruta;
            }
        }
        return null;
    }

    public Vector GetRuta(String ubicacion, String destino) {

        Vector resultado= new Vector();
        ubicacion= ubicacion.toLowerCase();
        destino= destino.toLowerCase();
        for(int i=0; i<m_vRutas.size(); i++) {
            Ruta ruta= (Ruta)m_vRutas.elementAt(i);
            if(ruta.Calcular(ubicacion, destino))
                resultado.addElement(ruta);
        }
        // ordenamos el vector resultante
        for(int i=resultado.size()-1; i>0; i--) {
            for(int j=0;j<i; j++) {
                Ruta ruta=(Ruta)resultado.elementAt(j+1);
                Ruta ruta2= (Ruta)resultado.elementAt(j);
                if(ruta2.GetParadas()>ruta.GetParadas()) {
                    Ruta temp= ruta2;
                    resultado.setElementAt(ruta, j);
                    resultado.setElementAt(temp, j+1);
                }
            }
        }
        return resultado;
    }
    
    public Vector GetTransbordos() {
        
        Vector resultado= new Vector();
        for(int i=0; i<m_vRutas.size(); i++) {
            Ruta ruta= (Ruta)m_vRutas.elementAt(i);
            if(ruta.m_nOrigen>-1) { // pasa por el origen
                for(int j=0;j<m_vRutas.size(); j++) {
                    if(i==j)
                        continue;
                    Ruta rdestino= (Ruta)m_vRutas.elementAt(j);
                    if(rdestino.m_nDestino>-1) { // pasa por el destino 
                        String estacion= ruta.GetInterseccion(rdestino);
                        if(estacion.length()!=0) {
                            rdestino= new Ruta(ruta.GetNombre() + "-" + estacion + "-" + rdestino.GetNombre(), ruta.GetNombre() + ": " + ruta.GetDescripcion() + "\n" +rdestino.GetNombre() + ": " + rdestino.GetDescripcion(), false);
                            rdestino.m_nParadas= ruta.m_nParadas;
                            resultado.addElement(rdestino);
                        }
                    }
                }
            }
        }
        // ordenamos el vector resultante
        for(int i=resultado.size()-1; i>0; i--) {
            for(int j=0;j<i; j++) {
                Ruta ruta=(Ruta)resultado.elementAt(j+1);
                Ruta ruta2= (Ruta)resultado.elementAt(j);
                if(ruta2.GetParadas()>ruta.GetParadas()) {
                    Ruta temp= ruta2;
                    resultado.setElementAt(ruta, j);
                    resultado.setElementAt(temp, j+1);
                }
            }
        }
        return resultado;
    }
    
    public Vector GetRutaID(int ubicacion, int destino) {

        Vector resultado= new Vector();
        
        for(int i=0; i<m_vRutas.size(); i++) {
            Ruta ruta= (Ruta)m_vRutas.elementAt(i);
            if(ruta.CalcularID(ubicacion, destino))
                resultado.addElement(ruta);
        }
        // ordenamos el vector resultante
        for(int i=resultado.size()-1; i>0; i--) {
            for(int j=0;j<i; j++) {
                Ruta ruta=(Ruta)resultado.elementAt(j+1);
                Ruta ruta2= (Ruta)resultado.elementAt(j);
                if(ruta2.GetParadas()>ruta.GetParadas()) {
                    Ruta temp= ruta2;
                    resultado.setElementAt(ruta, j);
                    resultado.setElementAt(temp, j+1);
                }
            }
        }
        return resultado;
    }
    
    public Ruta AddRuta(String ruta, String descripcion, boolean transbordo) {
        Ruta pRuta= new Ruta(ruta,descripcion,transbordo);
        m_vRutas.addElement(pRuta);
        return pRuta;
    }
    
    public Ruta AddRutaInvertida(String ruta, Ruta original, String descripcion) {
		
		Ruta nueva= new Ruta(ruta, descripcion, original.m_bTransbordo);
        Vector estaciones= original.GetEstaciones();
        int []ids= original.GetIDEstaciones();
        for(int i=estaciones.size()-1; i>=0; i--) 
            nueva.AddEstacion(estaciones.elementAt(i).toString(), ids[i]);
        if(original.GetHorarioLV()!=null)
            nueva.SetHorarioLV(original.GetHorarioLV().GetDesde(), original.GetHorarioLV().GetHasta());
        if(original.GetHorarioS()!=null)
            nueva.SetHorarioS(original.GetHorarioS().GetDesde(), original.GetHorarioS().GetHasta());
        if(original.GetHorarioF()!=null)
            nueva.SetHorarioF(original.GetHorarioF().GetDesde(), original.GetHorarioF().GetHasta());
		m_vRutas.addElement(nueva);
        return nueva;
    }
    
    public Ruta AddRutaInvertida(String ruta, Ruta original) {
    
	return AddRutaInvertida(ruta, original, original.GetDescripcion());
    }
    
    public Rutas() {
        
        m_vRutas= new Vector();
        Ruta ruta;
        
    	ruta= this.AddRuta("B1", "Lunes a sábado 05:00-23:00*Domingos y festivos 06:00-22:00", true);
    	ruta.AddEstacion("Portal Américas", 501);
    	ruta.AddEstacion("Patio Bonito", 502);
    	ruta.AddEstacion("Biblioteca Tintal", 503);
    	ruta.AddEstacion("Transversal 86", 504);
    	ruta.AddEstacion("Banderas", 505);
    	ruta.AddEstacion("Mandalay", 506);
    	ruta.AddEstacion("Mundo Aventura", 507);
    	ruta.AddEstacion("Marsella", 508);
    	ruta.AddEstacion("Pradera", 509);
    	ruta.AddEstacion("Américas - Kr. 53A", 510);
    	ruta.AddEstacion("Puente aranda", 511);
    	ruta.AddEstacion("Carrera 43", 512);
    	ruta.AddEstacion("Zona industrial", 513);
    	ruta.AddEstacion("CDS - Carrera 32", 514);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("San Facon - Kr. 22", 516);
    	ruta.AddEstacion("De la Sabana", 517);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 57", 5);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Calle 106", 112);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Mazurén", 105);
    	ruta.AddEstacion("Cardio infantil", 104);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Portal del norte", 102);
    	ruta.AddEstacion("C.C. Santafé", 101);
    	this.AddRutaInvertida("F1", ruta, "Lunes a sábado 05:00-23:00*Domingos y festivos 06:00-22:00");

    	ruta= this.AddRuta("D3", "Lunes a viernes 05:00-23:00*Sábados 05:00-23:00*Domingos y festivos 06:00-22:00", true);
    	ruta.AddEstacion("Portal del Tunal", 705);
    	ruta.AddEstacion("Parque", 706);
    	ruta.AddEstacion("Biblioteca", 707);
    	ruta.AddEstacion("Santa Lucía", 708);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Quiroga", 710);
    	ruta.AddEstacion("Olaya", 711);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Fucha", 713);
    	ruta.AddEstacion("Nariño", 714);
    	ruta.AddEstacion("Hortua", 715);
    	ruta.AddEstacion("Hospital", 716);
    	ruta.AddEstacion("Tercer milenio", 14);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 57", 5);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Polo", 313);
    	ruta.AddEstacion("Escuela militar", 312);
    	ruta.AddEstacion("Carrera 47", 311);
    	ruta.AddEstacion("Carrera 53", 310);
    	ruta.AddEstacion("Avenida 68", 309);
    	ruta.AddEstacion("Ferias", 308);
    	ruta.AddEstacion("Boyacá", 307);
    	ruta.AddEstacion("Minuto de Dios", 306);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Avenida Cali", 304);
    	ruta.AddEstacion("Carrera 90", 303);
    	ruta.AddEstacion("Quirigua", 302);
    	ruta.AddEstacion("Portal de la 80", 301);
    	this.AddRutaInvertida("H3", ruta, "Lunes a sábado 05:00-23:00*Domingos y festivos 06:00-22:00");

    	ruta= this.AddRuta("C4", "Lunes a sábado 05:00-23:00*Domingos y festivos 06:00-22:00", true);
    	ruta.AddEstacion("Portal de Usme", 701);
    	ruta.AddEstacion("Molinos", 702);
    	ruta.AddEstacion("Consuelo", 703);
    	ruta.AddEstacion("Socorro", 704);
    	ruta.AddEstacion("Santa Lucía", 708);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Quiroga", 710);
    	ruta.AddEstacion("Olaya", 711);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Fucha", 713);
    	ruta.AddEstacion("Nariño", 714);
    	ruta.AddEstacion("Hortua", 715);
    	ruta.AddEstacion("Hospital", 716);
    	ruta.AddEstacion("Tercer milenio", 14);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 57", 5);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Polo", 313);
    	ruta.AddEstacion("Escuela militar", 312);
    	ruta.AddEstacion("San Martín", 214);
    	ruta.AddEstacion("Rionegro", 213);
    	ruta.AddEstacion("Suba - Cl. 95", 212);
    	ruta.AddEstacion("Suba - Cl 100", 211);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Humedal Córdoba", 208);
    	ruta.AddEstacion("Niza - Cl. 127", 207);
    	ruta.AddEstacion("Suba - Av. Boyacá", 206);
    	ruta.AddEstacion("Gratamira", 205);
    	ruta.AddEstacion("21 Angeles", 204);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("La campiña", 202);
    	ruta.AddEstacion("Portal Suba", 201);
    	this.AddRutaInvertida("H4", ruta, "Lunes a sábado 05:00-23:00*Domingos y festivos 06:00-22:00");

    	ruta= this.AddRuta("B5", "Lunes a sábado 05:00-23:00*Domingos y festivos 06:00-22:00", true);
    	ruta.AddEstacion("Portal del sur", 601);
    	ruta.AddEstacion("Perdomo", 602);
    	ruta.AddEstacion("Madelena", 603);
    	ruta.AddEstacion("Sevillana", 604);
    	ruta.AddEstacion("Venecia", 605);
    	ruta.AddEstacion("Alquería", 606);
    	ruta.AddEstacion("General Santander", 607);
    	ruta.AddEstacion("NQS-Cl 38A Sur", 608);
    	ruta.AddEstacion("NQS-Cl 30 Sur", 609);
    	ruta.AddEstacion("SENA", 610);
    	ruta.AddEstacion("Santa Isabel", 611);
    	ruta.AddEstacion("Comuneros", 612);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("Paloquemao", 410);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("Av. El Dorado", 408);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Campín", 406);
    	ruta.AddEstacion("Coliseo", 405);
    	ruta.AddEstacion("Simón Bolívar", 404);
    	ruta.AddEstacion("Av. Chile", 403);
    	ruta.AddEstacion("NQS - Calle 75", 402);
    	ruta.AddEstacion("Escuela militar", 312);
    	ruta.AddEstacion("Polo", 313);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Calle 106", 112);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Mazurén", 105);
    	ruta.AddEstacion("Cardio infantil", 104);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("G5", ruta, "Lunes a sábado 05:00-23:00*Domingos y festivos 06:00-22:00");

    	ruta= this.AddRuta("B10", "Lunes a viernes 05:00-22:00*Sábados 05:00-22:00", true);
    	ruta.AddEstacion("Portal de la 80", 301);
    	ruta.AddEstacion("Carrera 90", 303);
    	ruta.AddEstacion("Avenida Cali", 304);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Carrera 53", 310);
    	ruta.AddEstacion("Carrera 47", 311);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Calle 106", 112);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Mazurén", 105);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("D10", ruta, "Lunes a viernes 05:00-22:00*Sábados 05:00-22:00");

    	ruta= this.AddRuta("B55", "Lunes a viernes 06:00-09:00", false);
    	ruta.AddEstacion("Portal de la 80", 301);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Héroes", 116);

    	ruta= this.AddRuta("H51", "Lunes a viernes 06:00-09:00", false);
    	ruta.AddEstacion("Portal del norte", 102);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Calle 57", 5);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Santa Lucía", 708);
    	ruta.AddEstacion("Socorro", 704);
    	ruta.AddEstacion("Consuelo", 703);
    	ruta.AddEstacion("Molinos", 702);
    	ruta.AddEstacion("Portal de Usme", 701);

    	ruta= this.AddRuta("B70", "Lunes a viernes 05:00-09:00", false);
    	ruta.AddEstacion("Portal de Usme", 701);
    	ruta.AddEstacion("Molinos", 702);
    	ruta.AddEstacion("Consuelo", 703);
    	ruta.AddEstacion("Socorro", 704);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Nariño", 714);
    	ruta.AddEstacion("Hortua", 715);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Toberín", 103);
    	this.AddRutaInvertida("H70", ruta, "Lunes a viernes 05:00-09:00");


    	ruta= this.AddRuta("A70", "Lunes a viernes 06:00-09:00", false);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72", 2);

    	ruta= this.AddRuta("B72", "Lunes a sábado 05:00-09:00", false);
    	ruta.AddEstacion("Portal de Usme", 701);
    	ruta.AddEstacion("Molinos", 702);
    	ruta.AddEstacion("Nariño", 714);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Portal del norte", 102);

    	ruta= this.AddRuta("B73", "Lunes a viernes 09:00-23:00*Sábados 05:00-23:00", true);
    	ruta.AddEstacion("Portal de Usme", 701);
    	ruta.AddEstacion("Molinos", 702);
    	ruta.AddEstacion("Consuelo", 703);
    	ruta.AddEstacion("Socorro", 704);
    	ruta.AddEstacion("Santa Lucía", 708);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Nariño", 714);
    	ruta.AddEstacion("Hortua", 715);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 57", 5);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("H74", ruta, "Lunes a viernes 09:00-23:00*Sábados 05:00-23:00");

    	ruta= this.AddRuta("B13", "Lunes a viernes 05:00-22:00*Sábados 06:00-22:00", true);
    	ruta.AddEstacion("Portal del Tunal", 705);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Quiroga", 710);
    	ruta.AddEstacion("Olaya", 711);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Hortua", 715);
    	ruta.AddEstacion("Tercer milenio", 14);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Calle 106", 112);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Mazurén", 105);
    	ruta.AddEstacion("Cardio infantil", 104);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("H13", ruta, "Lunes a viernes 05:00-22:00*Sábados 06:00-22:00");

    	ruta= this.AddRuta("B53", "Lunes a viernes 05:00-09:00", false);
    	ruta.AddEstacion("Portal del Tunal", 705);
    	ruta.AddEstacion("Santa Lucía", 708);
    	ruta.AddEstacion("Hortua", 715);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Portal del norte", 102);

    	ruta= this.AddRuta("H52", "Lunes a viernes 06:00-09:00", false);
    	ruta.AddEstacion("Portal del norte", 102);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Portal del Tunal", 705);

    	ruta= this.AddRuta("B61", "Lunes a viernes 16:00-19:00", false);
    	ruta.AddEstacion("Portal del Tunal", 705);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Portal del norte", 102);

    	ruta= this.AddRuta("B71", "Lunes a viernes 05:00-09:00*Sábados 05:00-09:00", false);
    	ruta.AddEstacion("Portal del Tunal", 705);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Olaya", 711);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Portal del norte", 102);

    	ruta= this.AddRuta("H73", "Lunes a viernes 05:00-09:00*Lunes a viernes 16:00-20:00", false);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Fucha", 713);
    	ruta.AddEstacion("Olaya", 711);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Portal del Tunal", 705);

    	ruta= this.AddRuta("B27", "Lunes a viernes 05:00-09:00*Lunes a viernes 16:00-21:00", false);
    	ruta.AddEstacion("Portal del Tunal", 705);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("H27", ruta, "Lunes a viernes 05:00-09:00*Lunes a viernes 16:00-21:00");

    	ruta= this.AddRuta("H60", "Lunes a viernes 16:00-20:00", false);
    	ruta.AddEstacion("Portal del norte", 102);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Olaya", 711);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Portal del Tunal", 705);

    	ruta= this.AddRuta("H61", "Lunes a viernes 16:00-20:00", false);
    	ruta.AddEstacion("Portal del norte", 102);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Nariño", 714);
    	ruta.AddEstacion("Molinos", 702);
    	ruta.AddEstacion("Portal de Usme", 701);

    	ruta= this.AddRuta("J70", "Lunes a viernes 05:00-09:00", false);
    	ruta.AddEstacion("Portal del norte", 102);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Museo del Oro", 801);
    	ruta.AddEstacion("Las aguas", 802);

    	ruta= this.AddRuta("B74", "Lunes a viernes 09:00-22:00*Sábados 07:00-20:00", true);
    	ruta.AddEstacion("Las aguas", 802);
    	ruta.AddEstacion("Museo del Oro", 801);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Calle 57", 5);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("J72", ruta, "Lunes a viernes 09:00-22:00*Sábados 07:00-20:00");


    	ruta= this.AddRuta("B14", "Lunes a viernes 05:00-22:00*Sábados 05:00-22:00", true);
    	ruta.AddEstacion("Portal Américas", 501);
    	ruta.AddEstacion("Biblioteca Tintal", 503);
    	ruta.AddEstacion("Transversal 86", 504);
    	ruta.AddEstacion("Banderas", 505);
    	ruta.AddEstacion("Pradera", 509);
    	ruta.AddEstacion("CDS - Carrera 32", 514);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Calle 106", 112);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("F14", ruta, "Lunes a viernes 05:00-22:00*Sábados 05:00-22:00");


    	ruta= this.AddRuta("B52", "Lunes a viernes 05:00-09:00", false);
    	ruta.AddEstacion("Portal Américas", 501);
    	ruta.AddEstacion("Biblioteca Tintal", 503);
    	ruta.AddEstacion("Banderas", 505);
    	ruta.AddEstacion("Marsella", 508);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("F61", ruta, "Lunes a viernes 17:00-20:00");

    	ruta= this.AddRuta("B11", "Lunes a viernes 05:00-21:00*Sábados 06:00-22:00", true);
    	ruta.AddEstacion("Portal del sur", 601);
    	ruta.AddEstacion("Venecia", 605);
    	ruta.AddEstacion("Alquería", 606);
    	ruta.AddEstacion("SENA", 610);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("Paloquemao", 410);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Campín", 406);
    	ruta.AddEstacion("Escuela militar", 312);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Calle 106", 112);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Mazurén", 105);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("G11", ruta, "Lunes a viernes 05:00-21:00*Sábados 06:00-22:00");

    	ruta= this.AddRuta("B54", "Lunes a viernes 05:00-08:00", false);
    	ruta.AddEstacion("Portal del sur", 601);
    	ruta.AddEstacion("Perdomo", 602);
    	ruta.AddEstacion("Madelena", 603);
    	ruta.AddEstacion("General Santander", 607);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Calle 106", 112);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Mazurén", 105);

    	ruta= this.AddRuta("G61", "Lunes a viernes 17:00-20:00", false);
    	ruta.AddEstacion("Portal del norte", 102);
    	ruta.AddEstacion("Mazurén", 105);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Calle 106", 112);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("General Santander", 607);
    	ruta.AddEstacion("Madelena", 603);
    	ruta.AddEstacion("Perdomo", 602);
    	ruta.AddEstacion("Portal del sur", 601);

    	ruta= this.AddRuta("B12", "Lunes a sábado 05:00-23:00", true);
    	ruta.AddEstacion("Portal del sur", 601);
    	ruta.AddEstacion("Perdomo", 602);
    	ruta.AddEstacion("Madelena", 603);
    	ruta.AddEstacion("General Santander", 607);
    	ruta.AddEstacion("Santa Isabel", 611);
    	ruta.AddEstacion("Comuneros", 612);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("Av. El Dorado", 408);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Simón Bolívar", 404);
    	ruta.AddEstacion("NQS - Calle 75", 402);
    	ruta.AddEstacion("La Castellana", 401);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("G12", ruta, "Lunes a sábado 05:00-23:00");

    	ruta= this.AddRuta("A52", "Lunes a viernes 05:00-08:00", false);
    	ruta.AddEstacion("Portal del sur", 601);
    	ruta.AddEstacion("Perdomo", 602);
    	ruta.AddEstacion("Venecia", 605);
    	ruta.AddEstacion("General Santander", 607);
    	ruta.AddEstacion("NQS-Cl 30 Sur", 609);
    	ruta.AddEstacion("Santa Isabel", 611);
    	ruta.AddEstacion("NQS - Calle 75", 402);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Flores", 3);
    	this.AddRutaInvertida("G52", ruta, "Lunes a viernes 17:00-20:00");

    	ruta= this.AddRuta("B28", "Lunes a sábado 05:00-09:00*Lunes a viernes 16:00-20:00", false);
    	ruta.AddEstacion("Portal Américas", 501);
    	ruta.AddEstacion("Biblioteca Tintal", 503);
    	ruta.AddEstacion("Banderas", 505);
    	ruta.AddEstacion("Zona industrial", 513);
    	ruta.AddEstacion("CDS - Carrera 32", 514);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Simón Bolívar", 404);
    	ruta.AddEstacion("Av. Chile", 403);
    	ruta.AddEstacion("NQS - Calle 75", 402);
    	ruta.AddEstacion("Calle 106", 112);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("F28", ruta, "Lunes a sábado 05:00-09:00*Lunes a viernes 16:00-20:00");

    	ruta= this.AddRuta("E32", "Lunes a viernes 05:00-08:00", false);
    	ruta.AddEstacion("Portal Américas", 501);
    	ruta.AddEstacion("Banderas", 505);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Simón Bolívar", 404);
    	ruta.AddEstacion("Av. Chile", 403);
    	ruta.AddEstacion("NQS - Calle 75", 402);
    	this.AddRutaInvertida("F32", ruta, "Lunes a viernes 16:00-20:00");

    	ruta= this.AddRuta("B50", "Lunes a viernes 06:00-08:00*Lunes a viernes 17:00-20:00", false);
    	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("La campiña", 202);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("21 Angeles", 204);
    	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Rionegro", 213);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Calle 106", 112);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Cardio infantil", 104);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("C61", ruta, "Lunes a viernes 06:00-08:00*Lunes a viernes 17:00-20:00");

    	ruta= this.AddRuta("B56", "Lunes a viernes 06:00-08:00", false);
    	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("Héroes", 116);
    	
    	ruta= this.AddRuta("H25", "Lunes a viernes 05:00-09:00*Lunes a viernes 16:00-20:00", false);
    	ruta.AddEstacion("Simón Bolívar", 404);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("Paloquemao", 410);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("SENA", 610);
    	ruta.AddEstacion("General Santander", 607);
    	ruta.AddEstacion("Sevillana", 604);
    	ruta.AddEstacion("Portal del sur", 601);
    	ruta.AddEstacion("Portal del Tunal", 705);
    	ruta.AddEstacion("Molinos", 702);
    	ruta.AddEstacion("Portal de Usme", 701);

    	ruta= this.AddRuta("E25", "Lunes a viernes 05:00-08:00*Lunes a viernes 17:00-19:00", false);
    	ruta.AddEstacion("Portal de Usme", 701);
    	ruta.AddEstacion("Molinos", 702);
    	ruta.AddEstacion("Portal del Tunal", 705);
    	ruta.AddEstacion("Sevillana", 604);
    	ruta.AddEstacion("General Santander", 607);
    	ruta.AddEstacion("SENA", 610);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("Paloquemao", 410);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Simón Bolívar", 404);

    	ruta= this.AddRuta("C15", "Lunes a viernes 05:00-22:00*Sábados 06:00-22:00", true);
    	ruta.AddEstacion("Portal del Tunal", 705);
    	ruta.AddEstacion("Biblioteca", 707);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Olaya", 711);
    	ruta.AddEstacion("Nariño", 714);
    	ruta.AddEstacion("Tercer milenio", 14);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Escuela militar", 312);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Humedal Córdoba", 208);
    	ruta.AddEstacion("Niza - Cl. 127", 207);
    	ruta.AddEstacion("Portal Suba", 201);
    	this.AddRutaInvertida("H15", ruta, "Lunes a viernes 05:00-22:00*Sábados 06:00-22:00");

    	ruta= this.AddRuta("A15", "Lunes a viernes 06:00-08:00", false);
    	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("Niza - Cl. 127", 207);
    	ruta.AddEstacion("Humedal Córdoba", 208);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Escuela militar", 312);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Calle 19", 12);

    	ruta= this.AddRuta("C17", "Lunes a sábado 05:00-09:00*Lunes a sábado 16:00-20:00", false);
    	ruta.AddEstacion("Portal de Usme", 701);
    	ruta.AddEstacion("Molinos", 702);
    	ruta.AddEstacion("Consuelo", 703);
    	ruta.AddEstacion("Socorro", 704);
    	ruta.AddEstacion("Santa Lucía", 708);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Hortua", 715);
    	ruta.AddEstacion("Hospital", 716);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 57", 5);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Polo", 313);
    	ruta.AddEstacion("San Martín", 214);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Suba - Av. Boyacá", 206);
    	ruta.AddEstacion("La campiña", 202);
    	ruta.AddEstacion("Portal Suba", 201);
    	this.AddRutaInvertida("H17", ruta, "Lunes a sábado 05:00-09:00*Lunes a sábado 16:00-20:00");

    	ruta= this.AddRuta("C29", "Lunes a viernes 05:00-08:00*Lunes a viernes 17:00-20:00", false);
    	ruta.AddEstacion("Portal Américas", 501);
    	ruta.AddEstacion("Patio Bonito", 502);
    	ruta.AddEstacion("Biblioteca Tintal", 503);
    	ruta.AddEstacion("Banderas", 505);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("La campiña", 202);
    	ruta.AddEstacion("Portal Suba", 201);
    	this.AddRutaInvertida("F29", ruta, "Lunes a viernes 05:00-08:00*Lunes a viernes 17:00-20:00");

    	ruta= this.AddRuta("C19", "Lunes a viernes 05:00-22:00*Sábados 06:00-22:00", true);
    	ruta.AddEstacion("Banderas", 505);
    	ruta.AddEstacion("Mandalay", 506);
    	ruta.AddEstacion("Marsella", 508);
    	ruta.AddEstacion("Américas - Kr. 53A", 510);
    	ruta.AddEstacion("Zona industrial", 513);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Suba - Cl 100", 211);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Gratamira", 205);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("La campiña", 202);
    	ruta.AddEstacion("Portal Suba", 201);
    	this.AddRutaInvertida("F19", ruta, "Lunes a viernes 05:00-22:00*Sábados 06:00-22:00");

    	ruta= this.AddRuta("C30", "Lunes a sábado 05:00-09:00*Lunes a sábado 16:00-20:00", false);
    	ruta.AddEstacion("Portal del sur", 601);
    	ruta.AddEstacion("NQS-Cl 38A Sur", 608);
    	ruta.AddEstacion("Santa Isabel", 611);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Coliseo", 405);
    	ruta.AddEstacion("NQS - Calle 75", 402);
    	ruta.AddEstacion("Suba - Cl. 95", 212);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Suba - Av. Boyacá", 206);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("La campiña", 202);
    	ruta.AddEstacion("Portal Suba", 201);
    	this.AddRutaInvertida("G30", ruta, "Lunes a sábado 05:00-09:00*Lunes a sábado 16:00-20:00");

    	ruta= this.AddRuta("C31", "Lunes a sábado 05:00-09:00*Lunes a sábado 16:00-20:00", false);
    	ruta.AddEstacion("Portal del sur", 601);
    	ruta.AddEstacion("Venecia", 605);
    	ruta.AddEstacion("General Santander", 607);
    	ruta.AddEstacion("NQS-Cl 30 Sur", 609);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Campín", 406);
    	ruta.AddEstacion("Simón Bolívar", 404);
    	ruta.AddEstacion("Av. Chile", 403);
    	ruta.AddEstacion("San Martín", 214);
    	ruta.AddEstacion("Rionegro", 213);
    	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("La campiña", 202);
    	ruta.AddEstacion("Portal Suba", 201);
    	this.AddRutaInvertida("G31", ruta, "Lunes a sábado 05:00-09:00*Lunes a sábado 16:00-20:00");

    	ruta= this.AddRuta("C71", "Lunes a sábado 09:00-16:00*Lunes a sábado 20:00-23:00", true);
    	ruta.AddEstacion("Santa Isabel", 611);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Campín", 406);
    	ruta.AddEstacion("Coliseo", 405);
    	ruta.AddEstacion("Simón Bolívar", 404);
    	ruta.AddEstacion("Av. Chile", 403);
    	ruta.AddEstacion("NQS - Calle 75", 402);
    	ruta.AddEstacion("San Martín", 214);
    	ruta.AddEstacion("Rionegro", 213);
    	ruta.AddEstacion("Suba - Cl. 95", 212);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Suba - Av. Boyacá", 206);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("La campiña", 202);
    	ruta.AddEstacion("Portal Suba", 201);
    	this.AddRutaInvertida("G71", ruta, "Lunes a sábado 09:00-16:00*Lunes a sábado 20:00-23:00");

    	ruta= this.AddRuta("C19", "Lunes a viernes 05:00-09:00", false);
    	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("La campiña", 202);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("Suba - Av. Boyacá", 206);
    	ruta.AddEstacion("Niza - Cl. 127", 207);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("San Martín", 214);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Museo del Oro", 801);
    	ruta.AddEstacion("Las aguas", 802);
    	this.AddRutaInvertida("J73", ruta, "Lunes a viernes 05:00-09:00");

    	ruta= this.AddRuta("C73", "Lunes a viernes 16:00-20:00", false);
    	ruta.AddEstacion("Las aguas", 802);
    	ruta.AddEstacion("Museo del Oro", 801);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("San Martín", 214);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Niza - Cl. 127", 207);
    	ruta.AddEstacion("Suba - Av. Boyacá", 206);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("La campiña", 202);
    	ruta.AddEstacion("Portal Suba", 201);

    	ruta= this.AddRuta("D20", "Lunes a viernes 05:00-22:00*Sábados 06:00-20:00", true);
    	ruta.AddEstacion("Portal de Usme", 701);
    	ruta.AddEstacion("Molinos", 702);
    	ruta.AddEstacion("Santa Lucía", 708);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Olaya", 711);
    	ruta.AddEstacion("Fucha", 713);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Polo", 313);
    	ruta.AddEstacion("Avenida 68", 309);
    	ruta.AddEstacion("Ferias", 308);
    	ruta.AddEstacion("Boyacá", 307);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Avenida Cali", 304);
    	ruta.AddEstacion("Portal de la 80", 301);
    	this.AddRutaInvertida("H20", ruta, "Lunes a viernes 05:00-22:00*Sábados 06:00-20:00");

    	ruta= this.AddRuta("D51", "Lunes a viernes 05:00-08:00", false);
    	ruta.AddEstacion("Portal de Usme", 701);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Minuto de Dios", 306);
    	ruta.AddEstacion("Portal de la 80", 301);

    	ruta= this.AddRuta("D60", "Lunes a viernes 16:00-20:00", false);
    	ruta.AddEstacion("Portal de Usme", 701);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Hortua", 715);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 57", 5);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Minuto de Dios", 306);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Carrera 90", 303);
    	ruta.AddEstacion("Portal de la 80", 301);
    	this.AddRutaInvertida("H50", ruta, "Lunes a viernes 05:00-08:00");

    	ruta= this.AddRuta("A50", "Lunes a viernes 06:00-08:00", false);
    	ruta.AddEstacion("Portal de la 80", 301);
    	ruta.AddEstacion("Carrera 90", 303);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Minuto de Dios", 306);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72", 2);

    	ruta= this.AddRuta("D50", "Lunes a viernes 05:00-08:00", false);
    	ruta.AddEstacion("Portal Américas", 501);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 57", 5);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Minuto de Dios", 306);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Portal de la 80", 301);
    	this.AddRutaInvertida("F62", ruta, "Lunes a viernes 17:00-20:00");

    	ruta= this.AddRuta("D70", "Lunes a viernes 09:00-23:00*Sábados 05:00-21:00", true);
    	ruta.AddEstacion("Las aguas", 802);
    	ruta.AddEstacion("Museo del Oro", 801);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Av. 39", 8);
    	ruta.AddEstacion("Polo", 313);
    	ruta.AddEstacion("Escuela militar", 312);
    	ruta.AddEstacion("Avenida 68", 309);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Portal de la 80", 301);
    	this.AddRutaInvertida("J24", ruta, "Lunes a viernes 09:00-23:00*Sábados 05:00-21:00");

    	ruta= this.AddRuta("D21", "Lunes a viernes 05:00-23:00*Sábados 06:00-23:00", true);
    	ruta.AddEstacion("Portal del Tunal", 705);
    	ruta.AddEstacion("Parque", 706);
    	ruta.AddEstacion("Santa Lucía", 708);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Olaya", 711);
    	ruta.AddEstacion("Fucha", 713);
    	ruta.AddEstacion("Nariño", 714);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 57", 5);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Avenida Cali", 304);
    	ruta.AddEstacion("Quirigua", 302);
    	ruta.AddEstacion("Portal de la 80", 301);
    	this.AddRutaInvertida("H21", ruta, "Lunes a viernes 05:00-23:00*Sábados 06:00-23:00");

    	ruta= this.AddRuta("D22", "Lunes a sábado 05:00-09:00*Lunes a sábado 16:00-20:00", false);
    	ruta.AddEstacion("Portal del sur", 601);
    	ruta.AddEstacion("Sevillana", 604);
    	ruta.AddEstacion("General Santander", 607);
    	ruta.AddEstacion("SENA", 610);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("Paloquemao", 410);
    	ruta.AddEstacion("Av. El Dorado", 408);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Av. Chile", 403);
    	ruta.AddEstacion("NQS - Calle 75", 402);
    	ruta.AddEstacion("Carrera 47", 311);
    	ruta.AddEstacion("Avenida 68", 309);
    	ruta.AddEstacion("Minuto de Dios", 306);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Portal de la 80", 301);
    	this.AddRutaInvertida("G22", ruta, "Lunes a sábado 05:00-09:00*Lunes a sábado 16:00-20:00");

    	ruta= this.AddRuta("D26", "Lunes a sábado 09:00-16:00*Lunes a sábado 20:00-23:00", true);
    	ruta.AddEstacion("Av. El Dorado", 408);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Av. Chile", 403);
    	ruta.AddEstacion("NQS - Calle 75", 402);
    	ruta.AddEstacion("Carrera 47", 311);
    	ruta.AddEstacion("Avenida 68", 309);
    	ruta.AddEstacion("Minuto de Dios", 306);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Portal de la 80", 301);
    	this.AddRutaInvertida("E26", ruta, "Lunes a sábado 09:00-16:00*Lunes a sábado 20:00-23:00");

    	ruta= this.AddRuta("F23", "Lunes a sábado 05:00-23:00", true);
    	ruta.AddEstacion("Las aguas", 802);
    	ruta.AddEstacion("Museo del Oro", 801);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("San Facon - Kr. 22", 516);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("Mundo Aventura", 507);
    	ruta.AddEstacion("Banderas", 505);
    	ruta.AddEstacion("Patio Bonito", 502);
    	ruta.AddEstacion("Portal Américas", 501);
    	this.AddRutaInvertida("J23", ruta, "Lunes a sábado 05:00-23:00");

    	ruta= this.AddRuta("F70", "Lunes a viernes 17:00-20:00", false);
    	ruta.AddEstacion("Portal Américas", 501);
    	ruta.AddEstacion("Biblioteca Tintal", 503);
    	ruta.AddEstacion("Banderas", 505);
    	ruta.AddEstacion("Pradera", 509);
    	ruta.AddEstacion("Puente aranda", 511);
    	ruta.AddEstacion("Carrera 43", 512);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("De la Sabana", 517);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	this.AddRutaInvertida("F60", ruta, "Lunes a viernes 05:00-09:00*Lunes a viernes 17:00-20:00");

    	ruta= this.AddRuta("K10", "Lunes a sábado 05:00-23:00", true);
    	ruta.AddEstacion("Portal del 20 de Julio", 1001);
    	ruta.AddEstacion("AV. 1a de mayo", 1003);
    	ruta.AddEstacion("Tercer milenio", 14);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Centro memoria", 913);
    	ruta.AddEstacion("Ciudad universitaria", 911);
    	ruta.AddEstacion("Gobernación", 908);
    	ruta.AddEstacion("CAN", 907);
    	ruta.AddEstacion("Salite - El Greco", 906);
    	ruta.AddEstacion("Normandía", 903);
    	ruta.AddEstacion("Modelia", 902);
    	ruta.AddEstacion("Portal Eldorado", 901);
    	this.AddRutaInvertida("L10", ruta, "Lunes a sábado 05:00-23:00");

    	ruta= this.AddRuta("B94", "Domingos y festivos 07:00-20:00", false);
    	ruta.AddEstacion("Portal de la 80", 301);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Minuto de Dios", 306);
    	ruta.AddEstacion("Ferias", 308);
    	ruta.AddEstacion("Avenida 68", 309);
    	ruta.AddEstacion("Carrera 53", 310);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("D94", ruta, "Domingos y festivos 07:00-20:00");

    	ruta= this.AddRuta("B92", "Domingos y festivos 06:00-22:00", false);
    	ruta.AddEstacion("Portal de Usme", 701);
    	ruta.AddEstacion("Molinos", 702);
    	ruta.AddEstacion("Consuelo", 703);
    	ruta.AddEstacion("Socorro", 704);
    	ruta.AddEstacion("Santa Lucía", 708);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Héroes", 116);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Calle 127", 110);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 142", 107);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("H92", ruta, "Domingos y festivos 06:00-22:00");

    	ruta= this.AddRuta("B93", "Domingos y festivos 06:00-21:00", false);
    	ruta.AddEstacion("Portal del Tunal", 705);
    	ruta.AddEstacion("Santa Lucía", 708);
    	ruta.AddEstacion("Calle 40 Sur", 709);
    	ruta.AddEstacion("Olaya", 711);
    	ruta.AddEstacion("Restrepo", 712);
    	ruta.AddEstacion("Fucha", 713);
    	ruta.AddEstacion("Nariño", 714);
    	ruta.AddEstacion("Tercer milenio", 14);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 85", 115);
    	ruta.AddEstacion("Virrey", 114);
    	ruta.AddEstacion("Pepe sierra", 111);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Toberín", 103);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("H93", ruta, "Domingos y festivos 06:00-21:00");

    	ruta= this.AddRuta("B90", "Domingos y festivos 06:00-22:00", false);
    	ruta.AddEstacion("Portal del sur", 601);
    	ruta.AddEstacion("Venecia", 605);
    	ruta.AddEstacion("Alquería", 606);
    	ruta.AddEstacion("General Santander", 607);
    	ruta.AddEstacion("SENA", 610);
    	ruta.AddEstacion("Santa Isabel", 611);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("Av. El Dorado", 408);
    	ruta.AddEstacion("Campín", 406);
    	ruta.AddEstacion("NQS - Calle 75", 402);
    	ruta.AddEstacion("La Castellana", 401);
    	ruta.AddEstacion("Calle 100", 113);
    	ruta.AddEstacion("Prado", 109);
    	ruta.AddEstacion("Alcalá", 108);
    	ruta.AddEstacion("Calle 146", 106);
    	ruta.AddEstacion("Portal del norte", 102);
    	this.AddRutaInvertida("G90", ruta, "Domingos y festivos 06:00-22:00");

    	ruta= this.AddRuta("C91", "Domingos y festivos 06:00-22:00", false);
    	ruta.AddEstacion("Portal Américas", 501);
    	ruta.AddEstacion("Biblioteca Tintal", 503);
    	ruta.AddEstacion("Banderas", 505);
    	ruta.AddEstacion("Pradera", 509);
    	ruta.AddEstacion("Zona industrial", 513);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("San Facon - Kr. 22", 516);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Escuela militar", 312);
    	ruta.AddEstacion("Suba - Cl. 95", 212);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("La campiña", 202);
    	ruta.AddEstacion("Portal Suba", 201);
    	this.AddRutaInvertida("F91", ruta, "Domingos y festivos 06:00-22:00");

    	ruta= this.AddRuta("C96", "Domingos y festivos 07:00-21:00", false);
    	ruta.AddEstacion("Portal del sur", 601);
    	ruta.AddEstacion("Perdomo", 602);
    	ruta.AddEstacion("Venecia", 605);
    	ruta.AddEstacion("NQS-Cl 38A Sur", 608);
    	ruta.AddEstacion("SENA", 610);
    	ruta.AddEstacion("Santa Isabel", 611);
    	ruta.AddEstacion("Comuneros", 612);
    	ruta.AddEstacion("Ricaurte", 515);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("Campín", 406);
    	ruta.AddEstacion("Coliseo", 405);
    	ruta.AddEstacion("Av. Chile", 403);
    	ruta.AddEstacion("Suba - Cl 100", 211);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Niza - Cl. 127", 207);
    	ruta.AddEstacion("Suba - Av. Boyacá", 206);
    	ruta.AddEstacion("Suba - TV. 91", 203);
    	ruta.AddEstacion("Portal Suba", 201);
    	this.AddRutaInvertida("G96", ruta, "Domingos y festivos 07:00-21:00");

    	ruta= this.AddRuta("D95", "Domingos y festivos 06:00-22:00", false);
    	ruta.AddEstacion("Las aguas", 802);
    	ruta.AddEstacion("Museo del Oro", 801);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Marly", 6);
    	ruta.AddEstacion("Calle 57", 5);
    	ruta.AddEstacion("Calle 63", 4);
    	ruta.AddEstacion("Calle 72", 2);
    	ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Escuela militar", 312);
    	ruta.AddEstacion("Boyacá", 307);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Avenida Cali", 304);
    	ruta.AddEstacion("Quirigua", 302);
    	ruta.AddEstacion("Portal de la 80", 301);
    	this.AddRutaInvertida("J95", ruta, "Domingos y festivos 06:00-22:00");

    	ruta= this.AddRuta("K97", "Domingos y festivos 06:00-22:00", false);
    	ruta.AddEstacion("Portal del 20 de Julio", 1001);
    	ruta.AddEstacion("AV. 1a de mayo", 1003);
    	ruta.AddEstacion("Tercer milenio", 14);
    	ruta.AddEstacion("Av. Jiménez", 13);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Centro memoria", 913);
    	ruta.AddEstacion("Ciudad universitaria", 911);
    	ruta.AddEstacion("Gobernación", 908);
    	ruta.AddEstacion("CAN", 907);
    	ruta.AddEstacion("Salite - El Greco", 906);
    	ruta.AddEstacion("Normandía", 903);
    	ruta.AddEstacion("Modelia", 902);
    	ruta.AddEstacion("Portal Eldorado", 901);
    	this.AddRutaInvertida("L97", ruta, "Domingos y festivos 06:00-22:00");
    	
    	ruta= this.AddRuta("L7", "Lunes a sábado 05:00-23:00*Domingos y festivos 06:00-22:00", true);
    	ruta.AddEstacion("San Diego", 1010);
    	ruta.AddEstacion("Policarpa", 1005);
    	ruta.AddEstacion("Ciudad Jardin", 1004);
    	ruta.AddEstacion("AV. 1a de mayo", 1003);
    	ruta.AddEstacion("Country Sur", 1002);
    	ruta.AddEstacion("Portal del 20 de Julio", 1001);
    	this.AddRutaInvertida("M7", ruta, "Lunes a sábado 05:00-23:00*Domingos y festivos 06:00-22:00");
    }
    
    public static String GetFechaRutas() {
    	
    	return "15/09/2012";
    }
}