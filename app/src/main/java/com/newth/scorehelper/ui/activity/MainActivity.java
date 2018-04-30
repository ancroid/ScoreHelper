package com.newth.scorehelper.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.newth.scorehelper.R;
import com.newth.scorehelper.bean.Inner.User;
import com.newth.scorehelper.bean.TeamBeanDB;
import com.newth.scorehelper.bean.UserBeanDB;
import com.newth.scorehelper.ui.fragment.MeFragment;
import com.newth.scorehelper.ui.fragment.TeamFragment;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author Mr.chen
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.frame_main)
    FrameLayout frameMain;
    @BindView(R.id.navigation_bar_main)
    BottomNavigationBar navigationBarMain;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    private FragmentManager fragmentManager;
    private int currentPosition = 0;

    private DialogPlus dialog;
    private Holder holder;
    private User user = User.getInstance();
    private String dialogInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        initBottomBar();
        inittoolbar();
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        changeFragment(0);
    }

    private void inittoolbar() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    private void initBottomBar() {
        navigationBarMain.setActiveColor(R.color.primary_color);
        navigationBarMain.addItem(new BottomNavigationItem(R.drawable.ic_team, "团队"))
                .addItem(new BottomNavigationItem(R.drawable.ic_me, "我的"))
                .initialise();
        navigationBarMain.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                changeFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void changeFragment(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment oldfragment = fragmentManager.findFragmentByTag(makeTag(currentPosition));
        if (oldfragment != null) {
            transaction.hide(oldfragment);
        }
        currentPosition = position;
        Fragment newfragment = fragmentManager.findFragmentByTag(makeTag(position));
        if (newfragment != null) {
            transaction.show(newfragment);
        } else {
            transaction.add(R.id.frame_main, makeFragment(position), makeTag(position));
        }
        transaction.commitAllowingStateLoss();
    }

    private String makeTag(int position) {
        return R.id.frame_main + position + "";
    }

    private Fragment makeFragment(int position) {
        switch (position) {
            case 0:
                return TeamFragment.newInstance();
            case 1:
                return MeFragment.newInstance();
            default:
                return TeamFragment.newInstance();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.join_team) {
            if (checkIsteamNull()) {
                createJoinDialog();
            } else {
                Toast.makeText(MainActivity.this, "已加入团队", Toast.LENGTH_SHORT).show();
            }
        }
        if (item.getItemId() == R.id.create_team) {
            if (checkIsteamNull()) {
                createFoundDialog();
            } else {
                Toast.makeText(MainActivity.this, "已加入团队", Toast.LENGTH_SHORT).show();
            }
        }
        if (item.getItemId() == R.id.team_invite_num) {
            if (checkIsteamNull()) {
                Toast.makeText(MainActivity.this, "未加入团队", Toast.LENGTH_SHORT).show();
            } else {
                showAlertDialog("邀请码", user.getJoinTeamID(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }, "确定", null, "");

            }
        }
        if (item.getItemId()==R.id.score_update){
            changeFragment(currentPosition);
        }
        return true;
    }

    private void createJoinDialog() {
        holder = new ViewHolder(R.layout.dialog_join_team);
        dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setGravity(Gravity.TOP)
                .setCancelable(true)
                .setPadding(100, 10, 100, 0)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.button_dialog_join:
                                if (checkInfo()) {
                                    queryTeamByInvite(dialogInput);
                                }
                                break;
                            case R.id.button_dialog_cancel:
                                if (getWindow().peekDecorView() != null) {
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    if (inputMethodManager != null) {
                                        inputMethodManager.hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(), 0);
                                    }
                                }
                                dialog.dismiss();
                                break;
                            default:
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .create();
        dialog.show();
    }

    private void createFoundDialog() {
        holder = new ViewHolder(R.layout.dialog_found_team);
        dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setGravity(Gravity.TOP)
                .setCancelable(false)
                .setPadding(100, 10, 100, 0)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.button_dialog_found:
                                if (checkInfo()) {
                                    foundTeam(dialogInput);
                                }
                                break;
                            case R.id.button_dialog_cancel:
                                if (getWindow().peekDecorView() != null) {
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    if (inputMethodManager != null) {
                                        inputMethodManager.hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(), 0);
                                    }
                                }
                                dialog.dismiss();
                                break;
                            default:
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .create();
        dialog.show();
    }

    private void queryTeamByInvite(String invite) {
        BmobQuery<TeamBeanDB> query = new BmobQuery<>();
        query.getObject(invite, new QueryListener<TeamBeanDB>() {
            @Override
            public void done(TeamBeanDB teamBeanDB, BmobException e) {
                if (e == null) {
                    joinTeam(teamBeanDB.getObjectId());
                } else {
                    Log.d("bmob", "加入失败：团队不存在" + e.getMessage() + "," + e.getErrorCode());
                    Toast.makeText(MainActivity.this, "团队不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void joinTeam(final String objID) {
        TeamBeanDB teamBeanDB = new TeamBeanDB();
        teamBeanDB.setObjectId(objID);
        teamBeanDB.addUnique("teamMembStuID", user.getUserStuID());
        teamBeanDB.addUnique("teamMembName", user.getUserName());
        teamBeanDB.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    user.setJoinTeamID(objID);
                    updateUserInfo(objID, false);
                } else {
                    Log.d("bmob", "加入失败：" + e.getMessage() + "," + e.getErrorCode());
                    Toast.makeText(MainActivity.this, "加入失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void foundTeam(String teamName) {
        TeamBeanDB teamBeanDB = new TeamBeanDB();
        teamBeanDB.setTeamName(teamName);
        teamBeanDB.setTeamLeaderStuID(user.getUserStuID());
        List<String> name = new ArrayList<>();
        name.add(user.getUserName());
        teamBeanDB.setTeamMembName(name);
        List<Long> id = new ArrayList<>();
        id.add(user.getUserStuID());
        teamBeanDB.setTeamMembStuID(id);
        teamBeanDB.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    user.setJoinTeamID(s);
                    user.setLeader(true);
                    updateUserInfo(s, true);
                } else {
                    Log.d("bmob", "创建失败：" + e.getMessage() + "," + e.getErrorCode());
                    Toast.makeText(MainActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkIsteamNull() {
        if (user.getJoinTeamID() == null || user.getJoinTeamID().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private boolean checkInfo() {
        View view = holder.getInflatedView();
        EditText editText = view.findViewById(R.id.text_input);
        if ("".equals(editText.getText().toString()) || editText.getText() == null) {
            Toast.makeText(this, "输入为空", Toast.LENGTH_SHORT).show();
        } else {
            dialogInput = editText.getText().toString();
            return true;
        }

        return false;
    }

    private void updateUserInfo(String teamid, Boolean isleader) {
        UserBeanDB userBeanDB = new UserBeanDB();
        userBeanDB.setObjectId(user.getObjID());
        userBeanDB.setJoinTeamID(teamid);
        userBeanDB.setLeader(isleader);
        userBeanDB.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    dialog.dismiss();
                    changeFragment(0);
                } else {
                    Log.d("bmob", "数据更新失败：" + e.getMessage() + "," + e.getErrorCode());
                    Toast.makeText(MainActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
