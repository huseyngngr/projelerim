# Katkıda Bulunma Kılavuzu

## Commit Mesajı Kuralları

Commit mesajlarımız [Conventional Commits](https://www.conventionalcommits.org/) standardını takip eder.

### Format

```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

### Tipler

- `feat`: Yeni bir özellik
- `fix`: Hata düzeltmesi
- `docs`: Sadece dokümantasyon değişiklikleri
- `style`: Kod davranışını etkilemeyen değişiklikler (boşluk, biçimlendirme, noktalama vb.)
- `refactor`: Hata düzeltmesi veya özellik eklemeyen kod değişikliği
- `perf`: Performans iyileştirmesi yapan kod değişikliği
- `test`: Test dosyaları ile ilgili değişiklikler
- `build`: Build sistemi veya dış bağımlılıkları etkileyen değişiklikler
- `ci`: CI yapılandırma dosyaları ve scriptlerde değişiklikler
- `chore`: Kaynak kodu veya testleri etkilemeyen diğer değişiklikler

### Scope

Değişikliğin hangi modülü/bileşeni etkilediğini belirtir:

- `auth`: Kimlik doğrulama ile ilgili
- `user`: Kullanıcı yönetimi ile ilgili
- `health`: Sağlık metrikleri ile ilgili
- `api`: API değişiklikleri
- `db`: Veritabanı değişiklikleri
- `security`: Güvenlik ile ilgili
- `ui`: Kullanıcı arayüzü ile ilgili
- `config`: Yapılandırma ile ilgili
- `deps`: Bağımlılıklar ile ilgili

### Örnekler

```
feat(user): kullanıcı profil fotoğrafı yükleme özelliği eklendi

fix(auth): JWT token yenileme hatası düzeltildi

docs(api): API dokümantasyonu güncellendi

style(ui): kod formatlaması düzeltildi

refactor(health): sağlık metrik hesaplama mantığı iyileştirildi

perf(db): veritabanı sorguları optimize edildi

test(auth): kimlik doğrulama testleri eklendi

build(deps): Spring Boot versiyonu 2.7.x'e güncellendi

ci(workflow): SonarQube analizi eklendi

chore(git): .gitignore güncellendi
```

## Pull Request Süreci

### PR Açmadan Önce

1. Kodunuzu test edin
2. Lint kontrollerini yapın
3. Commit mesajlarınızın kurallara uyduğundan emin olun
4. Değişikliklerinizi dokümante edin

### PR Template

```markdown
## Açıklama
[Değişikliklerinizi kısaca açıklayın]

## Değişiklik Tipi
- [ ] Bug fix
- [ ] Yeni özellik
- [ ] Breaking change
- [ ] Dokümantasyon güncellemesi

## Kontrol Listesi
- [ ] Testler yazıldı ve başarıyla çalışıyor
- [ ] Kod kalite standartlarına uyuluyor
- [ ] Dokümantasyon güncellendi
- [ ] Commit mesajları kurallara uygun

## Test Senaryoları
[Test ettiğiniz senaryoları listeleyin]

## Screenshots (varsa)
[Ekran görüntülerini ekleyin]

## Notlar
[Eklemek istediğiniz notları yazın]
```

## Kod Kalite Standartları

### Java Kod Stili

- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) takip edilmelidir
- Maksimum satır uzunluğu: 120 karakter
- Girintileme: 4 boşluk
- UTF-8 karakter kodlaması

### Naming Conventions

- Sınıf isimleri: PascalCase (`UserService`)
- Metod isimleri: camelCase (`getUserById`)
- Değişken isimleri: camelCase (`firstName`)
- Sabit isimleri: UPPER_SNAKE_CASE (`MAX_RETRY_COUNT`)
- Test sınıfları: Test ile bitmeli (`UserServiceTest`)

### Dokümantasyon

- Tüm public API'ler için Javadoc yazılmalı
- Karmaşık iş mantığı için kod içi yorumlar eklenmeli
- README dosyaları güncel tutulmalı

### Test Standartları

- Birim testler için JUnit 5 kullanılmalı
- Test coverage minimum %80 olmalı
- Her public metod için en az bir test yazılmalı
- Test isimleri açıklayıcı olmalı (`should_ReturnUser_When_ValidIdProvided`)

## Review Süreci

### Reviewer Sorumlulukları

- Kod kalitesini kontrol etmek
- Test coverage'ı değerlendirmek
- Dokümantasyonu incelemek
- Güvenlik açıklarını kontrol etmek
- Performans etkisini değerlendirmek

### Review Kriterleri

- Kod okunabilir mi?
- Testler yeterli mi?
- Dokümantasyon güncel mi?
- Güvenlik riskleri var mı?
- Performans etkileniyor mu?
- Kodlama standartlarına uyuluyor mu?

## Lisans

Bu projeye katkıda bulunarak, katkılarınızın MIT lisansı altında lisanslanmasını kabul etmiş olursunuz. 