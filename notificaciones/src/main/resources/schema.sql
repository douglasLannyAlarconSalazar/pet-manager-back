-- Script SQL para crear las tablas del microservicio de notificaciones
-- Este script se ejecutará automáticamente al iniciar la aplicación

-- Tabla de promociones
CREATE TABLE IF NOT EXISTS promocion (
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

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS cliente (
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

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre_usuario VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    id_rol INTEGER NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT true,
    fecha_creacion DATE NOT NULL,
    ultimo_acceso DATE
);

-- Tabla de notificaciones
CREATE TABLE IF NOT EXISTS notificaciones (
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
    
    -- Foreign keys
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    FOREIGN KEY (id_promocion) REFERENCES promocion(id_promocion),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_notificaciones_id_cliente ON notificaciones(id_cliente);
CREATE INDEX IF NOT EXISTS idx_notificaciones_id_promocion ON notificaciones(id_promocion);
CREATE INDEX IF NOT EXISTS idx_notificaciones_id_usuario ON notificaciones(id_usuario);
CREATE INDEX IF NOT EXISTS idx_notificaciones_tipo ON notificaciones(tipo_notificacion);
CREATE INDEX IF NOT EXISTS idx_notificaciones_canal ON notificaciones(canal_envio);
CREATE INDEX IF NOT EXISTS idx_notificaciones_estado ON notificaciones(estado_entrega);
CREATE INDEX IF NOT EXISTS idx_notificaciones_fecha_envio ON notificaciones(fecha_envio);

CREATE INDEX IF NOT EXISTS idx_promocion_categoria ON promocion(categoria_aplicable);
CREATE INDEX IF NOT EXISTS idx_promocion_estado ON promocion(estado);
CREATE INDEX IF NOT EXISTS idx_promocion_fechas ON promocion(fecha_inicio, fecha_fin);

CREATE INDEX IF NOT EXISTS idx_cliente_email ON cliente(email);
CREATE INDEX IF NOT EXISTS idx_cliente_estado ON cliente(estado);
CREATE INDEX IF NOT EXISTS idx_cliente_fecha_registro ON cliente(fecha_registro);

CREATE INDEX IF NOT EXISTS idx_usuario_email ON usuario(email);
CREATE INDEX IF NOT EXISTS idx_usuario_estado ON usuario(estado);
CREATE INDEX IF NOT EXISTS idx_usuario_rol ON usuario(id_rol);

-- Comentarios en las tablas
COMMENT ON TABLE promocion IS 'Tabla para almacenar las promociones disponibles';
COMMENT ON COLUMN promocion.id_promocion IS 'Identificador único de la promoción';
COMMENT ON COLUMN promocion.nombre IS 'Nombre de la promoción';
COMMENT ON COLUMN promocion.descripcion IS 'Descripción detallada de la promoción';
COMMENT ON COLUMN promocion.tipo_descuento IS 'Tipo de descuento: PORCENTAJE, FIJO';
COMMENT ON COLUMN promocion.valor_descuento IS 'Valor del descuento';
COMMENT ON COLUMN promocion.fecha_inicio IS 'Fecha de inicio de la promoción';
COMMENT ON COLUMN promocion.fecha_fin IS 'Fecha de fin de la promoción';
COMMENT ON COLUMN promocion.categoria_aplicable IS 'Categoría de productos aplicable';
COMMENT ON COLUMN promocion.estado IS 'Estado de la promoción (activa/inactiva)';
COMMENT ON COLUMN promocion.criterio_segmentacion IS 'Criterios para segmentar clientes';

COMMENT ON TABLE cliente IS 'Tabla para almacenar información de los clientes';
COMMENT ON COLUMN cliente.id_cliente IS 'Identificador único del cliente';
COMMENT ON COLUMN cliente.nombre IS 'Nombre del cliente';
COMMENT ON COLUMN cliente.apellido IS 'Apellido del cliente';
COMMENT ON COLUMN cliente.email IS 'Email del cliente';
COMMENT ON COLUMN cliente.telefono IS 'Teléfono del cliente';
COMMENT ON COLUMN cliente.direccion IS 'Dirección del cliente';
COMMENT ON COLUMN cliente.fecha_registro IS 'Fecha de registro del cliente';
COMMENT ON COLUMN cliente.estado IS 'Estado del cliente (activo/inactivo)';
COMMENT ON COLUMN cliente.puntos_fidelidad IS 'Puntos de fidelidad acumulados';

COMMENT ON TABLE usuario IS 'Tabla para almacenar información de los usuarios del sistema';
COMMENT ON COLUMN usuario.id_usuario IS 'Identificador único del usuario';
COMMENT ON COLUMN usuario.nombre_usuario IS 'Nombre de usuario';
COMMENT ON COLUMN usuario.email IS 'Email del usuario';
COMMENT ON COLUMN usuario.password_hash IS 'Hash de la contraseña';
COMMENT ON COLUMN usuario.id_rol IS 'ID del rol del usuario';
COMMENT ON COLUMN usuario.estado IS 'Estado del usuario (activo/inactivo)';
COMMENT ON COLUMN usuario.fecha_creacion IS 'Fecha de creación del usuario';
COMMENT ON COLUMN usuario.ultimo_acceso IS 'Fecha del último acceso';

COMMENT ON TABLE notificaciones IS 'Tabla para almacenar las notificaciones de promociones enviadas';
COMMENT ON COLUMN notificaciones.id_notificacion IS 'Identificador único de la notificación';
COMMENT ON COLUMN notificaciones.id_cliente IS 'ID del cliente destinatario';
COMMENT ON COLUMN notificaciones.id_promocion IS 'ID de la promoción';
COMMENT ON COLUMN notificaciones.id_usuario IS 'ID del usuario que creó la notificación';
COMMENT ON COLUMN notificaciones.tipo_notificacion IS 'Tipo de notificación: EMAIL, SMS, WHATSAPP, PUSH';
COMMENT ON COLUMN notificaciones.mensaje IS 'Mensaje personalizado de la notificación';
COMMENT ON COLUMN notificaciones.fecha_envio IS 'Fecha de envío de la notificación';
COMMENT ON COLUMN notificaciones.estado_entrega IS 'Estado de entrega: PENDIENTE, ENVIADA, ENTREGADA, LEIDA, FALLIDA';
COMMENT ON COLUMN notificaciones.canal_envio IS 'Canal de envío: EMAIL, SMS, WHATSAPP, PUSH';
COMMENT ON COLUMN notificaciones.fecha_lectura IS 'Fecha de lectura de la notificación';
