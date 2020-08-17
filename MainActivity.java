package tech.anomie.qrdisplay;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import android.content.ClipData;
import android.content.ClipboardManager;

import android.graphics.Point;
import android.view.Display;
import android.widget.ImageView;
import android.view.WindowManager;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


public class MainActivity extends AppCompatActivity {

    private ClipboardManager myClipboard;
    private ClipData myClip;

    ImageView imageView;
    Bitmap bitmap ;
    public final static int QRcodeWidth = 1080 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        String text = "EMPTY";
        if ( myClipboard.hasPrimaryClip()) {
            ClipData abc = myClipboard.getPrimaryClip();
            if (abc.getItemCount() > 0) {
                ClipData.Item item = abc.getItemAt(0);
                if (!item.getText().toString().isEmpty()) {
                    text = item.getText().toString();
                }
            }
        }
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(text);

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3/4;

        imageView = (ImageView)findViewById(R.id.imageView);
        try {
            bitmap = TextToImageEncode(text);
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        Log.d("TEST", String.valueOf(smallerDimension));
    }
    @Override
    public void onPause() {
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        String text = "EMPTY";
        if ( myClipboard.hasPrimaryClip()) {
            ClipData abc = myClipboard.getPrimaryClip();
            if (abc.getItemCount() > 0) {
                ClipData.Item item = abc.getItemAt(0);
                if (!item.getText().toString().isEmpty()) {
                    text = item.getText().toString();
                }
            }
        }
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(text);
        imageView = (ImageView)findViewById(R.id.imageView);
        try {
            bitmap = TextToImageEncode(text);
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        super.onPause();
        Log.d("TEST", "Paused..");
    }

    @Override
    public void onStart() {
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        String text = "EMPTY";
        if ( myClipboard.hasPrimaryClip()) {
            ClipData abc = myClipboard.getPrimaryClip();
            if (abc.getItemCount() > 0) {
                ClipData.Item item = abc.getItemAt(0);
                if (!item.getText().toString().isEmpty()) {
                    text = item.getText().toString();
                }
            }
        }

        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(text);
        imageView = (ImageView)findViewById(R.id.imageView);
        try {
            bitmap = TextToImageEncode(text);
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        super.onStart();
        Log.d("TEST", "Started..");
    }

    @Override
    public void onResume() {
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        String text = "EMPTY";
        if ( myClipboard.hasPrimaryClip()) {
            ClipData abc = myClipboard.getPrimaryClip();
            if (abc.getItemCount() > 0) {
                ClipData.Item item = abc.getItemAt(0);
                if (!item.getText().toString().isEmpty()) {
                    text = item.getText().toString();
                }
            }
        }
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(text);
        imageView = (ImageView)findViewById(R.id.imageView);
        try {
            bitmap = TextToImageEncode(text);
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        super.onResume();
        Log.d("TEST", "Resumed..");
    }

    @Override
    public void onStop() {
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Stoped..");
        super.onStop();
        Log.d("TEST", "Stoped..");
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {

        int colorWhite = ContextCompat.getColor(this, android.R.color.white);
        int colorBlack = ContextCompat.getColor(this, android.R.color.black);


        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        colorBlack:colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 1080, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}
