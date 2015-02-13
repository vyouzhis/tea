package org.ppl.BaseClass;

import org.ppl.core.PObject;

public abstract class BaseCronThread extends PObject{
	public BaseCronThread() {
		// TODO Auto-generated constructor stub
		ThreadSetCon();
	}
	public abstract int minute();
	public abstract int hour();
	public abstract int day();
	public abstract void Run();
}
