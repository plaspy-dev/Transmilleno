//obfuscate exclude OnHome,OnRutas
//obfuscate excludeMembersOf 

var inicial=-1;
var destino=-1;
var estacioni="";
var estacionf="";
var bRutas=false;
var zinicio="";
var zdestino="";
var m_szRuta="";
var nPaso=0;
var m_pRutas= new Rutas();

function GetRuta(szRuta) {
	var ruta= getRuta(m_pRutas,szRuta);
	if(ruta!=null) {
		var estaciones = getEstaciones(ruta);
		$('#lruta').html(getDescripcion(ruta));
		$("#srutan").html(getNombre(ruta));
		
		for(var j=0; j<estaciones.length; j++) {
			$('#lruta').after("<li class='delrut'>" + estaciones[j] + "</li>");
		}
	}
	return ruta!=null;
}

function OnRutas() {

	bRutas=!bRutas;

	if(bRutas)
		$("#hrutas .ui-icon").addClass("ui-icon-check").removeClass("ui-icon-radio-off");
	else $("#hrutas .ui-icon").removeClass("ui-icon-check").addClass("ui-icon-radio-off");
	
	$("#smensaje").text(bRutas ? "Zona rutas" : "Zona origen");
	$("#hrutasr").attr("href", "#presultados");
	$("#ha").attr("href", bRutas ? "#" : "#pzonaa");
	$("#hb").attr("href", bRutas ? "#" : "#pzonab");
	$("#hc").attr("href", bRutas ? "#" : "#pzonac");
	$("#hd").attr("href", bRutas ? "#" : "#pzonad");
	$("#he").attr("href", bRutas ? "#" : "#pzonae");
	$("#hf").attr("href", bRutas ? "#" : "#pzonaf");
	$("#hg").attr("href", bRutas ? "#" : "#pzonag");
	$("#hh").attr("href", bRutas ? "#" : "#pzonah");
	$("#hj").attr("href", bRutas ? "#" : "#pzonaj");
	$("#ha").attr("nom", $("#ha").html());
	$("#hb").attr("nom", $("#hb").html());
	$("#hc").attr("nom", $("#hc").html());
	$("#hd").attr("nom", $("#hd").html());
	$("#he").attr("nom", $("#he").html());
	$("#hf").attr("nom", $("#hf").html());
	$("#hg").attr("nom", $("#hg").html());
	$("#hh").attr("nom", $("#hh").html());
	$("#hj").attr("nom", $("#hj").html());
}
		
function OnHome(redirect) {
	destino=-1;
	zdestino="";
	inicial=-1;
	zinicio="";
	nPaso=0;
	$("#hrutas").show();
	$("#hhome").hide();
	$("#hinicio").hide();
	$("#smensaje").text(bRutas ? "Zona rutas" : "Zona origen");
	if(redirect)
		$.mobile.changePage("#prutas");
}

function OnAtrasZona() {
	
	nPaso--;
	if(nPaso==1) {
		zdestino="";
	} else {
		OnHome(true);
	}
}

function Atras() {
	
	nPaso--;
	if(nPaso==0) 
		inicial=-1;
	else if(nPaso==2) 
		destino=-1;
	else {
		OnHome(true);
	}
}

function CalcularRutas() {

	$(".delres").remove();
	var temp= getRutaID(m_pRutas, inicial, destino);
	if(temp.length==0) {
		$("#ldirectas").show();
	} else {
		$("#ldirectas").hide();
	}
	for(var i=temp.length-1; i>=0; i--) {
		var ruta= temp[i];
		$("#ldirectas").after("<li class='delres'><a href='#' class='hres' rut='" + getNombre(ruta) + "'><h3>" + getNombre(ruta) + "</h3><p>" + getDescripcion(ruta) + "</p><span class='ui-li-count'>" + getParadas(ruta) +"</span></a></li>");
	}
	temp= getTransbordos(m_pRutas);
	if(temp.length==0) {
		$("#ltransbordos").show();
	} else {
		$("#ltransbordos").hide();
	}
	for(var i=temp.length-1; i>=0; i--) {
		var ruta= temp[i];
		$("#ltransbordos").after("<li class='delres'><a href='#' class='hres' rut='" + getNombre(ruta) + "'><h3>" + getNombre(ruta) + "</h3><p>" + getDescripcion(ruta) + "</p><span class='ui-li-count'>" + getParadas(ruta) +"</span></a></li>");
	}
	$(".hres").click(function() {
		
		m_szRuta= $(this).attr("rut");
		$(".delrut").remove();
		if(!GetRuta(m_szRuta.substring(0, 3)))
			GetRuta(m_szRuta.substring(0, 2));
		$.mobile.changePage("#pruta");
		$('#lruta').parent().listview('refresh');
	});
}

function onRuta() {
	
	if($(this).attr("href")=="#") {
		if(bRutas) {
			
			var letra= $(this).attr("ti").toUpperCase()[1];
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
			$("#hrutasr").attr("href", "#prutas");
			$("#hrutasr").attr("ti", $(this).attr("ti"));
			$("#hrutasr").attr("nom", $(this).attr("nom"));
			
			$(".delrut").remove();
			
			$('#lruta').html("Seleccione la ruta");
			$("#srutan").html($(this).attr("nom"));
			
			// los agregamos
			for(var i=resultado.length-1; i>=0; i--) {
			   var ruta= resultado[i];
			   $("#lruta").after("<li class='delrut'><a href='#' class='hres' rut='" + getNombre(ruta) + "'>" + getNombre(ruta) + "</a></li>");
			}
			$(".hres").click(function() {
	
				m_szRuta= $(this).attr("rut");
				$(".delrut").remove();
				if(!GetRuta(m_szRuta.substring(0, 3)))
					GetRuta(m_szRuta.substring(0, 2));
				$("#hrutasr").attr("href", "#");
				$.mobile.changePage("#pruta");
				$('#lruta').parent().listview('refresh');
				$(window).scrollTop(0);
			});
			$.mobile.changePage("#pruta");
			$('#lruta').parent().listview('refresh');
		} else if(nPaso==1) {
			nPaso++;
			inicial= $(this).attr("alt");
			estacioni= $(this).html();
			if(inicial==518) { // jimenez
			  inicial=13;
			} else if(inicial==515) { // ricaute
			  inicial=613;
			}
			$.mobile.changePage("#prutas");
		} else if(nPaso==3) {
			destino= $(this).attr("alt");
			estacionf= $(this).html();
			if(destino==518) { // jimenez
			  destino=13;
			} else if(destino==515) { // ricaute
			  destino=613;
			}
			CalcularRutas();
			$.mobile.changePage("#presultados");
			$('#ldirectas').parent().listview('refresh');
		}
		return false;
	} else if($(this).attr("href").match(/.pzona./)) {
		if($(this).children().children(".ui-icon").length==0 && bRutas==false) { // es un atras
			nPaso++;
		}
		if(nPaso==1) {
			zinicio= $(this).attr("href");
			$("#hinicio").attr("href", zinicio);
		} else if(nPaso==3) {
			zdestino= $(this).attr("href");
			$("#hresultados").attr("href", zdestino);
		}
	} else if($(this).attr("href")=="index.html") {
		window.location= "./?f=1";
	}
	return true;
}

$(function() {

	var contenido= $("div[data-role=content] .adsense");
	$(document).bind ('pageshow', function (e, data) {
		contenido.find("ins:eq(0)").prependTo($.mobile.activePage.find(".ui-content .adsense"));
	});

	$.mobile.page.prototype.options.domCache = true;
	$("#hrutas").show();
	
	$("#hrutas .ui-icon").addClass("ui-icon-radio-off");	
	$('#prutas').live('pageshow', function(event) {
		
		if(inicial==-1) {
			$("#hrutas").show();
			$("#hhome").hide();
			$("#hinicio").hide();
			$("#smensaje").text(bRutas ? "Zona rutas" : "Zona origen");
		} else {
			$("#hrutas").hide();
			$("#hinicio").show();
			$("#hhome").show();
			$("#smensaje").text("Zona destino");
		}
	});
	$('#presultados').live('pageshow', function(event) {
		$("#sresultados").html(estacioni + "-" + estacionf);
	});
	$("a").click(onRuta);
	OnHome(true);
});
