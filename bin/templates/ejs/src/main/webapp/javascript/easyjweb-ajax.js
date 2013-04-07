<!--

// IE support
if (window.ActiveXObject && !window.XMLHttpRequest)
{
	window.XMLHttpRequest = function()
		{
			var msxmls = new Array('Msxml2.XMLHTTP.5.0','Msxml2.XMLHTTP.4.0','Msxml2.XMLHTTP.3.0','Msxml2.XMLHTTP','Microsoft.XMLHTTP');
			for (var i = 0; i < msxmls.length; i++)
			{
				try 
				{
					return new ActiveXObject(msxmls[i]);
				} 
				catch (e)
				{
				}
			}
			return null;
		};
}

// Gecko support
/* ;-) */
// Opera support  
//	Implement a ajax class
if (window.opera && !window.XMLHttpRequest)
{
	window.XMLHttpRequest = function()
	{
		this.readyState = 0; // 0=uninitialized,1=loading,2=loaded,3=interactive,4=complete
		this.status = 0; // HTTP status codes
		this.statusText = '';
		this._headers = [];
		this._aborted = false;
		this._async = true;
		this._defaultCharset = 'UTF-8';
		this._getCharset = function() 
		{
			var charset = _defaultCharset;
			var contentType = this.getResponseHeader('Content-type').toUpperCase();
			val = contentType.indexOf('CHARSET=');
			if (val != -1) 
			{
				charset = contentType.substring(val);
			}
			val = charset.indexOf(';');
			if (val != -1)
			{
				charset = charset.substring(0, val);
			}
			val = charset.indexOf(',');
			if (val != -1)
			{
				charset = charset.substring(0, val);
			}
			return charset;
		};

		this.abort = function()
		{
			this._aborted = true;
		};
		
		this.getAllResponseHeaders = function()
		{
			return this.getAllResponseHeader('*');
		};
		
		this.getAllResponseHeader = function(header)
		{
			var ret = '';
			for (var i = 0; i < this._headers.length; i++)
			{
				if (header == '*' || this._headers[i].h == header)
				{
					ret += this._headers[i].h + ': ' + this._headers[i].v + '\n';
				}
			}
			return ret;
		};
		
		this.getResponseHeader = function(header)
		{
			var ret = getAllResponseHeader(header);
			var i = ret.indexOf('\n');
			if (i != -1)
			{
				ret = ret.substring(0, i);
			}
			return ret;
		};
		
		this.setRequestHeader = function(header, value)
		{
			this._headers[this._headers.length] = {h:header, v:value};
		};

		this.open = function(method, url, async, user, password)
		{
			this.method = method;
			this.url = url;
			this._async = true;
			this._aborted = false;
			this._headers = [];
			if (arguments.length >= 3) 
			{
				this._async = async;
			}
			if (arguments.length > 3) 
			{
				opera.postError('XMLHttpRequest.open() - user/password not supported');
			}
			this.readyState = 1;
			if (this.onreadystatechange)
			{
				this.onreadystatechange();
			}
		};
		
		this.send = function(data)
		{
			if (!navigator.javaEnabled())
			{
				alert("XMLHttpRequest.send() - Java must be installed and enabled.");
				return;
			}
			if (this._async)
			{
				setTimeout(this._sendasync, 0, this, data);
			} 
			else
			{
				this._sendsync(data);
			}
		};
		
		this._sendasync = function(req, data)
		{
			if (!req._aborted)
			{
				req._sendsync(data);
			}
		};
		
		this._sendsync = function(data)
		{
			this.readyState = 2;
			if (this.onreadystatechange)
			{
				this.onreadystatechange();
			}
			var url = new java.net.URL(new java.net.URL(window.location.href), this.url);
			var conn = url.openConnection();
			for (var i = 0; i < this._headers.length; i++)
			{
				conn.setRequestProperty(this._headers[i].h, this._headers[i].v);
			}
			this._headers = [];
			if (this.method == 'POST')
			{
			// POST data
				conn.setDoOutput(true);
				var wr = new java.io.OutputStreamWriter(conn.getOutputStream(), this._getCharset());
				wr.write(data);
				wr.flush();
				wr.close();
			}
			// read response headers
			// NOTE: the getHeaderField() methods always return nulls for me :(
			var gotContentEncoding = false;
			var gotContentLength = false;
			var gotContentType = false;
			var gotDate = false;
			var gotExpiration = false;
			var gotLastModified = false;
			for (var i = 0; ; i++)
			{
				var hdrName = conn.getHeaderFieldKey(i);
				var hdrValue = conn.getHeaderField(i);
				if (hdrName == null && hdrValue == null)
				{
					break;
				}
				if (hdrName != null)
				{
					this._headers[this._headers.length] = {h:hdrName, v:hdrValue};
					switch (hdrName.toLowerCase())
					{
						case 'content-encoding': gotContentEncoding = true; break;
						case 'content-length' : gotContentLength = true; break;
						case 'content-type' : gotContentType = true; break;
						case 'date' : gotDate = true; break;
						case 'expires' : gotExpiration = true; break;
						case 'last-modified' : gotLastModified = true; break;
					}
				}
			}
			// try to fill in any missing header information 
			//尝试补充任何丢失的头信息
			var val;
			val = conn.getContentEncoding();
			if (val != null && !gotContentEncoding) this._headers[this._headers.length] = {h:'Content-encoding', v:val};
			val = conn.getContentLength();
			if (val != -1 && !gotContentLength) this._headers[this._headers.length] = {h:'Content-length', v:val};
			val = conn.getContentType();
			if (val != null && !gotContentType) this._headers[this._headers.length] = {h:'Content-type', v:val};
			val = conn.getDate();
			if (val != 0 && !gotDate) this._headers[this._headers.length] = {h:'Date', v:(new Date(val)).toUTCString()};
			val = conn.getExpiration();
			if (val != 0 && !gotExpiration) this._headers[this._headers.length] = {h:'Expires', v:(new Date(val)).toUTCString()};
			val = conn.getLastModified();
			if (val != 0 && !gotLastModified) this._headers[this._headers.length] = {h:'Last-modified', v:(new Date(val)).toUTCString()};
			// read response data
			var reqdata = '';
			var stream = conn.getInputStream();
			if (stream)
			{
				var reader = new java.io.BufferedReader(new java.io.InputStreamReader(stream, this._getCharset()));
				var line;
				while ((line = reader.readLine()) != null)
				{
					if (this.readyState == 2)
					{
						this.readyState = 3;
						if (this.onreadystatechange)
						{
							this.onreadystatechange();
						}
					}
					reqdata += line + '\n';
				}
				reader.close();
				this.status = 200;
				this.statusText = 'OK';
				this.responseText = reqdata;
				this.readyState = 4;
				if (this.onreadystatechange)
				{
					this.onreadystatechange();
				}
				if (this.onload)
				{
					this.onload();
				}
			}
			else
			{
				// error
				this.status = 404;
				this.statusText = 'Not Found';
				this.responseText = '';
				this.readyState = 4;
				if (this.onreadystatechange)
				{
					this.onreadystatechange();
				}
				if (this.onerror)
				{
					this.onerror();
				}
			}
		};
	};
}

// ActiveXObject emulation
if (!window.ActiveXObject && window.XMLHttpRequest)
{
	window.ActiveXObject = function(type)
	{
		switch (type.toLowerCase())
		{
			case 'microsoft.xmlhttp':
				case 'msxml2.xmlhttp':
				case 'msxml2.xmlhttp.3.0':
				case 'msxml2.xmlhttp.4.0':
				case 'msxml2.xmlhttp.5.0':
				return new XMLHttpRequest();
		}
		return null;
	};
}

<!--
//
// Function to limit key presses for phone numbers,
// zip codes, letters, etc.  To use:
// <input ... onKeyPress="return limit_keys( this, 'numeric', event )">
//
function limit_keys(selectObj, type, evt) {
    var keyCode = 0;
    var ret_val = true;
    if (evt) {
        keyCode = evt.keyCode || evt.which;
    } else {
    // The old version of this file did not use the evt parameter
    // and would only work under IE.
        keyCode = window.event.keyCode;
    }

  // Allow special characters: BACKSPACE, TAB, RETURN, LEFT ARROW,
  // RIGHT ARROW to go through
    if ((keyCode == 8) || (keyCode == 9) || (keyCode == 13) || (keyCode == 37) || (keyCode == 39)) {
        return (ret_val);
    }
    if (type == "phone") {
    // Numeric values and punctuation are OK
        ret_val = test_keycode("0123456789()-.", keyCode);
    } else {
        if (type == "alphanum") {
            ret_val = ((keyCode >= 48) && (keyCode <= 57)) || ((keyCode >= 65) && (keyCode <= 90)) || ((keyCode >= 97) && (keyCode <= 122));
        } else {
            if (type == "numeric") {
    // Simply test for a numeric value
                ret_val = ((keyCode >= 48) && (keyCode <= 57));
            }
        }
    }
    return (ret_val);
} // End of limit_keys()

// Check if any broker checkboxes are selected
onChange=function(){
var len = formObj.elements.length;
var i = 0;
while (i < len) {
    var e = formObj.elements[i];
    if (e.name.substring(0, 3) == "${ $agg_prefix }") {
        if (e.checked) {
            any_broker = true;
        // Highlight this selection
            colorTR((e.parentNode).parentNode, "#6699ff", "#ffffff");
        } else {
            if (i % 2) {
                colorTR((e.parentNode).parentNode, "#f0f0f0", "#000000");
            } else {
                colorTR((e.parentNode).parentNode, "#ffffff", "#000000");
            }
        }
    }
    i++;
}		
}




/*
function hidden(elementID){
	//alert(elementID);
	var c=document.getElementById(elementID);
	if(c){
		c.style.display="none";
	}
	//c=null;
}
function display(elementID){
	var c=document.getElementById(elementID);
	if(c){
		c.style.display="block";
	}
	//c=null;
}

function set(elementID,text){
	display(elementID);
	var c=document.getElementById(elementID);
	if(c){	
	
		if(c.innerHTML){
			c.innerHTML=text;
		}
	}
	//c=null;
}	  
function $$$(elementID,text){
	display(elementID);
	var c=document.getElementById(elementID);
	if(c){	
	
		if(c.innerHTML){
			c.innerHTML=text;
		}
	}
	//c=null;
}
*/



-->
