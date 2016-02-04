'use strict';

function register() {
	var user = {};
	user.email = $('#inputEmail').val();
	user.nickname = $('#inputNickname').val();
	user.password = $('#inputPassword').val();
	
	$.ajax({
		url: 'api/user/', 
		method: 'POST',
		data: JSON.stringify(user),
		headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		},
		success: function(result){	
			window.location.replace("dashboard");
		},
		error: function(xhr, ajaxOptions, thrownError) {
			alert(xhr.status + ' ' + thrownError);
		}
	});
}
