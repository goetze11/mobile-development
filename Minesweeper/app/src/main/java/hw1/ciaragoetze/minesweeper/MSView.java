package hw1.ciaragoetze.minesweeper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by ciaragoetze on 9/27/17.
 */

public class MSView extends View {

    private Paint paintBackgroundDark;
    private Paint paintBackgroundLight;
    private Paint paintLine;
    private Paint paintText;

    private Bitmap bitmapFlag;
    private Bitmap bitmapBomb;

    private boolean componentIsFlag;

    public static final String GAME_WON = "1";
    public static final String GAME_LOST_BY_FLAG = "2";
    public static final String GAME_LOST_BY_MINE = "3";

    public MSView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintBackgroundDark = new Paint();
        paintBackgroundDark.setColor(Color.GRAY);
        paintBackgroundDark.setStyle(Paint.Style.FILL);

        paintBackgroundLight = new Paint();
        paintBackgroundLight.setColor(Color.LTGRAY);
        paintBackgroundLight.setStyle(Paint.Style.FILL);

        paintText = new Paint();
        paintText.setColor(Color.BLUE);
        paintText.setTextSize(70);

        paintLine = new Paint();
        paintLine.setColor(Color.BLACK);
        paintLine.setStrokeWidth(5);


        bitmapFlag = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_action_flag);
        bitmapBomb = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_action_emo_evil);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getGameArea(canvas);
        drawComponents(canvas);
    }

    private void drawComponents(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int centerX = (i * getWidth() / 5 + getWidth() / 10) - 35;
                int centerY = (j * getHeight() / 5 + getHeight() / 10) - 35;

                if (MSModel.getInstance().getFieldContent(i, j).getIsMine() &&
                        MSModel.getInstance().getFieldContent(i, j).getHasBeenClicked()) {
                    canvas.drawBitmap(bitmapBomb, centerX, centerY, null);

                } else if (MSModel.getInstance().getFieldContent(i, j).getIsFlag()) {
                    canvas.drawBitmap(bitmapFlag, centerX, centerY, null);
                } else if (MSModel.getInstance().getFieldContent(i, j).getHasBeenClicked()) {
                    canvas.drawRect((i * getWidth() / 5) + 10, (j * getHeight() / 5) + 10,
                            ((i + 1) * getWidth() / 5) - 10,
                            ((j + 1) * getHeight() / 5) - 10,
                            paintBackgroundLight);
                    String numMines = Integer.toString
                            (MSModel.getInstance().getFieldContent(i, j).getNumMines());
                    canvas.drawText(numMines, centerX + 15, centerY + 50, paintText);
                }
            }

        }
    }

    private void getGameArea(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBackgroundDark);

        for (int i = 1; i < 6; i++) {
            //vertical lines
            canvas.drawLine(i * (getWidth() / 5), 0, i * (getWidth() / 5),
                    getHeight(), paintLine);

            // horizontal lines
            canvas.drawLine(0, i * (getHeight() / 5), getWidth(),
                    i * (getHeight() / 5), paintLine);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int tx = ((int) event.getX()) / (getWidth() / 5);
            int ty = ((int) event.getY()) / (getHeight() / 5);
            int numClicked = 0, numFlags = 0;

            if (componentIsFlag && !MSModel.getInstance().getFieldContent(tx, ty).getHasBeenClicked()) {
                MSModel.getInstance().setFlagContent(tx, ty, componentIsFlag);
                if (!MSModel.getInstance().getFieldContent(tx, ty).getIsMine()) {
                    showBoard(false, GAME_LOST_BY_FLAG);
                }
            } else if (MSModel.getInstance().getFieldContent(tx, ty).getIsMine()) {
                MSModel.getInstance().getFieldContent(tx, ty).setHasBeenClicked(true);
                showBoard(false, GAME_LOST_BY_MINE);
            } else {
                MSModel.getInstance().getFieldContent(tx, ty).setHasBeenClicked(true);
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (MSModel.getInstance().getFieldContent(i, j).getHasBeenClicked()) {
                        numClicked += 1;
                    }
                    if (MSModel.getInstance().getFieldContent(i, j).getIsFlag()) {
                        numFlags += 1;
                    }
                }
            }
            if (numClicked == 22 || numFlags == 3) {
                showBoard(true, GAME_WON);
            }

            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void setComponent(boolean component) {
        componentIsFlag = component;
    }

    public void showBoard(boolean gameWon, String endGameMessage) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                MSModel.getInstance().getFieldContent(i, j).setHasBeenClicked(true);
            }
        }
        invalidate();
        ((MainActivity) getContext()).endGame(gameWon, endGameMessage);
    }

    public void clearBoard() {
        MSModel.getInstance().initializeGame();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }
}
