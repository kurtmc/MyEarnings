package com.mcalpinedevelopment.calculatepay;

import java.util.ArrayList;
import java.util.List;

public class Pie {
	
	private double _width;
	private double _height;
	private double _radius;
	
	private int _lastX;
	private int _lastY;
	
	private double _angle;
	

	
	public Pie(int size) {
		_width = size;
		_height = size;
		_radius = size/2;
		_angle = 0;
		
		_lastX= toInt(_width/2);
		_lastY = 0;
	}
	
	public int[] centre() {
		return new int[] {toInt(_radius), toInt(_radius)};	
	}
	
	
	
	public List<int[]> slice(double percentage) {
		List<int[]> points = new ArrayList<int[]>();
		points.add(centre()); //Centre first
		points.add(new int[] {_lastX, _lastY}); //Top next
		
		double divisions = 100;
		for (int i = 0; i < toInt(divisions); i++) {
			_angle += 2*Math.PI*(percentage/100)/divisions;
			points.add(new int[] {toInt(x(_angle, _radius, _radius)), toInt(y(_angle, _radius, _radius))});
		}
		

//		_angle += 2*Math.PI*(percentage/100);
		_lastX = toInt(x(_angle, _radius, _radius));
		_lastY = toInt(y(_angle, _radius, _radius));
		points.add(new int[] {_lastX, _lastY});
		points.add(centre());
		
		
		return points;		
	}
	
	private double x(double angle, double radius, double centre) {
    	return centre - radius*Math.cos((angle+Math.PI/2));
    }
    private double y(double angle, double radius, double centre) {
    	return centre - radius*Math.sin((angle+Math.PI/2));
    }	
	
	private int toInt(double value) {
		return Math.round((float)value);
	}
}
