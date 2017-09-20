package com.application.innove.obex.Utilityclass;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.application.innove.obex.R;

/**
 * Created by abhisheksharma on 04-Aug-2017.
 */

public class TypefacedEdittext extends AppCompatEditText {
    public TypefacedEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
        String fontName = styledAttrs.getString(R.styleable.TypefacedTextView_typeface);
        styledAttrs.recycle();

        if (fontName != null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "Font/Roboto-Regular.ttf");
            setTypeface(typeface);
        }
    }
}
