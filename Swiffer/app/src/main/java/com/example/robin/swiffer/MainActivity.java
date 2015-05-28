package com.example.robin.swiffer;

import android.graphics.Color;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MainActivity extends ActionBarActivity {

    protected Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout myLayout =
                (RelativeLayout)findViewById(R.id.ID);

        myLayout.setOnTouchListener(
                new RelativeLayout.OnTouchListener() {
                    public boolean onTouch(View v,
                                           MotionEvent m) {
                        handleTouch(m);
                        return true;
                    }
                }
        );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public void handleTouch(MotionEvent m)
    {
        TextView textView1 = (TextView)findViewById(R.id.textView1);
        TextView textView2 = (TextView)findViewById(R.id.textView2);

        int pointerCount = m.getPointerCount();

        for (int i = 0; i < pointerCount; i++)
        {
            int x = (int) m.getX(i);
            int y = (int) m.getY(i);
            int id = m.getPointerId(i);
            int action = m.getActionMasked();
            int actionIndex = m.getActionIndex();
            String actionString;


            try {
                if (x<800)
                    writeSocket(new byte[]{'L', (byte) (x/4)});
                else
                    writeSocket(new byte[]{'R', (byte) (y/4)}); // y is between 800 and 0
            } catch(Exception ex)
            {
                TextView tv = (TextView) findViewById(R.id.textView_debug);
                tv.setText("Unable to write on socket !\n" + ex.toString());
            }

            switch (action)
            {
                case MotionEvent.ACTION_DOWN:
                    actionString = "DOWN";
                    break;
                case MotionEvent.ACTION_UP:
                    actionString = "UP";
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    actionString = "PNTR DOWN";
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    actionString = "PNTR UP";
                    break;
                case MotionEvent.ACTION_MOVE:
                    actionString = "MOVE";
                    break;
                default:
                    actionString = "";
            }

            String touchStatus = "Action: " + actionString + " Index: " + actionIndex + " ID: " + id + " X: " + x + " Y: " + y;

            if (id == 0)
                textView1.setText(touchStatus);
            else
                textView2.setText(touchStatus);
        }
    }

    public void openSocket(View view) {
        TextView tv = (TextView) findViewById(R.id.textView_debug);

        EditText editText = (EditText) findViewById(R.id.edit_ip_adress);
        String ip_address = editText.getText().toString();

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(ip_address), 22188));
            tv.setText("Socket opened on " + ip_address + " !");
        } catch(Exception ex)
        {
            tv.setText("Unable to open socket on " + ip_address + " !\n" + ex.toString());
        }
    }

    private void writeSocket(byte[] b) {
        try {
            socket.getOutputStream().write(b);
            TextView tv = (TextView) findViewById(R.id.textView_debug);
            tv.setText(":)");
        } catch(NullPointerException | IOException | NetworkOnMainThreadException ex)
        {
            TextView tv = (TextView) findViewById(R.id.textView_debug);
            tv.setText("Unable to write to the socket !" + ex.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
