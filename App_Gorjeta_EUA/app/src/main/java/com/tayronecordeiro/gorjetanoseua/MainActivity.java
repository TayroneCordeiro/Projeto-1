package com.tayronecordeiro.gorjetanoseua;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextValor;
    private TextView textResultado;
    private RadioGroup radioGroupAtendimento;
    private TextView texGorjeta;
    private RadioButton rbExcelente, rbMuitoBom, rbRazoavel, rbRuim, rbHorrivel;
    private double porcentagem = 0;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextValor = findViewById(R.id.editTextValor);
        textResultado = findViewById(R.id.textResultado);
        radioGroupAtendimento = findViewById(R.id.radioGroupAtendimento);
        texGorjeta = findViewById(R.id.textGorjeta);
        rbExcelente = findViewById(R.id.rbExcelente);
        rbMuitoBom = findViewById(R.id.rbMuitoBom);
        rbRazoavel = findViewById(R.id.rbRazoavel);
        rbRuim = findViewById(R.id.rbRuim);
        rbHorrivel = findViewById(R.id.rbHorrivel);

        //propaganda
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(this, "ca-app-pub-7989714842235622~1506996325");



        texGorjeta.setVisibility(View.GONE);
        textResultado.setVisibility(View.GONE);

        //exibir teclado na abertura da activity
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        valorRadioButton();

    }

//------------------------ VALOR DE CADA RADIO BUTTON -----------------------------

    public void valorRadioButton() {
        radioGroupAtendimento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //ocutar teclado
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(editTextValor.getWindowToken(), 0);

                //atribuir valor para cada radio Button
                if (i == R.id.rbExcelente) {
                    porcentagem = 20;
                } else if (i == R.id.rbMuitoBom) {
                    porcentagem = 18;
                } else if (i == R.id.rbRazoavel) {
                    porcentagem = 15;
                } else if (i == R.id.rbRuim) {
                    porcentagem = 13;
                } else {
                    porcentagem = 10;
                }
            }
        });
    }

//------------------------ BOTAO CALCULAR -----------------------------

    public void btCalcular(View view) {

        //ocultar teclado
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editTextValor.getWindowToken(), 0);

        //recuperar valores digitados
        String valorConta = editTextValor.getText().toString();

        //validar os campos digitados e selecionados
        Boolean campoValorValidado = this.validarCampoValor(valorConta);

        if (campoValorValidado) {
            if (rbExcelente.isChecked() || rbMuitoBom.isChecked() || rbRazoavel.isChecked() || rbRuim.isChecked() || rbHorrivel.isChecked()) {
                calculo();
            } else {
                Toast.makeText(getApplicationContext(), "Escolha uma opção de atendimento", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Preencha o campo valor da conta", Toast.LENGTH_LONG).show();
        }
    }

//------------------------ VERIFICAR CAMPO VALOR -----------------------------

    public Boolean validarCampoValor(String vConta) {
        Boolean campoValidado = true;

        //validar campo
        if (vConta == null || vConta.equals("")) {
            campoValidado = false;
        }
        return campoValidado;
    }

//------------------------ CALCULO -----------------------------

    public void calculo() {
        //Recuperar valor digitado
        Double valorDigitado = Double.parseDouble(editTextValor.getText().toString());

        //calcular a gorjeta
        Double valorGorjeta = valorDigitado * (porcentagem / 100);

        //formatando casas decimais
        DecimalFormat formato = new DecimalFormat("0.00");

        //exibe o valor da gorjeta
        textResultado.setText("$ " + formato.format(valorGorjeta));

        //exibir os textos que estavam ocultos
        texGorjeta.setVisibility(View.VISIBLE);
        textResultado.setVisibility(View.VISIBLE);
    }
}

