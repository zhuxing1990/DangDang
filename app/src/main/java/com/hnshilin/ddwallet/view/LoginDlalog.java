package com.hnshilin.ddwallet.view;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnshilin.ddwallet.R;

/**
 * Created by zhuxi on 2017/8/25.
 */
public class LoginDlalog {

    private Context context;
    private Dialog dialog;


    private Display display;
//    private LinearLayout dialog_main_linear;
    private EditText dialog_edit_username;
    private EditText dialog_edit_password;
    private Button dialog_button_login;
    private TextView dialong_textview_register;
    private ImageView dialog_imageview_close;
    public LoginDlalog(Context context){
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }
    public LoginDlalog builder() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_login, null);
//        dialog_main_linear = (LinearLayout) view.findViewById(R.id.dialog_main_linear);
//        dialog_main_linear.setLayoutParams(new FrameLayout.LayoutParams((int) (display
//                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        dialog_edit_username = (EditText) view.findViewById(R.id.dialog_edit_username);
        dialog_edit_password = (EditText) view.findViewById(R.id.dialog_edit_password);
        dialog_button_login = (Button) view.findViewById(R.id.dialong_button_login);
        dialong_textview_register = (TextView) view.findViewById(R.id.dialong_textview_register);
        dialog_imageview_close = (ImageView) view.findViewById(R.id.dialog_imageview_close);
        dialog_imageview_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()){
                    dialog.cancel();
                }
            }
        });
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);


        return this;
    }
    public Editable getUserName(){
        return  dialog_edit_username.getText();
    }
    public Editable getPassWord(){
        return  dialog_edit_password.getText();
    }
    public LoginDlalog UserNameAddTextWatcher(TextWatcher textWatcher){
        dialog_edit_username.addTextChangedListener(textWatcher);
        return this;
    }
    public LoginDlalog setUserName(String userName){
        if (!TextUtils.isEmpty(userName)){
            dialog_edit_username.setText(""+userName);
        }
        return this;
    }
    public LoginDlalog PassWordAddTextWatcher(TextWatcher textWatcher){
        dialog_edit_password.addTextChangedListener(textWatcher);
        return this;
    }
    public LoginDlalog setLoginOnClickListener(final View.OnClickListener listener) {
        dialog_button_login.setOnClickListener(listener);
        return this;
    }
    public LoginDlalog setRegisterOnClickListener( final View.OnClickListener listener) {
        dialong_textview_register.setOnClickListener(listener);
        return this;
    }
    public LoginDlalog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }
    public LoginDlalog setCanceledOnTouchOutside(boolean b){
        dialog.setCanceledOnTouchOutside(b);
        return this;
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }
    public void show() {
        dialog.show();
    }
    public void cancel(){
        if (dialog!=null)
            dialog.dismiss();
    }
}
