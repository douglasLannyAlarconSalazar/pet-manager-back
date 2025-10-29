# Script para probar los servicios SMS y WhatsApp
# Asegúrate de que la aplicación esté ejecutándose en el puerto 8080

Write-Host "🧪 Probando servicios SMS y WhatsApp..." -ForegroundColor Green
Write-Host "======================================" -ForegroundColor Green

# Configurar variables
$BASE_URL = "http://localhost:8080/api/notificaciones"
$PROMOCION_ID = 1
$CLIENTE_ID = 1
$USUARIO_ID = 1

Write-Host ""
Write-Host "📱 Probando SMS..." -ForegroundColor Yellow
Write-Host "------------------" -ForegroundColor Yellow

$smsBody = @{
    idPromocion = $PROMOCION_ID
    idCliente = $CLIENTE_ID
    idUsuario = $USUARIO_ID
    mensajePersonalizado = "¡Prueba de SMS desde el microservicio!"
} | ConvertTo-Json

try {
    $smsResponse = Invoke-RestMethod -Uri "$BASE_URL/sms" -Method POST -Body $smsBody -ContentType "application/json"
    $smsResponse | ConvertTo-Json -Depth 3
} catch {
    Write-Host "Error en SMS: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "📱 Probando WhatsApp..." -ForegroundColor Yellow
Write-Host "----------------------" -ForegroundColor Yellow

$whatsappBody = @{
    idPromocion = $PROMOCION_ID
    idCliente = $CLIENTE_ID
    idUsuario = $USUARIO_ID
    canalEnvio = "WHATSAPP"
    mensajePersonalizado = "¡Prueba de WhatsApp desde el microservicio!"
} | ConvertTo-Json

try {
    $whatsappResponse = Invoke-RestMethod -Uri "$BASE_URL/promocion-personalizada" -Method POST -Body $whatsappBody -ContentType "application/json"
    $whatsappResponse | ConvertTo-Json -Depth 3
} catch {
    Write-Host "Error en WhatsApp: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "✅ Pruebas completadas!" -ForegroundColor Green
Write-Host ""
Write-Host "📋 Notas:" -ForegroundColor Cyan
Write-Host "- Si no tienes credenciales configuradas, se usará modo simulado" -ForegroundColor White
Write-Host "- Revisa los logs de la aplicación para ver el estado del envío" -ForegroundColor White
Write-Host "- Para configurar credenciales reales, edita env.local.properties" -ForegroundColor White
