package com.example.demo.model.Response;

import java.util.List;

import com.example.demo.entity.PODSlot;

public class PODBookingResponse {
    private List<PODSlot> slots;
    private float totalPrice;
    private Long bookID;

    public PODBookingResponse(){

    }

    public List<PODSlot> getSlots(){
        return this.slots;
    }
    public float getTotalPrice(){
        return totalPrice;
    }
    public Long getBookID(){
        return bookID;
    }

    public void setSlots(List<PODSlot> slots){
        this.slots = slots;
    }
    public void setTotalPrice(float totalPrice){
        this.totalPrice = totalPrice;
    }
    public void setBookID(Long bookID){
        this.bookID = bookID;
    }
}