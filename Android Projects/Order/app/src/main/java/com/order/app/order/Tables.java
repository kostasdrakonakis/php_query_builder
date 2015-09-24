package com.order.app.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Tables extends Activity {

    private Button one, two, three, four, five, six, seven, eight, nine, zero, clear, ok, delete,
    q, w, e, r, t, y, u, i, o, p, a, s, d, f, g, h, j, k, l, z, x, c, v, b, n, m;
    private EditText tableNumber;
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tables);
        tableNumber = (EditText)findViewById(R.id.tableNumberEditText);
        setupButtons();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        setupButtonListeners();
    }

    private void setupButtons() {
        one = (Button)findViewById(R.id.btn1);
        two = (Button)findViewById(R.id.btn2);
        three = (Button)findViewById(R.id.btn3);
        four = (Button)findViewById(R.id.btn4);
        five = (Button)findViewById(R.id.btn5);
        six = (Button)findViewById(R.id.btn6);
        seven = (Button)findViewById(R.id.btn7);
        eight = (Button)findViewById(R.id.btn8);
        nine = (Button)findViewById(R.id.btn9);
        zero = (Button)findViewById(R.id.btn0);
        clear = (Button)findViewById(R.id.btnClear);
        ok = (Button)findViewById(R.id.btnOk);
        q = (Button)findViewById(R.id.btnQ);
        w = (Button)findViewById(R.id.btnW);
        e = (Button)findViewById(R.id.btnE);
        r = (Button)findViewById(R.id.btnR);
        t = (Button)findViewById(R.id.btnT);
        y = (Button)findViewById(R.id.btnY);
        u = (Button)findViewById(R.id.btnU);
        i = (Button)findViewById(R.id.btnI);
        o = (Button)findViewById(R.id.btnO);
        p = (Button)findViewById(R.id.btnP);
        a = (Button)findViewById(R.id.btnA);
        s = (Button)findViewById(R.id.btnS);
        d = (Button)findViewById(R.id.btnD);
        f = (Button)findViewById(R.id.btnF);
        g = (Button)findViewById(R.id.btnG);
        h = (Button)findViewById(R.id.btnH);
        j = (Button)findViewById(R.id.btnJ);
        k = (Button)findViewById(R.id.btnK);
        l = (Button)findViewById(R.id.btnL);
        z = (Button)findViewById(R.id.btnZ);
        x = (Button)findViewById(R.id.btnX);
        c = (Button)findViewById(R.id.btnC);
        v = (Button)findViewById(R.id.btnV);
        b = (Button)findViewById(R.id.btnB);
        n = (Button)findViewById(R.id.btnN);
        m = (Button)findViewById(R.id.btnM);
        delete = (Button)findViewById(R.id.btnDel);
    }

    private void setupButtonListeners() {
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "1");
                vibrator.vibrate(28);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "2");
                vibrator.vibrate(28);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "3");
                vibrator.vibrate(28);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "4");
                vibrator.vibrate(28);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "5");
                vibrator.vibrate(28);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "6");
                vibrator.vibrate(28);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "7");
                vibrator.vibrate(28);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "8");
                vibrator.vibrate(28);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "9");
                vibrator.vibrate(28);
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "0");
                vibrator.vibrate(28);
            }
        });
        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "Q");
                vibrator.vibrate(28);
            }
        });
        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "W");
                vibrator.vibrate(28);
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "E");
                vibrator.vibrate(28);
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "R");
                vibrator.vibrate(28);
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "T");
                vibrator.vibrate(28);
            }
        });
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "Y");
                vibrator.vibrate(28);
            }
        });
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "U");
                vibrator.vibrate(28);
            }
        });
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "I");
                vibrator.vibrate(28);
            }
        });
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "O");
                vibrator.vibrate(28);
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "P");
                vibrator.vibrate(28);
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "A");
                vibrator.vibrate(28);
            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "S");
                vibrator.vibrate(28);
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "D");
                vibrator.vibrate(28);
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "F");
                vibrator.vibrate(28);
            }
        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "G");
                vibrator.vibrate(28);
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "H");
                vibrator.vibrate(28);
            }
        });
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "J");
                vibrator.vibrate(28);
            }
        });
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "K");
                vibrator.vibrate(28);
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "L");
                vibrator.vibrate(28);
            }
        });
        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "Z");
                vibrator.vibrate(28);
            }
        });
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "X");
                vibrator.vibrate(28);
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "C");
                vibrator.vibrate(28);
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "V");
                vibrator.vibrate(28);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "B");
                vibrator.vibrate(28);
            }
        });
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "N");
                vibrator.vibrate(28);
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                tableNumber.setText(textNow + "M");
                vibrator.vibrate(28);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableNumber.setText("");
                vibrator.vibrate(28);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                if (textNow.isEmpty() || equals(null)){
                    tableNumber.setText("");
                    vibrator.vibrate(28);
                }else {
                    tableNumber.setText(textNow.replace(textNow.substring(textNow.length() - 1), ""));
                    vibrator.vibrate(28);
                }

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNow = tableNumber.getText().toString();
                if (textNow.isEmpty()){
                    Toast.makeText(Tables.this, getString(R.string.tableNumEmptyMessage), Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(28);
                }else if (textNow.equals("0")){
                    Toast.makeText(Tables.this, getString(R.string.tableNumZeroMessage), Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(28);
                }else {
                    vibrator.vibrate(28);
                    Intent intent = new Intent(Tables.this, ProductsViewOrder.class);
                    intent.putExtra("tableID", textNow);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    Tables.this.finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
    }
}
