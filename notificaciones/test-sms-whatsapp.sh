#!/bin/bash

# Script para probar los servicios SMS y WhatsApp
# Asegúrate de que la aplicación esté ejecutándose en el puerto 8080

echo "🧪 Probando servicios SMS y WhatsApp..."
echo "======================================"

# Configurar variables
BASE_URL="http://localhost:8080/api/notificaciones"
PROMOCION_ID=1
CLIENTE_ID=1
USUARIO_ID=1

echo ""
echo "📱 Probando SMS..."
echo "------------------"

curl -X POST "$BASE_URL/sms" \
  -H "Content-Type: application/json" \
  -d "{
    \"idPromocion\": $PROMOCION_ID,
    \"idCliente\": $CLIENTE_ID,
    \"idUsuario\": $USUARIO_ID,
    \"mensajePersonalizado\": \"¡Prueba de SMS desde el microservicio!\"
  }" | jq '.'

echo ""
echo "📱 Probando WhatsApp..."
echo "----------------------"

curl -X POST "$BASE_URL/promocion-personalizada" \
  -H "Content-Type: application/json" \
  -d "{
    \"idPromocion\": $PROMOCION_ID,
    \"idCliente\": $CLIENTE_ID,
    \"idUsuario\": $USUARIO_ID,
    \"canalEnvio\": \"WHATSAPP\",
    \"mensajePersonalizado\": \"¡Prueba de WhatsApp desde el microservicio!\"
  }" | jq '.'

echo ""
echo "✅ Pruebas completadas!"
echo ""
echo "📋 Notas:"
echo "- Si no tienes credenciales configuradas, se usará modo simulado"
echo "- Revisa los logs de la aplicación para ver el estado del envío"
echo "- Para configurar credenciales reales, edita env.local.properties"
