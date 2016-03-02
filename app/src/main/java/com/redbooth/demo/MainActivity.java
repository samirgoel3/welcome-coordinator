package com.redbooth.demo;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.redbooth.WelcomeCoordinatorLayout;
import com.redbooth.demo.animators.ChatAvatarsAnimator;
import com.redbooth.demo.animators.RocketAvatarsAnimator;
import com.redbooth.demo.animators.RocketFlightAwayAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private boolean animationReady = false;
    @Bind(R.id.coordinator) WelcomeCoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializePages();
    }

    private void initializePages() {
        final ValueAnimator backgroundAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                        0xff68B7AB, 0xff5088B8, 0xff61A3B6, 0xffffffff);
        backgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                coordinatorLayout.setBackgroundColor((int)animation.getAnimatedValue());
            }
        });
        coordinatorLayout.setOnPageScrollListener(new WelcomeCoordinatorLayout.OnPageScrollListener() {
            @Override
            public void onScrollPage(View v, float progress, float maximum) {
                if (!animationReady) {
                    animationReady = true;
                    backgroundAnimator.setDuration((long) maximum);
                }
                backgroundAnimator.setCurrentPlayTime((long) progress);
            }

            @Override
            public void onPageSelected(View v, int pageSelected) {
                switch (pageSelected) {
                    case 0:
                        new RocketAvatarsAnimator(coordinatorLayout).play();
                        break;
                    case 1:
                        new ChatAvatarsAnimator(coordinatorLayout).play();
                        break;
                    case 3:
                        new RocketFlightAwayAnimator(coordinatorLayout).play();
                        break;
                }
            }
        });
        coordinatorLayout.addPage(R.layout.welcome_page_1,
                R.layout.welcome_page_2,
                R.layout.welcome_page_3,
                R.layout.welcome_page_4);
    }
}
