package com.musab.autoscroll

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import java.util.Timer
import java.util.TimerTask

class AutoScrollService : AccessibilityService() {

    private var timer: Timer? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun onServiceConnected() {
        super.onServiceConnected()
        // بدء المؤقت للتمرير التلقائي كل 15 ثانية (يمكنك تعديل الوقت هنا)
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    triggerSwipeUp()
                }
            }
        }, 5000, 15000) // يبدأ بعد 5 ثوانٍ، ويتكرر كل 15 ثانية
    }

    private fun triggerSwipeUp() {
        // تحديد مسار السحب البرمجي من أسفل الشاشة إلى أعلاها
        val swipePath = Path()
        // الإحداثيات التقريبية (تعتمد على أبعاد الشاشة، هنا نأخذ قيم وسطية)
        swipePath.moveTo(500f, 1500f) // نقطة البداية (أسفل)
        swipePath.lineTo(500f, 300f)  // نقطة النهاية (أعلى)

        val gestureBuilder = GestureDescription.Builder()
        // تحديد فترة السحبة (تستغرق 300 مللي ثانية)
        gestureBuilder.addStroke(GestureDescription.StrokeDescription(swipePath, 0, 300))
        
        // تنفيذ السحبة على النظام
        dispatchGesture(gestureBuilder.build(), null, null)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // لا نحتاج للتفاعل مع الأحداث حالياً
    }

    override fun onInterrupt() {
        // عند إيقاف الخدمة
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        timer = null
    }
}