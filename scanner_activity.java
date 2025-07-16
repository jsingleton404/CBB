package com.cornerstone.buildingbrands;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    
    private static final int CAMERA_PERMISSION_REQUEST = 100;
    private ZXingScannerView scannerView;
    private LinearLayout contentLayout;
    private TextView tvScanStatus;
    private Button btnStartScan;
    private LinearLayout scanResultLayout;
    private TextView tvLine, tvAqNumber, tvImo, tvStatus, tvLocation, tvShippingDate;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        
        initializeViews();
        setupClickListeners();
        checkCameraPermission();
    }
    
    private void initializeViews() {
        contentLayout = findViewById(R.id.content_layout);
        tvScanStatus = findViewById(R.id.tv_scan_status);
        btnStartScan = findViewById(R.id.btn_start_scan);
        scanResultLayout = findViewById(R.id.scan_result_layout);
        tvLine = findViewById(R.id.tv_line);
        tvAqNumber = findViewById(R.id.tv_aq_number);
        tvImo = findViewById(R.id.tv_imo);
        tvStatus = findViewById(R.id.tv_status);
        tvLocation = findViewById(R.id.tv_location);
        tvShippingDate = findViewById(R.id.tv_shipping_date);
        
        // Initially hide scan results
        scanResultLayout.setVisibility(View.GONE);
    }
    
    private void setupClickListeners() {
        btnStartScan.setOnClickListener(v -> startScanning());
        
        findViewById(R.id.btn_back).setOnClickListener(v -> {
            onBackPressed();
        });
    }
    
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        }
    }
    
    private void startScanning() {
        if (scannerView == null) {
            scannerView = new ZXingScannerView(this);
            contentLayout.addView(scannerView);
        }
        
        scannerView.setResultHandler(this);
        scannerView.startCamera();
        
        btnStartScan.setVisibility(View.GONE);
        tvScanStatus.setText("Scanning...");
    }
    
    @Override
    public void handleResult(Result result) {
        // Handle the scanned result
        String scanResult = result.getText();
        
        // Stop camera
        scannerView.stopCamera();
        contentLayout.removeView(scannerView);
        
        // Update UI with scan results
        updateScanResults(scanResult);
        
        // Show success dialog
        showScanSuccessDialog();
    }
    
    private void updateScanResults(String scanResult) {
        tvScanStatus.setText("Scan Complete");
        btnStartScan.setVisibility(View.VISIBLE);
        scanResultLayout.setVisibility(View.VISIBLE);
        
        // Simulate data based on scan result
        // In real app, this would come from API
        tvLine.setText("Line: 1500");
        tvAqNumber.setText("AQ#: AQ12345678");
        tvImo.setText("IMO: IMO1234567");
        tvStatus.setText("Status: Shipped");
        tvLocation.setText("Location: 120 Paris");
        tvShippingDate.setText("Shipping Date: 07/08/2025");
    }
    
    private void showScanSuccessDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_scan_success, null);
        
        new MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
                showPermissionDeniedDialog();
            }
        }
    }
    
    private void showPermissionDeniedDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Camera Permission Required")
                .setMessage("This app needs camera permission to scan barcodes.")
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (scannerView != null) {
            scannerView.stopCamera();
        }
    }
}