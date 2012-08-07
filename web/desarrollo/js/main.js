//obfuscate exclude
//obfuscate excludeMembersOf 
var GLOBAL_WIDTH=1232;
var GLOBAL_HEIGHT=822;
var GLOBAL_AREAS= new Array();

function scaleArea() {   // using values stored at load, recalculate new values for the current size

	var arrayAreas = document.body.getElementsByTagName("AREA");
	for(var i = 0; i < arrayAreas.length; i++) {

	   rescaleX= $("#mapa").width()/GLOBAL_WIDTH;
	   rescaleY= $("#mapa").height()/GLOBAL_HEIGHT;
	   sarray=GLOBAL_AREAS[i].split(",");      // convert coordinates to a numeric array assuming comma-delimited values
	   var rarray =new Array();
	   for(var j = 0; j < sarray.length; j += 2) {
		  rarray[j]=Math.round(parseInt(sarray[j])*rescaleX);  // rescale the values
		  rarray[j+1]=Math.round(parseInt(sarray[j+1])*rescaleY);  // rescale the values
	   }
	   arrayAreas[i].coords=rarray.join(",");    // put the values back into a string
   }
   if($("#mapa").width()>900) { 
		$("#mapa").attr("usemap", "#m_mapa");
	} else {
		$("#mapa").attr("usemap", "");
	}
}

$(window).bind('resize', function() {

	scaleArea();
	if($("#mapa").width()>900) { 
		$("#mapa").attr("usemap", "#m_mapa");
	} else {
		$("#mapa").attr("usemap", "");
	}
});

$(function() {
	
	var maxh= GLOBAL_HEIGHT;
	var maxw= GLOBAL_WIDTH;
	var inicial=-1;
	var m_pRutas= new Rutas();
	
	$("#mapa").click(function(e) {
		if(this._drag) {
			this._drag=false;
			return;
		}
		if($(this).width()==GLOBAL_WIDTH) { // esta maximizado

			this._zoom=false;
			
			$("#mapac").width("100%");
			$("#mapac").height("auto");
			
			$(this)
			.css("left", 0)
			.css("top", 0)
			// .addClass("curzin")
			.removeClass("curzout")
			.draggable( "option", "disabled", true);
			
			$(this).animate({
				"margin-top": 0,
				"margin-left": 0,
				width: '100%'
			}, scaleArea);
		} else {
			if($(this).width()>900) { // no hacemos nada si el mapa es legible
				return;
			}
			this._zoom=true;
			var offset = $(this).offset();
			x= e.clientX - offset.left;
			y= e.clientY - offset.top;
			
			var minw= $("#mapac").width();
			var minh= $("#mapac").height();
			
			$("#mapac").width(minw);
			$("#mapac").height(minh);
			
			xd= x*maxw/minw;
			yd= y*maxh/minh;

			$(this)
			.css("left", 0)
			.css("top", 0)
			// .addClass("curzout")
			.removeClass("curzin")
			.draggable( "option", "disabled", false );
			
			$(this).animate({
				width: maxw,
				"margin-top": -yd+y,
				"margin-left": -xd+x
			}, scaleArea);
		}
	}).draggable({
		start: function() { this._drag=true; },
		disable: true
	});
	$("area, #truta")
	.css("cursor", "pointer")
	.click(function(){
		
		$("#cerrar").show();
		$(window).scrollTop(0);
		
		if($(this).attr("target")[0]=='0') {
			
			$(".delrut").remove();
			$("#nombreruta").text($(this).attr("alt"));
			$("#truta").text(" / " + $(this).attr("alt"));
			
			$("#truta").attr("target", $(this).attr("target"));
			$("#truta").attr("alt", $(this).attr("alt"));
			
			var letra= $(this).attr("target")[1];
			var resultado= new Array();
			var rutas= getRutasArray(m_pRutas);
			for(var i=0; i<rutas.length; i++) {
				var ruta= rutas[i];
				if(getNombre(ruta)[0]==letra) {
					resultado.push(ruta);
				}
			}
			// ordenamos las rutas
			for(var i=resultado.length-1; i>0; i--) {
				for(var j=0;j<i; j++) {
					var ruta= resultado[j+1];
					var ruta2= resultado[j];
					if(getNombre(ruta2).localeCompare(getNombre(ruta))>0) {
						var temp= ruta2;
						resultado[j]=ruta;
						resultado[j+1]=temp;
					}
				}
			}
			
			// los agregamos
			for(var i=0; i<resultado.length; i++) {
			   var ruta= resultado[i];
			   $('#trrutas').after("<tr class='delrut tdr'><td class='" + getNombre(ruta)[0] + " cruta'>" + getNombre(ruta) + "</td></tr>");
			}
			$("#trutar").show();
			$("#tresultados").show();
			$("#dresultados").slideDown('slow');
		} else {
			if($("#mapa").get(0)._zoom)
				$("#mapa").click();
			if(inicial==-1) {
				$(".delres").remove();
				$("#dresultados").slideUp('fast', function() {$("#tresultados").hide();});
				inicial= $(this).attr("target");
				$("#texto").text($(this).attr("alt"));
				$("#ayuda").text("Por favor seleccione su destino.");
			} else {
				$("#texto").text($("#texto").text() + " - " + $(this).attr("alt"));
				var destino=$(this).attr("target");
				var temp= getRutaID(m_pRutas,inicial, destino);
				if(temp.length==0) {
					$('#trutasn').show();
				} else {
					$('#trutasn').hide();
				}
				for(var i=temp.length-1; i>=0; i--) {
					var ruta= temp[i];
					$('#trutasn').after("<tr class='delres tdr'><td class='" + getNombre(ruta)[0] + " cruta'>" + getNombre(ruta) + "</td><td class='tdr' style='text-align: right;'>" + getParadas(ruta) + "</td><td class='tdr'>" + getDescripcion(ruta) + "</td></tr>");
				}
				
				temp= getTransbordos(m_pRutas);
				if(temp.length==0) {
					$('#ttransbordosn').show();
				} else {
					$('#ttransbordosn').hide();
				}
				for(var i=temp.length-1; i>=0; i--) {
					var ruta= temp[i];
					$('#ttransbordosn').after("<tr class='delres tdr'><td class='" + getNombre(ruta)[0]+ " cruta'>" + getNombre(ruta) + "</td><td class='tdr' style='text-align: right;'>" + getParadas(ruta) + "</td><td class='tdr'>" + getDescripcion(ruta) + "</td></tr>");
				}
				$("#ayuda").text("Resultados.");
				$("#tresultados").show();
				$("#tabresultados").show();
				$("#dresultados").slideDown('slow');
				inicial=-1;
			}
		}
		function GetRuta(szRuta) {
			var ruta= getRuta(m_pRutas,szRuta);
			if(ruta!=null) {
				var estaciones = getEstaciones(ruta);
				$("#nombreruta").html(getNombre(ruta) + "<br/>" + getDescripcion(ruta));
				
				for(var j=0; j<estaciones.length; j++) {
					$('#trrutas').after("<tr class='delrut tdr'><td class='" + getNombre(ruta)[0] + "'>" + estaciones[j] + "</td></tr>");
				}
			}
			return ruta!=null;
		}
		$(".cruta").css('cursor', 'pointer').click(function() {

			$(window).scrollTop(0);
			$(".delrut").remove();
			$("#trutar").show();
			if(!GetRuta($(this).text().substring(0, 3)))
				GetRuta($(this).text().substring(0, 2));
		});
		return false;
	});
	$("#cerrar,#texto").click(function() {
		inicial=-1;
		$(".delres").remove();
		$(".delrut").remove();
		$("#dresultados").slideUp('fast', function() {$("#tresultados").hide();});
		$("#texto").text("");
		$("#truta").text("");
		$("#cerrar").hide();
		$("#trutar").hide();
		$("#tabresultados").hide();
		$("#ayuda").text("Por favor seleccione su origen o una zona.");
	});
	
	var arrayAreas = document.body.getElementsByTagName("area");
	for(var i = 0; i < arrayAreas.length; i++) {
		GLOBAL_AREAS[i]= arrayAreas[i].coords;
	}
	scaleArea();
});
