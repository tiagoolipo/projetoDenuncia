$(document).ready(function() {
    $('#login-form').submit(function(e) {
        e.preventDefault();

        var username = $('#username').val();
        var password = $('#password').val();

        $.ajax({
          url: 'http://localhost:8081/api/v1/auth/login',
          type: 'post',
          data: JSON.stringify({
            email: username,
            password
          }),
          headers: {
              "Access-Control-Allow-Origin": '*',
              "Content-type": 'application/json'
          },
          dataType: 'json',
          success: function (data) {
            window.localStorage.setItem('token', data.jwt);
            console.log('aquyi')
            window.location.href = 'pagina-usuario.html'
          },
          error: function(err) {
            console.info(err)
          }
      });
    });
});