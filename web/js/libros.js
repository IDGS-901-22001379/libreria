let libros;
let idLibroSeleccionado = null;
let pdfLibroSeleccionado = null;

function insertarLibros() {
    const nombreLibro = document.getElementById('nombreLibro').value;
    const autor = document.getElementById('autor').value;
    const genero = document.getElementById('genero').value;
    const archivoPdf = document.getElementById('archivoPdf');

    if (archivoPdf.files.length === 0) {
        Swal.fire("Error", "Por favor selecciona un archivo PDF", "error");
        return;
    }

    const archivo = archivoPdf.files[0];
    const reader = new FileReader();

    reader.onload = function (e) {
        let libro = {
            nombre_libro: nombreLibro,
            autor: autor,
            genero: genero,
            archivo_pdf: e.target.result // Guardar el PDF en base64 en el objeto libro
        };

        let params = {l: JSON.stringify(libro)};

        let ruta = "http://localhost:8080/Login/api/libro/insertar";
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
                    cargarCatalogoLibros(); // Recargar la tabla de libros
                    limpiarFormularioRegistrar();
                })
                .catch(error => {
                    console.error("Error al insertar el libro:", error);
                    Swal.fire("Error", "Hubo un problema al insertar el libro", "error");
                    limpiarFormularioRegistrar();
                });
    };

    reader.readAsDataURL(archivo); // Convertir archivo a base64
}
function modificarLibro() {
    const nombreLibro = document.getElementById('nombreLibro_1').value;
    const autor = document.getElementById('autor_1').value;
    const genero = document.getElementById('genero_1').value;

    let libro; // Inicializamos la variable libro

    if (document.getElementById("archivoPdf_1").files.length === 0) {
        // Si no se seleccionó un nuevo PDF, usar el PDF existente
        libro = {
            id_libro: parseInt(idLibroSeleccionado),
            nombre_libro: nombreLibro,
            autor: autor,
            genero: genero,
            archivo_pdf: pdfLibroSeleccionado // PDF existente
        };

        let params = {l: JSON.stringify(libro)}; // Crear los parámetros para la solicitud
        console.log("Libro a enviar:", libro); // Verificar el objeto libro en la consola

        let ruta = "http://localhost:8080/Login/api/libro/modificar";
        fetch(ruta, {
            method: "POST",
            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
            body: new URLSearchParams(params)
        })
                .then(response => response.json())
                .then(response => {
                    if (response.result) {
                        Swal.fire("¡Éxito!", response.result, "success");
                    }
                    if (response.error) {
                        Swal.fire("¡Error!", response.error, "error");
                    }
                    cargarCatalogoLibros();
                    limpiarFormulario();
                })
                .catch(error => {
                    console.error("Error al modificar el libro:", error);
                    Swal.fire("Error", "Hubo un problema al modificar el libro", "error");
                    limpiarFormulario();
                });
    } else {
        // Si hay un nuevo archivo PDF
        const archivo = document.getElementById("archivoPdf_1").files[0];
        const reader = new FileReader();

        reader.onload = function (e) {
            pdfLibroSeleccionado = e.target.result; // Guardar el nuevo PDF en base64
            libro = {
                id_libro: parseInt(idLibroSeleccionado),
                nombre_libro: nombreLibro,
                autor: autor,
                genero: genero,
                archivo_pdf: pdfLibroSeleccionado // Nuevo PDF en base64
            };

            let params = {l: JSON.stringify(libro)}; // Crear los parámetros para la solicitud
            console.log("Libro a enviar:", libro); // Verificar el objeto libro en la consola

            let ruta = "http://localhost:8080/Login/api/libro/modificar";
            fetch(ruta, {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: new URLSearchParams(params)
            })
                    .then(response => response.json())
                    .then(response => {
                        if (response.result) {
                            Swal.fire("¡Éxito!", response.result, "success");
                        }
                        if (response.error) {
                            Swal.fire("¡Error!", response.error, "error");
                        }
                        cargarCatalogoLibros();
                        limpiarFormulario();
                    })
                    .catch(error => {
                        console.error("Error al modificar el libro:", error);
                        Swal.fire("Error", "Hubo un problema al modificar el libro", "error");
                        limpiarFormulario();
                    });
        };

        reader.readAsDataURL(archivo); // Convertir archivo a base64
    }
}


function limpiarFormulario() {
    document.getElementById('nombreLibro_1').value = '';
    document.getElementById('autor_1').value = '';
    document.getElementById('genero_1').value = '';
    document.getElementById('archivoPdf_1').value = null;
    document.getElementById('vistaPrevia_1').src = '';
}

function limpiarFormularioRegistrar() {
    document.getElementById('nombreLibro').value = null;
    document.getElementById('autor').value = null;
    document.getElementById('genero').value = null;
    document.getElementById('archivoPdf').value = null;
}

function cargarCatalogoLibros() {
    // Obtener el valor del checkbox
    const mostrarInactivos = document.getElementById("checkEstatus").checked;
    const url = mostrarInactivos
            ? "http://localhost:8080/Login/api/libro/getAll?activo=false"
            : "http://localhost:8080/Login/api/libro/getAll?activo=true";

    fetch(url)
            .then(response => response.json())
            .then(response => {
                let mostrar = ""; // Declaración fuera del ciclo
                libros = response;

                for (let i = 0; i < response.length; i++) {
                    mostrar += "<tr>"; // Usamos += para concatenar
                    mostrar += "<td>" + response[i].nombre_libro + "</td>";
                    mostrar += "<td>" + response[i].autor + "</td>";
                    mostrar += "<td>" + response[i].genero + "</td>";

                    if (response[i].archivo_pdf) {
                        const pdfUrl = `${response[i].archivo_pdf}`;
                        mostrar += `<td>
                                    <canvas id="pdfPreview-${response[i].id_libro}" style="width: 100px; height: auto; cursor: pointer;" onclick="window.open('${pdfUrl}', '_blank')"></canvas>
                                </td>`;
                        // Llamar a la función para renderizar la vista previa
                        renderPdfPreview(pdfUrl, `pdfPreview-${response[i].id_libro}`);
                    } else {
                        mostrar += "<td>No hay archivo PDF</td>";
                    }

                    if (response[i].estatus === 0) {
                        mostrar += "<td>Eliminado</td>";
                    } else {
                        mostrar += "<td>Registrado</td>";
                    }

                    mostrar +=
                            '<td>' +
                            '<button class="btn btn-warning" ' +
                            'type="button" ' +
                            `onclick="seleccionarLibro(${response[i].id_libro});" ` +
                            (response[i].estatus === 0 ? 'disabled' : '') +
                            ' data-toggle="modal" data-target="#modalLibro">' +
                            '<i class="bi bi-pencil-square fa-lg"></i> Seleccionar' +
                            '</button>' +
                            '</td>';

                    mostrar += "<td>";
                    if (response[i].estatus === 0) {
                        mostrar += `<button class='btn btn-success' onclick='activarLibro(${response[i].id_libro});'><i class='bi bi-check-square-fill'></i> Activar </button>`;
                    } else {
                        mostrar += `<button class='btn btn-danger' onclick='eliminarLibro(${response[i].id_libro});'><i class='bi bi-trash3-fill fa-lg'></i> Eliminar </button>`;
                    }
                    mostrar += "</td>";
                    mostrar += "</tr>";
                }

                // Asignar todo el HTML generado a la tabla
                document.getElementById("tblLibros").innerHTML = mostrar;
            })
            .catch(error => {
                console.error("Error al cargar el catálogo de libros:", error);
            });
}

// Variable global para almacenar el ID del libro seleccionado

function seleccionarLibro(idLibro) {
    // Guardar el ID del libro seleccionado
    idLibroSeleccionado = idLibro;

    // Buscar el libro seleccionado por su ID en la lista de libros
    const libroSeleccionado = libros.find(libro => libro.id_libro === idLibro);

    if (libroSeleccionado) {
        // Llenar los campos del formulario en el modal con los datos del libro seleccionado
        document.getElementById('nombreLibro_1').value = libroSeleccionado.nombre_libro;
        document.getElementById('autor_1').value = libroSeleccionado.autor;
        document.getElementById('genero_1').value = libroSeleccionado.genero;
        pdfLibroSeleccionado = libroSeleccionado.archivo_pdf;

        // Si el libro tiene un archivo PDF en base64, mostrarlo en la vista previa
        const vistaPrevia = document.getElementById('vistaPrevia_1');
        if (libroSeleccionado.archivo_pdf) {
            vistaPrevia.src = libroSeleccionado.archivo_pdf;
            vistaPrevia.style.display = 'block';
        } else {
            vistaPrevia.src = '';
            vistaPrevia.style.display = 'none';
        }

        // Abrir el modal
        $('#modalLibro').modal('show');
    } else {
        console.error("Libro no encontrado con ID:", idLibro);
    }
}

function mostrarVistaPrevia(event) {
    const archivo = event.target.files[0];
    const vistaPrevia = document.getElementById('vistaPrevia');

    if (archivo && archivo.type === 'application/pdf') {
        const reader = new FileReader();

        reader.onload = function (e) {
            // Establecer la vista previa con el PDF en base64
            vistaPrevia.src = e.target.result;
            vistaPrevia.style.display = 'block';

            window.pdfBase64 = e.target.result;

            // Abrir el modal para mostrar la vista previa del PDF
            $('#modalVistaPreviaPdf').modal('show');
        };

        reader.readAsDataURL(archivo); // Convertir archivo a base64
    }
}

function mostrarVistaPreviaModificar(event) {
    const archivo = event.target.files[0];
    const vistaPrevia = document.getElementById('vistaPrevia_1');

    if (archivo && archivo.type === 'application/pdf') {
        const reader = new FileReader();

        reader.onload = function (e) {
            // Establecer la vista previa con el PDF en base64
            vistaPrevia.src = e.target.result;
            vistaPrevia.style.display = 'block';

            window.pdfBase64 = e.target.result;
        };

        reader.readAsDataURL(archivo); // Convertir archivo a base64
    }
}
function eliminarLibro(id_libro) {
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
            fetch(`http://localhost:8080/Login/api/libro/eliminar?id_libro=${id_libro}`)
                    .then(response => response.json())
                    .then(response => {
                        if (response.result) {
                            Swal.fire("¡Exito!", response.result, "success");
                        }
                        if (response.error) {
                            Swal.fire("¡Error!", response.error, "error");
                        }
                        cargarCatalogoLibros();
                    })
                    .catch(() => Swal.fire('Error', 'Ocurrió un error al eliminar el Libro', 'error'));
        }
    });
}
function activarLibro(id_libro) {
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
            fetch(`http://localhost:8080/Login/api/libro/reactivar?id_libro=${id_libro}`)
                    .then(response => response.json())
                    .then(response => {
                        if (response.result) {
                            Swal.fire("¡Exito!", response.result, "success");
                        }
                        if (response.error) {
                            Swal.fire("¡Error!", response.error, "error");
                        }
                        cargarCatalogoLibros();
                    })
                    .catch(() => Swal.fire('Error', 'Ocurrió un error al reactivar el Libro', 'error'));
        }
    });
}
function buscarLibros() {
    const buscarTexto = document.getElementById('buscarLibro').value.toLowerCase(); // Obtener el texto en minúsculas
    const filas = document.querySelectorAll('#tblLibros tr'); // Seleccionar todas las filas de la tabla

    filas.forEach(fila => {
        const nombreLibro = fila.cells[0].textContent.toLowerCase(); // Nombre del libro en minúsculas
        const autor = fila.cells[1].textContent.toLowerCase(); // Autor en minúsculas
        const genero = fila.cells[2].textContent.toLowerCase(); // Género en minúsculas

        // Comprobar si el texto de búsqueda está en el nombre del libro, autor o género
        if (nombreLibro.includes(buscarTexto) || autor.includes(buscarTexto) || genero.includes(buscarTexto)) {
            fila.style.display = ''; // Mostrar la fila si coincide
        } else {
            fila.style.display = 'none'; // Ocultar la fila si no coincide
        }
    });
}
function renderPdfPreview(pdfUrl, canvasId) {
    const loadingTask = pdfjsLib.getDocument(pdfUrl);
    loadingTask.promise.then(pdf => {
        // Solo renderizar la primera página
        pdf.getPage(1).then(page => {
            const canvas = document.getElementById(canvasId);
            const context = canvas.getContext('2d');
            const scale = 2; // Escala mayor para mejorar la resolución
            const viewport = page.getViewport({ scale });

            // Ajustar el tamaño interno del canvas para la resolución
            canvas.width = viewport.width;
            canvas.height = viewport.height;

            const renderContext = {
                canvasContext: context,
                viewport: viewport
            };

            // Renderizar la página
            page.render(renderContext).promise.then(() => {
                // Redimensionar el canvas a su tamaño original (CSS)
                canvas.style.width = '100px'; // Tamaño visible
                canvas.style.height = 'auto'; // Altura automática
                // Redibujar la imagen para ajustar a las dimensiones del canvas
                context.drawImage(canvas, 0, 0, viewport.width, viewport.height, 0, 0, 100, 100);
            });
        });
    }, reason => {
        console.error(`Error al cargar el PDF: ${reason}`);
    });
}
