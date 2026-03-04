"""
vision_server.py
- OpenCV  : 노트북 웹캠(0번) 제어
- YOLOv11 : 실시간 객체 탐지
- Flask   : localhost:5000/video_feed 스트리밍 송출
"""

from flask import Flask, Response
from ultralytics import YOLO
import cv2

app = Flask(__name__)

# -------------------------------------------------------
# 1. YOLOv11 모델 로드 (최초 실행 시 자동 다운로드 ~6MB)
# -------------------------------------------------------
model = YOLO("yolo11n.pt")  # n = nano (가장 빠른 경량 모델)

# -------------------------------------------------------
# 2. 웹캠 오픈 (0 = 노트북 기본 카메라)
# -------------------------------------------------------
cap = cv2.VideoCapture(0)

if not cap.isOpened():
    raise RuntimeError("❌ 카메라를 열 수 없습니다. 웹캠이 연결되어 있는지 확인하세요.")


def generate_frames():
    """
    카메라 프레임을 읽어 YOLO 탐지 후
    MJPEG 스트림으로 yield
    """
    while True:
        success, frame = cap.read()
        if not success:
            break

        # YOLO 추론 (conf=0.4 이상만 표시)
        results = model(frame, conf=0.4, verbose=False)

        # 탐지 결과(바운딩박스 + 라벨)를 프레임에 그리기
        annotated_frame = results[0].plot()

        # JPEG 인코딩
        ret, buffer = cv2.imencode(".jpg", annotated_frame)
        if not ret:
            continue

        # MJPEG 포맷으로 yield
        yield (
            b"--frame\r\n"
            b"Content-Type: image/jpeg\r\n\r\n"
            + buffer.tobytes()
            + b"\r\n"
        )


# -------------------------------------------------------
# 3. Flask 라우트
# -------------------------------------------------------
@app.route("/")
def index():
    return """
    <h2>🎥 Vision Server 실행 중</h2>
    <p>스트림 주소: <a href="/video_feed">http://localhost:5000/video_feed</a></p>
    <img src="/video_feed" style="width:640px; border:2px solid #1abc9c;">
    """

@app.route("/video_feed")
def video_feed():
    """대시보드 img 태그가 바라보는 스트리밍 엔드포인트"""
    return Response(
        generate_frames(),
        mimetype="multipart/x-mixed-replace; boundary=frame"
    )


# -------------------------------------------------------
# 4. 서버 실행
# -------------------------------------------------------
if __name__ == "__main__":
    print("=" * 50)
    print("🚀 Vision Server 시작")
    print("📡 스트림 주소: http://localhost:5000/video_feed")
    print("⛔ 종료: Ctrl + C")
    print("=" * 50)
    app.run(host="0.0.0.0", port=5000, debug=False)