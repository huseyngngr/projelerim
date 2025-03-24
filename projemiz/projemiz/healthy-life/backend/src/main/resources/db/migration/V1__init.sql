CREATE TABLE users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    profile_picture_url VARCHAR(1000),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE health_profiles (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    height DOUBLE PRECISION,
    weight DOUBLE PRECISION,
    birth_date DATE,
    gender VARCHAR(50),
    medical_conditions TEXT,
    allergies TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE dietary_restrictions (
    health_profile_id UUID NOT NULL REFERENCES health_profiles(id),
    dietary_restrictions VARCHAR(255)
);

CREATE TABLE health_goals (
    health_profile_id UUID NOT NULL REFERENCES health_profiles(id),
    health_goals VARCHAR(255)
);

CREATE TABLE food_consumptions (
    id UUID PRIMARY KEY,
    health_profile_id UUID NOT NULL REFERENCES health_profiles(id),
    food_name VARCHAR(255) NOT NULL,
    portion DOUBLE PRECISION,
    portion_unit VARCHAR(50),
    nutritional_info TEXT,
    ai_analysis TEXT,
    ai_recommendation TEXT,
    consumed_at TIMESTAMP,
    image_url VARCHAR(1000),
    meal_type VARCHAR(50),
    user_notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_food_consumptions_consumed_at ON food_consumptions(consumed_at);
CREATE INDEX idx_health_profiles_user_id ON health_profiles(user_id); 