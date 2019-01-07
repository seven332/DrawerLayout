/*
 * Copyright 2016 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.drawerlayout.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;
import com.hippo.drawerlayout.DrawerLayout;

public class MainActivity extends Activity {

    private static void transformPointToViewLocal(int[] point, View parent, View child) {
        ViewParent viewParent = child.getParent();

        while (viewParent instanceof View) {
            View view = (View) viewParent;
            point[0] += view.getScrollX() - child.getLeft();
            point[1] += view.getScrollY() - child.getTop();

            if (view == parent) {
                break;
            }

            child = view;
            viewParent = child.getParent();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        drawerLayout.setDrawerShadow(R.drawable.drawer_left_shadow, Gravity.LEFT);
        drawerLayout.setDrawerShadow(R.drawable.drawer_right_shadow, Gravity.RIGHT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu);

        final HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);

        drawerLayout.setGestureBlocker(new DrawerLayout.GestureBlocker() {
            @Override
            public boolean shouldBlockGesture(MotionEvent ev) {
                int[] point = new int[] {(int) ev.getX(), (int) ev.getY()};
                transformPointToViewLocal(point, drawerLayout, horizontalScrollView);

                return !drawerLayout.isDrawersVisible()
                    && point[0] > 0 && point[0] < horizontalScrollView.getWidth()
                    && point[1] > 0 && point[1] < horizontalScrollView.getHeight();
            }
        });
    }
}
