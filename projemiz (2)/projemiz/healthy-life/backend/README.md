# Healthy Life - Backend Geliştirici Kılavuzu

## Proje Hakkında
Healthy Life, kullanıcıların sağlıklı yaşam hedeflerini takip etmelerine yardımcı olan bir uygulamadır. Bu belge, projenin backend kısmının geliştiriciler için teknik dokümantasyonunu içerir.

## Teknoloji Yığını
- Java 11
- Spring Boot 2.7.x
- Spring Security (JWT tabanlı kimlik doğrulama)
- Spring Data JPA
- PostgreSQL
- Maven
- Swagger/OpenAPI

## Başlangıç

### Gereksinimler
- JDK 11 veya üzeri
- Maven 3.6.x veya üzeri
- PostgreSQL 12 veya üzeri
- IDE (IntelliJ IDEA önerilir)

### Kurulum
1. Projeyi klonlayın:
```bash
git clone https://github.com/your-organization/healthy-life.git
cd healthy-life/backend
```

2. Veritabanını oluşturun:
```sql
CREATE DATABASE healthylife;
```

3. `application.properties` dosyasını düzenleyin:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/healthylife
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Projeyi derleyin:
```bash
mvn clean install
```

5. Uygulamayı çalıştırın:
```bash
mvn spring-boot:run
```

## Proje Yapısı

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── healthylife/
│   │           ├── application/    # Uygulama servisleri
│   │           ├── domain/         # Domain modelleri
│   │           ├── infrastructure/ # Altyapı konfigürasyonları
│   │           └── interfaces/     # REST API controllers
│   └── resources/
│       └── application.properties
└── test/
    └── java/                      # Test sınıfları
```

## Katmanlı Mimari

### Domain Katmanı
- Temel iş mantığı
- Entity sınıfları
- Value objects
- Domain servisleri

### Uygulama Katmanı
- Use case'lerin uygulanması
- İş akışı koordinasyonu
- Transaction yönetimi

### Altyapı Katmanı
- Veritabanı yapılandırması
- Güvenlik yapılandırması
- Harici servis entegrasyonları

### Arayüz Katmanı
- REST kontrolcüleri
- DTO'lar
- Request/Response modelleri

## API Dokümantasyonu
API dokümantasyonuna `http://localhost:8080/swagger-ui.html` adresinden erişilebilir.

## Güvenlik
- JWT tabanlı kimlik doğrulama
- Role tabanlı yetkilendirme
- API endpoint güvenliği

## Test
### Unit Testler
```bash
mvn test
```

### Integration Testler
```bash
mvn verify
```

## Monitoring ve Health Check
- Actuator endpoints: `http://localhost:8080/actuator`
- Health check: `http://localhost:8080/actuator/health`
- Metrics: `http://localhost:8080/actuator/metrics`

## Kod Kalitesi
- Checkstyle
- SonarQube
- JaCoCo kod kapsama raporu

## CI/CD
- GitHub Actions ile otomatik build ve test
- Docker container oluşturma
- Otomatik deployment

## Hata Ayıklama
### Loglar
- Loglar `logs/` dizininde tutulur
- Log seviyesi application.properties'den ayarlanabilir

### Yaygın Hatalar ve Çözümleri
1. Veritabanı Bağlantı Hatası
   - PostgreSQL servisinin çalıştığından emin olun
   - Veritabanı kimlik bilgilerini kontrol edin

2. JWT Token Hatası
   - Token süresinin dolmadığından emin olun
   - Secret key'in doğru olduğunu kontrol edin

## En İyi Pratikler
1. Kod Yazma Standartları
   - Google Java Style Guide takip edilmeli
   - Anlamlı değişken ve metod isimleri kullanılmalı
   - Her sınıf ve metod için Javadoc yazılmalı

2. Git Kullanımı
   - Feature branch'ler için: `feature/özellik-adı`
   - Bugfix branch'ler için: `bugfix/hata-adı`
   - Commit mesajları açıklayıcı olmalı

3. Code Review Süreci
   - Pull request açılmadan önce testler çalıştırılmalı
   - Kod kalite kuralları kontrol edilmeli
   - En az bir reviewer onayı alınmalı

## Katkıda Bulunma
1. Fork'layın
2. Feature branch oluşturun
3. Değişikliklerinizi commit'leyin
4. Branch'inizi push'layın
5. Pull Request açın

## İletişim
- Email: dev@healthylife.com
- Slack: #healthy-life-dev
- JIRA: https://healthylife.atlassian.net

## Lisans
Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakın. 