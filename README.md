TKT MUSIC - ỨNG NGHE NHẠC HIỆN ĐẠI

UI Figma:


https://www.figma.com/design/F55V60fdyBtNJexO0fjgt2/App-nghe-nh%E1%BA%A1c?node-id=0-1&t=zdDFbTI1YTiPZE35-1
1. Tên dự án và chủ đề
Sơ lược về dự án
TKT MUSIC là ứng dụng nghe nhạc được xây dựng như một bản demo mô phỏng Spotify, tập trung vào giao diện hiện đại, trải nghiệm người dùng mượt mà và các tính năng cốt lõi của một ứng dụng streaming nhạc.
Dự án mang tính học tập/demonstration, giúp người phát triển tìm hiểu quy trình xây dựng một app nghe nhạc hoàn chỉnh: từ thiết kế UI, quản lý trạng thái, đến xử lý API, phát nhạc và điều khiển trình phát.
- Mục tiêu của đề tài
  + Xây dựng một ứng dụng nghe nhạc giống Spotify ở mức độ demo.
  + Tái tạo các chức năng chính như: tìm kiếm, phát nhạc, xem album/playlist, lưu yêu thích.
  + Tối ưu giao diện theo phong cách Spotify.
  + Rèn luyện kỹ năng lập trình ứng dụng di động & làm việc với API nhạc.
- Phạm vi dự án
  + Không phải bản thương mại.
  + Không sử dụng nội dung bản quyền Spotify.
  + Không tích hợp thanh toán Premium.
  + Tập trung vào UI/UX + xử lý chức năng phát nhạc cơ bản.
2. Lý do chọn dự án
- Âm nhạc là nhu cầu thiết yếu và phổ biến
Nhu cầu nghe nhạc trên điện thoại ngày càng tăng mạnh. Spotify, Apple Music, Zing MP3… đã trở thành một phần quen thuộc trong đời sống. 
Vì vậy, việc tạo ra một ứng dụng nghe nhạc giúp người dùng thưởng thức âm thanh tiện lợi luôn là đề tài hấp dẫn và mang tính thực tiễn.
- Spotify là mô hình chuẩn mực để học hỏi
Spotify được xem là một trong những ứng dụng streaming nhạc tốt nhất thế giới, với:
  + UI/UX hiện đại
  + Tính năng mượt mà
  + Cách quản lý dữ liệu chuyên nghiệp
- Rèn luyện kỹ năng phát triển ứng dụng di động
Dự án bao gồm nhiều kỹ năng quan trọng:
  + Thiết kế UI/UX
  + Xử lý API và dữ liệu động
  + Quản lý trạng thái ứng dụng
  + Phát nhạc và điều khiển trình phát
Đây là cơ hội tốt để nâng cấp kỹ năng lập trình qua một ứng dụng có độ phức tạp vừa phải.
- Dễ mở rộng, phù hợp với học tập & nghiên cứu
Dự án có thể mở rộng thêm nhiều tính năng như: gợi ý nhạc, đồng bộ tài khoản, thuật toán đề xuất, nghe cùng nhau, đăng tải nhạc,… Điều này giúp đề tài linh hoạt, dễ nâng cấp nếu cần thực hiện trong môn học, báo cáo cuối kỳ hoặc portfolio cá nhân.
3. Công nghệ sử dụng
Dự án TKT MUSIC được xây dựng và phát triển dựa trên nền tảng các công nghệ hiện đại, tối ưu và mạnh mẽ nhwung vẫn đảm bảo được hiệu suất mượt mà cũng khả năng mở rộng và trải nghiệm người dùng được đặt lên ưu tiên hàng đầu
- Phát triển di động ( Mobile Development )
  + Ngôn ngữ lập trình: Kotlin
  + Framework UI: Jetpack Compose
  + Kiến trúc ứng dụng:
      - MVVM
      - Clean Architecture (3 tầng)
      - Repository Pattern
      - Hilt Dependency Injection
4. Các tính năng chính của của ứng dụng
- Phát nhạc trực tuyến:
  + Phát nhạc qua API, các bài hát có dính bản quyền từ phía nguồn API sẽ được thay thế bằng dữ liệu demo nhạc tự soạn
  + Hỗ trợ các thao tác như tua nhạc/play/pause/previous các bài nhạc, có thể tua nhạc theo ý muốn
  + Tự động chuyến bài khi phát xong
- Tìm kiếm bài hát / nghệ sĩ / album
  + Tìm kiếm theo từ khóa.
  + Hiển thị danh sách kết quả theo thời gian thực.
- Xem chi tiết album & playlist
  + Hiển thị danh sách bài hát trong album/playlist.
  + Cho phép phát toàn bộ hoặc từng bài riêng lẻ.
5. Cấu trúc dự án
'''
MusicApp-main/
├─ .gitignore
├─ README.md
└─ app/
   ├─ build.gradle.kts
   ├─ proguard-rules.pro
   └─ src/
      ├─ androidTest/
      │   └─ ... (Test tự động)
      ├─ test/
      │   └─ ... (Unit test)
      └─ main/
         ├─ AndroidManifest.xml
         ├─ java/com/example/tktmusicapp/
         │   ├─ MainActivity.kt
         │   │
         │   ├─ ui/                      # Giao diện người dùng
         │   │   ├─ components/
         │   │   │   ├─ CustomButton.kt
         │   │   │   ├─ InputField.kt
         │   │   │   └─ SongItem.kt
         │   │   ├─ screens/
         │   │   │   ├─ login/LoginScreen.kt
         │   │   │   ├─ register/RegisterScreen.kt
         │   │   │   ├─ home/HomeScreen.kt
         │   │   │   ├─ player/PlayerScreen.kt
         │   │   │   └─ splash/SplashScreen.kt
         │   │   └─ theme/
         │   │       ├─ Color.kt
         │   │       ├─ Theme.kt
         │   │       ├─ Typography.kt
         │   │       └─ Shape.kt
         │   │
         │   ├─ navigation/
         │   │   ├─ AppNavGraph.kt
         │   │   └─ Routes.kt
         │   │
         │   ├─ data/
         │   │   ├─ local/
         │   │   │   └─ PreferencesManager.kt
         │   │   ├─ remote/
         │   │   │   ├─ FirebaseAuthService.kt
         │   │   │   ├─ FirebaseFirestoreService.kt
         │   │   │   └─ SpotifyApiService.kt
         │   │   ├─ repository/
         │   │   │   ├─ AuthRepository.kt
         │   │   │   ├─ MusicRepository.kt
         │   │   │   └─ impl/
         │   │   │       ├─ AuthRepositoryImpl.kt
         │   │   │       └─ MusicRepositoryImpl.kt
         │   │
         │   ├─ domain/
         │   │   └─ model/
         │   │       ├─ Song.kt
         │   │       ├─ Playlist.kt
         │   │       └─ User.kt
         │   │
         │   ├─ di/                       # Dependency Injection (Hilt)
         │   │   ├─ AppModule.kt
         │   │   ├─ AuthModule.kt
         │   │   └─ RepositoryModule.kt
         │   │
         │   └─ utils/
         │       ├─ Constants.kt
         │       └─ Validation.kt
         │
         └─ res/
            ├─ layout/                    # XML UI (nếu có)
            ├─ drawable/
            ├─ values/
            │   ├─ colors.xml
            │   ├─ themes.xml
            │   └─ strings.xml
            └─ mipmap/                    # Icon app
'''
6. Cài đặt ứng dụng + demo ứng dụng
