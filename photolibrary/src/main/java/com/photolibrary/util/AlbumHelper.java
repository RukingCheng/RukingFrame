package com.photolibrary.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;

import com.photolibrary.PictureSelectionCache;
import com.photolibrary.bean.ImageAttr;
import com.photolibrary.bean.ImageBucket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class AlbumHelper {
    private Context context;
    private ContentResolver cr;

    private HashMap<String, String> thumbnailList = new HashMap<>();

    private HashMap<String, ImageBucket> bucketList = new HashMap<>();

//    private static AlbumHelper instance;

    public AlbumHelper(Context context) {
        if (this.context == null) {
            this.context = context;
            cr = context.getContentResolver();
        }
    }

//    public static AlbumHelper getHelper(@NonNull Context context) {
//        if (instance == null) {
//            instance = new AlbumHelper();
//        }
//        instance.init(context);
//        return instance;
//    }
//
//    public void init(Context context) {
//        if (this.context == null) {
//            this.context = context;
//            cr = context.getContentResolver();
//        }
//    }

    private void getThumbnail() {
        String[] projection = {Thumbnails._ID, Thumbnails.IMAGE_ID, Thumbnails.DATA};
        Cursor cursor = cr.query(Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
        getThumbnailColumnData(cursor);
    }

    private void getThumbnailColumnData(Cursor cur) {
        if (cur.moveToFirst()) {
            int image_id;
            String image_path;
            int _idColumn = cur.getColumnIndex(Thumbnails._ID);
            int image_idColumn = cur.getColumnIndex(Thumbnails.IMAGE_ID);
            int dataColumn = cur.getColumnIndex(Thumbnails.DATA);

            do {
                cur.getInt(_idColumn);
                image_id = cur.getInt(image_idColumn);
                image_path = cur.getString(dataColumn);
                thumbnailList.put("" + image_id, image_path);
            } while (cur.moveToNext());
        }
    }


    private boolean hasBuildImagesBucketList = false;

    private void buildImagesBucketList() {
        getThumbnail();
        Cursor cur1 = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.TITLE, MediaStore.Audio.Media.SIZE,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME},
                null, null, null);
        setData(cur1, 0);
        if (PictureSelectionCache.isVideo) {
            Cursor cur = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.BUCKET_ID,
                            MediaStore.Video.Media.DATA,
                            MediaStore.Video.Media.DISPLAY_NAME,
                            MediaStore.Video.Media.TITLE, MediaStore.Audio.Media.SIZE,
                            MediaStore.Video.Media.BUCKET_DISPLAY_NAME},
                    null, null, null);
            setData(cur, 1);
        }
        hasBuildImagesBucketList = true;
    }

    private void setData(Cursor cur, int type) {
        if (cur != null && cur.moveToFirst()) {
            int photoIDIndex = cur.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int photoPathIndex = cur.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            int bucketDisplayNameIndex = cur.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
            int bucketIdIndex = cur.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID);
            do {
                String _id = cur.getString(photoIDIndex);
                String path = cur.getString(photoPathIndex);
                String bucketName = cur.getString(bucketDisplayNameIndex);
                String bucketId = cur.getString(bucketIdIndex);
                ImageBucket bucket = bucketList.get(bucketId);
                if (bucket == null) {
                    bucket = new ImageBucket();
                    bucketList.put(bucketId, bucket);
                    bucket.imageList = new ArrayList<>();
                    bucket.bucketName = bucketName;
                }
                bucket.count++;
                ImageAttr imageItem = new ImageAttr();
                imageItem.imageId = _id;
                imageItem.url = path;
                imageItem.type = type;
                imageItem.thumbnailUrl = thumbnailList.get(_id);
                bucket.imageList.add(imageItem);

            } while (cur.moveToNext());
        }
    }

    public List<ImageBucket> getImagesBucketList(boolean refresh) {
        if (refresh || !hasBuildImagesBucketList) {
            buildImagesBucketList();
        }
        List<ImageBucket> tmpList = new ArrayList<>();
        for (Entry<String, ImageBucket> entry : bucketList.entrySet()) {
            tmpList.add(entry.getValue());
        }
        return tmpList;
    }


}
