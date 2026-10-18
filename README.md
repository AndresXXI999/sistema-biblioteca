# Sistema de Gestión de Biblioteca

Proyecto de la clase de Diseño Web. Este sistema resuelve el problema de la biblioteca del campus, la cual manejaba sus préstamos en hojas de Excel (lo que provocaba libros perdidos, fechas de devolución olvidadas y falta de control sobre la disponibilidad).

La aplicación permite a los encargados administrar el catálogo, los socios y los préstamos, mientras que los socios pueden consultar la disponibilidad de los libros y revisar su propio historial.

## Repositorio
https://github.com/AndresXXI999/sistema-biblioteca

## Integrantes
- Andrés (AndresXXI999)
- Jafet (JafetFong)
- Byron (bpinedal1-maker)
- Brandon (bmazariegoss2-boop)

## Funcionalidades Principales
El sistema cuenta con dos roles: **Admin** (encargado) y **Socio**.

- **Catálogo de libros:** Búsqueda por título, autor, ISBN y categoría. Control de disponibilidad en tiempo real.
- **Gestión de socios:** Registro de estudiantes y docentes con datos de contacto y creación de sus credenciales de acceso.
- **Préstamos:** Registro de salida de libros calculando automáticamente la fecha límite (7 días) y registro de devoluciones.
- **Multas automáticas:** Cálculo de multas por días de retraso. El monto por día es configurable por el administrador (ej. Q.5/día) y se registra el pago de las mismas.
- **Dashboard del encargado:** Panel con los 5 libros más prestados históricamente, lista de socios con deudas pendientes y el estado de disponibilidad de todos los libros.
- **Historial:** Los socios pueden ver su propio historial de préstamos, y el admin puede consultar el historial de cualquier socio.
- **Reporte PDF:** Exportación en PDF del reporte de préstamos activos (exclusivo para el admin).

## Tecnologías
- Java 17
- Spring Boot 4.0.8
- MySQL 8.0
- Thymeleaf (HTML renderizado en el servidor)
- Maven

## Cómo correr la app
1. Tener JDK 17 y MySQL 8.0 instalados.
2. Copiar `application-local.properties.example` a
   `application-local.properties` y poner la contraseña de su MySQL.
3. Ejecutar `./mvnw spring-boot:run` (Linux/Mac) o
   `mvnw.cmd spring-boot:run` (Windows).
4. Abrir http://localhost:8080 en el navegador.

## Script de Base de Datos
En la raíz del proyecto se incluye el archivo `script_base_de_datos.sql`.
Este script contiene la estructura de las tablas y datos de prueba.
Si se desea iniciar la base de datos desde cero, se puede importar este
script en MySQL. De lo contrario, la aplicación creará las tablas
automáticamente al arrancar (spring.jpa.hibernate.ddl-auto=update).

## Entorno verificado
- Brandon Mazariegos - Windows 11, JDK 17, MySQL 8.0
