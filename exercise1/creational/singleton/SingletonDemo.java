package creational.singleton;


public class SingletonDemo {
    public static void main(String[] args) {
        ConfigurationManager configManager1 = ConfigurationManager.getInstance();
        System.out.println(configManager1.getConfig()); 

        ConfigurationManager configManager2 = ConfigurationManager.getInstance();
        configManager2.setConfig("New Configuration");

        System.out.println(configManager1.getConfig()); 
    }
}
