package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application Entry Point
 * 
 * This is the main application class for the E-Commerce REST API service.
 * It starts an embedded Tomcat server and exposes REST endpoints.
 * 
 * HOSTING AND DEPLOYMENT GUIDE:
 * =============================
 * 
 * 1. LOCAL DEVELOPMENT:
 * To run the service locally during development:
 * Command: mvn spring-boot:run
 * Or: mvn clean package && java -jar target/ecommerce-console-1.0-SNAPSHOT.jar
 * Access URL: http://localhost:8080
 * API Endpoints start with: http://localhost:8080/api/
 * 
 * 2. HEROKU DEPLOYMENT:
 * Platform: Heroku (Free tier available)
 * Steps:
 * a) Create a Heroku account at heroku.com
 * b) Install Heroku CLI: https://devcenter.heroku.com/articles/heroku-cli
 * c) Create Procfile in project root with: web: java -jar
 * target/ecommerce-console-1.0-SNAPSHOT.jar
 * d) Login: heroku login
 * e) Create app: heroku create your-app-name
 * f) Deploy: git push heroku main
 * g) Configure environment variables: heroku config:set DB_HOST=your-host
 * DB_USER=your-user
 * Access URL: https://your-app-name.herokuapp.com
 * 
 * 3. AWS ELASTIC BEANSTALK:
 * Platform: Amazon Web Services
 * Steps:
 * a) Package application: mvn clean package
 * b) Create AWS account and install AWS CLI
 * c) Initialize EB: eb init -p java-17
 * d) Create environment: eb create production
 * e) Deploy: eb deploy
 * f) Configure environment variables in AWS Console
 * Access URL: Provided by AWS after deployment
 * 
 * 4. MICROSOFT AZURE APP SERVICE:
 * Platform: Microsoft Azure
 * Steps:
 * a) Install Azure CLI:
 * https://docs.microsoft.com/en-us/cli/azure/install-azure-cli
 * b) Login: az login
 * c) Create resource group: az group create --name myResourceGroup --location
 * eastus
 * d) Deploy: az webapp up --name your-app-name --resource-group myResourceGroup
 * e) Or use VS Code Azure extension: Right-click project -> Deploy to Web App
 * Access URL: https://your-app-name.azurewebsites.net
 * 
 * 5. DOCKER CONTAINER:
 * Platform: Any Docker host (Docker Hub, AWS ECS, Google Cloud Run)
 * Steps:
 * a) Create Dockerfile (see example in project)
 * b) Build image: docker build -t ecommerce-api:latest .
 * c) Run locally: docker run -p 8080:8080 -e DB_HOST=host.docker.internal
 * ecommerce-api
 * d) Push to registry: docker tag ecommerce-api your-registry/ecommerce-api
 * docker push your-registry/ecommerce-api
 * e) Deploy to cloud platform of choice
 * 
 * 6. GOOGLE CLOUD PLATFORM (APP ENGINE):
 * Platform: Google Cloud
 * Steps:
 * a) Create GCP project and install gcloud CLI
 * b) Create app.yaml configuration file
 * c) Deploy: gcloud app deploy
 * Access URL: https://your-project-id.appspot.com
 * 
 * PREREQUISITES:
 * - Java 17 or higher installed
 * - Maven for building the project
 * - MySQL database accessible from hosting platform
 * - Configure database connection in .env file or environment variables
 * 
 * ENVIRONMENT VARIABLES REQUIRED:
 * - DB_HOST: Database host address
 * - DB_PORT: Database port (default: 3306)
 * - DB_NAME: Database name (default: ecommerce_db)
 * - DB_USER: Database username
 * - DB_PASSWORD: Database password
 * 
 * TESTING THE SERVICE:
 * Once deployed, test endpoints using:
 * - cURL: curl http://your-url/api/users
 * - Postman: Import endpoints and test
 * - Console client: Run the provided Java console client
 * - Web browser: For GET requests
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  E-Commerce REST API Service");
        System.out.println("=================================================");
        System.out.println();

        // Start Spring Boot application
        SpringApplication.run(Application.class, args);

        System.out.println();
        System.out.println("✓ Service started successfully!");
        System.out.println("✓ API available at: http://localhost:8080/api/");
        System.out.println();
        System.out.println("Available endpoints:");
        System.out.println("  - Users:      http://localhost:8080/api/users");
        System.out.println("  - Products:   http://localhost:8080/api/products");
        System.out.println("  - Categories: http://localhost:8080/api/categories");
        System.out.println("  - Orders:     http://localhost:8080/api/orders");
        System.out.println();
        System.out.println("Use the console client to test the API or use tools like Postman/cURL");
        System.out.println("=================================================");
    }
}
