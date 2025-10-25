# 📚 **Librería — Java + Capas + Microservicios**

Sistema de **gestión de libros** en **Java** con arquitectura por **capas** y enfoque de **microservicios**.  
El backend expone **API REST (Jakarta JAX-RS)** para **altas**, **modificaciones**, **listados**, **borrado lógico** y **reactivación**.  
Las reglas de negocio se validan con **CQRS** antes de acceder a **MySQL**.  
Se habilita **CORS** para consumo desde el frontend.  
El frontend usa **HTML + CSS + Bootstrap + JavaScript** con **búsqueda**, **modales** y **vista previa de PDF**; mensajes con **SweetAlert2**.  
Se publica un **ViewModel** para respuestas públicas y un **AppService** que **agrega catálogos externos** para una lista unificada.

---

## 🚀 **Qué incluye**
- ✅ **CRUD completo** de libros con validaciones en **CQRS** (longitudes mín./máx. y verificación de ID).  
- 🗄️ **DAO** para **MySQL** con procedimientos y consultas preparadas; cierre seguro de conexiones.  
- 🌐 **API REST** para **insertar**, **modificar**, **listar activos/inactivos**, **eliminar (estatus)** y **reactivar**.  
- 🧾 **ViewModel público** desacoplado del modelo de BD para exponer solo lo necesario.  
- 🔗 **AppService** que consulta **microservicios externos** y **fusiona** resultados con el catálogo local.  
- 🔐 **CORS** habilitado para ambientes de desarrollo.  
- 🖥️ **Frontend** con **tabla filtrable**, **modales de edición**, **PDF en base64** (pdf.js) y **alertas** (SweetAlert2).  

---

## 🧱 **Arquitectura y módulos**
- 🧭 **Controller** → orquesta casos de uso y coordina respuestas hacia REST.  
- 🧠 **CQRS** → valida comandos (**insertar/modificar**) e IDs para **eliminar/reactivar** antes del DAO.  
- 💽 **DAO** → encapsula acceso a datos, ejecuta procedures/queries y mapea entidades.  
- 🔑 **BD** → credenciales y ciclo de vida de conexiones.  
- 📦 **Model** → entidades del dominio (**Libro**).  
- 🪪 **ViewModel** → proyecciones seguras para consumo público.  
- 🌍 **AppService** → cliente HTTP para **agregación** de catálogos externos.  
- 🚪 **REST** → endpoints que gestionan parámetros, errores y devuelven **JSON**.  
- 🎨 **Frontend** → formularios, tablas y modales con **Bootstrap**; **pdf.js** para miniaturas; **SweetAlert2** para confirmaciones.  

---

## 🧩 **Tecnologías y lenguajes**
- 🧑‍💻 **Backend**: Java · Jakarta JAX-RS · Gson · HttpClient  
- 🗃️ **Persistencia**: MySQL 8 (procedimientos almacenados)  
- 🎛️ **Frontend**: HTML · CSS · Bootstrap · JavaScript · pdf.js · SweetAlert2  
- 🌍 **Interoperabilidad**: **CORS** habilitado; contratos **JSON**  
- 🧱 **Prácticas**: Capas · **CQRS** · **ViewModel** · **Agregación de microservicios**  

---

## 🗃️ **Base de datos (visión funcional)**
- 📚 Tabla de **libros** con: *nombre*, *autor*, *género*, *estatus*, *archivo PDF (base64 para demo)* y *universidad*.  
- 🧭 **Procedimientos** para **insertar** y **modificar**, devolviendo el identificador.  
- ♻️ **Borrado lógico** cambiando el estatus; permite **reactivar** sin perder datos históricos.  

---

## 🌐 **API (uso conceptual)**
- 🟢 **Insertar** → recibe datos + PDF en base64; valida con **CQRS** y persiste vía **DAO**.  
- 🟡 **Modificar** → usa ID + nuevos datos; valida y actualiza.  
- 🔍 **Listar** → filtra por **activos** o **inactivos**.  
- 🧾 **Público local** → entrega **ViewModel** pensado para el cliente.  
- 🌐 **Público agregado** → combina catálogo local con **microservicios externos** a través del **AppService**.  
- 🗑️ **Eliminar / Reactivar** → cambia estatus para soportar **baja lógica** y **recuperación**.  

---

## ✅ **Reglas de negocio**
- 📏 `nombre_libro` entre **5–100** caracteres.  
- 🏷️ `genero` entre **5–30** caracteres.  
- 🔎 `id_libro` **> 0** para **eliminar** o **reactivar**.  
- 🔔 Mensajes de error **claros y consistentes** para el frontend.  

---

## ▶️ **Flujo de ejecución (alto nivel)**
1. 🧑‍🎓 El usuario registra/modifica desde la UI; el frontend valida y envía al API.  
2. 🧭 REST delega en **Controller** y **CQRS** para aplicar reglas.  
3. 💽 **DAO** ejecuta la operación en **MySQL**.  
4. 🪪 Para vistas públicas, REST transforma a **ViewModel** y responde en **JSON**.  
5. 🌍 El **AppService** consulta endpoints externos y **mezcla** con el catálogo local.  
6. 🎨 El frontend actualiza la tabla, permite búsqueda y muestra miniaturas/vista previa de **PDF** con **pdf.js**.  

---

## 🛠️ **Stack (resumen)**
**Java, JavaScript, HTML, CSS, Bootstrap** · **Jakarta JAX-RS**, **Gson**, **HttpClient** · **MySQL** · **pdf.js**, **SweetAlert2** · **CORS** · Arquitectura por **capas** con **CQRS**, **ViewModel** y **agregación de microservicios**.
