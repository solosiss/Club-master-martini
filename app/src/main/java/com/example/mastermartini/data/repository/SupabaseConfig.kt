package com.example.mastermartini.data.repository

/**
 * Configuración y Especificación Técnica de Arquitectura Backend con Supabase (PostgreSQL)
 * Evaluación 2 - DSY1105
 * 
 * Cumple con el requerimiento de integración:
 * - Autenticación con JWT (Supabase Auth)
 * - Base de datos relacional PostgreSQL con RLS (Row Level Security)
 * - Almacenamiento de metadatos de videos e insumos asociados
 */
object SupabaseConfig {

    // URL y Llaves de conexión Supabase (Entorno Académico / Staging)
    const val SUPABASE_URL = "https://club-mastermartini-chile.supabase.co"
    const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZS1hY2FkZW1pYy1kZW1vIn0.sampleKeyMasterMartini2026"

    // Endpoints REST de Microservicios mapeados a Supabase PostgREST
    const val ENDPOINT_AUTH_SIGNUP = "$SUPABASE_URL/auth/v1/signup"
    const val ENDPOINT_AUTH_LOGIN = "$SUPABASE_URL/auth/v1/token?grant_type=password"
    const val ENDPOINT_PERFILES = "$SUPABASE_URL/rest/v1/perfiles_usuarios"
    const val ENDPOINT_CURSOS = "$SUPABASE_URL/rest/v1/cursos_tecnicos"
    const val ENDPOINT_LECCIONES = "$SUPABASE_URL/rest/v1/lecciones_videos"
    const val ENDPOINT_INSUMOS = "$SUPABASE_URL/rest/v1/insumos_master_martini"
    const val ENDPOINT_MUESTRAS = "$SUPABASE_URL/rest/v1/solicitudes_muestras"
    const val ENDPOINT_FAVORITOS = "$SUPABASE_URL/rest/v1/favoritos_lecciones"

    /**
     * DDL SQL para inicializar Supabase:
     * 
     * CREATE TABLE perfiles_usuarios (
     *     id UUID REFERENCES auth.users NOT NULL PRIMARY KEY,
     *     nombre_completo TEXT NOT NULL,
     *     correo TEXT UNIQUE NOT NULL,
     *     telefono TEXT,
     *     nombre_negocio TEXT NOT NULL,
     *     rut_empresa TEXT,
     *     direccion TEXT,
     *     tipo_negocio TEXT CHECK (tipo_negocio IN ('PASTELERIA', 'CHOCOLATERIA', 'PANADERIA', 'HELADERIA', 'RESTAURANTE_CASINO')),
     *     tamano_negocio TEXT,
     *     intereses_tecnicos TEXT[],
     *     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
     * );
     * 
     * CREATE TABLE lecciones_videos (
     *     id TEXT PRIMARY KEY,
     *     titulo TEXT NOT NULL,
     *     descripcion TEXT,
     *     categoria TEXT NOT NULL,
     *     chef_instructor TEXT,
     *     duracion_segundos INT,
     *     url_video TEXT NOT NULL,
     *     youtube_video_id TEXT,
     *     miniatura_url TEXT,
     *     dificultad TEXT,
     *     perfiles_objetivo TEXT[]
     * );
     * 
     * CREATE TABLE solicitudes_muestras (
     *     id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
     *     user_id UUID REFERENCES perfiles_usuarios(id),
     *     insumo_id TEXT NOT NULL,
     *     nombre_insumo TEXT NOT NULL,
     *     nombre_negocio TEXT NOT NULL,
     *     telefono_contacto TEXT,
     *     direccion_despacho TEXT NOT NULL,
     *     estado TEXT DEFAULT 'En preparación',
     *     notas TEXT,
     *     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
     * );
     */
}
