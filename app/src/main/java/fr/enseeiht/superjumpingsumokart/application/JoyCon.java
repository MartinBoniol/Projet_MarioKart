package fr.enseeiht.superjumpingsumokart.application;

import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

public class JoyCon {
    public final static int UP       = 0;
    public final static int LEFT     = 1;
    public final static int RIGHT    = 2;
    public final static int DOWN     = 3;
    public  final static int CENTER   = 4;
    public final static int A      = 5;
    public final static int Arelease      = 15;
    public final static int B      = 6;
    public final static int Brelease      = 16;
    public final static int X      = 7;
    public final static int Xrelease      = 17;
    public final static int Y      = 8;
    public final static int Yrelease      = 18;
    public final static int L      = 9;
    public final static int Llong  = 19;
    public final static int Lrelease      = 29;
    public final static int R      = 10;
    public final static int Rrelease      = 20;

    int cmdPressed = -1; // initialized to -1

    public int getDirectionPressed(InputEvent event) {
        if (!isDpadDevice(event)) {
            return -1;
        }

        // If the input event is a MotionEvent, check its hat axis values.
        if (event instanceof MotionEvent) {

            // Use the hat axis value to find the D-pad direction
            MotionEvent motionEvent = (MotionEvent) event;
            float xaxis = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_X);
            float yaxis = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_Y);

            // Check if the AXIS_HAT_X value is -1 or 1, and set the D-pad
            // LEFT and RIGHT direction accordingly.
            if (Float.compare(xaxis, -1.0f) == 0) {
                cmdPressed =  JoyCon.LEFT;
            } else if (Float.compare(xaxis, 1.0f) == 0) {
                cmdPressed =  JoyCon.RIGHT;
            }
            // Check if the AXIS_HAT_Y value is -1 or 1, and set the D-pad
            // UP and DOWN direction accordingly.
            else if (Float.compare(yaxis, -1.0f) == 0) {
                cmdPressed =  JoyCon.UP;
            } else if (Float.compare(yaxis, 1.0f) == 0) {
                cmdPressed =  JoyCon.DOWN;
            } else {
                cmdPressed = JoyCon.CENTER;
            }
        } else if (event instanceof KeyEvent) {
            // Use the key code to find the D-pad direction.
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_A) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    cmdPressed = JoyCon.A;
                } else if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    cmdPressed = JoyCon.Arelease;
                }
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_C) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    cmdPressed = JoyCon.Y;
                } else if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    cmdPressed = JoyCon.Yrelease;
                }
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_X) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    cmdPressed = JoyCon.X;
                } else if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    cmdPressed = JoyCon.Xrelease;
                }
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_B) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    cmdPressed = JoyCon.B;
                } else if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    cmdPressed = JoyCon.Brelease;
                }
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_Z) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    cmdPressed = JoyCon.R;
                } else if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    cmdPressed = JoyCon.Rrelease;
                }
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_Y) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    long time = keyEvent.getEventTime() - keyEvent.getDownTime();
                    if (time > 200) {
                        cmdPressed = JoyCon.Llong;
                    } else {
                        cmdPressed = JoyCon.L;
                    }
                } else if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    cmdPressed = JoyCon.Lrelease;
                }
            }
        }
        return cmdPressed;
    }

    public static boolean isDpadDevice(InputEvent event) {
        // Check that input comes from a device with directional pads.
        if ((event.getSource() & InputDevice.SOURCE_DPAD)
                != InputDevice.SOURCE_DPAD) {
            return true;
        } else {
            return false;
        }
    }
}
