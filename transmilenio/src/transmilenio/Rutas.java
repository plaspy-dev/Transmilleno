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
    
    public Vector GetRutaID(String ubicacion, String destino) {

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
    
    public Ruta AddRuta(String ruta, String descripcion) {
        Ruta pRuta= new Ruta(ruta,descripcion);
        m_vRutas.addElement(pRuta);
        return pRuta;
    }
    
    public Ruta AddRutaInvertida(String ruta, Ruta original) {
        Ruta nueva= new Ruta(ruta, original.GetDescripcion());
        Vector estaciones= original.GetEstaciones();
        Vector ids= original.GetIDEstaciones();
        for(int i=estaciones.size()-1; i>=0; i--) {
            nueva.AddEstacion(estaciones.elementAt(i).toString(), ids.elementAt(i).toString());
        }
        if(original.GetHorarioLV()!=null)
            nueva.SetHorarioLV(original.GetHorarioLV().GetDesde(), original.GetHorarioLV().GetHasta());
        if(original.GetHorarioS()!=null)
            nueva.SetHorarioS(original.GetHorarioS().GetDesde(), original.GetHorarioS().GetHasta());
        if(original.GetHorarioF()!=null)
            nueva.SetHorarioF(original.GetHorarioF().GetDesde(), original.GetHorarioF().GetHasta());
	m_vRutas.addElement(nueva);
        return nueva;
    }
    
    public Rutas() {
        m_vRutas= new Vector();
        Ruta ruta=AddRuta("D10","L-V 5:00 AM-9:30 PM\nS 5:30 AM-9:30 PM");
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioS(5, 22);
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Mazuren", "104");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Pepe sierra", "110");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Virrey", "113");
        ruta.AddEstacion("Calle 85", "114");
        ruta.AddEstacion("Carrera 47", "312");
        ruta.AddEstacion("Carrera 53", "311");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Granja", "305");
        ruta.AddEstacion("AV CALI", "304");
        ruta.AddEstacion("Carrera 90", "303");
        ruta.AddEstacion("Portal de la 80", "301");

        ruta= AddRutaInvertida("B10", ruta);

        ruta= AddRuta("B55","L-V 6:00 AM-8:30 AM");
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Portal de la 80", "301");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Heroes", "115");
        
        ruta= AddRuta("H51","L-S 6:00 AM-9:00 AM");
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Pepe sierra", "110");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("Calle 63","4");
        ruta.AddEstacion("Calle 57","5");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Santa lucia", "708");
        ruta.AddEstacion("Socorro", "704");
        ruta.AddEstacion("Consuelo", "703");
        ruta.AddEstacion("Molinos", "702");
        ruta.AddEstacion("Portal Usme", "701");

        ruta= AddRuta("H70","L-V 4:30 AM-9:00 AM");
        ruta.SetHorarioLV(4, 9);
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Prado", "108");
        ruta.AddEstacion("Calle 127", "109");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("Calle 63","4");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Hortua", "715");
        ruta.AddEstacion("Narino", "714");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Calle 40 Sur", "709");
        ruta.AddEstacion("Socorro", "704");
        ruta.AddEstacion("Consuelo", "703");
        ruta.AddEstacion("Molinos", "702");
        ruta.AddEstacion("Portal Usme", "701");

        AddRutaInvertida("B70", ruta);
        
        ruta= AddRuta("H61","L-S 5:30 AM-9:00 AM");
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Calle 127", "109");
        ruta.AddEstacion("Pepe sierra", "110");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Calle 85", "114");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Narino", "714");
        ruta.AddEstacion("Molinos", "702");
        ruta.AddEstacion("Portal Usme", "701");

        AddRutaInvertida("B72", ruta);

        ruta= AddRuta("H74","L-V 9:00 AM - 11:00 PM\nS 5:00 AM - 11:00 PM");
        ruta.SetHorarioLV(9, 23);
        ruta.SetHorarioS(5, 23);
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Prado", "108");
        ruta.AddEstacion("Calle 127", "109");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("Calle 63","4");
        ruta.AddEstacion("Calle 57","5");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Hortua", "715");
        ruta.AddEstacion("Narino", "714");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Calle 40 Sur", "709");
        ruta.AddEstacion("Santa lucia", "708");
        ruta.AddEstacion("Socorro", "704");
        ruta.AddEstacion("Consuelo", "703");
        ruta.AddEstacion("Molinos", "702");
        ruta.AddEstacion("Portal Usme", "701");

        AddRutaInvertida("B73", ruta);

        ruta= AddRuta("H13","L-V 5:00 AM-10:00 PM\nS	6:00 AM - 9:30 PM");
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioS(6, 22);
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Cardio infantil", "103");
        ruta.AddEstacion("Mazuren", "104");
        ruta.AddEstacion("Calle 142", "106");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Calle 85", "114");
        ruta.AddEstacion("FLORES", "3");
        ruta.AddEstacion("Calle 63","4");
        ruta.AddEstacion("Calle 22", "11");
        ruta.AddEstacion("Tercer Milenio", "14");
        ruta.AddEstacion("Hortua", "715");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Olaya", "711");
        ruta.AddEstacion("Quiroga", "710");
        ruta.AddEstacion("Calle 40 Sur", "709");
        ruta.AddEstacion("Portal Tunal", "705");

        AddRutaInvertida("B13", ruta);
        
        ruta= AddRuta("B53","L-V 5:30 AM-8:30 AM");
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Portal Tunal", "705");
        ruta.AddEstacion("Santa lucia", "708");
        ruta.AddEstacion("Hortua", "715");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Pepe sierra", "110");
        ruta.AddEstacion("Calle 127", "109");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Portal Norte", "101");

        ruta= AddRuta("H52","L-V 6:00 AM-9:00 AM");
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Prado", "108");
        ruta.AddEstacion("Calle 45", "7");
        ruta.AddEstacion("Avenida 39", "8");
        ruta.AddEstacion("Profamilia", "9");
        ruta.AddEstacion("Calle 26", "10");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Portal Tunal", "705");

        ruta= AddRuta("B61","L-V 4:30 PM-7:30 PM");
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Portal Tunal", "705");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Calle 26", "10");
        ruta.AddEstacion("Profamilia", "9");
        ruta.AddEstacion("Avenida 39", "8");
        ruta.AddEstacion("Calle 45", "7");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Calle 142", "106");
        ruta.AddEstacion("Portal Norte", "101");

        ruta= AddRuta("H60","L-V 5:30 AM-8:30 AM\nS 5:30 AM-9:00 AM");
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioS(5, 9);
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Prado", "108");
        ruta.AddEstacion("Pepe sierra", "110");
        ruta.AddEstacion("Virrey", "113");
        ruta.AddEstacion("Calle 85", "114");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Olaya", "711");
        ruta.AddEstacion("CALLE 4O S", "709");
        ruta.AddEstacion("Portal Tunal", "705");

        AddRutaInvertida("B71", ruta);

        ruta= AddRuta("H73","L-V 5:30 AM-9:00 AM y 4:30 PM-8:00 PM");
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Calle 127", "109");
        ruta.AddEstacion("Pepe sierra", "110");
        ruta.AddEstacion("Calle 45", "7");
        ruta.AddEstacion("Avenida 39", "8");
        ruta.AddEstacion("Profamilia", "9");
        ruta.AddEstacion("Calle 26", "10");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Fucha", "713");
        ruta.AddEstacion("Olaya", "711");
        ruta.AddEstacion("Calle 40 Sur", "709");
        ruta.AddEstacion("Portal Tunal", "705");

        ruta= AddRuta("C61","L-V 6:00 AM-8:30 AM y 5:00 PM-7:30 PM");
        ruta.SetHorarioLV(6, 9);
        ruta.SetHorarioLV(17, 20);
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Cardio infantil", "103");
        ruta.AddEstacion("Calle 142", "106");
        ruta.AddEstacion("Calle 106", "111");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Virrey", "113");
        ruta.AddEstacion("Calle 85", "114");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Rionegro", "213");
        ruta.AddEstacion("Shaio", "209");
        ruta.AddEstacion("21 Angeles", "204");
        ruta.AddEstacion("Transversal 91", "203");
        ruta.AddEstacion("La campina", "202");
        ruta.AddEstacion("Portal Suba", "201");

        AddRutaInvertida("B50", ruta);
        
        ruta= AddRuta("J70","L-V 5:30 AM-9:00 AM");
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Calle 142", "106");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Prado", "108");
        ruta.AddEstacion("Calle 127", "109");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Museo del oro", "801");
        ruta.AddEstacion("LAS AGUAS", "802");

        ruta= AddRuta("J72","L-V 9:00 AM-10:00 PM\nS 7:00 AM-8:30 PM");
        ruta.SetHorarioLV(9, 22);
        ruta.SetHorarioS(7, 21);
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Calle 142", "106");
        ruta.AddEstacion("Prado", "108");
        ruta.AddEstacion("Pepe sierra", "110");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Virrey", "113");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("Calle 57","5");
        ruta.AddEstacion("Avenida 39", "8");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Museo del oro", "801");
        ruta.AddEstacion("LAS AGUAS", "802");
        
        AddRutaInvertida("B74", ruta);

        ruta= AddRuta("F14","L-V 5:00 AM-9:30 PM\nS 5:30 AM-9:30 PM");
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioLV(5, 22);
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Calle 127", "109");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Calle 85", "114");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Calle 45", "7");
        ruta.AddEstacion("Profamilia", "9");
        ruta.AddEstacion("Calle 26", "10");
        ruta.AddEstacion("CDS Carrera 32", "514");
        ruta.AddEstacion("Pradera", "509");
        ruta.AddEstacion("Banderas", "505");
        ruta.AddEstacion("Transversal 86", "504");
        ruta.AddEstacion("Biblioteca el tintal", "503");
        ruta.AddEstacion("Portal Americas", "501");

        AddRutaInvertida("B14", ruta);
        
        ruta= AddRuta("F61","L-V 5:30 AM-8:30 AM");
        // VOY AQUI
        // y esto genera error
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Virrey", "113");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Marsella", "508");
        ruta.AddEstacion("Banderas", "505");
        ruta.AddEstacion("Biblioteca el tintal", "503");
        ruta.AddEstacion("Portal Americas", "501");

        AddRutaInvertida("B52", ruta);
        
        ruta= AddRuta("G11","L-V 5:30 AM-9:00 PM\nS 6:00 AM-9:30 PM");
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Mazuren", "104");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Pepe sierra", "110");
        ruta.AddEstacion("Calle 106", "111");
        ruta.AddEstacion("Virrey", "113");
        ruta.AddEstacion("Calle 85", "114");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Escuela Militar", "313");
        ruta.AddEstacion("El campin", "406");
        ruta.AddEstacion("U. Nacional", "407");
        ruta.AddEstacion("CAD", "409");
        ruta.AddEstacion("Paloquemao", "410");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("SENA", "610");
        ruta.AddEstacion("Alqueria", "606");
        ruta.AddEstacion("Venecia", "605");
        ruta.AddEstacion("Portal Sur", "601");

        AddRutaInvertida("B11", ruta);
        
        ruta= AddRuta("G61","L-V 5:30 AM-8:00 AM");
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Mazuren", "104");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Calle 142", "106");
        ruta.AddEstacion("Pepe sierra", "110");
        ruta.AddEstacion("Calle 106", "111");
        ruta.AddEstacion("Virrey", "113");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("General Santander", "607");
        ruta.AddEstacion("PORTAL SUR", "601");

        AddRutaInvertida("B54", ruta);
        
        ruta= AddRuta("G12","L-S 5:00 AM-9:00 PM");
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Calle 142", "106");
        ruta.AddEstacion("Prado", "108");
        ruta.AddEstacion("Calle 127", "109");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("La Castellana", "401");
        ruta.AddEstacion("NQS Calle 75", "402");
        ruta.AddEstacion("Simon Bolivar", "404");
        ruta.AddEstacion("U. Nacional", "407");
        ruta.AddEstacion("Avenida Dorado", "408");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("Comuneros", "612");
        ruta.AddEstacion("SANTA ISABEL", "611");
        ruta.AddEstacion("General Santander", "607");
        ruta.AddEstacion("Madelena", "603");
        ruta.AddEstacion("Perdomo", "602");
        ruta.AddEstacion("PORTAL SUR", "601");

        AddRutaInvertida("B12", ruta);

        ruta= AddRuta("H15","L-V 5:30 AM-10:00 PM\nS 6:00 AM-10:00 PM");
        ruta.AddEstacion("PORTAL SUBA", "201");
        ruta.AddEstacion("Niza Calle 127", "207");
        ruta.AddEstacion("Humedal Cordoba", "208");
        ruta.AddEstacion("Puente largo", "210");
        ruta.AddEstacion("Escuela Militar", "313");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Calle 45", "7");
        ruta.AddEstacion("Avenida 39", "8");
        ruta.AddEstacion("Calle 22", "11");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Tercer Milenio", "14");
        ruta.AddEstacion("Narino", "714");
        ruta.AddEstacion("Olaya", "711");
        ruta.AddEstacion("Calle 40 Sur", "709");
        ruta.AddEstacion("Biblioteca", "707");
        ruta.AddEstacion("Portal Tunal", "705");

        AddRutaInvertida("C15", ruta);
        
        ruta= AddRuta("H17","L-S 5:00 AM-9:00 AM");
        ruta.AddEstacion("Portal Suba", "201");
        ruta.AddEstacion("La campina", "202");
        ruta.AddEstacion("Avenida Boyaca", "206");
        ruta.AddEstacion("Shaio", "209");
        ruta.AddEstacion("Puente largo", "210");
        ruta.AddEstacion("San Martin", "214");
        ruta.AddEstacion("El Polo", "314");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("FLORES", "3");
        ruta.AddEstacion("Calle 57","5");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Hospital", "716");
        ruta.AddEstacion("Hortua", "715");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Calle 40 Sur", "709");
        ruta.AddEstacion("Santa lucia", "708");
        ruta.AddEstacion("Socorro", "704");
        ruta.AddEstacion("Consuelo", "703");
        ruta.AddEstacion("Molinos", "702");
        ruta.AddEstacion("Portal Usme", "701");

        AddRutaInvertida("C17", ruta);
        
        ruta= AddRuta("F19","L-V 5:30 AM-10:00PM\nS 6:00 AM-10:00 PM");
        ruta.AddEstacion("Portal Suba", "201");
        ruta.AddEstacion("La campina", "202");
        ruta.AddEstacion("Transversal 91", "203");
        ruta.AddEstacion("Gratamira", "205");
        ruta.AddEstacion("Puente largo", "210");
        ruta.AddEstacion("Suba Calle 100", "211");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("Calle 63","4");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Calle 45", "7");
        ruta.AddEstacion("Profamilia", "9");
        ruta.AddEstacion("Calle 26", "10");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("Americas Carrera 53A", "510");
        ruta.AddEstacion("Marsella", "508");
        ruta.AddEstacion("Mandalay", "506");
        ruta.AddEstacion("Banderas", "505");

        AddRutaInvertida("C19", ruta);
        
        ruta= AddRuta("G16","L-S 5:00 AM-9:00 AM y 4:00 PM-8:00 PM");
        ruta.AddEstacion("Portal Suba", "201");
        ruta.AddEstacion("La campina", "202");
        ruta.AddEstacion("Transversal 91", "203");
        ruta.AddEstacion("Avenida Boyaca", "206");
        ruta.AddEstacion("Shaio", "209");
        ruta.AddEstacion("Puente largo", "210");
        ruta.AddEstacion("Suba Calle 95", "212");
        ruta.AddEstacion("Rionegro", "213");
        ruta.AddEstacion("San Martin", "214");
        ruta.AddEstacion("NQS Calle 75", "402");
        ruta.AddEstacion("Avenida Chile", "403");
        ruta.AddEstacion("Simon Bolivar", "404");
        ruta.AddEstacion("Coliseo", "405");
        ruta.AddEstacion("El Campin", "406");
        ruta.AddEstacion("U. Nacional", "407");
        ruta.AddEstacion("CAD", "409");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("Santa isabel", "611");
        ruta.AddEstacion("NQS Calle 30 Sur", "609");
        ruta.AddEstacion("NQS Calle 38A Sur", "608");
        ruta.AddEstacion("General Santander", "607");
        ruta.AddEstacion("Venecia", "605");
        ruta.AddEstacion("PORTAL SUR", "601");

        AddRutaInvertida("C16", ruta);
        
        ruta= AddRuta("G71","L-S 9:30 AM-4:00 PM  y 8:30 PM-11:00 PM");
        ruta.AddEstacion("Portal Suba", "201");
        ruta.AddEstacion("La campina", "202");
        ruta.AddEstacion("Transversal 91", "203");
        ruta.AddEstacion("Avenida Boyaca", "206");
        ruta.AddEstacion("Shaio", "209");
        ruta.AddEstacion("Puente largo", "210");
        ruta.AddEstacion("Suba Calle 95", "212");
        ruta.AddEstacion("Rionegro", "213");
        ruta.AddEstacion("San Martin", "214");
        ruta.AddEstacion("NQS Calle 75", "402");
        ruta.AddEstacion("Avenida Chile", "403");
        ruta.AddEstacion("Simon Bolivar", "404");
        ruta.AddEstacion("Coliseo", "405");
        ruta.AddEstacion("El Campin", "406");
        ruta.AddEstacion("U. Nacional", "407");
        ruta.AddEstacion("CAD", "409");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("Santa isabel", "611");

        AddRutaInvertida("C71", ruta);

        ruta= AddRuta("H20","L-V 5:30 AM-10:00 PM\nS 6:00 AM-8:00 PM");
        ruta.AddEstacion("Portal de la 80", "301");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Boyaca", "308");
        ruta.AddEstacion("Ferias", "309");
        ruta.AddEstacion("Avenida 68", "310");
        ruta.AddEstacion("El Polo", "314");
        ruta.AddEstacion("Calle 45", "7");
        ruta.AddEstacion("Profamilia", "9");
        ruta.AddEstacion("Calle 26", "10");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Fucha", "713");
        ruta.AddEstacion("Olaya", "711");
        ruta.AddEstacion("Calle 40 Sur", "709");
        ruta.AddEstacion("Santa lucia", "708");
        ruta.AddEstacion("Molinos", "702");
        ruta.AddEstacion("Portal Usme", "701");

        AddRutaInvertida("D20", ruta);
        
        ruta= AddRuta("D51","L-V 5:30 AM-8:30 AM");
        ruta.AddEstacion("Portal Usme", "701");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Calle 26", "10");
        ruta.AddEstacion("Profamilia", "9");
        ruta.AddEstacion("Avenida 39", "8");
        ruta.AddEstacion("Calle 45", "7");
        ruta.AddEstacion("Minuto de Dios", "307");
        ruta.AddEstacion("Portal de la 80", "301");

        ruta= AddRuta("H50","L-V 4:30 PM-8:30 PM");
        ruta.AddEstacion("Portal de la 80", "301");
        ruta.AddEstacion("Carrera 90", "303");
        ruta.AddEstacion("Granja", "305");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Minuto de Dios", "307");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("Calle 57","5");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Avenida 39", "8");
        ruta.AddEstacion("Hortua", "715");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Portal Usme", "701");

        AddRutaInvertida("D60", ruta);
        
        ruta= AddRuta("D50","L-V 5:30 AM-8:30 AM");
        ruta.AddEstacion("Portal Americas", "501");
        ruta.AddEstacion("Calle 26", "10");
        ruta.AddEstacion("Profamilia", "9");
        ruta.AddEstacion("Calle 45", "7");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Calle 57","5");
        ruta.AddEstacion("Calle 63","4");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Minuto de Dios", "307");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Portal de la 80", "301");

        ruta= AddRuta("J24","L-V 9:00 AM-11:00PM\nS 6:00 AM-9:00 PM");
        ruta.AddEstacion("Portal de la 80", "301");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Avenida 68", "310");
        ruta.AddEstacion("Escuela Militar", "313");
        ruta.AddEstacion("El Polo", "314");
        ruta.AddEstacion("Avenida 39", "8");
        ruta.AddEstacion("Calle 22", "11");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Museo del oro", "801");
        ruta.AddEstacion("LAS AGUAS", "802");

        AddRutaInvertida("D70", ruta);

        ruta= AddRuta("H21","L-V 5:00 AM-11:00 PM\nS 6:00 AM-11:00 PM");
        ruta.AddEstacion("Portal de la 80", "301");
        ruta.AddEstacion("Quirigua", "302");
        ruta.AddEstacion("Avenida Cali", "304");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("FLORES", "3");
        ruta.AddEstacion("Calle 63","4");
        ruta.AddEstacion("Calle 57","5");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Narino", "714");
        ruta.AddEstacion("Fucha", "713");
        ruta.AddEstacion("Olaya", "711");
        ruta.AddEstacion("Santa lucia", "708");
        ruta.AddEstacion("Parque", "706");
        ruta.AddEstacion("Portal Tunal", "705");
        
        AddRutaInvertida("D21", ruta);
        
        ruta= AddRuta("G22","L-V 5:30 AM-9:00 AM y 4:00 PM-7:30 PM\nS	6:00 AM-9:00 AM	y 4:00 PM-7:30 PM");
        ruta.AddEstacion("Portal de la 80", "301");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Minuto de Dios", "307");
        ruta.AddEstacion("Avenida 68", "310");
        ruta.AddEstacion("Carrera 47", "312");
        ruta.AddEstacion("NQS Calle 75", "402");
        ruta.AddEstacion("Avenida Chile", "403");
        ruta.AddEstacion("U. Nacional", "407");
        ruta.AddEstacion("Avenida Dorado", "408");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("SENA", "610");
        ruta.AddEstacion("General Santander", "607");
        ruta.AddEstacion("Sevillana", "604");
        ruta.AddEstacion("Portal Sur", "601");

        AddRutaInvertida("D22", ruta);
        
        ruta= AddRuta("E26","L-S 9:30 AM-4:00 PM y 8:00 PM-10:00 PM");
        ruta.AddEstacion("Portal de la 80", "301");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Minuto de Dios", "307");
        ruta.AddEstacion("Avenida 68", "310");
        ruta.AddEstacion("Carrera 47", "312");
        ruta.AddEstacion("NQS Calle 75", "402");
        ruta.AddEstacion("Avenida Chile", "403");
        ruta.AddEstacion("U. Nacional", "407");
        ruta.AddEstacion("Avenida Dorado", "408");

        AddRutaInvertida("D26", ruta);
        
        ruta= AddRuta("J23","L-S 5:00 AM-11:00 PM");
        ruta.AddEstacion("Portal Americas", "501");
        ruta.AddEstacion("Patio Bonito", "502");
        ruta.AddEstacion("Banderas", "505");
        ruta.AddEstacion("Mundo Aventura", "507");
        ruta.AddEstacion("Zona Industrial", "513");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("San Facon Carrera 22", "516");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Museo del oro", "801");
        ruta.AddEstacion("LAS AGUAS", "802");
        
        AddRutaInvertida("F23", ruta);
        
        ruta= AddRuta("F70","L-V 5:00 PM-8:30 PM");
        ruta.AddEstacion("Portal Americas", "501");
        ruta.AddEstacion("Biblioteca el tintal", "503");
        ruta.AddEstacion("Banderas", "505");
        ruta.AddEstacion("Pradera", "509");
        ruta.AddEstacion("Puente Aranda", "511");
        ruta.AddEstacion("Carrera 43", "512");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("De la Sabana", "517");
        ruta.AddEstacion("Avenida Jimenez", "13");

        AddRutaInvertida("F60", ruta);

        ruta= AddRuta("D94","D-F 7:00AM-8:00 PM");
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Carrera 53", "311");
        ruta.AddEstacion("Avenida 68", "310");
        ruta.AddEstacion("Ferias", "309");
        ruta.AddEstacion("Minuto de Dios", "307");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Portal de la 80", "301");

        AddRutaInvertida("B94", ruta);
        
        ruta= AddRuta("H92","D-F 6:00 AM-10:00 PM");
        ruta.AddEstacion("PORTAL NORTE", "101");
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Calle 142", "106");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Calle 127", "109");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Calle 85", "114");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("Calle 63","4");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Calle 45", "7");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Calle 40 Sur", "709");
        ruta.AddEstacion("Santa lucia", "708");
        ruta.AddEstacion("Socorro", "704");
        ruta.AddEstacion("Consuelo", "703");
        ruta.AddEstacion("Molinos", "702");
        ruta.AddEstacion("Portal Usme", "701");

        AddRutaInvertida("B92", ruta);
        
        ruta= AddRuta("H93","D-F 6:00 AM-8:00 PM");
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Toberin", "102");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Pepe sierra", "110");
        ruta.AddEstacion("Virrey", "113");
        ruta.AddEstacion("Calle 85", "114");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("FLORES", "3");
        ruta.AddEstacion("Calle 63","4");
        ruta.AddEstacion("Calle 22", "11");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Tercer Milenio", "14");
        ruta.AddEstacion("Narino", "714");
        ruta.AddEstacion("Fucha", "713");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Olaya", "711");
        ruta.AddEstacion("Calle 40 Sur", "709");
        ruta.AddEstacion("Santa lucia", "708");
        ruta.AddEstacion("Portal Tunal", "705");

        AddRutaInvertida("B93", ruta);
        
        ruta= AddRuta("G90","D-F 6:00 AM y 10:00 PM");
        ruta.AddEstacion("Portal Norte", "101");
        ruta.AddEstacion("Calle 146", "105");
        ruta.AddEstacion("Alcala", "107");
        ruta.AddEstacion("Prado", "108");
        ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Escuela Militar", "313");
        ruta.AddEstacion("NQS Calle 75", "402");
        ruta.AddEstacion("El Campin", "406");
        ruta.AddEstacion("Avenida Dorado", "408");
        ruta.AddEstacion("CAD", "409");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("Santa isabel", "611");
        ruta.AddEstacion("SENA", "610");
        ruta.AddEstacion("General Santander", "607");
        ruta.AddEstacion("Alqueria", "606");
        ruta.AddEstacion("Venecia", "605");
        ruta.AddEstacion("Portal Sur", "601");

        AddRutaInvertida("B90", ruta);
        
        ruta= AddRuta("F91","D-F 6:00 AM-10:00PM");
        ruta.AddEstacion("Portal Suba", "201");
        ruta.AddEstacion("La campina", "202");
        ruta.AddEstacion("Transversal 91", "203");
        ruta.AddEstacion("Shaio", "209");
        ruta.AddEstacion("Puente largo", "210");
        ruta.AddEstacion("Suba Calle 95", "212");
        ruta.AddEstacion("Escuela Militar", "313");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("FLORES", "3");
        ruta.AddEstacion("Calle 63","4");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Calle 45", "7");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("San Facon Carrera 22", "516");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("Zona Industrial", "513");
        ruta.AddEstacion("Pradera", "509");
        ruta.AddEstacion("Banderas", "505");
        ruta.AddEstacion("Biblioteca el tintal", "503");
        ruta.AddEstacion("Portal Americas", "501");

        AddRutaInvertida("C91", ruta);
        
        ruta= AddRuta("J95","D-F 6:00 AM-10:00PM");
        ruta.AddEstacion("Portal de la 80", "301");
        ruta.AddEstacion("Quiroga", "710");
        ruta.AddEstacion("AV CALI", "304");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Escuela Militar", "313");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("Calle 63","4");
        ruta.AddEstacion("Calle 57","5");
        ruta.AddEstacion("Marly","6");
        ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("Museo del oro", "801");
        ruta.AddEstacion("LAS AGUAS", "802");        
        
        AddRutaInvertida("D95", ruta);
        
        ruta= AddRuta("F62","L-V 5:00 PM-8:00 PM");
        ruta.AddEstacion("Portal de la 80", "301");
        ruta.AddEstacion("Carrera 77", "306");
        ruta.AddEstacion("Minuto de Dios", "307");
        ruta.AddEstacion("Calle 76", "1");
    	ruta.AddEstacion("Calle 72","2");
        ruta.AddEstacion("Calle 63","4");
    	ruta.AddEstacion("Calle 57","5");
    	ruta.AddEstacion("Marly","6");
    	ruta.AddEstacion("Calle 45", "7");
    	ruta.AddEstacion("Profamilia", "9");
    	ruta.AddEstacion("Calle 26", "10");
        ruta.AddEstacion("Portal Americas", "501");
        
        
        ruta= AddRuta("B56","L-V 6:00 AM-8:00 AM");
        ruta.AddEstacion("Portal Suba", "201");
        ruta.AddEstacion("Transversal 91", "203");
        ruta.AddEstacion("Heroes", "115");
        
        ruta= AddRuta("A15", "L-V 6:00 AM-8:00 AM");
        ruta.AddEstacion("Portal Suba", "201");
    	ruta.AddEstacion("Niza Calle 127", "207");
    	ruta.AddEstacion("Humedal Cordoba", "208");
    	ruta.AddEstacion("Puente largo", "210");
    	ruta.AddEstacion("Escuela Militar", "313");
        ruta.AddEstacion("Calle 76", "1");
        ruta.AddEstacion("Marly","6");
    	ruta.AddEstacion("Calle 45", "7");
    	ruta.AddEstacion("Avenida 39", "8");
    	ruta.AddEstacion("Calle 22", "11");
    	ruta.AddEstacion("Calle 19", "12");        
        
        ruta= AddRuta("F1", "Todos los dias");
        
        ruta.AddEstacion("Portal Norte", "101");
    	ruta.AddEstacion("Toberin", "102");
    	ruta.AddEstacion("Cardio infantil", "103");
    	ruta.AddEstacion("Mazuren", "104");
    	ruta.AddEstacion("Calle 146", "105");
    	ruta.AddEstacion("Calle 142", "106");
    	ruta.AddEstacion("Alcala", "107");
    	ruta.AddEstacion("Prado", "108");
    	ruta.AddEstacion("Calle 127", "109");
    	ruta.AddEstacion("Pepe sierra", "110");
    	ruta.AddEstacion("Calle 106", "111");
    	ruta.AddEstacion("Calle 100", "112");
    	ruta.AddEstacion("Virrey", "113");
    	ruta.AddEstacion("Calle 85", "114");
    	ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("Calle 76", "1");
    	ruta.AddEstacion("Calle 72","2");
    	ruta.AddEstacion("FLORES", "3");
    	ruta.AddEstacion("Calle 63","4");
    	ruta.AddEstacion("Calle 57","5");
    	ruta.AddEstacion("Marly","6");
    	ruta.AddEstacion("Calle 45", "7");
    	ruta.AddEstacion("Avenida 39", "8");
    	ruta.AddEstacion("Profamilia", "9");
    	ruta.AddEstacion("Calle 26", "10");
    	ruta.AddEstacion("Calle 22", "11");
    	ruta.AddEstacion("Calle 19", "12");
        ruta.AddEstacion("Avenida Jimenez", "13");
        ruta.AddEstacion("De la Sabana", "517");
        ruta.AddEstacion("San Facon Carrera 22", "516");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("CDS Carrera 32", "514");
        ruta.AddEstacion("Zona Industrial", "513");
        ruta.AddEstacion("Carrera 43", "512");
        ruta.AddEstacion("Puente Aranda", "511");
        ruta.AddEstacion("Americas Carrera 53A", "510");
        ruta.AddEstacion("Pradera", "509");
        ruta.AddEstacion("Marsella", "508");
        ruta.AddEstacion("Mundo Aventura", "507");
        ruta.AddEstacion("Mandalay", "506");
        ruta.AddEstacion("Banderas", "505");
        ruta.AddEstacion("Transversal 86", "504");
        ruta.AddEstacion("Biblioteca el tintal", "503");
        ruta.AddEstacion("Patio Bonito", "502");
        ruta.AddEstacion("Portal Americas", "501");

        AddRutaInvertida("B1", ruta);
        
        ruta= AddRuta("G5", "Todos los dias");
        ruta.AddEstacion("Portal Norte", "101");
    	ruta.AddEstacion("Toberin", "102");
    	ruta.AddEstacion("Cardio infantil", "103");
    	ruta.AddEstacion("Mazuren", "104");
    	ruta.AddEstacion("Calle 146", "105");
    	ruta.AddEstacion("Calle 142", "106");
    	ruta.AddEstacion("Alcala", "107");
    	ruta.AddEstacion("Prado", "108");
    	ruta.AddEstacion("Calle 127", "109");
    	ruta.AddEstacion("Pepe sierra", "110");
    	ruta.AddEstacion("Calle 106", "111");
    	ruta.AddEstacion("Calle 100", "112");
        ruta.AddEstacion("Virrey", "113");
        ruta.AddEstacion("Calle 85", "114");
        ruta.AddEstacion("Heroes", "115");
        ruta.AddEstacion("El Polo", "314");
        ruta.AddEstacion("Escuela Militar", "313");
    	ruta.AddEstacion("NQS Calle 75", "402");
    	ruta.AddEstacion("Avenida Chile", "403");
    	ruta.AddEstacion("Simon Bolivar", "404");
    	ruta.AddEstacion("Coliseo", "405");
    	ruta.AddEstacion("El Campin", "406");
    	ruta.AddEstacion("U. Nacional", "407");
    	ruta.AddEstacion("Avenida Dorado", "408");
    	ruta.AddEstacion("CAD", "409");
    	ruta.AddEstacion("Paloquemao", "410");
        ruta.AddEstacion("Ricaute", "613");
        ruta.AddEstacion("Comuneros", "612");
        ruta.AddEstacion("Santa isabel", "611");
        ruta.AddEstacion("SENA", "610");
        ruta.AddEstacion("NQS Calle 30 Sur", "609");
        ruta.AddEstacion("NQS Calle 38A Sur", "608");
        ruta.AddEstacion("General Santander", "607");
        ruta.AddEstacion("Alqueria", "606");
        ruta.AddEstacion("Venecia", "605");
        ruta.AddEstacion("Sevillana", "604");
        ruta.AddEstacion("Madelena", "603");
        ruta.AddEstacion("Perdomo", "602");
        ruta.AddEstacion("Portal Sur", "601");

        AddRutaInvertida("B5", ruta);

        ruta= AddRuta("H3", "Todos los dias");
        ruta.AddEstacion("Portal de la 80", "301");
    	ruta.AddEstacion("Quirigua", "302");
    	ruta.AddEstacion("Carrera 90", "303");
    	ruta.AddEstacion("Avenida Cali", "304");
    	ruta.AddEstacion("Granja", "305");
    	ruta.AddEstacion("Carrera 77", "306");
    	ruta.AddEstacion("Minuto de Dios", "307");
    	ruta.AddEstacion("Boyaca", "308");
    	ruta.AddEstacion("Ferias", "309");
    	ruta.AddEstacion("Avenida 68", "310");
    	ruta.AddEstacion("Carrera 53", "311");
    	ruta.AddEstacion("Carrera 47", "312");
    	ruta.AddEstacion("Escuela Militar", "313");
    	ruta.AddEstacion("El Polo", "314");
        ruta.AddEstacion("Calle 76", "1");
    	ruta.AddEstacion("Calle 72","2");
    	ruta.AddEstacion("FLORES", "3");
    	ruta.AddEstacion("Calle 63","4");
    	ruta.AddEstacion("Calle 57","5");
    	ruta.AddEstacion("Marly","6");
    	ruta.AddEstacion("Calle 45", "7");
    	ruta.AddEstacion("Avenida 39", "8");
    	ruta.AddEstacion("Profamilia", "9");
    	ruta.AddEstacion("Calle 26", "10");
    	ruta.AddEstacion("Calle 22", "11");
    	ruta.AddEstacion("Calle 19", "12");
    	ruta.AddEstacion("Avenida Jimenez", "13");
    	ruta.AddEstacion("Tercer Milenio", "14");
        ruta.AddEstacion("Hospital", "716");
        ruta.AddEstacion("Hortua", "715");
        ruta.AddEstacion("Narino", "714");
        ruta.AddEstacion("Fucha", "713");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Olaya", "711");
        ruta.AddEstacion("Quiroga", "710");
        ruta.AddEstacion("Calle 40 Sur", "709");
        ruta.AddEstacion("Santa lucia", "708");
        ruta.AddEstacion("Biblioteca", "707");
        ruta.AddEstacion("Parque", "706");
        ruta.AddEstacion("Portal Tunal", "705");

        AddRutaInvertida("D3", ruta);
        
        ruta= AddRuta("H4", "Todos los dias");
        ruta.AddEstacion("Portal Suba", "201");
    	ruta.AddEstacion("La campina", "202");
    	ruta.AddEstacion("Transversal 91", "203");
    	ruta.AddEstacion("21 Angeles", "204");
    	ruta.AddEstacion("Gratamira", "205");
    	ruta.AddEstacion("Avenida Boyaca", "206");
    	ruta.AddEstacion("Niza Calle 127", "207");
    	ruta.AddEstacion("Humedal Cordoba", "208");
    	ruta.AddEstacion("Shaio", "209");
    	ruta.AddEstacion("Puente largo", "210");
    	ruta.AddEstacion("Suba Calle 100", "211");
    	ruta.AddEstacion("Suba Calle 95", "212");
    	ruta.AddEstacion("Rionegro", "213");
    	ruta.AddEstacion("San Martin", "214");
        ruta.AddEstacion("Escuela Militar", "313");
    	ruta.AddEstacion("El Polo", "314");
        ruta.AddEstacion("Calle 76", "1");
    	ruta.AddEstacion("Calle 72","2");
    	ruta.AddEstacion("FLORES", "3");
    	ruta.AddEstacion("Calle 63","4");
    	ruta.AddEstacion("Calle 57","5");
    	ruta.AddEstacion("Marly","6");
    	ruta.AddEstacion("Calle 45", "7");
    	ruta.AddEstacion("Avenida 39", "8");
    	ruta.AddEstacion("Profamilia", "9");
    	ruta.AddEstacion("Calle 26", "10");
    	ruta.AddEstacion("Calle 22", "11");
    	ruta.AddEstacion("Calle 19", "12");
    	ruta.AddEstacion("Avenida Jimenez", "13");
    	ruta.AddEstacion("Tercer Milenio", "14");
        ruta.AddEstacion("Hospital", "716");
        ruta.AddEstacion("Hortua", "715");
        ruta.AddEstacion("Narino", "714");
        ruta.AddEstacion("Fucha", "713");
        ruta.AddEstacion("Restrepo", "712");
        ruta.AddEstacion("Olaya", "711");
        ruta.AddEstacion("Quiroga", "710");
        ruta.AddEstacion("Calle 40 Sur", "709");
        ruta.AddEstacion("Santa lucia", "708");
        ruta.AddEstacion("Socorro", "704");
        ruta.AddEstacion("Consuelo", "703");
        ruta.AddEstacion("Molinos", "702");
        ruta.AddEstacion("Portal Usme", "701");
                
        AddRutaInvertida("C4", ruta);
    }
}