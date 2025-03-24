import { NextResponse } from 'next/server';
import { promises as fs } from 'fs';
import path from 'path';
import { isAdmin } from '@/app/utils/auth';

// Basit bir veritabanı simülasyonu için JSON dosyası
const dataFilePath = path.join(process.cwd(), 'public', 'data', 'cars.json');

// Araç verilerini oku
async function getCars() {
  try {
    const data = await fs.readFile(dataFilePath, 'utf8');
    return JSON.parse(data);
  } catch (error) {
    console.error('Araç verileri okunamadı:', error);
    return [];
  }
}

// Araç verilerini güncelle
async function updateCars(cars: any[]) {
  try {
    await fs.writeFile(dataFilePath, JSON.stringify(cars, null, 2));
  } catch (error) {
    console.error('Araç verileri güncellenemedi:', error);
    throw error;
  }
}

export async function GET() {
  try {
    const cars = await getCars();
    return NextResponse.json(cars);
  } catch (error) {
    return NextResponse.json(
      { error: 'Araç verileri alınamadı' },
      { status: 500 }
    );
  }
}

export async function PUT(request: Request) {
  try {
    const admin = await isAdmin();
    if (!admin) {
      return NextResponse.json(
        { error: 'Yetkisiz erişim' },
        { status: 401 }
      );
    }

    const { id, price, image } = await request.json();
    
    if (typeof id !== 'number' || (price !== undefined && typeof price !== 'number')) {
      return NextResponse.json(
        { error: 'Geçersiz veri formatı' },
        { status: 400 }
      );
    }

    const cars = await getCars();
    const carIndex = cars.findIndex((car: any) => car.id === id);

    if (carIndex === -1) {
      return NextResponse.json(
        { error: 'Araç bulunamadı' },
        { status: 404 }
      );
    }

    // Eski resmi sil (eğer yeni resim varsa)
    if (image && cars[carIndex].image) {
      const oldImagePath = path.join(process.cwd(), 'public', cars[carIndex].image);
      try {
        await fs.unlink(oldImagePath);
      } catch (error) {
        console.error('Eski resim silinemedi:', error);
      }
    }

    // Aracı güncelle
    cars[carIndex] = {
      ...cars[carIndex],
      ...(price !== undefined && { price: Number(price) }),
      ...(image && { image }),
    };

    await updateCars(cars);
    return NextResponse.json(cars);
  } catch (error) {
    console.error('Güncelleme hatası:', error);
    return NextResponse.json(
      { error: 'Araç güncellenemedi' },
      { status: 500 }
    );
  }
} 