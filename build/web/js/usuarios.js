let usuarios;
let idUsuarioSeleccionado = null;

function cargarCatalogoUsuarios() {
    // Obtener el valor del checkbox
    const mostrarInactivos = document.getElementById("checkEstatus").checked;
    const url = mostrarInactivos
            ? "http://localhost:8080/Login/api/usuario/getAll?activo=false"
            : "http://localhost:8080/Login/api/usuario/getAll?activo=true";

    fetch(url)
            .then(response => response.json())
            .then(response => {
                let mostrar = ""; // Declaración fuera del ciclo
                usuarios = response;

                for (let i = 0; i < response.length; i++) {
                    mostrar += "<tr>"; // Usamos += para concatenar
                    mostrar += "<td>" + response[i].nombre + "</td>";
                    mostrar += "<td>" + response[i].apellidoPaterno + "</td>";
                    mostrar += "<td>" + response[i].apellidoMaterno + "</td>";
                    mostrar += "<td>" + response[i].telefono + "</td>";
                    mostrar += "<td>" + response[i].nombreUsuario + "</td>";
                    mostrar += "<td>" + response[i].contrasenia + "</td>";
                    if (response[i].rol === "ALUMN") {
                        rolDescripcion = "Alumno";
                    } else if (response[i].rol === "BIBLI") {
                        rolDescripcion = "Bibliotecario";
                    } else if (response[i].rol === "ADMIN") {
                        rolDescripcion = "Administrador";
                    } else {
                        rolDescripcion = response[i].rol; // En caso de que el rol no coincida con los anteriores
                    }

                    // Añadir la fila a la tabla
                    mostrar += "<td>" + rolDescripcion + "</td>";
                    mostrar +=
                            '<td>' +
                            '<button class="btn btn-warning" ' +
                            'type="button" ' +
                            `onclick="seleccionarUsuario(${response[i].idUsuario});" ` +
                            (response[i].estatus === 0 ? 'disabled' : '') +
                            ' data-toggle="modal" data-target="#modalUsuario">' +
                            '<i class="bi bi-pencil-square fa-lg"></i> Seleccionar' +
                            '</button>' +
                            '</td>';

                    mostrar += "<td>";
                    if (response[i].estatus === 0) {
                        mostrar += `<button class='btn btn-success' onclick='activarUsuario(${response[i].idUsuario});'><i class='bi bi-check-square-fill'></i> Activar </button>`;
                    } else {
                        mostrar += `<button class='btn btn-danger' onclick='eliminarUsuario(${response[i].idUsuario});'><i class='bi bi-trash3-fill fa-lg'></i> Eliminar </button>`;
                    }
                    mostrar += "</td>";
                    mostrar += "</tr>";
                }

                // Asignar todo el HTML generado a la tabla
                document.getElementById("tblUsuarios").innerHTML = mostrar;
            })
            .catch(error => {
                console.error("Error al cargar el catálogo de usuarios:", error);
            });
}

function insertarUsuario() {
    const nombre = document.getElementById('nombreUsuario').value;
    const apellidoP = document.getElementById('apellidoP').value;
    const apellidoM = document.getElementById('apellidoM').value;
    const telefono = document.getElementById('telefono').value;
    const rol = document.getElementById('rol').value;

    let usuario = {
        nombre: nombre,
        apellidoPaterno: apellidoP,
        apellidoMaterno: apellidoM,
        telefono: telefono,
        rol: rol
    };

    let params = {u: JSON.stringify(usuario)};

    let ruta = "http://localhost:8080/Login/api/usuario/insertar";
    fetch(ruta, {
        method: "POST",
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
        body: new URLSearchParams(params)
    })
            .then(response => response.json())
            .then(response => {
                if (response.result) {
                    Swal.fire("¡Exito!", response.result, "success");
                }
                if (response.error) {
                    Swal.fire("¡Error!", response.error, "error");
                }
                cargarCatalogoUsuarios();
                limpiarFormularioInsertar();
            })
            .catch(error => {
                console.error("Error al insertar el usuario:", error);
                Swal.fire("Error", "Hubo un problema al insertar el usuario", "error");
            });
}

function modificarUsuario() {
    const nombre = document.getElementById('nombre_1').value;
    const apellidoP = document.getElementById('apellidoP_1').value;
    const apellidoM = document.getElementById('apellidoM_1').value;
    const telefono = document.getElementById('telefono_1').value;
    const nombre_u = document.getElementById('nombre_usuario_1').value;
    const contrasenia = document.getElementById('contrasenia_1').value;
    const rol = document.getElementById('rol_1').value;

    let usuario = {
        idUsuario: idUsuarioSeleccionado,
        nombre: nombre,
        apellidoPaterno: apellidoP,
        apellidoMaterno: apellidoM,
        telefono: telefono,
        nombreUsuario: nombre_u,
        contrasenia: contrasenia,
        rol: rol
    };

    let params = {u: JSON.stringify(usuario)};

    let ruta = "http://localhost:8080/Login/api/usuario/modificar";
    fetch(ruta, {
        method: "POST",
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
        body: new URLSearchParams(params)
    })
            .then(response => response.json())
            .then(response => {
                if (response.result) {
                    Swal.fire("¡Exito!", response.result, "success");
                }
                if (response.error) {
                    Swal.fire("¡Error!", response.error, "error");
                }
                cargarCatalogoUsuarios();
                limpiarFormularioModificar();
            })
            .catch(error => {
                console.error("Error al modificar el usuario:", error);
                Swal.fire("Error", "Hubo un problema al modificar el usuario", "error");
            });
}
function seleccionarUsuario(idUsuario) {
    // Guardar el ID del usuario seleccionado
    idUsuarioSeleccionado = idUsuario;

    // Buscar el libro seleccionado por su ID en la lista de libros
    const usuarioSeleccionado = usuarios.find(usuario => usuario.idUsuario === idUsuario);

    if (idUsuarioSeleccionado) {
        document.getElementById('nombre_1').value = usuarioSeleccionado.nombre;
        document.getElementById('apellidoP_1').value = usuarioSeleccionado.apellidoPaterno;
        document.getElementById('apellidoM_1').value = usuarioSeleccionado.apellidoMaterno;
        document.getElementById('telefono_1').value = usuarioSeleccionado.telefono;
        document.getElementById('nombre_usuario_1').value = usuarioSeleccionado.nombreUsuario;
        document.getElementById('contrasenia_1').value = usuarioSeleccionado.contrasenia;
        document.getElementById('rol_1').value = usuarioSeleccionado.rol;

        // Abrir el modal
        $('#modalUsuario').modal('show');
    } else {
        console.error("Usuario no encontrado con ID:", idUsuario);
    }
}

function limpiarFormularioModificar() {
    document.getElementById('nombre_1').value = '';
    document.getElementById('apellidoP_1').value = '';
    document.getElementById('apellidoM_1').value = '';
    document.getElementById('telefono_1').value = '';
    document.getElementById('nombre_usuario_1').value = '';
    document.getElementById('contrasenia_1').value = '';
    document.getElementById('rol_1').value = '';
}

function limpiarFormularioInsertar() {
    document.getElementById('nombreUsuario').value = '';
    document.getElementById('apellidoP').value = '';
    document.getElementById('apellidoM').value = '';
    document.getElementById('telefono').value = '';
    document.getElementById('rol').value = '';
}

function eliminarUsuario(id_usuario) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "¡Continue si esta seguro!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, Eliminar',
        cancelButtonText: 'Regresar'
    }).then(result => {
        if (result.isConfirmed) {
            fetch(`http://localhost:8080/Login/api/usuario/eliminar?id_usuario=${id_usuario}`)
                    .then(response => response.json())
                    .then(response => {
                        if (response.result) {
                            Swal.fire("¡Exito!", response.result, "success");
                        }
                        if (response.error) {
                            Swal.fire("¡Error!", response.error, "error");
                        }
                        cargarCatalogoUsuarios();
                    })
                    .catch(() => Swal.fire('Error', 'Ocurrió un error al eliminar el Usuario', 'error'));
        }
    });
}
function activarUsuario(id_usuario) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "¡Quieres volver activarlo!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, Confirmar',
        cancelButtonText: 'Regresar'
    }).then(result => {
        if (result.isConfirmed) {
            fetch(`http://localhost:8080/Login/api/usuario/reactivar?id_usuario=${id_usuario}`)
                    .then(response => response.json())
                    .then(response => {
                        if (response.result) {
                            Swal.fire("¡Exito!", response.result, "success");
                        }
                        if (response.error) {
                            Swal.fire("¡Error!", response.error, "error");
                        }
                        cargarCatalogoUsuarios();
                    })
                    .catch(() => Swal.fire('Error', 'Ocurrió un error al reactivar el Usuario', 'error'));
        }
    });
}
function buscarUsuario() {
    const buscarTexto = document.getElementById('buscarUsuario').value.toLowerCase(); // Obtener el texto en minúsculas
    const filas = document.querySelectorAll('#tblUsuarios tr'); // Seleccionar todas las filas de la tabla

    filas.forEach(fila => {
        const nombreUSuario = fila.cells[0].textContent.toLowerCase(); // Nombre del usuario en minúsculas
        const apellidoP = fila.cells[1].textContent.toLowerCase(); // Apellido paterno en minúsculas
        const apellidoM = fila.cells[2].textContent.toLowerCase(); // Apellido Materno en minúsculas
        const telefono = fila.cells[3].textContent; // Teléfono
        const nom_usu = fila.cells[4].textContent.toLowerCase(); // Nombre de usuario
        const contrasenia = fila.cells[5].textContent.toLowerCase(); // Contrasenia
        const rol = fila.cells[6].textContent.toLowerCase(); // Rol

        // Comprobar si el texto de búsqueda está en el nombre del libro, autor o género
        if (nombreUSuario.includes(buscarTexto) || apellidoP.includes(buscarTexto) || apellidoM.includes(buscarTexto)
            || telefono.includes(buscarTexto) || nom_usu.includes(buscarTexto) || contrasenia.includes(buscarTexto) || rol.includes(buscarTexto)) {
            fila.style.display = ''; // Mostrar la fila si coincide
        } else {
            fila.style.display = 'none'; // Ocultar la fila si no coincide
        }
    });
}