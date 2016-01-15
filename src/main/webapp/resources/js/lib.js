
var SkinCookieName = "myskin";
setskin();

function setskin(){
    var thisskin = "blue";
    var cookieSkin=GetCookie(SkinCookieName);	
    if(cookieSkin!="")
        thisskin = cookieSkin;

    var reg = /\/.*\//; 
  
   // skinb.href = skinb.href.replace(reg, "/../skin/"+thisskin+"/css/");
	
    var cssArray=document.getElementsByTagName('link');
	for(var i=0;i<cssArray.length;i++){
		if(cssArray[i].href!=null && cssArray[i].href!=''){
			cssArray[i].href = cssArray[i].href.replace(/skin\/[a-zA-Z]*\/css/g, "skin/"+thisskin+"/css");
		}
	}
	document.onreadystatechange = subSomething;//当页面加载状态改变的时候执行这个方法.
	function subSomething(){
		var imgArray=document.getElementsByTagName("img");
		
		if(document.readyState == "complete") //当页面加载状态为完全结束时进入
	
			for(var i=0;i<imgArray.length;i++){
				if(imgArray[i].src!=null && imgArray[i].src!=''){
					imgArray[i].src = imgArray[i].src.replace(/skin\/[a-zA-Z]*\/images/g, "skin/"+thisskin+"/images");
				}
			}
		
	}
	
}

function changecss(url){
    if(url!=""){
        var expdate=new Date();
        expdate.setTime(expdate.getTime()+(24*60*60*1000*30));//+(24*60*60*1000*30)
        SetCookie(SkinCookieName,url,expdate,"/",null,false);
        setskin();
    }
}
function SetCookie(name,value){
    var argv=SetCookie.arguments;
    var argc=SetCookie.arguments.length;
    var expires=(2<argc)?argv[2]:null;
    var path=(3<argc)?argv[3]:null;
    var domain=(4<argc)?argv[4]:null;
    var secure=(5<argc)?argv[5]:false;
    document.cookie=name+"="+escape(value)+((expires==null)?"":("; expires="+expires.toGMTString()))+((path==null)?"":("; path="+path))+((domain==null)?"":("; domain="+domain))+((secure==true)?"; secure":"");
}

function GetCookie(Name) {
    var search = Name + "=";
    var returnvalue = "";
    if (document.cookie.length > 0)
    {
        offset = document.cookie.indexOf(search);
        if (offset != -1) 
        { 
            offset += search.length;
            end = document.cookie.indexOf(";", offset); 
            if (end == -1)
                end = document.cookie.length;
                
            returnvalue=unescape(document.cookie.substring(offset,end));
        }
    }
    return returnvalue;
}