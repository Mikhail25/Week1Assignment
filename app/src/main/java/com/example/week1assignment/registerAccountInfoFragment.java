package com.example.week1assignment;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;


/**
 * A simple {@link Fragment} subclass.
 */
public class registerAccountInfoFragment extends Fragment {
    View rootView;
    ImageView image_profile;
    EditText et_name, et_username, et_pass2,et_age, et_birthdate, et_post_address;
    Button btn_pic_picker, btn_save;
    Spinner spin_country;
    RadioGroup radio_gender_picker;
    RadioButton radioButtonGender;
    String country_item, pathToFile;
    Realm realm;
    private static final String TAG = "registerAccountInfoFrag";
    private static final int REQUEST_IMAGE_CAPTURE = 52;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public registerAccountInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_register_account_info, container, false);


        if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        //realm = Realm.getDefaultInstance();


        //Sooooo much more data binding...
        image_profile = rootView.findViewById(R.id.image_profile);
        et_name = rootView.findViewById(R.id.et_name);
        et_username= rootView.findViewById(R.id.et_username);
        et_pass2 = rootView.findViewById(R.id.et_pass2);
        et_age = rootView.findViewById(R.id.et_age);
        et_birthdate = rootView.findViewById(R.id.et_birthdate);
        et_post_address = rootView.findViewById(R.id.et_postal_address);
        btn_pic_picker = rootView.findViewById(R.id.btn_picture_picker);
        btn_save = rootView.findViewById(R.id.btn_account_save);
        spin_country= rootView.findViewById(R.id.spin_country);
        radio_gender_picker= rootView.findViewById(R.id.radio_gender_picker);

        //Okay, now onto the coding!
        //Getting Bundles
        Bundle bundle = getArguments();
        et_username.setText(bundle.getString("email"));
        et_pass2.setText(bundle.getString("password"));



        //Populate spinner with options
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(getContext(),R.array.country,android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_country.setAdapter(spinAdapter);

        //Listener to get the item value from the spinner
        spin_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Not-Specified")){

                }else{
                    country_item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: "+country_item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Listener to set the birth date, calls datepicker dialog
        et_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Birthdate Clicked!");
                Calendar calendar =Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),android.R.style.Theme_Holo_Panel,
                        dateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        //sets the date to the text
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: "+month+"/"+dayOfMonth+"/"+year);
                String date = month+"/"+dayOfMonth+"/"+year;
                et_birthdate.setText(date);
            }
        };

        //picks the options from the radiopicker
        radio_gender_picker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonGender = radio_gender_picker.findViewById(checkedId);
                //Toast.makeText(getContext(),"Clicked: "+radioButtonGender.getText(),Toast.LENGTH_SHORT).show();
            }
        });

        btn_pic_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCameraPic();
                Toast.makeText(getContext(),"Profile Image is currently in development",Toast.LENGTH_LONG).show();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });


        return rootView;
    }

    private void saveUserData() {
        String name = et_name.getText().toString().trim();
        String username = et_username.getText().toString().trim();
        String password = et_pass2.getText().toString();
        String age = et_age.getText().toString();
        String birthDate = et_birthdate.getText().toString();
        String country = country_item;
        String gender = radioButtonGender.getText().toString();
        String postalAddress = et_post_address.getText().toString().trim();

        if(username.isEmpty()){
            Toast.makeText(getContext(),"Username required...",Toast.LENGTH_LONG).show();
        }


                Toast.makeText(getContext(),"Users{" +
                        "name='" + name + '\'' +
                        ", username='" + username + '\'' +
                        ", password='" + password + '\'' +
                        ", age='" + age + '\'' +
                        ", birthDate='" + birthDate + '\'' +
                        ", country='" + country + '\'' +
                        ", gender='" + gender + '\'' +
                        ", postalAddress='" + postalAddress + '\'' +
         //               ", ImageUrl='" + ImageUrl + '\'' +
                        '}',Toast.LENGTH_LONG).show();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }



    //    private void getCameraPic() {
//        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Log.d(TAG, "getCameraPic: "+takePicIntent.resolveActivity(getContext().getPackageManager()));
//
//        if(takePicIntent.resolveActivity(getContext().getPackageManager())!= null){
//            File photoFile = null;
//            photoFile = createPhotoFromFile();
//            if(photoFile!=null){
//                pathToFile = photoFile.getAbsolutePath();
//                Uri photoUri = FileProvider.getUriForFile(getContext(),"fssdfs",photoFile);
//                takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
//                startActivityForResult(takePicIntent,1);
//            }
//            //startActivityForResult(takePicIntent,REQUEST_IMAGE_CAPTURE);
//
//        }
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK){
//            if(requestCode == 1){
//                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
//                image_profile.setImageBitmap(bitmap);
//            }
//        }
//    }
//
//    private File createPhotoFromFile() {
//        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File image = null;
//        try{
//            image = File.createTempFile(name,".jpg",storageDir);
//        }catch (Exception e){
//            Log.d(TAG, "createPhotoFromFile: "+e.toString());
//            e.printStackTrace();
//        }
//        return image;
//    }


}
