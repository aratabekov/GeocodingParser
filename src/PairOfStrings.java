
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;


/**
 * WritableComparable representing a pair of Strings. The elements in the pair
 * are referred to as the left and right elements. The natural sort order is:
 * first by the left element, and then by the right element.
 * 
 * @author Jimmy Lin
 */
public class PairOfStrings implements WritableComparable<PairOfStrings> {

	private String leftElement, rightElement;

	/**
	 * Creates a pair.
	 */
	String tlid;
	public PairOfStrings() {}

	/**
	 * Creates a pair.
	 *
	 * @param left the left element
	 * @param right the right element
	 */
	public PairOfStrings(String left, String right, String tlid) {
		//this.tlid=tlid;
		set(left, right,tlid);
	}
	public String getTLID(){
		return this.tlid;
	}

	/**
	 * Deserializes the pair.
	 *
	 * @param in source for raw byte representation
	 */
	public void readFields(DataInput in) throws IOException {
		leftElement = in.readUTF();
		rightElement = in.readUTF();
		tlid=in.readUTF();
	}

	/**
	 * Serializes this pair.
	 *
	 * @param out where to write the raw byte representation
	 */
	public void write(DataOutput out) throws IOException {
		out.writeUTF(leftElement);
		out.writeUTF(rightElement);
		out.writeUTF(tlid);
	}

	/**
	 * Returns the left element.
	 *
	 * @return the left element
	 */
	public String getLeftElement() {
		return leftElement;
	}

	/**
	 * Returns the right element.
	 *
	 * @return the right element
	 */
	public String getRightElement() {
		return rightElement;
	}

	/**
	 * Returns the key (left element).
	 *
	 * @return the key
	 */
	public String getKey() {
		return leftElement;
	}

	/**
	 * Returns the value (right element).
	 *
	 * @return the value
	 */
	public String getValue() {
		return rightElement;
	}

	/**
	 * Sets the right and left elements of this pair.
	 *
	 * @param left the left element
	 * @param right the right element
	 */
	public void set(String left, String right, String tlid) {
		leftElement = left;
		rightElement = right;
		this.tlid=tlid;
	}

	/**
	 * Checks two pairs for equality.
	 *
	 * @param obj object for comparison
	 * @return <code>true</code> if <code>obj</code> is equal to this object, <code>false</code> otherwise
	 */
	public boolean equals(Object obj) {
		PairOfStrings pair = (PairOfStrings) obj;
		return leftElement.equals(pair.getLeftElement())
				&& rightElement.equals(pair.getRightElement());
	}

	/**
	 * Defines a natural sort order for pairs. Pairs are sorted first by the
	 * left element, and then by the right element.
	 *
	 * @return a value less than zero, a value greater than zero, or zero if
	 *         this pair should be sorted before, sorted after, or is equal to
	 *         <code>obj</code>.
	 */
	public int compareTo(PairOfStrings pair) {
		String pl = pair.getLeftElement();
		String pr = pair.getRightElement();

		if (leftElement.equals(pl)) {
			return rightElement.compareTo(pr);
		}

		return leftElement.compareTo(pl);
	}

	/**
	 * Returns a hash code value for the pair.
	 *
	 * @return hash code for the pair
	 */
	public int hashCode() {
		return leftElement.hashCode() + rightElement.hashCode();
	}

	/**
	 * Generates human-readable String representation of this pair.
	 *
	 * @return human-readable String representation of this pair
	 */
	public String toString() {
		//return "(" + leftElement + ", " + rightElement + ")";
		return "(" + tlid+ ")";
		
	}

	/**
	 * Clones this object.
	 *
	 * @return clone of this object
	 */
	public PairOfStrings clone() {
		return new PairOfStrings(this.leftElement, this.rightElement, this.tlid);
	}

}