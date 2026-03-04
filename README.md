# rsv-stomp-stack

ROS2, Spring, Vue의 앞글자를 딴 약자 (26년 03월 03일 ~ 03월 05일 - 학습 레포지토리)

> 🤖 ROS2 UGV 로봇 관제 시스템 — Spring Boot STOMP WebSocket + Vue 3 대시보드 + Python AI 비전 서버

---

## 📁 프로젝트 구조

```
rsv-stomp-stack/
├── frontend/                        # Vue 3 UGV 대시보드 (Vite 빌드)
│   ├── src/
│   │   ├── components/              # UGV 제어 컴포넌트
│   │   │   ├── DriveControl.vue     # 주행 제어 (D-pad, 속도 슬라이더)
│   │   │   ├── MapView.vue          # 지도 / Nav (Canvas 기반)
│   │   │   ├── LidarView.vue        # LiDAR 시각화 (Canvas)
│   │   │   ├── ArmControl.vue       # 로봇팔 관절 제어
│   │   │   ├── StatusPanel.vue      # 로봇 상태 (배터리, IMU, 조인트)
│   │   │   ├── ConnectionBar.vue    # WebSocket 연결 바
│   │   │   └── LogPanel.vue         # 실시간 로그 패널
│   │   ├── composables/             # Vue Composables (WebSocket 공유 상태)
│   │   ├── router/
│   │   │   └── index.js             # Hash 라우터 (iframe 임베딩용)
│   │   ├── views/
│   │   │   ├── DashboardView.vue    # 메인 대시보드 뷰
│   │   │   └── LogHistoryView.vue   # 로그 히스토리 뷰
│   │   ├── App.vue
│   │   └── main.js
│   ├── vite.config.js               # 빌드 설정 (base: /ugv/, outDir: static/ugv)
│   └── package.json
│
├── src/main/
│   ├── java/com/example/rsv_stomp_stack/
│   │   ├── api/
│   │   │   ├── robots/              # 로봇 상태 REST API
│   │   │   ├── users/               # 사용자 프로필 API
│   │   │   └── visions/             # 비전 서버 제어 API
│   │   ├── config/                  # WebSocket / STOMP 설정
│   │   └── domain/                  # 엔티티 / 도메인 모델
│   └── resources/
│       ├── static/
│       │   ├── index.html           # 메인 관제 대시보드 (GridStack)
│       │   ├── css/
│       │   │   └── gridstack.min.css
│       │   ├── js/
│       │   │   └── gridstack-all.js
│       │   └── ugv/                 # Vue 빌드 결과물 (자동 생성)
│       │       ├── index.html
│       │       └── assets/
│       └── templates/
│
└── python_vision/                   # Python AI 비전 서버
    └── (YOLO v11 기반 카메라 스트리밍)
```

---

## 🛠️ 사용 라이브러리 / 기술 스택

### Backend (Spring Boot)
| 라이브러리 | 용도 |
|---|---|
| Spring Boot 3.x | 메인 서버 프레임워크 |
| Spring WebSocket + STOMP | 실시간 양방향 통신 |
| SockJS | WebSocket 폴백 처리 |
| Spring Data JPA | 데이터베이스 ORM |

### Frontend (Vue 3 UGV Dashboard)
| 라이브러리 | 용도 |
|---|---|
| Vue 3 | UI 프레임워크 |
| Vue Router (Hash Mode) | SPA 라우팅 (iframe 임베딩용) |
| @stomp/stompjs | WebSocket STOMP 클라이언트 |
| Vite | 빌드 도구 |

### Main Dashboard (Vanilla JS)
| 라이브러리 | 용도 |
|---|---|
| GridStack v12 | 드래그 앤 드롭 위젯 그리드 |
| Chart.js | 실시간 데이터 차트 |
| SockJS + STOMP.js | WebSocket 연결 |

### Python Vision Server
| 라이브러리 | 용도 |
|---|---|
| Flask | HTTP 스트리밍 서버 |
| YOLOv11 (Ultralytics) | 객체 감지 AI |
| OpenCV | 카메라 캡처 / 이미지 처리 |

---

## ✨ 주요 특징

- **드래그 앤 드롭 위젯 대시보드** — GridStack v12 기반으로 모듈을 자유롭게 배치
- **iframe 임베딩 방식** — Vue 앱을 Spring Boot static 리소스로 빌드 후 iframe으로 삽입 (CORS 없음)
- **Hash 라우터** — `createWebHashHistory()` 사용으로 서버 설정 없이 SPA 라우팅 가능
- **실시간 WebSocket** — STOMP 프로토콜로 로봇 상태 실시간 수신
- **AI 비전 스트리밍** — Python Flask + YOLO v11으로 카메라 영상에서 실시간 객체 감지
- **레이아웃 저장** — `localStorage`로 위젯 배치 영구 저장

### 🧩 대시보드 모듈 목록
| 모듈 | 타입 | 설명 |
|---|---|---|
| 📍 위치 정보 | `ROBOT_POS` | 로봇 위치 차트 |
| 🔋 배터리 | `ROBOT_BAT` | 배터리 상태 차트 |
| 📊 상태 요약 | `ROBOT_STT` | 종합 상태 차트 |
| 🎥 카메라 스트림 | `VISION_STREAM` | YOLO v11 AI 비전 |
| 🕹️ 주행 제어 | `UGV_DRIVE` | D-pad + 속도 슬라이더 |
| 🗺️ 지도/Nav | `UGV_MAP` | Canvas 지도 + 목표 지점 설정 |
| 📡 LiDAR | `UGV_LIDAR` | LiDAR 포인트 클라우드 시각화 |
| 🦾 로봇팔 제어 | `UGV_ARM` | 관절 슬라이더 + 그리퍼 |
| 📋 로봇 상태 | `UGV_STATUS` | 배터리, IMU, 조인트 상태 |
| 🔗 연결 바 | `UGV_CONNBAR` | WebSocket 연결 관리 |
| 📜 로그 패널 | `UGV_LOGPANEL` | 실시간 시스템 로그 |

---

## 🚀 실행 방법

### 1. Vue 대시보드 빌드
```powershell
cd rsv-stomp-stack/frontend
npm install
npm run build
# 빌드 결과: src/main/resources/static/ugv/
```

### 2. Spring Boot 서버 실행
```powershell
cd rsv-stomp-stack
./mvnw spring-boot:run
# 접속: http://localhost:8080
```

### 3. Python 비전 서버 실행 (선택)
```powershell
cd rsv-stomp-stack/python_vision
python app.py
# 스트림: http://localhost:5000/video_feed
```

---

## 📟 터미널별 명령어 정리

### 💻 Terminal 1 — Vue 빌드 (PowerShell)
```powershell
# 프로젝트 이동
cd E:\0_git_노도연사원\rsv-stomp-stack\rsv-stomp-stack\frontend

# 의존성 설치 (최초 1회)
npm install

# 개발 서버 실행 (선택)
npm run dev

# 프로덕션 빌드 → static/ugv/ 로 출력
npm run build

# 빌드 전 ugv 폴더 초기화 (선택)
Remove-Item ..\src\main\resources\static\ugv\assets\* -Force
```

### ☕ Terminal 2 — Spring Boot 서버 (PowerShell)
```powershell
# 프로젝트 이동
cd E:\0_git_노도연사원\rsv-stomp-stack\rsv-stomp-stack

# 서버 실행
./mvnw spring-boot:run

# 접속 주소
# http://localhost:8080
```

### 🐍 Terminal 3 — Python 비전 서버 (PowerShell)
```powershell
# 프로젝트 이동
cd E:\0_git_노도연사원\rsv-stomp-stack\rsv-stomp-stack\python_vision

# 가상환경 활성화
.\.venv\Scripts\activate

# 서버 실행
python app.py

# 스트리밍 주소
# http://localhost:5000/video_feed
```

### 🌿 Terminal 4 — Git (PowerShell)
```powershell
cd E:\0_git_노도연사원\rsv-stomp-stack

# 상태 확인
git status

# 스테이징
git add .

# 커밋
git commit -m "feat: UGV 대시보드 iframe 통합 및 모듈 추가"

# 푸시
git push origin main
```

---

## ⚙️ 주요 설정 파일

### `frontend/vite.config.js`
```javascript
export default defineConfig({
  plugins: [vue()],
  base: '/ugv/',                                          // Spring Boot static 경로
  build: {
    outDir: '../src/main/resources/static/ugv',           // 빌드 출력 위치
    emptyOutDir: true,                                    // 빌드 시 이전 파일 자동 삭제
  }
})
```

### `frontend/src/router/index.js`
```javascript
// ⚠️ iframe 임베딩 시 반드시 Hash 모드 사용
// createWebHistory → 서버 라우팅 필요 (Spring Boot에서 404)
// createWebHashHistory → 클라이언트 사이드 처리 (#/drive)
const router = createRouter({
  history: createWebHashHistory(),
  routes: [ ... ]
})
```

---

## 🐛 트러블슈팅 기록

### GridStack v12 드래그 앤 드롭 안 되는 문제
```javascript
// ❌ v12에서 작동 안 함
grid = GridStack.init({ dragIn: '.sidebar-item', dragInOptions: { ... } });

// ✅ v12 해결책: init 분리 + accept 강제 override
grid = GridStack.init({ cellHeight: 50, margin: 10, acceptWidgets: true });
GridStack.setupDragIn('.sidebar-item', { appendTo: 'body', helper: 'clone' });
grid.el.ddElement.ddDroppable.updateOption({ accept: (el) => true });
```

### Vue iframe 빈 화면 문제
```javascript
// ❌ createWebHistory → Spring Boot가 /ugv/drive 경로를 모름 → 404
history: createWebHistory()

// ✅ createWebHashHistory → #/drive 는 클라이언트에서만 처리
history: createWebHashHistory()

// iframe src 형식
'/ugv/index.html#/drive'   // ✅ Hash 방식
'/ugv/drive'               // ❌ History 방식 (404)
```

---

## 📝 개발 일지

| 날짜 | 내용 |
|---|---|
| 26-03-03 | Spring Boot STOMP WebSocket 구성, 학습 |
| 26-03-04 | GridStack 대시보드 기초, GridStack v12 드래그 트러블슈팅, Vue UGV 대시보드 iframe 통합, Python YOLO 비전 서버 연동, UGV 모듈 추가 (상태, 연결바, 로그패널), Vue 라우터 추가, README 정리|
| 26-03-05 | 객체탐지 학습, 캘리브레이션 라이브러리 찾기 |