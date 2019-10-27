function addToCart(id){
    $.post(
    'http://localhost:8080/add-to-cart',
    {p_id: id},
    function(res){
    alert(res);

    }
    );
}





function loginClick(){
	var inputLogin = document.getElementById('inputLogin');
		var login = inputLogin.value;

		if(login == ''){
			alert('Введите пожалуйста логин!!!');
			return;
		}

		var inputPassword = document.getElementById('inputPassword');
		var password = inputPassword.value;

		if(password == ''){
			alert('Введите пожалуйста пароль!!!');
			return;


}



	$.ajax({
      method: 'GET',
      url:'http://localhost:8080',
      success:function(res){
      	alert(res);
      },
      error:function(err){
      alert(err);
      }
	});
}
