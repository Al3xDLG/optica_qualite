let lentesDeContactoSeleccionado;
let lentesDeContactoEnCompra;
let lentesDeContacto;
let clientes;
let examenesVista;
let clienteSeleccionado;
let lenteDeContacto;
const header = document.getElementById("headerLentesDeContacto");
console.log(header);
const tbl = document.getElementById('tablaClientes');
console.log(tbl);
console.log("Hola");
export function inicializar() {
    buscarCliente();
    buscarLC();
    mostrarCampoBusquedaClientes();
    document.getElementById("btnClean").disabled = true;
    document.getElementById("btnSave").disabled = true;

    document.getElementById("btnCleanLC").disabled = true;
    document.getElementById("btnSaveLC").disabled = true;
    document.getElementById("btnDeleteLC").disabled = true;
    configureTableFilter(document.getElementById('txtBusquedaClientes'),
            tbl);
}

export function mostrarCampoBusquedaClientes()
{
    let busqueda = document.getElementById("txtBusquedaClientes");
    if (busqueda.style.display != "none") {
        busqueda.style.display = "none";
        document.getElementById("btnBuscarClientes").innerHTML = "<i class='fa-solid fa-magnifying-glass'></i>Buscar";
    } else {
        busqueda.style.display = "block";
        document.getElementById("btnBuscarClientes").innerHTML = "<i class='fa-solid fa-magnifying-glass'></i>Cerrar";
    }
}

export function setDetalleVisible() {
    const form = document.getElementById("areaContenido");
    let tbl = document.getElementById("tblPresupuesto");
    if (form.style.display == "none") {
        form.style.display = "block";
        document.getElementById("letreroFormulario").innerHTML = "Cerrar.";
        tbl.style.display = "none";
    } else {
        form.style.display = "none";
        document.getElementById("letreroFormulario").innerHTML = "Agregar.";
        tbl.style.display = "block";
    }
}

export function buscarCliente()
{
    let param = null;
    let lastToken = localStorage.getItem("lastToken");
    let rol = localStorage.getItem("rol");
    let filtro = document.getElementById("txtBusquedaClientes").value;
    datos = {
        filtro: filtro,
        token: lastToken
    };
    param = new URLSearchParams(datos);
    fetch("api/cliente/getAll",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: param
            }).then(response => {
        return response.json();
    })
            .then(function (data)
            {
                if (data.exception != null)
                {
                    Swal.fire('',
                            'Error interno del servidor. Intente nuevamente mas tarde.',
                            'error');
                    return;
                }
                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null) {
                    Swal.fire('Error', data.errorperm, 'error');
                    return;
                }
                if (data.errorsec != null)
                {
                    localStorage.clear();
                    Swal.fire({
                        title: 'Token incorrecto',
                        text: data.errorsec,
                        icon: 'error',
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'Iniciar sesion'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            console.log(data);
                            alert("consola");
                            window.location = "index.html";
                        }
                    });
                    return;
                }
                console.log("cargando clientes...");
                console.log(data);
                cargarClientes(data);
            });
}

export function cargarClientes(data)
{
    let contenido = "";
    clientes = data;
    clientes.forEach(function (cliente, index) {
        let registro =
                "<tr>" +
                "<td>" + cliente.numeroUnico + "</td>" +
                "<td>" + cliente.persona.nombre + "</td>" +
                "<td>" + cliente.persona.apellidoPaterno + "</td>" +
                "<td>" + cliente.persona.apellidoMaterno + "</td>" +
                "<td>" + cliente.persona.telMovil + "</td>" +
                "<td>" + cliente.persona.email + "</td>" +
                "<td><a onclick='moduloVentaPresupuestoLC.seleccionarCliente(" + index + ");'><i class='fa fa-plus-square'></i></a></td></tr>";
        contenido += registro;
    });
    document.getElementById("tblClientes").innerHTML = contenido;
}

export function cargarClienteSeleccionado()
{
    let registro =
            "<tr>" +
            "<td>" + clienteSeleccionado.numeroUnico + "</td>" +
            "<td>" + clienteSeleccionado.persona.nombre + "</td>" +
            "<td>" + clienteSeleccionado.persona.apellidoPaterno + "</td>" +
            "<td>" + clienteSeleccionado.persona.apellidoMaterno + "</td>" +
            "<td>" + clienteSeleccionado.persona.telMovil + "</td>" +
            "<td>" + clienteSeleccionado.persona.email + "</td>";
    document.getElementById("tblClienteSeleccionado").innerHTML = registro;
}

export function seleccionarCliente(index)
{
    if (clienteSeleccionado != null) {
        Swal.fire({
            title: 'Cliente ya seleccionado',
            text: "¿Quieres cambiar al cliente?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, cambiarlo',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire(
                        'Seleccionado',
                        'El cliente se ha seleccionado',
                        'success'
                        );
                clienteSeleccionado = clientes[index];
                console.log(clienteSeleccionado);
                buscarEV();
                cargarClienteSeleccionado();
            }
        });
    } else
    {
        Swal.fire(
                'Seleccionado',
                'El cliente se ha seleccionado',
                'success'
                );
        clienteSeleccionado = clientes[index];
        console.log(clienteSeleccionado);
        buscarEV();
        cargarClienteSeleccionado();
    }
}

export function buscarEV()
{
    let param = null;
    let lastToken = localStorage.getItem("lastToken");
    let filtro = clienteSeleccionado.idCliente;
    let rol = localStorage.getItem("rol");
    datos = {
        filtro: filtro,
        token: lastToken
    };
    console.log(datos);
    param = new URLSearchParams(datos);
    console.log(param);
    fetch("api/examenvista/getAll",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: param
            }).then(response => {
        return response.json();
    })
            .then(function (data)
            {
                if (data.exception != null)
                {
                    Swal.fire('',
                            'Error interno del servidor. Intente nuevamente mas tarde.',
                            'error');
                    return;
                }
                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null) {
                    Swal.fire('Error', data.errorperm, 'error');
                    return;
                }
                if (data.errorsec != null)
                {
                    localStorage.clear();
                    Swal.fire({
                        title: 'Token incorrecto',
                        text: data.errorsec,
                        icon: 'error',
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'Iniciar sesion'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            console.log(data);
                            alert("consola");
                            window.location = "index.html";
                        }
                    });
                    return;
                }
                console.log("cargando examenes vista...");
                console.log(data);
                cargarEV(data);
            });
}
export function buscarLC()
{
    let param = null;
    let lastToken = localStorage.getItem("lastToken");
    let rol = localStorage.getItem("rol");
    datos = {
        token: lastToken
    };
    param = new URLSearchParams(datos);
    fetch("api/lentescontacto/getAll",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: param
            }).then(response => {
        return response.json();
    })
            .then(function (data)
            {
                if (data.exception != null)
                {
                    Swal.fire('',
                            'Error interno del servidor. Intente nuevamente mas tarde.',
                            'error');
                    return;
                }
                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null) {
                    Swal.fire('Error', data.errorperm, 'error');
                    return;
                }
                if (data.errorsec != null)
                {
                    localStorage.clear();
                    Swal.fire({
                        title: 'Token incorrecto',
                        text: data.errorsec,
                        icon: 'error',
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'Iniciar sesion'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            console.log(data);
                            alert("consola");
                            window.location = "index.html";
                        }
                    });
                    return;
                }
                console.log("cargando examenes vista...");
                console.log(data);
                cargarLC(data);
            });
}
export function cargarEV(data)
{
    examenesVista = data;
    console.log(examenesVista);
    let select = document.getElementById("selectEV");
    let options = select.getElementsByTagName('option');
    for (var i = 0; i < options.length; i++) {
        select.removeChild(options[i]);
        i--;
    }
    let contenido = "<option value='' selected='' disabled=''>Selecciona el examen de vista</option>";
    examenesVista.forEach(function (ev, index) {
        let registro = "<option value='" + index + "'>" + ev.clave + "</option>";
        contenido += registro;
    });
    select.innerHTML = contenido;
}


export function cargarLC(data)
{
    lentesDeContacto = data;
    let select = document.getElementById("selectLC");
    let options = select.getElementsByTagName('option');
    for (var i = 0; i < options.length; i++) {
        select.removeChild(options[i]);
        i--;
    }
    let contenido = "<option value='SeleccionarLC' selected='' disabled=''>Selecciona el lente de contacto</option>";
    lentesDeContacto.forEach(function (lc, index) {
        let registro = "<option value='" + index + "' onclick='moduloVentaPresupuestoLC.cargarDatosLC(" + index + ");'>" + lc.producto.nombre + "</option>";
        contenido += registro;
    });
    select.innerHTML = contenido;
}

export function seleccionarLC(index) { //seleccionarProducto
    lenteDeContactoSeleccionado = lentesDeContacto[index];
    let enCarrito = false;
    lentesDeContactoEnCompra.forEach(function (lc, index) {
        if (lc == lenteDeContactoSeleccionado) {
            enCarrito = true;
            return;
        }
    });
    if (enCarrito) {
        Swal.fire('', 'Producto ya en carrito.', 'warning');
        setDetalleVisible(true);
        actualizarTablaVenta();
    } else {
        productosEnCompra.push(lenteDeContactoSeleccionado);
        setDetalleVisible(true);
        actualizarTablaVenta();
    }

}

export function agregarCompra()
{

    if (validarDescuento() && validarExistencias()) {
        let datos = null;
        let param = null;
        //let url = "api/empleado/getAll?token=" + currentUser.usuario.lastToken;}
        console.log(empleadoVenta);
        let venta = {
            idVenta: null,
            empleado: empleado,
            clave: ""
        };
        let ventaProducto = [];
        productosEnCompra.forEach(function (producto, index)
        {
            let ventaProduct = new Object();
            ventaProduct.producto = producto;
            ventaProduct.precioUnitario = producto.precioVenta;
            ventaProduct.cantidad = document.getElementById("cantidadProducto" + index + "").value;
            ventaProduct.descuento = document.getElementById("descuentoProducto" + index + "").value;
            ventaProducto.push(ventaProduct);
        });
        let datosVP =
                {
                    venta: venta,
                    listaProductos: ventaProducto
                };
        let lastToken = localStorage.getItem("lastToken");
        datos = {
            datosVP: JSON.stringify(datosVP),
            token: lastToken
        };
        param = new URLSearchParams(datos);
        console.log(datos);
        console.log(param);
        fetch("api/venta_producto/createVentaP",
                {
                    method: "POST",
                    headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                    body: param
                })
                .then(response => {
                    return response.json();
                })
                .then(function (data)
                {
                    if (data.exception != null)
                    {
                        Swal.fire('',
                                'Error interno del servidor. Intente nuevamente mas tarde.',
                                'error');
                        limpiarCampos();
                        return;
                    }
                    if (data.error != null)
                    {
                        Swal.fire('', data.error, 'warning');
                        return;
                    }
                    if (data.errorsec != null)
                    {
                        Swal.fire('', data.errorsec, 'error');
                        alert("Regresando al index por error");
                        window.location.replace('index.html');
                        return;
                    }
                    if (data.response != null) {
                        Swal.fire('Realizado', data.response, 'success');
                    }
                    //alert('cargando tabla despues del fetch...');
                    buscarEmpleado();
                    limpiar();
                    actualizarTablaVenta();
                    actualizarBotones();
                    productosEnCompra.splice(0, productosEnCompra.length);
                });
    }
}

export function eliminarLC(index) { //seleccionarProducto
    lentesDeContactoEnCompra.splice(index, 1);
    console.log("Checar");
    let cantidad = [];
    let descuento = [];
    lentesDeContactoEnCompra.forEach(function (lc, index) {
        cantidad.push(document.getElementById("cantidadLC" + index).value);
        descuento.push(document.getElementById("descuentoLC" + index).value);
    });
    console.log(lentesDeContactoEnCompra);
    actualizarTablaVenta();
    lentesDeContactoEnCompra.forEach(function (lc, index) {
        document.getElementById("cantidadLC" + index).value = cantidad[index];
        document.getElementById("descuentoLC" + index).value = descuento[index];
    });
}

export function guardarLC()
{
   lentesDeContactoEnCompra = lentesDeContacto[document.getElementById("selectLC").value];
   cargarTablaLC();
}

export function cargarDatosLC(index)
{
    lentesDeContactoSeleccionado = lentesDeContacto[index];

    document.getElementById("txtPrecioUnitario").value = lentesDeContactoSeleccionado.producto.precioVenta;
    document.getElementById("txtCantidad").max = lentesDeContactoSeleccionado.producto.existencias;
    document.getElementById("txtDescuento").max = 100;

    document.getElementById("txtCantidad").value = 1;
    document.getElementById("txtDescuento").value = 0;

    document.getElementById("txtCantidad").disabled = false;
    document.getElementById("txtDescuento").disabled = false;

    document.getElementById("btnSaveLC").disabled = false;
    document.getElementById("btnCleanLC").disabled = false;
}

export function limpiarCamposLC()
{
    document.getElementById("selectLC").value = "SeleccionarLC";
    document.getElementById("txtCantidad").value = "";
    document.getElementById("txtDescuento").value = "";
    document.getElementById("txtPrecioUnitario").value = "";
    
    document.getElementById("txtCantidad").disabled = true;
    document.getElementById("txtDescuento").disabled = true;

    document.getElementById("btnSaveLC").disabled = true;
    document.getElementById("btnCleanLC").disabled = true;
}

export function limpiar()
{
    console.log(lentesDeContactoEnCompra);
    lentesDeContactoEnCompra.splice(0, lentesDeContactoEnCompra.length);
    actualizarTablaVenta();
    refrescarTabla();
    buscarCliente();
    actualizarBotones();
    actualizarTotalVenta();
}

/**
 * Habilita y deshabilita los botones de Guardar y Limpiar segun si se selecciono
 * @returns {undefined}
 */
export function actualizarBotones()
{
    if (document.getElementById("selectCliente").value >= 1 || lentesDeContactoEnCompra[0] != null)
    {
        document.getElementById("btnClean").disabled = false;
        if (document.getElementById("selectCliente").value >= 1 && lentesDeContactoEnCompra[0] != null) {
            document.getElementById("totalVenta").style.display = "block";
            document.getElementById("btnSave").disabled = false;
        }
    } else
    {
        document.getElementById("totalVenta").style.display = "none";
        document.getElementById("btnClean").disabled = true;
        document.getElementById("btnSave").disabled = true;
    }
}
/**
 * Agrega a la tabla de detalles de la compra un renglon, segun el producto que se haya elegido de la tabla
 * de productos
 * @param {type} indice
 * @returns {undefined}
 */
/**
 * Actualiza la tabla de los detalles de la compra, segun lo que se haya agregado de los productos existentes y activos
 * registrados en la optica
 * @returns {undefined}
 */
export function actualizarTablaLC()
{
    let contenido = "";
    if (lentesDeContactoEnCompra[0] != null) {
        lentesDeContactoEnCompra.forEach(function (lc, index) {
            let registro =
                    "<tr>" +
                    "<td>" + lc.codigoBarras + "</td>" +
                    "<td>" + lc.nombre + "</td>" +
                    "<td> $" + lc.precioVenta + "</td>" +
                    "<td> <input type='number' id='cantidadProducto" + index + "' value='1' name='cantidadProducto" + index + "' oninput='moduloVentaProductos.actualizarTotalVenta();'  min='1' max='" + lc.existencias + "'></td>" +
                    "<td> <input type = 'number' id='descuentoProducto" + index + "' value='0' name='descuentoProducto" + index + "' oninput='moduloVentaProductos.actualizarTotalVenta();'></td>" +
                    "<td><a onclick='moduloVentaProductos.eliminarProducto(" + index + ");'><i class='fa-sharp fa-solid fa-trash'></i></a></td></tr>";
            contenido += registro;
        });
        console.log(lentesDeContactoEnCompra);
        document.getElementById("tblVentaProductos").innerHTML = contenido;
        document.getElementById("totalVenta").style.display = "block";
        actualizarBotones();
        actualizarTotalVenta();
        setDetalleVisible(true);
    } else
    {
        document.getElementById("totalVenta").style.display = "none";
        setDetalleVisible(false);
    }

}

/*export function refrescarTabla() {
 let datos = null;
 let param = null;
 //let url = "api/empleado/getAll?token=" + currentUser.usuario.lastToken;}
 let filtro = document.getElementById("txtBusqueda").value;
 let lastToken = localStorage.getItem("lastToken");
 datos = {
 filtro: filtro,
 token: lastToken
 };
 param = new URLSearchParams(datos);
 fetch("api/venta_producto/getAll",
 {
 method: "POST",
 headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
 body: param
 })
 .then(response => {
 return response.json();
 })
 .then(function (data)
 {
 if (data.exception != null)
 {
 Swal.fire('',
 'Error interno del servidor. Intente nuevamente mas tarde.',
 'error');
 return;
 }
 if (data.error != null)
 {
 Swal.fire('', data.error, 'warning');
 return;
 }
 if (data.errorsec != null)
 {
 Swal.fire('', data.errorsec, 'error');
 alert("Regresando al index por error");
 window.location.replace('index.html');
 return;
 }
 //alert('cargando tabla despues del fetch...');
 cargarTabla(data);
 });
 }
 
 export function actualizarTotalVenta()
 {
 if (validarDescuento() && validarExistencias()) {
 let totalVenta = 0;
 let subtotalVenta = 0;
 let descuento = 0;
 let totalPresupuesto = 0;
 lentesDeContactoEnCompra.forEach(function (lc, index) {
 totalPresupuesto += parseInt(document.getElementById("cantidadLC" + index + "").value);
 subtotalVenta += (lc.precioVenta * document.getElementById("cantidadLC" + index + "").value);
 descuento += parseFloat(document.getElementById("descuentoLC" + index + "").value);
 });
 totalVenta += subtotalVenta - descuento;
 console.log(totalVenta);
 document.getElementById("totalPresupuesto").value = totalPresupuesto;
 document.getElementById("subtotalCosto").value = "$ " + subtotalVenta;
 document.getElementById("descuentoCosto").value = "$ " + descuento;
 document.getElementById("totalCosto").value = "$ " + totalVenta;
 
 } else {
 
 console.log(totalVenta);
 document.getElementById("totalCosto").value = "Checa los valores de la tabla";
 }
 
 }
 
 export function cargarTabla(data) { // Carga en la tabla los datos que se envien
 let contenido = "";
 lentesDeContacto = data;
 lentesDeContacto.forEach(function (lc, index) {
 let registro =
 "<tr>" +
 "<td>" + lc.codigoBarras + "</td>" +
 "<td>" + lc.nombre + "</td>" +
 "<td> $" + lc.precioVenta + "</td>" +
 "<td>" + lc.existencias + "</td>" +
 "<td><a onclick='moduloVentaProductos.seleccionarProducto(" + index + ");'><i class='fa fa-plus-square'></i></a></td></tr>";
 contenido += registro;
 });
 console.log(productos);
 document.getElementById("tblProductos").innerHTML = contenido;
 }
 */

export function validarExistencias() {
    let valid = true;
    lentesDeContactoEnCompra.forEach(function (lc, index) {
        let id = document.getElementById("cantidadLC" + index + "");
        let existencias = parseInt(id.value);
        if (!Number.isInteger(existencias)) {
            notificacion(id, "Ingrese una cantidad válida");
            id.focus();
            valid = false;
        } else
        if (existencias > lc.existencias) {
            notificacion(id, "La cantidad supera a las existencias");
            id.focus();
            valid = false;
        }

    });
    return valid;
}


export function validarDescuento() {
    let valid = true;
    lentesDeContactoEnCompra.forEach(function (lc, index) {
        let id = document.getElementById("descuentoLC" + index + "");
        let descuento = parseFloat(id.value);
        if (Number.isNaN(descuento)) {
            notificacion(id, "Ingrese una cantidad válida");
            id.focus();
            valid = false;
        } else
        if (descuento > ((lc.precioVenta * document.getElementById("cantidadLC" + index).value) / 2)) {
            notificacion(id, "El descuento supera al maximo descuento (50%)");
            id.focus();
            valid = false;
        }
    });
    return valid;
}

export function notificacion(elm, msg) {
// agrega un elemento p con el texto luego del input seleccionado
    if (!$(elm).next().is("p")) {
        $(elm).after(`<p style='color: red;'><strong><small>${msg}</small></strong></p>`);
        // elimina el mensaje luego de 2 segundos
        setTimeout(function () {
            $(elm).next().remove();
        }, 3000);
    }
}