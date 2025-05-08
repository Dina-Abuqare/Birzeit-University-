package com.example.schoolsystem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowCompat;

public class LoginActivity extends AppCompatActivity {

    private boolean isPasswordVisible = false;
    private int currentMonkeyImageResId = R.drawable.monkey_open_eyes;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText passwordEditText = findViewById(R.id.password);
        ImageView monkeyImage = findViewById(R.id.monkeyImage);

        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.password,
                0,
                0,
                0);

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    passwordEditText.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.password,
                            0,
                            isPasswordVisible ? R.drawable.eye_show_password : R.drawable.eye_hide_password,
                            0);

                    // إذا كلمة السر مخفية (isPasswordVisible == false) الصورة تكون مغلقة
                    // إذا ظاهرة (isPasswordVisible == true) الصورة تكون monkey_peek
                    if (isPasswordVisible) {
                        fadeImageChange(monkeyImage, R.drawable.monkey_peek);
                    } else {
                        fadeImageChange(monkeyImage, R.drawable.monkey_closed_eyes);
                    }

                } else {
                    passwordEditText.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.password,
                            0,
                            0,
                            0);

                    // ارجاع صورة القرد الأصلية مع تلاشي فقط إذا اختلفت الصورة
                    fadeImageChange(monkeyImage, R.drawable.monkey_open_eyes);
                }
            }
            @Override public void afterTextChanged(Editable s) { }
        });

        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_END = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (passwordEditText.getCompoundDrawables()[DRAWABLE_END] != null) {
                        int drawableWidth = passwordEditText.getCompoundDrawables()[DRAWABLE_END].getBounds().width();
                        if (event.getRawX() >= (passwordEditText.getRight() - drawableWidth - passwordEditText.getPaddingEnd())) {
                            if (isPasswordVisible) {
                                // إخفاء كلمة السر
                                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                passwordEditText.setCompoundDrawablesWithIntrinsicBounds(
                                        R.drawable.password,
                                        0,
                                        R.drawable.eye_hide_password,
                                        0);
                                isPasswordVisible = false;

                                // ارجاع صورة القرد الأصلية أو المغلقة مع تلاشي
                                if (passwordEditText.getText().length() > 0) {
                                    fadeImageChange(monkeyImage, R.drawable.monkey_closed_eyes);
                                } else {
                                    fadeImageChange(monkeyImage, R.drawable.monkey_open_eyes);
                                }
                            } else {
                                // إظهار كلمة السر
                                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                passwordEditText.setCompoundDrawablesWithIntrinsicBounds(
                                        R.drawable.password,
                                        0,
                                        R.drawable.eye_show_password,
                                        0);
                                isPasswordVisible = true;

                                // تغيير صورة القرد إلى monkey_peek مع تلاشي
                                fadeImageChange(monkeyImage, R.drawable.peek);
                            }
                            // نقل مؤشر الكتابة لنهاية النص
                            passwordEditText.setSelection(passwordEditText.getText().length());
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    private void fadeImageChange(ImageView imageView, int newImageResId) {
        if (currentMonkeyImageResId == newImageResId) {
            return;
        }
        currentMonkeyImageResId = newImageResId;

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f);
        fadeOut.setDuration(200);
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setImageResource(newImageResId);
                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f);
                fadeIn.setDuration(200);
                fadeIn.start();
            }
        });
        fadeOut.start();
    }
}
