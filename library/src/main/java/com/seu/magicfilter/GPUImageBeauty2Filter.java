package com.seu.magicfilter;

import android.opengl.GLES20;

import com.seu.magicfilter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.utils.MagicFilterType;

import net.ossrs.yasea.R;


/**
 * @author wangshuo
 */
public class GPUImageBeauty2Filter extends GPUImageFilter {

    private int b;
    private int c;
    private int d;
    private float e;
    private float f;
    private int g;
    private int h;


    public GPUImageBeauty2Filter() {
        super(MagicFilterType.BEAUTY2, R.raw.beauty2);
    }

    @Override
    public void onInit() {
        super.onInit();
        this.h = GLES20.glGetUniformLocation(this.getProgram(), "singleStepOffset");
        this.b = GLES20.glGetUniformLocation(this.getProgram(), "aaCoef");
        this.c = GLES20.glGetUniformLocation(this.getProgram(), "mixCoef");
        this.d = GLES20.glGetUniformLocation(this.getProgram(), "iternum");

        setFilterLevel(0);
    }

    @Override
    public void onInputSizeChanged(final int width, final int height) {
        super.onInputSizeChanged(width, height);
        float var10001 = (float)width;
        float var5 = (float)height;
        float var4 = var10001;
        this.setFloatVec2(this.h, new float[]{2.0F / var4, 2.0F / var5});
    }

    public void setFilterLevel(float level1, float level2, float level3) {
        this.setFilterLevel(level1);
    }

    public void setFilterLevel(float flag) {
        switch((int)(flag / 20.0F + 1.0F)) {
            case 1:
                this.setFilterLevel(1, 0.19F, 0.54F);
                return;
            case 2:
                this.setFilterLevel(2, 0.29F, 0.54F);
                return;
            case 3:
                this.setFilterLevel(3, 0.17F, 0.39F);
                return;
            case 4:
                this.setFilterLevel(3, 0.25F, 0.54F);
                return;
            case 5:
                this.setFilterLevel(4, 0.13F, 0.54F);
                return;
            case 6:
                this.setFilterLevel(4, 0.19F, 0.69F);
                return;
            default:
                this.setFilterLevel(0, 0.0F, 0.0F);
        }
    }

    private void setFilterLevel(int var1, float var2, float var3) {
        this.g = var1;
        this.e = var2;
        this.f = var3;
        //onDrawArrays();
    }

   /* protected void onDrawArrays() {
        *//*GLES20.glUniform1i(this.toneLocation, this.toneLevel);
        GLES20.glUniform1f(this.beautyLocation, this.beautyLevel);
        GLES20.glUniform1f(this.brightLocation, this.brightLevel);*//*
        setInteger(this.d, this.g);
        setFloat(this.b, this.e);
        setFloat(this.c, this.f);
    }*/

    @Override
    protected void onDrawArraysPre(){
       GLES20.glUniform1i(this.d, this.g);
        GLES20.glUniform1f(this.b, this.e);
        GLES20.glUniform1f(this.c, this.f);
    }


}
