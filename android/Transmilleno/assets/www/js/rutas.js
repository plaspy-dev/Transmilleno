// Rutas
// clase horarios
function Horarios(desde, hasta) {
	this.m_desde= desde;
    this.m_hasta= hasta;
}

// clase ruta
function Ruta(nombre,descripcion, transbordo) {
	
	this.m_szNombre = nombre;
	this.m_vEstaciones= new Array();
	this.m_szDescripcion= descripcion;
	this.m_nParadas=0;
	this.m_vnIDEstaciones= new Array();
	this.m_LV= this.m_S= this.m_F= null;
	this.m_nOrigen= this.m_nDestino=-1;
	this.m_bTransbordo=transbordo;
	
	this.SetHorarioLV= function(desde, hasta) {
		this.m_LV= new Horarios(desde, hasta);
	}
    this.SetHorarioS= function(desde, hasta) {
		this.m_S= new Horarios(desde, hasta);
	}
	this.SetHorarioF= function(desde, hasta) {
		this.m_F= new Horarios(desde, hasta);
	}
	this.AddEstacion= function(estacion, id) {
		this.m_vEstaciones.push(estacion);
		this.m_vnIDEstaciones.push(id);
	}
	
	this.Calcular= function(ubicacion, destino) {
		var i=0;
		this.m_nOrigen= this.m_nDestino=-1;
		this.m_nParadas=0;
		for(i=0; i<this.m_vEstaciones.length; i++) {
			if(ubicacion==this.m_vEstaciones[i].toLowerCase())
				break;
		}
		for(;i<this.m_vEstaciones.length; i++,this.m_nParadas++) {
			if(destino==this.m_vEstaciones[i].toLowerCase()) {
				return true;
			}
		}
		return false;
	}
	this.CalcularID= function(ubicacion, destino) {
		var i=0;
		this.m_nParadas=0;
		this.m_nOrigen= this.m_nDestino=-1;
		
		for(i=0; i<this.m_vEstaciones.length; i++) {
			if(ubicacion==this.m_vnIDEstaciones[i]) {
				if(this.m_bTransbordo)
					this.m_nOrigen= i; // pasa por el origen
				break;
			}
		}
		for(;i<this.m_vEstaciones.length; i++,this.m_nParadas++) {
			if(destino==this.m_vnIDEstaciones[i]) {
				if(this.m_bTransbordo)
					this.m_nOrigen=this.m_nDestino=-1; // pasa por ambos
				return true;
			}
		}
		for(i=0; i<this.m_vEstaciones.length; i++) {
			if(destino==this.m_vnIDEstaciones[i]) {
				if(this.m_bTransbordo)
					this.m_nDestino= i; // llega al destino
				break;
			}
		}
		return false;
	}
	this.GetInterseccion= function(destino) {
		
		var resultado="";
		this.m_nParadas= 0;
		for(var i=this.m_nOrigen+1; i<this.m_vEstaciones.length; i++) {
			for( var j=0; j<destino.m_nDestino; j++) {
				if(this.m_vnIDEstaciones[i]==destino.m_vnIDEstaciones[j]) {
					resultado= this.m_vEstaciones[i];
					this.m_nParadas=i-this.m_nOrigen;
					this.m_nParadas+=destino.m_nDestino-j;
					break;
				}
			}
			if(this.m_nParadas>0) { // verificamos todo
				for( var j=destino.m_nDestino-1; j>0; j--) {
					if(this.m_vnIDEstaciones[i]==destino.m_vnIDEstaciones[j]) {
						var paradas=i-this.m_nOrigen;
						paradas+=destino.m_nDestino-j;
						if(paradas<this.m_nParadas) {
							resultado= this.m_vEstaciones[i];
							this.m_nParadas=paradas;
						}
						break;
					}
				}
			}
		}
		return resultado;
	}
}

// clase rutas
function Rutas() {
	
	this.m_vRutas= new Array();
	
	this.GetRutaI= function(szRuta) {
		for(i=0; i<this.m_vRutas.length; i++) {
            ruta= this.m_vRutas[i];
            if(ruta.m_szNombre==szRuta) {
                return ruta;
            }
        }
        return null;
	}
	this.GetRuta= function(ubicacion, destino) {
	
		if(destino==null)
			return this.GetRutaI(ubicacion);

        var resultado= new Array();
        ubicacion= ubicacion.toLowerCase();
        destino= destino.toLowerCase();
        for(var i=0; i<this.m_vRutas.length; i++) {
            var ruta= this.m_vRutas[i];
            if(ruta.Calcular(ubicacion, destino))
                resultado.push(ruta);
        }
        // ordenamos el vector resultante
        for(var i=resultado.length-1; i>0; i--) {
            for(var j=0;j<i; j++) {
                var ruta=resultado[j+1];
                var ruta2= resultado[j];
                if(ruta2.m_nParadas>ruta.m_nParadas) {
                    var temp= ruta2;
                    resultado[j]=ruta;
                    resultado[j+1]= temp;
                }
            }
        }
        return resultado;
    }
	
	this.GetTransbordos= function() {
        
        var resultado= new Array();
        for(var i=0; i<this.m_vRutas.length; i++) {
            var ruta= this.m_vRutas[i];
            if(ruta.m_nOrigen>-1) { // pasa por el origen
                for(var j=0;j<this.m_vRutas.length; j++) {
                    if(i==j)
                        continue;
                    var rdestino= this.m_vRutas[j];
                    if(rdestino.m_nDestino>-1) { // pasa por el destino 
                        var estacion= ruta.GetInterseccion(rdestino);
                        if(estacion.length!=0) {
                            rdestino= new Ruta(ruta.m_szNombre + "-<strong>" + estacion + "</strong>-" + rdestino.m_szNombre, "<strong>" + ruta.m_szNombre + "</strong>: " + ruta.m_szDescripcion + "<br/><strong>" +rdestino.m_szNombre + "</strong>: " + rdestino.m_szDescripcion, false);
                            rdestino.m_nParadas= ruta.m_nParadas;
                            resultado.push(rdestino);
                        }
                    }
                }
            }
        }
        // ordenamos el vector resultante
        for(var i=resultado.length-1; i>0; i--) {
            for(var j=0;j<i; j++) {
                var ruta= resultado[j+1];
                var ruta2= resultado[j];
                if(ruta2.m_nParadas>ruta.m_nParadas) {
                    var temp= ruta2;
                    resultado[j]=ruta;
                    resultado[j+1]=temp;
                }
            }
        }
        return resultado;
    }
	
	this.GetRutaID=function(ubicacion, destino) {

        var resultado= new Array();
		
        for(var i=0; i<this.m_vRutas.length; i++) {
            var ruta= this.m_vRutas[i];
            if(ruta.CalcularID(ubicacion, destino))
                resultado.push(ruta);
        }
        // ordenamos el vector resultante
        for(var i=resultado.length-1; i>0; i--) {
            for(var j=0;j<i; j++) {
                var ruta= resultado[j+1];
                var ruta2= resultado[j];
                if(ruta2.m_nParadas>ruta.m_nParadas) {
                    var temp= ruta2;
                    resultado[j]=ruta;
                    resultado[j+1]=temp;
                }
            }
        }
        return resultado;
    }
	this.AddRuta= function(ruta, descripcion, transbordo) {
        var pRuta= new Ruta(ruta,descripcion,transbordo);
        this.m_vRutas.push(pRuta);
        return pRuta;
    }
	
	this.AddRutaInvertida= function(ruta, original, descripcion) {
		
		if(descripcion==null)
			descripcion=original.m_szDescripcion;
		var nueva= new Ruta(ruta, descripcion, original.m_bTransbordo);
        var estaciones= original.m_vEstaciones;
        var ids= original.m_vnIDEstaciones;
        for(var i=estaciones.length-1; i>=0; i--) 
            nueva.AddEstacion(estaciones[i], ids[i]);
        if(original.m_LV!=null)
            nueva.SetHorarioLV(original.m_LV.m_desde, original.m_LV.m_hasta);
        if(original.m_S!=null)
            nueva.SetHorarioS(original.m_S.m_desde, original.m_S.m_hasta);
        if(original.m_F!=null)
            nueva.SetHorarioF(original.m_F.m_desde, original.m_F.m_hasta);
		this.m_vRutas.push(nueva);
        return nueva;
    }
		
	var ruta=this.AddRuta("D10","Lunes a Viernes 5:30am-10:00pm<br/>Sábados 6:00am-10:00pm", true);
	ruta.SetHorarioLV(5, 22);
	ruta.SetHorarioS(5, 22);
	ruta.AddEstacion("Portal Norte", 101);
	ruta.AddEstacion("Toberín", 102);
	ruta.AddEstacion("Mazurén", 104);
	ruta.AddEstacion("Calle 146", 105);
	ruta.AddEstacion("Alcalá", 107);
	ruta.AddEstacion("Pepe sierra", 110);
	ruta.AddEstacion("Calle 106", 111);
	ruta.AddEstacion("Calle 100", 112);
	ruta.AddEstacion("Virrey", 113);
	ruta.AddEstacion("Calle 85", 114);
	ruta.AddEstacion("Carrera 47", 311);
	ruta.AddEstacion("Carrera 53", 310);
	ruta.AddEstacion("Granja - Carrera 77", 305);
	ruta.AddEstacion("Avenida Cali", 304);
	ruta.AddEstacion("Carrera 90", 303);
	ruta.AddEstacion("Portal de la 80", 301);
	
	ruta= this.AddRutaInvertida("B10", ruta, "Lunes a Viernes 5:00am-9:30pm<br/>Sábados 5:30am-9:30pm"); 

        ruta= this.AddRuta("B55","Lunes a Viernes 6:00am-8:30am",false);
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Granja - Carrera 77", 305);
        ruta.AddEstacion("Héroes", 115);
        
        ruta= this.AddRuta("H51","Lunes a Viernes 6:00am-9:00am", false); 
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Santa Lucía", 708);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        ruta= this.AddRuta("H70","Lunes a Viernes 5:00am-9:00am", false);
        ruta.SetHorarioLV(4, 9);
        ruta.AddEstacion("Toberín", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Alcalá", 107);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Nariño", 714);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        this.AddRutaInvertida("B70", ruta, "Lunes a Viernes 4:30am-9:00am"); 
        
        ruta= this.AddRuta("H61","Lunes a Sábados 4:30pm-8:00pm", false);
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Alcalá", 107);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Nariño", 714);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        this.AddRutaInvertida("B72", ruta, "Lunes a Sábados 5:30am-9:00am"); 

        ruta= this.AddRuta("H74","Lunes a Viernes 9:00am-11:00pm<br/>Sábados 5:00am-11:00pm", true);
        ruta.SetHorarioLV(9, 23);
        ruta.SetHorarioS(5, 23);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberín", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Alcalá", 107);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Nariño", 714);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa Lucía", 708);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        this.AddRutaInvertida("B73", ruta, "Lunes a Viernes 9:00am-11:00pm<br/>Sábados 5:00am-11:00pm"); 

        ruta= this.AddRuta("H13","Lunes a Viernes 5:30am-10:00pm<br/>Sábados 6:30am-10:00pm",true); 
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioS(6, 22);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Cardio infantil", 103);
        ruta.AddEstacion("Mazurén", 104);
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

        this.AddRutaInvertida("B13", ruta, "Lunes a Viernes 5:00am-10:00pm<br/>Sábados 6:00am-9:30pm"); 
        
        ruta= this.AddRuta("B53","Lunes a Viernes 5:30am-8:30am", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Portal Tunal", 705);
        ruta.AddEstacion("Santa Lucía", 708);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Alcalá", 107);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Portal Norte", 101);

        ruta= this.AddRuta("H52","Lunes a Viernes 6:00am-9:00am", false); 
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

        ruta= this.AddRuta("B61","Lunes a Viernes 4:30pm-7:30pm", false); 
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Portal Tunal", 705);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Alcalá", 107);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Portal Norte", 101);

        ruta= this.AddRuta("H60","Lunes a Viernes 4:30pm-8:00pm",false); 
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioS(5, 9);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberín", 102);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("CALLE 4O S", 709);
        ruta.AddEstacion("Portal Tunal", 705);

        this.AddRutaInvertida("B71", ruta,"Lunes a Viernes 5:30am-8:30am<br/>Sábados 5:30am-9:00am"); 

        ruta= this.AddRuta("H73","Lunes a Viernes 5:30am-9:00am<br/>Lunes a Viernes 4:30pm-8:00pm", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Toberín", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Alcalá", 107);
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

        ruta= this.AddRuta("C61","Lunes a Viernes 6:30am-8:00am<br/>Lunes a Viernes 4:30pm-8:00pm", false); 
        ruta.SetHorarioLV(6, 9);
        ruta.SetHorarioLV(17, 20);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Cardio infantil", 103);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Rionegro", 213);
        ruta.AddEstacion("Shaio", 209);
        ruta.AddEstacion("21 Angeles", 204);
        ruta.AddEstacion("Transversal 91", 203);
        ruta.AddEstacion("La campina", 202);
        ruta.AddEstacion("Portal Suba", 201);

        this.AddRutaInvertida("B50", ruta,"Lunes a Viernes 6:00am-8:30am<br/>Lunes a Viernes 5:00pm-7:30pm");
        
        ruta= this.AddRuta("J70","Lunes a Viernes 5:30am-9:00am", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberín", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Alcalá", 107);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);

        ruta= this.AddRuta("J72","Lunes a Viernes 9:00am-9:30pm<br/>Sábados 6:30am-8:00pm", true); 
        ruta.SetHorarioLV(9, 22);
        ruta.SetHorarioS(7, 21);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberín", 102);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);
        
        this.AddRutaInvertida("B74", ruta,"Lunes a Viernes 9:00am-10:30pm<br/>Sábados 7:00am-8:30pm"); 

        ruta= this.AddRuta("F14","Lunes a Viernes 5:30am-10:00pm<br/>Sábados 6:00am-10:00pm", true); 
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioLV(5, 22);
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Alcalá", 107);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("CDS Carrera 32", 514);
        ruta.AddEstacion("Pradera", 509);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Transversal 86", 504);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Portal Americas", 501);

        this.AddRutaInvertida("B14", ruta,"Lunes a Viernes 5:00am-9:30pm<br/>Sábados 5:30am-9:30pm"); 
        
        ruta= this.AddRuta("F61","Lunes a Viernes 5:00pm-8:00pm", false); 

        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Alcalá", 107);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Marsella", 508);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Portal Americas", 501);

        this.AddRutaInvertida("B52", ruta,"Lunes a Viernes 5:30am-8:30am"); 
        
        ruta= this.AddRuta("G11","Lunes a Viernes 6:00am-10:00pm<br/>Sábados 6:30am-10:30pm", true); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberín", 102);
        ruta.AddEstacion("Mazurén", 104);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Escuela Militar", 312);
        ruta.AddEstacion("El Campín", 406);
        ruta.AddEstacion("U. Nacional", 407);
        ruta.AddEstacion("CAD", 409);
        ruta.AddEstacion("Paloquemao", 410);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("SENA", 610);
        ruta.AddEstacion("Alqueria", 606);
        ruta.AddEstacion("Venecia", 605);
        ruta.AddEstacion("Portal Sur", 601);

        this.AddRutaInvertida("B11", ruta,"Lunes a Viernes 5:30am-9:00pm<br/>Sábados 6:00am-9:30pm"); 
        
        ruta= this.AddRuta("G61","Lunes a Viernes 5:00pm-8:00pm", false); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Mazurén", 104);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Madelena", 603);
        ruta.AddEstacion("Perdomo", 602);
        ruta.AddEstacion("PORTAL SUR", 601);
        
        ruta= this.AddRuta("B54","Lunes a Viernes 5:30am-8:00am", false);
        ruta.AddEstacion("PORTAL SUR", 601);
        ruta.AddEstacion("Perdomo", 602);
        ruta.AddEstacion("Madelena", 603);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 106", 111);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Mazurén", 104);
                
        ruta= this.AddRuta("G12","Lunes a Sábados 5:00am-11:00pm", true); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberín", 102);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("La Castellana", 401);
        ruta.AddEstacion("NQS Calle 75", 402);
        ruta.AddEstacion("Simón Bolívar", 404);
        ruta.AddEstacion("U. Nacional", 407);
        ruta.AddEstacion("Avenida Dorado", 408);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Comuneros", 612);
        ruta.AddEstacion("SANTA ISABEL", 611);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Madelena", 603);
        ruta.AddEstacion("Perdomo", 602);
        ruta.AddEstacion("PORTAL SUR", 601);

        this.AddRutaInvertida("B12", ruta,"Lunes a Sábados 5:00am-11:00pm"); 

        ruta= this.AddRuta("H15","Lunes a Viernes 5:30am-10:00pm<br/>Sábados 6:00am-10:00pm", true); 
        ruta.AddEstacion("PORTAL SUBA", 201);
        ruta.AddEstacion("Niza Calle 127", 207);
        ruta.AddEstacion("Humedal Cordóba", 208);
        ruta.AddEstacion("Puente largo", 210);
        ruta.AddEstacion("Escuela Militar", 312);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Calle 22", 11);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Tercer Milenio", 14);
        ruta.AddEstacion("Nariño", 714);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Biblioteca", 707);
        ruta.AddEstacion("Portal Tunal", 705);

        this.AddRutaInvertida("C15", ruta,"Lunes a Viernes 5:30am-10:00pm<br/>Sábados 6:00am-10:00pm"); 
        
        ruta= this.AddRuta("H17","Lunes a Sábados 5:00am-9:00am<br/>Lunes a Sábados 4:00pm-8:00pm", false); 
        ruta.AddEstacion("Portal Suba", 201);
        ruta.AddEstacion("La campina", 202);
        ruta.AddEstacion("Avenida Boyacá", 206);
        ruta.AddEstacion("Shaio", 209);
        ruta.AddEstacion("Puente largo", 210);
        ruta.AddEstacion("San Martín", 214);
        ruta.AddEstacion("El Polo", 313);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Flores", 3);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Hospital", 716);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa Lucía", 708);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        this.AddRutaInvertida("C17", ruta,"Lunes a Sábados 5:00am-9:00am<br/>Lunes a Sábados 4:00pm-8:00pm"); 
        
        ruta= this.AddRuta("F19","Lunes a Viernes 5:30am-10:00pm<br/>Sábados 6:00am-10:00pm", true); 
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

        this.AddRutaInvertida("C19", ruta,"Lunes a Viernes 5:30am-10:00pm<br/>Sábados 6:00am-10:00pm");  
        
        ruta= this.AddRuta("G71","Lunes a Sábados 9:00am-4:00pm <br/>Lunes a Viernes 8:00pm-11:00pm", true); 
        ruta.AddEstacion("Portal Suba", 201);
        ruta.AddEstacion("La campina", 202);
        ruta.AddEstacion("Transversal 91", 203);
        ruta.AddEstacion("Avenida Boyacá", 206);
        ruta.AddEstacion("Shaio", 209);
        ruta.AddEstacion("Puente largo", 210);
        ruta.AddEstacion("Suba Calle 95", 212);
        ruta.AddEstacion("Rionegro", 213);
        ruta.AddEstacion("San Martín", 214);
        ruta.AddEstacion("NQS Calle 75", 402);
        ruta.AddEstacion("Avenida Chile", 403);
        ruta.AddEstacion("Simón Bolívar", 404);
        ruta.AddEstacion("Coliseo", 405);
        ruta.AddEstacion("El Campín", 406);
        ruta.AddEstacion("U. Nacional", 407);
        ruta.AddEstacion("CAD", 409);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Santa isabel", 611);

        this.AddRutaInvertida("C71", ruta,"Lunes a Sábados 9:30am-4:00pm <br/>Lunes a Viernes 8:30pm-11:00pm"); 

        ruta= this.AddRuta("H20","Lunes a Viernes 5:30am-10:00pm<br/>Sábados 6:00am-8:00pm", true); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Avenida Cali", 304);
        ruta.AddEstacion("Granja - Carrera 77", 305);
        ruta.AddEstacion("Boyacá", 307);
        ruta.AddEstacion("Ferias", 308);
        ruta.AddEstacion("Avenida 68", 309);
        ruta.AddEstacion("El Polo", 313);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Fucha", 713);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa Lucía", 708);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        this.AddRutaInvertida("D20", ruta,"Lunes a Viernes 5:30am-10:00pm<br/>Sábados 6:00am-8:00pm");  
        
        ruta= this.AddRuta("D51","Lunes a Viernes 5:30am-8:30am", false); 
        ruta.AddEstacion("Portal Usme", 701);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Calle 45", 7);

        ruta.AddEstacion("Minuto de Dios", 306);
        ruta.AddEstacion("Portal de la 80", 301);

        ruta= this.AddRuta("H50","Lunes a Viernes 5:30am-8:30am", false); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Carrera 90", 303);
        ruta.AddEstacion("Granja - Carrera 77", 305);
        ruta.AddEstacion("Minuto de Dios", 306);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Portal Usme", 701);

        this.AddRutaInvertida("D60", ruta,"Lunes a Viernes 4:30pm-8:30pm"); 
        
        ruta= this.AddRuta("D50","Lunes a Viernes 5:30am-8:30am", false); 
        ruta.AddEstacion("Portal Americas", 501);
        ruta.AddEstacion("Calle 26", 10);
        ruta.AddEstacion("Profamilia", 9);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Minuto de Dios", 306);
        ruta.AddEstacion("Granja - Carrera 77", 305);
        ruta.AddEstacion("Portal de la 80", 301);
        
        this.AddRutaInvertida("F62", ruta,"Lunes a Viernes 5:00pm-8:00pm");

        ruta= this.AddRuta("J24","Lunes a Viernes 5:30am-10:00pm<br/>Sábados 5:30am-8:30pm", true); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Granja - Carrera 77", 305);
        ruta.AddEstacion("Avenida 68", 309);
        ruta.AddEstacion("Escuela Militar", 312);
        ruta.AddEstacion("El Polo", 313);
        ruta.AddEstacion("Avenida 39", 8);
        ruta.AddEstacion("Calle 22", 11);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);

        this.AddRutaInvertida("D70", ruta,"Lunes a Viernes 9:00am-11:00pm<br/>S 6:00am-9:00pm"); 

        ruta= this.AddRuta("H21","Lunes a Viernes 5:00am-11:00pm<br/>S 6:00am-11:00pm", true); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Quirigua", 302);
        ruta.AddEstacion("Avenida Cali", 304);
        ruta.AddEstacion("Granja - Carrera 77", 305);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Flores", 3);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Nariño", 714);
        ruta.AddEstacion("Fucha", 713);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa Lucía", 708);
        ruta.AddEstacion("Parque", 706);
        ruta.AddEstacion("Portal Tunal", 705);
        
        this.AddRutaInvertida("D21", ruta,"Lunes a Viernes 5:00am-11:00pm<br/>S 6:00am-11:00pm"); 
        
        ruta= this.AddRuta("G22","Lunes a Sábados 5:00am-9:00am<br/>Lunes a Sábados 4:00pm-7:30pm", false); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Granja - Carrera 77", 305);
        ruta.AddEstacion("Minuto de Dios", 306);
        ruta.AddEstacion("Avenida 68", 309);
        ruta.AddEstacion("Carrera 47", 311);
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

        this.AddRutaInvertida("D22", ruta,"Lunes a Sábados 5:00am-9:00am<br/>Lunes a Sábados 4:00pm-7:30pm"); 
        
        ruta= this.AddRuta("E26","Lunes a Sábados 9:00am-4:00pm<br/>Lunes a Sábados 7:30pm-11:00pm", true); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Granja - Carrera 77", 305);
        ruta.AddEstacion("Minuto de Dios", 306);
        ruta.AddEstacion("Avenida 68", 309);
        ruta.AddEstacion("Carrera 47", 311);
        ruta.AddEstacion("NQS Calle 75", 402);
        ruta.AddEstacion("Avenida Chile", 403);
        ruta.AddEstacion("U. Nacional", 407);
        ruta.AddEstacion("Avenida Dorado", 408);

        this.AddRutaInvertida("D26", ruta,"Lunes a Sábados 9:30am-4:30pm<br/>Lunes a Sábados 8:00pm-11:00pm"); 
        
        ruta= this.AddRuta("J23","Lunes a Sábados 5:00am-11:00pm", true); 
        ruta.AddEstacion("Portal Americas", 501);
        ruta.AddEstacion("Patio Bonito", 502);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Mundo Aventura", 507);
        ruta.AddEstacion("Zona Industrial", 513);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("San Facon Carrera 22", 516);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);
        
        this.AddRutaInvertida("F23", ruta,"Lunes a Sábados 5:00am-11:00pm"); 
        
        ruta= this.AddRuta("F70","Lunes a Viernes 5:30am-9:00am<br/>Lunes a Viernes 4:30pm-8:00pm", false); 
        ruta.AddEstacion("Portal Americas", 501);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Pradera", 509);
        ruta.AddEstacion("Puente Aranda", 511);
        ruta.AddEstacion("Carrera 43", 512);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("De la Sabana", 517);
        ruta.AddEstacion("Avenida Jiménez", 13);

        this.AddRutaInvertida("F60", ruta,"Lunes a Viernes 5:00pm-8:30pm"); 

        ruta= this.AddRuta("D94","Domingos y festivos 7:00am-8:00pm", false); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberín", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Alcalá", 107);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Carrera 53", 310);
        ruta.AddEstacion("Avenida 68", 309);
        ruta.AddEstacion("Ferias", 308);
        ruta.AddEstacion("Minuto de Dios", 306);
        ruta.AddEstacion("Granja - Carrera 77", 305);
        ruta.AddEstacion("Portal de la 80", 301);

        this.AddRutaInvertida("B94", ruta,"Domingos y festivos 7:00am-8:00pm"); 
        
        ruta= this.AddRuta("H92","Domingos y festivos 6:00am-10:00pm", false); 
        ruta.AddEstacion("PORTAL NORTE", 101);
        ruta.AddEstacion("Toberín", 102);
        ruta.AddEstacion("Calle 142", 106);
        ruta.AddEstacion("Alcalá", 107);
        ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa Lucía", 708);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);

        this.AddRutaInvertida("B92", ruta,"Domingos y festivos 6:00am-10:00pm"); 
        
        ruta= this.AddRuta("H93","Domingos y festivos 7:00am-9:00pm", false); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Toberín", 102);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Pepe sierra", 110);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Flores", 3);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 22", 11);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Tercer Milenio", 14);
        ruta.AddEstacion("Nariño", 714);
        ruta.AddEstacion("Fucha", 713);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa Lucía", 708);
        ruta.AddEstacion("Portal Tunal", 705);

        this.AddRutaInvertida("B93", ruta,"Domingos y festivos 6:00am-8:00pm"); 
        
        ruta= this.AddRuta("G90","Domingos y festivos 6:00am-10:00pm", false); 
        ruta.AddEstacion("Portal Norte", 101);
        ruta.AddEstacion("Calle 146", 105);
        ruta.AddEstacion("Alcalá", 107);
        ruta.AddEstacion("Prado", 108);
        ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("La Castellana", 401);
        ruta.AddEstacion("NQS Calle 75", 402);
        ruta.AddEstacion("El Campín", 406);
        ruta.AddEstacion("Avenida Dorado", 408);
        ruta.AddEstacion("CAD", 409);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Santa isabel", 611);
        ruta.AddEstacion("SENA", 610);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Alqueria", 606);
        ruta.AddEstacion("Venecia", 605);
        ruta.AddEstacion("Portal Sur", 601);

        this.AddRutaInvertida("B90", ruta,"Domingos y festivos 6:00am-10:00pm"); 
        
        ruta= this.AddRuta("F91","Domingos y festivos 6:00am-10:00pm", false); 
        ruta.AddEstacion("Portal Suba", 201);
        ruta.AddEstacion("La campina", 202);
        ruta.AddEstacion("Transversal 91", 203);
        ruta.AddEstacion("Shaio", 209);
        ruta.AddEstacion("Puente largo", 210);
        ruta.AddEstacion("Suba Calle 95", 212);
        ruta.AddEstacion("Escuela Militar", 312);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Flores", 3);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 45", 7);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("San Facon Carrera 22", 516);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Zona Industrial", 513);
        ruta.AddEstacion("Pradera", 509);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Portal Americas", 501);

        this.AddRutaInvertida("C91", ruta,"Domingos y festivos 6:00am-10:00pm"); 
        
        ruta= this.AddRuta("J95","Domingos y festivos 6:00am-9:15pm", false); 
        ruta.AddEstacion("Portal de la 80", 301);
        ruta.AddEstacion("Quirigua", 710);
        ruta.AddEstacion("Avenida Cali", 304);
        ruta.AddEstacion("Granja - Carrera 77", 305);
        ruta.AddEstacion("Escuela Militar", 312);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Calle 72",2);
        ruta.AddEstacion("Calle 63",4);
        ruta.AddEstacion("Calle 57",5);
        ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Calle 19", 12);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);        
        
        this.AddRutaInvertida("D95", ruta,"Domingos y festivos 6:00am-10:00pm");  
        
        ruta= this.AddRuta("B56","Lunes a Viernes 6:00am-8:00am", false); 
        ruta.AddEstacion("Portal Suba", 201);
        ruta.AddEstacion("Transversal 91", 203);
        ruta.AddEstacion("Héroes", 115);
        
        ruta= this.AddRuta("A15", "Lunes a Viernes 6:00am-8:00am", false); 
        ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("Niza Calle 127", 207);
    	ruta.AddEstacion("Humedal Cordóba", 208);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Escuela Militar", 312);
        ruta.AddEstacion("Calle 76", 1);
        ruta.AddEstacion("Marly",6);
    	ruta.AddEstacion("Calle 45", 7);
    	ruta.AddEstacion("Avenida 39", 8);
    	ruta.AddEstacion("Calle 22", 11);
    	ruta.AddEstacion("Calle 19", 12);
    	
    	ruta= this.AddRuta("A70", "6:30am-8:30am", false);  
    	ruta.AddEstacion("Toberín", 102);
    	ruta.AddEstacion("Calle 146", 105);
    	ruta.AddEstacion("Calle 142", 106);
    	ruta.AddEstacion("Alcalá", 107);
    	ruta.AddEstacion("Prado", 108);
    	ruta.AddEstacion("Calle 127", 109);
        ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72",2);
 
	ruta= this.AddRuta("H27", "Lunes a Viernes 6:00am-8:30am<br/>Lunes a Viernes 5:00pm-9:30pm", false);
	ruta.AddEstacion("Portal Norte", 101);
    	ruta.AddEstacion("Virrey", 113);
    	ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72",2);
    	ruta.AddEstacion("Flores", 3);
    	ruta.AddEstacion("Calle 63",4);
    	ruta.AddEstacion("Marly",6);
        ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Portal Tunal", 705);
        
        this.AddRutaInvertida("B27", ruta, "Lunes a Viernes 5:30am-8:00am<br/>Lunes a Viernes 4:30pm-9:30pm");
        
        ruta= this.AddRuta("G52", "Lunes a Viernes 5:00pm-8:00pm", false);
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

        this.AddRutaInvertida("A52", ruta, "Lunes a Viernes 5:30am-8:00am");
        
        ruta= this.AddRuta("F28", "Lunes a Viernes 6:30am-8:30am<br/>Lunes a Viernes 4:30pm-8:00pm", false);
        ruta.AddEstacion("Portal Norte", 101);
    	ruta.AddEstacion("Prado", 108);
    	ruta.AddEstacion("Pepe sierra", 110);
    	ruta.AddEstacion("Calle 106", 111);
    	ruta.AddEstacion("NQS Calle 75", 402);
    	ruta.AddEstacion("Avenida Chile", 403);
    	ruta.AddEstacion("Simón Bolívar", 404);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("CAD", 409);
        ruta.AddEstacion("CDS Carrera 32", 514);
        ruta.AddEstacion("Zona Industrial", 513);
        ruta.AddEstacion("Banderas", 505);
        ruta.AddEstacion("Biblioteca el tintal", 503);
        ruta.AddEstacion("Portal Americas", 501);
        
        this.AddRutaInvertida("B28", ruta, "Lunes a Viernes 5:30am-8:30am<br/>Lunes a Viernes 5:00pm-8:00pm");
        
        ruta= this.AddRuta("E25", "Lunes a Viernes 5:30am-8:30am<br/>Lunes a Viernes 5:00pm-7:30pm", false);
        ruta.AddEstacion("Portal Usme", 701);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Tunal", 705);
        ruta.AddEstacion("Sevillana", 604);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("SENA", 610);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Paloquemao", 410);
        ruta.AddEstacion("CAD", 409);
    	ruta.AddEstacion("U. Nacional", 407);
    	ruta.AddEstacion("Simón Bolívar", 404);
    	
        ruta= this.AddRuta("H25", "Lunes a Viernes 6:30am-8:30am<br/>Lunes a Viernes 5:00pm-8:15pm", false);
        ruta.AddEstacion("Simón Bolívar", 404);
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
        
        ruta= this.AddRuta("F29", "Lunes a Viernes 5:30am-8:30am<br/>Lunes a Viernes 5:00pm-8:30pm", false);
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
        ruta.AddEstacion("Portal Americas", 501);
        
        this.AddRutaInvertida("C29", ruta, "Lunes a Viernes 5:30am-8:30am<br/>Lunes a Viernes 5:00pm-8:30pm");
        
        ruta= this.AddRuta("G30", "Lunes a Viernes 5:00am-9:00am<br/>Lunes a Viernes 4:00pm-8:00pm", false);
       	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("La campina", 202);
    	ruta.AddEstacion("Transversal 91", 203);
    	ruta.AddEstacion("Avenida Boyacá", 206);
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
    	
       	this.AddRutaInvertida("C30", ruta, "Lunes a Viernes 5:00am-9:00am<br/>Lunes a Viernes 4:00pm-8:00pm");
       	
       	ruta= this.AddRuta("G31", "Lunes a Viernes 5:00am-9:00am<br/>Lunes a Viernes 4:00pm-8:00pm", false);
       	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("La campina", 202);
    	ruta.AddEstacion("Transversal 91", 203);
       	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Rionegro", 213);
    	ruta.AddEstacion("San Martín", 214);
    	ruta.AddEstacion("Avenida Chile", 403);
    	ruta.AddEstacion("Simón Bolívar", 404);
    	ruta.AddEstacion("El Campín", 406);
    	ruta.AddEstacion("U. Nacional", 407);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("NQS Calle 30 Sur", 609);
        ruta.AddEstacion("General Santander", 607);
        ruta.AddEstacion("Venecia", 605);
        ruta.AddEstacion("Portal Sur", 601);
    	
       	this.AddRutaInvertida("C31", ruta, "Lunes a Viernes 5:00am-9:00am<br/>Lunes a Viernes 4:00pm-8:00pm");
       	
       	ruta= this.AddRuta("J73", "Lunes a Viernes 5:00am-9:00am", false);
       	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("La campina", 202);
    	ruta.AddEstacion("Transversal 91", 203);
    	ruta.AddEstacion("Avenida Boyacá", 206);
    	ruta.AddEstacion("Niza Calle 127", 207);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("San Martín", 214);
    	ruta.AddEstacion("Avenida Jiménez", 13);
        ruta.AddEstacion("Museo del oro", 801);
        ruta.AddEstacion("LAS AGUAS", 802);
       	
       	this.AddRutaInvertida("C73", ruta, "Lunes a Viernes 4:00pm-8:00pm");
       	
       	ruta= this.AddRuta("A50", "Lunes a Viernes 6:30am-8:30am", false);
       	ruta.AddEstacion("Portal de la 80", 301);
    	ruta.AddEstacion("Carrera 90", 303);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Minuto de Dios", 306);
        ruta.AddEstacion("Calle 76", 1);
    	ruta.AddEstacion("Calle 72",2);
    	
    	ruta= this.AddRuta("G96", "Domingos y festivos 7:00am-9:00pm", false);    	
    	ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("Transversal 91", 203);
    	ruta.AddEstacion("Avenida Boyacá", 206);
    	ruta.AddEstacion("Niza Calle 127", 207);
    	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Suba Calle 100", 211);
    	ruta.AddEstacion("Avenida Chile", 403);
    	ruta.AddEstacion("Coliseo", 405);
    	ruta.AddEstacion("El Campín", 406);
    	ruta.AddEstacion("CAD", 409);
        ruta.AddEstacion("Ricaute", 613);
        ruta.AddEstacion("Comuneros", 612);
        ruta.AddEstacion("Santa isabel", 611);
        ruta.AddEstacion("SENA", 610);
        ruta.AddEstacion("NQS Calle 38A Sur", 608);
        ruta.AddEstacion("Venecia", 605);
        ruta.AddEstacion("Perdomo", 602);
        ruta.AddEstacion("Portal Sur", 601);
        
    	this.AddRutaInvertida("C96", ruta, "Domingos y festivos 7:00am-9:00pm");
        
        ruta= this.AddRuta("F1", "Todos los días", true); 
        
        ruta.AddEstacion("Portal Norte", 101);
    	ruta.AddEstacion("Toberín", 102);
    	ruta.AddEstacion("Cardio infantil", 103);
    	ruta.AddEstacion("Mazurén", 104);
    	ruta.AddEstacion("Calle 146", 105);
    	ruta.AddEstacion("Calle 142", 106);
    	ruta.AddEstacion("Alcalá", 107);
    	ruta.AddEstacion("Prado", 108);
    	ruta.AddEstacion("Calle 127", 109);
    	ruta.AddEstacion("Pepe sierra", 110);
    	ruta.AddEstacion("Calle 106", 111);
    	ruta.AddEstacion("Calle 100", 112);
    	ruta.AddEstacion("Virrey", 113);
    	ruta.AddEstacion("Calle 85", 114);
    	ruta.AddEstacion("Héroes", 115);
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
        ruta.AddEstacion("Avenida Jiménez", 13);
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
        ruta.AddEstacion("Portal Americas", 501);

        this.AddRutaInvertida("B1", ruta); 
        
        ruta= this.AddRuta("G5", "Todos los días", true);
        ruta.AddEstacion("Portal Norte", 101);
    	ruta.AddEstacion("Toberín", 102);
    	ruta.AddEstacion("Cardio infantil", 103);
    	ruta.AddEstacion("Mazurén", 104);
    	ruta.AddEstacion("Calle 146", 105);
    	ruta.AddEstacion("Calle 142", 106);
    	ruta.AddEstacion("Alcalá", 107);
    	ruta.AddEstacion("Prado", 108);
    	ruta.AddEstacion("Calle 127", 109);
    	ruta.AddEstacion("Pepe sierra", 110);
    	ruta.AddEstacion("Calle 106", 111);
    	ruta.AddEstacion("Calle 100", 112);
        ruta.AddEstacion("Virrey", 113);
        ruta.AddEstacion("Calle 85", 114);
        ruta.AddEstacion("Héroes", 115);
        ruta.AddEstacion("El Polo", 313);
        ruta.AddEstacion("Escuela Militar", 312);
    	ruta.AddEstacion("NQS Calle 75", 402);
    	ruta.AddEstacion("Avenida Chile", 403);
    	ruta.AddEstacion("Simón Bolívar", 404);
    	ruta.AddEstacion("Coliseo", 405);
    	ruta.AddEstacion("El Campín", 406);
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

        this.AddRutaInvertida("B5", ruta);

        ruta= this.AddRuta("H3", "Todos los días", true);
        ruta.AddEstacion("Portal de la 80", 301);
    	ruta.AddEstacion("Quirigua", 302);
    	ruta.AddEstacion("Carrera 90", 303);
    	ruta.AddEstacion("Avenida Cali", 304);
    	ruta.AddEstacion("Granja - Carrera 77", 305);
    	ruta.AddEstacion("Minuto de Dios", 306);
    	ruta.AddEstacion("Boyacá", 307);
    	ruta.AddEstacion("Ferias", 308);
    	ruta.AddEstacion("Avenida 68", 309);
    	ruta.AddEstacion("Carrera 53", 310);
    	ruta.AddEstacion("Carrera 47", 311);
    	ruta.AddEstacion("Escuela Militar", 312);
    	ruta.AddEstacion("El Polo", 313);
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
    	ruta.AddEstacion("Avenida Jiménez", 13);
    	ruta.AddEstacion("Tercer Milenio", 14);
        ruta.AddEstacion("Hospital", 716);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Nariño", 714);
        ruta.AddEstacion("Fucha", 713);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Quiroga", 710);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa Lucía", 708);
        ruta.AddEstacion("Biblioteca", 707);
        ruta.AddEstacion("Parque", 706);
        ruta.AddEstacion("Portal Tunal", 705);

        this.AddRutaInvertida("D3", ruta);
        
        ruta= this.AddRuta("H4", "Todos los días", true);
        ruta.AddEstacion("Portal Suba", 201);
    	ruta.AddEstacion("La campina", 202);
    	ruta.AddEstacion("Transversal 91", 203);
    	ruta.AddEstacion("21 Angeles", 204);
    	ruta.AddEstacion("Gratamira", 205);
    	ruta.AddEstacion("Avenida Boyacá", 206);
    	ruta.AddEstacion("Niza Calle 127", 207);
    	ruta.AddEstacion("Humedal Cordóba", 208);
    	ruta.AddEstacion("Shaio", 209);
    	ruta.AddEstacion("Puente largo", 210);
    	ruta.AddEstacion("Suba Calle 100", 211);
    	ruta.AddEstacion("Suba Calle 95", 212);
    	ruta.AddEstacion("Rionegro", 213);
    	ruta.AddEstacion("San Martín", 214);
        ruta.AddEstacion("Escuela Militar", 312);
    	ruta.AddEstacion("El Polo", 313);
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
    	ruta.AddEstacion("Avenida Jiménez", 13);
    	ruta.AddEstacion("Tercer Milenio", 14);
        ruta.AddEstacion("Hospital", 716);
        ruta.AddEstacion("Hortua", 715);
        ruta.AddEstacion("Nariño", 714);
        ruta.AddEstacion("Fucha", 713);
        ruta.AddEstacion("Restrepo", 712);
        ruta.AddEstacion("Olaya", 711);
        ruta.AddEstacion("Quiroga", 710);
        ruta.AddEstacion("Calle 40 Sur", 709);
        ruta.AddEstacion("Santa Lucía", 708);
        ruta.AddEstacion("Socorro", 704);
        ruta.AddEstacion("Consuelo", 703);
        ruta.AddEstacion("Molinos", 702);
        ruta.AddEstacion("Portal Usme", 701);
                
        this.AddRutaInvertida("C4", ruta);
}
