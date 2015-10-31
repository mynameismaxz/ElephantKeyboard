package com.mymacz.elephantkeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

/**
 * Created by mynameismaxz on 10/30/2015 AD.
 */
public class ElephantKeyboard extends InputMethodService
    implements KeyboardView.OnKeyboardActionListener{

    static final boolean PROCESS_HARD_KEYS = true;

    private StringBuilder mComposing = new StringBuilder();
    private boolean mPredictionOn;
    private boolean mCompletionOn;
    private int mLastDisplayWidth;
    private boolean mCapsLock;
    private long mLastShiftTime;
    private long mMetaState;

    static final int KEYCODE_CHANGE_LANGUAGETH = -99;
    static final int KEYCODE_CHANGE_LANGUAGEEN = -98;
    static final int KEYCODE_SYMS = -3;
    static final int KEYCODE_THAI_CAP = -2;
    static final int KEYCODE_EN_CAP = -1;

    // initial keyboard view
    private KeyboardView kv;
    private Keyboard keyboardEN;
    private Keyboard keyboardTH;
    private Keyboard keyboardSyms;
    private Keyboard keyboardTH_CAP;
    private Keyboard keyboardEN_CAP;


    @Override
    public View onCreateInputView() {
        // TODO: When callback activity to using keyboard try to edit this method.
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard,null);
        keyboardEN = new Keyboard(this,R.xml.qwerty);
        keyboardTH = new Keyboard(this,R.xml.qwertyth);
        keyboardSyms = new Keyboard(this,R.xml.syms);
        keyboardTH_CAP = new Keyboard(this,R.xml.qwertyth_shift);
        keyboardEN_CAP = new Keyboard(this,R.xml.qwerty_shift);
        kv.setKeyboard(keyboardEN);
        kv.setOnKeyboardActionListener(this);
        kv.invalidateAllKeys();
        return kv;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);
        switch (primaryCode) {
            case KEYCODE_CHANGE_LANGUAGETH:
                kv.setKeyboard(keyboardTH);
                kv.setOnKeyboardActionListener(this);
                kv.invalidateAllKeys();
                break;
            case KEYCODE_SYMS:
                kv.setKeyboard(keyboardSyms);
                kv.setOnKeyboardActionListener(this);
                kv.invalidateAllKeys();
                break;
            case KEYCODE_CHANGE_LANGUAGEEN:
                kv.setKeyboard(keyboardEN);
                kv.setOnKeyboardActionListener(this);
                kv.invalidateAllKeys();
                break;
            case KEYCODE_THAI_CAP:
                kv.setKeyboard(keyboardTH_CAP);
                kv.setOnKeyboardActionListener(this);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;
            case KEYCODE_EN_CAP:
                kv.setKeyboard(keyboardEN_CAP);
                kv.setOnKeyboardActionListener(this);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE :
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                break;
            default :
                char code = (char) primaryCode;
                ic.commitText(String.valueOf(code),1);
        }
    }

    private void playClick(int primaryCode) {
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(primaryCode){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
