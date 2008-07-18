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
    
    public Ruta AddRuta(String ruta, String descripcion) {
        Ruta pRuta= new Ruta(ruta,descripcion);
        m_vRutas.addElement(pRuta);
        return pRuta;
    }
    
    public Ruta AddRutaInvertida(String ruta, Ruta original) {
        Ruta nueva= new Ruta(ruta, original.GetDescripcion());
        Vector estaciones= original.GetEstaciones();
        for(int i=estaciones.size()-1; i>=0; i--)
            nueva.AddEstacion(estaciones.elementAt(i).toString());
	  m_vRutas.addElement(nueva);
        return nueva;
    }
    
    public Rutas() {
        m_vRutas= new Vector();
        Ruta ruta=AddRuta("D10","LUNES A VIERNES 5:00 AM-9:30 PM\nSABADO 5:30 AM-9:30 PM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("MAZUREN");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("PEPE SIERRA");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("VIRREY");
        ruta.AddEstacion("CALLE 85");
        ruta.AddEstacion("Carrera 47");
        ruta.AddEstacion("Carrera 53");
        ruta.AddEstacion("Carrera 77");
        ruta.AddEstacion("GRANJA");
        ruta.AddEstacion("AV CALI");
        ruta.AddEstacion("Carrera 90");
        ruta.AddEstacion("PORTAL DE LA 80");

        ruta= AddRutaInvertida("B10", ruta);

        ruta= AddRuta("B55","LUNES A VIERNES 6:00 AM-8:30 AM");
        ruta.AddEstacion("PORTAL DE LA 80");
        ruta.AddEstacion("Carrera 77");
        ruta.AddEstacion("HEROES");
        
        ruta= AddRuta("H51","LUNES A SABADO 6:00 AM-9:00 AM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("PEPE SIERRA");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("CALLE 63");
        ruta.AddEstacion("CALLE 57");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("SANTA LUCIA");
        ruta.AddEstacion("SOCORRO");
        ruta.AddEstacion("CONSUELO");
        ruta.AddEstacion("MOLINOS");
        ruta.AddEstacion("PORTAL USME");

        ruta= AddRuta("H70","LUNES A VIERNES 4:30 AM-9:00 AM");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("PRADO");
        ruta.AddEstacion("CALLE 127");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("CALLE 63");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("HORTUA");
        ruta.AddEstacion("Narino");
        ruta.AddEstacion("RESTREPO");
        ruta.AddEstacion("Calle 40 Sur");
        ruta.AddEstacion("SOCORRO");
        ruta.AddEstacion("CONSUELO");
        ruta.AddEstacion("MOLINOS");
        ruta.AddEstacion("PORTAL USME");

        AddRutaInvertida("B70", ruta);
        
        ruta= AddRuta("H61","LUNES A SABADO 5:30 AM-9:00 AM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("CALLE 127");
        ruta.AddEstacion("PEPE SIERRA");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("CALLE 85");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("Narino");
        ruta.AddEstacion("MOLINOS");
        ruta.AddEstacion("PORTAL USME");

        AddRutaInvertida("B72", ruta);

        ruta= AddRuta("H74","LUNES A VIERNES 9:00 AM - 11:00 PM\nSABADOS 5:00 AM - 11:00 PM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("PRADO");
        ruta.AddEstacion("CALLE 127");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("CALLE 63");
        ruta.AddEstacion("CALLE 57");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("HORTUA");
        ruta.AddEstacion("Narino");
        ruta.AddEstacion("RESTREPO");
        ruta.AddEstacion("Calle 40 Sur");
        ruta.AddEstacion("SANTA LUCIA");
        ruta.AddEstacion("SOCORRO");
        ruta.AddEstacion("CONSUELO");
        ruta.AddEstacion("MOLINOS");
        ruta.AddEstacion("PORTAL USME");

        AddRutaInvertida("B73", ruta);

        ruta= AddRuta("H13","LUNES A VIERNES 5:00 AM-10:00 PM\nSABADOS	6:00 AM - 9:30 PM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("CARDIO INFANTIL");
        ruta.AddEstacion("MAZUREN");
        ruta.AddEstacion("CALLE 142");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("CALLE 85");
        ruta.AddEstacion("FLORES");
        ruta.AddEstacion("CALLE 63");
        ruta.AddEstacion("CALLE 22");
        ruta.AddEstacion("TERCER MILENIO");
        ruta.AddEstacion("HORTUA");
        ruta.AddEstacion("RESTREPO");
        ruta.AddEstacion("OLAYA");
        ruta.AddEstacion("QUIROGA");
        ruta.AddEstacion("Calle 40 Sur");
        ruta.AddEstacion("PORTAL TUNAL");

        AddRutaInvertida("B13", ruta);
        
        ruta= AddRuta("B53","LUNES A VIERNES 5:30 AM-8:30 AM");
        ruta.AddEstacion("PORTAL TUNAL");
        ruta.AddEstacion("SANTA LUCIA");
        ruta.AddEstacion("HORTUA");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("PEPE SIERRA");
        ruta.AddEstacion("CALLE 127");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("PORTAL NORTE");

        ruta= AddRuta("H52","LUNES A VIERNES 6:00 AM-9:00 AM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("PRADO");
        ruta.AddEstacion("CALLE 45");
        ruta.AddEstacion("Avenida 39");
        ruta.AddEstacion("PROFAMILIA");
        ruta.AddEstacion("CALLE 26");
        ruta.AddEstacion("CALLE 19");
        ruta.AddEstacion("RESTREPO");
        ruta.AddEstacion("PORTAL TUNAL");

        ruta= AddRuta("B61","LUNES A VIERNES 4:30 PM-7:30 PM");
        ruta.AddEstacion("PORTAL TUNAL");
        ruta.AddEstacion("RESTREPO");
        ruta.AddEstacion("CALLE 19");
        ruta.AddEstacion("CALLE 26");
        ruta.AddEstacion("PROFAMILIA");
        ruta.AddEstacion("Avenida 39");
        ruta.AddEstacion("CALLE 45");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("CALLE 142");
        ruta.AddEstacion("PORTAL NORTE");

        ruta= AddRuta("H60","LUNES A VIERNES 5:30 AM-8:30 AM\nSABADO 5:30 AM-9:00 AM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("PRADO");
        ruta.AddEstacion("PEPE SIERRA");
        ruta.AddEstacion("VIRREY");
        ruta.AddEstacion("CALLE 85");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("RESTREPO");
        ruta.AddEstacion("OLAYA");
        ruta.AddEstacion("CALLE 4O S");
        ruta.AddEstacion("PORTAL TUNAL");

        AddRutaInvertida("B71", ruta);

        ruta= AddRuta("H73","LUNES A VIERNES 5:30 AM-9:00 AM y 4:30 PM-8:00 PM");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("CALLE 127");
        ruta.AddEstacion("PEPE SIERRA");
        ruta.AddEstacion("CALLE 45");
        ruta.AddEstacion("Avenida 39");
        ruta.AddEstacion("PROFAMILIA");
        ruta.AddEstacion("CALLE 26");
        ruta.AddEstacion("CALLE 19");
        ruta.AddEstacion("FUCHA");
        ruta.AddEstacion("OLAYA");
        ruta.AddEstacion("Calle 40 Sur");
        ruta.AddEstacion("PORTAL TUNAL");

        ruta= AddRuta("C61","LUNES A VIERNES 6:00 AM-8:30 AM y 5:00 PM-7:30 PM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("CARDIO INFANTIL");
        ruta.AddEstacion("CALLE 142");
        ruta.AddEstacion("CALLE 106");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("VIRREY");
        ruta.AddEstacion("CALLE 85");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("RIONEGRO");
        ruta.AddEstacion("SHAIO");
        ruta.AddEstacion("21 ANGELES");
        ruta.AddEstacion("Transversal 91");
        ruta.AddEstacion("La campina");
        ruta.AddEstacion("PORTAL SUBA");

        AddRutaInvertida("B50", ruta);
        
        ruta= AddRuta("J70","LUNES A VIERNES 5:30 AM-9:00 AM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("CALLE 142");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("PRADO");
        ruta.AddEstacion("CALLE 127");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("MUSEO DEL ORO");
        ruta.AddEstacion("LAS AGUAS");

        ruta= AddRuta("J72","LUNES A VIERNES 9:00 AM-10:00 PM\nSABADOS 7:00 AM-8:30 PM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("CALLE 142");
        ruta.AddEstacion("PRADO");
        ruta.AddEstacion("PEPE SIERRA");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("VIRREY");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("CALLE 57");
        ruta.AddEstacion("Avenida 39");
        ruta.AddEstacion("CALLE 19");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("MUSEO DEL ORO");
        ruta.AddEstacion("LAS AGUAS");
        
        AddRutaInvertida("B74", ruta);

        ruta= AddRuta("F14","LUNES A VIERNES 5:00 AM-9:30 PM\nSABADOS 5:30 AM-9:30 PM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("CALLE 127");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("CALLE 85");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("CALLE 45");
        ruta.AddEstacion("PROFAMILIA");
        ruta.AddEstacion("CALLE 26");
        ruta.AddEstacion("CDS Carrera 32");
        ruta.AddEstacion("PRADERA");
        ruta.AddEstacion("BANDERAS");
        ruta.AddEstacion("Transversal 86");
        ruta.AddEstacion("BIBLIOTECA EL TINTAL");
        ruta.AddEstacion("PORTAL AMERICAS");

        AddRutaInvertida("B14", ruta);
        
        ruta= AddRuta("F61","LUNES A VIERNES 5:30 AM-8:30 AM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("VIRREY");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("MARSELLA");
        ruta.AddEstacion("BANDERAS");
        ruta.AddEstacion("BIBLIOTECA EL TINTAL");
        ruta.AddEstacion("PORTAL AMERICAS");

        AddRutaInvertida("B52", ruta);
        
        ruta= AddRuta("G11","LUNES A VIERNES 5:30 AM-9:00 PM\nSABADOS 6:00 AM-9:30 PM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("MAZUREN");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("PEPE SIERRA");
        ruta.AddEstacion("CALLE 106");
        ruta.AddEstacion("VIRREY");
        ruta.AddEstacion("CALLE 85");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("ESCUELA MILITAR");
        ruta.AddEstacion("CAMPIN");
        ruta.AddEstacion("U. Nacional");
        ruta.AddEstacion("CAD");
        ruta.AddEstacion("PALOQUEMAO");
        ruta.AddEstacion("RICAUTE");
        ruta.AddEstacion("SENA");
        ruta.AddEstacion("ALQUERIA");
        ruta.AddEstacion("VENECIA");
        ruta.AddEstacion("PORTAL SUR");

        AddRutaInvertida("B11", ruta);
        
        ruta= AddRuta("G61","LUNES A VIERNES 5:30 AM-8:00 AM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("MAZUREN");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("CALLE 142");
        ruta.AddEstacion("PEPE SIERRA");
        ruta.AddEstacion("CALLE 106");
        ruta.AddEstacion("VIRREY");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("General Santander");
        ruta.AddEstacion("PORTAL DEL SUR");

        AddRutaInvertida("B54", ruta);
        
        ruta= AddRuta("G12","LUNES A SABADO 5:00 AM-9:00 PM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("CALLE 142");
        ruta.AddEstacion("PRADO");
        ruta.AddEstacion("CALLE 127");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("LA CASTELLANA");
        ruta.AddEstacion("NQS CALLE 75");
        ruta.AddEstacion("SIMON BOLIVAR");
        ruta.AddEstacion("U. Nacional");
        ruta.AddEstacion("Avenida Dorado");
        ruta.AddEstacion("RICAUTE");
        ruta.AddEstacion("COMUNEROS");
        ruta.AddEstacion("SANTA ISABELL");
        ruta.AddEstacion("General Santander");
        ruta.AddEstacion("MADELENA");
        ruta.AddEstacion("PERDOMO");
        ruta.AddEstacion("PORTAL DEL SUR");

        AddRutaInvertida("B12", ruta);

        ruta= AddRuta("H15","LUNES A VIERNES 5:30 AM-10:00 PM\nSABADO 6:00 AM-10:00 PM");
        ruta.AddEstacion("PORATAL SUBA");
        ruta.AddEstacion("NIZA CALLE 127");
        ruta.AddEstacion("HUMEDAL CORDOBA");
        ruta.AddEstacion("PUENTE LARGO");
        ruta.AddEstacion("ESCUELA MILITAR");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("CALLE 45");
        ruta.AddEstacion("Avenida 39");
        ruta.AddEstacion("CALLE 22");
        ruta.AddEstacion("CALLE 19");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("TERCER MILENIO");
        ruta.AddEstacion("Narino");
        ruta.AddEstacion("OLAYA");
        ruta.AddEstacion("Calle 40 Sur");
        ruta.AddEstacion("BIBLIOTECA");
        ruta.AddEstacion("PORTAL TUNAL");

        AddRutaInvertida("C15", ruta);
        
        ruta= AddRuta("H17","LUNES A SABADO 5:00 AM-9:00 AM");
        ruta.AddEstacion("PORTAL SUBA");
        ruta.AddEstacion("La campina");
        ruta.AddEstacion("Avenida Boyaca");
        ruta.AddEstacion("SHAIO");
        ruta.AddEstacion("PUENTE LARGO");
        ruta.AddEstacion("SAN MARTIN");
        ruta.AddEstacion("EL POLO");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("FLORES");
        ruta.AddEstacion("CALLE 57");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("HOSPITAL");
        ruta.AddEstacion("HORTUA");
        ruta.AddEstacion("RESTREPO");
        ruta.AddEstacion("Calle 40 Sur");
        ruta.AddEstacion("SANTA LUCIA");
        ruta.AddEstacion("SOCORRO");
        ruta.AddEstacion("CONSUELO");
        ruta.AddEstacion("MOLINOS");
        ruta.AddEstacion("PORTAL USME");

        AddRutaInvertida("C17", ruta);
        
        ruta= AddRuta("F19","LUNES A VIERNES 5:30 AM-10:00PM\nSABADOS 6:00 AM-10:00 PM");
        ruta.AddEstacion("PORTAL SUBA");
        ruta.AddEstacion("La campina");
        ruta.AddEstacion("Transversal 91");
        ruta.AddEstacion("GRATAMIRA");
        ruta.AddEstacion("PUENTE LARGO");
        ruta.AddEstacion("SUBA CALLE 100");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("CALLE 63");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("CALLE 45");
        ruta.AddEstacion("PROFAMILIA");
        ruta.AddEstacion("CALE 26");
        ruta.AddEstacion("CALE 19");
        ruta.AddEstacion("RICAUTE");
        ruta.AddEstacion("Americas Carrera 53A");
        ruta.AddEstacion("MARSELLA");
        ruta.AddEstacion("MANDALAY");
        ruta.AddEstacion("BANDERAS");

        AddRutaInvertida("C19", ruta);
        
        ruta= AddRuta("G16","LUNES A SABADO 5:00 AM-9:00 AM y 4:00 PM-8:00 PM");
        ruta.AddEstacion("PORTAL SUBA");
        ruta.AddEstacion("La campina");
        ruta.AddEstacion("Transversal 91");
        ruta.AddEstacion("Avenida Boyaca");
        ruta.AddEstacion("SHAIO");
        ruta.AddEstacion("PUENTE LARGO");
        ruta.AddEstacion("SUBA CALLE 95");
        ruta.AddEstacion("RIONEGRO");
        ruta.AddEstacion("SAN MARTIN");
        ruta.AddEstacion("NQS CALLE 75");
        ruta.AddEstacion("Avenida Chile");
        ruta.AddEstacion("SIMON BOLIVAR");
        ruta.AddEstacion("COLISEO");
        ruta.AddEstacion("EL CAMPIN");
        ruta.AddEstacion("U. Nacional");
        ruta.AddEstacion("CAD");
        ruta.AddEstacion("RICAUTE");
        ruta.AddEstacion("SANTA ISABEL");
        ruta.AddEstacion("NQS Calle 30 Sur");
        ruta.AddEstacion("NQS Calle 38A Sur");
        ruta.AddEstacion("General Santander");
        ruta.AddEstacion("VENECIA");
        ruta.AddEstacion("PORTAL DEL SUR");

        AddRutaInvertida("C16", ruta);
        
        ruta= AddRuta("G71","LUNES A SABADO 9:30 AM-4:00 PM  y 8:30 PM-11:00 PM");
        ruta.AddEstacion("PORTAL SUBA");
        ruta.AddEstacion("La campina");
        ruta.AddEstacion("Transversal 91");
        ruta.AddEstacion("Avenida Boyaca");
        ruta.AddEstacion("SHAIO");
        ruta.AddEstacion("PUENTE LARGO");
        ruta.AddEstacion("SUBA CALLE 95");
        ruta.AddEstacion("RIONEGRO");
        ruta.AddEstacion("SAN MARTIN");
        ruta.AddEstacion("NQS CALLE 75");
        ruta.AddEstacion("Avenida Chile");
        ruta.AddEstacion("SIMON BOLIVAR");
        ruta.AddEstacion("COLISEO");
        ruta.AddEstacion("EL CAMPIN");
        ruta.AddEstacion("U. Nacional");
        ruta.AddEstacion("CAD");
        ruta.AddEstacion("RICAUTE");
        ruta.AddEstacion("SANTA ISABEL");

        AddRutaInvertida("C71", ruta);

        ruta= AddRuta("H20","LUNES A VIERNES 5:30 AM-10:00 PM\nSABADOS 6:00 AM-8:00 PM");
        ruta.AddEstacion("PORTAL DE LA 80");
        ruta.AddEstacion("Carrera 77");
        ruta.AddEstacion("BOYACA");
        ruta.AddEstacion("FERIAS");
        ruta.AddEstacion("Avenida 68");
        ruta.AddEstacion("EL POLO");
        ruta.AddEstacion("CALLE 45");
        ruta.AddEstacion("PROFAMILIA");
        ruta.AddEstacion("CALLE 26");
        ruta.AddEstacion("CALLE 19");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("FUCHA");
        ruta.AddEstacion("OLAYA");
        ruta.AddEstacion("Calle 40 Sur");
        ruta.AddEstacion("SANTA LUCIA");
        ruta.AddEstacion("MOLINOS");
        ruta.AddEstacion("PORTAL USME");

        AddRutaInvertida("D20", ruta);
        
        ruta= AddRuta("D51","LUNES A VIERNES 5:30 AM-8:30 AM");
        ruta.AddEstacion("PORTAL USME");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("CALLE 19");
        ruta.AddEstacion("CALLE 26");
        ruta.AddEstacion("PROFAMILIA");
        ruta.AddEstacion("Avenida 39");
        ruta.AddEstacion("CALLE 45");
        ruta.AddEstacion("MINUTO DE DIOS");
        ruta.AddEstacion("PORTAL DE LA 80");

        ruta= AddRuta("H50","LUNES A VIERNES 4:30 PM-8:30 PM");
        ruta.AddEstacion("PORTAL DE LA 80");
        ruta.AddEstacion("Carrera 90");
        ruta.AddEstacion("GRANJA");
        ruta.AddEstacion("CR 77");
        ruta.AddEstacion("MINUTO DE DIOS");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("CALLE 57");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("Avenida 39");
        ruta.AddEstacion("HORTUA");
        ruta.AddEstacion("RESTREPO");
        ruta.AddEstacion("PORTAL USME");

        AddRutaInvertida("D60", ruta);
        
        ruta= AddRuta("D50","LUNES A VIERNES 5:30 AM-8:30 AM");
        ruta.AddEstacion("PORTAL AMERICAS");
        ruta.AddEstacion("CALLE 26");
        ruta.AddEstacion("PROFAMILIA");
        ruta.AddEstacion("CALLE 45");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("CALLE 57");
        ruta.AddEstacion("CALLE 63");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("MINUTO DE DIOS");
        ruta.AddEstacion("CR 77");
        ruta.AddEstacion("PORTAL DE LA 80");

        ruta= AddRuta("J24","LUNES A VIERNES 9:00 AM-11:00PM\nSABADO 6:00 AM-9:00 PM");
        ruta.AddEstacion("PORTAL DE LA 80");
        ruta.AddEstacion("CR 77");
        ruta.AddEstacion("Avenida 68");
        ruta.AddEstacion("ESCUELA MILITAR");
        ruta.AddEstacion("EL POLO");
        ruta.AddEstacion("Avenida 39");
        ruta.AddEstacion("CALLE 22");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("MUSEO DEL ORO");
        ruta.AddEstacion("LAS AGUAS");

        AddRutaInvertida("D70", ruta);

        ruta= AddRuta("H21","LUNES A VIERNES 5:00 AM-11:00 PM\nSABADOS 6:00 AM-11:00 PM");
        ruta.AddEstacion("PORTAL DE LA 80");
        ruta.AddEstacion("QUIRIGUA");
        ruta.AddEstacion("Avenida Cali");
        ruta.AddEstacion("CR 77");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("FLORES");
        ruta.AddEstacion("CALLE 63");
        ruta.AddEstacion("CALLE 57");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("Narino");
        ruta.AddEstacion("FUCHA");
        ruta.AddEstacion("OLAYA");
        ruta.AddEstacion("SANTA LUCIA");
        ruta.AddEstacion("PARQUE");
        ruta.AddEstacion("PORTAL TUNAL");
        
        AddRutaInvertida("D21", ruta);
        
        ruta= AddRuta("G22","LUNES A VIERNES 5:30 AM-9:00 AM y 4:00 PM-7:30 PM\nSABADOS	6:00 AM-9:00 AM	y 4:00 PM-7:30 PM");
        ruta.AddEstacion("PORTAL DE LA 80");
        ruta.AddEstacion("CR 77");
        ruta.AddEstacion("MINUTO DE DIOS");
        ruta.AddEstacion("Avenida 68");
        ruta.AddEstacion("Carrera 47");
        ruta.AddEstacion("NQS CALLE 75");
        ruta.AddEstacion("Avenida Chile");
        ruta.AddEstacion("U. Nacional");
        ruta.AddEstacion("Avenida Dorado");
        ruta.AddEstacion("RICAUTE");
        ruta.AddEstacion("SENA");
        ruta.AddEstacion("General Santander");
        ruta.AddEstacion("SEVILLANA");
        ruta.AddEstacion("PORTAL SUR");

        AddRutaInvertida("D22", ruta);
        
        ruta= AddRuta("E26","LUNES A SABADO 9:30 AM-4:00 PM y 8:00 PM-10:00 PM");
        ruta.AddEstacion("PORTAL DE LA 80");
        ruta.AddEstacion("CR 77");
        ruta.AddEstacion("MINUTO DE DIOS");
        ruta.AddEstacion("Avenida 68");
        ruta.AddEstacion("Carrera 47");
        ruta.AddEstacion("NQS CALLE 75");
        ruta.AddEstacion("Avenida Chile");
        ruta.AddEstacion("U. Nacional");
        ruta.AddEstacion("Avenida Dorado");

        AddRutaInvertida("D26", ruta);
        
        ruta= AddRuta("J23","LUNES A SABADO 5:00 AM-11:00 PM");
        ruta.AddEstacion("PORTAL AMERICAS");
        ruta.AddEstacion("PATIO BONITO");
        ruta.AddEstacion("BANDERAS");
        ruta.AddEstacion("MUNDO AVENTURA");
        ruta.AddEstacion("ZONA INDUSTRIAL");
        ruta.AddEstacion("RICAUTE");
        ruta.AddEstacion("San Facon Carrera 22");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("MUSEO DEL ORO");
        ruta.AddEstacion("LAS AGUAS");
        
        AddRutaInvertida("F23", ruta);
        
        ruta= AddRuta("F70","LUNES A VIERNES 5:00 PM-8:30 PM");
        ruta.AddEstacion("PORTAL AMERICAS");
        ruta.AddEstacion("BILBIOTECA EL TINTAL");
        ruta.AddEstacion("BANDERAS");
        ruta.AddEstacion("PRADERA");
        ruta.AddEstacion("PUENTE ARANDA");
        ruta.AddEstacion("Carrera 43");
        ruta.AddEstacion("RICAUTE");
        ruta.AddEstacion("DE LA SABANA");
        ruta.AddEstacion("Avenida Jimenez");

        AddRutaInvertida("F60", ruta);

        ruta= AddRuta("D94","DOMINGOS Y FESTIVOS 7:00AM-8:00 PM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("Carrera 53");
        ruta.AddEstacion("Avenida 68");
        ruta.AddEstacion("FERIAS");
        ruta.AddEstacion("MINUTO DE DIOS");
        ruta.AddEstacion("CR 77");
        ruta.AddEstacion("PORTAL DE LA 80");

        AddRutaInvertida("B94", ruta);
        
        ruta= AddRuta("H92","DOMINGOS Y FESTIVOS 6:00 AM-10:00 PM");
        ruta.AddEstacion("PORTAL DEL NORTE");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("CALLE 142");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("CALLE 127");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("CALLE 85");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("CALLE 63");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("CALLE 45");
        ruta.AddEstacion("CALLE 19");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("RESTREPO");
        ruta.AddEstacion("Calle 40 Sur");
        ruta.AddEstacion("SANTA LUCIA");
        ruta.AddEstacion("SOCORRO");
        ruta.AddEstacion("CONSUELO");
        ruta.AddEstacion("MOLINOS");
        ruta.AddEstacion("PORTAL USME");

        AddRutaInvertida("B92", ruta);
        
        ruta= AddRuta("H93","DOMINGOS Y FESTIVOS 6:00 AM-8:00 PM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("TOBERIN");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("PEPE SIERRA");
        ruta.AddEstacion("VIRREY");
        ruta.AddEstacion("CALLE 85");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("FLORES");
        ruta.AddEstacion("CALLE 63");
        ruta.AddEstacion("CALLE 22");
        ruta.AddEstacion("CALLE 19");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("TERCER MILENIO");
        ruta.AddEstacion("Narino");
        ruta.AddEstacion("FUCHA");
        ruta.AddEstacion("RESTREPO");
        ruta.AddEstacion("OLAYA");
        ruta.AddEstacion("Calle 40 Sur");
        ruta.AddEstacion("SANTA LUCIA");
        ruta.AddEstacion("PORTAL TUNAL");

        AddRutaInvertida("B93", ruta);
        
        ruta= AddRuta("G90","DOMINGOS Y FESTIVOS 6:00 AM y 10:00 PM");
        ruta.AddEstacion("PORTAL NORTE");
        ruta.AddEstacion("CALLE 146");
        ruta.AddEstacion("ALCALA");
        ruta.AddEstacion("PRADO");
        ruta.AddEstacion("CALLE 100");
        ruta.AddEstacion("HEROES");
        ruta.AddEstacion("ESCUELA MILITAR");
        ruta.AddEstacion("NQS CALLE 75");
        ruta.AddEstacion("EL CAMPIN");
        ruta.AddEstacion("AV EL DORADO");
        ruta.AddEstacion("CAD");
        ruta.AddEstacion("RICAUTE");
        ruta.AddEstacion("SANTA ISABEL");
        ruta.AddEstacion("SENA");
        ruta.AddEstacion("General Santander");
        ruta.AddEstacion("ALQUERIA");
        ruta.AddEstacion("VENECIA");
        ruta.AddEstacion("PORTAL SUR");

        AddRutaInvertida("B90", ruta);
        
        ruta= AddRuta("F91","DOMINGOS Y FESTIVOS 6:00 AM-10:00PM");
        ruta.AddEstacion("PORTAL SUBA");
        ruta.AddEstacion("La campina");
        ruta.AddEstacion("Transversal 91");
        ruta.AddEstacion("SHAIO");
        ruta.AddEstacion("PUENTE LARGO");
        ruta.AddEstacion("SUBA CALLE 95");
        ruta.AddEstacion("ESCUELA MILITAR");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("FLORES");
        ruta.AddEstacion("CALLE 63");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("CALLE 45");
        ruta.AddEstacion("CALLE 19");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("SAN FACON");
        ruta.AddEstacion("RICAUTE");
        ruta.AddEstacion("ZONA INDUSTRIAL");
        ruta.AddEstacion("PRADERA");
        ruta.AddEstacion("BANDERAS");
        ruta.AddEstacion("BIBLIOTECA TINTAL");
        ruta.AddEstacion("PORTAL AMERICAS");

        AddRutaInvertida("C91", ruta);
        
        ruta= AddRuta("J95","DOMINGOS Y FESTIVOS 6:00 AM-10:00PM");
        ruta.AddEstacion("PORTAL DE LA 80");
        ruta.AddEstacion("QUIROGA");
        ruta.AddEstacion("AV CALI");
        ruta.AddEstacion("CR 77");
        ruta.AddEstacion("ESCUELA MILITAR");
        ruta.AddEstacion("CALLE 76");
        ruta.AddEstacion("CALLE 72");
        ruta.AddEstacion("CALLE 63");
        ruta.AddEstacion("CALLE 57");
        ruta.AddEstacion("MARLY");
        ruta.AddEstacion("CALLE 19");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("MUSEO DEL ORO");
        ruta.AddEstacion("LAS AGUAS");

        AddRutaInvertida("D95", ruta);
        
        ruta= AddRuta("F1", "Todos los dias");
        
        ruta.AddEstacion("Portal Norte");
    	ruta.AddEstacion("Toberin");
    	ruta.AddEstacion("Cardio infantil");
    	ruta.AddEstacion("Mazuren");
    	ruta.AddEstacion("Calle 146");
    	ruta.AddEstacion("Calle 142");
    	ruta.AddEstacion("Alcala");
    	ruta.AddEstacion("Prado");
    	ruta.AddEstacion("Calle 127");
    	ruta.AddEstacion("Pepe sierra");
    	ruta.AddEstacion("Calle 106");
    	ruta.AddEstacion("Calle 100");
    	ruta.AddEstacion("Virrey");
    	ruta.AddEstacion("Calle 85");
    	ruta.AddEstacion("Heroes");
        ruta.AddEstacion("Calle 76");
    	ruta.AddEstacion("Calle 72");
    	ruta.AddEstacion("Flores");
    	ruta.AddEstacion("Calle 63");
    	ruta.AddEstacion("Calle 57");
    	ruta.AddEstacion("Marly");
    	ruta.AddEstacion("Calle 45");
    	ruta.AddEstacion("Avenida 39");
    	ruta.AddEstacion("Profamilia");
    	ruta.AddEstacion("Calle 26");
    	ruta.AddEstacion("Calle 22");
    	ruta.AddEstacion("Calle 19");
        ruta.AddEstacion("Avenida Jimenez");
        ruta.AddEstacion("De la Sabana");
        ruta.AddEstacion("San Facon Carrera 22");
        ruta.AddEstacion("Ricaute");
        ruta.AddEstacion("CDS Carrera 32");
        ruta.AddEstacion("Zona Industrial");
        ruta.AddEstacion("Carrera 43");
        ruta.AddEstacion("Puente Aranda");
        ruta.AddEstacion("Americas Carrera 53A");
        ruta.AddEstacion("Pradera");
        ruta.AddEstacion("Marsella");
        ruta.AddEstacion("Mundo Aventura");
        ruta.AddEstacion("Mandalay");
        ruta.AddEstacion("Banderas");
        ruta.AddEstacion("Transversal 86");
        ruta.AddEstacion("Biblioteca el tintal");
        ruta.AddEstacion("Patio Bonito");
        ruta.AddEstacion("Portal Americas");

        AddRutaInvertida("B1", ruta);
        
        ruta= AddRuta("G5", "Todos los dias");
        ruta.AddEstacion("Portal Norte");
    	ruta.AddEstacion("Toberin");
    	ruta.AddEstacion("Cardio infantil");
    	ruta.AddEstacion("Mazuren");
    	ruta.AddEstacion("Calle 146");
    	ruta.AddEstacion("Calle 142");
    	ruta.AddEstacion("Alcala");
    	ruta.AddEstacion("Prado");
    	ruta.AddEstacion("Calle 127");
    	ruta.AddEstacion("Pepe sierra");
    	ruta.AddEstacion("Calle 106");
    	ruta.AddEstacion("Calle 100");
        ruta.AddEstacion("La Castellana");
    	ruta.AddEstacion("NQS Calle 75");
    	ruta.AddEstacion("Avenida Chile");
    	ruta.AddEstacion("Simon Bolivar");
    	ruta.AddEstacion("Coliseo");
    	ruta.AddEstacion("El Campin");
    	ruta.AddEstacion("U. Nacional");
    	ruta.AddEstacion("Avenida Dorado");
    	ruta.AddEstacion("CAD");
    	ruta.AddEstacion("Paloquemao");
        ruta.AddEstacion("Ricaute");
        ruta.AddEstacion("Comuneros");
        ruta.AddEstacion("Santa isabel");
        ruta.AddEstacion("SENA");
        ruta.AddEstacion("NQS Calle 30 Sur");
        ruta.AddEstacion("NQS Calle 38A Sur");
        ruta.AddEstacion("General Santander");
        ruta.AddEstacion("Alqueria");
        ruta.AddEstacion("Venecia");
        ruta.AddEstacion("Sevillana");
        ruta.AddEstacion("Madelena");
        ruta.AddEstacion("Perdomo");
        ruta.AddEstacion("Portal Sur");

        AddRutaInvertida("B5", ruta);

        ruta= AddRuta("H3", "Todos los dias");
        ruta.AddEstacion("Portal de la 80");
    	ruta.AddEstacion("Quirigua");
    	ruta.AddEstacion("Carrera 90");
    	ruta.AddEstacion("Avenida Cali");
    	ruta.AddEstacion("Granja");
    	ruta.AddEstacion("Carrera 77");
    	ruta.AddEstacion("Minuto de Dios");
    	ruta.AddEstacion("Boyaca");
    	ruta.AddEstacion("Ferias");
    	ruta.AddEstacion("Avenida 68");
    	ruta.AddEstacion("Carrera 53");
    	ruta.AddEstacion("Carrera 47");
    	ruta.AddEstacion("Escuela Militar");
    	ruta.AddEstacion("El Polo");
        ruta.AddEstacion("Calle 76");
    	ruta.AddEstacion("Calle 72");
    	ruta.AddEstacion("Flores");
    	ruta.AddEstacion("Calle 63");
    	ruta.AddEstacion("Calle 57");
    	ruta.AddEstacion("Marly");
    	ruta.AddEstacion("Calle 45");
    	ruta.AddEstacion("Avenida 39");
    	ruta.AddEstacion("Profamilia");
    	ruta.AddEstacion("Calle 26");
    	ruta.AddEstacion("Calle 22");
    	ruta.AddEstacion("Calle 19");
    	ruta.AddEstacion("Avenida Jimenez");
    	ruta.AddEstacion("Tercer Milenio");
        ruta.AddEstacion("Hospital");
        ruta.AddEstacion("Hortua");
        ruta.AddEstacion("Narino");
        ruta.AddEstacion("Fucha");
        ruta.AddEstacion("Restrepo");
        ruta.AddEstacion("Olaya");
        ruta.AddEstacion("Quiroga");
        ruta.AddEstacion("Calle 40 Sur");
        ruta.AddEstacion("Santa lucia");
        ruta.AddEstacion("Biblioteca");
        ruta.AddEstacion("Parque");
        ruta.AddEstacion("Portal Tunal");

        AddRutaInvertida("D3", ruta);
        
        ruta= AddRuta("A4", "Todos los dias");
        ruta.AddEstacion("Portal Suba");
    	ruta.AddEstacion("La campina");
    	ruta.AddEstacion("Transversal 91");
    	ruta.AddEstacion("21 Angeles");
    	ruta.AddEstacion("Gratamira");
    	ruta.AddEstacion("Avenida Boyaca");
    	ruta.AddEstacion("Niza Calle 127");
    	ruta.AddEstacion("Humedal Cordoba");
    	ruta.AddEstacion("Shaio");
    	ruta.AddEstacion("Puente largo");
    	ruta.AddEstacion("Suba Calle 100");
    	ruta.AddEstacion("Suba Calle 95");
    	ruta.AddEstacion("Rionegro");
    	ruta.AddEstacion("San Martin");
        ruta.AddEstacion("Escuela Militar");
    	ruta.AddEstacion("El Polo");
        ruta.AddEstacion("Calle 76");
    	ruta.AddEstacion("Calle 72");
    	ruta.AddEstacion("Flores");
    	ruta.AddEstacion("Calle 63");
    	ruta.AddEstacion("Calle 57");
    	ruta.AddEstacion("Marly");
    	ruta.AddEstacion("Calle 45");
    	ruta.AddEstacion("Avenida 39");
    	ruta.AddEstacion("Profamilia");
    	ruta.AddEstacion("Calle 26");
    	ruta.AddEstacion("Calle 22");
    	ruta.AddEstacion("Calle 19");
    	ruta.AddEstacion("Avenida Jimenez");
    	ruta.AddEstacion("Tercer Milenio");
        
        AddRutaInvertida("C4", ruta);
    }
}