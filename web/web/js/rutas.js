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
		
	var ruta=this.AddRuta("Línea Demo 1", "Recorrido ficticio 1", true);
	ruta.SetHorarioLV(5, 22);
	ruta.SetHorarioS(5, 22);
	ruta.AddEstacion("Parada Demo 1", 101);
	ruta.AddEstacion("Parada Demo 2", 102);
	ruta.AddEstacion("Parada Demo 3", 104);
	ruta.AddEstacion("Parada Demo 4", 105);
	ruta.AddEstacion("Parada Demo 5", 107);
	ruta.AddEstacion("Parada Demo 6", 110);
	ruta.AddEstacion("Parada Demo 7", 111);
	ruta.AddEstacion("Parada Demo 8", 112);
	ruta.AddEstacion("Parada Demo 9", 113);
	ruta.AddEstacion("Parada Demo 10", 114);
	ruta.AddEstacion("Parada Demo 11", 311);
	ruta.AddEstacion("Parada Demo 12", 310);
	ruta.AddEstacion("Parada Demo 13", 305);
	ruta.AddEstacion("Parada Demo 14", 304);
	ruta.AddEstacion("Parada Demo 15", 303);
	ruta.AddEstacion("Parada Demo 16", 301);
	
	ruta= this.AddRutaInvertida("Línea Demo 60", ruta, "Trayecto inverso ficticio"); 

        ruta= this.AddRuta("Línea Demo 2", "Recorrido ficticio 2",false);
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Parada Demo 16", 301);
        ruta.AddEstacion("Parada Demo 13", 305);
        ruta.AddEstacion("Parada Demo 17", 115);
        
        ruta= this.AddRuta("Línea Demo 3", "Recorrido ficticio 3", false); 
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 6", 110);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 20",4);
        ruta.AddEstacion("Parada Demo 21",5);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 23", 708);
        ruta.AddEstacion("Parada Demo 24", 704);
        ruta.AddEstacion("Parada Demo 25", 703);
        ruta.AddEstacion("Parada Demo 26", 702);
        ruta.AddEstacion("Parada Demo 27", 701);

        ruta= this.AddRuta("Línea Demo 4", "Recorrido ficticio 4", false);
        ruta.SetHorarioLV(4, 9);
        ruta.AddEstacion("Parada Demo 2", 102);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 28", 106);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 29", 108);
        ruta.AddEstacion("Parada Demo 30", 109);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 20",4);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 32", 715);
        ruta.AddEstacion("Parada Demo 33", 714);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 24", 704);
        ruta.AddEstacion("Parada Demo 25", 703);
        ruta.AddEstacion("Parada Demo 26", 702);
        ruta.AddEstacion("Parada Demo 27", 701);

        this.AddRutaInvertida("Línea Demo 61", ruta, "Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 5", "Recorrido ficticio 5", false);
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 30", 109);
        ruta.AddEstacion("Parada Demo 6", 110);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 10", 114);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 33", 714);
        ruta.AddEstacion("Parada Demo 26", 702);
        ruta.AddEstacion("Parada Demo 27", 701);

        this.AddRutaInvertida("Línea Demo 62", ruta, "Trayecto inverso ficticio"); 

        ruta= this.AddRuta("Línea Demo 6", "Recorrido ficticio 6", true);
        ruta.SetHorarioLV(9, 23);
        ruta.SetHorarioS(5, 23);
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 2", 102);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 29", 108);
        ruta.AddEstacion("Parada Demo 30", 109);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 20",4);
        ruta.AddEstacion("Parada Demo 21",5);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 32", 715);
        ruta.AddEstacion("Parada Demo 33", 714);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 23", 708);
        ruta.AddEstacion("Parada Demo 24", 704);
        ruta.AddEstacion("Parada Demo 25", 703);
        ruta.AddEstacion("Parada Demo 26", 702);
        ruta.AddEstacion("Parada Demo 27", 701);

        this.AddRutaInvertida("Línea Demo 63", ruta, "Trayecto inverso ficticio"); 

        ruta= this.AddRuta("Línea Demo 7", "Recorrido ficticio 7",true); 
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioS(6, 22);
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 36", 103);
        ruta.AddEstacion("Parada Demo 3", 104);
        ruta.AddEstacion("Parada Demo 28", 106);
        ruta.AddEstacion("Parada Demo 7", 111);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 10", 114);
        ruta.AddEstacion("Parada Demo 37", 3);
        ruta.AddEstacion("Parada Demo 20",4);
        ruta.AddEstacion("Parada Demo 38", 11);
        ruta.AddEstacion("Parada Demo 39", 14);
        ruta.AddEstacion("Parada Demo 32", 715);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 40", 711);
        ruta.AddEstacion("Parada Demo 41", 710);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 42", 705);

        this.AddRutaInvertida("Línea Demo 64", ruta, "Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 8", "Recorrido ficticio 8", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Parada Demo 42", 705);
        ruta.AddEstacion("Parada Demo 23", 708);
        ruta.AddEstacion("Parada Demo 32", 715);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 6", 110);
        ruta.AddEstacion("Parada Demo 30", 109);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 1", 101);

        ruta= this.AddRuta("Línea Demo 9", "Recorrido ficticio 9", false); 
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 29", 108);
        ruta.AddEstacion("Parada Demo 43", 7);
        ruta.AddEstacion("Parada Demo 44", 8);
        ruta.AddEstacion("Parada Demo 45", 9);
        ruta.AddEstacion("Parada Demo 46", 10);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 42", 705);

        ruta= this.AddRuta("Línea Demo 10", "Recorrido ficticio 10", false); 
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Parada Demo 42", 705);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 46", 10);
        ruta.AddEstacion("Parada Demo 45", 9);
        ruta.AddEstacion("Parada Demo 44", 8);
        ruta.AddEstacion("Parada Demo 43", 7);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 28", 106);
        ruta.AddEstacion("Parada Demo 1", 101);

        ruta= this.AddRuta("Línea Demo 11", "Recorrido ficticio 11",false); 
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioS(5, 9);
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 2", 102);
        ruta.AddEstacion("Parada Demo 29", 108);
        ruta.AddEstacion("Parada Demo 6", 110);
        ruta.AddEstacion("Parada Demo 9", 113);
        ruta.AddEstacion("Parada Demo 10", 114);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 40", 711);
        ruta.AddEstacion("Parada Demo 48", 709);
        ruta.AddEstacion("Parada Demo 42", 705);

        this.AddRutaInvertida("Línea Demo 65", ruta,"Trayecto inverso ficticio"); 

        ruta= this.AddRuta("Línea Demo 12", "Recorrido ficticio 12", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Parada Demo 2", 102);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 30", 109);
        ruta.AddEstacion("Parada Demo 6", 110);
        ruta.AddEstacion("Parada Demo 43", 7);
        ruta.AddEstacion("Parada Demo 44", 8);
        ruta.AddEstacion("Parada Demo 45", 9);
        ruta.AddEstacion("Parada Demo 46", 10);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 49", 713);
        ruta.AddEstacion("Parada Demo 40", 711);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 42", 705);

        ruta= this.AddRuta("Línea Demo 13", "Recorrido ficticio 13", false); 
        ruta.SetHorarioLV(6, 9);
        ruta.SetHorarioLV(17, 20);
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 36", 103);
        ruta.AddEstacion("Parada Demo 28", 106);
        ruta.AddEstacion("Parada Demo 7", 111);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 9", 113);
        ruta.AddEstacion("Parada Demo 10", 114);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 50", 213);
        ruta.AddEstacion("Parada Demo 51", 209);
        ruta.AddEstacion("Parada Demo 52", 204);
        ruta.AddEstacion("Parada Demo 53", 203);
        ruta.AddEstacion("Parada Demo 54", 202);
        ruta.AddEstacion("Parada Demo 55", 201);

        this.AddRutaInvertida("Línea Demo 66", ruta,"Trayecto inverso ficticio");
        
        ruta= this.AddRuta("Línea Demo 14", "Recorrido ficticio 14", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 2", 102);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 28", 106);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 29", 108);
        ruta.AddEstacion("Parada Demo 30", 109);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 56", 801);
        ruta.AddEstacion("Parada Demo 57", 802);

        ruta= this.AddRuta("Línea Demo 15", "Recorrido ficticio 15", true); 
        ruta.SetHorarioLV(9, 22);
        ruta.SetHorarioS(7, 21);
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 2", 102);
        ruta.AddEstacion("Parada Demo 28", 106);
        ruta.AddEstacion("Parada Demo 29", 108);
        ruta.AddEstacion("Parada Demo 6", 110);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 9", 113);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 21",5);
        ruta.AddEstacion("Parada Demo 44", 8);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 56", 801);
        ruta.AddEstacion("Parada Demo 57", 802);
        
        this.AddRutaInvertida("Línea Demo 67", ruta,"Trayecto inverso ficticio"); 

        ruta= this.AddRuta("Línea Demo 16", "Recorrido ficticio 16", true); 
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioLV(5, 22);
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 30", 109);
        ruta.AddEstacion("Parada Demo 7", 111);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 10", 114);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 43", 7);
        ruta.AddEstacion("Parada Demo 45", 9);
        ruta.AddEstacion("Parada Demo 46", 10);
        ruta.AddEstacion("Parada Demo 58", 514);
        ruta.AddEstacion("Parada Demo 59", 509);
        ruta.AddEstacion("Parada Demo 60", 505);
        ruta.AddEstacion("Parada Demo 61", 504);
        ruta.AddEstacion("Parada Demo 62", 503);
        ruta.AddEstacion("Parada Demo 63", 501);

        this.AddRutaInvertida("Línea Demo 68", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 17", "Recorrido ficticio 17", false); 

        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 9", 113);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 44", 8);
        ruta.AddEstacion("Parada Demo 64", 508);
        ruta.AddEstacion("Parada Demo 60", 505);
        ruta.AddEstacion("Parada Demo 62", 503);
        ruta.AddEstacion("Parada Demo 63", 501);

        this.AddRutaInvertida("Línea Demo 69", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 18", "Recorrido ficticio 18", true); 
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 2", 102);
        ruta.AddEstacion("Parada Demo 3", 104);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 6", 110);
        ruta.AddEstacion("Parada Demo 7", 111);
        ruta.AddEstacion("Parada Demo 9", 113);
        ruta.AddEstacion("Parada Demo 10", 114);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 65", 312);
        ruta.AddEstacion("Parada Demo 66", 406);
        ruta.AddEstacion("Parada Demo 67", 407);
        ruta.AddEstacion("Parada Demo 68", 409);
        ruta.AddEstacion("Parada Demo 69", 410);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 71", 610);
        ruta.AddEstacion("Parada Demo 72", 606);
        ruta.AddEstacion("Parada Demo 73", 605);
        ruta.AddEstacion("Parada Demo 74", 601);

        this.AddRutaInvertida("Línea Demo 70", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 19", "Recorrido ficticio 19", false); 
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 3", 104);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 28", 106);
        ruta.AddEstacion("Parada Demo 6", 110);
        ruta.AddEstacion("Parada Demo 7", 111);
        ruta.AddEstacion("Parada Demo 9", 113);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 75", 607);
        ruta.AddEstacion("Parada Demo 76", 603);
        ruta.AddEstacion("Parada Demo 77", 602);
        ruta.AddEstacion("Parada Demo 78", 601);
        
        ruta= this.AddRuta("Línea Demo 20", "Recorrido ficticio 20", false);
        ruta.AddEstacion("Parada Demo 78", 601);
        ruta.AddEstacion("Parada Demo 77", 602);
        ruta.AddEstacion("Parada Demo 76", 603);
        ruta.AddEstacion("Parada Demo 75", 607);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 9", 113);
        ruta.AddEstacion("Parada Demo 7", 111);
        ruta.AddEstacion("Parada Demo 6", 110);
        ruta.AddEstacion("Parada Demo 28", 106);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 3", 104);
                
        ruta= this.AddRuta("Línea Demo 21", "Recorrido ficticio 21", true); 
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 2", 102);
        ruta.AddEstacion("Parada Demo 28", 106);
        ruta.AddEstacion("Parada Demo 29", 108);
        ruta.AddEstacion("Parada Demo 30", 109);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 79", 401);
        ruta.AddEstacion("Parada Demo 80", 402);
        ruta.AddEstacion("Parada Demo 81", 404);
        ruta.AddEstacion("Parada Demo 67", 407);
        ruta.AddEstacion("Parada Demo 82", 408);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 83", 612);
        ruta.AddEstacion("Parada Demo 84", 611);
        ruta.AddEstacion("Parada Demo 75", 607);
        ruta.AddEstacion("Parada Demo 76", 603);
        ruta.AddEstacion("Parada Demo 77", 602);
        ruta.AddEstacion("Parada Demo 78", 601);

        this.AddRutaInvertida("Línea Demo 71", ruta,"Trayecto inverso ficticio"); 

        ruta= this.AddRuta("Línea Demo 22", "Recorrido ficticio 22", true); 
        ruta.AddEstacion("Parada Demo 85", 201);
        ruta.AddEstacion("Parada Demo 86", 207);
        ruta.AddEstacion("Parada Demo 87", 208);
        ruta.AddEstacion("Parada Demo 88", 210);
        ruta.AddEstacion("Parada Demo 65", 312);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 43", 7);
        ruta.AddEstacion("Parada Demo 44", 8);
        ruta.AddEstacion("Parada Demo 38", 11);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 39", 14);
        ruta.AddEstacion("Parada Demo 33", 714);
        ruta.AddEstacion("Parada Demo 40", 711);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 89", 707);
        ruta.AddEstacion("Parada Demo 42", 705);

        this.AddRutaInvertida("Línea Demo 72", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 23", "Recorrido ficticio 23", false); 
        ruta.AddEstacion("Parada Demo 55", 201);
        ruta.AddEstacion("Parada Demo 54", 202);
        ruta.AddEstacion("Parada Demo 90", 206);
        ruta.AddEstacion("Parada Demo 51", 209);
        ruta.AddEstacion("Parada Demo 88", 210);
        ruta.AddEstacion("Parada Demo 91", 214);
        ruta.AddEstacion("Parada Demo 92", 313);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 37", 3);
        ruta.AddEstacion("Parada Demo 21",5);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 93", 716);
        ruta.AddEstacion("Parada Demo 32", 715);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 23", 708);
        ruta.AddEstacion("Parada Demo 24", 704);
        ruta.AddEstacion("Parada Demo 25", 703);
        ruta.AddEstacion("Parada Demo 26", 702);
        ruta.AddEstacion("Parada Demo 27", 701);

        this.AddRutaInvertida("Línea Demo 73", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 24", "Recorrido ficticio 24", true); 
        ruta.AddEstacion("Parada Demo 55", 201);
        ruta.AddEstacion("Parada Demo 54", 202);
        ruta.AddEstacion("Parada Demo 53", 203);
        ruta.AddEstacion("Parada Demo 94", 205);
        ruta.AddEstacion("Parada Demo 88", 210);
        ruta.AddEstacion("Parada Demo 95", 211);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 20",4);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 43", 7);
        ruta.AddEstacion("Parada Demo 45", 9);
        ruta.AddEstacion("Parada Demo 46", 10);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 96", 510);
        ruta.AddEstacion("Parada Demo 64", 508);
        ruta.AddEstacion("Parada Demo 97", 506);
        ruta.AddEstacion("Parada Demo 60", 505);

        this.AddRutaInvertida("Línea Demo 74", ruta,"Trayecto inverso ficticio");  
        
        ruta= this.AddRuta("Línea Demo 25", "Recorrido ficticio 25", true); 
        ruta.AddEstacion("Parada Demo 55", 201);
        ruta.AddEstacion("Parada Demo 54", 202);
        ruta.AddEstacion("Parada Demo 53", 203);
        ruta.AddEstacion("Parada Demo 90", 206);
        ruta.AddEstacion("Parada Demo 51", 209);
        ruta.AddEstacion("Parada Demo 88", 210);
        ruta.AddEstacion("Parada Demo 98", 212);
        ruta.AddEstacion("Parada Demo 50", 213);
        ruta.AddEstacion("Parada Demo 91", 214);
        ruta.AddEstacion("Parada Demo 80", 402);
        ruta.AddEstacion("Parada Demo 99", 403);
        ruta.AddEstacion("Parada Demo 81", 404);
        ruta.AddEstacion("Parada Demo 100", 405);
        ruta.AddEstacion("Parada Demo 66", 406);
        ruta.AddEstacion("Parada Demo 67", 407);
        ruta.AddEstacion("Parada Demo 68", 409);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 101", 611);

        this.AddRutaInvertida("Línea Demo 75", ruta,"Trayecto inverso ficticio"); 

        ruta= this.AddRuta("Línea Demo 26", "Recorrido ficticio 26", true); 
        ruta.AddEstacion("Parada Demo 16", 301);
        ruta.AddEstacion("Parada Demo 14", 304);
        ruta.AddEstacion("Parada Demo 13", 305);
        ruta.AddEstacion("Parada Demo 102", 307);
        ruta.AddEstacion("Parada Demo 103", 308);
        ruta.AddEstacion("Parada Demo 104", 309);
        ruta.AddEstacion("Parada Demo 92", 313);
        ruta.AddEstacion("Parada Demo 43", 7);
        ruta.AddEstacion("Parada Demo 45", 9);
        ruta.AddEstacion("Parada Demo 46", 10);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 49", 713);
        ruta.AddEstacion("Parada Demo 40", 711);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 23", 708);
        ruta.AddEstacion("Parada Demo 26", 702);
        ruta.AddEstacion("Parada Demo 27", 701);

        this.AddRutaInvertida("Línea Demo 76", ruta,"Trayecto inverso ficticio");  
        
        ruta= this.AddRuta("Línea Demo 27", "Recorrido ficticio 27", false); 
        ruta.AddEstacion("Parada Demo 27", 701);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 46", 10);
        ruta.AddEstacion("Parada Demo 45", 9);
        ruta.AddEstacion("Parada Demo 44", 8);
        ruta.AddEstacion("Parada Demo 43", 7);

        ruta.AddEstacion("Parada Demo 105", 306);
        ruta.AddEstacion("Parada Demo 16", 301);

        ruta= this.AddRuta("Línea Demo 28", "Recorrido ficticio 28", false); 
        ruta.AddEstacion("Parada Demo 16", 301);
        ruta.AddEstacion("Parada Demo 15", 303);
        ruta.AddEstacion("Parada Demo 13", 305);
        ruta.AddEstacion("Parada Demo 105", 306);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 21",5);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 44", 8);
        ruta.AddEstacion("Parada Demo 32", 715);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 27", 701);

        this.AddRutaInvertida("Línea Demo 77", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 29", "Recorrido ficticio 29", false); 
        ruta.AddEstacion("Parada Demo 63", 501);
        ruta.AddEstacion("Parada Demo 46", 10);
        ruta.AddEstacion("Parada Demo 45", 9);
        ruta.AddEstacion("Parada Demo 43", 7);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 21",5);
        ruta.AddEstacion("Parada Demo 20",4);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 105", 306);
        ruta.AddEstacion("Parada Demo 13", 305);
        ruta.AddEstacion("Parada Demo 16", 301);
        
        this.AddRutaInvertida("Línea Demo 78", ruta,"Trayecto inverso ficticio");

        ruta= this.AddRuta("Línea Demo 30", "Recorrido ficticio 30", true); 
        ruta.AddEstacion("Parada Demo 16", 301);
        ruta.AddEstacion("Parada Demo 13", 305);
        ruta.AddEstacion("Parada Demo 104", 309);
        ruta.AddEstacion("Parada Demo 65", 312);
        ruta.AddEstacion("Parada Demo 92", 313);
        ruta.AddEstacion("Parada Demo 44", 8);
        ruta.AddEstacion("Parada Demo 38", 11);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 56", 801);
        ruta.AddEstacion("Parada Demo 57", 802);

        this.AddRutaInvertida("Línea Demo 79", ruta,"Trayecto inverso ficticio"); 

        ruta= this.AddRuta("Línea Demo 31", "Recorrido ficticio 31", true); 
        ruta.AddEstacion("Parada Demo 16", 301);
        ruta.AddEstacion("Parada Demo 106", 302);
        ruta.AddEstacion("Parada Demo 14", 304);
        ruta.AddEstacion("Parada Demo 13", 305);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 37", 3);
        ruta.AddEstacion("Parada Demo 20",4);
        ruta.AddEstacion("Parada Demo 21",5);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 33", 714);
        ruta.AddEstacion("Parada Demo 49", 713);
        ruta.AddEstacion("Parada Demo 40", 711);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 23", 708);
        ruta.AddEstacion("Parada Demo 107", 706);
        ruta.AddEstacion("Parada Demo 42", 705);
        
        this.AddRutaInvertida("Línea Demo 80", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 32", "Recorrido ficticio 32", false); 
        ruta.AddEstacion("Parada Demo 16", 301);
        ruta.AddEstacion("Parada Demo 13", 305);
        ruta.AddEstacion("Parada Demo 105", 306);
        ruta.AddEstacion("Parada Demo 104", 309);
        ruta.AddEstacion("Parada Demo 11", 311);
        ruta.AddEstacion("Parada Demo 80", 402);
        ruta.AddEstacion("Parada Demo 99", 403);
        ruta.AddEstacion("Parada Demo 67", 407);
        ruta.AddEstacion("Parada Demo 82", 408);
        ruta.AddEstacion("Parada Demo 69", 410);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 71", 610);
        ruta.AddEstacion("Parada Demo 75", 607);
        ruta.AddEstacion("Parada Demo 108", 604);
        ruta.AddEstacion("Parada Demo 74", 601);

        this.AddRutaInvertida("Línea Demo 81", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 33", "Recorrido ficticio 33", true); 
        ruta.AddEstacion("Parada Demo 16", 301);
        ruta.AddEstacion("Parada Demo 13", 305);
        ruta.AddEstacion("Parada Demo 105", 306);
        ruta.AddEstacion("Parada Demo 104", 309);
        ruta.AddEstacion("Parada Demo 11", 311);
        ruta.AddEstacion("Parada Demo 80", 402);
        ruta.AddEstacion("Parada Demo 99", 403);
        ruta.AddEstacion("Parada Demo 67", 407);
        ruta.AddEstacion("Parada Demo 82", 408);

        this.AddRutaInvertida("Línea Demo 82", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 34", "Recorrido ficticio 34", true); 
        ruta.AddEstacion("Parada Demo 63", 501);
        ruta.AddEstacion("Parada Demo 109", 502);
        ruta.AddEstacion("Parada Demo 60", 505);
        ruta.AddEstacion("Parada Demo 110", 507);
        ruta.AddEstacion("Parada Demo 111", 513);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 112", 516);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 56", 801);
        ruta.AddEstacion("Parada Demo 57", 802);
        
        this.AddRutaInvertida("Línea Demo 83", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 35", "Recorrido ficticio 35", false); 
        ruta.AddEstacion("Parada Demo 63", 501);
        ruta.AddEstacion("Parada Demo 62", 503);
        ruta.AddEstacion("Parada Demo 60", 505);
        ruta.AddEstacion("Parada Demo 59", 509);
        ruta.AddEstacion("Parada Demo 113", 511);
        ruta.AddEstacion("Parada Demo 114", 512);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 115", 517);
        ruta.AddEstacion("Parada Demo 31", 13);

        this.AddRutaInvertida("Línea Demo 84", ruta,"Trayecto inverso ficticio"); 

        ruta= this.AddRuta("Línea Demo 36", "Recorrido ficticio 36", false); 
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 2", 102);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 12", 310);
        ruta.AddEstacion("Parada Demo 104", 309);
        ruta.AddEstacion("Parada Demo 103", 308);
        ruta.AddEstacion("Parada Demo 105", 306);
        ruta.AddEstacion("Parada Demo 13", 305);
        ruta.AddEstacion("Parada Demo 16", 301);

        this.AddRutaInvertida("Línea Demo 85", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 37", "Recorrido ficticio 37", false); 
        ruta.AddEstacion("Parada Demo 116", 101);
        ruta.AddEstacion("Parada Demo 2", 102);
        ruta.AddEstacion("Parada Demo 28", 106);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 30", 109);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 10", 114);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 20",4);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 43", 7);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 23", 708);
        ruta.AddEstacion("Parada Demo 24", 704);
        ruta.AddEstacion("Parada Demo 25", 703);
        ruta.AddEstacion("Parada Demo 26", 702);
        ruta.AddEstacion("Parada Demo 27", 701);

        this.AddRutaInvertida("Línea Demo 86", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 38", "Recorrido ficticio 38", false); 
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 2", 102);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 6", 110);
        ruta.AddEstacion("Parada Demo 9", 113);
        ruta.AddEstacion("Parada Demo 10", 114);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 37", 3);
        ruta.AddEstacion("Parada Demo 20",4);
        ruta.AddEstacion("Parada Demo 38", 11);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 39", 14);
        ruta.AddEstacion("Parada Demo 33", 714);
        ruta.AddEstacion("Parada Demo 49", 713);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 40", 711);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 23", 708);
        ruta.AddEstacion("Parada Demo 42", 705);

        this.AddRutaInvertida("Línea Demo 87", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 39", "Recorrido ficticio 39", false); 
        ruta.AddEstacion("Parada Demo 1", 101);
        ruta.AddEstacion("Parada Demo 4", 105);
        ruta.AddEstacion("Parada Demo 5", 107);
        ruta.AddEstacion("Parada Demo 29", 108);
        ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 79", 401);
        ruta.AddEstacion("Parada Demo 80", 402);
        ruta.AddEstacion("Parada Demo 66", 406);
        ruta.AddEstacion("Parada Demo 82", 408);
        ruta.AddEstacion("Parada Demo 68", 409);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 101", 611);
        ruta.AddEstacion("Parada Demo 71", 610);
        ruta.AddEstacion("Parada Demo 75", 607);
        ruta.AddEstacion("Parada Demo 72", 606);
        ruta.AddEstacion("Parada Demo 73", 605);
        ruta.AddEstacion("Parada Demo 74", 601);

        this.AddRutaInvertida("Línea Demo 88", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 40", "Recorrido ficticio 40", false); 
        ruta.AddEstacion("Parada Demo 55", 201);
        ruta.AddEstacion("Parada Demo 54", 202);
        ruta.AddEstacion("Parada Demo 53", 203);
        ruta.AddEstacion("Parada Demo 51", 209);
        ruta.AddEstacion("Parada Demo 88", 210);
        ruta.AddEstacion("Parada Demo 98", 212);
        ruta.AddEstacion("Parada Demo 65", 312);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 37", 3);
        ruta.AddEstacion("Parada Demo 20",4);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 43", 7);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 112", 516);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 111", 513);
        ruta.AddEstacion("Parada Demo 59", 509);
        ruta.AddEstacion("Parada Demo 60", 505);
        ruta.AddEstacion("Parada Demo 62", 503);
        ruta.AddEstacion("Parada Demo 63", 501);

        this.AddRutaInvertida("Línea Demo 89", ruta,"Trayecto inverso ficticio"); 
        
        ruta= this.AddRuta("Línea Demo 41", "Recorrido ficticio 41", false); 
        ruta.AddEstacion("Parada Demo 16", 301);
        ruta.AddEstacion("Parada Demo 106", 710);
        ruta.AddEstacion("Parada Demo 14", 304);
        ruta.AddEstacion("Parada Demo 13", 305);
        ruta.AddEstacion("Parada Demo 65", 312);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 20",4);
        ruta.AddEstacion("Parada Demo 21",5);
        ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 56", 801);
        ruta.AddEstacion("Parada Demo 57", 802);        
        
        this.AddRutaInvertida("Línea Demo 90", ruta,"Trayecto inverso ficticio");  
        
        ruta= this.AddRuta("Línea Demo 42", "Recorrido ficticio 42", false); 
        ruta.AddEstacion("Parada Demo 55", 201);
        ruta.AddEstacion("Parada Demo 53", 203);
        ruta.AddEstacion("Parada Demo 17", 115);
        
        ruta= this.AddRuta("Línea Demo 43", "Recorrido ficticio 43", false); 
        ruta.AddEstacion("Parada Demo 55", 201);
    	ruta.AddEstacion("Parada Demo 86", 207);
    	ruta.AddEstacion("Parada Demo 87", 208);
    	ruta.AddEstacion("Parada Demo 88", 210);
    	ruta.AddEstacion("Parada Demo 65", 312);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 22",6);
    	ruta.AddEstacion("Parada Demo 43", 7);
    	ruta.AddEstacion("Parada Demo 44", 8);
    	ruta.AddEstacion("Parada Demo 38", 11);
    	ruta.AddEstacion("Parada Demo 47", 12);
    	
    	ruta= this.AddRuta("Línea Demo 44", "Recorrido ficticio 44", false);  
    	ruta.AddEstacion("Parada Demo 2", 102);
    	ruta.AddEstacion("Parada Demo 4", 105);
    	ruta.AddEstacion("Parada Demo 28", 106);
    	ruta.AddEstacion("Parada Demo 5", 107);
    	ruta.AddEstacion("Parada Demo 29", 108);
    	ruta.AddEstacion("Parada Demo 30", 109);
        ruta.AddEstacion("Parada Demo 18", 1);
    	ruta.AddEstacion("Parada Demo 19",2);
 
	ruta= this.AddRuta("Línea Demo 45", "Recorrido ficticio 45", false);
	ruta.AddEstacion("Parada Demo 1", 101);
    	ruta.AddEstacion("Parada Demo 9", 113);
    	ruta.AddEstacion("Parada Demo 10", 114);
        ruta.AddEstacion("Parada Demo 18", 1);
    	ruta.AddEstacion("Parada Demo 19",2);
    	ruta.AddEstacion("Parada Demo 37", 3);
    	ruta.AddEstacion("Parada Demo 20",4);
    	ruta.AddEstacion("Parada Demo 22",6);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 42", 705);
        
        this.AddRutaInvertida("Línea Demo 91", ruta, "Trayecto inverso ficticio");
        
        ruta= this.AddRuta("Línea Demo 46", "Recorrido ficticio 46", false);
        ruta.AddEstacion("Parada Demo 37", 3);
        ruta.AddEstacion("Parada Demo 19",2);
        ruta.AddEstacion("Parada Demo 18", 1);
        ruta.AddEstacion("Parada Demo 80", 402);
        ruta.AddEstacion("Parada Demo 101", 611);
        ruta.AddEstacion("Parada Demo 117", 609);
        ruta.AddEstacion("Parada Demo 75", 607);
        ruta.AddEstacion("Parada Demo 73", 605);
        ruta.AddEstacion("Parada Demo 77", 602);
        ruta.AddEstacion("Parada Demo 74", 601);

        this.AddRutaInvertida("Línea Demo 92", ruta, "Trayecto inverso ficticio");
        
        ruta= this.AddRuta("Línea Demo 47", "Recorrido ficticio 47", false);
        ruta.AddEstacion("Parada Demo 1", 101);
    	ruta.AddEstacion("Parada Demo 29", 108);
    	ruta.AddEstacion("Parada Demo 6", 110);
    	ruta.AddEstacion("Parada Demo 7", 111);
    	ruta.AddEstacion("Parada Demo 80", 402);
    	ruta.AddEstacion("Parada Demo 99", 403);
    	ruta.AddEstacion("Parada Demo 81", 404);
    	ruta.AddEstacion("Parada Demo 67", 407);
    	ruta.AddEstacion("Parada Demo 68", 409);
        ruta.AddEstacion("Parada Demo 58", 514);
        ruta.AddEstacion("Parada Demo 111", 513);
        ruta.AddEstacion("Parada Demo 60", 505);
        ruta.AddEstacion("Parada Demo 62", 503);
        ruta.AddEstacion("Parada Demo 63", 501);
        
        this.AddRutaInvertida("Línea Demo 93", ruta, "Trayecto inverso ficticio");
        
        ruta= this.AddRuta("Línea Demo 48", "Recorrido ficticio 48", false);
        ruta.AddEstacion("Parada Demo 27", 701);
        ruta.AddEstacion("Parada Demo 26", 702);
        ruta.AddEstacion("Parada Demo 42", 705);
        ruta.AddEstacion("Parada Demo 108", 604);
        ruta.AddEstacion("Parada Demo 75", 607);
        ruta.AddEstacion("Parada Demo 71", 610);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 69", 410);
        ruta.AddEstacion("Parada Demo 68", 409);
    	ruta.AddEstacion("Parada Demo 67", 407);
    	ruta.AddEstacion("Parada Demo 81", 404);
    	
        ruta= this.AddRuta("Línea Demo 49", "Recorrido ficticio 49", false);
        ruta.AddEstacion("Parada Demo 81", 404);
    	ruta.AddEstacion("Parada Demo 67", 407);
    	ruta.AddEstacion("Parada Demo 68", 409);
    	ruta.AddEstacion("Parada Demo 69", 410);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 71", 610);
        ruta.AddEstacion("Parada Demo 75", 607);
        ruta.AddEstacion("Parada Demo 108", 604);
        ruta.AddEstacion("Parada Demo 74", 601);
        ruta.AddEstacion("Parada Demo 42", 705);
        ruta.AddEstacion("Parada Demo 26", 702);
        ruta.AddEstacion("Parada Demo 27", 701);
        
        ruta= this.AddRuta("Línea Demo 50", "Recorrido ficticio 50", false);
        ruta.AddEstacion("Parada Demo 55", 201);
    	ruta.AddEstacion("Parada Demo 54", 202);
    	ruta.AddEstacion("Parada Demo 53", 203);
    	ruta.AddEstacion("Parada Demo 19",2);
    	ruta.AddEstacion("Parada Demo 20",4);
    	ruta.AddEstacion("Parada Demo 22",6);
    	ruta.AddEstacion("Parada Demo 45", 9);
    	ruta.AddEstacion("Parada Demo 46", 10);
	ruta.AddEstacion("Parada Demo 60", 505);
        ruta.AddEstacion("Parada Demo 62", 503);
        ruta.AddEstacion("Parada Demo 109", 502);
        ruta.AddEstacion("Parada Demo 63", 501);
        
        this.AddRutaInvertida("Línea Demo 94", ruta, "Trayecto inverso ficticio");
        
        ruta= this.AddRuta("Línea Demo 51", "Recorrido ficticio 51", false);
       	ruta.AddEstacion("Parada Demo 55", 201);
    	ruta.AddEstacion("Parada Demo 54", 202);
    	ruta.AddEstacion("Parada Demo 53", 203);
    	ruta.AddEstacion("Parada Demo 90", 206);
    	ruta.AddEstacion("Parada Demo 88", 210);
    	ruta.AddEstacion("Parada Demo 98", 212);
    	ruta.AddEstacion("Parada Demo 80", 402);
    	ruta.AddEstacion("Parada Demo 100", 405);
    	ruta.AddEstacion("Parada Demo 67", 407);
    	ruta.AddEstacion("Parada Demo 68", 409);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 101", 611);
        ruta.AddEstacion("Parada Demo 118", 608);
        ruta.AddEstacion("Parada Demo 74", 601);
    	
       	this.AddRutaInvertida("Línea Demo 95", ruta, "Trayecto inverso ficticio");
       	
       	ruta= this.AddRuta("Línea Demo 52", "Recorrido ficticio 52", false);
       	ruta.AddEstacion("Parada Demo 55", 201);
    	ruta.AddEstacion("Parada Demo 54", 202);
    	ruta.AddEstacion("Parada Demo 53", 203);
       	ruta.AddEstacion("Parada Demo 51", 209);
    	ruta.AddEstacion("Parada Demo 50", 213);
    	ruta.AddEstacion("Parada Demo 91", 214);
    	ruta.AddEstacion("Parada Demo 99", 403);
    	ruta.AddEstacion("Parada Demo 81", 404);
    	ruta.AddEstacion("Parada Demo 66", 406);
    	ruta.AddEstacion("Parada Demo 67", 407);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 117", 609);
        ruta.AddEstacion("Parada Demo 75", 607);
        ruta.AddEstacion("Parada Demo 73", 605);
        ruta.AddEstacion("Parada Demo 74", 601);
    	
       	this.AddRutaInvertida("Línea Demo 96", ruta, "Trayecto inverso ficticio");
       	
       	ruta= this.AddRuta("Línea Demo 53", "Recorrido ficticio 53", false);
       	ruta.AddEstacion("Parada Demo 55", 201);
    	ruta.AddEstacion("Parada Demo 54", 202);
    	ruta.AddEstacion("Parada Demo 53", 203);
    	ruta.AddEstacion("Parada Demo 90", 206);
    	ruta.AddEstacion("Parada Demo 86", 207);
    	ruta.AddEstacion("Parada Demo 88", 210);
    	ruta.AddEstacion("Parada Demo 91", 214);
    	ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 56", 801);
        ruta.AddEstacion("Parada Demo 57", 802);
       	
       	this.AddRutaInvertida("Línea Demo 97", ruta, "Trayecto inverso ficticio");
       	
       	ruta= this.AddRuta("Línea Demo 54", "Recorrido ficticio 54", false);
       	ruta.AddEstacion("Parada Demo 16", 301);
    	ruta.AddEstacion("Parada Demo 15", 303);
    	ruta.AddEstacion("Parada Demo 13", 305);
    	ruta.AddEstacion("Parada Demo 105", 306);
        ruta.AddEstacion("Parada Demo 18", 1);
    	ruta.AddEstacion("Parada Demo 19",2);
    	
    	ruta= this.AddRuta("Línea Demo 55", "Recorrido ficticio 55", false);    	
    	ruta.AddEstacion("Parada Demo 55", 201);
    	ruta.AddEstacion("Parada Demo 53", 203);
    	ruta.AddEstacion("Parada Demo 90", 206);
    	ruta.AddEstacion("Parada Demo 86", 207);
    	ruta.AddEstacion("Parada Demo 51", 209);
    	ruta.AddEstacion("Parada Demo 88", 210);
    	ruta.AddEstacion("Parada Demo 95", 211);
    	ruta.AddEstacion("Parada Demo 99", 403);
    	ruta.AddEstacion("Parada Demo 100", 405);
    	ruta.AddEstacion("Parada Demo 66", 406);
    	ruta.AddEstacion("Parada Demo 68", 409);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 83", 612);
        ruta.AddEstacion("Parada Demo 101", 611);
        ruta.AddEstacion("Parada Demo 71", 610);
        ruta.AddEstacion("Parada Demo 118", 608);
        ruta.AddEstacion("Parada Demo 73", 605);
        ruta.AddEstacion("Parada Demo 77", 602);
        ruta.AddEstacion("Parada Demo 74", 601);
        
    	this.AddRutaInvertida("Línea Demo 98", ruta, "Trayecto inverso ficticio");
        
        ruta= this.AddRuta("Línea Demo 56", "Recorrido ficticio 56", true); 
        
        ruta.AddEstacion("Parada Demo 1", 101);
    	ruta.AddEstacion("Parada Demo 2", 102);
    	ruta.AddEstacion("Parada Demo 36", 103);
    	ruta.AddEstacion("Parada Demo 3", 104);
    	ruta.AddEstacion("Parada Demo 4", 105);
    	ruta.AddEstacion("Parada Demo 28", 106);
    	ruta.AddEstacion("Parada Demo 5", 107);
    	ruta.AddEstacion("Parada Demo 29", 108);
    	ruta.AddEstacion("Parada Demo 30", 109);
    	ruta.AddEstacion("Parada Demo 6", 110);
    	ruta.AddEstacion("Parada Demo 7", 111);
    	ruta.AddEstacion("Parada Demo 8", 112);
    	ruta.AddEstacion("Parada Demo 9", 113);
    	ruta.AddEstacion("Parada Demo 10", 114);
    	ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 18", 1);
    	ruta.AddEstacion("Parada Demo 19",2);
    	ruta.AddEstacion("Parada Demo 37", 3);
    	ruta.AddEstacion("Parada Demo 20",4);
    	ruta.AddEstacion("Parada Demo 21",5);
    	ruta.AddEstacion("Parada Demo 22",6);
    	ruta.AddEstacion("Parada Demo 43", 7);
    	ruta.AddEstacion("Parada Demo 44", 8);
    	ruta.AddEstacion("Parada Demo 45", 9);
    	ruta.AddEstacion("Parada Demo 46", 10);
    	ruta.AddEstacion("Parada Demo 38", 11);
    	ruta.AddEstacion("Parada Demo 47", 12);
        ruta.AddEstacion("Parada Demo 31", 13);
        ruta.AddEstacion("Parada Demo 115", 517);
        ruta.AddEstacion("Parada Demo 112", 516);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 58", 514);
        ruta.AddEstacion("Parada Demo 111", 513);
        ruta.AddEstacion("Parada Demo 114", 512);
        ruta.AddEstacion("Parada Demo 113", 511);
        ruta.AddEstacion("Parada Demo 96", 510);
        ruta.AddEstacion("Parada Demo 59", 509);
        ruta.AddEstacion("Parada Demo 64", 508);
        ruta.AddEstacion("Parada Demo 110", 507);
        ruta.AddEstacion("Parada Demo 97", 506);
        ruta.AddEstacion("Parada Demo 60", 505);
        ruta.AddEstacion("Parada Demo 61", 504);
        ruta.AddEstacion("Parada Demo 62", 503);
        ruta.AddEstacion("Parada Demo 109", 502);
        ruta.AddEstacion("Parada Demo 63", 501);

        this.AddRutaInvertida("B1", ruta); 
        
        ruta= this.AddRuta("Línea Demo 57", "Recorrido ficticio 57", true);
        ruta.AddEstacion("Parada Demo 1", 101);
    	ruta.AddEstacion("Parada Demo 2", 102);
    	ruta.AddEstacion("Parada Demo 36", 103);
    	ruta.AddEstacion("Parada Demo 3", 104);
    	ruta.AddEstacion("Parada Demo 4", 105);
    	ruta.AddEstacion("Parada Demo 28", 106);
    	ruta.AddEstacion("Parada Demo 5", 107);
    	ruta.AddEstacion("Parada Demo 29", 108);
    	ruta.AddEstacion("Parada Demo 30", 109);
    	ruta.AddEstacion("Parada Demo 6", 110);
    	ruta.AddEstacion("Parada Demo 7", 111);
    	ruta.AddEstacion("Parada Demo 8", 112);
        ruta.AddEstacion("Parada Demo 9", 113);
        ruta.AddEstacion("Parada Demo 10", 114);
        ruta.AddEstacion("Parada Demo 17", 115);
        ruta.AddEstacion("Parada Demo 92", 313);
        ruta.AddEstacion("Parada Demo 65", 312);
    	ruta.AddEstacion("Parada Demo 80", 402);
    	ruta.AddEstacion("Parada Demo 99", 403);
    	ruta.AddEstacion("Parada Demo 81", 404);
    	ruta.AddEstacion("Parada Demo 100", 405);
    	ruta.AddEstacion("Parada Demo 66", 406);
    	ruta.AddEstacion("Parada Demo 67", 407);
    	ruta.AddEstacion("Parada Demo 82", 408);
    	ruta.AddEstacion("Parada Demo 68", 409);
    	ruta.AddEstacion("Parada Demo 69", 410);
        ruta.AddEstacion("Parada Demo 70", 613);
        ruta.AddEstacion("Parada Demo 83", 612);
        ruta.AddEstacion("Parada Demo 101", 611);
        ruta.AddEstacion("Parada Demo 71", 610);
        ruta.AddEstacion("Parada Demo 117", 609);
        ruta.AddEstacion("Parada Demo 118", 608);
        ruta.AddEstacion("Parada Demo 75", 607);
        ruta.AddEstacion("Parada Demo 72", 606);
        ruta.AddEstacion("Parada Demo 73", 605);
        ruta.AddEstacion("Parada Demo 108", 604);
        ruta.AddEstacion("Parada Demo 76", 603);
        ruta.AddEstacion("Parada Demo 77", 602);
        ruta.AddEstacion("Parada Demo 74", 601);

        this.AddRutaInvertida("B5", ruta);

        ruta= this.AddRuta("Línea Demo 58", "Recorrido ficticio 58", true);
        ruta.AddEstacion("Parada Demo 16", 301);
    	ruta.AddEstacion("Parada Demo 106", 302);
    	ruta.AddEstacion("Parada Demo 15", 303);
    	ruta.AddEstacion("Parada Demo 14", 304);
    	ruta.AddEstacion("Parada Demo 13", 305);
    	ruta.AddEstacion("Parada Demo 105", 306);
    	ruta.AddEstacion("Parada Demo 102", 307);
    	ruta.AddEstacion("Parada Demo 103", 308);
    	ruta.AddEstacion("Parada Demo 104", 309);
    	ruta.AddEstacion("Parada Demo 12", 310);
    	ruta.AddEstacion("Parada Demo 11", 311);
    	ruta.AddEstacion("Parada Demo 65", 312);
    	ruta.AddEstacion("Parada Demo 92", 313);
        ruta.AddEstacion("Parada Demo 18", 1);
    	ruta.AddEstacion("Parada Demo 19",2);
    	ruta.AddEstacion("Parada Demo 37", 3);
    	ruta.AddEstacion("Parada Demo 20",4);
    	ruta.AddEstacion("Parada Demo 21",5);
    	ruta.AddEstacion("Parada Demo 22",6);
    	ruta.AddEstacion("Parada Demo 43", 7);
    	ruta.AddEstacion("Parada Demo 44", 8);
    	ruta.AddEstacion("Parada Demo 45", 9);
    	ruta.AddEstacion("Parada Demo 46", 10);
    	ruta.AddEstacion("Parada Demo 38", 11);
    	ruta.AddEstacion("Parada Demo 47", 12);
    	ruta.AddEstacion("Parada Demo 31", 13);
    	ruta.AddEstacion("Parada Demo 39", 14);
        ruta.AddEstacion("Parada Demo 93", 716);
        ruta.AddEstacion("Parada Demo 32", 715);
        ruta.AddEstacion("Parada Demo 33", 714);
        ruta.AddEstacion("Parada Demo 49", 713);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 40", 711);
        ruta.AddEstacion("Parada Demo 41", 710);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 23", 708);
        ruta.AddEstacion("Parada Demo 89", 707);
        ruta.AddEstacion("Parada Demo 107", 706);
        ruta.AddEstacion("Parada Demo 42", 705);

        this.AddRutaInvertida("D3", ruta);
        
        ruta= this.AddRuta("Línea Demo 59", "Recorrido ficticio 59", true);
        ruta.AddEstacion("Parada Demo 55", 201);
    	ruta.AddEstacion("Parada Demo 54", 202);
    	ruta.AddEstacion("Parada Demo 53", 203);
    	ruta.AddEstacion("Parada Demo 52", 204);
    	ruta.AddEstacion("Parada Demo 94", 205);
    	ruta.AddEstacion("Parada Demo 90", 206);
    	ruta.AddEstacion("Parada Demo 86", 207);
    	ruta.AddEstacion("Parada Demo 87", 208);
    	ruta.AddEstacion("Parada Demo 51", 209);
    	ruta.AddEstacion("Parada Demo 88", 210);
    	ruta.AddEstacion("Parada Demo 95", 211);
    	ruta.AddEstacion("Parada Demo 98", 212);
    	ruta.AddEstacion("Parada Demo 50", 213);
    	ruta.AddEstacion("Parada Demo 91", 214);
        ruta.AddEstacion("Parada Demo 65", 312);
    	ruta.AddEstacion("Parada Demo 92", 313);
        ruta.AddEstacion("Parada Demo 18", 1);
    	ruta.AddEstacion("Parada Demo 19",2);
    	ruta.AddEstacion("Parada Demo 37", 3);
    	ruta.AddEstacion("Parada Demo 20",4);
    	ruta.AddEstacion("Parada Demo 21",5);
    	ruta.AddEstacion("Parada Demo 22",6);
    	ruta.AddEstacion("Parada Demo 43", 7);
    	ruta.AddEstacion("Parada Demo 44", 8);
    	ruta.AddEstacion("Parada Demo 45", 9);
    	ruta.AddEstacion("Parada Demo 46", 10);
    	ruta.AddEstacion("Parada Demo 38", 11);
    	ruta.AddEstacion("Parada Demo 47", 12);
    	ruta.AddEstacion("Parada Demo 31", 13);
    	ruta.AddEstacion("Parada Demo 39", 14);
        ruta.AddEstacion("Parada Demo 93", 716);
        ruta.AddEstacion("Parada Demo 32", 715);
        ruta.AddEstacion("Parada Demo 33", 714);
        ruta.AddEstacion("Parada Demo 49", 713);
        ruta.AddEstacion("Parada Demo 34", 712);
        ruta.AddEstacion("Parada Demo 40", 711);
        ruta.AddEstacion("Parada Demo 41", 710);
        ruta.AddEstacion("Parada Demo 35", 709);
        ruta.AddEstacion("Parada Demo 23", 708);
        ruta.AddEstacion("Parada Demo 24", 704);
        ruta.AddEstacion("Parada Demo 25", 703);
        ruta.AddEstacion("Parada Demo 26", 702);
        ruta.AddEstacion("Parada Demo 27", 701);
                
        this.AddRutaInvertida("C4", ruta);
}
