package com.example.bluetoothtest.utility;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.room.util.FileUtil;

import com.example.bluetoothtest.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.concurrent.Executors;

public class ProfileHelper {
    private Bitmap bitmap;
    private Context context;
    private ContentResolver contentResolver;

    private final String MAIN_PROFILE_NAME = "profiles";
    private final String IMAGE_CACHE_NAME = "image-cache";
    private final String UTILITY_FILE_NAME = "utility";
    private final String DEFAULT_IMAGE_NAME = "user_profile_default";


    public String defaultProfilePath = "";

    public ProfileHelper(Context context, ContentResolver contentResolver) {
        this.context = context;
        this.contentResolver = contentResolver;


    }


    public Bitmap rotate(Bitmap bitmap, int angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public Bitmap createBitmapFromUri(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(contentResolver, uri);
        } catch (IOException e) {
            return null;
        }
    }

    public static void getImage(String path, ImageView imageView) {

        if (path != null) {
            //Calling fit method will prevent some issues when there is lot of pictures loading
            //also it prevents extra memory usage
            Picasso.get().load(new File(path)).placeholder(R.drawable.ic_baseline_person_24).fit().noFade().into(imageView);
            return;
        }
        //Loading default image if the path is null
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_default_user));

    }

    public String uploadProfile(String name, String directoryName, Bitmap bitmapParameter) {


        if (bitmapParameter != null) {

            File mainStorage = context.getFilesDir();
            File profileDirectory = new File(mainStorage, MAIN_PROFILE_NAME);
            File parentCategory = new File(profileDirectory, directoryName);


            if (!parentCategory.exists())
                parentCategory.mkdirs();


            File imageFile = new File(parentCategory, name);


            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(imageFile);
                bitmapParameter.compress(Bitmap.CompressFormat.WEBP, 20, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bitmapParameter = null;
            return imageFile.getAbsolutePath();

        }
        return null;

    }

    public void moveImages(String directoryName, String newDirectoryName) {
        File mainStorage = context.getFilesDir();
        File profileDirectory = new File(mainStorage, MAIN_PROFILE_NAME);
        File parentCategory = new File(profileDirectory, directoryName);

        if (!parentCategory.exists())
            parentCategory.mkdirs();

        File[] files = parentCategory.listFiles();


        File newCategory = new File(profileDirectory, newDirectoryName);

        if (!newCategory.exists())
            newCategory.mkdirs();

        if (files != null && files.length > 0) {

            FileChannel source = null;
            FileChannel destination = null;
            try {
                for (File file : files) {
                    source = new FileInputStream(file).getChannel();
                    destination = new FileOutputStream(new File(newCategory, file.getName())).getChannel();
                    destination.transferFrom(source, 0, source.size());
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (source != null) {
                    try {
                        source.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (destination != null) {
                    try {
                        destination.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            parentCategory.delete();
        }


    }

    public void renameDirectory(String name, String oldName) {
        File mainStorage = context.getFilesDir();
        File profileDirectory = new File(mainStorage, MAIN_PROFILE_NAME);
        File parentCategory = new File(profileDirectory, oldName);
        parentCategory.renameTo(new File(profileDirectory, name));
    }

    public String uploadAsCache(Bitmap bitmap, String name) {
        if (bitmap == null) return null;

        File mainStorage = context.getFilesDir();

        File profilesDirectory = new File(mainStorage, MAIN_PROFILE_NAME);

        File imageCache = new File(profilesDirectory, IMAGE_CACHE_NAME);

        if (!imageCache.exists()) imageCache.mkdirs();

        File cameraCache = new File(imageCache, name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(cameraCache);
            bitmap.compress(Bitmap.CompressFormat.WEBP, 20, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cameraCache.getAbsolutePath();
    }

    public static void getDefaultImage(ImageView imageView) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_default_user));
    }

    public static void delete(String path) {
        if (path == null) return;

        File imageFile = new File(path);
        if (imageFile.exists()) {
            imageFile.delete();
            path = null;
        }
    }

    public static void startCropImage(Activity activity) {
        CropImage
                .activity().setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(activity);
    }

    public static void startCropImage(Fragment fragment, Context context) {
        // If we're on the fragment we should call 'getContext' instead of 'getActivity'
        // because if we call getContext , the onActivityResult of current class will be called
        // otherwise the MainActivity will be called and our algorithm won't work
        CropImage
                .activity().setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(context, fragment);
    }
}
