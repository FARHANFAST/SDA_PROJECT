package application;

public class BookedRoomPriceCalculation {

	static double calculatePaymentAmount(String RoomType,String RoomQuantity,String RoomBookingDays) {
        double Amount = 0.0;

        if(RoomType=="Single Room") // 1000
        {
        	Amount=(1000)*(Integer.parseInt(RoomQuantity))*(Integer.parseInt(RoomBookingDays));
        }
        if(RoomType=="Double Room") // 2000
        {
        	Amount=(2000)*(Integer.parseInt(RoomQuantity))*(Integer.parseInt(RoomBookingDays));
        }
        if(RoomType=="Delux Room") // 3000
        {
        	Amount=(3000)*(Integer.parseInt(RoomQuantity))*(Integer.parseInt(RoomBookingDays));
        }
        if(RoomType=="Suit Room") // 5000
        {
        	Amount=(5000)*(Integer.parseInt(RoomQuantity))*(Integer.parseInt(RoomBookingDays));
        }
   
        return Amount;
    }
	
}