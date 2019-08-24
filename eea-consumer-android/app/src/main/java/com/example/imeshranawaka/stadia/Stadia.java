package com.example.imeshranawaka.stadia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Fragments.Login;
import com.example.imeshranawaka.stadia.Fragments.MainMenu;
import com.example.imeshranawaka.stadia.Models.LoginDTO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Stadia extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) static DrawerLayout drawer_layout;
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
            SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(this);

            String token = sharedPref.getUserToken();
            if(token.isEmpty()) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.mainFragment, new Login(), "Login").
                        commit();
            }else{
                LoginDTO loginDTO = new LoginDTO(sharedPref.getUserEmail(),sharedPref.getUserPass(),sharedPref.getUserToken());
                Call<Boolean> apiClient = APIBuilder.createBuilder().validateToken(loginDTO);
                apiClient.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Boolean status = response.body();
                        System.out.println("status "+status);
                        if(status==null){
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.mainFragment, new Login(), "Login").
                                    commit();
                        }else{
                            drawerLockOpen();
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.mainFragment, new MainMenu(), "MainMenu").
                                    commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        //.Editor editor = SharedPreferenceUtility.getInstance(this).getEditor();
                        System.err.println("error "+t.getMessage());
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.mainFragment, new Login(), "Login").
                                commit();
                    }
                });
            }
        }
    }

    public static void drawerLockOpen(){
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
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
