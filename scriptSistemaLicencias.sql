-- =========================
-- Tabla conductores
-- =========================
CREATE TABLE conductores (
    id BIGSERIAL PRIMARY KEY,
    cedula VARCHAR(10) UNIQUE NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(10),
    email VARCHAR(100),
    tipo_sangre VARCHAR(5),
    documentos_validados BOOLEAN DEFAULT FALSE,
    observaciones TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- Tabla licencias
-- =========================
CREATE TABLE licencias (
    id BIGSERIAL PRIMARY KEY,
    conductor_id BIGINT NOT NULL,
    numero_licencia VARCHAR(20) UNIQUE NOT NULL,
    tipo_licencia VARCHAR(50) NOT NULL,
    fecha_emision DATE NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    activa BOOLEAN DEFAULT TRUE,
    prueba_psicometrica_id BIGINT,
    observaciones TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_licencia_conductor
        FOREIGN KEY (conductor_id) REFERENCES conductores(id)
);

-- =========================
-- Tabla pruebas psicométricas
-- =========================
CREATE TABLE pruebas_psicometricas (
    id BIGSERIAL PRIMARY KEY,
    conductor_id BIGINT NOT NULL,
	puntaje_reaccion DECIMAL(5,2),
    puntaje_visual DECIMAL(5,2),
    puntaje_auditivo DECIMAL(5,2),
    puntaje_motor DECIMAL(5,2),
    puntaje_psicologico DECIMAL(5,2),
    observaciones TEXT,
	fecha_prueba DATE NOT NULL,
	aprobado VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prueba_conductor
        FOREIGN KEY (conductor_id) REFERENCES conductores(id)
);

-- =========================
-- Índices
-- =========================
CREATE INDEX idx_conductor_cedula ON conductores(cedula);
CREATE INDEX idx_licencia_numero ON licencias(numero_licencia);
CREATE INDEX idx_licencia_conductor ON licencias(conductor_id);
CREATE INDEX idx_prueba_conductor ON pruebas_psicometricas(conductor_id);


