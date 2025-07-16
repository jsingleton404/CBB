// build.gradle (app level)
// Add these dependencies to your app's build.gradle file

dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.recyclerview:recyclerview:1.3.1'
    implementation 'androidx.cardview:cardview:1.0.0'
    
    // For barcode scanning
    implementation 'me.dm7.barcodescanner:zxing:1.9.13'
    implementation 'com.google.zxing:core:3.5.1'
    
    // For image loading (if needed for profile pictures)
    implementation 'com.github.bumptech.glide:glide:4.15.1'
    
    // For HTTP requests (if needed for API calls)
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'
    
    // For local database (if needed)
    implementation 'androidx.room:room-runtime:2.5.2'
    annotationProcessor 'androidx.room:room-compiler:2.5.2'
}

// ScanHistoryItem.java
// Place in com.cornerstone.buildingbrands.models package
package com.cornerstone.buildingbrands.models;

public class ScanHistoryItem {
    private String type;
    private String aqNumber;
    private String imo;
    private String location;
    private String status;
    private String time;
    
    public ScanHistoryItem(String type, String aqNumber, String imo, 
                          String location, String status, String time) {
        this.type = type;
        this.aqNumber = aqNumber;
        this.imo = imo;
        this.location = location;
        this.status = status;
        this.time = time;
    }
    
    // Getters
    public String getType() { return type; }
    public String getAqNumber() { return aqNumber; }
    public String getImo() { return imo; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }
    public String getTime() { return time; }
    
    // Setters
    public void setType(String type) { this.type = type; }
    public void setAqNumber(String aqNumber) { this.aqNumber = aqNumber; }
    public void setImo(String imo) { this.imo = imo; }
    public void setLocation(String location) { this.location = location; }
    public void setStatus(String status) { this.status = status; }
    public void setTime(String time) { this.time = time; }
}

// RepairItem.java
// Place in com.cornerstone.buildingbrands.models package
package com.cornerstone.buildingbrands.models;

public class RepairItem {
    private String type;
    private String name;
    private String imoNumber;
    private String status;
    private String jobNumber;
    private String eta;
    private boolean needsApproval;
    
    public RepairItem(String type, String name, String imoNumber, String status, 
                     String jobNumber, String eta, boolean needsApproval) {
        this.type = type;
        this.name = name;
        this.imoNumber = imoNumber;
        this.status = status;
        this.jobNumber = jobNumber;
        this.eta = eta;
        this.needsApproval = needsApproval;
    }
    
    // Add getters and setters...
}

// UnitItem.java
// Place in com.cornerstone.buildingbrands.models package
package com.cornerstone.buildingbrands.models;

public class UnitItem {
    private String aqNumber;
    private String imoNumber;
    private String status;
    private String comment;
    private boolean isSpecial;
    
    public UnitItem(String aqNumber, String imoNumber, String status, 
                   String comment, boolean isSpecial) {
        this.aqNumber = aqNumber;
        this.imoNumber = imoNumber;
        this.status = status;
        this.comment = comment;
        this.isSpecial = isSpecial;
    }
    
    // Add getters and setters...
}