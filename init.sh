#!/bin/bash

docker-compose up -d

echo "Waiting for PostgreSQL to start..."
sleep 10

docker exec -i postgres psql -U user -d device_db < ./seed.sql

echo "Database has been seeded successfully."
