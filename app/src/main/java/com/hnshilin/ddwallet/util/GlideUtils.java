package com.hnshilin.ddwallet.util;


/*
 *  项目名：  GankClient
 *  包名：    com.liuguilin.gankclient.util
 *  文件名:   GlideUtils
 *  创建者:   LGL
 *  创建时间:  2016/10/20 16:45
 *  描述：    Glide封装
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class GlideUtils {

    /**
     * Glide特点
     * 使用简单
     * 可配置度高，自适应程度高
     * 支持常见图片格式 Jpg png gif webp
     * 支持多种数据源  网络、本地、资源、Assets 等
     * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
     * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
     * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
     * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
     */

//    //默认加载
//    public static void loadImageView(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).into(mImageView);
//    }
//
//    //加载指定大小
//    public static void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
//        Glide.with(mContext).load(path).override(width, height).into(mImageView);
//    }
//
//    //设置加载中以及加载失败图片
//    public static void loadImageViewLoding(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
//        Glide.with(mContext).load(path).placeholder(lodingImage).error(errorImageView).into(mImageView);
//    }
//
//    //设置加载中以及加载失败图片并且指定大小
//    public static void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView, int lodingImage, int errorImageView) {
//        Glide.with(mContext).load(path).override(width, height).placeholder(lodingImage).error(errorImageView).into(mImageView);
//    }
//
//    //设置跳过内存缓存
//    public static void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).skipMemoryCache(true).into(mImageView);
//    }
//
//    //设置下载优先级
//    public static void loadImageViewPriority(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).priority(Priority.NORMAL).into(mImageView);
//    }

    /**
     * 策略解说：
     * <p>
     * all:缓存源资源和转换后的资源
     * <p>
     * none:不作任何磁盘缓存
     * <p>
     * source:缓存源资源
     * <p>
     * result：缓存转换后的资源
     */

    //设置缓存策略
//    public static void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(mImageView);
//    }

    /**
     * api也提供了几个常用的动画：比如crossFade()
     */

//    //设置加载动画
//    public static void loadImageViewAnim(Context mContext, String path, int anim, ImageView mImageView) {
//        Glide.with(mContext).load(path).animate(anim).into(mImageView);
//    }

    /**
     * 会先加载缩略图
     */

    //设置缩略图支持
//    public static void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
//    }

    /**
     * api提供了比如：centerCrop()、fitCenter()等
     */

    //设置动态转换
//    public static void loadImageViewCrop(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).centerCrop().into(mImageView);
//    }

    //设置动态GIF加载方式
//    public static void loadImageViewDynamicGif(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).asGif().into(mImageView);
//    }

    //设置静态GIF加载方式
//    public static void loadImageViewStaticGif(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).asBitmap().into(mImageView);
//    }

    //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘

    //设置监听请求接口
//    public static void loadImageViewListener(Context mContext, String path, ImageView mImageView, RequestListener<String, GlideDrawable> requstlistener) {
//        Glide.with(mContext).load(path).listener(requstlistener).into(mImageView);
//    }

    //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排

    //设置要加载的内容
//    public static void loadImageViewContent(Context mContext, String path, SimpleTarget<GlideDrawable> simpleTarget) {
//        Glide.with(mContext).load(path).centerCrop().into(simpleTarget);
//    }

    //清理磁盘缓存
//    public static void GuideClearDiskCache(Context mContext) {
//        //理磁盘缓存 需要在子线程中执行
////        Glide.get(mContext).clearDiskCache();
//    }

    //清理内存缓存
//    public static void GuideClearMemory(Context mContext) {
//        //清理内存缓存  可以在UI主线程中进行
//        Glide.get(mContext).clearMemory();
//    }


    private static GlideUtils instance;
    public static GlideUtils getInstance(){
        if(instance==null){
            synchronized (GlideUtils.class) {
                if(instance==null){
                    instance=new GlideUtils();
                }
            }
        }
        return instance;
    }

    //  with(Context context). 使用Application上下文，Glide请求将不受Activity/Fragment生命周期控制。
    //  with(Activity activity).使用Activity作为上下文，Glide的请求会受到Activity生命周期控制。
    //  with(FragmentActivity activity).Glide的请求会受到FragmentActivity生命周期控制。
    //  with(android.app.Fragment fragment).Glide的请求会受到Fragment 生命周期控制。
    //  with(android.support.v4.app.Fragment fragment).Glide的请求会受到Fragment生命周期控制。
    //-----------------------------
    //  Glide基本可以load任何可以拿到的媒体资源，如：
    //  load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
    //  load assets资源：load("file:///android_asset/f003.gif")
    //  load raw资源：load("android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
    //  load drawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
    //  load ContentProvider资源：load("content://media/external/images/media/139469")
    //  load http资源：load("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg")
    //  load https资源：load("https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp")
    //  当然，load不限于String类型，还可以：
    //  load(Uri uri)，load(File file)，load(Integer resourceId)，load(URL url)，load(byte[] model, final String id)，load(byte[] model)，load(T model)。
    //  而且可以使用自己的ModelLoader进行资源加载：
    //  using(ModelLoader<A, T> modelLoader, Class<T> dataClass)，using(final StreamModelLoader<T> modelLoader)，using(StreamByteArrayLoader modelLoader)，using(final FileDescriptorModelLoader<T> modelLoader)。
    //  返回RequestBuilder实例
    //--------------------------------------
    //  * thumbnail(float sizeMultiplier). 请求给定系数的缩略图。如果缩略图比全尺寸图先加载完，
    //        就显示缩略图，否则就不显示。系数sizeMultiplier必须在(0,1)之间，可以递归调用该方法。

    //  * sizeMultiplier(float sizeMultiplier). 在加载资源之前给Target大小设置系数。

    //  * skipMemoryCache(boolean skip). 设置是否跳过内存缓存，但不保证一定不被缓存
    //     （比如请求已经在加载资源且没设置跳过内存缓存，这个资源就会被缓存在内存中）。
    //  *  diskCacheStrategy(DiskCacheStrategy strategy).设置缓存策略。
    //     DiskCacheStrategy.SOURCE：缓存原始数据，
    //     DiskCacheStrategy.RESULT：缓存变换修改后的资源数据，
    //     DiskCacheStrategy.NONE：什么都不缓存，
    //     DiskCacheStrategy.ALL：缓存所有图片  默认
    //          默认采用DiskCacheStrategy.RESULT策略，对于download only操作要使用DiskCacheStrategy.SOURCE。

    //  * priority(Priority priority). 指定加载的优先级，优先级越高越优先加载，但不保证所有图片都按序加载。
    //       枚举Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW。默认为Priority.NORMAL。
    //  * crossFade(5000) //设置淡入淡出效果，默认300ms，可以传参
    //  * dontAnimate(). 移除所有的动画。
    //  * animate(int animationId). 在异步加载资源完成时会执行该动画。
    //  * animate(ViewPropertyAnimation.Animator animator). 在异步加载资源完成时会执行该动画。
    //  * placeholder(int resourceId). 设置资源加载过程中的占位Drawable。
    //  * placeholder(Drawable drawable). 设置资源加载过程中的占位Drawable。

    //  * fallback(int resourceId). 设置model为空时要显示的Drawable。如果没设置fallback，
    //    model为空时将显示error的Drawable，如果error的Drawable也没设置，就显示placeholder的Drawable。
    //  * fallback(Drawable drawable).设置model为空时显示的Drawable。
    //  * error(int resourceId).设置load失败时显示的Drawable。
    //  * error(Drawable drawable).设置load失败时显示的Drawable。

    //  * Glide支持两种图片缩放形式，CenterCrop 和 FitCenter
    //    CenterCrop：等比例缩放图片， 直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。
    //    FitCenter：等比例缩放图片，宽或者是高等于ImageView的宽或者是高。

    //  * 当列表在滑动的时候，调用pauseRequests()取消请求，滑动停止时，调用resumeRequests()恢复请求

    //  * listener(RequestListener<? super ModelType, TranscodeType> requestListener).
    //        监听资源加载的请求状态，可以使用两个回调：
    //     onResourceReady(R resource, T model, Target<R> target, boolean isFromMemoryCache, boolean isFirstResource)
    //       和onException(Exception e, T model, Target&lt;R&gt; target, boolean isFirstResource)，
    //       但不要每次请求都使用新的监听器，要避免不必要的内存申请，可以使用单例进行统一的异常监听和处理。
    //  * clear() 清除掉所有的图片加载请求
    //  * override(int width, int height). 重新设置Target的宽高值（单位为pixel）。
    //  * into(Y target).设置资源将被加载到的Target。
    //  * into(ImageView view). 设置资源将被加载到的ImageView。取消该ImageView之前所有的加载并释放资源。
    //  * into(int width, int height). 后台线程加载时要加载资源的宽高值（单位为pixel）。
    //  * preload(int width, int height). 预加载resource到缓存中（单位为pixel）。
    //  * asBitmap(). 无论资源是不是gif动画，都作为Bitmap对待。如果是gif动画会停在第一帧。
    //  * asGif().把资源作为GifDrawable对待。如果资源不是gif动画将会失败，会回调.error()。
    //-------------------------------------------------------------------------------
    /**
     * 加载bitmap，如果是GIF则显示第一帧
     */
    public static String LOAD_BITMAP="GLIDEUTILS_GLIDE_LOAD_BITMAP";
    /**
     * 加载gif动画
     */
    public static String LOAD_GIF="GLIDEUTILS_GLIDE_LOAD_GIF";
    /**
     * 使用Application上下文，Glide请求将不受Activity/Fragment生命周期控制
     * 使用activity 会受到Activity生命周期控制
     * 使用FragmentActivity 会受到FragmentActivity生命周期控制
     * @param context
     * @param path
     * @param imageView
     * @param placeid 占位
     * @param errorid  错误
     * @param bitmapOrgif  加载普通图片 或者GIF图片 ，GIF图片设置bitmap显示第一帧
     */
    public void LoadContextBitmap(Activity context, String path, ImageView imageView, int placeid, int errorid, String bitmapOrgif){
        if (context!=null)
        if(bitmapOrgif==null||bitmapOrgif.equals(LOAD_BITMAP)){
            Glide.with(context).load(path).placeholder(placeid).error(errorid).crossFade().into(imageView);
        }else if(bitmapOrgif.equals(LOAD_GIF)){
            Glide.with(context).load(path).asGif().crossFade().into(imageView);
        }
    }
    /**
     * Glide请求图片，会受到Fragment 生命周期控制。
     * @param fragment
     * @param path
     * @param imageView
     * @param placeid
     * @param errorid
     * @param bitmapOrgif  加载普通图片 或者GIF图片 ，GIF图片设置bitmap显示第一帧
     */
    public void LoadFragmentBitmap(android.app.Fragment fragment,String path,ImageView imageView,int placeid,int errorid,String bitmapOrgif){
        if(bitmapOrgif==null||bitmapOrgif.equals(LOAD_BITMAP)){
            Glide.with(fragment).load(path).placeholder(placeid).error(errorid).crossFade().into(imageView);
        }else if(bitmapOrgif.equals(LOAD_GIF)){
            Glide.with(fragment).load(path).asGif().crossFade().into(imageView);
        }
    }
    /**
     * Glide请求图片，会受到support.v4.app.Fragment生命周期控制。
     * @param fragment
     * @param path
     * @param imageView
     * @param placeid
     * @param errorid
     * @param bitmapOrgif  加载普通图片 或者GIF图片 ，GIF图片设置bitmap显示第一帧
     */
    public void LoadSupportv4FragmentBitmap(android.support.v4.app.Fragment fragment,String path,ImageView imageView,int placeid,int errorid,String bitmapOrgif){
        if(bitmapOrgif==null||bitmapOrgif.equals(LOAD_BITMAP)){
            Glide.with(fragment).load(path).placeholder(placeid).error(errorid).crossFade().into(imageView);
        }else if(bitmapOrgif.equals(LOAD_GIF)){
            Glide.with(fragment).load(path).asGif().crossFade().into(imageView);
        }
    }
    //---------------------圆形图片-------------------
    /**
     * 加载设置圆形图片
     * 使用Application上下文，Glide请求将不受Activity/Fragment生命周期控制
     * <BR/>使用activity 会受到Activity生命周期控制
     * <BR/>使用FragmentActivity 会受到FragmentActivity生命周期控制
     * @param context
     * @param path
     * @param imageView
     */
    @SuppressWarnings("unchecked")
    public void LoadContextCircleBitmap(Context context,String path,ImageView imageView){
        Glide.with(context).load(path).bitmapTransform(new GlideCircleTransform(context)).into(imageView);
    }
    /**
     * Glide请求图片设置圆形，会受到android.app.Fragment生命周期控制
     * @param fragment
     * @param path
     * @param imageView
     */
    @SuppressWarnings("unchecked")
    public void LoadfragmentCircleBitmap(android.app.Fragment fragment,String path,ImageView imageView){
        Glide.with(fragment).load(path).bitmapTransform(new GlideCircleTransform(fragment.getActivity())).into(imageView);
    }
    /**
     * Glide请求图片设置圆形，会受到android.support.v4.app.Fragment生命周期控制
     * @param fragment
     * @param path
     * @param imageView
     */
    @SuppressWarnings("unchecked")
    public void LoadSupportv4FragmentCircleBitmap(android.support.v4.app.Fragment fragment,String path,ImageView imageView){
        Glide.with(fragment).load(path).bitmapTransform(new GlideCircleTransform(fragment.getActivity())).into(imageView);
    }
    //-----------------------圆角图片----------------------
    /**
     * 加载设置圆角图片
     * 使用Application上下文，Glide请求将不受Activity/Fragment生命周期控制
     * <BR/>使用activity 会受到Activity生命周期控制
     * <BR/>使用FragmentActivity 会受到FragmentActivity生命周期控制
     * @param context
     * @param path
     * @param imageView
     * @param roundradius 圆角大小（>0）
     */
    @SuppressWarnings("unchecked")
    public void LoadContextRoundBitmap(Activity context,String path,ImageView imageView,int roundradius){
        if(roundradius<0){
            Glide.with(context).load(path).bitmapTransform(new GlideRoundTransform(context)).into(imageView);
        }else{
            Glide.with(context).load(path).bitmapTransform(new GlideRoundTransform(context,roundradius)).into(imageView);
        }
    }
    /**
     * Glide请求图片设置圆角，会受到android.app.Fragment生命周期控制
     * @param fragment
     * @param path
     * @param imageView
     * @param roundradius
     */
    @SuppressWarnings("unchecked")
    public void LoadfragmentRoundBitmap(android.app.Fragment fragment,String path,ImageView imageView,int roundradius){
        if(roundradius<0){
            Glide.with(fragment).load(path).bitmapTransform(new GlideRoundTransform(fragment.getActivity())).into(imageView);
        }else{
            Glide.with(fragment).load(path).bitmapTransform(new GlideRoundTransform(fragment.getActivity(),roundradius)).into(imageView);
        }
    }
    /**
     * Glide请求图片设置圆角，会受到android.support.v4.app.Fragment生命周期控制
     * @param fragment
     * @param path
     * @param imageView
     * @param roundradius
     */
    @SuppressWarnings("unchecked")
    public void LoadSupportv4FragmentRoundBitmap(android.support.v4.app.Fragment fragment,String path,ImageView imageView,int roundradius){
        if(roundradius<0){
            Glide.with(fragment).load(path).bitmapTransform(new GlideRoundTransform(fragment.getActivity())).into(imageView);
        }else{
            Glide.with(fragment).load(path).bitmapTransform(new GlideRoundTransform(fragment.getActivity(),roundradius)).into(imageView);
        }
    }
    //-------------------------------------------------
    /**
     * Glide 加载模糊图片
     * 使用Application上下文，Glide请求将不受Activity/Fragment生命周期控制
     * <BR/>使用activity 会受到Activity生命周期控制
     * <BR/>使用FragmentActivity 会受到FragmentActivity生命周期控制
     * @param context
     * @param path
     * @param imageView
     */
    @SuppressWarnings("unchecked")
    public void LoadContextBlurBitmap(Activity context,String path,ImageView imageView){
        Glide.with(context).load(path).bitmapTransform(new BlurTransformation(context)).into(imageView);
    }
    /**
     * Glide 加载模糊图片 会受到Fragment生命周期控制
     * @param fragment
     * @param path
     * @param imageView
     */
    @SuppressWarnings("unchecked")
    public void LoadFragmentBlurBitmap(android.app.Fragment fragment,String path,ImageView imageView){
        Glide.with(fragment).load(path).bitmapTransform(new BlurTransformation(fragment.getActivity())).into(imageView);
    }
    /**
     * Glide 加载模糊图片 会受到support.v4.app.Fragment生命周期控制
     * @param fragment
     * @param path
     * @param imageView
     */
    @SuppressWarnings("unchecked")
    public void LoadSupportv4FragmentBlurBitmap(android.support.v4.app.Fragment fragment,String path,ImageView imageView){
        Glide.with(fragment).load(path).bitmapTransform(new BlurTransformation(fragment.getActivity())).into(imageView);
    }
    //---------------------------------------------------------
    /**
     * 旋转图片
     *使用Application上下文，Glide请求将不受Activity/Fragment生命周期控制
     * <BR/>使用activity 会受到Activity生命周期控制
     * <BR/>使用FragmentActivity 会受到FragmentActivity生命周期控制
     * @param context
     * @param path
     * @param imageView
     * @param rotateRotationAngle 旋转角度
     */
    @SuppressWarnings("unchecked")
    public void LoadContextRotateBitmap(Activity context,String path,ImageView imageView,Float rotateRotationAngle){
        Glide.with(context).load(path).bitmapTransform(new RotateTransformation(context, rotateRotationAngle)).into(imageView);
    }
    /**
     * Glide 加载旋转图片 会受到Fragment生命周期控制
     * @param fragment
     * @param path
     * @param imageView
     * @param rotateRotationAngle
     */
    @SuppressWarnings("unchecked")
    public void LoadFragmentRotateBitmap(android.app.Fragment fragment,String path,ImageView imageView,Float rotateRotationAngle){
        Glide.with(fragment).load(path).bitmapTransform(new RotateTransformation(fragment.getActivity(), rotateRotationAngle)).into(imageView);
    }
    /**
     * Glide 加载旋转图片 会受到support.v4.app.Fragment生命周期控制
     * @param fragment
     * @param path
     * @param imageView
     * @param rotateRotationAngle
     */
    @SuppressWarnings("unchecked")
    public void LoadSupportv4FragmentRotateBitmap(android.support.v4.app.Fragment fragment,String path,ImageView imageView,Float rotateRotationAngle){
        Glide.with(fragment).load(path).bitmapTransform(new RotateTransformation(fragment.getActivity(), rotateRotationAngle)).into(imageView);
    }
    //----------------------旋转---------------------------
    /**
     *旋转
     */
    public class RotateTransformation extends BitmapTransformation {

        private float rotateRotationAngle = 0f;

        public RotateTransformation(Context context, float rotateRotationAngle) {
            super( context );

            this.rotateRotationAngle = rotateRotationAngle;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Matrix matrix = new Matrix();

            matrix.postRotate(rotateRotationAngle);

            return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        }

        @Override
        public String getId() {
            return "rotate" + rotateRotationAngle;
        }
    }
    //--------------------------------------------------
    /**
     *图片转圆形
     */
    public  class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private  Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_4444);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_4444);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }
        @Override
        public String getId() {
            return getClass().getName();
        }
    }
    //-----------------------------图片模糊----------------------------------
    /**
     *图片模糊
     */
    public class BlurTransformation extends BitmapTransformation {

        private RenderScript rs;

        public BlurTransformation(Context context) {
            super( context );

            rs = RenderScript.create( context );
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap blurredBitmap = toTransform.copy( Bitmap.Config.ARGB_8888, true );

            // Allocate memory for Renderscript to work with
            Allocation input = Allocation.createFromBitmap(
                    rs,
                    blurredBitmap,
                    Allocation.MipmapControl.MIPMAP_FULL,
                    Allocation.USAGE_SHARED
            );
            Allocation output = Allocation.createTyped(rs, input.getType());

            // Load up an instance of the specific script that we want to use.
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setInput(input);

            // Set the blur radius
            script.setRadius(10);

            // Start the ScriptIntrinisicBlur
            script.forEach(output);

            // Copy the output to the blurred bitmap
            output.copyTo(blurredBitmap);

            toTransform.recycle();

            return blurredBitmap;
        }

        @Override
        public String getId() {
            return "blur";
        }
    }
    //-------------------图片转换圆角图片------------------------------
    /**
     *图片转换圆角图片
     */
    public  class GlideRoundTransform extends BitmapTransformation {

        private  float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        /**
         * 自定义圆角大小
         * @param context
         * @param dp
         */
        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private  Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }
}
