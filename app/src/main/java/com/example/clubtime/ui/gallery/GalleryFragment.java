package com.example.clubtime.ui.gallery;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.app.TimePickerDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.TimePicker;

import com.example.clubtime.Club;
import com.example.clubtime.ConexionDB;
import com.example.clubtime.R;
import com.example.clubtime.Usuario;

import java.io.File;
import java.util.Calendar;



public class GalleryFragment extends Fragment implements View.OnClickListener {

    private GalleryViewModel galleryViewModel;
    private final String CARPETA_RAIZ="misImagenesPruba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misfotos";
    private final int CARGAR_IMAGEN=10;
    private final int TOMAR_IMAGEN=20;
    String path;
    TextView tv_hora_fin,tv_hora_ini;
    ImageView iv_CargarImagen;
    int t1Hora,t1Minuto,t2Hora,t2Minuto;
    Club club;
    Usuario usuario;
    ConexionDB bd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        //Asignar elementos

        tv_hora_fin = root.findViewById(R.id.tv_hora_fin);
        tv_hora_ini = root.findViewById(R.id.tv_hora_ini);
        iv_CargarImagen=root.findViewById(R.id.iv_CargarImagen);

        //Validar permisos de fotos
        if(validarPermisos()){
            iv_CargarImagen.setEnabled(true);
        }else {
            iv_CargarImagen.setEnabled(false);
        }
        //Crear acciones al dar clic

        tv_hora_ini.setOnClickListener(this);
        tv_hora_fin.setOnClickListener(this);
        iv_CargarImagen.setOnClickListener(this);

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        //club=(Club) getArguments().getSerializable("club");

       // usuario=(Usuario) getArguments().getSerializable("usuario");


        return root;

    }

    private boolean validarPermisos() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)&&(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)){

            return true;
        }
        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)||shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
          cargarDialogoPermisos();
        }else {
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length==2&&grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){

            }else {
                solicitarPermisosManual();
            }
        }
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Entonces lo hacemas manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which].equals("si")){
                    Intent intent=new Intent();
                    Uri uri=Uri.fromParts("package", getActivity().getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);

                }else {
                    dialog.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoPermisos() {
        AlertDialog.Builder dialogoPer=new AlertDialog.Builder(getContext());
        dialogoPer.setTitle("Permisos Desactivados");
        dialogoPer.setMessage("Debe aceptar los permisos para que funcione esta cosa");
        dialogoPer.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
        });
        dialogoPer.show();
    }


    @Override
    public void onClick(final View v) {

        if(v.getId() == R.id.tv_hora_ini) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    t1Hora = hourOfDay;
                    t1Minuto = minute;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(0, 0, 0, t1Hora, t1Minuto);

                    tv_hora_ini.setText(DateFormat.format("hh:mm aa", calendar));



                }
            }, 12, 0, false);

            timePickerDialog.updateTime(t2Hora, t2Minuto);
            timePickerDialog.show();
        }

        if(v.getId() == R.id.tv_hora_fin)
        {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                t2Hora = hourOfDay;
                t2Minuto = minute;
                Calendar calendar = Calendar.getInstance();
                calendar.set(0, 0, 0, t2Hora, t2Minuto);

                tv_hora_fin.setText(DateFormat.format("hh:mm aa", calendar));



                }
            }, 12, 0, false);

                timePickerDialog.updateTime(t2Hora, t2Minuto);
                timePickerDialog.show();

        }
        if(v.getId() == R.id.iv_CargarImagen){
            cargarImagen();
        }




    }

    private void cargarImagen() {
        final CharSequence[] opciones={"Tomar imagen","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una opcion");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which].equals("Tomar Foto")){
                    tomarFoto();

                }else {
                    if(opciones[which].equals("Cargar Imagen")){
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("imagen/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),CARGAR_IMAGEN);
                    }else{
                        dialog.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    private void tomarFoto() {
        String nombreIm = null;
        //asignar la ruta donde guardaremos la imagen
        File fileImgen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);

        //pasa saber si la ruta ya existe
        boolean isCreada=fileImgen.exists();
        //si la ruta no exite la crea
        if(isCreada==false){
            isCreada=fileImgen.mkdir();
        }
        //si la ruta ya existe entonces creamos el nombre del archivo a guardar
        if(isCreada==true){
             nombreIm=System.currentTimeMillis()+".jpg";
        }
        //entonces ya tenemos el path del archivo a guardar
        path=Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreIm;

        //crear el archivo
        File imagen=new File(path);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            String authorities= getActivity().getApplicationContext()+".provider";
            Uri imageUri= FileProvider.getUriForFile(getContext(),authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        }else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imagen));
        }


        //activa el metoso on ActivityResult
        startActivityForResult(intent,TOMAR_IMAGEN);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==-1 ){
            if(requestCode==CARGAR_IMAGEN){
                Uri mipath=data.getData();
                iv_CargarImagen.setImageURI(mipath);
            }else if(requestCode==TOMAR_IMAGEN){
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("Ruta de almacenamiento","path:"+path);

                    }
                });
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                iv_CargarImagen.setImageBitmap(bitmap);
            }
        }

    }
}