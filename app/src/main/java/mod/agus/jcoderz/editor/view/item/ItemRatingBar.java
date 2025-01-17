package mod.agus.jcoderz.editor.view.item;

import a.a.a.sy;
import a.a.a.wB;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.RatingBar;
import com.besome.sketch.beans.ViewBean;

public class ItemRatingBar extends RatingBar implements sy {
    public ViewBean b;
    public boolean c;
    public boolean d;
    public Paint e;
    public float f;

    public ItemRatingBar(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        this.f = wB.a(context, 1.0f);
        this.e = new Paint(1);
        this.e.setColor(-1785080368);
        setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return this.b;
    }

    public boolean getFixed() {
        return this.d;
    }

    public boolean getSelection() {
        return this.c;
    }

    public void onDraw(Canvas canvas) {
        if (this.c) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.e);
        }
        super.onDraw(canvas);
    }

    public void setBean(ViewBean viewBean) {
        this.b = viewBean;
    }

    public void setFixed(boolean z) {
        this.d = z;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.f;
        super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (f2 * ((float) i4)));
    }

    public void setSelection(boolean z) {
        this.c = z;
        invalidate();
    }
}
