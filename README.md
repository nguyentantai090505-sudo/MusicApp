# TKT MUSIC – ỨNG DỤNG NGHE NHẠC HIỆN ĐẠI

## UI Figma
Thiết kế giao diện ứng dụng trên Figma:  
https://www.figma.com/design/F55V60fdyBtNJexO0fjgt2/App-nghe-nh%E1%BA%A1c?node-id=0-1

---

## 1. Tên dự án và chủ đề

### Sơ lược về dự án
**TKT MUSIC** là ứng dụng nghe nhạc được xây dựng như một bản demo mô phỏng **Spotify**, tập trung vào giao diện hiện đại, trải nghiệm mượt mà và các tính năng cốt lõi của ứng dụng streaming.

Dự án mang tính học tập/demonstration, giúp lập trình viên rèn luyện:
- Thiết kế UI
- Quản lý trạng thái
- Làm việc với API
- Phát nhạc & điều khiển trình phát

### Mục tiêu của đề tài
- Xây dựng ứng dụng nghe nhạc giống Spotify (bản demo)
- Tái tạo các tính năng: tìm kiếm, phát nhạc, album, playlist, lưu yêu thích
- Tối ưu giao diện theo phong cách Spotify
- Rèn luyện kỹ năng lập trình Android & API

### Phạm vi dự án
- Không phải bản thương mại  
- Không dùng nội dung bản quyền Spotify  
- Không có thanh toán Premium  
- Tập trung chủ yếu vào UI/UX và phát nhạc cơ bản  

---

## 2. Lý do chọn dự án

### Âm nhạc là nhu cầu thiết yếu
Nghe nhạc trên smartphone là thói quen phổ biến → đề tài thực tế, dễ tiếp cận.

### Spotify – mô hình chuẩn mực
Spotify nổi bật ở:
- UI/UX đẹp
- Trải nghiệm mượt mà
- Quản lý dữ liệu tốt

→ Rất phù hợp để mô phỏng và học hỏi.

### Rèn luyện kỹ năng lập trình
Dự án bao gồm:
- UI/UX
- API
- Trạng thái ứng dụng
- Phát nhạc

### Dễ mở rộng – phù hợp học tập & nghiên cứu
Có thể nâng cấp: gợi ý nhạc, đề xuất, tài khoản, upload nhạc,…

---

## 3. Công nghệ sử dụng

### Mobile Development
- **Ngôn ngữ:** Kotlin  
- **UI Framework:** Jetpack Compose  
- **Kiến trúc:**  
  - MVVM  
  - Clean Architecture (3 tầng)  
  - Repository Pattern  
  - Hilt Dependency Injection  

---

## 4. Các tính năng chính của ứng dụng

### Phát nhạc trực tuyến
- Phát nhạc qua API  
- Thay thế bài nhạc bản quyền bằng demo  
- Play / Pause / Next / Previous  
- Tua thời gian theo thanh kéo  
- Tự chuyển bài khi phát xong  

### Tìm kiếm bài hát / nghệ sĩ / album
- Tìm kiếm theo từ khóa  
- Cập nhật kết quả theo thời gian thực  

### Xem chi tiết album & playlist
- Liệt kê bài hát trong album  
- Phát từng bài hoặc toàn bộ  

---

## 5. Cấu trúc dự án

```text
MusicApp-main/
├─ .gitignore
├─ README.md
├─ app/
│  ├─ build.gradle.kts
│  ├─ proguard-rules.pro
│  └─ src/
│     ├─ androidTest/          # Test tự động (Instrumentation tests)
│     ├─ test/                 # Unit tests
│     └─ main/
│        ├─ AndroidManifest.xml
│        ├─ java/com/example/tktmusicapp/
│        │   ├─ MainActivity.kt
│        │   │
│        │   ├─ ui/               # Giao diện người dùng (Jetpack Compose)
│        │   │   ├─ components/   # Thành phần UI tái sử dụng
│        │   │   │   ├─ CustomButton.kt
│        │   │   │   ├─ InputField.kt
│        │   │   │   └─ SongItem.kt
│        │   │   ├─ screens/      # Các màn hình chính
│        │   │   │   ├─ login/LoginScreen.kt
│        │   │   │   ├─ register/RegisterScreen.kt
│        │   │   │   ├─ home/HomeScreen.kt
│        │   │   │   ├─ player/PlayerScreen.kt
│        │   │   │   └─ splash/SplashScreen.kt
│        │   │   └─ theme/        # Theme Compose
│        │   │       ├─ Color.kt
│        │   │       ├─ Theme.kt
│        │   │       ├─ Typography.kt
│        │   │       └─ Shape.kt
│        │   │
│        │   ├─ navigation/       # Điều hướng
│        │   │   ├─ AppNavGraph.kt
│        │   │   └─ Routes.kt
│        │   │
│        │   ├─ data/             # Nguồn dữ liệu
│        │   │   ├─ local/
│        │   │   │   └─ PreferencesManager.kt
│        │   │   ├─ remote/       # API services
│        │   │   │   ├─ FirebaseAuthService.kt
│        │   │   │   ├─ FirebaseFirestoreService.kt
│        │   │   │   └─ SpotifyApiService.kt
│        │   │   └─ repository/
│        │   │       ├─ AuthRepository.kt
│        │   │       ├─ MusicRepository.kt
│        │   │       └─ impl/
│        │   │           ├─ AuthRepositoryImpl.kt
│        │   │           └─ MusicRepositoryImpl.kt
│        │   │
│        │   ├─ domain/           # Model và use cases
│        │   │   └─ model/
│        │   │       ├─ Song.kt
│        │   │       ├─ Playlist.kt
│        │   │       └─ User.kt
│        │   │
│        │   ├─ di/               # Dependency Injection (Hilt)
│        │   │   ├─ AppModule.kt
│        │   │   ├─ AuthModule.kt
│        │   │   └─ RepositoryModule.kt
│        │   │
│        │   └─ utils/            # Tiện ích
│        │       ├─ Constants.kt
│        │       └─ Validation.kt
│        │
│        └─ res/                  # Resources
│            ├─ layout/           # XML UI (nếu có, hiện tại dùng Compose nên ít dùng)
│            ├─ drawable/
│            ├─ values/
│            │   ├─ colors.xml
│            │   ├─ themes.xml
│            │   └─ strings.xml
│            └─ mipmap/           # Icon ứng dụng
└─ ... (các file Gradle khác ở root)
                  
6. Cài đặt ứng dụng + demo ứng dụng