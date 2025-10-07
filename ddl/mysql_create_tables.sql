CREATE DATABASE ai_gcp_gcfunctions_cloudrun;

USE ai_gcp_gcfunctions_cloudrun;

-- Then paste the tables above.

CREATE TABLE gcp_functions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    runtime VARCHAR(100),
    entry_point VARCHAR(100),
    url TEXT,
    is_anomaly BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE gcp_cloudrun_services (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    uri TEXT,
    template TEXT,
    is_anomaly BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
