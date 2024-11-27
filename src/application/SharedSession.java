package application;

public class SharedSession {
    private static String userEmail;
    private static String userPassword;
    private static int userbookingid;
    private static double usersum;
    
    private static String userAccNumber;
    private static String userAccPass;
    
    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String email) {
        userEmail = email;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public static void setUserPassword(String password) {
        userPassword = password;
    }
    
    public static int getUserId() {
        return userbookingid;
    }
    
    public static void setUserId(int bookingid) {
    	userbookingid = bookingid;
    }
    
    
    public static double getSum() {
        return usersum;
    }
    
    public static void setSum(double sum) {
    	usersum = sum;
    }
    
  
}
