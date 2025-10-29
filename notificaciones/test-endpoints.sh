#!/bin/bash

# Script de prueba para el microservicio de notificaciones
# Asegúrate de que la aplicación esté ejecutándose en el puerto 8080

echo "🚀 Probando Microservicio de Notificaciones de Promociones"
echo "=================================================="

# Health check
echo "1. Health Check..."
curl -s http://localhost:8080/api/notificaciones/health | jq .

echo -e "\n2. Enviando promoción por email..."
curl -X POST "http://localhost:8080/api/notificaciones/email" \
  -H "Content-Type: application/json" \
  -d '{
    "idCliente": 1,
    "idPromocion": 1,
    "idUsuario": 1,
    "mensajePersonalizado": "¡Hola! Esta es una promoción especial para ti."
  }' | jq .

echo -e "\n3. Enviando promoción por SMS..."
curl -X POST "http://localhost:8080/api/notificaciones/sms" \
  -H "Content-Type: application/json" \
  -d '{
    "idCliente": 2,
    "idPromocion": 1,
    "idUsuario": 1,
    "mensajePersonalizado": "¡Oferta especial! No te la pierdas."
  }' | jq .

echo -e "\n4. Enviando promoción personalizada..."
curl -X POST "http://localhost:8080/api/notificaciones/personalizada" \
  -H "Content-Type: application/json" \
  -d '{
    "idCliente": 3,
    "idPromocion": 1,
    "idUsuario": 1,
    "canalEnvio": "EMAIL",
    "mensajePersonalizado": "Promoción personalizada basada en tus preferencias."
  }' | jq .

echo -e "\n5. Envío masivo por email..."
curl -X POST "http://localhost:8080/api/notificaciones/email-masivo" \
  -H "Content-Type: application/json" \
  -d '{
    "idClientes": [1, 2, 3, 4, 5],
    "idPromocion": 1,
    "idUsuario": 1,
    "mensajePersonalizado": "Gran oferta de verano para todos nuestros clientes."
  }' | jq .

echo -e "\n6. Obteniendo todas las notificaciones..."
curl -s http://localhost:8080/api/notificaciones | jq .

echo -e "\n7. Obteniendo notificaciones del cliente 1..."
curl -s http://localhost:8080/api/notificaciones/cliente/1 | jq .

echo -e "\n8. Obteniendo notificaciones de la promoción 1..."
curl -s http://localhost:8080/api/notificaciones/promocion/1 | jq .

echo -e "\n9. Marcando notificación como leída..."
curl -X PUT "http://localhost:8080/api/notificaciones/1/leida" | jq .

echo -e "\n✅ Pruebas completadas!"
echo "📖 Documentación disponible en: http://localhost:8080/swagger-ui.html"
