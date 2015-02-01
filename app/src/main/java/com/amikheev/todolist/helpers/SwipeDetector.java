package com.amikheev.todolist.helpers;

import android.view.MotionEvent;
import android.view.View;

public class SwipeDetector implements View.OnTouchListener {
    public enum Action {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        TOP_TO_BOTTOM,
        BOTTOM_TO_TOP,
        None
    }

    private float downX, downY, upX, upY;
    private Action swipeDetected = Action.None;

    public int MIN_DISTANCE = 100;

    public boolean isSwipeDetected() {
        return this.swipeDetected != Action.None;
    }

    public Action getAction() {
        return this.swipeDetected;
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.downX = event.getX();
                this.downY = event.getY();

                this.swipeDetected = Action.None;

                return false;

            case MotionEvent.ACTION_MOVE:
                this.upX = event.getX();
                this.upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                if (Math.abs(deltaX) > this.MIN_DISTANCE) {
                    this.swipeDetected = (deltaX > 0 ? Action.RIGHT_TO_LEFT : Action.LEFT_TO_RIGHT);

                    return true;
                } else if (Math.abs(deltaY) > this.MIN_DISTANCE) {
                    this.swipeDetected = (deltaY > 0 ? Action.BOTTOM_TO_TOP : Action.TOP_TO_BOTTOM);

                    return true;
                }
        }

        return false;
    }
}
