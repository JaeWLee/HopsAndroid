package com.mineru.hops;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Activity {

    private FloatingActionMenu menuRed;
    private FloatingActionMenu menuYellow;
    private FloatingActionMenu menuGreen;
    private FloatingActionMenu menuBlue;
    private FloatingActionMenu menuDown;
    private FloatingActionMenu menuLabelsRight;

    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    private FloatingActionButton fabEdit;

    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menus_fragment);

        menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
        menuYellow = (FloatingActionMenu) findViewById(R.id.menu_yellow);
        menuGreen = (FloatingActionMenu) findViewById(R.id.menu_green);
        menuBlue = (FloatingActionMenu) findViewById(R.id.menu_blue);
        menuDown = (FloatingActionMenu) findViewById(R.id.menu_down);
        menuLabelsRight = (FloatingActionMenu) findViewById(R.id.menu_labels_right);

        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        final FloatingActionButton programFab1 = new FloatingActionButton(getApplicationContext());
        programFab1.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab1.setLabelText(getString(R.string.lorem_ipsum));
        programFab1.setImageResource(R.drawable.icon_edit);
        menuRed.addMenuButton(programFab1);
        programFab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programFab1.setLabelColors(ContextCompat.getColor(getApplicationContext(), R.color.grey),
                        ContextCompat.getColor(getApplicationContext(), R.color.grey),
                        ContextCompat.getColor(getApplicationContext(), R.color.white));
                programFab1.setLabelTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
            }
        });

        ContextThemeWrapper context = new ContextThemeWrapper(getApplicationContext(), R.style.MenuButtonsStyle);
        FloatingActionButton programFab2 = new FloatingActionButton(context);
        programFab2.setLabelText("Programmatically added button");
        programFab2.setImageResource(R.drawable.icon_edit);
        menuYellow.addMenuButton(programFab2);

        fab1.setEnabled(false);
        menuRed.setClosedOnTouchOutside(false);
        menuBlue.setIconAnimated(false);

        menuDown.hideMenuButton(false);
        menuRed.hideMenuButton(false);
        menuYellow.hideMenuButton(false);
        menuGreen.hideMenuButton(false);
        menuBlue.hideMenuButton(false);
        menuLabelsRight.hideMenuButton(false);

        fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit);
        fabEdit.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fabEdit.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));
        menus.add(menuDown);
        menus.add(menuRed);
        menus.add(menuYellow);
        menus.add(menuGreen);
        menus.add(menuBlue);
        menus.add(menuLabelsRight);

        menuYellow.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                String text;
                if (opened) {
                    text = "Menu opened";
                } else {
                    text = "Menu closed";
                }
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);

        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fabEdit.show(true);
            }
        }, delay + 150);

        menuRed.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuRed.isOpened()) {
                    Toast.makeText(getApplicationContext(), menuRed.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
                }

                menuRed.toggle(true);
            }
        });

        createCustomAnimation();
    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menuGreen.getMenuIconView().setImageResource(menuGreen.isOpened()
                        ? R.drawable.icon_delete : R.drawable.img_home);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menuGreen.setIconToggleAnimatorSet(set);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab1:
                    break;
                case R.id.fab2:
                    fab2.setVisibility(View.GONE);
                    break;
                case R.id.fab3:
                    fab2.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
}
