# CRUD de Productos con Spring Boot

API REST sencilla para crear, leer, actualizar y eliminar productos (CRUD). Usa Spring Boot, Spring Data JPA y una base de datos H2 en memoria.

## Tecnologías

- Java 21
- Spring Boot 4.1.1 (Web MVC, Data JPA)
- H2 Database (en memoria) con consola web
- Maven (incluye Maven Wrapper, no hace falta instalar Maven)

## Estructura del proyecto

```
src/main/java/com/example/crud/
├── CrudApplication.java       # Punto de arranque de la aplicación
├── Producto.java              # Entidad JPA (tabla "producto")
├── ProductoRepository.java    # Acceso a datos (extiende JpaRepository)
└── ProductoController.java    # Endpoints REST en /api/productos
src/main/resources/
└── application.properties     # Configuración (H2, consola, logs SQL)
```

## Requisitos

- **JDK 21** o superior instalado. Compruébalo con:

  ```bash
  java -version
  ```

## Levantar el servidor

Desde la carpeta raíz del proyecto (donde está `pom.xml`):

**Windows (PowerShell o CMD):**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux / macOS / Git Bash:**

```bash
./mvnw spring-boot:run
```

La primera vez tardará un poco porque descarga las dependencias. Cuando veas en la consola algo como `Tomcat started on port 8080`, el servidor estará listo en:

```
http://localhost:8080
```

Para detenerlo, pulsa `Ctrl + C`.

### Alternativa: generar un JAR y ejecutarlo

```bash
./mvnw clean package
java -jar target/crud-0.0.1-SNAPSHOT.jar
```

(En Windows usa `.\mvnw.cmd clean package`.)

## Endpoints de la API

Todas las rutas empiezan por `/api/productos`.

| Método | Ruta                   | Descripción                     | Respuesta                    |
|--------|------------------------|---------------------------------|------------------------------|
| GET    | `/api/productos`       | Lista todos los productos       | 200 + lista JSON             |
| GET    | `/api/productos/{id}`  | Obtiene un producto por id      | 200 o 404 si no existe       |
| POST   | `/api/productos`       | Crea un producto                | 200 + producto creado con id |
| PUT    | `/api/productos/{id}`  | Actualiza nombre y precio       | 200 o 404 si no existe       |
| DELETE | `/api/productos/{id}`  | Elimina un producto             | 204 o 404 si no existe       |

Formato JSON de un producto:

```json
{
  "id": 1,
  "nombre": "Teclado",
  "precio": 25.5
}
```

### Ejemplos con curl

> En PowerShell usa `curl.exe` en lugar de `curl` para evitar el alias de `Invoke-WebRequest`.

```bash
# Crear
curl -X POST http://localhost:8080/api/productos -H "Content-Type: application/json" -d "{\"nombre\":\"Teclado\",\"precio\":25.5}"

# Listar
curl http://localhost:8080/api/productos

# Obtener uno
curl http://localhost:8080/api/productos/1

# Actualizar
curl -X PUT http://localhost:8080/api/productos/1 -H "Content-Type: application/json" -d "{\"nombre\":\"Teclado mecánico\",\"precio\":49.99}"

# Eliminar
curl -X DELETE http://localhost:8080/api/productos/1
```

También puedes probar la API con Postman, Insomnia o la extensión REST Client de VS Code.

## Consola de la base de datos H2

Con el servidor en marcha, abre:

```
http://localhost:8080/h2-console
```

Datos de conexión:

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** *(vacío)*

Ahí puedes consultar la tabla con `SELECT * FROM PRODUCTO;`.

> ⚠️ La base de datos es **en memoria**: todos los datos se pierden al detener el servidor.

## Ejecutar los tests

```bash
./mvnw test
```
