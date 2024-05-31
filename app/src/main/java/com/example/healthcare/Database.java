package com.example.healthcare;

import android.util.Log;

import androidx.annotation.NonNull;

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
    }

    public interface DatabaseDataCallback {
        void onDataReceived(User user);
        void onFailure(Exception e);
    }

    public interface CartCallback {
        void onCartDataReceived(ArrayList<CartItem> cartData);
        void onFailure(Exception e);
    }

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
