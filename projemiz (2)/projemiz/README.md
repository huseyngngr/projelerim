# Healthy Life Application

A comprehensive health tracking and monitoring application built with Spring Boot and React.

## Features

- User authentication and authorization
- Health metrics tracking (weight, blood pressure, heart rate, steps)
- Real-time data visualization
- Personalized health recommendations
- API documentation with OpenAPI/Swagger
- Comprehensive monitoring and alerting

## Technology Stack

- Backend: Spring Boot 3.x
- Frontend: React 18.x
- Database: PostgreSQL
- Cache: Redis
- Monitoring: Prometheus & Grafana
- Container Orchestration: Kubernetes
- CI/CD: GitHub Actions

## Getting Started

### Prerequisites

- JDK 17
- Maven 3.8+
- Docker & Docker Compose
- Kubernetes cluster (for production deployment)

### Local Development

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/healthy-life.git
   cd healthy-life
   ```

2. Start dependencies (PostgreSQL, Redis):
   ```bash
   docker-compose up -d
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Access the application:
   - Backend API: http://localhost:8080/api
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - Frontend: http://localhost:3000

### Production Deployment

1. Build the Docker image:
   ```bash
   docker build -t healthylife:latest .
   ```

2. Deploy to Kubernetes:
   ```bash
   kubectl apply -f kubernetes/overlays/production
   ```

## Documentation

- [API Documentation](docs/api/README.md)
- [Architecture Overview](docs/architecture/README.md)
- [Deployment Guide](docs/deployment/README.md)
- [Development Guide](docs/development/README.md)

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## Monitoring & Alerts

- Grafana Dashboard: http://monitoring.healthylife.com
- Prometheus Metrics: http://monitoring.healthylife.com/prometheus

## Security

For security issues, please contact security@healthylife.com

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details 