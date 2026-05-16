package com.musab.autoscroll

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.View
import android.view.WindowManager

class TouchBlockerService : Service() {

    private var windowManager: WindowManager? = null
    private var blockerView: View? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // إنشاء واجهة برمجية تغطي الشاشة بالكامل
        blockerView = View(this)
        // اللون أسود مع تعتيم كامل (يمكنك جعله شفافاً كلياً بـ Color.TRANSPARENT إذا كنت تريد رؤية الفيديو)
        // الأفضل هو الأسود Color.BLACK لحماية الهاتف في الجيب وحفظ البطارية
        blockerView?.setBackgroundColor(Color.BLACK) 

        // إعدادات النافذة العائمة لمنع اللمس
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or 
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, // إبقاء الشاشة تعمل أثناء التمرير
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.CENTER

        // إضافة الطبقة فوق كل التطبيقات
        windowManager?.addView(blockerView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        // إزالة الطبقة عند إغلاق الخدمة ليعود اللمس طبيعياً
        if (blockerView != null && windowManager != null) {
            windowManager?.removeView(blockerView)
        }
    }
}