import Image from "next/image";

export default function Home() {
  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <header className="bg-white shadow-md">
        <div className="container mx-auto px-4 py-6">
          <nav className="flex items-center justify-between">
            <div className="text-2xl font-bold text-blue-600">AracKirala.com</div>
            <div className="space-x-4">
              <a href="/araclar" className="text-gray-600 hover:text-blue-600">Araçlar</a>
              <a href="/hakkimizda" className="text-gray-600 hover:text-blue-600">Hakkımızda</a>
              <a href="/iletisim" className="text-gray-600 hover:text-blue-600">İletişim</a>
              <a href="/giris" className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">Giriş Yap</a>
            </div>
          </nav>
        </div>
      </header>

      {/* Hero Section */}
      <section className="container mx-auto px-4 py-16">
        <div className="text-center">
          <h1 className="text-4xl font-bold text-gray-800 mb-4">
            En Uygun Fiyatlarla Araç Kiralama
          </h1>
          <p className="text-xl text-gray-600 mb-8">
            Türkiye'nin her yerinde güvenilir ve konforlu araç kiralama hizmeti
          </p>
          <div className="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow-lg">
            <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
              <select className="p-2 border rounded-lg">
                <option>Şehir Seçin</option>
                <option>İstanbul</option>
                <option>Ankara</option>
                <option>İzmir</option>
              </select>
              <input
                type="date"
                className="p-2 border rounded-lg"
                placeholder="Alış Tarihi"
              />
              <input
                type="date"
                className="p-2 border rounded-lg"
                placeholder="Dönüş Tarihi"
              />
              <button className="bg-blue-600 text-white p-2 rounded-lg hover:bg-blue-700">
                Araç Ara
              </button>
            </div>
          </div>
        </div>
      </section>

      {/* Featured Cars */}
      <section className="container mx-auto px-4 py-16">
        <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">
          Öne Çıkan Araçlar
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {[
            {
              id: 1,
              name: "Volkswagen Passat",
              specs: "Otomatik • Dizel • 5 Kişilik",
              price: "750",
              image: "/car1.jpg"
            },
            {
              id: 2,
              name: "BMW 3 Serisi",
              specs: "Otomatik • Benzin • 5 Kişilik",
              price: "950",
              image: "/car2.jpg"
            },
            {
              id: 3,
              name: "Mercedes C-Serisi",
              specs: "Otomatik • Dizel • 5 Kişilik",
              price: "1100",
              image: "/car3.jpg"
            }
          ].map((car) => (
            <div key={car.id} className="bg-white rounded-lg shadow-lg overflow-hidden">
              <div className="relative h-48">
                <img
                  src={car.image}
                  alt={car.name}
                  className="w-full h-full object-cover"
                />
              </div>
              <div className="p-4">
                <h3 className="text-xl font-semibold mb-2">{car.name}</h3>
                <p className="text-gray-600 mb-4">{car.specs}</p>
                <div className="flex justify-between items-center">
                  <span className="text-2xl font-bold text-blue-600">₺{car.price}/gün</span>
                  <button className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                    Hemen Kirala
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-800 text-white py-8">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
            <div>
              <h3 className="text-xl font-bold mb-4">AracKirala.com</h3>
              <p className="text-gray-400">
                Türkiye'nin en güvenilir araç kiralama platformu
              </p>
            </div>
            <div>
              <h4 className="text-lg font-semibold mb-4">Hızlı Linkler</h4>
              <ul className="space-y-2">
                <li><a href="/araclar" className="text-gray-400 hover:text-white">Tüm Araçlar</a></li>
                <li><a href="/kampanyalar" className="text-gray-400 hover:text-white">Kampanyalar</a></li>
                <li><a href="/blog" className="text-gray-400 hover:text-white">Blog</a></li>
              </ul>
            </div>
            <div>
              <h4 className="text-lg font-semibold mb-4">İletişim</h4>
              <ul className="space-y-2">
                <li className="text-gray-400">info@arackirala.com</li>
                <li className="text-gray-400">+90 (555) 123 45 67</li>
                <li className="text-gray-400">İstanbul, Türkiye</li>
              </ul>
            </div>
            <div>
              <h4 className="text-lg font-semibold mb-4">Bizi Takip Edin</h4>
              <div className="flex space-x-4">
                <a href="#" className="text-gray-400 hover:text-white">Facebook</a>
                <a href="#" className="text-gray-400 hover:text-white">Twitter</a>
                <a href="#" className="text-gray-400 hover:text-white">Instagram</a>
              </div>
            </div>
          </div>
        </div>
      </footer>
    </div>
  );
}
