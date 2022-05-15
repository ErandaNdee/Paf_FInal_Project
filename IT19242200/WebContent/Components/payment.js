$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
	$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

//SAVE
$(document).on("click", "#btnSave", function(event)
	{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true)
	{
	$("#alertError").text(status);
	$("#alertError").show();
	return;
	}
	
	// If valid------------------------
	var type = ($("#payID").val() == "") ? "POST" : "PUT";
	$.ajax(
	{
	url : "PaymentAPI",
	type : type,
	data : $("#formItem").serialize(),
	dataType : "text",
	complete : function(response, status)
	{
	onItemSaveComplete(response.responseText, status);
	}
	});
});


function onItemSaveComplete(response, status)
{
	if (status == "success")
	{
	var resultSet = JSON.parse(response);
	if (resultSet.status.trim() == "success")
	{
	$("#alertSuccess").text("Successfully saved.");
	$("#alertSuccess").show();
	$("#divItemsGrid").html(resultSet.data);
	} else if (resultSet.status.trim() == "error")
	{
	$("#alertError").text(resultSet.data);
	$("#alertError").show();
	}
	} else if (status == "error")
	{
	$("#alertError").text("Error while saving.");
	$("#alertError").show();
	} else
	{
	$("#alertError").text("Unknown error while saving..");
	$("#alertError").show();
	}
	$("#payID").val("");
	$("#formItem")[0].reset();
}


$(document).on("click", ".btnUpdate", function(event)
{	
	$("#payID").val($(this).data("itemid"));
	$("#bill_ID").val($(this).closest("tr").find('td:eq(0)').text());
	$("#card_Holder").val($(this).closest("tr").find('td:eq(1)').text());
	$("#card_type").val($(this).closest("tr").find('td:eq(2)').text());
	$("#card_No").val($(this).closest("tr").find('td:eq(3)').text());
	$("#cvv").val($(this).closest("tr").find('td:eq(4)').text());
	$("#amount").val($(this).closest("tr").find('td:eq(5)').text());
	
})


$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
	url : "PaymentAPI",
	type : "DELETE",
	data : "payID=" + $(this).data("itemid"),
	dataType : "text",
	complete : function(response, status)
	{
	onItemDeleteComplete(response.responseText, status);
	}
	});
})


function onItemDeleteComplete(response, status)
{
	if (status == "success")
	{
	var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
		$("#alertSuccess").text("Successfully deleted.");
		$("#alertSuccess").show();
		$("#divItemsGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
		$("#alertError").text(resultSet.data);
		$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
	$("#alertError").text("Error while deleting.");
	$("#alertError").show();
	} 
	else
	{
	$("#alertError").text("Unknown error while deleting..");
	$("#alertError").show();
	}
}





// CLIENT-MODEL================================================================
function validateItemForm()
{
	// PROJECT ID
	if ($("#bill_ID").val().trim() == "")
	
	{
	return "Insert a numerical value for bill ID.";
	}
	if ($("#card_Holder").val().trim() == "")
	{
	return "Insert  card Holder.";
	}
	if ($("#card_type").val().trim() == "")
	{
	return "Insert project card type.";
	}if ($("#card_No").val().trim() == "")
	{
	return "Insert project card_No.";
	}

	
	// REVIEW
	if ($("#cvv").val().trim() == "")
	{
	return "Insert project cvv.";
	}
	
	// ACCEPTANCE
	if ($("#amount").val().trim() == "")
	{
	return "Insert  amount.";
	}
	
	return true;
}