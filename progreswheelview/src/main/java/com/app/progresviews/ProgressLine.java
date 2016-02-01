package com.app.progresviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.app.progreswheelview.R;

/**
 * Created by Zeki Guler on 01,February,2016
 * ©2015 Appscore. All Rights Reserved
 */
public class ProgressLine extends View {

    //Sizes (with defaults)
    private float  mBarWidth = 24f;
    private float mValueTextSize = 48;
    private float mDefTextSize = 24;
    private int layoutHeight = 0;
    private int layoutWidth = 0;

    //Colors (with defaults)
    private int    mProgressColor   = 0x61b50CCC;//Color.GREEN;
    private int    mUnderLineColor  = Color.GRAY;

    //Rectangles
    private RectF mUnderLineBounds = new RectF();
    private RectF mProgressBounds  = new RectF();

    //Painy
    private TextPaint mValueTextPaint = new TextPaint();
    private TextPaint mDefTextPaint = new TextPaint();
    private Paint mProgressLinePaint = new Paint();
    private Paint mUnderLinePaint = new Paint();


    //Padding (with defaults)
    private int marginTopBars       = 30;
    private int paddingBetweenText = 20;
    private int defaultPaddingLeft  = 30;
    private int defaultPaddingRight = 30;
    private int widthPaddingBetweenBars = 3;



    private String mValueText = "6,0090";
    private String mDefText = "daily steps";
    private float mDefTextHeight;
    private float mDefTextWidth;
    private float mValueTextHeight;
    private float mValueTextWidth;

    private int mPercentage = 70;


    public ProgressLine(Context context) {
        super(context);
        init(null, 0);
    }

    public ProgressLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ProgressLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ProgressLine, defStyle, 0);

        if(a.hasValue(R.styleable.ProgressLine_value))
            mValueText  =    a.getString(R.styleable.ProgressLine_value);
        if(a.hasValue(R.styleable.ProgressLine_definition))
            mDefText = a.getString(R.styleable.ProgressLine_definition);

        mBarWidth       = a.getDimension(R.styleable.ProgressLine_lineBarWidth, mBarWidth);
        mProgressColor  = a.getColor(R.styleable.ProgressLine_lineProgressColor, mProgressColor);
        mDefTextSize    = a.getDimension(R.styleable.ProgressLine_lineDefTextSize, mDefTextSize);
        mValueTextSize  = a.getDimension(R.styleable.ProgressLine_valueDefTextSize, mValueTextSize);
        mPercentage     = a.getInt(R.styleable.ProgressLine_valuePercentage, mPercentage);

        a.recycle();

        // Update TextPaint and text measurements from attributes
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        layoutWidth = w;
        layoutHeight = h;
        setupBounds();
        setupPaints();

        invalidate();
    }

    private void setupPaints() {
        mValueTextPaint.setColor(Color.BLACK);
        mValueTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mDefTextPaint.setColor(Color.BLACK);
        mDefTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);


        mProgressLinePaint.setColor(mProgressColor);
        mProgressLinePaint.setAntiAlias(true);
        mProgressLinePaint.setStyle(Paint.Style.FILL);
        mProgressLinePaint.setStrokeWidth(mBarWidth);

        mUnderLinePaint.setColor(mUnderLineColor);
        mUnderLinePaint.setAntiAlias(true);
        mUnderLinePaint.setStyle(Paint.Style.FILL);
        mUnderLinePaint.setStrokeWidth(3);


    }

    private void setupBounds() {

        // Count number text
        mValueTextPaint.setTextSize(mValueTextSize);
        Paint.FontMetrics fontMetrics = mValueTextPaint.getFontMetrics();
        mValueTextHeight = fontMetrics.bottom;
        mValueTextWidth = mValueTextPaint.measureText(mValueText);


        // Definition text
        mDefTextPaint.setTextSize(mDefTextSize);
        Paint.FontMetrics fontDefMetrics = mDefTextPaint.getFontMetrics();
        mDefTextHeight = fontDefMetrics.bottom;
        mDefTextWidth = mDefTextPaint.measureText(mDefText);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawText(mValueText,
                getPaddingLeft() + defaultPaddingLeft,
                this.getHeight() / 2 ,
                mValueTextPaint
        );

        canvas.drawText(mDefText,
                getPaddingLeft() + defaultPaddingLeft + paddingBetweenText + mValueTextWidth ,
                this.getHeight() / 2 ,
                mDefTextPaint
        );


        float endX = (float) (getWidth() * mPercentage / 100) - defaultPaddingRight - getPaddingRight();
        float startX = getPaddingLeft() + defaultPaddingLeft;

        canvas.drawLine(
                startX,
                this.getHeight() / 2 + marginTopBars,
                endX,
                this.getHeight() / 2 + marginTopBars,
                mProgressLinePaint);

        canvas.drawLine(
                (float) (getWidth() * mPercentage / 100) + widthPaddingBetweenBars,
                this.getHeight() / 2 + mBarWidth / 2 + marginTopBars,
                (float) (getWidth()) - getPaddingRight() - defaultPaddingRight,
                this.getHeight() / 2  + mBarWidth / 2 + marginTopBars,
                mUnderLinePaint);
    }
}
