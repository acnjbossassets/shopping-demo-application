<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <script src='http://code.jquery.com/jquery-1.11.2.min.js'></script>
    <script>
    
    
    $( document ).ready(function() {
    	
	});


    function showProducts(){
        $('#result').html("");
        $.ajax({ type: "GET",   
                 url: "/route/shoppingApplication/products",   
                 async: false,
                 success : function(text)
                    {
                        alert(text);
                        $.each(text, function(i, item) {
                            alert(item.productCode);
                            $('<tr>').html(
                                "<td>"+(item.productCode)+"</td>"+
                                "<td>"+(item.productName)+"</td>"+
                                "<td>"+(item.productLine)+"</td>"+
                                "<td>"+(item.productDescription)+"</td>"+
                                "<td>"+(item.quantityInStock)+"</td>"+
                                "<td>"+(item.buyPrice)+"</td>"
                                 ).appendTo('#result');
            
                             });
                    $('#result').css("display","block");
                     }
                });
        }



function authenticate(){
		window.open("http://localhost:8080/authorize");
}

function buyProducts(){
        $('#result').html("");
        $.ajax({ type: "GET",   
                 url: "/route/shoppingApplication/products"+document.getElementById('productCode').value+"/buy",   
                 async: false,
                 success : function(text)
                    {alert(text) }
                });
        }
        
function createInputBuyProducts(){
		var productCode  = document.getElementById('productCode').value;
		if(readCookie("userLoggedIn") !=""){
        	productCode+=":notAuthenticated";
        	alert("Buying products requires authentication. You will be directed to the google authentication page now");
        	authenticate();
        }
        $('#userInput').html("");
        $('#userInput').html("<input type='text' id='productCode'/>"+
            "<input type='button' id='buyProducts' value='BUY' onclick='buyProducts()'/>"
            );
    }
</script>
<%
Boolean displayAuthentication = true;
if(request.getSession().getAttribute("displayAuthentication")==null){
    request.getSession().setAttribute("displayAuthentication",true);
}else{
    displayAuthentication = true;
}

%>


</head>
<body>
<h2>Welcome to shopping application!</h2>
<h5>Choose an option</h5>
<input type="button" id="showProducts" value="Show All Products" onclick="showProducts()"/>
<input type="button" id="addProducts" value="Add New Products"/>
<input type="button" id="buyProducts" value="Buy Existing Products" onclick="createInputBuyProducts()"/>

<%  if(displayAuthentication){%>
<input type="button" id="buyProducts" value="Authenticate" onclick="authenticate()"/>
<%}%>

<div id="userInput"/>
<table id="result" border="10" style="display:none"/></table>

<% if(displayAuthentication){%>
<div id="userContactsDetails">
    <%  if(request.getSession().getAttribute("userContacts") != null){%>
        <p>The user has been authenticated following is the contact info</p>

        <%List<String> userContacts = (List<String>)request.getSession().getAttribute("userContacts");
        for (String contact:userContacts ) {%>
                    <div><%= contact%></div>
            <%}
        }
        %>
</div>
<%}%>
</body>
</html>