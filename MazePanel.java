package falstad;

//import java.awt.*;


import android.graphics.*;

import android.util.DisplayMetrics;
import android.view.*;
import android.graphics.drawable.Drawable;
import android.content.Context;
import android.util.AttributeSet;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 *
 * @author pk
 *
 */
public class MazePanel extends View {
    /* Panel operates a double buffer see
     * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
     * for details
     */
    //private MazeController controller;
    //private static RenderingHints renderinghints;
    //private static Event event;
    private Paint paint= new Paint();
    private Bitmap bitmap ;
    private Canvas gc;

    private Bitmap bmp;

    /**
     * Constructor. Object is not focusable.
     */
    public MazePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK);

        bitmap =  Bitmap.createBitmap(Constants.VIEW_WIDTH,Constants.VIEW_HEIGHT,Bitmap.Config.ARGB_8888);

        gc = new Canvas(bitmap);

    }


    public Canvas getGraphics(){
        return gc;
    };
    public Canvas getGraphics(Bitmap bf){
        return gc;
    }


    public void fillOval(int x, int y, int width, int height){
        RectF oval = new RectF(x, y, x+width, y+height);
        gc.drawOval(oval, paint);
    }

        @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 70, 350, paint);

    }

    public void update(Canvas g) {
        paint(g) ;
    }

    public void update() {
        invalidate() ;
    }

    public void paint(Canvas g) {
        if (null == g) {
            System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation") ;
        }
        else {

            g.drawBitmap(bitmap, 0, 0, null) ;
        }
    }

    public void initBufferImage() {
        //bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
        bitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        if (null == bitmap)
        {
            System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
        }

    }


    public Canvas getBufferGraphics() {
        if (null == bitmap)
            initBufferImage() ;
        if (null == bitmap)
            return null ;
        return getGraphics(bitmap) ;
    }



    public void passColor(int color){
        setColor(new Paint(color));
    }

    public void assignColor(int part2, int rgbValue) {
        Paint p = new Paint();
        switch (part2) {

            case 0: p.setARGB(255,rgbValue, 20, 20); break;
            case 1: p.setARGB(255,20, rgbValue, 20); break;
            case 2: p.setARGB(255,20, 20, rgbValue); break;
            case 3: p.setARGB(255,rgbValue, rgbValue, 20); break;
            case 4: p.setARGB(255,20, rgbValue, rgbValue); break;
            case 5: p.setARGB(255,rgbValue, 20, rgbValue); break;
            default: p.setARGB(255,20, 20, 20); break;
        }
        setColor(p);
    }

    public Paint getColor(){
        return paint;
    }
    public void setColor(Paint paint1) {



        paint.setColor(paint1.getColor());
    }
    public void fillPolygon(int[] xps, int[] yps, int i) {
        Path path = new Path();
        path.reset();
        path.moveTo(xps[0], yps[0]);
        path.lineTo(xps[1], yps[1]);
        path.lineTo(xps[2], yps[2]);
        path.lineTo(xps[3], yps[3]);
        path.lineTo(xps[0], yps[0]);

        gc.drawPath(path, paint);
    }



    public static class panelcolor extends Color {

        private int r, g, b, a = 255;
        private int RGB = -1;
        public panelcolor(int i, int val1, int val12) {
            super();
            r = i;
            g = val1;
            b = val12;
        }
        public int getR() { return r; }
        public int getRGB() {
            if (RGB != -1) return RGB;
            return ((a & 0xFF) << 24) |
                    ((r & 0xFF) << 16) |
                    ((g & 0xFF) <<  8) |
                    ((b & 0xFF) <<  0);
        }
    }



}
