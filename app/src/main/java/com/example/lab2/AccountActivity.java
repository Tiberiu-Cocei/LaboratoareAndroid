package com.example.lab2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AccountActivity extends AppCompatActivity {

    private TextView TextToSave;
    private TextView TextToLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        TextToSave = findViewById(R.id.saveText);
        TextToLoad = findViewById(R.id.loadText);

        //loading -> read from file
        Context context = this;
        try(FileInputStream fis = context.openFileInput("test_file")) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (Exception e) {
            } finally {
                TextToLoad.setText(stringBuilder.toString());
            }
        }
        catch(Exception e) { }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.catalog:
                openCatalogMenu();
                return true;
            case R.id.cart:
                openCartMenu();
                return true;
            case R.id.account:
                return true;
            case R.id.settings:
                openSettingsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openSettingsMenu() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openCatalogMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openCartMenu() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void intentFilterTest(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void alertDialogTest(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(AccountActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Login failed - not implemented");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void saveTextTest(View view) {
        //saving -> write to file
        Context context = this;
        String saveText = TextToSave.getText().toString();
        String filename = "test_file";
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(saveText.getBytes());
        } catch(Exception e) {}

        //loading -> read from file
        try(FileInputStream fis = context.openFileInput("test_file")) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (Exception e) {
            } finally {
                TextToLoad.setText(stringBuilder.toString());
            }
        }
        catch(Exception e) { }
    }
}
