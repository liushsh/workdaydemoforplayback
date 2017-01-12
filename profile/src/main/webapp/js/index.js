var customerAccountServiceEndPoint = "";
var redisDB = "";
var elasticSearchEndPoint = "";
var adminUserName = "";
var adminPassword = "";

function search()
{
	//alert("Search ne");
	window.location.href = "search.jsp?searchkey="+document.forms["form-search"]["searchkey"].value;
}

function getCustomerAccountServiceEndPoint()
{
    
    //headerTemp.value = "Bearer xyz";
    //alert("Quyen oi");
	xhrGet('webapi/properties/customerAccountServiceEndPoint',function(data){
		
		
		
		var receivedItems = data.body || [];
		var items = [];
		var i;
		// Make sure the received items have correct format
		for(i = 0; i < receivedItems.length; ++i){
			var item = receivedItems[i];
//			alert(item);
			if(item && 'id' in item){
				items.push(item);
			}
		}
		//alert("Quyen toan bo");
		customerAccountServiceEndPoint = data.customerAccountServiceEndPoint;
		//alert("Quyen customerAccountServiceEndPoint "+customerAccountServiceEndPoint);
		
		
		
	}, function(err){
		console.log(err);
		
		
	});
}

function getElasticSearchEndPoint()
{
    
    //headerTemp.value = "Bearer xyz";
    //alert("Quyen oi");
	xhrGet('webapi/properties/elasticSearchEndPoint',function(data){
		
		
		
		var receivedItems = data.body || [];
		var items = [];
		var i;
		// Make sure the received items have correct format
		for(i = 0; i < receivedItems.length; ++i){
			var item = receivedItems[i];
//			alert(item);
			if(item && 'id' in item){
				items.push(item);
			}
		}
		//alert("Quyen toan bo");
		elasticSearchEndPoint = data.elasticSearchEndPoint;
		//alert("Quyen customerAccountServiceEndPoint "+customerAccountServiceEndPoint);
		
		
		
	}, function(err){
		console.log(err);
		
		
	});
}

function getProperties()
{
    
    //headerTemp.value = "Bearer xyz";
    //alert("Quyen oi");
	xhrGet('webapi/properties',function(data){
		
		
		
		var receivedItems = data.body || [];
		var items = [];
		var i;
		// Make sure the received items have correct format
		for(i = 0; i < receivedItems.length; ++i){
			var item = receivedItems[i];
//			alert(item);
			if(item && 'id' in item){
				items.push(item);
			}
		}
		//alert("Quyen toan bo");
		customerAccountServiceEndPoint = data.customerAccountServiceEndPoint;
		redisDB = data.redisDB;
		elasticSearchEndPoint = data.elasticSearchEndPoint;
		adminUserName = data.adminUserName;
		adminPassword = data.adminPassword;
		//alert("Quyen customerAccountServiceEndPoint "+customerAccountServiceEndPoint);
		
		
		
	}, function(err){
		console.log(err);
		
		
	});
}

function getProperty(property)
{
    
    //headerTemp.value = "Bearer xyz";
    //alert("Quyen oi");
	var result = "";
	xhrGet('webapi/properties/'+property,function(data){
		
		
		
		var receivedItems = data.body || [];
		var items = [];
		var i;
		// Make sure the received items have correct format
		for(i = 0; i < receivedItems.length; ++i){
			var item = receivedItems[i];
//			alert(item);
			if(item && 'id' in item){
				items.push(item);
			}
		}
		//alert("Quyen toan bo");
		result = data["'"+property+"'"];
		//alert("Quyen customerAccountServiceEndPoint "+customerAccountServiceEndPoint);
		
		
		
	}, function(err){
		console.log(err);
		
		
	});
	
	return result;
}

function redisCreateProfile(customerAccountServiceEndPoint)
{
	
	var file = $('input[type=file]')[0].files[0];
	//alert(""+ document.getElementById("fileUpload").value);
	//if file not selected, throw error
	//var file = "";
	if(!file)
	{
		alert("File not selected for upload... \t\t\t\t \n\n - Choose a file to upload. \n - Then click on Upload button.");
		return;
	}
	
	//var row = node.parentNode.parentNode;
	//var formElement = document.querySelector("form");
	//var form = new FormData(formElement);
	var form = new FormData();
	form.append("file", file);
	
	//var id = row.getAttribute('data-id');
	
	//make queryParams
	var id =  $("#email").val()+':'+$("#password").val();
	var password =  $("#password").val();
	var firstName =  $("#firstname").val();
	var lastName =  $("#lastname").val();
	var address =  $("#address").val();
	var city =  $("#city").val();
	var state =  $("#state").val();
	var zip =  $("#zip").val();
	var homePhone =  $("#homephone").val();
	var workPhone =  $("#workphone").val();
	var mobilePhone =  $("#mobilephone").val();
	var email =  $("#email").val();
	var gender =  $("#gender").val();
	var birthDate =  $("#birthdate").val();
	var fileName = file.name;

	var queryParams = "id=" + id;
	queryParams+= "&firstname="+firstName;
	queryParams+= "&lastname="+lastName;
	queryParams+= "&address="+address;
	queryParams+= "&city="+city;
	queryParams+= "&state="+state;
	queryParams+= "&zip="+zip;
	queryParams+= "&homephone="+homePhone;
	queryParams+= "&workphone="+workPhone;
	queryParams+= "&mobilephone="+mobilePhone;
	queryParams+= "&email="+email;
	queryParams+= "&gender="+gender;
	queryParams+= "&birthdate="+birthDate;
	queryParams+= "&password="+password;
	queryParams+= "&filename="+fileName;
	
	//var table = row.firstChild.nextSibling.firstChild;	
	//var newRow = addNewRow(table);	
	
	//startProgressIndicator(newRow);
	var fullURL = customerAccountServiceEndPoint+"/api/redisattach?"+queryParams;
	//alert("full URL "+fullURL);
	xhrAttach(fullURL, form, function(item){
		$("#register_error_div").html("Profile has been registered successfully.");
		//$('#register_form')[0].reset(); // To reset form fields
	}, function(err){
		console.log(err);
		showErrorMsg("Could not register the profile.");
		$('#form')[0].reset(); // To reset form fields
	});
	
}