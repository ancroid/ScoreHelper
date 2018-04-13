package com.newth.scorehelper.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.newth.scorehelper.R;
import com.newth.scorehelper.bean.Inner.User;
import com.newth.scorehelper.bean.UserBeanDB;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * @author Mr.chen
 * @date 2018/3/18
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.edit_username)
    TextInputEditText editUsername;
    @BindView(R.id.edit_stuid)
    TextInputEditText editStuid;
    @BindView(R.id.edit_password)
    TextInputEditText editPassword;
    @BindView(R.id.btu_register)
    Button btuRegister;
    @BindView(R.id.btu_login)
    Button btuLogin;
    @BindView(R.id.tv_not_enter)
    TextView tvNotEnter;

    User user = User.getInstance();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(LoginActivity.this, "学号已注册", LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(LoginActivity.this, "注册成功", LENGTH_SHORT).show();
                    startMainAt();
                    break;
                case 3:
                    //登陆成功
                    Toast.makeText(LoginActivity.this, "登录成功", LENGTH_SHORT).show();
                    startMainAt();
                    break;
                case 4:
                    Toast.makeText(LoginActivity.this, "学号未注册", LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(LoginActivity.this, "登录失败,姓名或者密码输入错误", LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            changeRegistBtuStatus(0);
            changLogintBtuStatus(0);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        btuRegister.setOnClickListener(this);
        btuLogin.setOnClickListener(this);
        tvNotEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btu_register:
                changeRegistBtuStatus(1);
                queryUserIsExit();
                break;
            case R.id.btu_login:
                changLogintBtuStatus(1);
                login();
                break;
            case R.id.tv_not_enter:
                createDialog();
                break;
            default:
                break;
        }
    }

    private void registerUser() {
        UserBeanDB userBeanDB = new UserBeanDB();
        userBeanDB.setUserStuID(getStuID());
        userBeanDB.setUserName(getUserName());
        userBeanDB.setUserPassword(getPassword());
        userBeanDB.setTeacher(false);
        userBeanDB.setLeader(false);
        userBeanDB.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    user.setObjID(s);
                    user.setUserName(getUserName());
                    user.setUserStuID(getStuID());
                    user.setUserPassword(getPassword());
                    user.setLeader(false);
                    user.setTeacher(false);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } else {
                    Log.d("bmob1", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

    private void queryUserIsExit() {
        if (!checkInfoISNull()) {
            BmobQuery<UserBeanDB> query = new BmobQuery<>();
            query.addWhereEqualTo("userStuID", getStuID());
            query.findObjects(new FindListener<UserBeanDB>() {
                @Override
                public void done(List<UserBeanDB> list, BmobException e) {
                    if (e == null) {
                        if (list.size() > 0) {
                            Message msg = new Message();
                            msg.what = 0;
                            handler.sendMessage(msg);
                        } else {
                            registerUser();
                        }
                    } else {
                        Log.d("bmob2", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        }
    }

    private void login() {
        if (!checkInfoISNull()) {
            BmobQuery<UserBeanDB> query = new BmobQuery<>();
            query.addWhereEqualTo("userStuID", getStuID());
            query.findObjects(new FindListener<UserBeanDB>() {
                @Override
                public void done(List<UserBeanDB> list, BmobException e) {
                    if (e == null) {
                        Message msg = new Message();
                        if (list.size() > 0) {
                            if (list.get(0).getUserPassword().equals(getPassword()) && list.get(0).getUserName().equals(getUserName())) {
                                msg.what = 3;
                                user.setObjID(list.get(0).getObjectId());
                                user.setUserPassword(list.get(0).getUserPassword());
                                user.setUserStuID(list.get(0).getUserStuID());
                                user.setUserName(list.get(0).getUserName());
                                user.setJoinTeamID(list.get(0).getJoinTeamID());
                                user.setLeader(list.get(0).isLeader());
                                user.setTeacher(list.get(0).isTeacher());
                                Log.d("mm", "done login: " + list.get(0).getObjectId());
                            } else {
                                msg.what = 5;
                            }
                        } else {
                            msg.what = 4;
                        }
                        handler.sendMessage(msg);
                    } else {
                        Log.d("bmob3", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        }

    }

    private void createDialog() {
        showAlertDialog("", "若学号从未注册过，请先点击注册，若已注册过，请直接点击登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }, "确定", null, "");
    }

    private void startMainAt() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean checkInfoISNull() {
        if (!getUserName().isEmpty() && getStuID() != null && !getPassword().isEmpty()) {
            return false;
        } else {
            changeRegistBtuStatus(0);
            changLogintBtuStatus(0);
            return true;
        }
    }

    private String getUserName() {
        String content = editUsername.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "姓名不能为空", LENGTH_SHORT).show();
            return "";
        }
        return content;
    }

    private Long getStuID() {
        String content = editStuid.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "学号不能为空", LENGTH_SHORT).show();
            return null;
        }
        return Long.valueOf(content);
    }

    private String getPassword() {
        String content = editPassword.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "密码不能为空", LENGTH_SHORT).show();
            return "";
        }
        return content;
    }

    private void changLogintBtuStatus(int status) {
        if (status == 1) {
            btuLogin.setText("登录中....");
        } else {
            btuLogin.setText("登    录");
        }
    }

    private void changeRegistBtuStatus(int status) {
        if (status == 1) {
            btuLogin.setText("注册中....");
        } else {
            btuLogin.setText("注    册");
        }
    }
}
