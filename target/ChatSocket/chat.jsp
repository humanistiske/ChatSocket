<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
body {
  /* magic is here */
  overflow: hidden;
}
#chatHistory {
  position: fixed;
  top: 1%;
  left: 20%;
  width: 60%;
  height: 70%;
  color: #EEEEEE;
  background-color: #1C658C;
  overflow-y: auto;
  -webkit-overflow-scrolling: touch;
  display: flex;
  flex-direction: column-reverse;
  border-radius: 25px;
  border: 10px solid #D8D2CB;
  
  padding-top: 5px;
  padding-right: 10px;
  padding-bottom: 5px;
  padding-left: 10px;
}

#chatHistory span {
  color: #D8D2CB;
}

.site-header {
  width: 100%;
  height: 120px;
  background-color: orange;
}

.main-content {
  height: 200%;
}
</style>
<script type="text/javascript">
var currentKey = 0;

function listen()
{
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange =  function()
	{
		if(this.readyState==4 && this.status==200)
		{
			var response = JSON.parse(this.responseText);
			var count = Object.keys(response).length;
			
			if(count > currentKey)
			{
				currentKey = count;
				for(key in response)
				{
					var keyValue = response[key].split('>>>');
					var user = keyValue[0] + ': ';
					/* var user = '<b>' + keyValue[0] + ': </b>'; */
					document.getElementById("chatHistory").innerHTML +=  user + keyValue[1] + '<br><br>';
				}
			}
		}
	};
	xhttp.open("GET", "http://localhost:8080/ChatSocket/rest/chat/getHistory", true);
	xhttp.send();
}

function onCall()
{
	/* listen(); */
	setInterval(listen, 1000);
}
</script>
</head>
<body onload="onCall();">
	<div id="chatHistory"></div>
</body>
</html>