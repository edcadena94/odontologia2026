package com.odontologia.util;

import org.mindrot.jbcrypt.BCrypt;

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
 * Genera un hash seguro d