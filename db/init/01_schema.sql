CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE usuarios (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          nombre VARCHAR(100) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          capital_disponible NUMERIC(10,2) NOT NULL
);

CREATE TABLE productos_financieros (
                                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                       nombre VARCHAR(150) NOT NULL,
                                       descripcion TEXT,
                                       costo NUMERIC(10,2) NOT NULL,
                                       porcentaje_retorno NUMERIC(5,2) NOT NULL,
                                       activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE simulaciones (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              usuario_id UUID NOT NULL,
                              fecha_simulacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              capital_disponible NUMERIC(10,2) NOT NULL,
                              costo_total NUMERIC(10,2) NOT NULL,
                              capital_restante NUMERIC(10,2) NOT NULL,
                              ganancia_total NUMERIC(10,2) NOT NULL,
                              retorno_total_porcentaje NUMERIC(5,2) NOT NULL,
                              eficiencia_capital NUMERIC(5,2) NOT NULL,
                              mensaje VARCHAR(255),

                              CONSTRAINT fk_simulacion_usuario
                                  FOREIGN KEY (usuario_id)
                                      REFERENCES usuarios(id)
);

CREATE TABLE simulaciones_detalle (
                                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                      simulacion_id UUID NOT NULL,
                                      nombre_producto VARCHAR(150) NOT NULL,
                                      precio NUMERIC(10,2) NOT NULL,
                                      riesgo NUMERIC(5,2),
                                      porcentaje_ganancia NUMERIC(5,2) NOT NULL,
                                      ganancia_esperada NUMERIC(10,2) NOT NULL,
                                      retorno_estimado NUMERIC(10,2) NOT NULL,

                                      CONSTRAINT fk_detalle_simulacion
                                          FOREIGN KEY (simulacion_id)
                                              REFERENCES simulaciones(id)
                                              ON DELETE CASCADE
);