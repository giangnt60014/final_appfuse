function getLink(){
	var freeLink = $("#freeLink").text(); //current free link
	if(freeLink>0){
		var link = $("#link").val(); //link to get direct link
		var account = $("#account").val(); //Chosen account
		var directLink = $.ajax({ url: 'fsaccount/getLink?link='+link+'&account='+account, 
			data: {}, 
			dataType: 'json', 
			async: false,
			cache: false
		}).responseText; //Load Sections from db depends on customerId
		 $("#directLink").val(directLink);
	}else{
		alert("No free link available");
	}
	
}