function enviarFormulario(event) {
    event.preventDefault();
    var firstName = $('#firstName').val();
    var lastName = $('#lastName').val();
    var office = $('#office').val();
    var email = $('#email').val();
    var password = $('#password').val();
    var userData = {
        firstName: firstName,
        lastName: lastName,
        office: office,
        email: email,
        password: password,
        roleId: '1'
    };
    $.ajax({
        url: 'https://www.denunciaonlinepi19.online/cadastro.html',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(userData),
        success: function(response) {
            console.log('Usuário cadastrado com sucesso:', response, userData);
            alert('Cadastro realizado com sucesso!');
            window.location.href = 'pagina-usuario.html';
        },
        error: function(err) {
            console.error('Erro ao cadastrar usuário:', err);
            if (err.responseJSON && err.responseJSON.errors && err.responseJSON.errors.length > 0) {
                var errorMessage = err.responseJSON.errors[0].messageError;
                alert('Erro ao cadastrar usuário: ' + errorMessage);
            } else {
                alert('Erro ao cadastrar usuário. Por favor, tente novamente.');
            }
        }
    });
}