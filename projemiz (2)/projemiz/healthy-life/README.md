# Healthy Life

[![CI/CD Pipeline](https://github.com/your-organization/healthy-life/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/your-organization/healthy-life/actions/workflows/ci-cd.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=healthylife&metric=alert_status)](https://sonarcloud.io/dashboard?id=healthylife)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=healthylife&metric=coverage)](https://sonarcloud.io/dashboard?id=healthylife)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=healthylife&metric=security_rating)](https://sonarcloud.io/dashboard?id=healthylife)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=healthylife&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=healthylife)
[![Docker Pulls](https://img.shields.io/docker/pulls/your-registry/healthylife)](https://hub.docker.com/r/your-registry/healthylife)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Sağlıklı yaşam hedeflerinizi takip etmenize yardımcı olan kapsamlı bir uygulama.

## 🚀 Özellikler

- 👤 Kullanıcı yönetimi ve profil özelleştirme
- 🔒 Güvenli kimlik doğrulama ve yetkilendirme
- 📊 Sağlık metriklerinin takibi
- 📱 Responsive tasarım
- 📝 Detaylı raporlama
- 🔍 Gelişmiş arama ve filtreleme
- 📈 İstatistikler ve grafikler

## 🛠 Teknoloji Yığını

### Backend
- Java 11
- Spring Boot 2.7.x
- Spring Security
- PostgreSQL
- JUnit 5
- Swagger/OpenAPI

### DevOps
- Docker
- Kubernetes
- GitHub Actions
- SonarQube
- OWASP ZAP
- Prometheus & Grafana

## 📦 Kurulum

Detaylı kurulum talimatları için [DEPLOYMENT.md](backend/docs/DEPLOYMENT.md) dosyasına bakın.

### Hızlı Başlangıç

```bash
# Repository'yi klonlayın
git clone https://github.com/your-organization/healthy-life.git

# Backend dizinine gidin
cd healthy-life/backend

# Bağımlılıkları yükleyin
mvn clean install

# Uygulamayı başlatın
mvn spring-boot:run
```

## 📚 Dokümantasyon

- [API Dokümantasyonu](http://localhost:8080/swagger-ui.html)
- [Geliştirici Kılavuzu](backend/docs/DEVELOPMENT.md)
- [Dağıtım Talimatları](backend/docs/DEPLOYMENT.md)

## 🧪 Test

```bash
# Unit testleri çalıştır
mvn test

# Integration testleri çalıştır
mvn verify

# Güvenlik taraması yap
mvn verify -Psecurity
```

## 📊 Monitoring

- Actuator: `http://localhost:8080/actuator`
- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000`

## 🔒 Güvenlik

- OWASP güvenlik standartları
- Otomatik güvenlik taraması
- Düzenli güvenlik güncellemeleri
- SSL/TLS şifreleme

## 🤝 Katkıda Bulunma

1. Fork'layın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'feat: Add amazing feature'`)
4. Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 📝 Lisans

Bu proje MIT lisansı altında lisanslanmıştır - detaylar için [LICENSE](LICENSE) dosyasına bakın.

## 📫 İletişim

- Email: dev@healthylife.com
- Slack: #healthy-life-dev
- JIRA: https://healthylife.atlassian.net

## 🙏 Teşekkürler

Bu projeye katkıda bulunan herkese teşekkür ederiz! 