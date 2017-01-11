package com.example.kemo.socializer.View.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.kemo.socializer.Connections.CommandsExecutor;
import com.example.kemo.socializer.Connections.ConnectionListener;
import com.example.kemo.socializer.Connections.TransmissionFailureListener;
import com.example.kemo.socializer.Control.MainServerConnection;
import com.example.kemo.socializer.R;
import com.example.kemo.socializer.View.FragmentNavigator;
import com.example.kemo.socializer.View.IntentNavigator;
import com.example.kemo.socializer.View.ProfileActivity.ProfileActivity;

public class MainActivity extends AppCompatActivity implements FragmentNavigator , IntentNavigator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                   final MainServerConnection mainServerConnection = new MainServerConnection();
                    CommandsExecutor.getInstance().setOnTransmissionFailure(new TransmissionFailureListener() {
                        @Override
                        public void onDisconnection() {
                            mainServerConnection.endConnection();
                            mainServerConnection.reconnect();
                            CommandsExecutor.getInstance().updateSocket(mainServerConnection.connectionSocket);
                        }
                    });
                    mainServerConnection.setConnectionListener(new ConnectionListener() {
                        @Override
                        public void onStart() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MainActivity.this.findViewById(R.id.connecting_bar).setVisibility(View.VISIBLE);
                                }
                            });


                        }

                        @Override
                        public void onConnectionSuccess() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MainActivity.this.findViewById(R.id.connecting_bar).setVisibility(View.GONE);
                                    MainActivity.this.findViewById(R.id.connected_bar).setVisibility(View.VISIBLE);
                                }
                            });
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            MainActivity.this.findViewById(R.id.connected_bar).setVisibility(View.GONE);
                                        }
                                    });
                                }
                            }).start();
                        }
                    });
                    mainServerConnection.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        navigate(new RegisterFragment());
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

    @Override
    public void navigate(final Fragment fragment) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container,
                                fragment)
                        .commit();
            }
        });

    }
    @Override
    public void navigate(String extra) {
        Intent intent = new Intent(this, ProfileActivity.class).putExtra(Intent.EXTRA_TEXT,
                extra);
        startActivity(intent);
    }
}
