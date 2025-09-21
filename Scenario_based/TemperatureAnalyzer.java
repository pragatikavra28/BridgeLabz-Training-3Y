public class TemperatureAnalyzer {
    
    public static void analyzeTemperatures(float[][] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            System.out.println("No temperature data provided");
            return;
        }
        
        float hottestTemp = Float.MIN_VALUE;
        float coldestTemp = Float.MAX_VALUE;
        int hottestDay = -1;
        int coldestDay = -1;
        
        System.out.println("Daily average temperatures:");
        for (int day = 0; day < temperatures.length; day++) {
            float dailySum = 0;
            
            for (int hour = 0; hour < temperatures[day].length; hour++) {
                dailySum += temperatures[day][hour];
                
                if (temperatures[day][hour] > hottestTemp) {
                    hottestTemp = temperatures[day][hour];
                    hottestDay = day;
                }
                
                if (temperatures[day][hour] < coldestTemp) {
                    coldestTemp = temperatures[day][hour];
                    coldestDay = day;
                }
            }
            
            float dailyAvg = dailySum / temperatures[day].length;
            System.out.println("Day " + (day + 1) + ": " + dailyAvg + "°C");
        }
        
        System.out.println("\nHottest day: Day " + (hottestDay + 1) + " with " + hottestTemp + "°C");
        System.out.println("Coldest day: Day " + (coldestDay + 1) + " with " + coldestTemp + "°C");
    }
    
    public static void main(String[] args) {
        float[][] weeklyTemperatures = {
            {15.5f, 16.2f, 17.8f, 18.5f, 19.2f, 20.1f, 21.5f, 22.8f, 24.1f, 25.3f, 26.2f, 27.1f, 
             27.5f, 27.8f, 27.2f, 26.5f, 25.8f, 24.2f, 22.5f, 20.8f, 19.2f, 18.1f, 17.2f, 16.5f},
            {14.8f, 15.5f, 16.8f, 17.5f, 18.2f, 19.1f, 20.5f, 21.8f, 23.1f, 24.3f, 25.2f, 26.1f, 
             26.5f, 26.8f, 26.2f, 25.5f, 24.8f, 23.2f, 21.5f, 19.8f, 18.2f, 17.1f, 16.2f, 15.5f},
            {16.2f, 17.0f, 18.5f, 19.2f, 20.0f, 21.2f, 22.8f, 24.1f, 25.5f, 26.8f, 27.5f, 28.2f, 
             28.8f, 29.1f, 28.5f, 27.8f, 26.5f, 24.8f, 23.2f, 21.5f, 20.2f, 19.1f, 18.2f, 17.5f}
        };
        
        analyzeTemperatures(weeklyTemperatures);
    }
}