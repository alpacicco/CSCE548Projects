# Dockerfile for containerizing the E-Commerce REST API
# 
# DOCKER HOSTING INSTRUCTIONS:
# ============================
# 
# 1. BUILD THE IMAGE:
#    docker build -t ecommerce-api:latest .
# 
# 2. RUN LOCALLY:
#    docker run -p 8080:8080 \
#      -e DB_HOST=host.docker.internal \
#      -e DB_PORT=3306 \
#      -e DB_NAME=ecommerce_db \
#      -e DB_USER=your_user \
#      -e DB_PASSWORD=your_password \
#      ecommerce-api:latest
# 
# 3. PUSH TO DOCKER HUB:
#    docker tag ecommerce-api:latest your-username/ecommerce-api:latest
#    docker push your-username/ecommerce-api:latest
# 
# 4. DEPLOY TO AWS ECS:
#    - Create ECS cluster
#    - Create task definition using this image
#    - Create service and configure load balancer
# 
# 5. DEPLOY TO GOOGLE CLOUD RUN:
#    gcloud builds submit --tag gcr.io/PROJECT-ID/ecommerce-api
#    gcloud run deploy --image gcr.io/PROJECT-ID/ecommerce-api --platform managed

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/target/ecommerce-console-*.jar app.jar

# Copy .env file if it exists (optional)
COPY .env* ./

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost:8080/api/users || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
