$( document ).ready(function() {
	$("#link").change(function(){
            $("#submit").removeAttr('disabled');
    });
});
function getLink() {
	var freeLink = localStorage.getItem('linkCount');
	freeLink = freeLink ? parseInt(freeLink) : 10;
	if (freeLink > 1) {
		var link = $("#link").val(); // link to get direct link
		if (validate(link)) {
			getLinkGeneral(link,null);
			localStorage.setItem('linkCount', --freeLink);
		}else{
			$("#errorMessage").css('visibility', 'visible');
		}
	} 
}

function getLinkList(id) {
	var link = $("#link").attr("href"); // link to get direct link
	getLinkGeneral(link,id);
}

function getLinkReg() {
	var link = $("#link").val(); // link to get direct link
	if (validate(link)) {
		getLinkGeneral(link,null);
	}
}

function validate(link) {
	if(/^(https:\/\/www)\.(?:[^\.]+\.)?fshare\.vn\/file\/.*$/.test(link)){
		$("#errorMessage").css('display', 'none');
		$("#fieldSetErr").removeClass("error");
		return true;
	}else{
		$("#errorMessage").css('display', '');
		$("#fieldSetErr").addClass("error");
		return false;
	}
	return ;
}
function getLinkGeneral(link,id){
	var account = $("#account").val(); // Chosen account
	var directLink = $.ajax({
		url : 'fsaccount/getLink?link=' + link + '&account=' + account,
		data : {},
		dataType : 'json',
		async : false,
		cache : false
	}).responseText;
	if(directLink.indexOf("sau...") > -1){
		$("#directLink").val(directLink);
		$("#drLink").text(directLink);
	}else{
		var shortLink = $.ajax({
			url : 'https://api.shorte.st/v1/data/url',
			headers : {
				'public-api-token' : 'ac1924174f0cf4f064521cf61f066e14'
			},
			type : 'PUT',
			data : 'urlToShorten=' + directLink,
			dataType : 'application/json',
			complete : function(data) {
				if(id == null){
					$("#directLink")
					.val(JSON.parse(data.responseText).shortenedUrl);
					$("#drLink").text(JSON.parse(data.responseText).shortenedUrl);
					$("#drLink").attr("href",
							JSON.parse(data.responseText).shortenedUrl);
					$("#submit").attr('disabled','disabled');
				}else{
					$("#directLink"+id)
					.val(JSON.parse(data.responseText).shortenedUrl);
					$("#drLink"+id).text(JSON.parse(data.responseText).shortenedUrl);
					$("#drLink"+id).attr("href",
							JSON.parse(data.responseText).shortenedUrl);
					$("#submit"+id).attr('disabled','disabled');
				}
				
			}
		});
		var d = new Date();
		document.cookie = "cookiename=1;expires=" + d.toGMTString() + ";" + ";";
	}
	
}