package br.com.alura.agenda.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by caique on 13/11/16.
 */

public class ImageUtil {

    public static byte[] convertInByte(String caminhoImagem) {
        try {
            FileInputStream is = new FileInputStream(new File(caminhoImagem));
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayOutputStream bos = new ByteArrayOutputStream(500);
            int current = 0;
            while ((current = bis.read()) != -1) {
                bos.write((byte) current);
            }

            return bos.toByteArray();
        } catch (IOException e) {
            Log.d("convertInByte", "Error: " + e.toString());
        }
        return null;
    }

    public static Bitmap convertInBitmap(byte[] bytesFotos) {
        if (bytesFotos != null) {
            ByteArrayInputStream fotoStream = new ByteArrayInputStream(bytesFotos);
            Bitmap imagemFoto = BitmapFactory.decodeStream(fotoStream);
            return imagemFoto;
        }
        return null;
    }
}
