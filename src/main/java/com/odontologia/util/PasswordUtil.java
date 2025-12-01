package com.odontologia.util;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * CLASE PASSWORD UTIL
 * Utilidad para manejo seguro de contraseñas
 *
 * FUNCIONES:
 * - Encriptar contraseñas con BCrypt
 * - Verificar contraseñas
 * - Validar fortaleza de contraseña
 * - Generar contraseñas aleatorias
 */
public class PasswordUtil {

    // ========== CONFIGURACIÓN DE SEGURIDAD ==========

    /**
     * Coste computacional de BCrypt
     * 12 es un buen balance entre seguridad y velocidad
     */
    private static final int BCRYPT_LOG_ROUNDS = 12;

    /**
     * Expresión regular para validar contraseña fuerte
     * Requisitos: 8+ caracteres, 1 mayúscula, 1 número
     */
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d).{8,}$";

    /**
     * Patrón compilado para validación
     */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);


    // ========== MÉTODO PARA ENCRIPTAR ==========

    /**
     * Genera un hash seguro de la contraseña usando BCrypt
     *
     * @param plainPassword Contraseña en texto plano
     * @return Hash seguro de la contraseña
     * @throws IllegalArgumentException Si la contraseña está vacía
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_LOG_ROUNDS));
    }


    // ========== MÉTODO PARA VERIFICAR ==========

    /**
     * Verifica si una contraseña coincide con un hash BCrypt
     *
     * @param plainPassword Contraseña en texto plano a verificar
     * @param hashedPassword Hash almacenado para comparación
     * @return true si coinciden, false en caso contrario
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }


    // ========== MÉTODO PARA VALIDAR FORTALEZA ==========

    /**
     * Valida la fortaleza de una contraseña según políticas de seguridad
     * Requisitos: 8+ caracteres, 1 mayúscula, 1 número
     *
     * @param password Contraseña a validar
     * @return true si cumple con los requisitos de seguridad
     */
    public static boolean isPasswordStrong(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }


    // ========== MÉTODO PARA GENERAR CONTRASEÑA ALEATORIA ==========

    /**
     * Genera una contraseña aleatoria segura
     * Útil para reseteos de contraseña
     *9
     * @return Contraseña aleatoria de 12 caracteres
     */
    public static String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}9