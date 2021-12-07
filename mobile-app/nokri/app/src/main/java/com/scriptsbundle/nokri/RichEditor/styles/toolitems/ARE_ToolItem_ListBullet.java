package com.scriptsbundle.nokri.RichEditor.styles.toolitems;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scriptsbundle.nokri.R;
import com.scriptsbundle.nokri.RichEditor.AREditText;
import com.scriptsbundle.nokri.RichEditor.Util;
import com.scriptsbundle.nokri.RichEditor.styles.IARE_Style;
import com.scriptsbundle.nokri.RichEditor.styles.toolitems.styles.ARE_Style_ListBullet;

/**
 * Created by wliu on 13/08/2018.
 */

public class ARE_ToolItem_ListBullet extends ARE_ToolItem_Abstract {

    @Override
    public IARE_ToolItem_Updater getToolItemUpdater() {
        return null;
    }

    @Override
    public IARE_Style getStyle() {
        if (mStyle == null) {
            AREditText editText = this.getEditText();
            mStyle = new ARE_Style_ListBullet(editText, (ImageView) mToolItemView);
        }
        return mStyle;
    }

    @Override
    public View getView(Context context) {
        if (null == context) {
            return mToolItemView;
        }
        if (mToolItemView == null) {
            ImageView imageView = new ImageView(context);
            int size = Util.getPixelByDp(context, 40);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.listbullet);
            imageView.bringToFront();
            mToolItemView = imageView;
        }

        return mToolItemView;
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        return;
    }
}
