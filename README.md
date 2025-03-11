# 3. Configuración correcta en Postman
Endpoint para insertar un paciente (POST /v1/patients)

Método HTTP: Selecciona POST.
URL: http://localhost:9090/v1/patients

Headers:
Asegúrate de que el header Content-Type esté configurado como application/json.
# Body:

Selecciona la opción raw y elige JSON como formato.
Ingresa el cuerpo de la solicitud en formato JSON. Por ejemplo:

```json
    {
        "active": "Y",
        "gender": "male",
        "birthDate": "1990-01-01",
        "deceasedBoolean": "N",
        "nameFamily": "Pérez",
        "nameGiven": "Juan",
        "identifierTypeCode": "CI",
        "identifierValue": "1-9",
        "nombreOficialFamily": "Pérez",
        "nombreOficialGiven": "Juan",
        "segundoApellido": "González",
        "nombreSocialGiven": "Juanito"
    }