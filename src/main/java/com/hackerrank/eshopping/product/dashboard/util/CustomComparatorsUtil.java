package com.hackerrank.eshopping.product.dashboard.util;

import com.hackerrank.eshopping.product.dashboard.model.Product;

import java.util.Comparator;

public interface CustomComparatorsUtil {
    Comparator<Product> idComparator = (p1, p2) -> Long.compare(p1.getId(), p2.getId());
    Comparator<Product> discountComparator = (p1, p2) -> Double.compare(p1.getDiscountedPrice(), p2.getDiscountedPrice());
    Comparator<Product> stockComparator = (p1, p2) -> {
        if (p1.getAvailability() && p2.getAvailability())
            if (p1.getDiscountedPrice().equals(p2.getDiscountedPrice())) {
                return idComparator.compare(p1, p2);
            } else {
                return discountComparator.compare(p1,p2);
            }
        else
            return p2.getAvailability().compareTo(p1.getAvailability());
    };
    Comparator<Product> categoryAndAvailabilityComparator = (p1, p2) -> {

        Double discountPercentageProduct1  = getdiscountedPercentage(p1.getRetailPrice(),p1.getDiscountedPrice());
        Double discountPercentageProduct2  = getdiscountedPercentage(p2.getRetailPrice(),p2.getDiscountedPrice());

        if(discountPercentageProduct1 != discountPercentageProduct2){
            return (int)(discountPercentageProduct2 - discountPercentageProduct1);
//            return discountPercentageProduct2.compareTo(discountPercentageProduct1);
        }else{
            if(p1.getDiscountedPrice().equals(p2.getDiscountedPrice())){
                return discountComparator.compare(p1,p2);
            }else{
                return idComparator.compare(p1,p2);
            }
        }
    };
    static double getdiscountedPercentage(Double retailPrice, Double discountedPrice){
        return ((retailPrice - discountedPrice)/retailPrice)*100;
    }
}

