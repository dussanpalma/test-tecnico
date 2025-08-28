# Farmatodo API

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Coverage](https://img.shields.io/badge/coverage-88%25-blue)

## Descripción

Farmatodo API es un sistema de gestión de clientes, productos, carritos y pagos que simula operaciones de e-commerce con tarjetas de crédito tokenizadas. Incluye endpoints seguros, logs centralizados, notificaciones por correo, y se despliega fácilmente usando Docker y Cloud Run.

Repositorio público: [https://github.com/dussanpalma/test-tecnico](https://github.com/dussanpalma/test-tecnico)

---

## Requerimientos Funcionales

### 1. Tokenización de Tarjetas de Crédito
- Componente que recibe los datos de tarjeta (número, CVV, expiración) y devuelve un token único. ✅
- API autenticada mediante API Key o Secret Key. ✅
- Configuración de probabilidad de rechazo al crear tokens. ✅

### 2. Ping API
- Endpoint `/ping` retorna `pong` con código HTTP 200 para verificar disponibilidad. ✅

### 3. Gestión de Clientes
- Registro de clientes con datos básicos: nombre, email, teléfono, dirección. ✅
- Validación de unicidad y consistencia (email y teléfono). ✅

### 4. Búsqueda de Productos
- Funcionalidad para buscar productos en la base de datos. ✅
- Almacenamiento asíncrono de búsquedas realizadas. ✅
- Visualización solo de productos con stock > parámetro configurable. ✅

### 5. Carrito de Compras
- Funcionalidad para agregar productos al carrito. ✅

### 6. Gestión de Pedidos y Pagos
- Registro de pedidos con cliente, tarjeta y dirección. ✅
- Lógica de aprobación o rechazo de pagos basada en probabilidad configurable. ✅
- Reintentos configurables en caso de fallo y notificación al cliente. ✅

### 7. Notificaciones por Correo
- Envío de emails en caso de éxito o fallo de pagos. ✅

### 8. Logs Centralizados
- Registro de todos los eventos y transacciones con UUID único. ✅

### 9. Despliegue
- Despliegue mediante Docker y Docker Compose. ✅
- Despliegue en Cloud Run: [https://farmatodo-yzsgxs2tya-uc.a.run.app](https://farmatodo-yzsgxs2tya-uc.a.run.app) ✅

---

## Requerimientos No Funcionales

### Seguridad
- Autenticación mediante API Key o Secret Key. ✅
- Encriptación de datos sensibles como tarjetas de crédito. ✅

### Escalabilidad y Desempeño
- Soporta múltiples usuarios concurrentes. ✅
- Configuración centralizada de parámetros clave. ✅

### Robustez
- Control de errores y validaciones exhaustivas. ✅

### Pruebas
- Cobertura mínima del 80% en pruebas unitarias. ✅
- Colección de Postman para pruebas manuales y automatizadas. ✅

### Mantenibilidad
- Código modular y documentado mediante Swagger. ✅

### DevSecOps
- Integración Continua (CI). ✅
- Pruebas Continuas. ✅
- Despliegue Continuo (CD). ✅

---

## Información del Sistema

### Swagger UI
- [https://farmatodo-yzsgxs2tya-uc.a.run.app/swagger-ui.html](https://farmatodo-yzsgxs2tya-uc.a.run.app/swagger-ui.html)

### OpenAPI JSON
- [https://farmatodo-yzsgxs2tya-uc.a.run.app/v3/api-docs](https://farmatodo-yzsgxs2tya-uc.a.run.app/v3/api-docs)

### H2 Console (solo local)
- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `fm`
- Password: `fmtest`

### Pruebas Unitarias
- Cobertura: 88% ✅
- Comando: `mvn clean test`
- Reporte: `target/site/jacoco/index.html`

### Docker
- Comandos:
```bash
mvn -N io.takari:maven:wrapper
mvn clean package -DskipTests
docker-compose up --build
