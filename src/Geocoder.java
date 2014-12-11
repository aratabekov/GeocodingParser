
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
//test
public class Geocoder {

	static enum MoreIterations {
		numberOfIterations
	}
	
	public static class GeocoderMapper extends MapReduceBase implements 
		Mapper<LongWritable, Text, PairOfStrings, Text> {
	
		private PairOfStrings pair = new PairOfStrings();
		
		@Override
		public void map(LongWritable key, Text value,
				OutputCollector<PairOfStrings, Text> output, Reporter reporter) throws IOException {
			
			String info =value.toString();
			
			if (AddrFeat.isAddrFeat(info)) {
				AddrFeat address=AddrFeat.prase(info);
				pair.set(address.getTFIDL(), address.getTLID());
				output.collect(pair, address.getAddressInfo());
				
				pair.set(address.getTFIDR(), address.getTLID());
				output.collect(pair, address.getAddressInfo());
			} else {
				FaceAttributes face=FaceAttributes.parse(info);
				pair.set(face.getTFID() ,"*"); //That way reducer will always receive info from FACES first
				output.collect(pair, face.getFaceInfo());
			}
			
		}
		
	}
	
	public static class GeocoderReducer extends MapReduceBase implements
		Reducer<PairOfStrings, Text, PairOfStrings, Text> {

		
		  private static Map<String,String> cousubCodes= new HashMap<String,String>();
	      
	       public void configure(JobConf job)
	       {
	              //To load the Delivery Codes and Messages into a hash map
	              loadCousubCodes();
	             
	       }
	      
	       
		String facesKey = new String();
		String facesInfo = new String(); 
		String TLid = "";
		/**
		 * FACES info will always be first, then ADDRESS info
		 */
		@Override
		public void reduce(PairOfStrings key, Iterator<Text> values,
				OutputCollector<PairOfStrings, Text> output, Reporter reporter) throws IOException {
			//System.out.println("doing reduce");
			if (key.getRightElement().equals("*")) { //first time reducer is called (FACES info)
				facesKey = key.getLeftElement();
				facesInfo = values.next().toString(); // only one FACES info 
			} else
			{
				ArrayList<String> LFrom_LTo = new ArrayList<String>();
				ArrayList<String> RFrom_RTo = new ArrayList<String>();
				String LZips = new String();
				String RZips = new String();
				String streetName = "";
				
				
				while (values.hasNext()) {
					String info = values.next().toString();
					
					AddrFeat address =  AddrFeat.prase(info);
					
					//It's possible for a record with TLID,TFID combination to have different street names,
					//it happens when a street was recently renamed, so the there's 2 exactly same records
					//but with a different street name
					if(streetName.length()>0 && !streetName.contains(address.getFullName())){
						streetName+=","+address.getFullName();
					}
					else
					{
						streetName = address.getFullName();
					}
					/*if(TLid.length()>0 && !address.getTLID().equals(TLid)){
						System.out.println("new tlid here "+address.getTLID()+" "+address.getFullName());
					}*/
					
					TLid = address.getTLID();
					
					if (address.getLfromhn().trim().length() > 0)
						LFrom_LTo.add(address.getLfromhn()+"-"+address.getLtohn());
					
					if (address.getRfromhn().trim().length() > 0)
						RFrom_RTo.add(address.getRfromhn()+"-"+address.getRtohn());
						
					if (address.getZipl().trim().length() > 0)
						LZips += address.getZipl();
					
					if (address.getZipr().trim().length() > 0)
						RZips += address.getZipr();				
				}
				
				if(facesInfo.trim().length()>0){
					FaceAttributes face=FaceAttributes.parse(facesInfo);
					String combinedInfo = face.getTFID()+" | "+
							face.getStateFips() + " | " +
									cousubCodes.get(face.getCousub().trim()) + " | " +
									streetName + " | " + 
									LFrom_LTo.toString() + " | " + 
									RFrom_RTo.toString() + " | " + 
									LZips.toString() + " | " + 
									RZips.toString();


					output.collect(key, new Text(combinedInfo));
				}
				
			}
			
		}
		
		 private void loadCousubCodes()
	       {
	    	   String strRead;
	    	   try {
	    		   //read file from Distributed Cache
	    		   String str=new String("hdfs://127.0.0.1:9000/cities/cities.txt");
                 FileSystem fs = FileSystem.get(URI.create(str),new Configuration());
                 BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(new Path(str))));
	    		   while ((strRead=br.readLine() ) != null)
	    		   {
	    			   String splitarray[] = strRead.split(",");
	    			   //parse record and load into HahMap
	    			   cousubCodes.put(splitarray[1].trim(), splitarray[2].trim());

	    		   }
	    	   }
	    	   catch (FileNotFoundException e) {
	    		   e.printStackTrace();
	    	   }catch( IOException e ) {
	    		   e.printStackTrace();
	    	   }

	       }
	       
		
	}
	
	protected static class CustomPartitioner extends MapReduceBase implements Partitioner<PairOfStrings, Text> {
		
		public int getPartition(PairOfStrings key, Text value, int numReduceTasks) {
			return (key.getLeftElement().hashCode() & Integer.MAX_VALUE) % numReduceTasks;
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		String input = "hdfs://127.0.0.1:9000/in";
		String output = "hdfs://127.0.0.1:9000/out";
		
		JobConf conf = new JobConf(Geocoder.class); 
	    conf.setJobName("Geocoder"); 
	    conf.setNumReduceTasks(2);
	    
	    conf.setOutputKeyClass(PairOfStrings.class); 
	    conf.setOutputValueClass(Text.class); 
	  
	    conf.setMapperClass(GeocoderMapper.class); 
	    conf.setReducerClass(GeocoderReducer.class); 
	    conf.setPartitionerClass(CustomPartitioner.class);
	  
	    conf.setInputFormat(TextInputFormat.class); 
	    conf.setOutputFormat(TextOutputFormat.class); 
	  

	    FileOutputFormat.setOutputPath(conf, new Path(output)); 
	    FileInputFormat.setInputPaths(conf, new Path(input)); 
	  
	    JobClient.runJob(conf); 
	}

	public void loadCousubCodes() {
		// TODO Auto-generated method stub
		
	}

}
