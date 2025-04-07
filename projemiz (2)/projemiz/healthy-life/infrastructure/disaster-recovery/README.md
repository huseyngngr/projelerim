# Healthy Life - Felaket Kurtarma Planı

Bu doküman, Healthy Life uygulaması için felaket kurtarma prosedürlerini detaylandırır.

## 1. Yedekleme Stratejisi

### 1.1 Otomatik Yedeklemeler
- **Günlük Yedeklemeler**
  - Zaman: Her gün 01:00 UTC
  - Saklama Süresi: 30 gün
  - Soğuk Depolamaya Taşıma: 7 gün sonra
  
- **Haftalık Yedeklemeler**
  - Zaman: Her Pazar 01:00 UTC
  - Saklama Süresi: 90 gün
  - Soğuk Depolamaya Taşıma: 30 gün sonra

### 1.2 Yedeklenen Kaynaklar
- PostgreSQL veritabanı
- Redis önbellek
- S3 depolama
- DynamoDB tabloları
- Kubernetes yapılandırmaları

## 2. Felaket Senaryoları ve Yanıtları

### 2.1 Veritabanı Hatası
1. **Otomatik Failover**
   - RDS Multi-AZ yapılandırması ile otomatik yük devri
   - Tahmini kurtarma süresi: 2-3 dakika

2. **Manuel Müdahale Gerekirse**
   ```bash
   # Son yedekten geri yükleme
   aws rds restore-db-instance-from-db-snapshot \
     --db-instance-identifier healthylife-db-restored \
     --db-snapshot-identifier <snapshot-id>
   ```

### 2.2 Redis Hatası
1. **Otomatik Failover**
   - Redis Sentinel ile otomatik yük devri
   - Tahmini kurtarma süresi: 30 saniye

2. **Manuel Müdahale Gerekirse**
   ```bash
   # Yeni Redis cluster oluşturma
   aws elasticache create-cache-cluster \
     --cache-cluster-id healthylife-redis-restored \
     --snapshot-name <snapshot-name>
   ```

### 2.3 Uygulama Hatası
1. **Pod Hatası**
   - Kubernetes self-healing ile otomatik yeniden başlatma
   - Tahmini kurtarma süresi: 30 saniye

2. **Node Hatası**
   - Node pool auto-scaling ile yeni node oluşturma
   - Pod'ların yeni node'a taşınması
   - Tahmini kurtarma süresi: 2-3 dakika

### 2.4 Bölge Hatası
1. **Otomatik Failover**
   - Route 53 health check ile yedek bölgeye yönlendirme
   - Tahmini kurtarma süresi: 5 dakika

2. **Manuel Bölge Değişimi**
   ```bash
   # DNS güncellemesi
   aws route53 change-resource-record-sets \
     --hosted-zone-id <zone-id> \
     --change-batch file://dns-failover.json
   ```

## 3. Test Prosedürleri

### 3.1 Düzenli Testler
- Her ay bir felaket senaryosu test edilmelidir
- Test sonuçları dokümante edilmelidir
- İyileştirme önerileri kaydedilmelidir

### 3.2 Test Senaryoları
1. **Veritabanı Failover Testi**
   ```bash
   # Test başlatma
   aws rds failover-db-cluster \
     --db-cluster-identifier healthylife-db-cluster
   ```

2. **Uygulama Yük Devri Testi**
   ```bash
   # Test başlatma
   kubectl drain <node-name>
   ```

3. **Tam Geri Yükleme Testi**
   - Yedekten test ortamına geri yükleme
   - Veri tutarlılığı kontrolü
   - Performans kontrolü

## 4. İletişim ve Eskalasyon

### 4.1 İletişim Kanalları
- **Birincil**: PagerDuty
- **İkincil**: Slack (#incidents kanalı)
- **Üçüncül**: E-posta ve SMS

### 4.2 Eskalasyon Süreci
1. **Seviye 1**: DevOps Ekibi (15 dakika)
2. **Seviye 2**: Sistem Mimarı (30 dakika)
3. **Seviye 3**: CTO (1 saat)

## 5. Kurtarma Sonrası Prosedürler

### 5.1 Doğrulama Kontrolleri
- Veri tutarlılığı kontrolü
- Performans metrikleri kontrolü
- Güvenlik kontrolü
- Bağlantı testleri

### 5.2 Dokümantasyon
- Olay raporu hazırlama
- Öğrenilen dersleri kaydetme
- Prosedürleri güncelleme

### 5.3 İyileştirme Önerileri
- Teknik iyileştirmeler
- Süreç iyileştirmeleri
- Dokümantasyon güncellemeleri

## 6. Bakım ve Güncelleme

### 6.1 Plan Güncellemesi
- 3 ayda bir plan gözden geçirilmeli
- Değişiklikler dokümante edilmeli
- Tüm ekip bilgilendirilmeli

### 6.2 Eğitim
- Yeni ekip üyeleri için eğitim
- Düzenli tatbikatlar
- Senaryo bazlı alıştırmalar 