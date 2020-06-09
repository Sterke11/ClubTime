package com.example.clubtime;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;

import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.Objects;

public class MenuPrincipalClubAdmin extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_club_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        //Es el xml del menu lateral
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();
        final NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //Construccion del menu
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
     //Obtener header del menu navegador
        View hView =navigationView.getHeaderView(0);
        TextView tv_menu_nombre_club= hView.findViewById(R.id.tv_menu_nombre_club);
        TextView tv_nombre_usuario=hView.findViewById(R.id.textView);
       //Obtener el club activo
        Club club = (Club) getIntent().getSerializableExtra("ative_club");
        Usuario usuario = (Usuario) Objects.requireNonNull(getIntent().getExtras()).getSerializable("usuario");
        tv_menu_nombre_club.setText(club.getNombre());
        tv_nombre_usuario.setText(usuario.getNombreUser());
        //Quitar tinta de los iconos
        navigationView.setItemIconTintList(null);
        //hacer que el boton de hamburguesa abra el menu lateral
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                drawer.openDrawer(Gravity.START);
            }
        });
        //Pasar los objetos a los fragments
        Bundle bundle=new Bundle();
        bundle.putSerializable("usuario",usuario);
        navController.getCurrentDestination();
       // bundle.putSerializable("club",club);
        //navController.navigate(R.id.nav_gallery,bundle);
       // navController.navigate(R.id.nav_home,bundle);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_club_admin, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}