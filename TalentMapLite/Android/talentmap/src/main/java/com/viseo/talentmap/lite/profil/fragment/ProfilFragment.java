package com.viseo.talentmap.lite.profil.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viseo.talentmap.R;
import com.viseo.talentmap.common.utils.LoaderUtils;
import com.viseo.talentmap.lite.MainActivity;
import com.viseo.talentmap.lite.profil.adapter.ProfilAdapter;
import com.viseo.talentmap.lite.profil.data.ProfilUser;
import com.viseo.talentmap.lite.profil.loader.GetMeLoader;
import com.viseo.talentmap.lite.profil.loader.UpdateNameLoader;
import com.viseo.talentmap.lite.profil.loader.UpdatePhotoLoader;
import com.viseo.talentmap.modifications_skills.activity.SkillsActivity;
import com.viseo.talentmap.view.DistantImageView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by patiencebayogha on 20/02/15.
 * 16/03/2015
 * 31/03/15
 * last revision:23/04/2015 and 24/04/15
 * Permit to create Profil User and recuperate: name, lastName, profil picture, listview (skills ,category, level).
 * you need to have a parameter email for find user
 */
public class ProfilFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object>, View.OnClickListener {

    private ProgressDialog progressDialog;
    private static final int SELECT_PICTURE = 1;          //selected picture in gallery
    private static final int LOADER_GET_ME = LoaderUtils.getInstance().getLoaderId();       //add get me loader Id
    private static final int LOADER_UPDATE_PHOTO = LoaderUtils.getInstance().getLoaderId();       //add update photo loader Id
    private static final int LOADER_UPDATE_NAME = LoaderUtils.getInstance().getLoaderId();      //add update name loader Id
    protected Handler myHandler;            //handle for activity
    TextView choiceSkill;                   //Textview for appear skill il listview
    TextView choiceCategory;                 //Textview for appear category il listview
    private ProfilAdapter myAdapter;          //adapter for recuperate in listview categories,skills,level of user
    private EditText name;                     //EditText for name
    private EditText lastName;                  //EditText for lastname
    private ListView list;                      //listView for recuperate list of users profil (categories, levels, skills)
    private DistantImageView image;              //for show image profil user
    private ImageButton add;                     // add a new skill
    private String email;
    Uri selectedImage;
    String imgPath;
    String upLoadServerUri = "http://tml.hubi.org:80/api/v1";
    int serverResponseCode = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getActivity().getIntent().getExtras().getString(MainActivity.EMAIL);
        myHandler = new Handler();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//hide the keyboard everytime the activty starts
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View resultView = inflater.inflate(R.layout.fragment_profil, container, false);

        list = (ListView) resultView.findViewById(R.id.swipedelete);
        myAdapter = new ProfilAdapter(getActivity(), myHandler, email);
        choiceSkill = (TextView) resultView.findViewById(R.id.choix_competence);
        choiceCategory = (TextView) resultView.findViewById(R.id.choix_categorie);

        list.setAdapter(myAdapter);

        image = (DistantImageView) resultView.findViewById(R.id.image_users);
        image.setOnClickListener(this);

        name = (EditText) resultView.findViewById(R.id.name_users);
        name.setBackgroundResource(R.color.cyan_primary);


        lastName = (EditText) resultView.findViewById(R.id.lastName_users);
        lastName.setBackgroundResource(R.color.cyan_primary);


        add = (ImageButton) resultView.findViewById(R.id.activity_skills_image_button_plus);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SkillsActivity.class);
                intent.putExtra(MainActivity.EMAIL, email);
                startActivity(intent);
            }
        });


        return resultView;
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        /*
    Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned,
    but before any saved state has been restored in to the view.
     */
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        initLoader(LOADER_GET_ME);  //for recuperate loader Get Me
    }


    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        Loader result = null;
        if (id == LOADER_GET_ME) {
            //used for method initLoader
            result = new GetMeLoader(getActivity());
        } else if (LOADER_UPDATE_NAME == id) {
            //used for method modificationProfil
            result = new UpdateNameLoader(getActivity(), args);
        } else if (LOADER_UPDATE_PHOTO == id) {

            result = new UpdatePhotoLoader(getActivity(), args);
        }
        return result;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        if (LOADER_GET_ME == loader.getId()) {
            if (data != null && data instanceof ProfilUser) {
                ProfilUser user = (ProfilUser) data; //cast ProfilUser in loader
                image.setHandler(myHandler);
                name.setText(user.getName());
                if (imgPath != null) {
                    new uploadPhoto();
                    image.setImageURI(selectedImage);
                } else
                    image.setUrl(user.getPhoto());
                lastName.setText(user.getSurname());
                myAdapter.setData(user.getSkills());

            } else {

                Log.e(getClass().getCanonicalName(), "error during loading: ProfilFragment.");
            }
        } else if (LOADER_UPDATE_NAME == loader.getId()) {

            if (data != null && data instanceof ProfilUser) {
                ProfilUser users = (ProfilUser) data;
                name.setText(users.getName());
                lastName.setText(users.getSurname());
                myAdapter.setData(users.getSkills());
                if (imgPath != null) {
                    new uploadPhoto();
                    image.setImageURI(selectedImage);
                } else
                    image.setUrl(users.getPhoto());

            }
        } else if (LOADER_UPDATE_PHOTO == loader.getId()) {

            if (data != null && data instanceof ProfilUser) {
                ProfilUser u = (ProfilUser) data;

                image.setHandler(myHandler);

                if (imgPath != null) {
                    new uploadPhoto();
                    image.setImageURI(selectedImage);
                } else
                    image.setUrl(u.getPhoto());
            }

        }


        /**
         * For Show and hide Keyboard for name, lastname and to mofify profil
         */
        name.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                name.setFocusable(true);
                lastName.setFocusable(true);
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    try {
                        modificationProfil();  //modification Profil
                        Toast.makeText(getActivity().getApplicationContext(), "vous avez modifié le nom", Toast.LENGTH_SHORT).show();
                        InputMethodManager mr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });


        lastName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    try {
                        modificationProfil();  //modification Profil

                        Toast.makeText(getActivity().getApplicationContext(), "vous avez modifié le prénom", Toast.LENGTH_SHORT).show();
                        InputMethodManager mr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onLoaderReset(Loader<Object> loader) {
    }


    public ListView getListView() {
        //return ListView
        return list;
    }


    /**
     * @param loaderId Initialisation of Loader Get Me (for recuparerate profil user)
     */
    private void initLoader(int loaderId) {
        if (getLoaderManager().getLoader(LOADER_GET_ME) == null) {
            getLoaderManager().initLoader(LOADER_GET_ME, null, ProfilFragment.this);
        } else {
            getLoaderManager().restartLoader(LOADER_GET_ME, null, ProfilFragment.this);
        }
    }


    public void modificationProfil() {
        /*
        *LOADER_UPDATE_NAME
        *method of recuperate modification of name and lastname users in your profil
         */
        Bundle bundle = new Bundle();
        bundle.putString(UpdateNameLoader.EMAIL, email);
        bundle.putString(UpdateNameLoader.SURNAME, lastName.getText().toString());
        bundle.putString(UpdateNameLoader.NAME, name.getText().toString());
        if (getLoaderManager().getLoader(LOADER_UPDATE_NAME) == null) {
            getLoaderManager().initLoader(LOADER_UPDATE_NAME, bundle, ProfilFragment.this);
        } else {
            getLoaderManager().restartLoader(LOADER_UPDATE_NAME, bundle, ProfilFragment.this);
        }
    }


    @Override
    public void onClick(View v) {
        //Change image if is clicked
        switch (v.getId()) {
            case R.id.image_users:

                /**
                 * Change picture if you click */
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PICTURE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * Retrives the result returned from selecting image, by invoking the method
         */
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK && null != data) {
            // Image captured and saved to fileUri specified in the Intent
            selectedImage = data.getData();
            imgPath = getPath(selectedImage);
            Log.e("DEBUG", "Choose: " + imgPath);

            new uploadPhoto();
        }
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        getActivity().startManagingCursor(cursor);    // manages Cursors, whereas the LoaderManager manages Loader<D> objects
        int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    class uploadPhoto extends AsyncTask<Void, Void, Void>

    {

        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath();

            String fileName = file_path;
            HttpURLConnection connection = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            //File sourceFile = new File(sourceFileUri);

          /*  if (!sourceFile.isFile()) {

                progressDialog.dismiss();

                Log.e("uploadFile", "Source File not exist :"
                        + imgPath + "" + selectedImage);

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getActivity(), "Source File not exist :\"\n" +
                                        "                            +uploadFilePath + \"\" + uploadFileName",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                return 0;

            } else {*/
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(new File(fileName));
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true); // Allow Inputs
                connection.setDoOutput(true); // Allow Outputs
                connection.setUseCaches(false); // Don't use a Cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", fileName);


                dos = new DataOutputStream(connection.getOutputStream());
                Log.e("Response", connection.getResponseCode() + "");


                dos.writeBytes(twoHyphens + boundary + lineEnd);
                // uploaded_file_name is the Name of the File to be uploaded
                dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

               /* // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                final String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {



                            Log.e("200","OK" );
                            Toast.makeText(getActivity(), "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
*/
                Bundle bundle = new Bundle();
                bundle.putString(UpdatePhotoLoader.PHOTO, String.valueOf(selectedImage));
                if (getLoaderManager().getLoader(LOADER_UPDATE_PHOTO) == null) {
                    getLoaderManager().initLoader(LOADER_UPDATE_PHOTO, bundle, ProfilFragment.this);
                } else {
                    getLoaderManager().restartLoader(LOADER_UPDATE_PHOTO, bundle, ProfilFragment.this);
                }
                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                Log.e("Debug", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {


                Log.e("Upload file to server Exception", "Exception : "
                        + e.getMessage(), e);
            }


            //------------------ read the SERVER RESPONSE
            try {
                DataInputStream stream = new DataInputStream(connection.getInputStream());
                String str;
                while ((str = stream.readLine()) != null) {
                    String reponse_data = str;
                }
                stream.close();
            } catch (IOException ioex) {
                Log.e("Debug", "error: " + ioex.getMessage(), ioex);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

}

