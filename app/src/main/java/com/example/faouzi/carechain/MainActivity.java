package com.example.faouzi.carechain;




import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    LinearLayout bt_scan_prescription,bt_scan;
    //private Button bt_scan;
    //private Button bt_scan_prescription;
    private int prescription = 0;
    private int identification = 0;
    private int isprescription=0;
    private String identif="";
    private String prescrip="";

    BufferedReader reader =null;
    String response = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity = this;
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.launcher);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        // INSURANCE CARD

        bt_scan= (LinearLayout) findViewById(R.id.layout_dossier);
        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan Card");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                identification=1;
                isprescription=0;
            }

        });

        // PRESCRIPTION

        bt_scan_prescription= (LinearLayout) findViewById(R.id.layout_prelevements);
        bt_scan_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan Prescription");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                prescription=1;
                isprescription=1;

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents()==null) {
                Toast.makeText(this,"Scanning aborted",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                if (isprescription==1) prescrip=result.getContents();
                else identif=result.getContents();

                if (prescription==1 && identification==1){


                    ScrollView scr = (ScrollView) findViewById(R.id.scr);
                    scr.setVisibility(View.GONE);
                    WebView web = (WebView) findViewById(R.id.web);
                    web.setVisibility(View.VISIBLE);
                    web.loadUrl("http://192.33.203.60:8080/update.html?id="+prescrip);
//                    HttpURLConnection client = null;
//                    try {
//                        URL url = new URL("http://www/somewebsite.com/api/patient");
//                        client = (HttpURLConnection) url.openConnection();
//                        client.setRequestMethod("POST");
//                        client.setRequestProperty("patient_addr",identif);
//                        client.setRequestProperty("prescription_addr",prescrip);
//                        client.setDoOutput(true);
//
//                        OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
//                        outputPost.flush();
//                        outputPost.close();
//                        client.setChunkedStreamingMode(0);
//
//                        // get response
//                        InputStream responseStream = new BufferedInputStream(con.getInputStream());
//                        BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
//                        String line = "";
//                        StringBuilder stringBuilder = new StringBuilder();
//                        while ((line = responseStreamReader.readLine()) != null) {
//                            stringBuilder.append(line);
//                        }
//                        responseStreamReader.close();
//
//                        String response = stringBuilder.toString();
//                        JSONObject jsonResponse = new JSONObject(response);
//
//                        /*
//                        int responseCode = client.getResponseCode();
//                        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                        StringBuilder sb = new StringBuilder();
//                        String line = null;
//
//                        while ((line = reader.readLine()) != null) {
//                            sb.append(line + "\n");
//                        }
//
//                        response = sb.toString();
//                        Toast.makeText(this,response,Toast.LENGTH_LONG);*/
//
//                    } catch(MalformedURLException error) {
//                        Toast.makeText(this,"Handles an incorrectly entered UR",Toast.LENGTH_LONG).show();
//                    }
//                    catch(SocketTimeoutException error) {
//                        Toast.makeText(this,"Handles URL access timeout",Toast.LENGTH_LONG).show();
//                    }
//                    catch (IOException error) {
//                        Toast.makeText(this,"Handles input and output errors",Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } finally {
//                        if(client != null)
//                            client.disconnect();
//                    }

                }
//                else {
//
//                    HttpURLConnection client = null;
//                    try {
//                        URL url = new URL("http://www/somewebsite.com/api/prescription_addr");
//                        client = (HttpURLConnection) url.openConnection();
//                        client.setRequestMethod("POST");
//                        client.setRequestProperty("prescription_addr",result.getContents());
//                        client.setDoOutput(true);
//
//                        OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
//                        outputPost.flush();
//                        outputPost.close();
//                        client.setChunkedStreamingMode(0);
//                        int responseCode = client.getResponseCode();
//                        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                        StringBuilder sb = new StringBuilder();
//                        String line = null;
//
//                        while ((line = reader.readLine()) != null) {
//                            sb.append(line + "\n");
//                        }
//
//                        response = sb.toString();
//                        Toast.makeText(this,response,Toast.LENGTH_LONG);
//
//                    } catch(MalformedURLException error) {
//                        Toast.makeText(this,"Handles an incorrectly entered UR",Toast.LENGTH_LONG).show();
//                    }
//                    catch(SocketTimeoutException error) {
//                        Toast.makeText(this,"Handles URL access timeout",Toast.LENGTH_LONG).show();
//                    }
//                    catch (IOException error) {
//                        Toast.makeText(this,"Handles input and output errors",Toast.LENGTH_LONG).show();
//                    }finally {
//                        if(client != null)
//                            client.disconnect();
//                    }
//
//                }
            }
        }
        else {
            super.onActivityResult(requestCode,resultCode,data);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
