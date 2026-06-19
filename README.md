# Examen2p_AndeFin_Mena_Panata

## Examen Conjunto - Segundo Parcial
### Arquitectura de Software
### Universidad de las Fuerzas Armadas - ESPE

---

## Integrantes

| Nombre | Correo | Rol |
|--------|--------|-----|
| James Steve Mena Pérez | jsmena@espe.edu.ec | Desarrollador Backend |
| Kevin Panata | kipanata@espe.edu.ec | Desarrollador Backend |

---

## Tabla de Contenidos

1. [Descripción del Proyecto](#descripcion-del-proyecto)
2. [Arquitectura del Sistema](#arquitectura-del-sistema)
3. [Tecnologías Utilizadas](#tecnologias-utilizadas)
4. [Estructura del Proyecto](#estructura-del-proyecto)
5. [Base de Datos](#base-de-datos)
6. [Endpoints](#endpoints)
7. [Relación entre Componentes](#relacion-entre-componentes)
8. [Instalación y Ejecución](#instalacion-y-ejecucion)
9. [Datos de Prueba](#datos-de-prueba)
10. [Ejemplos de Peticiones](#ejemplos-de-peticiones)
11. [Licencia](#licencia)
12. [Créditos](#creditos)

---

## Descripción del Proyecto

AndesFin es una plataforma digital de microinversiones para clientes minoristas (personas naturales) que desean invertir montos entre $50 y $2,000 sin requerir asesoría financiera presencial. Este microservicio permite:

- Consultar usuarios registrados en la plataforma.
- Consultar productos financieros disponibles (bonos, fondos indexados, crowdfunding, etc.).
- Realizar simulaciones de inversión que calculan automáticamente la combinación óptima de productos que maximiza las ganancias, respetando el capital disponible.
- Consultar simulaciones anteriores por usuario para trazabilidad y auditoría.

### Objetivo del Examen

Implementar un microservicio robusto que:

1. Calcule combinaciones óptimas de productos financieros según el capital disponible.
2. Persista las simulaciones con todos los detalles para auditoría y trazabilidad.
3. Precargue datos iniciales (usuarios y productos) al levantar la aplicación.
4. Documente la API con Swagger/OpenAPI para facilitar su uso.

---


## Tecnologias Utilizadas

| Tecnologia | Version | Proposito |
|------------|---------|-----------|
| Java | 21 | Lenguaje de programacion |
| Spring Boot | 4.1.0 | Framework principal |
| Spring Data JPA | - | ORM para acceso a datos |
| Spring Web | - | APIs RESTful |
| Spring Validation | - | Validacion de datos |
| PostgreSQL | 16 | Base de datos relacional |
| Lombok | - | Reduccion de codigo boilerplate |
| OpenAPI (Swagger) | 2.8.17 | Documentacion interactiva de API |
| Maven | 3.9+ | Gestor de dependencias y build |
| Docker | 24+ | Contenerizacion de la base de datos |
| Docker Compose | 2+ | Orquestacion de contenedores |

## Base de Datos

### Configuracion con Docker Compose

La base de datos PostgreSQL esta contenerizada para facilitar la ejecucion.

**docker-compose.yml:**
```yaml
version: '3.8'

services:
  postgres:
    image: postgres:16-alpine
    container_name: andefin-db
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: andefin_db
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    restart: unless-stopped

volumes:
  postgres_data:
Conexion a la Base de Datos
application.properties:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/andefin_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
server.port=8080

## Estructura del Proyecto
andefin/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── ec/
│ │ │ └── edu/
│ │ │ └── espe/
│ │ │ └── andefin/
│ │ │ ├── AndesFinApplication.java
│ │ │ ├── controller/
│ │ │ │ ├── UsuarioController.java
│ │ │ │ ├── ProductoController.java
│ │ │ │ └── SimulacionController.java
│ │ │ ├── service/
│ │ │ │ ├── UsuarioService.java
│ │ │ │ ├── ProductoService.java
│ │ │ │ └── SimulacionService.java
│ │ │ ├── repository/
│ │ │ │ ├── UsuarioRepository.java
│ │ │ │ ├── ProductoRepository.java
│ │ │ │ └── SimulacionRepository.java
│ │ │ ├── entity/
│ │ │ │ ├── Usuario.java
│ │ │ │ ├── ProductoFinanciero.java
│ │ │ │ └── Simulacion.java
│ │ │ ├── dto/
│ │ │ │ ├── UsuarioDTO.java
│ │ │ │ ├── ProductoDTO.java
│ │ │ │ ├── SimulacionRequestDTO.java
│ │ │ │ └── SimulacionResponseDTO.java
│ │ │ └── config/
│ │ │ └── DataLoader.java
│ │ └── resources/
│ │ └── application.properties
│ └── test/
│ └── java/
│ └── ec/
│ └── edu/
│ └── espe/
│ └── andefin/
│ └── AndesFinApplicationTests.java
├── docker-compose.yml
├── README.md
└── pom.xml

text


## Arquitectura del Sistema
┌─────────────────────────────────────────────────────────────────────┐
│                                                                     │
│                      CLIENTE (Postman / Frontend)                   │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────┐
│                                                                     │
│                         MICROSERVICIO ANDESFIN                      │
│                                                                     │
│  ┌───────────────────────────────────────────────────────────────┐  │
│  │                                                               │  │
│  │    ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │  │
│  │    │   USUARIO    │  │   PRODUCTO   │  │    SIMULACION    │  │  │
│  │    │  CONTROLLER  │  │  CONTROLLER  │  │    CONTROLLER    │  │  │
│  │    │              │  │              │  │                  │  │  │
│  │    │  GET /       │  │  GET /       │  │  POST /          │  │  │
│  │    │  usuarios    │  │  productos   │  │  GET /{usuarioId}│  │  │
│  │    └──────────────┘  └──────────────┘  └──────────────────┘  │  │
│  │            │                 │                   │            │  │
│  │            ▼                 ▼                   ▼            │  │
│  │    ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │  │
│  │    │   USUARIO    │  │   PRODUCTO   │  │    SIMULACION    │  │  │
│  │    │   SERVICE    │  │   SERVICE    │  │    SERVICE       │  │  │
│  │    │              │  │              │  │                  │  │  │
│  │    │  CRUD        │  │  CRUD        │  │  - Optimizacion  │  │  │
│  │    │  Usuarios    │  │  Productos   │  │  - Algoritmo     │  │  │
│  │    │              │  │              │  │    mochila       │  │  │
│  │    │              │  │              │  │  - Persistencia  │  │  │
│  │    └──────────────┘  └──────────────┘  └──────────────────┘  │  │
│  │            │                 │                   │            │  │
│  │            ▼                 ▼                   ▼            │  │
│  │    ┌──────────────────────────────────────────────────────┐   │  │
│  │    │                REPOSITORIOS (JPA)                   │   │  │
│  │    │                                                      │   │  │
│  │    │  ┌────────────────┐  ┌────────────────┐  ┌───────┐  │   │  │
│  │    │  │UsuarioRepository│  │ProductoRepos- │  │Simula-│  │   │  │
│  │    │  │                │  │itory          │  │cionRep│  │   │  │
│  │    │  └────────────────┘  └────────────────┘  └───────┘  │   │  │
│  │    └──────────────────────────────────────────────────────┘   │  │
│  │                                                               │  │
│  └───────────────────────────────────────────────────────────────┘  │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────┐
│                                                                     │
│                       BASE DE DATOS PostgreSQL                      │
│                                                                     │
│  ┌──────────────────┐  ┌──────────────────┐  ┌──────────────────┐  │
│  │     USUARIOS     │  │  PRODUCTOS_      │  │   SIMULACIONES   │  │
│  │                  │  │  FINANCIEROS     │  │                  │  │
│  │  - id (UUID)     │  │                  │  │  - id (UUID)     │  │
│  │  - nombre        │  │  - id (UUID)     │  │  - usuario_id    │  │
│  │  - email (unico) │  │  - nombre        │  │    (FK)          │  │
│  │  - capital_      │  │  - descripcion   │  │  - fecha_        │  │
│  │    disponible    │  │  - costo         │  │    simulacion    │  │
│  │                  │  │  - porcentaje_   │  │  - capital_      │  │
│  │                  │  │    retorno       │  │    disponible    │  │
│  │                  │  │  - activo        │  │  - ganancia_     │  │
│  │                  │  │                  │  │    total         │  │
│  │                  │  │                  │  │  - costo_total   │  │
│  │                  │  │                  │  │  - capital_      │  │
│  │                  │  │                  │  │    restante      │  │
│  │                  │  │                  │  │  - retorno_      │  │
│  │                  │  │                  │  │    porcentaje    │  │
│  │                  │  │                  │  │  - productos_    │  │
│  │                  │  │                  │  │    seleccionados │  │
│  │                  │  │                  │  │    _json         │  │
│  │                  │  │                  │  │  - mensaje       │  │
│  └──────────────────┘  └──────────────────┘  └──────────────────┘  │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘

### Diagrama de Relaciones (ER)
┌─────────────────────┐          ┌─────────────────────────────────┐
│       USUARIO       │          │          SIMULACION             │
├─────────────────────┤          ├─────────────────────────────────┤
│  id (PK)            │──────────│  id (PK)                       │
│  nombre             │          │  usuario_id (FK)               │
│  email (UK)         │          │  fecha_simulacion              │
│  capital_disponible │          │  capital_disponible            │
└─────────────────────┘          │  ganancia_total                │
                                 │  costo_total                   │
                                 │  capital_restante              │
                                 │  retorno_porcentaje            │
                                 │  productos_seleccionados_json  │
                                 │  mensaje                       │
                                 └─────────────────────────────────┘

┌─────────────────────────────────┐
│       PRODUCTO_FINANCIERO       │
├─────────────────────────────────┤
│  id (PK)                       │
│  nombre                        │
│  descripcion                   │
│  costo                         │
│  porcentaje_retorno            │
│  activo                        │
└─────────────────────────────────┘
---
## Endpoints
Resumen de Endpoints
Metodo	Endpoint	Descripcion
GET	/usuarios	Listar todos los usuarios registrados
GET	/productos	Listar todos los productos activos
POST	/simulaciones	Realizar una simulacion de inversion
GET	/simulaciones/{usuarioId}	Consultar simulaciones por usuario
1. GET /usuarios
Lista todos los usuarios registrados en la plataforma.

Respuesta:

json
[
  {
    "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "nombre": "Juan Perez",
    "email": "juan.perez@email.com",
    "capitalDisponible": 5000.00
  },
  {
    "id": "b2c3d4e5-f6g7-8901-bcde-f23456789012",
    "nombre": "Maria Garcia",
    "email": "maria.garcia@email.com",
    "capitalDisponible": 8000.00
  }
]
2. GET /productos
Lista todos los productos financieros activos.

Respuesta:

json
[
  {
    "id": "c3d4e5f6-g7h8-9012-cdef-345678901234",
    "nombre": "Fondo Acciones Tech",
    "descripcion": "Fondo de inversion en acciones tecnologicas",
    "costo": 1000.00,
    "porcentajeRetorno": 8.50,
    "activo": true
  },
  {
    "id": "d4e5f6g7-h8i9-0123-defg-456789012345",
    "nombre": "Bonos Corporativos AAA",
    "descripcion": "Bonos corporativos de alta calificacion",
    "costo": 500.00,
    "porcentajeRetorno": 5.25,
    "activo": true
  }
]
3. POST /simulaciones
Realiza una simulacion de inversion. La aplicacion evalua todas las combinaciones posibles y selecciona la que maximiza las ganancias.

Request Body:

json
{
  "usuarioId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "capitalDisponible": 3000.00,
  "productos": [
    {
      "nombre": "Fondo Acciones Tech",
      "precio": 1000.00,
      "porcentajeGanancia": 8.50
    },
    {
      "nombre": "Bonos Corporativos AAA",
      "precio": 500.00,
      "porcentajeGanancia": 5.25
    },
    {
      "nombre": "ETF Global",
      "precio": 1500.00,
      "porcentajeGanancia": 12.00
    },
    {
      "nombre": "Fondo de Dividendos",
      "precio": 800.00,
      "porcentajeGanancia": 6.75
    }
  ]
}
Respuesta (Optimizacion Exitosa):

json
{
  "id": "f6g7h8i9-j0k1-2345-fghi-678901234567",
  "usuarioId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "fechaSimulacion": "2024-01-15T10:30:00Z",
  "capitalDisponible": 3000.00,
  "productosSeleccionados": [
    {
      "nombre": "ETF Global",
      "precio": 1500.00,
      "porcentajeGanancia": 12.00,
      "gananciaEsperada": 180.00
    },
    {
      "nombre": "Fondo Acciones Tech",
      "precio": 1000.00,
      "porcentajeGanancia": 8.50,
      "gananciaEsperada": 85.00
    }
  ],
  "costoTotal": 2500.00,
  "capitalRestante": 500.00,
  "gananciaTotal": 265.00,
  "retornoTotalPorcentaje": 10.60,
  "eficienciaCapital": 83.33,
  "mensaje": "Simulacion exitosa con ganancias optimas"
}
Respuesta (Fondos Insuficientes):

json
{
  "id": "h8i9j0k1-l2m3-4567-hijk-890123456789",
  "usuarioId": "c3d4e5f6-g7h8-9012-cdef-345678901234",
  "fechaSimulacion": "2024-01-15T12:00:00Z",
  "capitalDisponible": 500.00,
  "productosSeleccionados": [],
  "costoTotal": 0.00,
  "capitalRestante": 500.00,
  "gananciaTotal": 0.00,
  "retornoTotalPorcentaje": 0.00,
  "eficienciaCapital": 0.00,
  "mensaje": "Fondos insuficientes: capital $500.00 es insuficiente para adquirir cualquier producto. Producto mas barato: $1200.00"
}
4. GET /simulaciones/{usuarioId}
Consulta todas las simulaciones realizadas por un usuario.

Respuesta:

json
[
  {
    "id": "f6g7h8i9-j0k1-2345-fghi-678901234567",
    "usuarioId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "fechaSimulacion": "2024-01-15T10:30:00Z",
    "capitalDisponible": 3000.00,
    "gananciaTotal": 265.00,
    "retornoTotalPorcentaje": 10.60,
    "mensaje": "Simulacion exitosa con ganancias optimas"
  },
  {
    "id": "h8i9j0k1-l2m3-4567-hijk-890123456789",
    "usuarioId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "fechaSimulacion": "2024-01-14T14:20:00Z",
    "capitalDisponible": 2000.00,
    "gananciaTotal": 150.75,
    "retornoTotalPorcentaje": 7.54,
    "mensaje": "Simulacion con ganancias minimas. Considere aumentar capital para mejores opciones."
  }
]

## Ciclo de Vida de la Simulacion

┌─────────────────────────────────────────────────────────────────────┐
│                                                                     │
│   ┌──────────────┐     ┌──────────────┐     ┌──────────────────┐  │
│   │   USUARIO    │────▶│  SIMULACION  │────▶│ PRODUCTOS        │  │
│   │              │     │              │     │ SELECCIONADOS    │  │
│   │ - id         │     │ - id         │     │                  │  │
│   │ - nombre     │     │ - usuario_id │     │ - nombre         │  │
│   │ - email      │     │ - fecha      │     │ - precio         │  │
│   │ - capital    │     │ - capital    │     │ - % ganancia     │  │
│   │              │     │ - ganancia   │     │ - ganancia_esp   │  │
│   └──────────────┘     │ - costo      │     └──────────────────┘  │
│                        │ - restante   │                          │
│                        │ - retorno %  │                          │
│                        │ - json       │                          │
│                        │ - mensaje    │                          │
│                        └──────────────┘                          │
│                                                                     │
│   Cada simulacion queda registrada con evidencia de todos los      │
│   calculos realizados para auditoria y trazabilidad.              │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
## DIAGRAMA DE SIMULACION 

┌─────────────────────────────────────────────────────────────────────┐
│                                                                     │
│  1. Cliente envia POST /simulaciones                                │
│                        │                                            │
│                        ▼                                            │
│  2. SimulacionController recibe la peticion                        │
│                        │                                            │
│                        ▼                                            │
│  3. SimulacionService valida el usuario                            │
│     (llama a UsuarioService)                                       │
│                        │                                            │
│                        ▼                                            │
│  4. SimulacionService ejecuta el algoritmo de optimizacion:        │
│     ├── Filtra productos viables (precio <= capital)              │
│     ├── Calcula ganancia por producto                              │
│     ├── Aplica algoritmo de mochila                                │
│     │   (selecciona combinacion optima)                           │
│     └── Calcula metricas:                                          │
│         - costo_total                                              │
│         - ganancia_total                                           │
│         - retorno                                                  │
│         - eficiencia                                               │
│                        │                                            │
│                        ▼                                            │
│  5. SimulacionService construye la respuesta                       │
│     (SimulacionResponseDTO)                                        │
│                        │                                            │
│                        ▼                                            │
│  6. SimulacionService persiste la simulacion:                      │
│     ├── Crea entidad Simulacion                                   │
│     ├── Convierte productos seleccionados a JSON                  │
│     └── Guarda en PostgreSQL usando SimulacionRepository          │
│                        │                                            │
│                        ▼                                            │
│  7. Retorna respuesta al cliente                                   │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
Instalacion y Ejecucion

Prerrequisitos
Java 17 o superior

Maven 3.9+

Docker y Docker Compose (para la base de datos)

Postman o similar (para probar endpoints)

1. Clonar el Repositorio
bash
git clone https://github.com/jsmena5/Examen2p_AndeFin_Mena_Panata.git
cd Examen2p_AndeFin_Mena_Panata
2. Levantar la Base de Datos con Docker
bash
docker-compose up -d
docker ps
3. Ejecutar el Microservicio
bash
./mvnw spring-boot:run

Licencia
Este proyecto fue desarrollado con fines academicos para la asignatura Arquitectura de Software de la Universidad de las Fuerzas Armadas - ESPE.

Creditos
Integrante	Contribucion
James Mena	Desarrollo Backend
Kevin Panata	Desarrollo Backend
