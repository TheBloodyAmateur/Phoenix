CREATE TABLE minio_buckets (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT NOW(),
    UNIQUE (user_id, name)
);

CREATE TABLE minio_objects (
    id BIGSERIAL PRIMARY KEY,
    bucket_id BIGINT REFERENCES minio_buckets(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('FILE', 'FOLDER')),
    parent_id BIGINT REFERENCES minio_objects(id) ON DELETE SET NULL,
    size BIGINT,
    content_type VARCHAR(100),
    minio_path VARCHAR(1024) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);