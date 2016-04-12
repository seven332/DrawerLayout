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

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

import com.hippo.drawerlayout.DrawerLayoutChild;

public class DrawerViewRight extends FrameLayout implements DrawerLayoutChild {

    private int mFitPaddingTop;
    private int mActionBarSize;

    public DrawerViewRight(Context context) {
        super(context);
        init(context);
    }

    public DrawerViewRight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawerViewRight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mActionBarSize = getAttrDimensionPixelOffset(context, R.attr.actionBarSize);
    }

    @Override
    public void setFitPadding(int top, int bottom) {
        mFitPaddingTop = top;
    }

    @Override
    public int getLayoutPaddingTop() {
        return mFitPaddingTop + mActionBarSize;
    }

    @Override
    public int getLayoutPaddingBottom() {
        return 0;
    }

    private static int getAttrDimensionPixelOffset(Context context, @AttrRes int attrId) {
        TypedValue value = new TypedValue();
        getAttrValue(context, attrId, value);
        if (value.type == TypedValue.TYPE_DIMENSION) {
            return TypedValue.complexToDimensionPixelOffset(
                    value.data, context.getResources().getDisplayMetrics());
        }
        throw new Resources.NotFoundException(
                "Attribute ID #0x" + Integer.toHexString(attrId) + " type #0x"
                        + Integer.toHexString(value.type) + " is not valid");
    }

    private static void getAttrValue(Context context, int attrId, TypedValue value) {
        context.getTheme().resolveAttribute(attrId, value, true);
    }
}
