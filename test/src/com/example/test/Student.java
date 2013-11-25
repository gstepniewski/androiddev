package com.example.test;

public class Student {

	private int mId;
	private String mName;
	private String mFaculty;

	public Student() {
	}
	
	public Student(String name, String faculty) {
		this.mName = name;
		this.mFaculty = faculty;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getFaculty() {
		return mFaculty;
	}

	public void setFaculty(String faculty) {
		this.mFaculty = faculty;
	}

}
