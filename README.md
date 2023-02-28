# pruebaUsuariosTCM
Prueba back crud

Ricardo Avendaño Casas

## Descripción general
Crud de tablas usuario y tarea.
Registro de tareas por usuario. El desarrollo determina que tareas están pendientes por realizar y cuales ya están realizadas dependiendo de la fecha de ejecución y fecha de cierre.
Se creo una base de datos que contiene la información de dichas tareas y usuarios. Una tarea se le puede asignar sólo un usuario (relación de muchos a uno). Un usuario puede estar asignado a más de una tarea.
Se realiza cálculo de los días de retraso de la ejecución respecto al día de hoy (si no hay retraso se marca 0 (cero)), y quién está asignado a dicha tarea.

## Generalidades tecnológicas
1. Java 11
2. Maven
3. JUnit, Cobertura de código 93.4%
4. Base de datos H2
5. Swagger
6. SpringBoot
7. Manejo de errores y excepciones
8. Esquema de branch
        Rama principal: main
        Rama desarrollo: develop
        Ramas desarrollo (features): feature/crud

## Para ejecutar el proyecto se deben realizar los siguientes pasos
1. Descargar fuente de github: git clone git clone https://github.com/ricardoavendano/pruebaUsuariosTCM.git
2. Ir al directorio donde se encuentra el fuente y crear jar: mvn clean install (se crea la carpeta target)
3. Ir al directorio donde se encuentra el fuente y dirigirse a la carpeta target; por línea de comandos ejecutar jar: java -jar prueba-0.0.1-SNAPSHOT.jar
4. La aplicación ya se encuentra desplegada localmente en la url (http://localhost:8080)
5. Ingreso a la BD H2
    url: http://localhost:8080/pruebaTCM/h2-console/login.jsp
    JDBC URL: jdbc:h2:mem:pruebaTCM
    User name: pruebaTCM
    Password: pruebaTCM
    
    Tablas: TAREA, USUARIO
7. Uso de Swagger
    http://localhost:8080/pruebaTCM/swagger-ui.html#/


## Servicios expuestos
1. Crear/modificar usuario

```bash
curl --location --request POST 'http://localhost:8080/pruebaTCM/crearUsuario' \
--header 'Content-Type: application/json' \
--data-raw '{
    "idUsuario": 0,
    "nombre": "prueba"
}'
```

Response
```json
{
    "code": "200",
    "description": "Usuario creado con exito",
    "status": "OK"
}
```
2. Listar usuarios

```bash
curl --location --request GET 'http://localhost:8080/pruebaTCM/listarUsuario' \
--data-raw ''
```

Response
```json
[
    {
        "idUsuario": 1,
        "nombre": "Ricardo"
    },
    {
        "idUsuario": 2,
        "nombre": "Juan"
    },
    {
        "idUsuario": 3,
        "nombre": "Pedro"
    },
    {
        "idUsuario": 4,
        "nombre": "Jose"
    },
    {
        "idUsuario": 5,
        "nombre": "Carlos"
    },
    {
        "idUsuario": 6,
        "nombre": "prueba"
    }
]
```
3. Crear/editar tarea

```batch
curl --location --request POST 'http://localhost:8080/pruebaTCM/crearTarea' \
--header 'Content-Type: application/json' \
--data-raw '{
    "idTarea": 0,
    "estado": 2,
    "fechaEjecucion": "2023-02-20",
    "fechaCierre": "2023-02-28",
    "diasRetraso": 0,
    "idUsuario": {
        "idUsuario": 1,
        "nombre": "tarea nueva"
    }
}'
```
Response
```json
{
    "code": "200",
    "description": "Tarea creada con exito",
    "status": "OK"
}
```
4. Eliminar tarea

```batch
curl --location --request GET 'http://localhost:8080/pruebaTCM/eliminarTarea/5' \
--data-raw ''
```
Response
```json
{
    "code": "200",
    "description": "Tarea eliminada con exito",
    "status": "OK"
}
```
5. Listar todas las tareas

```batch
curl --location --request GET 'http://localhost:8080/pruebaTCM/listarTodasTareas' \
--data-raw ''
```
Response
```json
[
    {
        "idTarea": 1,
        "estado": "Tarea terminada",
        "fechaEjecucion": "2023-02-01",
        "fechaCierre": "2023-02-05",
        "diasRetraso": 0,
        "idUsuario": {
            "idUsuario": 1,
            "nombre": "Ricardo"
        }
    },
    {
        "idTarea": 2,
        "estado": "Tarea pendiente",
        "fechaEjecucion": "2023-02-02",
        "fechaCierre": null,
        "diasRetraso": 26,
        "idUsuario": {
            "idUsuario": 2,
            "nombre": "Juan"
        }
    },
    {
        "idTarea": 3,
        "estado": "Tarea terminada",
        "fechaEjecucion": "2023-02-04",
        "fechaCierre": "2023-02-16",
        "diasRetraso": 0,
        "idUsuario": {
            "idUsuario": 3,
            "nombre": "Pedro"
        }
    },
    {
        "idTarea": 4,
        "estado": "Tarea pendiente",
        "fechaEjecucion": "2023-02-28",
        "fechaCierre": null,
        "diasRetraso": 0,
        "idUsuario": {
            "idUsuario": 4,
            "nombre": "Jose"
        }
    }
]
```
6. Listar tareas donde la fecha ingresada por parámetro se encuentre dentro de la fecha de ejecución y fecha de cierre 

```batch
curl --location --request GET 'http://localhost:8080/pruebaTCM/listarTareasUsuarioFecha?fecha=2023-02-04'
```
Response
```json
[
    {
        "idTarea": 1,
        "estado": "Tarea terminada",
        "fechaEjecucion": "2023-02-01",
        "fechaCierre": "2023-02-05",
        "diasRetraso": 0,
        "idUsuario": {
            "idUsuario": 1,
            "nombre": "Ricardo"
        }
    },
    {
        "idTarea": 2,
        "estado": "Tarea pendiente",
        "fechaEjecucion": "2023-02-02",
        "fechaCierre": null,
        "diasRetraso": 26,
        "idUsuario": {
            "idUsuario": 2,
            "nombre": "Juan"
        }
    },
    {
        "idTarea": 3,
        "estado": "Tarea terminada",
        "fechaEjecucion": "2023-02-04",
        "fechaCierre": "2023-02-16",
        "diasRetraso": 0,
        "idUsuario": {
            "idUsuario": 3,
            "nombre": "Pedro"
        }
    }
]
```


