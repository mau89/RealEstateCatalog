package com.example.realestatecatalog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText username;
    private EditText password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        setHasOptionsMenu(true);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);

        view.findViewById(R.id.login).setOnClickListener(v -> mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getActivity(), "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getFragmentManager()).beginTransaction()
                        .replace(R.id.container, new MainFragment())
                        .addToBackStack(LoginFragment.class.getName())
                        .commit();
            } else
                Toast.makeText(getActivity(), "Aвторизация не удалась", Toast.LENGTH_SHORT).show();

        }));
        view.findViewById(R.id.registration).setOnClickListener(v -> mAuth.createUserWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getActivity(), "Регистрация успешна", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getActivity(), "Регистрация не удалась", Toast.LENGTH_SHORT).show();
        }));
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_edit_property).setVisible(false);
        menu.findItem(R.id.action_add_property).setVisible(false);
        menu.findItem(R.id.action_exit).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

}
