package com.scriptsbundle.nokri.Video;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

public class Utills {

//    String[] projection = {MediaStore.Video.Media.DATA};
//    public String getMediaPath(Context context, Uri uri){
//        try{
//            Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);
//            if (cursor!=null){
//                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
//                cursor.moveToFirst();
//                return cursor.getString(columnIndex);
//            }else{
//                return "";
//            }
//        }catch (Exception e){
//            String filePath = (context.getApplicationInfo().dataDir + File.separator
//                    + System.currentTimeMillis());
//
//            File file = new File(filePath);
//
//        }
//
//    }
}
