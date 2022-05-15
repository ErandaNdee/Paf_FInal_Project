<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
     <%@page import = "com.Payment" %>
      
     
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment handling</title>


<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<link rel="stylesheet" href="Views/bootstrap1.min.css"> 
<script src = "Components/jquery-3.6.0.min.js"></script>
<script src = "Components/payment.js"></script>



</head>
<body>

<div class = "container"> 
	<div class="row">
		<div class="col-6">

		
		<h1>Payment Gateway</h1> 
			
	<form id="formItem" name="formItem"  >
		
		
		Bill ID:
		<input id="bill_ID" name="bill_ID" type="text" class="form-control form-control-sm"><br>
		Card Holder Name:
		<input id="card_Holder" name="card_Holder" type="text" class="form-control form-control-sm"><br>
		Card Type:
		<input id="card_type" name="card_type" type="text" class="form-control form-control-sm"><br>
		Card No:
		<input id="card_No" name="card_No" type="text" class="form-control form-control-sm"><br>
		CVV:
		<input id="cvv" name="cvv" type="text" class="form-control form-control-sm"><br>
		 Amount:
		<input id="amount" name="amount" type="text" class="form-control form-control-sm"><br> 
		
		 
		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
		<input type="hidden" id="payID" name="payID" value="">
	</form>
    <div id="alertSuccess" class="alert alert-success"></div>
     <div id="alertError" class="alert alert-danger"></div>
    
    <br>
	<div id="divItemsGrid">
	<%
			Payment itemObj = new Payment();
			out.print(itemObj.readPay());
	%>
	</div>

<br>


</div>
</div>
</div>


</body>
</html>