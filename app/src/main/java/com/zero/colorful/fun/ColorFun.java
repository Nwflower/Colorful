package com.zero.colorful.fun;

import java.util.Random;
import static java.lang.Math.pow;


/**
 * 酷安零寒开发
 * 多彩内部类
 * ColorFun
 ***/
public class ColorFun {
    public static String getRandomColor(){
        //返回一个大写的HEX字符串
        Random random=new Random(  );
        return RGBtoHEX( random.nextInt(256),random.nextInt(256),random.nextInt(256) );
    }
    public static boolean colorLight(int colorR, int colorG, int colorB){
        return colorR + colorG + colorB >= 384;
    }
    public static boolean colorLight(String HEX){
        int [] RGB=HEXtoRGB( HEX );
        return RGB[0] + RGB[1] + RGB[2] >= 384;
    }
    public static String RGBtoHEX(int colorR, int colorG, int colorB){
        //输入RGB,返回大写HEX字符串,RGB不合法返回null
        if (colorR>255||colorG>255||colorB>255||colorR<0||colorG<0||colorB<0){
            return null;
        }
        String TemporaryR = Integer.toHexString(colorR).toUpperCase();
        String TemporaryG = Integer.toHexString(colorG).toUpperCase();
        String TemporaryB = Integer.toHexString(colorB).toUpperCase();
        TemporaryR = TemporaryR.length()==1 ? "0" + TemporaryR : TemporaryR ;
        TemporaryG = TemporaryG.length()==1 ? "0" + TemporaryG : TemporaryG ;
        TemporaryB = TemporaryB.length()==1 ? "0" + TemporaryB : TemporaryB ;
        return "#"+TemporaryR+TemporaryG+TemporaryB;
    }
    public static int[] HEXtoRGB(String HEX){
        int colorR = Integer.parseInt(HEX.substring( 1,3) ,16);
        int colorG = Integer.parseInt(HEX.substring( 3,5) ,16);
        int colorB = Integer.parseInt(HEX.substring( 5,7) ,16);
        return new int[]{colorR,colorG,colorB};
    }
    public static int[] RGBtoHSV(int colorR, int colorG, int colorB){
        double 	maxRGB	=	Math.max( colorR , Math.max( colorG,colorB ) );
        double	minRGB	=	Math.min( colorR , Math.min( colorG,colorB ) );
        if( maxRGB == minRGB ) return new int[]{ 0, 0, (int)maxRGB / 255};
        double	temp	=	maxRGB - minRGB;
        double HSVh = 0,HSVs,HSVv;
        HSVv = maxRGB *100/ 255;
        HSVs = temp *100/ maxRGB;
        if( colorR == maxRGB ) HSVh = (colorG - colorB)/temp;
        else if( colorG == maxRGB ) HSVh = (colorB - colorR)/temp + 2;
        else if( colorB == maxRGB ) HSVh = (colorR - colorG)/temp + 4;
        if (HSVh < 0) HSVh +=6;
        HSVh *=	60;
        return new int[]{ (int)HSVh, (int)HSVs, (int)HSVv};
    }

    public static int[] RGBtoCMYK(int colorR, int colorG, int colorB){
        double c,m,y,k;
        c = (double)(255 - colorR) / 255;
        m = (double)(255 - colorG) / 255;
        y = (double)(255 - colorB) / 255;

        k=Math.min(c,Math.min(m,y));
        if(1.0 == k)
            c = m = y = 0;
        else{
            c = (c - k) / (1 - k);
            m = (m - k) / (1 - k);
            y = (y - k) / (1 - k);
        }
        int C= (int) (100*c);
        int M= (int) (100*m);
        int Y= (int) (100*y);
        int K= (int) (100*k);
        return new int[]{C,M,Y,K};
    }
    public static int[] RGBtoHSL(int colorR, int colorG, int colorB){
        double 	maxRGB	=	Math.max( colorR , Math.max( colorG,colorB ) );
        double	minRGB	=	Math.min( colorR , Math.min( colorG,colorB ) );
        double	sumTemp	=	maxRGB + minRGB;
        double	subTemp	=	maxRGB - minRGB;
        double HSVh = 0,HSVs,HSVl;
        HSVl = sumTemp*100/510;
        if( maxRGB == minRGB || (int)sumTemp==0){
            HSVh = 0;
            HSVs = 0;
            return new int[]{ (int)HSVh, (int)HSVs, (int)HSVl};
        }
        if (HSVl<=50) HSVs=(subTemp/sumTemp)*100;
        else HSVs=(subTemp/(510-sumTemp))*100;
        double	RGBr	=	( maxRGB - colorR )	/ subTemp;
        double	RGBg	=	( maxRGB - colorG ) / subTemp;
        double	RGBb	=	( maxRGB - colorB ) / subTemp;
        if( colorR == maxRGB ){
            if( colorG == minRGB )
                HSVh = 5 + RGBb;
            else
                HSVh = 1 - RGBg;
        }
        else if( colorG == maxRGB ){
            if( colorB == minRGB )
                HSVh = 1 + RGBr;
            else
                HSVh = 3 - RGBb;
        }
        else if( colorB == maxRGB ){
            if( RGBr == minRGB )
                HSVh = 3 + RGBg;
            else
                HSVh = 5 - RGBr;
        }
        HSVh *=	60;
        return new int[]{ (int)HSVh, (int)HSVs, (int)HSVl};
    }
    public static int[] RGBtoLab(int colorR, int colorG, int colorB){
        //转为XYZ颜色
        double R=colorR/255.0;
        double G=colorG/255.0;
        double B=colorB/255.0;
        double X=0.412453*R+0.357580*G+0.180423*B;
        double Y=0.212671*R+0.715160*G+0.072169*B;
        double Z=0.019334*R+0.119193*G+0.950227*B;
        X/=0.95047;
        Y/=1.0;
        Z/=1.08883;
        double FX = X > 0.008856f ? pow(X,1.0f/3.0f) : (7.787f * X +0.137931f);
        double FY = Y > 0.008856f ? pow(Y,1.0f/3.0f) : (7.787f * Y +0.137931f);
        double FZ = Z > 0.008856f ? pow(Z,1.0f/3.0f) : (7.787f * Z +0.137931f);
        int L = (int) (Y > 0.008856f ? (116.0f * FY - 16.0f) : (903.3f * Y));
        int a = (int) (500.f * (FX - FY));
        int b = (int) (200.f * (FY - FZ));
        return new int[]{L,a,b};
    }

    public static int[] HSVtoRGB(int colorH, int colorS, int colorV)
    {
        float RGB_min, RGB_max;
        RGB_max = colorV*2.55f;
        RGB_min = RGB_max*(100 - colorS) / 100.0f;
        float R,G,B;
        float RGB_Adj = (RGB_max - RGB_min)*(colorH % 60) / 60.0f;
        switch (colorH/60) {
            case 0:
            default:
                R = RGB_max;
                G = RGB_min + RGB_Adj;
                B = RGB_min;
                break;
            case 1:
                R = RGB_max - RGB_Adj;
                G = RGB_max;
                B = RGB_min;
                break;
            case 2:
                R = RGB_min;
                G = RGB_max;
                B = RGB_min + RGB_Adj;
                break;
            case 3:
                R = RGB_min;
                G = RGB_max - RGB_Adj;
                B = RGB_max;
                break;
            case 4:
                R = RGB_min + RGB_Adj;
                G = RGB_min;
                B = RGB_max;
                break;
            case 5:
                R = RGB_max;
                G = RGB_min;
                B = RGB_max - RGB_Adj;
                break;
        }
        return new int[] {(int) R, (int) G, (int) B};
    }

}
