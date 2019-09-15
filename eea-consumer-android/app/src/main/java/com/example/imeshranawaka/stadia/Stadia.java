package com.example.imeshranawaka.stadia;

import android.os.Bundle;

import com.example.imeshranawaka.stadia.Fragments.OrdersView;
import com.example.imeshranawaka.stadia.Fragments.ProductsView;
import com.example.imeshranawaka.stadia.Fragments.ProfileView;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

import com.example.imeshranawaka.stadia.APIs.APIBuilder;
import com.example.imeshranawaka.stadia.Fragments.Login;
import com.example.imeshranawaka.stadia.Fragments.MainMenu;
import com.example.imeshranawaka.stadia.Models.LoginDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
                            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            int backStackEntry = fm.getBackStackEntryCount();
            List<Fragment> fragments = fm.getFragments();
            if (backStackEntry > 0) {
                for (int i = 0; i < backStackEntry; i++) {

                    fm.popBackStackImmediate();
                    if(fragments.size()<i) {
                        Fragment frag = fragments.get(0);
                        transaction.remove(frag);
                        if(frag.getTag()!=null && !frag.getTag().equals("ProductsView")) {
                            break;
                        }
                    }
                    fragments = fm.getFragments();
                }
            }
            transaction.replace(R.id.subFragment, new ProductsView(), "ProductsView");
            transaction.commit();
        } else if (id == R.id.nav_account) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            int backStackEntry = fm.getBackStackEntryCount();
            List<Fragment> fragments = fm.getFragments();
            if (backStackEntry > 0) {
                for (int i = 0; i < backStackEntry; i++) {
                    fm.popBackStackImmediate();
                    if(fragments.size()<i) {
                        Fragment frag = fragments.get(0);
                        transaction.remove(frag);
                        System.err.println("frag tag : "+frag.getTag());
                        if(frag.getTag()!=null && !frag.getTag().equals("ProfileView")) {
                            break;
                        }
                    }
                    fragments = fm.getFragments();
                }
            }
            transaction.replace(R.id.subFragment, new ProfileView(), "ProfileView");
            transaction.commit();
        } else if (id == R.id.nav_order) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            int backStackEntry = fm.getBackStackEntryCount();
            List<Fragment> fragments = fm.getFragments();
            if (backStackEntry > 0) {
                for (int i = 0; i < backStackEntry; i++) {
                    fm.popBackStackImmediate();
                    if(fragments.size()<i) {
                        Fragment frag = fragments.get(0);
                        transaction.remove(frag);
                        System.err.println("frag tag : "+frag.getTag());
                        if(frag.getTag()!=null && !frag.getTag().equals("OrdersView")) {
                            break;
                        }
                    }
                    fragments = fm.getFragments();
                }
            }
            transaction.replace(R.id.subFragment, new OrdersView(), "OrdersView");
            transaction.commit();

        }else if(id == R.id.nav_contact){

        }else if(id == R.id.nav_logout){
            APIBuilder.Logout(getApplicationContext(),this);
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
