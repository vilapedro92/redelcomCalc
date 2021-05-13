package com.redelcom.myapplicationdraw;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private EditText display;
    private Button pay;
    private Button add;
    private ImageButton sheetDialog;
    private String totalPay = "0";
    private ArrayList<Integer> valuesList = new ArrayList<Integer>();
    private int sum = 0;
    private boolean isActive = false;
    private BottomSheetDialog bottomSheetDialog;
    private Bundle bundleSendArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sheetDialog = findViewById(R.id.historyBTN);
        display = findViewById(R.id.input);
        add = findViewById(R.id.addBTN);
        pay = findViewById(R.id.paymentBTN);
        pay.setText(pay.getText().toString().concat(totalPay));
        display.setShowSoftInputOnFocus(false);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getString(R.string.display).equals(display.getText().toString())) {
                    display.setText("");
                }
            }
        });
        sheetDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHistory mdh = new DialogHistory();
                bundleSendArray = new Bundle();
                bundleSendArray.putIntegerArrayList("valueList", valuesList);
                bundleSendArray.putString("prueba", "prueba");
                mdh.setArguments(bundleSendArray);
                mdh.show(getFragmentManager(), "Hitorial");
            }
        });

        try {
            Intent intent = getIntent();
            String action = intent.getAction();
            View parentLayout = findViewById(android.R.id.content);
            if (action != null && Intent.ACTION_SEND.equals(action)) {
//                Toast.makeText(getApplicationContext(), "Pago Realizado", Toast.LENGTH_LONG).show();
//                verificar la respuesta del rdc pass
                Snackbar.make(parentLayout, "Pago Realizado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void updateText(String strToAdd) {
        String oldStr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftSrt = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);
        if (getString(R.string.display).equals(display.getText().toString())) {
            display.setText(strToAdd);
            display.setSelection(cursorPos + 1);
        } else {
            display.setText(String.format("%s%s%s", leftSrt, strToAdd, rightStr));
            display.setSelection(cursorPos + 1);
        }
    }

    public void backSpaceBTN(View view) {
        int cursorPos = display.getSelectionStart();
        int textLen = display.getText().length();
        if (cursorPos != 0 && textLen != 0) {
            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPos - 1, cursorPos, "");
            display.setText(selection);
            display.setSelection(cursorPos - 1);
        }
    }

    //    en desuso...
    public void equalsBTN(View view) {
        String userExp = display.getText().toString();
        userExp = userExp.replaceAll("÷", "/");
        userExp = userExp.replaceAll("×", "*");
        Expression exp = new Expression(userExp);
        String result = String.valueOf(exp.calculate()).split("\\.")[0];
        display.setText(result);
        display.setSelection(result.length());
        pay.setText("COBRAR $ ".concat(result));
        totalPay = result;
    }

    public void paymentBTN(View view) {
        try {
            String total = totalPay;
            if (totalPay != "" && totalPay != "0.0" && totalPay != "0") {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setClassName("com.example.rdcpassreceptor", "com.example.rdcpassreceptor.MainActivity");
                sharingIntent.putExtra("packageName", getPackageName());
                sharingIntent.putExtra("className", getClass().toString().split(" ")[1]);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, total);
                startActivity(sharingIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Debe especificar un monto", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void zeroBTN(View view) {
        if (isActive) {
            cleanEdit();
            isActive = false;
            add.setSelected(false);
        }
        updateText("0");
    }

    public void oneBTN(View view) {
        if (isActive) {
            cleanEdit();
            isActive = false;
            add.setSelected(false);
        }
        updateText("1");
    }

    public void twoBTN(View view) {
        if (isActive) {
            cleanEdit();
            isActive = false;
            add.setSelected(false);
        }
        updateText("2");
    }

    public void threeBTN(View view) {
        if (isActive) {
            cleanEdit();
            isActive = false;
            add.setSelected(false);
        }
        updateText("3");
    }

    public void fourBTN(View view) {
        if (isActive) {
            cleanEdit();
            isActive = false;
            add.setSelected(false);
        }
        updateText("4");
    }

    public void fiveBTN(View view) {
        if (isActive) {
            cleanEdit();
            isActive = false;
            add.setSelected(false);
        }
        updateText("5");
    }

    public void sixBTN(View view) {
        if (isActive) {
            cleanEdit();
            isActive = false;
            add.setSelected(false);
        }
        updateText("6");
    }

    public void sevenBTN(View view) {
        if (isActive) {
            cleanEdit();
            isActive = false;
            add.setSelected(false);
        }
        updateText("7");
    }

    public void eightBTN(View view) {
        if (isActive) {
            cleanEdit();
            isActive = false;
            add.setSelected(false);
        }
        updateText("8");
    }

    public void nineBTN(View view) {
        if (isActive) {
            cleanEdit();
            isActive = false;
            add.setSelected(false);
        }
        updateText("9");
    }

    public void multiplyBTN(View view) {
        updateText("×");
    }

    public void addBTN(View view) {
        if (display.getText().toString().length() != 0) {
            isActive = true;
            add.setSelected(true);
            valuesList.add(Integer.parseInt(display.getText().toString()));
            if (valuesList.size() > 1) {
                int s = valuesList.stream().mapToInt(element -> (int) element).sum();
                sum = s;
                Log.d("Suma", String.valueOf(sum));
                String result = String.valueOf(sum);
                display.setText(result);
                display.setSelection(result.length());
                pay.setText("COBRAR $ ".concat(result));
                totalPay = result;
            } else {
                String result = display.getText().toString();
                display.setText(result);
                display.setSelection(result.length());
                pay.setText("COBRAR $ ".concat(result));
                totalPay = result;
            }
        }

    }

    public void cleanRegisters(View view) {
        display.getText().clear();
        valuesList.clear();
        String result = display.getText().toString();
        pay.setText("COBRAR $ ".concat(result));
        totalPay = result;
        Toast.makeText(getApplicationContext(), "Ha eliminado todos los registros", Toast.LENGTH_LONG).show();
    }

    public void cleanEdit() {
        display.getText().clear();
    }

    public void deleteElementOfList(View view) {
        Toast.makeText(getApplicationContext(), "Aqui se eliminara de la lista....", Toast.LENGTH_LONG).show();
    }
}