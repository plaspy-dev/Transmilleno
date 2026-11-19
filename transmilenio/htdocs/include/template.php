<?php
function genericheader($titulo) {
?>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- DW6 -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><?php echo $titulo; ?></title>
<link rel="stylesheet" href="/include/emx_nav_left.css" type="text/css">
<script type="text/javascript">
<!--
var time = 3000;
var numofitems = 7;

//menu constructor
function menu(allitems,thisitem,startstate){ 
  callname= "gl"+thisitem;
  divname="subglobal"+thisitem;  
  this.numberofmenuitems = 7;
  this.caller = document.getElementById(callname);
  this.thediv = document.getElementById(divname);
  this.thediv.style.visibility = startstate;
}

//menu methods
function ehandler(event,theobj){
  for (var i=1; i<= theobj.numberofmenuitems; i++){
    var shutdiv =eval( "menuitem"+i+".thediv");
    shutdiv.style.visibility="hidden";
  }
  theobj.thediv.style.visibility="visible";
}
				
function closesubnav(event){
  if ((event.clientY <48)||(event.clientY > 107)){
    for (var i=1; i<= numofitems; i++){
      var shutdiv =eval('menuitem'+i+'.thediv');
      shutdiv.style.visibility='hidden';
    }
  }
}
// -->
</script>
</head>
<body onmousemove="closesubnav(event);"> 
<div class="skipLinks">skip to: <a href="/include/#content">page content</a> | <a href="/include/pageNav">links on this page</a> | <a href="/include/#globalNav">site navigation</a> | <a href="/include/#siteInfo">footer (site information)</a> </div>
<div id="masthead"> 
  <h1 id="siteName">Transmilenio</h1> 
  <div id="utility"> 
    <a href="/include/#">Utility Link</a> | <a href="/include/#">Utility Link</a> | <a href="/include/#">Utility
    Link</a> 
  </div> 
  <div id="globalNav"> 
    <img alt="" src="gblnav_left.gif" height="32" width="4" id="gnl"> <img alt="" src="glbnav_right.gif" height="32" width="4" id="gnr"> 
    <div id="globalLink"> 
      <a href="/include/#" id="gl1" class="glink" onmouseover="ehandler(event,menuitem1);">global 
	  link</a><a href="/include/#" id="gl2" class="glink" onmouseover="ehandler(event,menuitem2);">global
      link</a><a href="/include/#" id="gl3" class="glink" onmouseover="ehandler(event,menuitem3);">global
      link</a><a href="/include/#" id="gl4" class="glink" onmouseover="ehandler(event,menuitem4);">global
      link</a><a href="/include/#" id="gl5" class="glink" onmouseover="ehandler(event,menuitem5);">global
      link</a><a href="/include/#" id="gl6" class="glink" onmouseover="ehandler(event,menuitem6);">global
      link</a><a href="/include/#" id="gl7" class="glink" onmouseover="ehandler(event,menuitem7);">global
      link</a> 
    </div> 
    <!--end globalLinks--> 
    <form id="search" action=""> 
      <input name="searchFor" type="text" size="10"> 
      <a href="/include/">search</a> 
    </form> 
  </div> 
  <!-- end globalNav --> 
  <div id="subglobal1" class="subglobalNav"> 
    <a href="/include/#">subglobal1 link</a> | <a href="/include/#">subglobal1 link</a> | <a href="/include/#">subglobal1
    link</a> | <a href="/include/#">subglobal1 link</a> | <a href="/include/#">subglobal1 link</a> | <a href="/include/#">subglobal1
    link</a> | <a href="/include/#">subglobal1 link</a> 
  </div> 
  <div id="subglobal2" class="subglobalNav"> 
    <a href="/include/#">subglobal2 link</a> | <a href="/include/#">subglobal2 link</a> | <a href="/include/#">subglobal2
    link</a> | <a href="/include/#">subglobal2 link</a> | <a href="/include/#">subglobal2 link</a> | <a href="/include/#">subglobal2
    link</a> | <a href="/include/#">subglobal2 link</a> 
  </div> 
  <div id="subglobal3" class="subglobalNav"> 
    <a href="/include/#">subglobal3 link</a> | <a href="/include/#">subglobal3 link</a> | <a href="/include/#">subglobal3
    link</a> | <a href="/include/#">subglobal3 link</a> | <a href="/include/#">subglobal3 link</a> | <a href="/include/#">subglobal3
    link</a> | <a href="/include/#">subglobal3 link</a> 
  </div> 
  <div id="subglobal4" class="subglobalNav"> 
    <a href="/include/#">subglobal4 link</a> | <a href="/include/#">subglobal4 link</a> | <a href="/include/#">subglobal4
    link</a> | <a href="/include/#">subglobal4 link</a> | <a href="/include/#">subglobal4 link</a> | <a href="/include/#">subglobal4
    link</a> | <a href="/include/#">subglobal4 link</a> 
  </div> 
  <div id="subglobal5" class="subglobalNav"> 
    <a href="/include/#">subglobal5 link</a> | <a href="/include/#">subglobal5 link</a> | <a href="/include/#">subglobal5
    link</a> | <a href="/include/#">subglobal5 link</a> | <a href="/include/#">subglobal5 link</a> | <a href="/include/#">subglobal5
    link</a> | <a href="/include/#">subglobal5 link</a> 
  </div> 
  <div id="subglobal6" class="subglobalNav"> 
    <a href="/include/#">subglobal6 link</a> | <a href="/include/#">subglobal6 link</a> | <a href="/include/#">subglobal6
    link</a> | <a href="/include/#">subglobal6 link</a> | <a href="/include/#">subglobal6 link</a> | <a href="/include/#">subglobal6
    link</a> | <a href="/include/#">subglobal6 link</a> 
  </div> 
  <div id="subglobal7" class="subglobalNav"> 
    <a href="/include/#">subglobal7 link</a> | <a href="/include/#">subglobal7 link</a> | <a href="/include/#">subglobal7
    link</a> | <a href="/include/#">subglobal7 link</a> | <a href="/include/#">subglobal7 link</a> | <a href="/include/#">subglobal7
    link</a> | <a href="/include/#">subglobal7 link</a> 
  </div> 
  <div id="subglobal8" class="subglobalNav"> 
    <a href="/include/#">subglobal8 link</a> | <a href="/include/#">subglobal8 link</a> | <a href="/include/#">subglobal8
    link</a> | <a href="/include/#">subglobal8 link</a> | <a href="/include/#">subglobal8 link</a> | <a href="/include/#">subglobal8
    link</a> | <a href="/include/#">subglobal8 link</a> 
  </div> 
</div> 
<!-- end masthead --> 
<div id="pagecell1"> 
  <!--pagecell1--> 
  <img alt="" src="tl_curve_white.gif" height="6" width="6" id="tl"> <img alt="" src="tr_curve_white.gif" height="6" width="6" id="tr"> 
  <div id="pageName"> 
    <h2><?php echo $titulo; ?></h2> 
    <img alt="small logo" src="" height="59" width="66"/> 
  </div> 
  <div id="pageNav"> 
    <div id="sectionLinks"> 
    <a href="/trans/buses.php">Buses</a> <a href="/trans/conductores.php">Conductores</a>
	<a href="/trans/vayuda.php">Vehiculo de Ayuda</a><a href="/trans/vvalores.php">Vehiculo de Valores</a>
	<a href="/trans/parqueaderos.php">Parqueaderos</a><a href="/trans/ruta.php">Ruta</a><a href="/trans/apublico.php">Atencion al publico</a>
	<a href="/trans/turnos.php">Turnos</a> <a href="/trans/estaciones.php">Estaciones</a> <a href="/trans/repuestos.php">Repuestos</a><a href="/trans/historialbuses.php">Historial del Bus</a>
	</div> 
    <div class="relatedLinks"> 
      <h3>Related Link Category</h3> 
      <a href="/include/#">Related Link</a> <a href="/include/#">Related Link</a> <a href="/include/#">Related
      Link</a> <a href="/include/#">Related Link</a> <a href="/include/#">Related Link</a> <a href="/include/#">Related
      Link</a> 
    </div> 
    <div class="relatedLinks"> 
      <h3>Related Link Category</h3> 
      <a href="/include/#">Related Link</a> <a href="/include/#">Related Link</a> <a href="/include/#">Related
      Link</a> <a href="/include/#">Related Link</a> <a href="/include/#">Related Link</a> <a href="/include/#">Related
      Link</a> 
    </div> 
  </div> 
  <div id="content"> 
    <div class="feature">
<?php
}
// Hola Mundo
function genericfooter() {
?>
      <p>&nbsp;</p> 
    </div> 
    </div> 
   <div id="siteInfo">     <a href="/include/#">About Us</a> | <a href="/include/#">Site
    Map</a> | <a href="/include/#">Privacy Policy</a> | <a href="/include/#">Contact Us</a> | &copy;2005 Company Name
  </div> 
</div> 
<!--end pagecell1--> 
<br> 
<script type="text/javascript">
    <!--
      var menuitem1 = new menu(7,1,"hidden");
			var menuitem2 = new menu(7,2,"hidden");
			var menuitem3 = new menu(7,3,"hidden");
			var menuitem4 = new menu(7,4,"hidden");
			var menuitem5 = new menu(7,5,"hidden");
			var menuitem6 = new menu(7,6,"hidden");
			var menuitem7 = new menu(7,7,"hidden");
    // -->
    </script> 
</body>
</html>
<?php
}
?>