package kmitl.lab03.taweewong58070045.simplemydot.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class ScreenshotUtils {
    public static Bitmap getScreenshot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static File getMainDirectoryName(Context context) {
        File mainDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "SimpleMyDot");

        if (!mainDir.exists()) {
            if (mainDir.mkdir())
                Toast.makeText(context, "Main Directory Created : " + mainDir, Toast.LENGTH_SHORT).show();
                Log.e("Create Directory", "Main Directory Created : " + mainDir);
        }
        return mainDir;
    }

    public static File store(Bitmap bm, String fileName, File saveFilePath) {
        File dir = new File(saveFilePath.getAbsolutePath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(saveFilePath.getAbsolutePath(), fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
