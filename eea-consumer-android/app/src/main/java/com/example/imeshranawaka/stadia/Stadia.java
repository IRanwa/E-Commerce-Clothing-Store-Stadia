package com.example.imeshranawaka.stadia;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.imeshranawaka.stadia.Fragments.MainMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Stadia extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout drawer_layout;
    @BindView(R.id.nav_view) NavigationView nav_view;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadia);

        unbinder = ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);

        nav_view.setEnabled(true);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        if (findViewById(R.id.mainFragment) != null) {
            if (savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.mainFragment, new MainMenu(), "MainMenu").
                    commit();
//            String email = SharedPreferenceUtility.getInstance(this).getUserEmail();
//            if(email.isEmpty()) {
//                getSupportFragmentManager().beginTransaction().
//                        replace(R.id.mainFragment, new SignIn(), "SignIn").
//                        commit();
//            }else{
//                List<Login> login = Login.find(Login.class, "email=?", email);
//                if(login.size()>0) {
//                    SharedPreferenceUtility shraed = SharedPreferenceUtility.getInstance(this);
//                    getSupportFragmentManager().beginTransaction().
//                            replace(R.id.mainFragment, new MainMenu(), "MainMenu").
//                            commit();
//                }else{
//                    SharedPreferences.Editor editor = SharedPreferenceUtility.getInstance(this).getEditor();
//                    editor.remove("email");
//                    editor.remove("user");
//                    editor.remove("pass");
//                    editor.commit();
//                    getSupportFragmentManager().beginTransaction().
//                            replace(R.id.mainFragment, new SignIn(), "SignIn").
//                            commit();
//                }
//            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_order) {

        }else if(id == R.id.nav_contact){

        }else if(id == R.id.nav_logout){

        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
