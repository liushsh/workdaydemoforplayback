<!DOCTYPE html>
<html lang="en">
<head>
<title>Global | Contacts</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" media="screen" href="css/reset.css">
<link rel="stylesheet" type="text/css" media="screen" href="css/style.css">
<link rel="stylesheet" type="text/css" media="screen" href="css/grid_12.css">



<style type="text/css">.thumb-image{width:200px;position:relative;padding:3px;}</style>
<link href='http://fonts.googleapis.com/css?family=Condiment' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>
<script src="js/jquery-1.7.min.js"></script>
<script src="js/jquery.easing.1.3.js"></script>
<script type = "text/javascript"  src = "js/util.js"></script>
<script type = "text/javascript"  src = "js/index.js"></script>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.10/css/jquery.dataTables.min.css">


<script type = "text/javascript"  src = "jquery/jquery-1.12.0.min.js"></script>
<script type = "text/javascript" src = "https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<style>



</style>
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="css/ie.css">
<![endif]-->
<script>
//alert("hello");
var searchKey = '<%= request.getParameter("searchkey") %>';



//alert(username);
//alert(token);
(function($)
{
    var oldHtml = $.fn.html;
    $.fn.html = function()
    {
        var ret = oldHtml.apply(this, arguments);

        //trigger your event.
        this.trigger("change");

        return ret;
    };
})(jQuery);
</script>
<script>



function showSearchResult()
{
    
    //alert(customerAccountServiceEndPoint);
    //http://localhost:8080/customeraccountservice/api/redisprofile/search?key=Shane%20Billy
	xhrGet(customerAccountServiceEndPoint+'/api/redisprofile/search?key='+searchKey,function(data){
		
		
		//alert(data.hits.total);
		//alert(data.hits.hits[0]._source.birthdate);
		var output = "";
		var totalHit = parseInt(data.hits.total);
		if (totalHit==0)
		{
			output = '<b>Not found</b>';
		}
		else
		{
			output = output + "<b>Search results</b>"+
			"<table id=\"summary_table\" class=\"display\" cellspacing=\"0\" width=\"100%\">"+
			"<thead>"+
			"	<tr>"+
			"		<th>Name</th>"+
			"		<th>Birth Date</th>"+
			"		<th>Email</th>"+
			"		<th>Phone</th>"+
			"	</tr>"+
			"</thead>";
				
		        	for (var i = 0, len = totalHit; i < len; ++i) {
		            	var row = data.hits.hits[i]._source;
		            	//temp = row.birthdate;
		            	//date_temp = moment(temp).format("YYYY-MM-DD");
		            	//tblRow = "<tr><td class='border'><a href='#' onclick=\"return showResultDetails('" + row.test_date + "');\">" + row.test_date + "</a></td>";
		            	var detailedURL = "redisprofile.jsp?username="+row.key+'&token='+row.email+"Qllygd_1";
		            	tblRow = "<tr><td class='border'><a href='"+detailedURL+"'>" + row.firstname+" "+row.lastname + "</a></td>";
		                tblRow += "<td class='border'>" + row.birthdate + "</td>";
		                tblRow += "<td class='border'>" + row.email + "</td>";
		                tblRow += "<td class='border'>" + row.mobilephone + "</td></tr>";
		                output = output + tblRow; 	
		            }
		         	output = output +"</table>";
		        	
		            
		       
		        
		        
		
		
		//alert("Quyen oi oi " + data.filename);
		//alert("Quyen toan bo" + data.body);
		/* document.getElementById("maincontent").innerHTML = ""+
		"<div class=\"block-5\">"+
        "    <h3 class=\"p5\">"+ data.firstname + " " + data.lastname + "</h3>"+
        "    <div class=\"box-shadow\">"+
        "    <img src=\""+customerAccountServiceEndPoint+"/photos/"+data.photo+"\" class= \"thumb-image\"></div>"+
        
        ""+   "<dl>"+
        "      <dt>" + data.address +","+"<br>"+
        ""+        data.city+ "," + data.state +" "+data.zip+"."+"</dt>"+
        ""+      "<dd><span>Cell phone: </span>" +data.mobilephone+"</dd>"+
        ""+      "<dd><span>Work phone: </span>"+data.workphone+"</dd>"+
        ""+      "<dd><span>E-mail: </span><a href=\"#\" class=\"link\">"+data.email+"</a></dd>"+
        ""+    "</dl>"+
        "  </div>"+
        "  <div class=\"block-6\">"+
        "    <h3> </h3>"+
        "    <table style=\"border-collapse: separate; border-spacing: 15px;\">"+
        "      <tr>"+
        "      <td>First Name:</td><td>"+data.firstname+"</td>"+
        "      </tr>"+
        "      <tr>"+
        "      <td>Last Name:</td><td>"+data.lastname+"</td>"+
        "      </tr>"+
        "      <tr>"+
        "      <td>Address:</td><td>"+data.address+"</td>"+
        "      </tr>"+  
        "      <tr>"+
        "      <td>City:</td><td>"+data.city+"</td>"+
        "      </tr>"+
        "      <tr>"+
        "      <td>State:</td><td>"+data.state+"</td>"+
        "      </tr>"+
        "      <tr>"+
        "      <td>Zip:</td><td>"+data.zip+"</td>"+
        "      </tr>"+
        "      <tr>"+
        "      <td>Gender:</td><td>"+data.gender+"</td>"+
        "      </tr>"+  
        "      <tr>"+
        "      <td>Birth Date:</td><td>"+data.birthdate+"</td>"+
        "      </tr>"+
        
        "      </tr>"+  
        "      <tr>"+
        "      <td>Cell Phone:</td><td>"+data.mobilephone+"</td>"+
        "      </tr>"+
        
        "      </tr>"+  
        "      <tr>"+
        "      <td>Home Phone:</td><td>"+data.homephone+"</td>"+
        "      </tr>"+
        
        "      </tr>"+  
        "      <tr>"+
        "      <td>Work Phone:</td><td>"+data.workphone+"</td>"+
        "      </tr>"+
        
        "      </tr>"+  
        "      <tr>"+
        "      <td>Email:</td><td>"+data.email+"</td>"+
        "      </tr>"+
        
        "    </table>"+
        "  </div>"; */
	 }	
	 document.getElementById("maincontent").innerHTML = output;
	 $('#summary_table').DataTable();
	}, function(err){
		console.log(err);
		
		
	});
}




$(document).ready(function() {
    getProperties();
    // menu
     $('.nav li').hover(
      function () { //appearing on hover
        $('ul', this).fadeIn();
      },
      function () { //disappearing on hover
        $('ul', this).fadeOut();
      }
    );    
    // end menu
    //showProfileEx();
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
		showSearchResult();
		
		
		
		
	}, function(err){
		console.log(err);
		
		
	});
    
    	
});   

                                                                                                        
</script>
</head>

<body>
<div class="main">
  <!--==============================header=================================-->
  <header>
    <h1><a href="index.html"><img src="images/logo.png" alt=""></a></h1>
    <div class="form-search">
      <form id="form-search" method="post" action="#">
        <input id="searchkey" name="searchkey" type="text" value="People name..." onBlur="if(this.value=='') this.value='People name...'" onFocus="if(this.value =='People name...' ) this.value=''"  />
        <a href="javascript:search()" class="search_button" id="search_submit"></a>
      </form>
    </div>
    <div class="clear"></div>
    <nav class="box-shadow">
      <div>
        <ul class="menu">
          <li class="home-page"><a href="index.html"><span></span></a></li>
          <li><a href="about.html">About</a></li>
          <li><a href="services.html">Services</a></li>
          <li><a href="contacts.html">Contacts</a></li>
        </ul>
        <div class="social-icons"> <span>Follow us:</span> <a href="#" class="icon-3"></a> <a href="#" class="icon-2"></a> <a href="#" class="icon-1"></a> </div>
        <div class="clear"></div>
      </div>
    </nav>
  </header>
  <!--==============================content================================-->
  <section id="content">
    <div class="container_12">
      <div class="grid_12">
        <div class="wrap pad-3" id="maincontent">
          
        </div>
      </div>
      <div class="clear"></div>
    </div>
  </section>
</div>
<!--==============================footer=================================-->
<footer>
  <p>© 2016 IBM Usecase team 4400 North First street, San Jose, CA 95134 </p>
  <p>Designed by <a target="_blank" href="http://www.google.com/" class="link">Quyen Tran</a></p>
</footer>
</body>
</html>
