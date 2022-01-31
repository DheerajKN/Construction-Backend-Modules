CREATE SCHEMA IF NOT EXISTS construction_schema;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" SCHEMA construction_schema;
ALTER ROLE construction SET search_path to construction_schema;