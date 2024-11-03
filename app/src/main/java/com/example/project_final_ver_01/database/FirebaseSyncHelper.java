package com.example.project_final_ver_01.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_final_ver_01.database.entities.User;
import com.example.project_final_ver_01.database.entities.UserYogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaClassInstance;
import com.example.project_final_ver_01.database.entities.YogaCourse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class FirebaseSyncHelper {
    private final DatabaseHelper db;
    private final DatabaseReference firebaseDb;

    public FirebaseSyncHelper(DatabaseHelper db, DatabaseReference firebaseDb) {
        this.db = db;
        this.firebaseDb = firebaseDb;
    }
    //User
    public void createUserToFirebase(){
        List<User> userList = db.getAllUser();
        for(User user: userList) {
            firebaseDb.child(User.TABLE_NAME).child(String.valueOf(user.getId())).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(!task.isSuccessful()) return;
                    if(task.getResult().exists()) return;
                    firebaseDb.child(User.TABLE_NAME).child(String.valueOf(user.getId())).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }
            });
        }
    }
    public void updateUserToFirebase(int id, User user){
        firebaseDb.child(User.TABLE_NAME).child(String.valueOf(id)).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
    public void deleteUserToFirebase(int id){
        firebaseDb.child(User.TABLE_NAME).child(String.valueOf(id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    //Yoga class
    public void createYogaClassInstanceToFirebase(){
        List<YogaClassInstance> yogaClassInstanceList = db.getAllYogaClassInstance();
        for(YogaClassInstance yogaClassInstance: yogaClassInstanceList) {
            firebaseDb.child(YogaClassInstance.TABLE_NAME).child(String.valueOf(yogaClassInstance.getYoga_class_instance_id())).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(!task.isSuccessful()) return;
                    if(task.getResult().exists()) return;
                    firebaseDb.child(YogaClassInstance.TABLE_NAME).child(String.valueOf(yogaClassInstance.getYoga_class_instance_id())).setValue(yogaClassInstance).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            });
        }
    }
    public void updateYogaClassInstanceToFirebase(int id, YogaClassInstance yogaClassInstance){
        firebaseDb.child(YogaClassInstance.TABLE_NAME).child(String.valueOf(id)).setValue(yogaClassInstance).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
    public void deleteYogaClassInstanceToFirebase(int id){
        firebaseDb.child(YogaClassInstance.TABLE_NAME).child(String.valueOf(id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    //Yoga course
    public void createYogaCourseToFirebase(){
        List<YogaCourse> yogaCourseList = db.getALlYogaCourse();
        for (YogaCourse yogaCourse: yogaCourseList) {
            firebaseDb.child(YogaCourse.TABLE_NAME).child(String.valueOf(yogaCourse.getId())).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(!task.isSuccessful()) return;
                    if(task.getResult().exists()) return;
                    firebaseDb.child(YogaCourse.TABLE_NAME).child(String.valueOf(yogaCourse.getId())).setValue(yogaCourse).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            });
        }
    }
    public void updateYogaCourseToFirebase(int id, YogaCourse yogaCourse){
        firebaseDb.child(YogaCourse.TABLE_NAME).child(String.valueOf(id)).setValue(yogaCourse).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
    public void deleteYogaCourseToFirebase(int id){
        firebaseDb.child(YogaCourse.TABLE_NAME).child(String.valueOf(id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    //User yoga class
    public void createUserYogaClassInstanceToFirebase(){
        List<UserYogaClassInstance> userYogaClassInstanceList = db.getAllUserYogaClassInstance();
        for(UserYogaClassInstance userYogaClassInstance: userYogaClassInstanceList) {
            firebaseDb.child(UserYogaClassInstance.TABLE_NAME).child(String.valueOf(userYogaClassInstance.getYoga_class_instance_id())).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(!task.isSuccessful()) return;
                    if(task.getResult().exists()) return;
                    firebaseDb.child(UserYogaClassInstance.TABLE_NAME).child(String.valueOf(userYogaClassInstance.getYoga_class_instance_id())).setValue(userYogaClassInstance).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            });
        }
    }
    public void updateUserYogaClassInstanceToFirebase(int id, UserYogaClassInstance userYogaClassInstance){
        firebaseDb.child(UserYogaClassInstance.TABLE_NAME).child(String.valueOf(id)).setValue(userYogaClassInstance).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
    public void deleteUserYogaClassInstanceToFirebase(int id){
        firebaseDb.child(UserYogaClassInstance.TABLE_NAME).child(String.valueOf(id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }


    //Sync and delete data
    public void pushDataSQLiteToFirebase(){
        deleteAllDataFromFirebase();
        List<User> userList = db.getAllUser();
        List<YogaClassInstance> yogaClassInstanceList = db.getAllYogaClassInstance();
        List<YogaCourse> yogaCourseList = db.getALlYogaCourse();
        List<UserYogaClassInstance> userYogaClassInstanceList = db.getAllUserYogaClassInstance();
        for (User user: userList) {
            firebaseDb.child(User.TABLE_NAME).child(String.valueOf(user.getId())).setValue(user);
        }
        for(YogaClassInstance yogaClassInstance: yogaClassInstanceList) {
            firebaseDb.child(YogaClassInstance.TABLE_NAME).child(String.valueOf(yogaClassInstance.getYoga_class_instance_id())).setValue(yogaClassInstance);
        }
        for(YogaCourse yogaCourse: yogaCourseList) {
            firebaseDb.child(YogaCourse.TABLE_NAME).child(String.valueOf(yogaCourse.getId())).setValue(yogaCourse);
        }
        for(UserYogaClassInstance userYogaClassInstance: userYogaClassInstanceList) {
            firebaseDb.child(UserYogaClassInstance.TABLE_NAME).child(String.valueOf(userYogaClassInstance.getYoga_class_instance_id())).setValue(userYogaClassInstance);
        }
    }
    public void deleteAllDataFromFirebase(){
        firebaseDb.removeValue();
    }
//    public void pullDataFirebaseToSQLite(){
//        firebaseDb.child(User.TABLE_NAME).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) return;
//                DataSnapshot dataSnapshot = task.getResult();
//                if(!dataSnapshot.exists()) return;
//                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
//                    int userId = Integer.parseInt(userSnapshot.getKey());
//                    String email = userSnapshot.child(User.COLUMN_EMAIL).getValue(String.class);
//                    String role = userSnapshot.child(User.COLUMN_ROLE).getValue(String.class);
//                    String phone_number = userSnapshot.child(User.COLUMN_PHONE_NUMBER).getValue(String.class);
//                    String user_name = userSnapshot.child(User.COLUMN_NAME).getValue(String.class);
//                    String description = userSnapshot.child(User.COLUMN_DESCRIPTION).getValue(String.class);
//                    db.addOrUpdateUser(new User(userId, email,role,phone_number, user_name, description));
//                }
//            }
//        });
//    }
}
