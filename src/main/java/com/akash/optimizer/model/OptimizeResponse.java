package com.akash.optimizer.model;

import java.util.List;

public class OptimizeResponse {
    public String truck_id;
    public List<String> selected_order_ids;
    public long total_payout_cents;
    public int total_weight_lbs;
    public int total_volume_cuft;
    public double utilization_weight_percent;
    public double utilization_volume_percent;
}
