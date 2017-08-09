package net.ossrs.yasea.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class FilterControllerView extends LinearLayout implements SeekBar.OnSeekBarChangeListener {

    public static int LEVEL1 = 60;//0-100


    SeekBar skinBeauty;
    TextView skinBlurProgress;
    View level1Container;

    //REMOVED
    private ProgressListener progressListener;

    public FilterControllerView(Context context) {
        super(context);
    }

    public FilterControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FilterControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        skinBeauty.setOnSeekBarChangeListener(this);
    }

    private void initView() {
        skinBeauty = (SeekBar) findViewById(R.id.seek_bar_skin_beauty);
        skinBlurProgress = (TextView) findViewById(R.id.txtv_skin_blur_progress);
        level1Container = findViewById(R.id.ll_level1);
    }

    public void setListener(ProgressListener listener) {
        progressListener = listener;
    }

    @Override
    public void setVisibility(int visible) {
        level1Container.setVisibility(visible);
        super.setVisibility(visible);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek_bar_skin_beauty:
                skinBlurProgress.setText(String.valueOf(progress));
                LEVEL1 = progress;
                break;
            default:
                break;
        }
        if (progressListener != null) {
            progressListener.onProgressChanaged(LEVEL1);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void initProgress(int level1) {
        skinBeauty.setMax(100);
        skinBeauty.setProgress(level1);
        skinBlurProgress.setText(String.valueOf(level1));
    }

    public interface ProgressListener {
        boolean onProgressChanaged(int level1);
    }
}
