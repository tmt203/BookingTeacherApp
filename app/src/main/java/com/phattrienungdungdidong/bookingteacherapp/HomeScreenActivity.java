package com.phattrienungdungdidong.bookingteacherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.phattrienungdungdidong.bookingteacherapp.Fragments.BookFragment;
import com.phattrienungdungdidong.bookingteacherapp.Fragments.ChatFragment;
import com.phattrienungdungdidong.bookingteacherapp.Fragments.FavoriteFragment;
import com.phattrienungdungdidong.bookingteacherapp.Fragments.NewsFragment;
import com.phattrienungdungdidong.bookingteacherapp.Fragments.SettingFragment;

public class HomeScreenActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NewsFragment newsFragment = new NewsFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();
    BookFragment bookFragment = new BookFragment();
    ChatFragment chatFragment = new ChatFragment();
    SettingFragment settingFragment = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, newsFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, newsFragment).commit();
                        return true;
                    case R.id.favorite:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, favoriteFragment).commit();
                        return true;
                    case R.id.book:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, bookFragment).commit();
                        return true;
                    case R.id.chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, chatFragment).commit();
                        return true;
                    case R.id.setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, settingFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}