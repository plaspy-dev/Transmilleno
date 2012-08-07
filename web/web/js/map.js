GLOBAL_AREAS= new Array();

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

$(function() { // inicilizamos todo
	var arrayAreas = document.body.getElementsByTagName("area");
	for(var i = 0; i < arrayAreas.length; i++) {
		GLOBAL_AREAS[i]= arrayAreas[i].coords;
	}
	scaleArea();
});

$(window).bind('resize', function() {

	scaleArea();
	if($("#mapa").width()>900) { 
		$("#mapa").attr("usemap", "#m_mapa");
	} else {
		$("#mapa").attr("usemap", "");
	}
});
