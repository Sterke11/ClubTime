package com.example.clubtime;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MenuPrincipalClubAdmin extends AppCompatActivity {
    public static Club clubActivo;
    public static Usuario usuarioActivo;
    ImageView img;
    GlobalClass gc;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_club_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        Club club = (Club) getIntent().getSerializableExtra("ative_club");
        Usuario usuario = (Usuario) Objects.requireNonNull(getIntent().getExtras()).getSerializable("usuario");

        //Es el xml del menu lateral
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //Construccion del menu
        NavController navController;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_principal_alumno, R.id.nav_list_alumnos_de_alumno,
                R.id.nav_enviar_correo, R.id.generaCodigoLista, R.id.nav_ingresar_codigo)
                .setDrawerLayout(drawer)
                .build();

        if(usuario.getTipo() == 1){
            Menu nav_menu = navigationView.getMenu();
            nav_menu.findItem(R.id.nav_principal_alumno).setVisible(false);
            nav_menu.findItem(R.id.nav_list_alumnos_de_alumno).setVisible(false);
            nav_menu.findItem(R.id.nav_ingresar_codigo).setVisible(false);

            nav_menu.findItem(R.id.nav_neutral).setVisible(false);
        }
        else{
            Menu nav_menu = navigationView.getMenu();
            nav_menu.findItem(R.id.nav_home).setVisible(false);
            nav_menu.findItem(R.id.nav_gallery).setVisible(false);
            nav_menu.findItem(R.id.nav_slideshow).setVisible(false);
            nav_menu.findItem(R.id.nav_enviar_correo).setVisible(false);
            nav_menu.findItem(R.id.generaCodigoLista).setVisible(false);

            nav_menu.findItem(R.id.nav_neutral).setVisible(false);
        }

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Obtener header del menu navegador
        View hView =navigationView.getHeaderView(0);
        TextView tv_menu_nombre_club= hView.findViewById(R.id.tv_menu_nombre_club);
        TextView tv_nombre_usuario=hView.findViewById(R.id.textView);

        //Obtener el club activo

        tv_menu_nombre_club.setText(club.getNombre());
        tv_nombre_usuario.setText(usuario.getNombreUser());
        //Quitar tinta de los iconos
        navigationView.setItemIconTintList(null);
        //hacer que el boton de hamburguesa abra el menu lateral

        //Pasar los objetos a los fragments
        Bundle bundle=new Bundle();
        bundle.putSerializable("usuario",usuario);
        navController.getCurrentDestination();
        // bundle.putSerializable("club",club);
        //navController.navigate(R.id.nav_gallery,bundle);
        // navController.navigate(R.id.nav_home,bundle);
        //Validar permisos de fotos
        //      if(validarPermisos()){

        //    }

         gc = (GlobalClass) getApplicationContext();
        clubActivo = club;
        gc.setActive_club(club);
        gc.setActive_user(usuario);
        usuarioActivo = gc.getActive_user();
        //Toast.makeText(getApplicationContext(),gc.getActive_club().getNombre(),Toast.LENGTH_LONG).show();
        img=hView.findViewById(R.id.imageView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Picasso.with(getApplicationContext()).load("https://clubescom.000webhostapp.com/imagenes/"+gc.getActive_club().getAlias()+".jpg").memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).placeholder(R.drawable.sinimagen).into(img);
                drawer.openDrawer(Gravity.START);
            }
        });


    }

    public static Club getClubActivo()
    {
        return clubActivo;
    }
    public static Usuario getUsuarioActivo()
    {
        return usuarioActivo;
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



/*
    private boolean validarPermisos() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&(checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) &&(checkSelfPermission(READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)){

            return true;
        }
        if(shouldShowRequestPermissionRationale(CAMERA)||shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)||shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)){
            cargarDialogoPermisos();
        }else {
            requestPermissions(new String[]{CAMERA,WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},100);
        }
        return false;
    }

    private void cargarDialogoPermisos() {
        AlertDialog.Builder dialogoPer=new AlertDialog.Builder(MenuPrincipalClubAdmin.this);
        dialogoPer.setTitle("Permisos Desactivados");
        dialogoPer.setMessage("Debe aceptar los permisos para que funcione esta cosa");
        dialogoPer.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{CAMERA,WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},100);
            }
        });
        dialogoPer.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length==3&&grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED&&grantResults[2]==PackageManager.PERMISSION_GRANTED){

            }else {
                solicitarPermisosManual();
            }
        }
    }
    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(MenuPrincipalClubAdmin.this);
        alertOpciones.setTitle("Entonces lo hacemas manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which].equals("si")){
                    Intent intent=new Intent();
                    Uri uri=Uri.fromParts("package", getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);

                }else {
                    dialog.dismiss();
                }
            }
        });
        alertOpciones.show();
    }*/
}