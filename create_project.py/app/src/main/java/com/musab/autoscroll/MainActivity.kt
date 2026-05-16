package com.musab.autoscroll

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAccessibility = findViewById<Button>(R.id.btn_accessibility)
        val btnTouchBlocker = findViewById<Button>(R.id.btn_touch_blocker)

        // 1. زر تفعيل التمرير التلقائي (يوجه المستخدم لإعدادات الوصول)
        btnAccessibility.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
            Toast.makeText(this, "ابحث عن 'Auto Scroller' وقم بتفعيله", Toast.LENGTH_LONG).show()
        }

        // 2. زر تفعيل وضع الجيب وقفل اللمس
        btnTouchBlocker.setOnClickListener {
            // التحقق مما إذا كان التطبيق يملك صلاحية الظهور فوق التطبيقات الأخرى
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
                Toast.makeText(this, "يرجى إعطاء صلاحية الظهور فوق التطبيقات أولاً", Toast.LENGTH_LONG).show()
            } else {
                // تشغيل خدمة قفل اللمس والشاشة السوداء
                val intent = Intent(this, TouchBlockerService::class.java)
                startService(intent)
                Toast.makeText(this, "تم تفعيل وضع الجيب! لإلغائه أغلق التطبيق", Toast.LENGTH_SHORT).show()
            }
        }
    }
}