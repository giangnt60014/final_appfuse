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
		}).responseText;
		 $("#directLink").val(directLink);
		 var d = new Date();
		 document.cookie = "cookiename=1;expires=" + d.toGMTString() + ";" + ";";
	}else{
		alert("No free link available");
	}
}
