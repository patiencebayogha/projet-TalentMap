package com.viseo.talentmap.lite.profil.loader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.lite.profil.manager.ProfilManager;

/**
 * Created by patiencebayogha on 23/04/15.
 */
public class UpdatePhotoLoader extends AbstractLoader<Boolean> {


        //variables declaration

        //Used to render a login field for a String property.


        public static final String PHOTO = "photo";


        //for call LoginManager.java
        protected ProfilManager profilManager;
        //declare Bundle
        private Bundle bundle;


        public UpdatePhotoLoader(Activity myActivity, Bundle bundle) {


            super(myActivity);
            this.profilManager = new ProfilManager();
            this.bundle = bundle;
        }


        @Override
        public Boolean loadInBackground() {


            //Called on a thread to perform the actual load and return a result of load operation
            String photo = bundle.getString(PHOTO);

            try {
                result = profilManager.updatePhoto(photo);
            } catch (TalentMapException e) {

                Log.e(getClass().getCanonicalName(), "error during loading: DeleteSkillLoader.");
            }

            return result;
        }



}
