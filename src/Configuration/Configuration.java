package Configuration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

//Configuration stores config needed to spin up a SkipGraph node
//current fields:
//   - introducer (address)
//   - nameID
//   - numID
//   - port (RMI)

public final class Configuration implements Serializable{



	private static final long serialVersionUID = 1L;
	private static final String defaultPath = System.getProperty("user.dir") + "/node.conf"; 
	private String configPath;
	Properties prop = new Properties();

	// Fields
	private String introducer  = "none";
	private String nameID      = "nameID";
	private String numID       = "0";
	private String port        = "1099";


	public Configuration(String introducer, String nameID, String numID, String port) {
		this.introducer = introducer;
		this.nameID     = nameID;
		this.numID      = numID;
		this.port       = port;
	}

	public Configuration(String configPath) {
		this.configPath = configPath;
	}

	public Configuration() {
		this.configPath = defaultPath;
	}

	public void parseConfig() {
		try {
			prop.load(new FileInputStream(configPath));
		} catch(Exception e) {
			System.err.println("could not open config file: " + configPath);
			System.exit(1);
		}
		introducer = prop.getProperty("introducer");
		nameID = prop.getProperty("nameID");
		numID  = prop.getProperty("numID");
		port   = prop.getProperty("port");
	}

	public static ArrayList<Configuration> parseConfigurations(){
		return parseConfigurations(defaultPath);
	}

	public static ArrayList<Configuration> parseConfigurations(String path){
		ArrayList<Configuration> lst = new ArrayList<Configuration>();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(path));
			String input = in.readLine();
			int n = Integer.parseInt(input);
			for(int i = 0 ; i < n ; i++) {
				String introducer;
				String nameID;
				String numID;
				String port;
				introducer = in.readLine().split("=")[1];
				nameID = in.readLine().split("=")[1];
				numID = in.readLine().split("=")[1];
				port = in.readLine().split("=")[1];
				lst.add(new Configuration(introducer,nameID,numID,port));
			}
			in.close();
		} catch (Exception e) {

		}

		return lst;
	}


	public void parseIntroducer() {
		try {
			prop.load(new FileInputStream(configPath));
		} catch(Exception e) {
			System.err.println("could not open config file: " + configPath);
			System.exit(1);
		}
		introducer = prop.getProperty("introducer");
	}

	public String getIntroducer() {
		return introducer;
	}
	
	public void setIntroducer(String introducer) {
		this.introducer= introducer;
	}

	public String getNameID() {
		return nameID;
	}

	public String getNumID() {
		return numID;
	}

	public String getPort() {
		return port;
	}
}
