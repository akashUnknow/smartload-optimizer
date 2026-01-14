package com.akash.optimizer.service;

import com.akash.optimizer.model.OptimizeRequest;
import com.akash.optimizer.model.OptimizeResponse;
import com.akash.optimizer.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoadOptimizerService {
    private long bestPayout=0;
    private int bestWeight=0;
    private int bestVolume=0;
    private long bestMask=0;
    private List<Order> orders;
    private long[] suffixMax;
    private int maxWeight;
    private int maxVolume;
    public OptimizeResponse optimize(OptimizeRequest request) {
        this.orders=filterOrders(request.orders);
        this.maxVolume=request.truck.max_volume_cuft;
        this.maxWeight=request.truck.max_weight_lbs;
        int n=orders.size();
        suffixMax=new long[n+1];
        for (int i=n-1;i>=0;i--){
            suffixMax[i]=suffixMax[i+1]+orders.get(i).payout_cents;
        }
        dfs(0,0,0,0,0);
        List<String> selected=new ArrayList<>();
        for (int i=0;i<n;i++){
            if ((bestMask &(1L<<i))!=0){
                selected.add(orders.get(i).id);
            }
        }
        OptimizeResponse res = new OptimizeResponse();
        res.truck_id = request.truck.id;
        res.selected_order_ids = selected;
        res.total_payout_cents = bestPayout;
        res.total_weight_lbs = bestWeight;
        res.total_volume_cuft = bestVolume;
        res.utilization_weight_percent = bestWeight * 100.0 / maxWeight;
        res.utilization_volume_percent = bestVolume * 100.0 / maxVolume;
        return res;
    }
    private void dfs(int idx, long payout, int weight, int volume, long mask) {
        if (weight > maxWeight || volume > maxVolume) return;
        if (idx == orders.size()) {
            if (payout > bestPayout) {
                bestPayout = payout;
                bestWeight = weight;
                bestVolume = volume;
                bestMask = mask;
            }
            return;
        }
        if (payout + suffixMax[idx] <= bestPayout) return;


        dfs(idx + 1, payout, weight, volume, mask);
        Order o = orders.get(idx);
        dfs(idx + 1, payout + o.payout_cents, weight + o.weight_lbs, volume + o.volume_cuft, mask | (1L << idx));
    }
    private List<Order> filterOrders(List<Order> input) {
        if (input == null) return List.of();
        List<Order> result = new ArrayList<>();
        String origin = null, destination = null;
        for (Order o : input) {
            if (origin == null) {
                origin = o.origin;
                destination = o.destination;
            }
            if (!o.origin.equals(origin) || !o.destination.equals(destination)) continue;
            if (o.weight_lbs <= 0 || o.volume_cuft <= 0) continue;
            result.add(o);
        }
        return result;
    }
}
