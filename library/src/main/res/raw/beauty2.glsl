#extension GL_OES_EGL_image_external : require

precision lowp float;
precision lowp int;
uniform samplerExternalOES inputImageTexture;
uniform vec2 singleStepOffset;
uniform int iternum;
uniform float aaCoef;
uniform float mixCoef;
varying highp vec2 textureCoordinate;
const float distanceNormalizationFactor = 4.0;
const mat3 saturateMatrix = mat3(1.1102,-0.0598,-0.061,-0.0774,1.0826,-0.1186,-0.0228,-0.0228,1.1772);
void main() {
    vec2 blurCoord1s[14];
    blurCoord1s[0] = textureCoordinate + singleStepOffset * vec2( 0.0, -10.0);
    blurCoord1s[1] = textureCoordinate + singleStepOffset * vec2( 8.0, -5.0);
    blurCoord1s[2] = textureCoordinate + singleStepOffset * vec2( 8.0, 5.0);
    blurCoord1s[3] = textureCoordinate + singleStepOffset * vec2( 0.0, 10.0);
    blurCoord1s[4] = textureCoordinate + singleStepOffset * vec2( -8.0, 5.0);
    blurCoord1s[5] = textureCoordinate + singleStepOffset * vec2( -8.0, -5.0);
    blurCoord1s[6] = textureCoordinate + singleStepOffset * vec2( 0.0, -6.0);
    blurCoord1s[7] = textureCoordinate + singleStepOffset * vec2( -4.0, -4.0);
    blurCoord1s[8] = textureCoordinate + singleStepOffset * vec2( -6.0, 0.0);
    blurCoord1s[9] = textureCoordinate + singleStepOffset * vec2( -4.0, 4.0);
    blurCoord1s[10] = textureCoordinate + singleStepOffset * vec2( 0.0, 6.0);
    blurCoord1s[11] = textureCoordinate + singleStepOffset * vec2( 4.0, 4.0);
    blurCoord1s[12] = textureCoordinate + singleStepOffset * vec2( 6.0, 0.0);
    blurCoord1s[13] = textureCoordinate + singleStepOffset * vec2( 4.0, -4.0);
    vec3 centralColor;
    float central;
    float gaussianWeightTotal;
    float sum;
    float sampleColor;
    float distanceFromCentralColor;
    float gaussianWeight;
    central = texture2D( inputImageTexture, textureCoordinate ).g;
    gaussianWeightTotal = 0.2;
    sum = central * 0.2;
    for (int i = 0; i < 6; i++) {
        sampleColor = texture2D( inputImageTexture, blurCoord1s[i] ).g;
        distanceFromCentralColor = min( abs( central - sampleColor ) * distanceNormalizationFactor, 1.0 );
        gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);
        gaussianWeightTotal += gaussianWeight;
        sum += sampleColor * gaussianWeight;
    }
    for (int i = 6; i < 14; i++) {
        sampleColor = texture2D( inputImageTexture, blurCoord1s[i] ).g;
        distanceFromCentralColor = min( abs( central - sampleColor ) * distanceNormalizationFactor, 1.0 );
        gaussianWeight = 0.1 * (1.0 - distanceFromCentralColor);
        gaussianWeightTotal += gaussianWeight;
        sum += sampleColor * gaussianWeight;
    }
    sum = sum / gaussianWeightTotal;
    centralColor = texture2D( inputImageTexture, textureCoordinate ).rgb;
    sampleColor = centralColor.g - sum + 0.5;
    for (int i = 0; i < iternum; ++i) {
        if (sampleColor <= 0.5) {
            sampleColor = sampleColor * sampleColor * 2.0;
        }else {
            sampleColor = 1.0 - ((1.0 - sampleColor)*(1.0 - sampleColor) * 2.0);
        }
    }
    float aa = 1.0 + pow( centralColor.g, 0.3 )*aaCoef;
    vec3 smoothColor = centralColor*aa - vec3( sampleColor )*(aa - 1.0);
    smoothColor = clamp( smoothColor, vec3( 0.0 ), vec3( 1.0 ) );
    smoothColor = mix( centralColor, smoothColor, pow( centralColor.g, 0.33 ) );
    smoothColor = mix( centralColor, smoothColor, pow( centralColor.g, mixCoef ) );
    gl_FragColor = vec4( pow( smoothColor, vec3( 0.96 ) ), 1.0 );
    vec3 satcolor = gl_FragColor.rgb * saturateMatrix;
    gl_FragColor.rgb = mix( gl_FragColor.rgb, satcolor, 0.23 );
}