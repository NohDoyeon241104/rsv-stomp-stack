import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    host: '0.0.0.0',
    port: 5173
  },
    // 26-03-04 NDY : Spring Boot static 폴더로 빌드 결과 출력
  base: '/ugv/',
  build: {
    outDir: '../src/main/resources/static/ugv',  // ← ../ 한 번만!
    emptyOutDir: true   // ← 이거 추가 // 빌드 전에 기존 파일 삭제 옵션 (기본값: true) - true로 설정하면 빌드 전에 기존 파일이 삭제됩니다. false로 설정하면 기존 파일이 유지됩니다.
  }
})
