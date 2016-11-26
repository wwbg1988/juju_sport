package com.juju.sport.common.support;

public interface Queue<T> {

	void push(T t);
	
	T pop();
	
	T bPop();
	
	void empty();
}
