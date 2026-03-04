import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
import DashboardView from '@/views/DashboardView.vue'
import LogHistoryView from '@/views/LogHistoryView.vue'

// 26-03-04 NDY : Grid형 대시보드에 현재 vue 컴포넌트를 라우터해서 가져다가 쓰기 위한 단독 컴포넌트 라우트 추가
// 26-03-04 NDY : 단독 컴포넌트 라우트를 위한 import 추가
import DriveControl from '@/components/DriveControl.vue'
import MapView from '@/components/MapView.vue'
import LidarView from '@/components/LidarView.vue'
import ArmControl from '@/components/ArmControl.vue'
// 26-03-04 NDY : 나머지 모듈들 전부 추가
// 26-03-04 NDY: 단독 모듈 추가
import StatusPanel from '@/components/StatusPanel.vue'
import ConnectionBar from '@/components/ConnectionBar.vue'
import LogPanel from '@/components/LogPanel.vue'

const router = createRouter({
  // history: createWebHistory(),   // 기존 
  history: createWebHashHistory(),  // ← 이게 핵심! 빌드 후엔 createWebHashHistory()로 바꿔야 #/drive 방식이 작동
  routes: [
    { path: '/', name: 'dashboard', component: DashboardView },
    { path: '/logs', name: 'logs', component: LogHistoryView },

    // 26-03-04 NDY : Grid형 대시보드 iframe 모듈용 단독 라우트
    { path: '/drive', name: 'drive', component: DriveControl },
    { path: '/map',   name: 'map',   component: MapView },
    { path: '/lidar', name: 'lidar', component: LidarView },
    { path: '/arm',   name: 'arm',   component: ArmControl },

    // 26-03-04 NDY : Grid형 대시보드 나머지 단독 라우트
    // 26-03-04 NDY: 단독 모듈 라우트
    { path: '/status',  name: 'status',     component: StatusPanel },
    { path: '/connbar', name: 'connbar',    component: ConnectionBar },
    { path: '/logpanel',name: 'logpanel',   component: LogPanel },
  ]
})

export default router
