package com.besome.sketch.editor.view.palette;

import android.content.Context;
import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;

public class IconButton extends IconBase {
    public IconButton(Context context) {
        super(context);
    }

    @Override // com.besome.sketch.lib.base.BaseWidget
    public void a(Context context) {
        super.a(context);
        setWidgetImage(2131166237);
        setWidgetName("Button");
    }

    @Override // com.besome.sketch.editor.view.palette.IconBase
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 3;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.text.text = getName();
        viewBean.convert = "Button";
        return viewBean;
    }
}
