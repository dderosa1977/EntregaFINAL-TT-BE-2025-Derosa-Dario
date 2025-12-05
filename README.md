# EntregaFINAL-TT-BackEnd-2025-Derosa-Dario

Escenarios de Prueba en Postman (CRUD + Excepciones):

1. Pruebas de Búsqueda y Lectura (método GET)

Buscar Producto por ID:	http://localhost:8080/products/1  >> 200 OK. Devuelve el JSON del producto 1.

Buscar ID Inexistente:	http://localhost:8080/products/999  >> 400 Bad Request. El try-catch atrapa el error y devuelve el mensaje: "Error: No se encontró el producto con ID: 999".

Listar Todos:	http://localhost:8080/products  >> 200 OK. Devuelve una lista JSON de todos los productos (la base de datos).

Filtro por Nombre:	http://localhost:8080/products?name=aspiradora  >> 200 OK. Devuelve solo los productos que contienen "aspiradora" en el nombre.

2. Pruebas de Creación (método POST) (validaciones sobre ProductService. El Controller usa try-catch para esto)

Creación Exitosa: http://localhost:8080/products  >> {name: "Libro", description: "Libro de Java", price: 25.50, category: "Libros", stock: 10}	200 OK. Devuelve el producto creado con su ID.

Validación de Precio Negativo:	http://localhost:8080/products  >> {..., price: -10.0, ...}, 400 Bad Request. Mensaje: "Error de validación: El precio no puede ser negativo ni nulo."

Validación de Stock Negativo:	http://localhost:8080/products  >> {..., price: 5.0, stock: -5}, 400 Bad Request. Mensaje: "El stock no puede ser negativo ni nulo."

3. Pruebas de Modificación/Edición (método PUT) (edición de todos los campos y la captura de errores. Usaremos un producto existente (ej: ID 2).

Editar SOLO el Stock: http://localhost:8080/products/2  >> {"stock": 500}, 200 OK. El producto 2 ahora tiene 500 unidades. Todos los demás campos (nombre, precio, etc.) permanecen iguales.

Editar TODOS los Campos: http://localhost:8080/products/2  >> {name: "Laptop Nueva", price: 1200.0, category: "Gaming", stock: 10, description: "Potente"}, 200 OK. Actualiza todos.

Intentar Editar ID Inexistente: http://localhost:8080/products/999  >> {name: "Test"}, 400 Bad Request. Mensaje: "No se pudo editar: Error: No se encontró el producto con ID: 999"

Intentar Editar Precio a Negativo: http://localhost:8080/products/2  >> {"price": -0.01}, 400 Bad Request. Mensaje: "No se pudo editar: No puedes actualizar a un precio negativo."

4. Pruebas de Eliminación (método DELETE)

Eliminar Exitoso: http://localhost:8080/products/1  >> 200 OK. Devuelve el producto que fue eliminado.

Intentar Eliminar ID Inexistente: http://localhost:8080/products/999  >> 400 Bad Request. Mensaje: "No se pudo eliminar el producto con ID 999" o similar (si falla al buscarlo).

NOTA:

Edición Completa: el método del Servicio "editProductById" verifica campo por campo (name, description, price, stock, category). Si el dato viene en el JSON, lo actualiza; si no, deja el original.

Manejo de Errores (Try-Catch): bloques try-catch implementados en el Controlador. Si se intenta crear un producto con precio negativo (que validé en el servicio), el sistema no explota con un error 500 feo, sino que atrapa la excepción y devuelve un mensaje claro al usuario (Error 400).



