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
                password: password
            }),
            headers: {
                "Access-Control-Allow-Origin": '*',
                "Content-type": 'application/json'
            },
            dataType: 'json',
            success: function(data) {
                setSessionToken(data.jwt);
                console.log('Token de autenticação obtido com sucesso.');
                window.location.href = 'pagina-usuario.html';
            },
            error: function(err) {
                console.error(err);
            }
        });
    });
    function setSessionToken(token) {
        localStorage.setItem('sessionToken', token);
    }
});

