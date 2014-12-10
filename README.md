GeocodingParser
===============

The file called edges.txt contains edges table.
Here is how to parse the record:
That's how I implemented it's toString method, so parse it
by looking at the code below

result+=TLID+"\t"+TFIDL+"\t"+TFIDR+"\t"+fullName+"\t"
		+fromhn+"\t"+tohn+"\t"+side+"\t"+zip+"\t"+cousubr+"\t"+cousubl;
		return result;

The file called addr.txt contains address table.
Here is how to parse the record:
That's how I implemented it's toString method, so parse it
by looking at the code below

return tlid+"\t"+fromhn+"\t"+tohn+"\t"+side+"\t"+zip;


As we can see, both have a variable called TLID, that's the key by which we will join these 2 tables. 

The third table is coming up, but for now you can start joining these two.
You will have to write a method to determine if the given record is edges or addr record.

Have to write something like Edges.isEdges(String line);