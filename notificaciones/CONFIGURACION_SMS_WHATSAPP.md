# Configuración de Servicios SMS y WhatsApp

## 📱 Servicios Implementados

### 1. SMS con Twilio
- **Proveedor**: Twilio
- **Funcionalidad**: Envío real de SMS
- **Fallback**: Modo simulado si no hay credenciales

### 2. WhatsApp Business API
- **Proveedor**: Meta (Facebook)
- **Funcionalidad**: Envío real de mensajes de WhatsApp
- **Fallback**: Modo simulado si no hay credenciales

## 🔧 Configuración

### 1. Configurar Twilio (SMS)

1. **Crear cuenta en Twilio**:
   - Ve a https://console.twilio.com/
   - Regístrate y verifica tu número de teléfono

2. **Obtener credenciales**:
   - Account SID: En el dashboard principal
   - Auth Token: En el dashboard principal
   - Phone Number: Compra un número de Twilio

3. **Configurar variables**:
   ```properties
   TWILIO_ACCOUNT_SID=ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
   TWILIO_AUTH_TOKEN=tu_auth_token_aqui
   TWILIO_PHONE_NUMBER=+1234567890
   ```

### 2. Configurar WhatsApp Business API

1. **Crear aplicación en Meta for Developers**:
   - Ve a https://developers.facebook.com/
   - Crea una nueva aplicación
   - Agrega el producto "WhatsApp Business API"

2. **Obtener credenciales**:
   - Access Token: En la sección de WhatsApp
   - Phone Number ID: En la sección de WhatsApp

3. **Configurar variables**:
   ```properties
   WHATSAPP_API_URL=https://graph.facebook.com/v18.0
   WHATSAPP_ACCESS_TOKEN=tu_access_token_aqui
   WHATSAPP_PHONE_NUMBER_ID=123456789012345
   ```

## 📝 Archivo de Configuración

Crea un archivo `.env` en la raíz del proyecto:

```properties
# Email
MAIL_USERNAME=tu_email@gmail.com
MAIL_PASSWORD=tu_app_password

# Twilio (SMS)
TWILIO_ACCOUNT_SID=ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
TWILIO_AUTH_TOKEN=tu_auth_token_aqui
TWILIO_PHONE_NUMBER=+1234567890

# WhatsApp Business API
WHATSAPP_API_URL=https://graph.facebook.com/v18.0
WHATSAPP_ACCESS_TOKEN=tu_access_token_aqui
WHATSAPP_PHONE_NUMBER_ID=123456789012345
```

## 🚀 Uso

### Modo Real
- Si las credenciales están configuradas, se envían mensajes reales
- Los logs muestran el estado del envío

### Modo Simulado
- Si no hay credenciales, se usa modo simulado
- Los mensajes se registran en los logs
- Útil para desarrollo y testing

## 📊 Logs

Los servicios registran:
- ✅ Envío exitoso
- ❌ Errores de envío
- 🔄 Fallback a modo simulado
- 📱 Detalles del mensaje enviado

## 🔍 Validaciones

### Teléfonos
- Formato: 10-15 dígitos
- Limpieza automática de caracteres especiales
- Formato internacional para WhatsApp (+57 para Colombia)

### Mensajes
- Construcción automática basada en promociones
- Personalización con datos del cliente
- Emojis y formato para WhatsApp

## 💡 Notas Importantes

1. **Twilio**: Requiere número de teléfono verificado y número de Twilio
2. **WhatsApp**: Requiere verificación de negocio y aprobación de Meta
3. **Costos**: Ambos servicios tienen costos por mensaje enviado
4. **Límites**: Verificar límites de rate limiting de cada proveedor
5. **Testing**: Usar modo simulado para desarrollo local
