# Healthy Life Dağıtım Kılavuzu

## İçindekiler
1. [Gereksinimler](#gereksinimler)
2. [Ortam Hazırlığı](#ortam-hazırlığı)
3. [Docker ile Dağıtım](#docker-ile-dağıtım)
4. [Kubernetes ile Dağıtım](#kubernetes-ile-dağıtım)
5. [Manuel Dağıtım](#manuel-dağıtım)
6. [Veritabanı Kurulumu](#veritabanı-kurulumu)
7. [Ortam Değişkenleri](#ortam-değişkenleri)
8. [SSL/TLS Yapılandırması](#ssltls-yapılandırması)
9. [Monitoring Kurulumu](#monitoring-kurulumu)
10. [Yedekleme Stratejisi](#yedekleme-stratejisi)
11. [Sorun Giderme](#sorun-giderme)

## Gereksinimler

### Minimum Sistem Gereksinimleri
- 2 CPU çekirdek
- 4GB RAM
- 20GB disk alanı
- Ubuntu 20.04 LTS veya üzeri

### Yazılım Gereksinimleri
- Java 11 veya üzeri
- PostgreSQL 12 veya üzeri
- Docker 20.10 veya üzeri (Docker ile dağıtım için)
- Kubernetes 1.19 veya üzeri (Kubernetes ile dağıtım için)
- Nginx 1.18 veya üzeri (Reverse proxy için)

## Ortam Hazırlığı

### 1. Java Kurulumu
```bash
# OpenJDK 11 kurulumu
sudo apt update
sudo apt install openjdk-11-jdk

# JAVA_HOME ayarı
echo "export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64" >> ~/.bashrc
echo "export PATH=\$PATH:\$JAVA_HOME/bin" >> ~/.bashrc
source ~/.bashrc
```

### 2. PostgreSQL Kurulumu
```bash
# PostgreSQL kurulumu
sudo apt install postgresql postgresql-contrib

# Servisin başlatılması
sudo systemctl start postgresql
sudo systemctl enable postgresql

# Veritabanı kullanıcısı oluşturma
sudo -u postgres createuser --interactive --pwprompt healthylife
```

## Docker ile Dağıtım

### 1. Dockerfile Oluşturma
```dockerfile
FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 2. Docker Image Oluşturma ve Yükleme
```bash
# Image oluşturma
mvn clean package
docker build -t healthylife:1.0.0 .

# Docker Hub'a yükleme
docker tag healthylife:1.0.0 your-registry/healthylife:1.0.0
docker push your-registry/healthylife:1.0.0
```

### 3. Docker Compose ile Çalıştırma
```yaml
version: '3.8'

services:
  app:
    image: healthylife:1.0.0
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/healthylife
      - SPRING_DATASOURCE_USERNAME=healthylife
      - SPRING_DATASOURCE_PASSWORD=your_password
    depends_on:
      - db

  db:
    image: postgres:12
    environment:
      - POSTGRES_DB=healthylife
      - POSTGRES_USER=healthylife
      - POSTGRES_PASSWORD=your_password
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

## Kubernetes ile Dağıtım

### 1. Kubernetes Manifest Dosyaları

#### deployment.yaml
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: healthylife
spec:
  replicas: 3
  selector:
    matchLabels:
      app: healthylife
  template:
    metadata:
      labels:
        app: healthylife
    spec:
      containers:
      - name: healthylife
        image: your-registry/healthylife:1.0.0
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: SPRING_DATASOURCE_URL
          valueFrom:
            configMapKeyRef:
              name: healthylife-config
              key: database-url
        resources:
          requests:
            memory: "512Mi"
            cpu: "500m"
          limits:
            memory: "1Gi"
            cpu: "1000m"
```

#### service.yaml
```yaml
apiVersion: v1
kind: Service
metadata:
  name: healthylife
spec:
  selector:
    app: healthylife
  ports:
  - port: 80
    targetPort: 8080
  type: LoadBalancer
```

### 2. Kubernetes'e Dağıtım
```bash
# ConfigMap oluşturma
kubectl create configmap healthylife-config --from-file=config/

# Secret oluşturma
kubectl create secret generic healthylife-secrets \
  --from-literal=db-password=your_password \
  --from-literal=jwt-secret=your_jwt_secret

# Dağıtımı uygulama
kubectl apply -f k8s/
```

## Manuel Dağıtım

### 1. JAR Dosyası Oluşturma
```bash
mvn clean package -DskipTests
```

### 2. Systemd Service Oluşturma
```ini
[Unit]
Description=Healthy Life Application
After=syslog.target network.target

[Service]
User=healthylife
ExecStart=/usr/bin/java -jar /opt/healthylife/app.jar
SuccessExitStatus=143
Restart=always
RestartSec=5
Environment=SPRING_PROFILES_ACTIVE=prod

[Install]
WantedBy=multi-user.target
```

### 3. Nginx Reverse Proxy Yapılandırması
```nginx
server {
    listen 80;
    server_name healthylife.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## Veritabanı Kurulumu

### 1. Veritabanı ve Kullanıcı Oluşturma
```sql
CREATE DATABASE healthylife;
CREATE USER healthylife WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE healthylife TO healthylife;
```

### 2. Veritabanı Yedekleme
```bash
# Yedek alma
pg_dump -U healthylife -F c -b -v -f backup.dump healthylife

# Yedekten geri yükleme
pg_restore -U healthylife -d healthylife backup.dump
```

## Ortam Değişkenleri

Uygulama için gerekli ortam değişkenleri:

```properties
# Veritabanı
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/healthylife
SPRING_DATASOURCE_USERNAME=healthylife
SPRING_DATASOURCE_PASSWORD=your_password

# JWT
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000

# Mail
SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=your_email
SPRING_MAIL_PASSWORD=your_email_password

# Logging
LOGGING_LEVEL_ROOT=INFO
LOGGING_FILE_PATH=/var/log/healthylife
```

## SSL/TLS Yapılandırması

### 1. SSL Sertifikası Oluşturma
```bash
# Self-signed sertifika oluşturma
keytool -genkeypair -alias healthylife \
  -keyalg RSA -keysize 2048 -storetype PKCS12 \
  -keystore healthylife.p12 -validity 365
```

### 2. Spring Boot SSL Yapılandırması
```properties
server.ssl.key-store-type=PKCS12
server.ssl.key-store=classpath:healthylife.p12
server.ssl.key-store-password=your_password
server.ssl.key-alias=healthylife
```

## Monitoring Kurulumu

### 1. Prometheus Yapılandırması
```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'healthylife'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']
```

### 2. Grafana Dashboard Kurulumu
- JVM Metrics Dashboard ID: 4701
- Spring Boot Statistics Dashboard ID: 6756

## Yedekleme Stratejisi

### 1. Veritabanı Yedekleme
```bash
#!/bin/bash
BACKUP_DIR="/backup/database"
DATE=$(date +%Y%m%d_%H%M%S)
pg_dump -U healthylife -F c -b -v -f "$BACKUP_DIR/healthylife_$DATE.dump" healthylife
```

### 2. Uygulama Logları Yedekleme
```bash
#!/bin/bash
BACKUP_DIR="/backup/logs"
DATE=$(date +%Y%m%d)
tar -czf "$BACKUP_DIR/logs_$DATE.tar.gz" /var/log/healthylife/
```

## Sorun Giderme

### Yaygın Hatalar ve Çözümleri

1. Uygulama Başlatma Hataları
```bash
# Log dosyalarını kontrol et
tail -f /var/log/healthylife/application.log

# JVM bellek durumunu kontrol et
jmap -heap <pid>
```

2. Veritabanı Bağlantı Hataları
```bash
# PostgreSQL servis durumunu kontrol et
systemctl status postgresql

# Bağlantı testi
psql -U healthylife -h localhost -d healthylife
```

3. Container Hataları
```bash
# Container loglarını kontrol et
docker logs healthylife

# Container durumunu kontrol et
docker inspect healthylife
```

### Performans İzleme

1. JVM Performans Metrikleri
```bash
# Thread dump alma
jstack <pid> > thread_dump.txt

# Heap dump alma
jmap -dump:format=b,file=heap_dump.hprof <pid>
```

2. Sistem Kaynakları İzleme
```bash
# CPU ve RAM kullanımı
top -p <pid>

# Disk kullanımı
df -h
``` 