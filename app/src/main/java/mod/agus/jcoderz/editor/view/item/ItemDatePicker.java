package mod.agus.jcoderz.editor.view.item;

import a.a.a.sy;
import a.a.a.wB;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.DatePicker;
import com.besome.sketch.beans.ViewBean;

public class ItemDatePicker extends DatePicker implements sy {


    public ViewBean f21a;
    public boolean b;
    public boolean c;
    public Paint d;
    public float e;

    public ItemDatePicker(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        this.e = wB.a(context, 1.0f);
        this.d = new Paint(1);
        this.d.setColor(-1785080368);
        setFocusable(false);
        setClickable(false);
        setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return this.f21a;
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

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public void setBean(ViewBean viewBean) {
        this.f21a = viewBean;
    }

    public void setFixed(boolean z) {
        this.c = z;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        float f = this.e;
        super.setPadding((int) (((float) i) * f), (int) (((float) i2) * f), (int) (((float) i3) * f), (int) (f * ((float) i4)));
    }

    public void setSelection(boolean z) {
        this.b = z;
        invalidate();
    }
}
