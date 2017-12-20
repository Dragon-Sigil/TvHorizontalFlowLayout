package com.module.utils;

import android.content.Context;

/**
 * Created by liuqiwu on 2017/7/27.
 * 根据名称获取资源id
 */

public class ResourceUtil {
    private Context mContext;
    private static ResourceUtil instance;

    private ResourceUtil(Context mContext) {
        this.mContext = mContext;
    }

    public static ResourceUtil getInstance(Context context) {
        if (instance == null) {
            instance = new ResourceUtil(context);
        }
        return instance;
    }

    public int getId(String resourceName) {
        return getIdentifierByType(resourceName, "id");
    }

    public int getLayoutId(String resourceName) {
        return getIdentifierByType(resourceName, "layout");
    }

    public int getStringId(String resourceName) {
        return getIdentifierByType(resourceName, "string");
    }

    public int getDrawableId(String resourceName) {
        return getIdentifierByType(resourceName, "drawable");
    }

    public int getMipmapId(String resourceName) {
        return getIdentifierByType(resourceName, "mipmap");
    }

    public int getColorId(String resourceName) {
        return getIdentifierByType(resourceName, "color");
    }

    public int getDimenId(String resourceName) {
        return getIdentifierByType(resourceName, "dimen");
    }

    public int getAttrId(String resourceName) {
        return getIdentifierByType(resourceName, "attr");
    }

    public int getStyleId(String resourceName) {
        return getIdentifierByType(resourceName, "style");
    }

    public int getAnimId(String resourceName) {
        return getIdentifierByType(resourceName, "anim");
    }

    public int getArrayId(String resourceName) {
        return getIdentifierByType(resourceName, "array");
    }

    public int getIntegerId(String resourceName) {
        return getIdentifierByType(resourceName, "integer");
    }

    public int getBoolId(String resourceName) {
        return getIdentifierByType(resourceName, "bool");
    }

    private int getIdentifierByType(String resourceName, String defType) {
        return mContext.getResources().getIdentifier(resourceName, defType, mContext.getPackageName());
    }
}
