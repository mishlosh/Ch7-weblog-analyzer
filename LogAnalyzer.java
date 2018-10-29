/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 * @author Michal Legocki
 * @version     2018.10.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[] dayCounts;
    private int[] monthCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new int[32];
        monthCounts = new int[13];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }
    
    /**
     * Create an object to analyze hourly web accesses, accepting a string
     * of the file name
     * @param filename the name of the file to be analyzed.
     */
    public LogAnalyzer(String filename)
    {
        hourCounts = new int[24];
        dayCounts = new int[32];
        monthCounts = new int[13];
        reader = new LogfileReader(filename);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
    }
    
    public void analyzeMonthlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthCounts[month]++;
        }
    }
    
    /**
     * Returns the busiest hour in the log
     * @return the busiest hour
     */
    public int busiestHour(){
        int busiest = 0;
        for (int c=1 ; c<24 ; c++){
            if (hourCounts[c] > hourCounts[busiest]){
                busiest = c;
            }
        }        
        return busiest;
    }
    
    /**
     * Returns the quietest hour in the log
     * @return the quietest hour
     */
    public int quietestHour(){
        int quietest = hourCounts[0];
        for (int c=1 ; c<24 ; c++){
            if (hourCounts[c] < hourCounts[quietest]){
                quietest = c;
            }
        }
        return quietest;
    }
    
    /**
     * Returns the busiest two hour period in the log
     * @return the first hour of the busiest two hour period
     */
    public int busiestTwoHour(){
        int busiest = 0;
        for (int c=1 ; c<24 ; c++){
            if (hourCounts[c] + hourCounts[(c+1)%24] > 
            hourCounts[busiest] + hourCounts[busiest+1]){
                busiest = c;
            }
        }
        return busiest;
    }
    
    public int quietestDay(){
        int quietest = 1;
        for (int c=2 ; c<32 ; c++){
            if (dayCounts[c] < dayCounts[quietest]){
                quietest = c;
            }
        }
        return quietest;
    }
    
    
    public int busiestDay(){
        int busiest = 1;
        for (int c=2 ; c<32 ; c++){
            if (dayCounts[c] > dayCounts[busiest]){
                busiest = c;
            }
        }
        return busiest;
    }
    
    
    public int quietestMonth(){
        int quietest = 1;
        for (int c=2 ; c<13 ; c++){
            if (monthCounts[c] < monthCounts[quietest]){
                quietest = c;
            }
        }
        return quietest;
    }
    
    
    public int busiestMonth(){
        int busiest = 1;
        for (int c=2 ; c<13 ; c++){
            if (dayCounts[c] > dayCounts[busiest]){
                busiest = c;
            }
        }
        return busiest;
    }
    
    /**
     * Totals the number of accesses
     * @return number of accesses in the log
     */
    public int numberOfAccesses()
    {
        int accesses = 0;
        while(reader.hasNext()) {
            reader.next();
            accesses++;
        }
        return accesses;
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    
    public void totalAccessesPerMonth()
    {
        System.out.println("Month : Count");
        for(int month = 1; month < 13 ; month++)
        {
            System.out.println(month + " : " + monthCounts[month]);
        }
    }
    
    
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
