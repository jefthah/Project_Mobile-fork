package com.example.healthcare;

import android.util.Log;

import androidx.annotation.NonNull;

<<<<<<< HEAD
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private static final String TAG = "Database";
    private FirebaseAuth auth;
    private DatabaseReference database;

    public Database() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
    }

    public interface DatabaseCallback {
        void onSuccess();
        void onFailure(Exception e);
=======
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry1 = "create table users(username text, email text, password text)";
        sqLiteDatabase.execSQL(qry1);

        String qry2 = "create table cart(username text, product text, price float, otype text)";
        sqLiteDatabase.execSQL(qry2);

        String qry3 = "create table orderplace(username text, fullname text, address text, contactno text, pincode int, date text, time text, amount float, otype text)";
        sqLiteDatabase.execSQL(qry3);
>>>>>>> upstream/main
    }

    public interface DatabaseDataCallback {
        void onDataReceived(User user);
        void onFailure(Exception e);
    }

    public interface CartCallback {
        void onCartDataReceived(ArrayList<CartItem> cartData);
        void onFailure(Exception e);
    }

<<<<<<< HEAD
    public interface OrderDetailsCallback {
        void onOrderDataReceived(ArrayList<OrderDetail> orderDetails);
        void onFailure(Exception e);
    }

    // Register user
    public void register(String username, String email, String password, final DatabaseCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            User newUser = new User(username, email);
                            database.child("users").child(user.getUid()).setValue(newUser)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            callback.onSuccess();
                                        } else {
                                            callback.onFailure(task1.getException());
                                        }
                                    });
                        }
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Login user
    public void login(String email, String password, final DatabaseCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Update profile
    public void updateProfile(String newEmail, String newPassword, final DatabaseCallback callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.updateEmail(newEmail).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            callback.onSuccess();
                        } else {
                            callback.onFailure(task1.getException());
                        }
                    });
                } else {
                    callback.onFailure(task.getException());
                }
            });
=======
    public void addCart(String username, String product, float price, String otype) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("product", product);
        cv.put("price", price);
        cv.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart", null, cv);
        db.close();
    }

    public int checkCart(String username,String product) {
        int result=0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = product;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from cart where username = ? and product = ?",str);
        if(c.moveToFirst()) {
            result=1;
        }
        db.close();
        return  result;
    }

    public void removeCart(String username, String otype) {
        String str[] = new String[2];
        str[0] = username;
        str[1] = otype;
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart","username=? and otype=?",str);
        db.close();
    }

    public ArrayList<String> getCartData(String username, String otype){
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] str = new String[2];
        str[0] = username;
        str[1] = otype;
        Cursor c = db.rawQuery("select * from cart where username = ? and otype = ?",str);
        if(c.moveToFirst()){
            do{
                String product = c.getString(1);
                String price = c.getString(2);
                arr.add(product+" $"+price);
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return arr;
    }

    public void addOrder(String username, String fullname, String address, String contact, int pincode, String date, String time, float price, String otype){
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("fullname",fullname);
        cv.put("address", address);
        cv.put("contactno",contact);
        cv.put("pincode",pincode);
        cv.put("date",date);
        cv.put("time",time);
        cv.put("amount",price);
        cv.put("otype",otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("orderplace",null,cv);
        db.close();
    }

    public ArrayList getOrderData(String username){
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str[] = new String[1];
        str[0] = username;
        Cursor c = db.rawQuery("select * from orderplace where username = ?", str);
        if(c.moveToFirst()){
            do{
                arr.add(c.getString(1)+"$"+c.getString(2)+"$"+c.getString(3)+"$"+c.getString(4)+"$"+c.getString(5)+"$"+c.getString(6)+"$"+c.getString(7)+"$"+c.getString(8));
            }while (c.moveToNext());
        }
        db.close();
        return arr;
    }

    public int login(String username, String password){
        int result = 0;
        String str [] =new String[2];
        str [0] = username;
        str [1] = password;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from users where username=? and password=?", str);
        if(c.moveToFirst()){
            result=1;
        }
        return result;
    }

    public void updateProfile(String username, String newEmail, String newPassword) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("email", newEmail);
            cv.put("password", newPassword);
            db.update("users", cv, "username=?", new String[]{username});
            db.close();
        } catch (Exception e) {
            Log.e(TAG, "updateProfile: ", e);
>>>>>>> upstream/main
        }
    }

    // Delete account
    public void deleteAccount(final DatabaseCallback callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            database.child("users").child(user.getUid()).removeValue()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.delete().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    callback.onSuccess();
                                } else {
                                    callback.onFailure(task1.getException());
                                }
                            });
                        } else {
                            callback.onFailure(task.getException());
                        }
                    });
        }
    }

<<<<<<< HEAD
    // Get user data
    public void getUserData(DatabaseDataCallback callback) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = database.child("users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    callback.onDataReceived(user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onFailure(databaseError.toException());
                }
            });
        } else {
            callback.onFailure(new Exception("No user logged in"));
        }
    }

    // Add to cart
    public void addToCart(String username, String otype, CartItem item, final DatabaseCallback callback) {
        String encodedUsername = Utils.encodeUsername(username);
        DatabaseReference cartRef = database.child("cart").child(encodedUsername).child(otype).push();
        cartRef.setValue(item)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Get cart data
    public void getCartData(String username, String otype, final CartCallback callback) {
        String encodedUsername = Utils.encodeUsername(username);
        DatabaseReference cartRef = database.child("cart").child(encodedUsername).child(otype);
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CartItem> cartData = new ArrayList<>();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    CartItem cartItem = itemSnapshot.getValue(CartItem.class);
                    cartData.add(cartItem);
                }
                callback.onCartDataReceived(cartData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.toException());
            }
        });
    }

    // Remove from cart
    public void removeFromCart(String username, String otype, String itemId, final DatabaseCallback callback) {
        String encodedUsername = Utils.encodeUsername(username);
        DatabaseReference cartRef = database.child("cart").child(encodedUsername).child(otype).child(itemId);
        cartRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Add order
    public void addOrder(String username, OrderDetail order, final DatabaseCallback callback) {
        String encodedUsername = Utils.encodeUsername(username);
        DatabaseReference orderRef = database.child("orders").child(encodedUsername).push();
        orderRef.setValue(order)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Clear cart
    public void clearCart(String userId, DatabaseCallback callback) {
        String encodedUserId = Utils.encodeUsername(userId);
        DatabaseReference cartRef = database.child("carts").child(encodedUserId); // Ensure the correct path
        cartRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Get order details
    public void getOrderDetails(String username, final OrderDetailsCallback callback) {
        String encodedUsername = Utils.encodeUsername(username);
        DatabaseReference ordersRef = database.child("orders").child(encodedUsername);
        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<OrderDetail> orderDetails = new ArrayList<>();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    OrderDetail orderDetail = orderSnapshot.getValue(OrderDetail.class);
                    orderDetails.add(orderDetail);
                }
                callback.onOrderDataReceived(orderDetails);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onFailure(error.toException());
            }
        });
    }

    // Move cart to order and clear cart
    public void moveCartToOrder(String userId, ArrayList<HashMap<String, String>> cartItems, DatabaseCallback callback) {
        String encodedUserId = Utils.encodeUsername(userId);
        DatabaseReference orderRef = database.child("orders").child(encodedUserId).push();
        orderRef.setValue(cartItems)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        clearCart(encodedUserId, new DatabaseCallback() {
                            @Override
                            public void onSuccess() {
                                callback.onSuccess();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                callback.onFailure(e);
                            }
                        });
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }
}
=======
    public int checkAppointmentExists(String username, String fullname, String address, String contact, String date, String time){
        int result=0;
        String str[] = new String[6];
        str[0] = username;
        str[1] = fullname;
        str[2] = address;
        str[3] = contact;
        str[4] = date;
        str[5] = time;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from orderplace where username = ? and fullname = ? and address = ? and contactno = ? and date = ? and time = ?", str);
        if(c.moveToFirst()){
            result=1;
        }
        db.close();
        return result;
    }

}
>>>>>>> upstream/main
