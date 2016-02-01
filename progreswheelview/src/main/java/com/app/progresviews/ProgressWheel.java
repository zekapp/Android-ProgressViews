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
 * TODO: document your custom view class.
 */
public class ProgressWheel extends View {

    //Sizes (with defaults)
    private float  mBarWidth = 24f;
    private float mCountTextSize= 48; // TODO: use a default from R.dimen...
    private float mDefTextSize = 24;
    private int layoutHeight = 0;
    private int layoutWidth = 0;

    //Colors (with defaults)
    private int    mProgressColor   = Color.GREEN;
    private int    mRimColor        = 0xEEEEEEEE;
    private int    mCountTextColor  = Color.BLACK;
    private int    mDefTextColor    = Color.BLACK;

    //Padding (with defaults)
    private int paddingTop = 5;
    private int paddingBottom = 5;
    private int paddingLeft = 5;
    private int paddingRight = 5;

    //Rectangles
    private RectF mRimBounds = new RectF();
    private RectF mProgressBounds = new RectF();

    //Paints
    private Paint mCirclePaint = new Paint();
    private Paint mBarPaint = new Paint();
    private TextPaint mCountTextPaint = new TextPaint();
    private TextPaint mDefTextPaint = new TextPaint();


    private String mCountText = "10,000"; // TODO: use a default from R.string...
    private String mDefText = "Steps"; // TODO: use a default from R.string...


    private float mCountTextWidth;
    private float mCountTextHeight;
    private float mDefTextHeight;
    private float mDefTextWidth;

    // Set percentage
    private int mPercentage = 60;

    public ProgressWheel(Context context) {
        super(context);
        init(null, 0);
    }

    public ProgressWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ProgressWheel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
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
        mBarPaint.setColor(mProgressColor);
        mBarPaint.setAntiAlias(true);
        mBarPaint.setStyle(Paint.Style.STROKE);
        mBarPaint.setStrokeWidth(mBarWidth);
        mBarPaint.setStrokeCap(Paint.Cap.ROUND);


        mCirclePaint.setColor(mRimColor);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mBarWidth);

        mCountTextPaint.setColor(mCountTextColor);
        mCountTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mDefTextPaint.setColor(mDefTextColor);
        mDefTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private void setupBounds(){
        int minValue = Math.min(layoutWidth, layoutHeight);

        // Calc the Offset if needed
        int xOffset = layoutWidth - minValue;
        int yOffset = layoutHeight - minValue;

        // Offset
        paddingTop = this.getPaddingTop() + (yOffset / 2);
        paddingBottom = this.getPaddingBottom() + (yOffset / 2);
        paddingLeft = this.getPaddingLeft() + (xOffset / 2);
        paddingRight = this.getPaddingRight() + (xOffset / 2);

        int width = getWidth();
        int height = getHeight();

        mRimBounds =  new RectF(
                paddingLeft + mBarWidth,
                paddingTop + mBarWidth,
                width - paddingRight - mBarWidth,
                height - paddingBottom - mBarWidth);

        mProgressBounds = new RectF(
                paddingLeft + mBarWidth,
                paddingTop + mBarWidth,
                width - paddingRight - mBarWidth,
                height - paddingBottom - mBarWidth);

        // Count number text
        mCountTextPaint.setTextSize(mCountTextSize);
        Paint.FontMetrics fontMetrics = mCountTextPaint.getFontMetrics();
        mCountTextHeight = fontMetrics.bottom;
        mCountTextWidth = mCountTextPaint.measureText(mCountText == null || mCountText.isEmpty()?" ":mCountText);

        // Definition text
        mDefTextPaint.setTextSize(mDefTextSize);
        Paint.FontMetrics fontDefMetrics = mDefTextPaint.getFontMetrics();
        mDefTextHeight = fontDefMetrics.bottom;
        mDefTextWidth = mDefTextPaint.measureText( mDefText == null || mDefText.isEmpty()?" " : mDefText);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ProgressWheel, defStyle, 0);

        if(a.hasValue(R.styleable.ProgressWheel_countText))
            mCountText  =    a.getString(R.styleable.ProgressWheel_countText);
        if(a.hasValue(R.styleable.ProgressWheel_definitionText))
            mDefText = a.getString(R.styleable.ProgressWheel_definitionText);

        mBarWidth       = a.getDimension(R.styleable.ProgressWheel_barWidth, mBarWidth);
        mProgressColor  = a.getColor(R.styleable.ProgressWheel_progressColor, mProgressColor);
        mRimColor       = a.getColor(R.styleable.ProgressWheel_rimColor, mRimColor);
        mCountTextColor = a.getColor(R.styleable.ProgressWheel_countTextColor, mCountTextColor);
        mDefTextColor   = a.getColor(R.styleable.ProgressWheel_defTextColor, mDefTextColor);
        mCountTextSize  = a.getDimension(R.styleable.ProgressWheel_countTextSize, mCountTextSize);
        mDefTextSize    = a.getDimension(R.styleable.ProgressWheel_defTextSize, mDefTextSize);
        mPercentage     = a.getInt(R.styleable.ProgressWheel_percentage, mPercentage);

        a.recycle();

        // Set up a default TextPaint object
        mCountTextPaint = new TextPaint();
        mCountTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mCountTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRimBounds, 0, 360, false, mCirclePaint);
        canvas.drawArc(mProgressBounds, -90, mPercentage, false, mBarPaint);

        float horizontalCountTextOffset = mCountTextPaint.measureText(mCountText) / 2;

        canvas.drawText(mCountText,
                    this.getWidth() / 2 - horizontalCountTextOffset,
                    this.getHeight() / 2 ,
                    mCountTextPaint
                );

        float horizontalDefTextOffset = mDefTextPaint.measureText(mDefText) / 2;

        canvas.drawText(mDefText,
                this.getWidth() / 2 - horizontalDefTextOffset,
                this.getHeight() / 2 + mCountTextHeight + 20,
                mDefTextPaint
        );
    }

    public void setStepCountText(String countText) {
        mCountText = countText;
        invalidate();
    }

    public void setDefText(String defText) {
        mDefText = defText;
        invalidate();
    }

    public void setPercentage(int per) {
        mPercentage = per;
        invalidate();
    }
}
