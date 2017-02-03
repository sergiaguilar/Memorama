package com.example.sergiaguilaranguera.memorama;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by sergi.aguilar.anguera on 2/3/17.
 */
public class memorama extends Activity{

    private static final int[] CARTA_RESOURCES = new int[] {
            R.drawable.mec,
            R.drawable.rajoy,
            R.drawable.lapayude,
            R.drawable.image11s,
            R.drawable.images,
            R.drawable.images22,
            R.drawable.terstegen,
            R.drawable.sergi
    };

    private final Handler handler = new Handler();
    private Carta[] cartas;
    private boolean touchActivo = true;
    private Carta visible = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TableLayout tabla = new TableLayout(this);
        final int tam = 4;
        cartas = crearCeldas(tam*tam);
        Collections.shuffle(Arrays.asList(cartas));
        for (int y = 0; y < tam; y++) {
            final TableRow fila = new TableRow(this);
            for (int x = 0; x < tam; x++) {
                fila.addView(cartas[(y*tam)+x].boton);
            }
            tabla.addView(fila);
        }
        setContentView(tabla);
    }

    private class Carta implements View.OnClickListener {
        private final ImageButton boton;
        private final int imagen;
        private boolean caraVisible = false;
        Carta(final int imagen) {
            this.imagen = imagen;
            this.boton = new ImageButton(memorama.this);
            this.boton.setLayoutParams(new TableRow.LayoutParams(64, 64));
            this.boton.setScaleType(ImageView.ScaleType.FIT_XY);
            this.boton.setImageResource(R.drawable.linea);
            this.boton.setOnClickListener(this);
        }

        void setCaraVisible(final boolean caraVisible) {
            this.caraVisible = caraVisible;
            boton.setImageResource(caraVisible? imagen : R.drawable.linea);
        }

        public void onClick(View arg0) {
            if(!caraVisible && touchActivo) {
                onCartaDescubierta(this);
            }
        }
    }

    private Carta[] crearCeldas(final int cont) {
        final Carta[] array = new Carta[cont];
        for (int i = 0; i < cont; i++) {
            array[i] = new Carta(CARTA_RESOURCES[i/2]);

        }
        return array;
    }

    public void onCartaDescubierta(final Carta celda) {
        if(visible == null) {
            visible = celda;
            visible.setCaraVisible(true);
        }
        else if (visible.imagen == celda.imagen) {
            celda.setCaraVisible(true);
            celda.boton.setEnabled(false);
            visible.boton.setEnabled(false);
            visible = null;
        }
        else {
            celda.setCaraVisible(true);
            touchActivo = false;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    celda.setCaraVisible(false);
                    visible.setCaraVisible(false);
                    visible = null;
                    touchActivo = true;
                                    }
            }, 1000);
        }
    }
}
