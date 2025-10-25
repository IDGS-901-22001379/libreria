function ingresar() {
    let usu = document.getElementById("username").value;
    let con = document.getElementById("password").value;
    let ruta = "http://localhost:8080/Login/api/login/acceso";

    fetch(ruta, {
        method: "POST",
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: 'usuario=' + encodeURIComponent(usu) + '&contrasenia=' + encodeURIComponent(con)
    })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error de red o servidor.');
                }
                return response.json();
            })
            .then(data => {
                if (data.rol === "ADMIN") {
                    Swal.fire({
                        title: "¡BIENVENIDO ADMINISTRADOR!",
                        text: "Su token es:" + data.token,
                        icon: "success",
                        showConfirmButton: true
                    })
                            .then(() => {
                                localStorage.setItem('usuario', usu);
                                localStorage.setItem('token', data.token);
                                localStorage.setItem('contrasenia', con);
                                localStorage.setItem('rol', data.rol);
                                location.href = "http://localhost:8080/Login/CatalagoDeUsuarios.html";
                            });
                } else if (data.rol === "BIBLI") {
                    Swal.fire({
                        title: "¡BIENVENIDO BIBLIOTECARIO!",
                        text: "Su token es:" + data.token,
                        icon: "success",
                        showConfirmButton: true
                    })
                            .then(() => {
                                localStorage.setItem('usuario', usu);
                                localStorage.setItem('token', data.token);
                                localStorage.setItem('contrasenia', con);
                                localStorage.setItem('rol', data.rol);
                                location.href = "http://localhost:8080/Login/CatalagoDeLibros.html";
                            });
                } else if (data.rol === "ALUMN") {
                    Swal.fire({
                        title: "¡BIENVENIDO ALUMNO!",
                        text: "Su token es:" + data.token,
                        icon: "success",
                        showConfirmButton: true
                    })
                            .then(() => {
                                localStorage.setItem('usuario', usu);
                                localStorage.setItem('token', data.token);
                                localStorage.setItem('contrasenia', con);
                                localStorage.setItem('rol', data.rol);
                                location.href = "http://localhost:8080/Login/CatalagoDeLibrosAlumnos.html";
                            });
                } else if(data.error){
                    Swal.fire("INVALIDO", data.error, "error");
                    document.getElementById("username").value = "";
                    document.getElementById("password").value = "";
                } else {
                    Swal.fire("INVALIDO", "¡CREDENCIALES INCORRECTAS!", "error");
                    document.getElementById("username").value = "";
                    document.getElementById("password").value = "";
                }
            })
            .catch(error => {
                console.error('Error:', error);
                Swal.fire("Error", "Error de red o servidor.", "error");
            });
}

function cerrarSesion() {
    Swal.fire({
        title: '¿Estás seguro de que quieres cerrar sesión?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, cerrar sesión',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            var usuario = localStorage.getItem('usuario');

            fetch('http://localhost:8080/Login/api/login/salir', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'usuario=' + encodeURIComponent(usuario)
            })
            .then(response => response.json())
            .then(data => {
                if (data.exito) {
                    localStorage.removeItem('usuario');
                    localStorage.removeItem('contrasenia');
                    localStorage.removeItem('token');
                    localStorage.removeItem('rol');

                    Swal.fire("¡HECHO!", "SE CERRÓ LA SESION CON ÉXITO", "warning")
                    .then(() => {
                        window.location.href = 'http://localhost:8080/Login/index.html';
                    });
                } else {
                    Swal.fire("Error", "No se pudo cerrar sesión. Intenta nuevamente.", "error");
                }
            })
            .catch(error => {
                console.error('ERROR:', error);
                Swal.fire("Error", "Ocurrió un error al cerrar sesión.", "error");
            });
        }
    });
}
