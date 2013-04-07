COMMON={
	debug:false,
	external:function(){
		if (!document.getElementsByTagName) return; 
		var anchors = document.getElementsByTagName("a"); 
		for (var i=0; i<anchors.length; i++) {
			var anchor = anchors[i]; 
			if (anchor.getAttribute("href") && anchor.getAttribute("rel") == "external") 
				anchor.target = "_blank"; 
		}
	},
	$:function(element){
		return document.getElementById(element);
	},
	$$:function(element){
		return document.getElementsByTagName(element);
	},
	windowInit : function(){
		var b=COMMON.$$("input");
		for(i=0;i<b.length;i++){
			if(b[i].type=="text"){
				if(b[i].select())b[i].select();
				b[i].focus();
				return;
			}
		}
	},
	fontZoom:function(element,size){
		return COMMON.$(element).style.fontSize=size+'px';
	},
	/**
    *@author WilliamRaym in Easyjf China 2007-5-29
    *function name : addLoadEvent
    *return : an array of event from your current browser
    *parameter : function
    *usage : EJF.addLoadEvent(function(){alert('OK,I am added to onload');};)
    */
	addLoadEvent : function(f){
			if (this.debug)alert("Added a function to load event \r\n\r\n"+f.toString());
	        if((typeof f!="function")&&(typeof f!="object")){alert(f+"\u4e0d\u662f\u51fd\u6570\u6216\u5bf9\u8c61,\u53ea\u80fd\u6dfb\u52a0\u4e00\u4e2a\u51fd\u6570\u6216\u5bf9\u8c61");return;};
	        var o=window.onload;
	        if(typeof o!="function"){
	            window.onload=f;
	        }else{
	            window.onload=function(){
	                if(o){
	                    o();
	                }
	                f();
	            }
	        }
	    },

    /**
    *@author WilliamRaym in cqbjh China 2007-5-29
    *function name : addEvent
    *return : get an available event from your current browser
    *parameter : no
    *usage : EJFHTML.addEvent();
	# document.addEventListener('load',singlehandler,false): MSN/OSX, op7.50, saf1.2, ow5b6.1
	# window.addEventListener('load',singlehandler,false): moz, saf1.2, ow5b6.1
	# document.attachEvent('onload',singlehandler): op7.50
	# window.attachEvent('onload',singlehandler): op7.50, ie5.0w,
	*/
    addEvent : function(){
        var eventArray=new Array(document.addEventListener,window.addEventListener,document.attachEvent,window.attachEvent);
        for(var i=0;i<eventArray.length;i++){
			
            try{
				if(this.debug){
					alert(eventArray[i]);
				}
								
                if((typeof eventArray[i]=="object")||(typeof eventArray[i]=="function")){
                    return eventArray[i];
                }
            }catch(e){
                alert("\u60a8\u7684\u6d4f\u89c8\u5668\u4e0d\u652f\u6301\u4efb\u4f55\u4e8b\u4ef6\u51fd\u6570");
            }
        }
    },
    /**
    @author WilliamRaym in Easyjf China 2007-11-1
    function name: addFavorite
    */
    addFavorite:function(url,name){
		if (document.all)
		{
		  window.external.addFavorite(url,name);
		}
		else if (window.sidebar)
		{
		  window.sidebar.addPanel(name,url, "");
		}
		return false;
	},
    /**
    @author WilliamRaym in Easyjf China 2007-11-1
    function name: setHomePage
    use to invoke
    */
	setHomePage:function(obj,url){
		if(document.all){
			obj.style.behavior='url(#default#homepage)';obj.setHomePage(url); 
		}else if(window.netscape){
		try {
		netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
		
		
		var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
		prefs.setCharPref('browser.startup.homepage',url);
		}
			catch (e) {
				alert(e);
				return false;
			}
		}
		return false;
	}    
};
