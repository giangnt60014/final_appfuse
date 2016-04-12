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
		var json;
		var shortLink = $.ajax({ url: 'https://api.shorte.st/v1/data/url', 
						headers: {'public-api-token': 'ac1924174f0cf4f064521cf61f066e14'},
						type: 'PUT',
						data: 'urlToShorten='+directLink,
						dataType: 'application/json',
					    complete: function(data){
					    	$("#directLink").val(JSON.parse(data.responseText).shortenedUrl);
					    	$("#drLink").text(JSON.parse(data.responseText).shortenedUrl);
							$("#drLink").attr("href", JSON.parse(data.responseText).shortenedUrl);
					    }
					});
		 var d = new Date();
		 document.cookie = "cookiename=1;expires=" + d.toGMTString() + ";" + ";";
	}else{
		alert("No free link available");
	}
}
