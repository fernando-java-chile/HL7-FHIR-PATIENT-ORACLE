package com.fichaClinica.HL7FHIR_SAMPLES_ORACLE.validation;

public class RutValidator {

    /**
     * Valida que el RUT tenga el formato correcto y que el dígito verificador coincida.
     *
     * @param rut El RUT en formato "xxxxxxxx-d" o con puntos, por ejemplo "13.075.373-6"
     * @return true si el RUT es válido, false en caso contrario.
     */
    public static boolean validarRut(String rut) {
        // Verifica que el RUT no sea nulo y contenga el guion
        if (rut == null || !rut.contains("-")) {
            return false;
        }

        // Elimina puntos y espacios en blanco
        String rutLimpio = rut.replace(".", "").trim();

        // Separa el número del dígito verificador usando el guion
        String[] partes = rutLimpio.split("-");
        if (partes.length != 2) {
            return false;
        }

        String numeroRutStr = partes[0];
        String dvInput = partes[1].toUpperCase();  // Convierte a mayúsculas para comparar "K" de forma uniforme

        try {
            long numeroRut = Long.parseLong(numeroRutStr);
            String dvCalculado = calcularDigitoVerificador(numeroRut);
            return dvCalculado.equalsIgnoreCase(dvInput);
        } catch (NumberFormatException e) {
            // Si no se puede convertir la parte numérica, el RUT es inválido
            return false;
        }
    }

    /**
     * Función que calcula y devuelve el dígito verificador de un RUT chileno.
     *
     * @param rutNumero número del RUT (sin dígito verificador)
     * @return dígito verificador como String ("K", "0" o el dígito numérico)
     */
    private static String calcularDigitoVerificador(long rutNumero) {
        try {
            // Formatea el RUT con ceros a la izquierda para asegurar 8 dígitos
            String rutFormateado = String.format("%08d", rutNumero);
            String factoresCalculo = "32765432";
            int sumaPonderada = 0;

            // Calcula la suma ponderada de los dígitos del RUT multiplicados por los factores
            for (int i = 0; i < 8; i++) {
                int digito = Character.getNumericValue(rutFormateado.charAt(i));
                int factor = Character.getNumericValue(factoresCalculo.charAt(i));
                sumaPonderada += digito * factor;
            }

            // Calcula el dígito verificador según la fórmula
            int mod = sumaPonderada % 11;
            int digitoVerificador = 11 - mod;

            // Retorna el dígito verificador de acuerdo a la lógica del RUT
            if (digitoVerificador == 10) {
                return "K";
            } else if (digitoVerificador == 11) {
                return "0";
            } else {
                return String.valueOf(digitoVerificador);
            }
        } catch (Exception e) {
            // Manejo de excepciones: registrar el error y retornar un valor por defecto ("Z")
            System.err.println("Error en CALCULAR_DIGITO_VERIFICADOR: " + e.getMessage());
            return "Z";
        }
    }
}
