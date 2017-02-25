package com.whatrecipes.whatrecipes.data.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whatrecipes.whatrecipes.data.Recipe;
import com.whatrecipes.whatrecipes.data.common.FirebaseConstants;
import com.whatrecipes.whatrecipes.data.firebase.listeners.RequestListener;

/**
 * Created by fatal on 2/19/2017.
 */
public class FirebaseDatabaseInteractor implements IFirebaseDatabaseInteractor {
    public final FirebaseDatabase firebaseDatabase;


    public FirebaseDatabaseInteractor(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase=firebaseDatabase;
    }

    @Override
    public void requestFromValidLink(final RequestListener<Recipe> listener) {
        firebaseDatabase.getReference(FirebaseConstants.RECIPE_REFERENCE).child(FirebaseConstants.TEST_LOCATION).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Recipe model = dataSnapshot.getValue(Recipe.class);
                if (model != null) {
                    listener.onSuccessfulRequest(model); //successful parse
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void requestFromInvalidLink(final RequestListener<Recipe> listener) {
        firebaseDatabase.getReference(FirebaseConstants.USER_REFERENCE).child(FirebaseConstants.TEST_LOCATION).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Recipe model = dataSnapshot.getValue(Recipe.class);
                listener.onSuccessfulRequest(model);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void pushRecipe(Recipe recipe) {

        firebaseDatabase.getReference(FirebaseConstants.RECIPE_REFERENCE).push().setValue(recipe);
    }

    @Override
    public void getAllRecipes(ChildEventListener listener) {
       firebaseDatabase.getReference(FirebaseConstants.RECIPE_REFERENCE).addChildEventListener(listener);
    }
}
