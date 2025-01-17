package dev.aldi.sayuti.editor.view.item;

import a.a.a.sy;
import a.a.a.wB;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.besome.sketch.beans.ViewBean;

public class ItemBottomNavigationView extends LinearLayout implements sy {

    /* renamed from: a  reason: collision with root package name */
    public ViewBean f12a;
    public boolean b;
    public boolean c;
    public Paint d;
    public float e;
    public ImageView f;

    public ItemBottomNavigationView(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        this.e = wB.a(context, 1.0f);
        this.d = new Paint(1);
        this.d.setColor(-1785080368);
        setDrawingCacheEnabled(true);
        this.f = new ImageView(getContext());
        this.f.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.f.setImageResource(2131166307);
        this.f.setScaleType(ImageView.ScaleType.FIT_XY);
        this.f.setPadding(0, 0, 0, 0);
        addView(this.f);
        setGravity(17);
    }

    public ViewBean getBean() {
        return this.f12a;
    }

    public boolean getFixed() {
        return this.c;
    }

    public boolean getSelection() {
        return this.b;
    }

    public void onDraw(Canvas canvas) {
        if (this.b) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.d);
        }
        super.onDraw(canvas);
    }

    public void setBean(ViewBean viewBean) {
        this.f12a = viewBean;
    }

    public void setFixed(boolean z) {
        this.c = z;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.e;
        super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (f2 * ((float) i4)));
    }

    public void setSelection(boolean z) {
        this.b = z;
        invalidate();
    }
}
