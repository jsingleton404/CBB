package com.cornerstone.buildingbrands.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cornerstone.buildingbrands.R;
import com.cornerstone.buildingbrands.models.ScanHistoryItem;
import java.util.ArrayList;
import java.util.List;

public class ScanHistoryFragment extends Fragment {
    
    private RecyclerView recyclerView;
    private ScanHistoryAdapter adapter;
    private TextView tvEmptyState;
    private Button btnScanNow;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_history, container, false);
        
        initializeViews(view);
        loadScanHistory();
        
        return view;
    }
    
    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_scan_history);
        tvEmptyState = view.findViewById(R.id.tv_empty_state);
        btnScanNow = view.findViewById(R.id.btn_scan_now);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ScanHistoryAdapter();
        recyclerView.setAdapter(adapter);
        
        btnScanNow.setOnClickListener(v -> {
            // Navigate to scanner
            getActivity().startActivity(new Intent(getContext(), ScannerActivity.class));
        });
    }
    
    private void loadScanHistory() {
        // TODO: Load from database or API
        List<ScanHistoryItem> items = getDummyData();
        
        if (items.isEmpty()) {
            showEmptyState();
        } else {
            showScanHistory(items);
        }
    }
    
    private void showEmptyState() {
        recyclerView.setVisibility(View.GONE);
        tvEmptyState.setVisibility(View.VISIBLE);
        btnScanNow.setVisibility(View.VISIBLE);
    }
    
    private void showScanHistory(List<ScanHistoryItem> items) {
        recyclerView.setVisibility(View.VISIBLE);
        tvEmptyState.setVisibility(View.GONE);
        btnScanNow.setVisibility(View.GONE);
        adapter.setItems(items);
    }
    
    private List<ScanHistoryItem> getDummyData() {
        List<ScanHistoryItem> items = new ArrayList<>();
        
        items.add(new ScanHistoryItem(
            "AQ_NUMBER", "AQ12345678", "IMO1234567", 
            "Warehouse A", "Shipped", "Today, 2:30 PM"
        ));
        
        items.add(new ScanHistoryItem(
            "IMO", "AQ87654321", "IMO7654321",
            "Warehouse B", "Shipped", "Yesterday, 4:15 PM"
        ));
        
        items.add(new ScanHistoryItem(
            "AQ_NUMBER", "AQ11223344", "IMO3344556",
            "Warehouse C", "Pending", "July 9, 10:22 AM"
        ));
        
        return items;
    }
    
    // Adapter class
    public static class ScanHistoryAdapter extends RecyclerView.Adapter<ScanHistoryAdapter.ViewHolder> {
        
        private List<ScanHistoryItem> items = new ArrayList<>();
        
        public void setItems(List<ScanHistoryItem> items) {
            this.items = items;
            notifyDataSetChanged();
        }
        
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_scan_history, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(items.get(position));
        }
        
        @Override
        public int getItemCount() {
            return items.size();
        }
        
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvType, tvAqNumber, tvImo, tvLocation, tvStatus, tvTime;
            View statusBadge;
            
            ViewHolder(View itemView) {
                super(itemView);
                tvType = itemView.findViewById(R.id.tv_type);
                tvAqNumber = itemView.findViewById(R.id.tv_aq_number);
                tvImo = itemView.findViewById(R.id.tv_imo);
                tvLocation = itemView.findViewById(R.id.tv_location);
                tvStatus = itemView.findViewById(R.id.tv_status);
                tvTime = itemView.findViewById(R.id.tv_time);
                statusBadge = itemView.findViewById(R.id.status_badge);
            }
            
            void bind(ScanHistoryItem item) {
                tvType.setText(item.getType());
                tvAqNumber.setText("AQ#: " + item.getAqNumber());
                tvImo.setText("IMO: " + item.getImo());
                tvLocation.setText("Location: " + item.getLocation());
                tvStatus.setText(item.getStatus());
                tvTime.setText(item.getTime());
                
                // Set status badge color
                if ("Shipped".equals(item.getStatus())) {
                    statusBadge.setBackgroundResource(R.drawable.status_badge_green);
                    tvStatus.setTextColor(Color.parseColor("#27AE60"));
                } else {
                    statusBadge.setBackgroundResource(R.drawable.status_badge_orange);
                    tvStatus.setTextColor(Color.parseColor("#F39C12"));
                }
            }
        }
    }
}