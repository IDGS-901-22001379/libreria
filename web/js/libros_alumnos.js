function cargarCatalogoLibrosConsulta() {
    const url = "http://localhost:8080/Login/api/libro/getAll";

    fetch(url)
            .then(response => response.json())
            .then(response => {
                let mostrar = ""; // Declaración fuera del ciclo
                libros = response;

                for (let i = 0; i < response.length; i++) {
                    if ((response[i].estatus === 1)) {
                        mostrar += "<tr>";
                        mostrar += "<td>" + response[i].nombre_libro + "</td>";
                        mostrar += "<td>" + response[i].autor + "</td>";
                        mostrar += "<td>" + response[i].genero + "</td>";

                        if (response[i].archivo_pdf) {
                            // Revisar si el archivo ya tiene la nomenclatura y añadirla si no está presente
                            let pdfData = response[i].archivo_pdf.startsWith("data:application/pdf;base64,")
                                    ? response[i].archivo_pdf
                                    : `data:application/pdf;base64,${response[i].archivo_pdf}`;

                            const pdfUrl = pdfData;
                            mostrar += `<td>
                        <canvas id="pdfPreview-${i}" style="width: 100px; height: auto; cursor: pointer;" onclick="window.open('${pdfUrl}', '_blank')"></canvas>
                    </td>`;
                            renderPdfPreview(pdfUrl, `pdfPreview-${i}`);
                        } else {
                            mostrar += "<td>No hay archivo PDF</td>";
                        }
                        mostrar += "<td>" + response[i].universidad + "</td>";
                    }
                }
                // Asignar todo el HTML generado a la tabla
                document.getElementById("tblLibros").innerHTML = mostrar;
            })
            .catch(error => {
                console.error("Error al cargar el catálogo de libros:", error);
            });
}

function renderPdfPreview(pdfUrl, canvasId) {
    const loadingTask = pdfjsLib.getDocument(pdfUrl);
    loadingTask.promise.then(pdf => {
        pdf.getPage(1).then(page => {
            const canvas = document.getElementById(canvasId);
            const context = canvas.getContext('2d');
            const scale = 2;
            const viewport = page.getViewport({scale});

            canvas.width = viewport.width;
            canvas.height = viewport.height;

            const renderContext = {
                canvasContext: context,
                viewport: viewport
            };

            page.render(renderContext).promise.then(() => {
                // Redimensionar el canvas
                canvas.style.width = '100px';
                canvas.style.height = 'auto';
            });
        });
    }).catch(reason => {
        console.error(`Error al cargar el PDF: ${reason}`);
    });
}

function buscarLibros() {
    const buscarTexto = document.getElementById('buscarLibro').value.toLowerCase(); // Obtener el texto en minúsculas
    const filas = document.querySelectorAll('#tblLibros tr'); // Seleccionar todas las filas de la tabla

    filas.forEach(fila => {
        const nombreLibro = fila.cells[0].textContent.toLowerCase(); // Nombre del libro en minúsculas
        const autor = fila.cells[1].textContent.toLowerCase(); // Autor en minúsculas
        const genero = fila.cells[2].textContent.toLowerCase(); // Género en minúsculas
        const universidad = fila.cells[4].textContent.toLowerCase(); // Universidad en minúsculas

        // Comprobar si el texto de búsqueda está en el nombre del libro, autor o género
        if (nombreLibro.includes(buscarTexto) || autor.includes(buscarTexto) || genero.includes(buscarTexto) || universidad.includes(buscarTexto)) {
            fila.style.display = ''; // Mostrar la fila si coincide
        } else {
            fila.style.display = 'none'; // Ocultar la fila si no coincide
        }
    });
}