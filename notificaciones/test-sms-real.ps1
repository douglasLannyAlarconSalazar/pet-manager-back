# Script para probar SMS con credenciales reales de Twilio
# Asegúrate de que la aplicación esté ejecutándose en el puerto 8080

Write-Host "🧪 Probando SMS con credenciales reales de Twilio..." -ForegroundColor Green
Write-Host "=================================================" -ForegroundColor Green

# Configurar variables
$BASE_URL = "http://localhost:8080/api/notificaciones"
$PROMOCION_ID = 1
$CLIENTE_ID = 1
$USUARIO_ID = 1

Write-Host ""
Write-Host "📱 Enviando SMS real..." -ForegroundColor Yellow
Write-Host "----------------------" -ForegroundColor Yellow

$smsBody = @{
    idPromocion = $PROMOCION_ID
    idCliente = $CLIENTE_ID
    idUsuario = $USUARIO_ID
    mensajePersonalizado = "¡Hola desde PetManager! 🐾 Este es un SMS de prueba con Twilio."
} | ConvertTo-Json

try {
    Write-Host "Enviando SMS a través de Twilio..." -ForegroundColor Cyan
    $smsResponse = Invoke-RestMethod -Uri "$BASE_URL/sms" -Method POST -Body $smsBody -ContentType "application/json"
    Write-Host "✅ Respuesta del servidor:" -ForegroundColor Green
    $smsResponse | ConvertTo-Json -Depth 3
} catch {
    Write-Host "❌ Error en SMS: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Detalles del error: $responseBody" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "📋 Notas:" -ForegroundColor Cyan
Write-Host "- Revisa los logs de la aplicación para ver el estado del envío" -ForegroundColor White
Write-Host "- El SMS se enviará al número configurado en la base de datos" -ForegroundColor White
Write-Host "- Verifica tu teléfono para recibir el mensaje" -ForegroundColor White
