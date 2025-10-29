-- Datos de ejemplo para poblar la base de datos
-- Este script se ejecutará automáticamente al iniciar la aplicación

-- Insertar promociones de ejemplo
INSERT INTO promocion (nombre, descripcion, tipo_descuento, valor_descuento, fecha_inicio, fecha_fin, categoria_aplicable, estado, criterio_segmentacion) VALUES
('Oferta Especial para Perros', 'Descuento en productos para perros de todas las marcas', 'PORCENTAJE', 25.00, '2024-01-01', '2024-12-31', 'perros', true, 'Clientes con compras previas en categoría perros'),
('Promoción Gatos Premium', 'Oferta especial en comida premium para gatos', 'PORCENTAJE', 20.00, '2024-01-01', '2024-12-31', 'gatos', true, 'Clientes con gatos registrados'),
('Descuento Aves', 'Productos especializados para aves con descuento', 'FIJO', 15.00, '2024-01-01', '2024-12-31', 'aves', true, 'Clientes interesados en aves'),
('Oferta Acuarios', 'Todo lo necesario para acuarios con descuento', 'PORCENTAJE', 30.00, '2024-01-01', '2024-12-31', 'peces', true, 'Clientes con acuarios'),
('Gran Oferta de Verano', 'Descuentos especiales en toda la tienda', 'PORCENTAJE', 15.00, '2024-06-01', '2024-08-31', 'general', true, 'Todos los clientes activos');

-- Insertar clientes de ejemplo
INSERT INTO cliente (nombre, apellido, email, telefono, direccion, fecha_registro, estado, puntos_fidelidad) VALUES
('Juan', 'Pérez', 'juan.perez@email.com', '+573001234567', 'Calle 123 #45-67, Bogotá', '2024-01-15', true, 150),
('María', 'González', 'maria.gonzalez@email.com', '+573001234568', 'Carrera 45 #78-90, Medellín', '2024-01-20', true, 200),
('Carlos', 'Rodríguez', 'carlos.rodriguez@email.com', '+573001234569', 'Avenida 80 #12-34, Cali', '2024-02-01', true, 75),
('Ana', 'Martínez', 'ana.martinez@email.com', '+573001234570', 'Calle 100 #23-45, Barranquilla', '2024-02-10', true, 300),
('Luis', 'Hernández', 'luis.hernandez@email.com', '+573001234571', 'Carrera 7 #89-12, Bucaramanga', '2024-02-15', true, 125);

-- Insertar usuarios de ejemplo
INSERT INTO usuario (nombre_usuario, email, password_hash, id_rol, estado, fecha_creacion, ultimo_acceso) VALUES
('admin', 'admin@mascotasfelices.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 1, true, '2024-01-01', '2024-12-01'),
('marketing', 'marketing@mascotasfelices.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 2, true, '2024-01-01', '2024-12-01'),
('vendedor1', 'vendedor1@mascotasfelices.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 3, true, '2024-01-01', '2024-12-01');

-- Insertar notificaciones de ejemplo
INSERT INTO notificaciones (id_cliente, id_promocion, id_usuario, tipo_notificacion, mensaje, fecha_envio, estado_entrega, canal_envio, fecha_lectura) VALUES
(1, 1, 2, 'EMAIL', '¡Hola Juan! Esta es una promoción especial para ti y tu perro.', '2024-12-01', 'ENVIADA', 'EMAIL', '2024-12-01'),
(2, 2, 2, 'SMS', '¡Oferta especial para gatos! No te la pierdas.', '2024-12-01', 'ENVIADA', 'SMS', NULL),
(3, 3, 2, 'WHATSAPP', 'Promoción personalizada para tus aves.', '2024-12-01', 'ENVIADA', 'WHATSAPP', '2024-12-01'),
(4, 4, 2, 'PUSH', 'Gran oferta en productos para acuarios.', '2024-12-01', 'ENVIADA', 'PUSH', NULL),
(5, 5, 2, 'EMAIL', 'Oferta de verano para todos nuestros clientes.', '2024-12-01', 'PENDIENTE', 'EMAIL', NULL);
