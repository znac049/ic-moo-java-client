package uk.org.wookey.IC.Utils;

import java.util.ArrayList;

public class AssociativeArray {
	private ArrayList<String> _names;
	private ArrayList<Object> _values;
	
	public AssociativeArray() {
		_names = new ArrayList<String>();
		_values = new ArrayList<Object>();
	}

	public void add(String name, Object ob) {
		_names.add(name);
		_values.add(ob);
	}
	
	public Object get(String name) {
		for (int i=0; i<_names.size(); i++) {
			if (_names.get(i).equals(name)) {
				return _values.get(i);
			}
		}
		
		return null;
	}
	
	public Object get(int i) {
		if ((i < 0) || (i >= _values.size())) {
			return null;
		}
		
		return _values.get(i);
	}
	
	public int getIndex(String name) {
		
		for (int i=0; i<_names.size(); i++) {
			if (_names.get(i).equals(name)) {
				return i;
			}
		}
		
		return -1;
	}

	public void clear() {
		_names.clear();
		_values.clear();
	}
	
	public void remove(int i) {
		if ((i >= 0) && (i < _names.size())) {
			_names.remove(i);
			_values.remove(i);
		}
	}
}