'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Image from 'next/image';
import { isAdmin } from '../utils/auth';

interface Car {
  id: number;
  name: string;
  specs: string;
  price: number;
  image: string;
}

export default function AdminPanel() {
  const router = useRouter();
  const [cars, setCars] = useState<Car[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [updating, setUpdating] = useState(false);

  useEffect(() => {
    const checkAuth = async () => {
      const admin = await isAdmin();
      if (!admin) {
        router.push('/giris');
      }
    };

    const fetchCars = async () => {
      try {
        const response = await fetch('/api/cars');
        if (!response.ok) {
          throw new Error('Araçlar yüklenemedi');
        }
        const data = await response.json();
        setCars(data);
      } catch (err) {
        setError('Araçlar yüklenirken bir hata oluştu');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    checkAuth();
    fetchCars();
  }, [router]);

  const handleUpdateCar = async (carId: number, newPrice: number, imageFile: File | null) => {
    try {
      setUpdating(true);
      setError('');
      
      let imageUrl = '';
      
      if (imageFile) {
        const formData = new FormData();
        formData.append('file', imageFile);
        
        const uploadResponse = await fetch('/api/upload', {
          method: 'POST',
          body: formData,
        });
        
        if (!uploadResponse.ok) {
          throw new Error('Resim yüklenemedi');
        }
        
        const { filePath } = await uploadResponse.json();
        imageUrl = filePath;
      }
      
      const response = await fetch('/api/cars', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          id: carId,
          price: Number(newPrice),
          ...(imageUrl && { image: imageUrl }),
        }),
      });

      if (!response.ok) {
        throw new Error('Araç güncellenemedi');
      }

      const updatedCars = await response.json();
      setCars(updatedCars);
    } catch (err) {
      setError('Araç güncellenirken bir hata oluştu');
      console.error(err);
    } finally {
      setUpdating(false);
    }
  };

  if (loading) {
    return <div className="p-4">Yükleniyor...</div>;
  }

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Admin Paneli</h1>
      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {cars.map((car) => (
          <div key={car.id} className="border p-4 rounded-lg">
            <Image
              src={car.image}
              alt={car.name}
              width={300}
              height={200}
              className="w-full h-48 object-cover mb-4 rounded"
            />
            <h2 className="text-xl font-semibold">{car.name}</h2>
            <p className="text-gray-600 mb-2">{car.specs}</p>
            <div className="flex flex-col gap-2">
              <div className="flex items-center gap-2">
                <input
                  type="number"
                  defaultValue={car.price}
                  className="border p-2 rounded w-full"
                  onChange={(e) => {
                    const newPrice = parseFloat(e.target.value);
                    if (!isNaN(newPrice) && newPrice > 0) {
                      handleUpdateCar(car.id, newPrice, null);
                    }
                  }}
                />
                <span className="text-gray-600">₺/gün</span>
              </div>
              <input
                type="file"
                accept="image/*"
                className="border p-2 rounded"
                onChange={(e) => {
                  const file = e.target.files?.[0];
                  if (file) {
                    handleUpdateCar(car.id, car.price, file);
                  }
                }}
              />
            </div>
          </div>
        ))}
      </div>
    </div>
  );
} 