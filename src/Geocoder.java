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
		Mapper<Text, Text, Text, Text> {
	
		@Override
		public void map(Text key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			
			Address address = new Address(value.toString());
			
			if (address.isAddrFeat()) {
				output.collect(new Text(address.getTFIDL()), address.getAddressInfo());
				output.collect(new Text(address.getTFIDR()), address.getAddressInfo());
			} else {
				output.collect(new Text(address.getTfid()), address.getAddressInfo());
			}
			
		}
		
	}
	
	public static class Reduce extends MapReduceBase implements
		Reducer<Text, Text, Text, Text> {
		
		@Override
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			
		}
		
	}
	
	public static JobConf setupJob() throws Exception {
		JobConf conf = new JobConf(Geocoder.class);
		
		conf.setJobName("Geocoder");
		conf.setNumReduceTasks(2);
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		
		conf.setMapperClass(GeocoderMapper.class);
		conf.setReducerClass(Reduce.class);
		
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		
		return conf;		
	}
	
	public static void main(String[] args) throws Exception {
		
		int numIteration = 0;
		long termination = 1;

		while (termination > 0) {
			JobConf conf = setupJob();

			String input = "", output = "";

			if (numIteration == 0)
				input = "hdfs://127.0.0.1:9000/in";
			else
				input = "hdfs://127.0.0.1:9000/out" + numIteration;

			output = "hdfs://127.0.0.1:9000/out" + (numIteration + 1);

			FileInputFormat.setInputPaths(conf,  input);
			FileOutputFormat.setOutputPath(conf, new Path(output));

			RunningJob job = JobClient.runJob(conf);
			
			Counters jobCntrs = job.getCounters();
			termination = jobCntrs.findCounter(
					MoreIterations.numberOfIterations).getValue();
			numIteration++;

		}

	}

}
