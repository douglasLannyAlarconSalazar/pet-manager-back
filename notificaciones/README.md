# 🚀 Microservicio de Notificaciones de Promociones

## 📋 Descripción
Microservicio Spring Boot para el envío de notificaciones de promociones por email, SMS, WhatsApp y Push a clientes, con personalización basada en historial de compras.

## 🛠️ Tecnologías
- **Spring Boot 3.5.7**
- **PostgreSQL (Neon Cloud)**
- **Spring Data JPA**
- **Spring Mail**
- **Swagger/OpenAPI 3**
- **Maven**

## 🚀 Inicio Rápido

### 1. Configuración de Variables de Entorno
```bash
# Configurar credenciales de email (Gmail)
export MAIL_USERNAME=tu-email@gmail.com
export MAIL_PASSWORD=tu-app-password
```

### 2. Ejecutar la Aplicación
```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run
```

### 3. Acceder a la Documentación
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## 📡 Endpoints de la API

### Envío de Notificaciones
- `POST /api/notificaciones/email` - Enviar promoción por email
- `POST /api/notificaciones/sms` - Enviar promoción por SMS
- `POST /api/notificaciones/email-masivo` - Envío masivo por email
- `POST /api/notificaciones/personalizada` - Promoción personalizada

### Consultas
- `GET /api/notificaciones` - Obtener todas las notificaciones
- `GET /api/notificaciones/cliente/{idCliente}` - Notificaciones por cliente
- `GET /api/notificaciones/health` - Health check

## 📝 Ejemplos de Uso

### Enviar Promoción por Email
```bash
curl -X POST "http://localhost:8080/api/notificaciones/email" \
  -H "Content-Type: application/json" \
  -d '{
    "idCliente": 1,
    "idPromocion": 1,
    "idUsuario": 1,
    "mensajePersonalizado": "¡Hola! Esta es una promoción especial para ti."
  }'
```

### Enviar Promoción Personalizada
```bash
curl -X POST "http://localhost:8080/api/notificaciones/personalizada" \
  -H "Content-Type: application/json" \
  -d '{
    "idCliente": 1,
    "idPromocion": 1,
    "idUsuario": 1,
    "canalEnvio": "EMAIL",
    "mensajePersonalizado": "Promoción personalizada basada en tus preferencias."
  }'
```

### Envío Masivo por Email
```bash
curl -X POST "http://localhost:8080/api/notificaciones/email-masivo" \
  -H "Content-Type: application/json" \
  -d '{
    "idClientes": [1, 2, 3, 4, 5],
    "idPromocion": 1,
    "idUsuario": 1,
    "mensajePersonalizado": "Gran oferta de verano para todos nuestros clientes."
  }'
```

## 🗄️ Base de Datos

### Tablas Principales

#### Tabla: promocion
```sql
CREATE TABLE promocion (
    id_promocion INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    tipo_descuento VARCHAR(50) NOT NULL,
    valor_descuento NUMERIC(10,2) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    categoria_aplicable VARCHAR(100),
    estado BOOLEAN NOT NULL DEFAULT true,
    criterio_segmentacion TEXT
);
```

#### Tabla: cliente
```sql
CREATE TABLE cliente (
    id_cliente INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    direccion TEXT,
    fecha_registro DATE NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT true,
    puntos_fidelidad INTEGER DEFAULT 0
);
```

#### Tabla: notificaciones
```sql
CREATE TABLE notificaciones (
    id_notificacion INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_cliente INTEGER NOT NULL,
    id_promocion INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,
    tipo_notificacion TEXT NOT NULL,
    mensaje TEXT,
    fecha_envio DATE,
    estado_entrega TEXT NOT NULL DEFAULT 'PENDIENTE',
    canal_envio TEXT NOT NULL,
    fecha_lectura DATE,
    
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    FOREIGN KEY (id_promocion) REFERENCES promocion(id_promocion),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);
```

## 🔧 Configuración

### application.properties
```properties
# Configuración de la aplicación
spring.application.name=notificaciones
server.port=8080

# Base de datos PostgreSQL (Neon)
spring.datasource.url=jdbc:postgresql://ep-round-cherry-adczvo7f-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb
spring.datasource.username=neondb_owner
spring.datasource.password=npg_xLhw93aoiTgs

# Configuración de email
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME:}
spring.mail.password=${MAIL_PASSWORD:}
```

## 📊 Características

### ✅ Funcionalidades Implementadas
- ✅ Envío de notificaciones por email
- ✅ Envío de notificaciones por SMS (simulado)
- ✅ Envío de notificaciones por WhatsApp (simulado)
- ✅ Envío de notificaciones push (simulado)
- ✅ Envío masivo de notificaciones
- ✅ Promociones personalizadas con mensajes personalizados
- ✅ API REST documentada con Swagger
- ✅ Validación de datos de entrada
- ✅ Manejo de excepciones
- ✅ Logging detallado
- ✅ Conexión a PostgreSQL (Neon Cloud)
- ✅ Trabajo con promociones existentes (no genera promociones)
- ✅ Relaciones entre entidades (Cliente, Promoción, Usuario)
- ✅ Estados de entrega de notificaciones
- ✅ Marcado de notificaciones como leídas

### 🎯 Características del Sistema
- **Promociones Existentes**: Trabaja con promociones ya creadas en la BD
- **Múltiples Canales**: Email, SMS, WhatsApp y Push notifications
- **Personalización**: Mensajes personalizados por notificación
- **Trazabilidad**: Seguimiento completo del estado de entrega
- **Relaciones**: Integración con clientes, promociones y usuarios
- **Validaciones**: Verificación de existencia de entidades relacionadas

## 🚀 Próximos Pasos

### Integraciones Reales
1. **Twilio** para SMS reales
2. **WhatsApp Business API** para mensajes de WhatsApp
3. **Firebase Cloud Messaging** para notificaciones push
4. **SendGrid** o **AWS SES** para emails masivos

### Mejoras Futuras
- Programación de notificaciones
- Plantillas de mensajes personalizables
- Métricas y analytics
- Cola de mensajes (RabbitMQ/Kafka)
- Rate limiting
- Autenticación y autorización

## 📞 Soporte
Para soporte técnico, contactar al equipo de desarrollo en: desarrollo@mascotasfelices.com

## 📄 Licencia
MIT License - Ver archivo LICENSE para más detalles.
