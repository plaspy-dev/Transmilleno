<?php
$mobile_browser = '0';

//$_SERVER['HTTP_USER_AGENT'] -> el agente de usuario que está accediendo a la página.
//preg_match -> Realizar una comparación de expresión regular

if(preg_match('/(up.browser|up.link|mmp|symbian|smartphone|midp|wap|phone)/i',strtolower($_SERVER['HTTP_USER_AGENT']))){
    $mobile_browser++;
}

//$_SERVER['HTTP_ACCEPT'] -> Indica los tipos MIME que el cliente puede recibir. 
if((strpos(strtolower($_SERVER['HTTP_ACCEPT']),'application/vnd.wap.xhtml+xml')>0) or
    ((isset($_SERVER['HTTP_X_WAP_PROFILE']) or isset($_SERVER['HTTP_PROFILE'])))){
    $mobile_browser++;
}

$usuario = $_SERVER['HTTP_USER_AGENT']; //Con esta leemos la info de su navegador
$usuarios_moviles = "Android, AvantGo, Blackberry, Blazer, Cellphone, Danger, DoCoMo, EPOC, EudoraWeb, Handspring, HTC, Kyocera, LG, MMEF20, MMP, MOT-V, Mot, Motorola, NetFront, Newt, Nokia, Opera Mini, Palm, Palm, PalmOS, PlayStation Portable, ProxiNet, Proxinet, SHARP-TQ-GX10, Samsung, Small, Smartphone, SonyEricsson, SonyEricsson, Symbian, SymbianOS, TS21i-10, UP.Browser, UP.Link, WAP, webOS, Windows CE, hiptop, iPhone, iPod, portalmmm, Elaine/3.0, OPWV"; //En esta cadena podemos quitar o agregar navegadores de dispositivos moviles, te recomiendo que hagas un echo $_SERVER['HTTP_USER_AGENT']; en otra pagina de prueba y veas la info que arroja para que despues agregues el navegador que quieras detectar
 
$navegador_usuario = explode(',',$usuarios_moviles);
 
   foreach($navegador_usuario AS $navegador){ //Este ciclo es el que se encarga de detectar el navegador y devolver un TRUE si encuentra la cadena

      if(strpos(trim($usuario),trim($navegador))){
         $mobile_browser++;
	 break;
      }
   }

$mobile_ua = strtolower(substr($_SERVER['HTTP_USER_AGENT'],0,4));
$mobile_agents = array(
    'w3c ','acs-','alav','alca','amoi','audi','avan','benq','bird','blac',
    'blaz','brew','cell','cldc','cmd-','dang','doco','eric','hipt','inno',
    'ipaq','java','jigs','kddi','keji','leno','lg-c','lg-d','lg-g','lge-',
    'maui','maxo','midp','mits','mmef','mobi','mot-','moto','mwbp','nec-',
    'newt','noki','oper','palm','pana','pant','phil','play','port','prox',
    'qwap','sage','sams','sany','sch-','sec-','send','seri','sgh-','shar',
    'sie-','siem','smal','smar','sony','sph-','symb','t-mo','teli','tim-',
    'tosh','tsm-','upg1','upsi','vk-v','voda','wap-','wapa','wapi','wapp',
    'wapr','webc','winw','winw','xda','xda-');

//buscar agentes en el array de agentes
if(in_array($mobile_ua,$mobile_agents)){
    $mobile_browser++;
}

//$_SERVER['ALL_HTTP'] -> Todas las cabeceras HTTP
//strpos -> Primera aparicion de una cadena dentro de otra
if(strpos(strtolower($_SERVER['ALL_HTTP']),'OperaMini')>0) {
    $mobile_browser++;
}
if(strpos(strtolower($_SERVER['HTTP_USER_AGENT']),'windows')>0) {
    $mobile_browser=0;
}

if($mobile_browser>0){
        // Mostrar contenido para dispositivos móviles
        // Estos pueden ser más ligeros: un titulo, resumen y algunos enlaces.
        // Aca puede redirigir a la ruta donde este el contenido para moviles
        // Por ejemplo: https://service.example.invalid/movil ó https://service.example.invalid
    header('Location: m.html');
} else {
    header('Location: mapa.html');
}
?>