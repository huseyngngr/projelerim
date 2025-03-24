import { NextResponse } from 'next/server';
import { writeFile, mkdir } from 'fs/promises';
import path from 'path';
import { isAdmin } from '@/app/utils/auth';

export async function POST(request: Request) {
  try {
    // Admin kontrolü
    const admin = await isAdmin();
    if (!admin) {
      return NextResponse.json(
        { error: 'Yetkisiz erişim' },
        { status: 401 }
      );
    }

    const formData = await request.formData();
    const file = formData.get('file') as File;

    if (!file) {
      return NextResponse.json(
        { error: 'Dosya bulunamadı' },
        { status: 400 }
      );
    }

    // Dosya tipini kontrol et
    if (!file.type.startsWith('image/')) {
      return NextResponse.json(
        { error: 'Sadece resim dosyaları yüklenebilir' },
        { status: 400 }
      );
    }

    const bytes = await file.arrayBuffer();
    const buffer = Buffer.from(bytes);

    // Dosya adını oluştur
    const timestamp = Date.now();
    const originalName = file.name;
    const extension = path.extname(originalName);
    const fileName = `car-${timestamp}${extension}`;
    const uploadsDir = path.join(process.cwd(), 'public', 'uploads');
    const filePath = path.join(uploadsDir, fileName);
    const publicPath = `/uploads/${fileName}`;

    try {
      // uploads klasörünü oluştur (eğer yoksa)
      await mkdir(uploadsDir, { recursive: true });

      // Dosyayı kaydet
      await writeFile(filePath, buffer);

      return NextResponse.json({ filePath: publicPath });
    } catch (error) {
      console.error('Dosya kaydetme hatası:', error);
      return NextResponse.json(
        { error: 'Dosya kaydedilemedi' },
        { status: 500 }
      );
    }
  } catch (error) {
    console.error('Dosya yükleme hatası:', error);
    return NextResponse.json(
      { error: 'Dosya yüklenemedi' },
      { status: 500 }
    );
  }
} 