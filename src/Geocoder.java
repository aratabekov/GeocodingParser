import java.io.IOException;
import java.util.*;

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
				pair.set(address.getTFIDL(), address.getFullName());
				output.collect(pair, address.getAddressInfo());
				
				pair.set(address.getTFIDR(), address.getFullName());
				output.collect(pair, address.getAddressInfo());
			} else {
				FaceAttributes face=FaceAttributes.parse(info);
				pair.set(face.getTFID(), "*"); //That way reducer will always receive info from FACES first
				output.collect(pair, face.getFaceInfo());
			}
			
		}
		
	}
	
	public static class GeocoderReducer extends MapReduceBase implements
		Reducer<PairOfStrings, Text, PairOfStrings, Text> {

		String facesKey = new String();
		String facesInfo = new String(); 
		
		/**
		 * FACES info will always be first, then ADDRESS info
		 */
		@Override
		public void reduce(PairOfStrings key, Iterator<Text> values,
				OutputCollector<PairOfStrings, Text> output, Reporter reporter) throws IOException {
			
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
				String TLid = "";
				
				while (values.hasNext()) {
					String info = values.next().toString();
					
					AddrFeat address =  AddrFeat.prase(info);
					streetName = address.getFullName();
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
				
				String combinedInfo = 
									facesInfo.replace(facesKey, "") + "\t" +
									streetName + " | " + 
									LFrom_LTo.toString() + " | " + 
									RFrom_RTo.toString() + " | " + 
									LZips.toString() + " | " + 
									RZips.toString();
			
				if(facesInfo.trim().length()>0){
					output.collect(key, new Text(combinedInfo));
				}
				
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

}
