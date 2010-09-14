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
        
        Ruta ruta=AddRuta("D10","L-V 5:30am-10:00pm\nS 6:00am-10:00pm", true); 
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioS(5, 22);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Mazuren", 104);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Carrera 47", 312);
        ruta.AddEstacion("Carrera 53", 311);
        ruta.AddEstacion("Carrera 77", 306);
        ruta.AddEstacion("Granja", 305);
        ruta.AddEstacion("AV. Cali", 304);
        ruta.AddEstacion("Carrera 90", 303);
        ruta.AddEstacion("Portal de la 80", 301);

        ruta= AddRutaInvertida("B10", ruta, "L-V 5:00am-9:30pm\nS 5:30am-9:30pm"); 

        ruta= AddRuta("B55","L-V 6:00am-8:30am",false);
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Carrera 77", 306);
        ruta.AddEstacion("Heroes", 115);
        
        ruta= AddRuta("H51","L-V 6:00am-9:00am", false); 
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Santa lucia", 708);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        ruta= AddRuta("H70","L-V 5:00am-9:00am", false); 
        ruta.SetHorarioLV(4, 9);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Narino", 714);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        AddRutaInvertida("B70", ruta, "L-V 4:30am-9:00am"); 
        
        ruta= AddRuta("H61","L-S 4:30pm-8:00pm", false);
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Narino", 714);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        AddRutaInvertida("B72", ruta, "L-S 5:30am-9:00am"); 

        ruta= AddRuta("H74","L-V 9:00am-11:00pm\nS 5:00am-11:00pm", true); 
        ruta.SetHorarioLV(9, 23);
        ruta.SetHorarioS(5, 23);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Narino", 714);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa lucia", 708);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        AddRutaInvertida("B73", ruta, "L-V 9:00am-11:00pm\nS 5:00am-11:00pm"); 

        ruta= AddRuta("H13","L-V 5:30am-10:00pm\nS 6:30am-10:00pm",true); 
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioS(6, 22);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Cardio infantil", 103);
        ruta.AddEstacion("Mazuren", 104);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Flores", 3);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 22", 11);
        ruta.AddEstacion("Tercer Milenio", 14);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Quiroga", 710);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Portal Tunal", 705);

        AddRutaInvertida("B13", ruta, "L-V 5:00am-10:00pm\nS 6:00am-9:30pm"); 
        
        ruta= AddRuta("B53","L-V 5:30am-8:30am", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Portal Tunal", 705);
        ruta.AddEstacion("Santa lucia", 708);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Portal Norte", 101);

        ruta= AddRuta("H52","L-V 6:00am-9:00am", false); 
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Portal Tunal", 705);

        ruta= AddRuta("B61","L-V 4:30pm-7:30pm", false); 
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Portal Tunal", 705);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Portal Norte", 101);

        ruta= AddRuta("H60","L-V 4:30pm-8:00pm",false); 
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioS(5, 9);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("CALLE 4O S", 709);
        ruta.AddEstacion("Portal Tunal", 705);

        AddRutaInvertida("B71", ruta,"L-V 5:30am-8:30am\nS 5:30am-9:00am"); 

        ruta= AddRuta("H73","L-V 5:30am-9:00am\nL-V 4:30pm-8:00pm", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Fucha", 713);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Portal Tunal", 705);

        ruta= AddRuta("C61","L-V 6:30am-8:00am\nL-V 4:30pm-8:00pm", false); 
        ruta.SetHorarioLV(6, 9);
        ruta.SetHorarioLV(17, 20);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Cardio infantil", 103);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Rionegro", 213);
        ruta.AddEstacion("Shaio", 209);
        ruta.AddEstacion("21 Angeles", 204);
        ruta.AddEstacion("Transversal 91", 203);
        ruta.AddEstacion("La campina", 202);
        ruta.AddEstacion("Portal Suba", 201);

        AddRutaInvertida("B50", ruta,"L-V 6:00am-8:30am\nL-V 5:00pm-7:30pm"); 
        
        ruta= AddRuta("J70","L-V 5:30am-9:00am", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);

        ruta= AddRuta("J72","L-V 9:00am-9:30pm\nS 6:30am-8:00pm", true); 
        ruta.SetHorarioLV(9, 22);
        ruta.SetHorarioS(7, 21);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);
        
        AddRutaInvertida("B74", ruta,"L-V 9:00am-10:30pm\nS 7:00am-8:30pm"); 

        ruta= AddRuta("F14","L-V 5:30am-10:00pm\nS 6:00am-10:00pm", true); 
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioLV(5, 22);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("CDS Carrera 32", 514);
        ruta.AddEstacion("Pradera", 509);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Transversal 86", 504);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Portalamericas", 501);

        AddRutaInvertida("B14", ruta,"L-V 5:00am-9:30pm\nS 5:30am-9:30pm"); 
        
        ruta= AddRuta("F61","L-V 5:00pm-8:00pm", false); 

        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Marsella", 508);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Portalamericas", 501);

        AddRutaInvertida("B52", ruta,"L-V 5:30am-8:30am"); 
        
        ruta= AddRuta("G11","L-V 6:00am-10:00pm\nS 6:30am-10:30pm", true); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Mazuren", 104);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Escuela Militar", 313);
        ruta.AddEstacion("El campin", 406);
        ruta.AddEstacion("U. Nacional", 407);
        ruta.AddEstacion("CAD", 409);
        ruta.AddEstacion("Paloquemao", 410);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("SENA", 610);
        ruta.AddEstacion("Alqueria", 606);
        ruta.AddEstacion("Venecia", 605);
        ruta.AddEstacion("Portal Sur", 601);

        AddRutaInvertida("B11", ruta,"L-V 5:30am-9:00pm\nS 6:00am-9:30pm"); 
        
        ruta= AddRuta("G61","L-V 5:00pm-8:00pm", false); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Mazuren", 104);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Madelena", 603);
        ruta.AddEstacion("Perdomo", 602);
        ruta.AddEstacion("PORTAL SUR", 601);
        
        ruta= AddRuta("B54","L-V 5:30am-8:00am", false);
        ruta.AddEstacion("PORTAL SUR", 601);
        ruta.AddEstacion("Perdomo", 602);
        ruta.AddEstacion("Madelena", 603);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Mazuren", 104);
                
        ruta= AddRuta("G12","L-S 5:00am-11:00pm", true); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("La Castellana", 401);
        ruta.AddEstacion("NQS Calle 75", 402);
        ruta.AddEstacion("Simon Bolivar", 404);
        ruta.AddEstacion("U. Nacional", 407);
        ruta.AddEstacion("Avenida Dorado", 408);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Comuneros", 612);
        ruta.AddEstacion("SANTA ISABEL", 611);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Madelena", 603);
        ruta.AddEstacion("Perdomo", 602);
        ruta.AddEstacion("PORTAL SUR", 601);

        AddRutaInvertida("B12", ruta,"L-S 5:00am-11:00pm"); 

        ruta= AddRuta("H15","L-V 5:30am-10:00pm\nS 6:00am-10:00pm", true); 
        ruta.AddEstacion("PORTAL SUBA", 201);
        ruta.AddEstacion("Niza Calle 127", 207);
        ruta.AddEstacion("Humedal Cordoba", 208);
        ruta.AddEstacion("Puente largo", 210);
        ruta.AddEstacion("Escuela Militar", 313);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Calle 22", 11);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Tercer Milenio", 14);
        ruta.AddEstacion("Narino", 714);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Biblioteca", 707);
        ruta.AddEstacion("Portal Tunal", 705);

        AddRutaInvertida("C15", ruta,"L-V 5:30am-10:00pm\nS 6:00am-10:00pm"); 
        
        ruta= AddRuta("H17","L-S 5:00am-9:00am\nL-S 4:00pm-8:00pm", false); 
        ruta.AddEstacion("Portal Suba", 201);
        ruta.AddEstacion("La campina", 202);
        ruta.AddEstacion("Avenida Boyaca", 206);
        ruta.AddEstacion("Shaio", 209);
        ruta.AddEstacion("Puente largo", 210);
        ruta.AddEstacion("San Martin", 214);
        ruta.AddEstacion("El Polo", 314);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Flores", 3);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Hospital", 716);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa lucia", 708);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        AddRutaInvertida("C17", ruta,"L-S 5:00am-9:00am\nL-S 4:00pm-8:00pm"); 
        
        ruta= AddRuta("F19","L-V 5:30am-10:00pm\nS 6:00am-10:00pm", true); 
        ruta.AddEstacion("Portal Suba", 201);
        ruta.AddEstacion("La campina", 202);
        ruta.AddEstacion("Transversal 91", 203);
        ruta.AddEstacion("Gratamira", 205);
        ruta.AddEstacion("Puente largo", 210);
        ruta.AddEstacion("Suba Calle 100", 211);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("americas Carrera 53A", 510);
        ruta.AddEstacion("Marsella", 508);
        ruta.AddEstacion("Mandalay", 506);
        ruta.AddEstacion("Banderas", 505);

        AddRutaInvertida("C19", ruta,"L-V 5:30am-10:00pm\nS 6:00am-10:00pm");  
        
        ruta= AddRuta("G71","L-S 9:00am-4:00pm \nL-V 8:00pm-11:00pm", true); 
        ruta.AddEstacion("Portal Suba", 201);
        ruta.AddEstacion("La campina", 202);
        ruta.AddEstacion("Transversal 91", 203);
        ruta.AddEstacion("Avenida Boyaca", 206);
        ruta.AddEstacion("Shaio", 209);
        ruta.AddEstacion("Puente largo", 210);
        ruta.AddEstacion("Suba Calle 95", 212);
        ruta.AddEstacion("Rionegro", 213);
        ruta.AddEstacion("San Martin", 214);
        ruta.AddEstacion("NQS Calle 75", 402);
        ruta.AddEstacion("Avenida Chile", 403);
        ruta.AddEstacion("Simon Bolivar", 404);
        ruta.AddEstacion("Coliseo", 405);
        ruta.AddEstacion("El Campin", 406);
        ruta.AddEstacion("U. Nacional", 407);
        ruta.AddEstacion("CAD", 409);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Santa isabel", 611);

        AddRutaInvertida("C71", ruta,"L-S 9:30am-4:00pm \nL-V 8:30pm-11:00pm"); 

        ruta= AddRuta("H20","L-V 5:30am-10:00pm\nS 6:00am-8:00pm", true); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Avenida Cali", 304);
        ruta.AddEstacion("Carrera 77", 306);
        ruta.AddEstacion("Boyaca", 308);
        ruta.AddEstacion("Ferias", 309);
        ruta.AddEstacion("Avenida 68", 310);
        ruta.AddEstacion("El Polo", 314);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Fucha", 713);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa lucia", 708);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        AddRutaInvertida("D20", ruta,"L-V 5:30am-10:00pm\nS 6:00am-8:00pm");  
        
        ruta= AddRuta("D51","L-V 5:30am-8:30am", false); 
        ruta.AddEstacion("Portal Usme", 701);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Minuto de Dios", 307);
        ruta.AddEstacion("Portal de la 80", 301);

        ruta= AddRuta("H50","L-V 5:30am-8:30am", false); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Carrera 90", 303);
        ruta.AddEstacion("Granja", 305);
        ruta.AddEstacion("Carrera 77", 306);
        ruta.AddEstacion("Minuto de Dios", 307);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Portal Usme", 701);

        AddRutaInvertida("D60", ruta,"L-V 4:30pm-8:30pm"); 
        
        ruta= AddRuta("D50","L-V 5:30am-8:30am", false); 
        ruta.AddEstacion("Portal Americas", 501);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Minuto de Dios", 307);
        ruta.AddEstacion("Carrera 77", 306);
        ruta.AddEstacion("Portal de la 80", 301);
        
        AddRutaInvertida("F62", ruta,"L-V 5:00pm-8:00pm");

        ruta= AddRuta("J24","L-V 5:30am-10:00pm\nS 5:30am-8:30pm", true); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Carrera 77", 306);
        ruta.AddEstacion("Avenida 68", 310);
        ruta.AddEstacion("Escuela Militar", 313);
        ruta.AddEstacion("El Polo", 314);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Calle 22", 11);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);

        AddRutaInvertida("D70", ruta,"L-V 9:00am-11:00pm\nS 6:00am-9:00pm"); 

        ruta= AddRuta("H21","L-V 5:00am-11:00pm\nS 6:00am-11:00pm", true); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Quirigua", 302);
        ruta.AddEstacion("Avenida Cali", 304);
        ruta.AddEstacion("Carrera 77", 306);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Flores", 3);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Narino", 714);
        ruta.AddEstacion("Fucha", 713);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa lucia", 708);
        ruta.AddEstacion("Parque", 706);
        ruta.AddEstacion("Portal Tunal", 705);
        
        AddRutaInvertida("D21", ruta,"L-V 5:00am-11:00pm\nS 6:00am-11:00pm"); 
        
        ruta= AddRuta("G22","L-S 5:00am-9:00am\nL-S 4:00pm-7:30pm", false); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Carrera 77", 306);
        ruta.AddEstacion("Minuto de Dios", 307);
        ruta.AddEstacion("Avenida 68", 310);
        ruta.AddEstacion("Carrera 47", 312);
        ruta.AddEstacion("NQS Calle 75", 402);
        ruta.AddEstacion("Avenida Chile", 403);
        ruta.AddEstacion("U. Nacional", 407);
        ruta.AddEstacion("Avenida Dorado", 408);
        ruta.AddEstacion("Paloquemao", 410);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("SENA", 610);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Sevillana", 604);
        ruta.AddEstacion("Portal Sur", 601);

        AddRutaInvertida("D22", ruta,"L-S 5:00am-9:00am\nL-S 4:00pm-7:30pm"); 
        
        ruta= AddRuta("E26","L-S 9:00am-4:00pm\nL-S 7:30pm-11:00pm", true); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Carrera 77", 306);
        ruta.AddEstacion("Minuto de Dios", 307);
        ruta.AddEstacion("Avenida 68", 310);
        ruta.AddEstacion("Carrera 47", 312);
        ruta.AddEstacion("NQS Calle 75", 402);
        ruta.AddEstacion("Avenida Chile", 403);
        ruta.AddEstacion("U. Nacional", 407);
        ruta.AddEstacion("Avenida Dorado", 408);

        AddRutaInvertida("D26", ruta,"L-S 9:30am-4:30pm\nL-S 8:00pm-11:00pm"); 
        
        ruta= AddRuta("J23","L-S 5:00am-11:00pm", true); 
        ruta.AddEstacion("Portalamericas", 501);
        ruta.AddEstacion("Patio Bonito", 502);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Mundo Aventura", 507);
        ruta.AddEstacion("Zona Industrial", 513);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("San Facon Carrera 22", 516);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);
        
        AddRutaInvertida("F23", ruta,"L-S 5:00am-11:00pm"); 
        
        ruta= AddRuta("F70","L-V 5:30am-9:00am\nL-V 4:30pm-8:00pm", false); 
        ruta.AddEstacion("Portalamericas", 501);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Pradera", 509);
        ruta.AddEstacion("Puente Aranda", 511);
        ruta.AddEstacion("Carrera 43", 512);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("De la Sabana", 517);
        ruta.AddEstacion("Avenida Jimenez", 13);

        AddRutaInvertida("F60", ruta,"L-V 5:00pm-8:30pm"); 

        ruta= AddRuta("D94","D-F 7:00am-8:00pm", false); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Carrera 53", 311);
        ruta.AddEstacion("Avenida 68", 310);
        ruta.AddEstacion("Ferias", 309);
        ruta.AddEstacion("Minuto de Dios", 307);
        ruta.AddEstacion("Carrera 77", 306);
        ruta.AddEstacion("Portal de la 80", 301);

        AddRutaInvertida("B94", ruta,"D-F 7:00am-8:00pm"); 
        
        ruta= AddRuta("H92","D-F 6:00am-10:00pm", false); 
        ruta.AddEstacion("PORTAL NORTE", 101);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa lucia", 708);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        AddRutaInvertida("B92", ruta,"D-F 6:00am-10:00pm"); 
        
        ruta= AddRuta("H93","D-F 7:00am-9:00pm", false); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberin", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Flores", 3);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 22", 11);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Tercer Milenio", 14);
        ruta.AddEstacion("Narino", 714);
        ruta.AddEstacion("Fucha", 713);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa lucia", 708);
        ruta.AddEstacion("Portal Tunal", 705);

        AddRutaInvertida("B93", ruta,"D-F 6:00am-8:00pm"); 
        
        ruta= AddRuta("G90","D-F 6:00am-10:00pm", false); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Alcala", 107);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("La Castellana", 401);
        ruta.AddEstacion("NQS Calle 75", 402);
        ruta.AddEstacion("El Campin", 406);
        ruta.AddEstacion("Avenida Dorado", 408);
        ruta.AddEstacion("CAD", 409);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Santa isabel", 611);
        ruta.AddEstacion("SENA", 610);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Alqueria", 606);
        ruta.AddEstacion("Venecia", 605);
        ruta.AddEstacion("Portal Sur", 601);

        AddRutaInvertida("B90", ruta,"D-F 6:00am-10:00pm"); 
        
        ruta= AddRuta("F91","D-F 6:00am-10:00pm", false); 
        ruta.AddEstacion("Portal Suba", 201);
        ruta.AddEstacion("La campina", 202);
        ruta.AddEstacion("Transversal 91", 203);
        ruta.AddEstacion("Shaio", 209);
        ruta.AddEstacion("Puente largo", 210);
        ruta.AddEstacion("Suba Calle 95", 212);
        ruta.AddEstacion("Escuela Militar", 313);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Flores", 3);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("San Facon Carrera 22", 516);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Zona Industrial", 513);
        ruta.AddEstacion("Pradera", 509);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Portalamericas", 501);

        AddRutaInvertida("C91", ruta,"D-F 6:00am-10:00pm"); 
        
        ruta= AddRuta("J95","D-F 6:00am-9:15pm", false); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Quirigua", 710);
        ruta.AddEstacion("AV. Cali", 304);
        ruta.AddEstacion("Carrera 77", 306);
        ruta.AddEstacion("Escuela Militar", 313);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);        
        
        AddRutaInvertida("D95", ruta,"D-F 6:00am-10:00pm");  
        
        ruta= AddRuta("B56","L-V 6:00am-8:00am", false); 
        ruta.AddEstacion("Portal Suba", 201);
        ruta.AddEstacion("Transversal 91", 203);
        ruta.AddEstacion("Heroes", 115);
        
        ruta= AddRuta("A15", "L-V 6:00am-8:00am", false); 
        ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("Niza Calle 127", 207);
    	ruta.AddEstacion("Humedal Cordoba", 208);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Escuela Militar", 313);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Marly",6);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Avenida 39", 8);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Calle 19", 12);
    	
    	ruta= AddRuta("A70", "6:30am-8:30am", false);        
    	ruta.AddEstacion("Toberin", 102);
    	ruta.AddEstacion("Calle 146", 105);
    	ruta.AddEstacion("Calle 142", 106);
    	ruta.AddEstacion("Alcala", 107);
    	ruta.AddEstacion("Prado", 108);
    	ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72",2);
 
	ruta= AddRuta("H27", "L-V 6:00am-8:30am\nL-V 5:00pm-9:30pm", false);
	ruta.AddEstacion("Portal Norte", 101);
    	ruta.AddEstacion("Virrey", 113);
    	ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72",2);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 63",4);
    	ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Portal Tunal", 705);
        
        AddRutaInvertida("B27", ruta, "L-V 5:30am-8:00am\nL-V 4:30pm-9:30pm");
        
        ruta= AddRuta("G52", "L-V 5:00pm-8:00pm", false);
        ruta.AddEstacion("Flores", 3);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("NQS Calle 75", 402);
        ruta.AddEstacion("Santa isabel", 611);
        ruta.AddEstacion("NQS Calle 30 Sur", 609);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Venecia", 605);
        ruta.AddEstacion("Perdomo", 602);
        ruta.AddEstacion("Portal Sur", 601);

        AddRutaInvertida("A52", ruta, "L-V 5:30am-8:00am");
        
        ruta= AddRuta("F28", "L-V 6:30am-8:30am\nL-V 4:30pm-8:00pm", false);
        ruta.AddEstacion("Portal Norte", 101);
    	ruta.AddEstacion("Prado", 108);
    	ruta.AddEstacion("Pepe sierra", 110);
    	ruta.AddEstacion("Calle 106", 111);
    	ruta.AddEstacion("NQS Calle 75", 402);
    	ruta.AddEstacion("Avenida Chile", 403);
    	ruta.AddEstacion("Simon Bolivar", 404);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("CAD", 409);
        ruta.AddEstacion("CDS Carrera 32", 514);
        ruta.AddEstacion("Zona Industrial", 513);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Portalamericas", 501);
        
        AddRutaInvertida("B28", ruta, "L-V 5:30am-8:30am\nL-V 5:00pm-8:00pm");
        
        ruta= AddRuta("E25", "L-V 5:30am-8:30am\nL-V 5:00pm-7:30pm", false);
        ruta.AddEstacion("Portal Usme", 701);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Tunal", 705);
        ruta.AddEstacion("Portal Sur", 601);
        ruta.AddEstacion("Sevillana", 604);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("SENA", 610);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Paloquemao", 410);
        ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Simon Bolivar", 404);
    	
        ruta= AddRuta("H25", "L-V 6:30am-8:30am\nL-V 5:00pm-8:15pm", false);
        ruta.AddEstacion("Simon Bolivar", 404);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("Paloquemao", 410);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("SENA", 610);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Sevillana", 604);
        ruta.AddEstacion("Portal Sur", 601);
        ruta.AddEstacion("Portal Tunal", 705);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);
        
        ruta= AddRuta("F29", "L-V 5:30am-8:30am\nL-V 5:00pm-8:30pm", false);
        ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("La campina", 202);
    	ruta.AddEstacion("Transversal 91", 203);
    	ruta.AddEstacion("Calle 72",2);
    	ruta.AddEstacion("Calle 63",4);
    	ruta.AddEstacion("Marly",6);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Calle 26", 10);
		ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Patio Bonito", 502);
        ruta.AddEstacion("Portalamericas", 501);
        
        AddRutaInvertida("C29", ruta, "L-V 5:30am-8:30am\nL-V 5:00pm-8:30pm");
        
        ruta= AddRuta("G30", "L-V 5:00am-9:00am\nL-V 4:00pm-8:00pm", false);
       	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("La campina", 202);
    	ruta.AddEstacion("Transversal 91", 203);
    	ruta.AddEstacion("Avenida Boyaca", 206);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Suba Calle 95", 212);
    	ruta.AddEstacion("NQS Calle 75", 402);
    	ruta.AddEstacion("Coliseo", 405);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("CAD", 409);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Santa isabel", 611);
        ruta.AddEstacion("NQS Calle 38A Sur", 608);
        ruta.AddEstacion("Portal Sur", 601);
    	
       	AddRutaInvertida("C30", ruta, "L-V 5:00am-9:00am\nL-V 4:00pm-8:00pm");
       	
       	ruta= AddRuta("G31", "L-V 5:00am-9:00am\nL-V 4:00pm-8:00pm", false);
       	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("La campina", 202);
    	ruta.AddEstacion("Transversal 91", 203);
       	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Rionegro", 213);
    	ruta.AddEstacion("San Martin", 214);
    	ruta.AddEstacion("Avenida Chile", 403);
    	ruta.AddEstacion("Simon Bolivar", 404);
    	ruta.AddEstacion("El Campin", 406);
    	ruta.AddEstacion("U. Nacional", 407);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("NQS Calle 30 Sur", 609);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Venecia", 605);
        ruta.AddEstacion("Portal Sur", 601);
    	
       	AddRutaInvertida("C31", ruta, "L-V 5:00am-9:00am\nL-V 4:00pm-8:00pm");
       	
       	ruta= AddRuta("J73", "L-V 5:00am-9:00am", false);
       	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("La campina", 202);
    	ruta.AddEstacion("Transversal 91", 203);
    	ruta.AddEstacion("Avenida Boyaca", 206);
    	ruta.AddEstacion("Niza Calle 127", 207);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("San Martin", 214);
    	ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);
       	
       	AddRutaInvertida("C73", ruta, "L-V 4:00pm-8:00pm");
       	
       	ruta= AddRuta("A50", "L-V 6:30am-8:30am", false);
       	ruta.AddEstacion("Portal de la 80", 301);
    	ruta.AddEstacion("Carrera 90", 303);
    	ruta.AddEstacion("Granja", 305);
    	ruta.AddEstacion("Carrera 77", 306);
    	ruta.AddEstacion("Minuto de Dios", 307);
        ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72",2);
    	
    	ruta= AddRuta("G96", "D-F 7:00am-9:00pm", false);    	
    	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("Transversal 91", 203);
    	ruta.AddEstacion("Avenida Boyaca", 206);
    	ruta.AddEstacion("Niza Calle 127", 207);
    	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Suba Calle 100", 211);
    	ruta.AddEstacion("Avenida Chile", 403);
    	ruta.AddEstacion("Coliseo", 405);
    	ruta.AddEstacion("El Campin", 406);
    	ruta.AddEstacion("CAD", 409);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Comuneros", 612);
        ruta.AddEstacion("Santa isabel", 611);
        ruta.AddEstacion("SENA", 610);
        ruta.AddEstacion("NQS Calle 38A Sur", 608);
        ruta.AddEstacion("Venecia", 605);
        ruta.AddEstacion("Perdomo", 602);
        ruta.AddEstacion("Portal Sur", 601);
        
    	AddRutaInvertida("C96", ruta, "D-F 7:00am-9:00pm");
        
        ruta= AddRuta("F1", "Todos los dias", true);
        
        ruta.AddEstacion("Portal Norte", 101);
    	ruta.AddEstacion("Toberin", 102);
    	ruta.AddEstacion("Cardio infantil", 103);
    	ruta.AddEstacion("Mazuren", 104);
    	ruta.AddEstacion("Calle 146", 105);
    	ruta.AddEstacion("Calle 142", 106);
    	ruta.AddEstacion("Alcala", 107);
    	ruta.AddEstacion("Prado", 108);
    	ruta.AddEstacion("Calle 127", 109);
    	ruta.AddEstacion("Pepe sierra", 110);
    	ruta.AddEstacion("Calle 106", 111);
    	ruta.AddEstacion("Calle 100", 112);
    	ruta.AddEstacion("Virrey", 113);
    	ruta.AddEstacion("Calle 85", 114);
    	ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72",2);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 63",4);
    	ruta.AddEstacion("Calle 57",5);
    	ruta.AddEstacion("Marly",6);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Avenida 39", 8);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jimenez", 13);
        ruta.AddEstacion("De la Sabana", 517);
        ruta.AddEstacion("San Facon Carrera 22", 516);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("CDS Carrera 32", 514);
        ruta.AddEstacion("Zona Industrial", 513);
        ruta.AddEstacion("Carrera 43", 512);
        ruta.AddEstacion("Puente Aranda", 511);
        ruta.AddEstacion("americas Carrera 53A", 510);
        ruta.AddEstacion("Pradera", 509);
        ruta.AddEstacion("Marsella", 508);
        ruta.AddEstacion("Mundo Aventura", 507);
        ruta.AddEstacion("Mandalay", 506);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Transversal 86", 504);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Patio Bonito", 502);
        ruta.AddEstacion("Portalamericas", 501);

        AddRutaInvertida("B1", ruta);
        
        ruta= AddRuta("G5", "Todos los dias", true);
        ruta.AddEstacion("Portal Norte", 101);
    	ruta.AddEstacion("Toberin", 102);
    	ruta.AddEstacion("Cardio infantil", 103);
    	ruta.AddEstacion("Mazuren", 104);
    	ruta.AddEstacion("Calle 146", 105);
    	ruta.AddEstacion("Calle 142", 106);
    	ruta.AddEstacion("Alcala", 107);
    	ruta.AddEstacion("Prado", 108);
    	ruta.AddEstacion("Calle 127", 109);
    	ruta.AddEstacion("Pepe sierra", 110);
    	ruta.AddEstacion("Calle 106", 111);
    	ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Heroes", 115);
        ruta.AddEstacion("El Polo", 314);
        ruta.AddEstacion("Escuela Militar", 313);
    	ruta.AddEstacion("NQS Calle 75", 402);
    	ruta.AddEstacion("Avenida Chile", 403);
    	ruta.AddEstacion("Simon Bolivar", 404);
    	ruta.AddEstacion("Coliseo", 405);
    	ruta.AddEstacion("El Campin", 406);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Avenida Dorado", 408);
    	ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("Paloquemao", 410);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Comuneros", 612);
        ruta.AddEstacion("Santa isabel", 611);
        ruta.AddEstacion("SENA", 610);
        ruta.AddEstacion("NQS Calle 30 Sur", 609);
        ruta.AddEstacion("NQS Calle 38A Sur", 608);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Alqueria", 606);
        ruta.AddEstacion("Venecia", 605);
        ruta.AddEstacion("Sevillana", 604);
        ruta.AddEstacion("Madelena", 603);
        ruta.AddEstacion("Perdomo", 602);
        ruta.AddEstacion("Portal Sur", 601);

        AddRutaInvertida("B5", ruta);

        ruta= AddRuta("H3", "Todos los dias", true);
        ruta.AddEstacion("Portal de la 80", 301);
    	ruta.AddEstacion("Quirigua", 302);
    	ruta.AddEstacion("Carrera 90", 303);
    	ruta.AddEstacion("Avenida Cali", 304);
    	ruta.AddEstacion("Granja", 305);
    	ruta.AddEstacion("Carrera 77", 306);
    	ruta.AddEstacion("Minuto de Dios", 307);
    	ruta.AddEstacion("Boyaca", 308);
    	ruta.AddEstacion("Ferias", 309);
    	ruta.AddEstacion("Avenida 68", 310);
    	ruta.AddEstacion("Carrera 53", 311);
    	ruta.AddEstacion("Carrera 47", 312);
    	ruta.AddEstacion("Escuela Militar", 313);
    	ruta.AddEstacion("El Polo", 314);
        ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72",2);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 63",4);
    	ruta.AddEstacion("Calle 57",5);
    	ruta.AddEstacion("Marly",6);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Avenida 39", 8);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Avenida Jimenez", 13);
    	ruta.AddEstacion("Tercer Milenio", 14);
        ruta.AddEstacion("Hospital", 716);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Narino", 714);
        ruta.AddEstacion("Fucha", 713);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Quiroga", 710);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa lucia", 708);
        ruta.AddEstacion("Biblioteca", 707);
        ruta.AddEstacion("Parque", 706);
        ruta.AddEstacion("Portal Tunal", 705);

        AddRutaInvertida("D3", ruta);
        
        ruta= AddRuta("H4", "Todos los dias", true);
        ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("La campina", 202);
    	ruta.AddEstacion("Transversal 91", 203);
    	ruta.AddEstacion("21 Angeles", 204);
    	ruta.AddEstacion("Gratamira", 205);
    	ruta.AddEstacion("Avenida Boyaca", 206);
    	ruta.AddEstacion("Niza Calle 127", 207);
    	ruta.AddEstacion("Humedal Cordoba", 208);
    	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Suba Calle 100", 211);
    	ruta.AddEstacion("Suba Calle 95", 212);
    	ruta.AddEstacion("Rionegro", 213);
    	ruta.AddEstacion("San Martin", 214);
        ruta.AddEstacion("Escuela Militar", 313);
    	ruta.AddEstacion("El Polo", 314);
        ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72",2);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 63",4);
    	ruta.AddEstacion("Calle 57",5);
    	ruta.AddEstacion("Marly",6);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Avenida 39", 8);
    	ruta.AddEstacion("Profamilia", 9);
    	ruta.AddEstacion("Calle 26", 10);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Calle 19", 12);
    	ruta.AddEstacion("Avenida Jimenez", 13);
    	ruta.AddEstacion("Tercer Milenio", 14);
        ruta.AddEstacion("Hospital", 716);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Narino", 714);
        ruta.AddEstacion("Fucha", 713);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Quiroga", 710);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa lucia", 708);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);
                
        AddRutaInvertida("C4", ruta);
    }
}